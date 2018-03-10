package cc.litstar.core;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cc.litstar.conf.ConfReader;
import cc.litstar.conf.Options;
import cc.litstar.conf.ServerConf;
import cc.litstar.node.RaftNode;
import cc.litstar.rpc.AppendEntriesArgs;
import cc.litstar.rpc.AppendEntriesArgs.LogEntry;
import cc.litstar.sm.StateMachine;
import cc.litstar.rpc.AppendEntriesReply;
import cc.litstar.rpc.ClientSubmitReply;
import cc.litstar.rpc.ClientSubmitRequest;
import cc.litstar.rpc.RaftGrpc;
import cc.litstar.rpc.RequestVoteArgs;
import cc.litstar.rpc.RequestVoteReply;
import io.grpc.stub.StreamObserver;

public class RaftCore {
	//Raft Server
	private RaftServer server;
	//RPC句柄
	private Map<Integer, RaftCall> peers;
	//RPC调用
	private RaftCallHandler handler;
	//当前编号
	private int id;
	//当前状态
	private volatile RaftStatus status;
	//投票数
	private volatile int voteCount;
	//服务器最后一次知道的任期号
	private volatile int currentTerm;
	//在当时获得选票的候选人ID
	private volatile int voteFor;
	//日志条目集，每一条日志包含一个用户状态机执行的指令，和收到的任期号
	private volatile List<LogEntryObj> log;
	//最大的已知被提交的日志条目集
	private volatile int commitIndex;
	//最后被应用到状态机的日志条目索引值
	private volatile int lastApplied;
	
	//心跳时间间隔
	private int hbInterval;
	
	//状态Channel，超时控制
	//Follower态是否心跳超时，以及Candidate态处于自己成为Leader、其他节点成为Leader(收到心跳)或者超时重选
	private LinkedBlockingQueue<String> stateChannel;
	//提交Channel
	private LinkedBlockingQueue<String> commitChannel;
	//应用Channel
	private LinkedBlockingQueue<ApplyMsg> applyChannel;
	
	//Leader相关变量
	//对于每一个服务器，需要发送给他的下一条日志的索引值
	private volatile Map<Integer, Integer> nextIndex;
	//对于每一个服务器，已经复制给他的日志的最高索引值
	private volatile Map<Integer, Integer> matchIndex;
	
	//状态机相关
	private volatile RaftApply applyHandler;
	private volatile StateMachine sm;
	
	//Config
	private ServerConf config;
	//Options
	private Options options;
	
	//线程池，RPC调用线程
	private volatile ExecutorService rpcCallPool;
	
	//输出日志信息
	private final static Logger logger = LoggerFactory.getLogger(RaftCore.class);
	
	public RaftCore() { }
	
	//返回当前任期
	public int getCurrentTerm() {
		return currentTerm;
	}
	
	//设置状态机
	public void setStateMachine(StateMachine sm) {
		this.sm = sm;
	}
	
	//设置Option
	public void setOptions(Options options) {
		this.options = options;
	}
	
	//是否是Leader
	public boolean isLeader() {
		return status == RaftStatus.LEADER;
	}
	
	//最后一条日志的索引
	public int getLastIndex() {
		return log.get(log.size() - 1).getLogIndex();
	}
	
	//最后一条日志的log任期
	public int getLastTerm() {
		return log.get(log.size() - 1).getLogTerm();
	}
	
	public int getRandomTime(int start, int end) {
		Random rand = new Random();
		return rand.nextInt(end - start) + start;
	}
	
	//参数检验
	public boolean optionCheck() {
		if(options.getIntOption("HbInterval") < 0) {
			logger.info("HbInterval is not setted or wrong value");
			return false;
		}
		return true;
	}
	
	//初始化操作
	public void init() {
		this.config = ConfReader.getConf();
		RaftNode curNode = config.getLocalNode();
		List<RaftNode> remoteList = config.getRemoteNode();
		
		this.id = curNode.getId();
		//this.hbInterval = config.getHbInterval();
		this.hbInterval = options.getIntOption("HbInterval");
		this.handler = new RaftCallHandler();
		this.server = new RaftServer(curNode.getPort(), handler);
		
		this.peers = new HashMap<>();
		for(RaftNode remoteNode : remoteList) {
			int remoteId = remoteNode.getId();
			RaftCall client = new RaftCall(remoteNode.getIpAddress(), remoteNode.getPort());
			peers.put(remoteId, client);
		}
		this.status = RaftStatus.FOLLOWER;
		this.voteFor = -1;
		
		this.log = new LinkedList<>();
		LogEntryObj firstLog = new LogEntryObj();
		firstLog.setLogTerm(0);
		this.log.add(firstLog);
		
		this.currentTerm = 0;
		this.stateChannel = new LinkedBlockingQueue<>();
		this.commitChannel = new LinkedBlockingQueue<>();
		this.applyChannel = RaftApplyMQ.getMQ();
		this.rpcCallPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2);
		
		//状态机
		this.sm = sm;
		this.applyHandler = new RaftApply(this.sm);
		new Thread(this.applyHandler).start();
		logger.info("Initializing raft successful.");
	}
	
	
	/**
	 * 启动Raft
	 * @throws IOException 
	 */
	public void start() throws IOException {
		this.server.start();
		//Raft状态变更
		new Thread(new Runnable() {		
			@Override
			public void run() {
				try {
					String msg = null;
					while(!Thread.currentThread().isInterrupted()) {
						switch (status) {
						case FOLLOWER:
							//随机超时时间
							msg = stateChannel.poll(getRandomTime(2 * hbInterval, 4 * hbInterval),
									TimeUnit.MILLISECONDS);
							logger.info("Follower: " + msg);
							//超时开始重新选举
							if(msg == null) {
								status = RaftStatus.CANDIDATE;
								logger.info("Follower: Heartheat timed out, switch to candidate");
							} else if(msg.equals("HeartBeat")) { //收到心跳
								logger.info("Follower: Heartbeat received");
							} else {}
							break;
						case LEADER:
							//广播附加日志条目，Sleep控制速率
							broadcastAppendEntries();
							logger.info("Leader: Broadcast append log entries");
							Thread.sleep(hbInterval);
							break;
						case CANDIDATE:
							synchronized (RaftCore.this) {
								//为自己投票
								currentTerm++;
								voteFor = id;
								voteCount = 1;
							}
							//CANDITATE的三种状态：
							// 1. 自己赢得了选举
							// 2. 其他服务器赢得了选举
							// 3. 一段时间后没有选出Leader
							//stateChannel.clear();//切换后收到附加日志(必定没有)
							new Thread(() -> broadcastRequestVote()).start();
							logger.info("Candidate: Broadcast vote request");
							msg = stateChannel.poll(getRandomTime(3 * hbInterval, 5 * hbInterval),
									TimeUnit.MILLISECONDS);
							logger.info("Candidate: " + msg);
							//超时开始重新选举
							if(msg == null) {
								//选票瓜分没有选出Leader，超时后重新发生选主
								logger.info("Candidate: Vote timed out, re-vote");
							} else if(msg.equals("HeartBeat")) {
								//已有节点被选为主
								status = RaftStatus.FOLLOWER;
								logger.info("Candidate: Leader was voted, switch to leader");
							} else if(msg.equals("SetLeader")) {	
								synchronized (RaftCore.this) {
									//状态切换到LEADER
									status = RaftStatus.LEADER;
									//重建Leader数据结构
									logger.info("Candidate: Win this vote, switch to leader");
									nextIndex = new HashMap<>();
									matchIndex = new HashMap<>();
									for(int server : peers.keySet()) {
										//设置初值，借助若干次附加日志RPC调整
										nextIndex.put(server, RaftCore.this.getLastIndex() + 1);
										matchIndex.put(server, 0);
									}
									stateChannel.clear();
								}
							} else {}
							break;
						default:
							break;
						}
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
		//消息加入队列之后的操作
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					String msg = null;
					while(!Thread.currentThread().isInterrupted()) {
						msg = commitChannel.take();
						if(!msg.equals("Commit")) {
							continue;
						}
						logger.info("Log entry commited, apply them");
						int baseIndex = log.get(0).getLogIndex();
						//如果commitIndex > lastApplied，那么就 lastApplied 加一，并把log[lastApplied]应用到状态机中(5.3 节)
						for(int i = lastApplied + 1; i <= commitIndex; i++) {
							LogEntryObj logety = log.get(i - baseIndex);
							ApplyMsg applyMsg = new ApplyMsg(i, logety.getOp(), logety.getData());
							applyChannel.add(applyMsg);
							lastApplied = i;
						}
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
	
	public void shutdown() throws InterruptedException {
		this.server.stop();
	}
	
	/**
	 * 从客户端收到命令的处理流程
	 * 需要客户端RPC提交日志
	 * 加个RPC可解
	 */
	public synchronized boolean append(String op, String data) {
		int index = -1;
		int term = currentTerm;
		boolean isLeader = (status == RaftStatus.LEADER);
		if(isLeader) {
			//Index递增
			index = getLastIndex() + 1;
			log.add(new LogEntryObj(index, term, op, data));
		}
		return isLeader;
	}

	/**
	 * 使用RequestVote RPC(请求投票RPC)发送给服务器
	 * @param server peer中的key
	 * @param replyBuilder 构造reply字段
	 */
	public boolean sendRequestVote(int server, RequestVoteArgs request) {
		synchronized (RaftCore.this) {
			RaftCall rpc = peers.get(server);
			RequestVoteReply reply = rpc.requestVoteCall(request);
			if(reply != null) {
				int term = currentTerm;
				//CANDIDATE态不做处理
				if (status != RaftStatus.CANDIDATE) {
					return true;
				}
				//当前Term发生了变化
				if(request.getTerm() != term) {
					return true;
				}
				//收到了任期号更大的回复，说明新Server已经被选出
				if(reply.getTerm() > term) {
					currentTerm = reply.getTerm();
					status = RaftStatus.FOLLOWER;
					voteFor = -1;
				}
				//收到了来自Follower的选票
				if(reply.getVoteGranted()) {
					voteCount++;
					//票数过半
					if(status == RaftStatus.CANDIDATE && voteCount > peers.size() / 2) {
						status = RaftStatus.FOLLOWER;
						stateChannel.add(new String("SetLeader"));
					}
				}
			}
		}
		return true;
	}
	
	/**
	 * 发送一条附加日志RPC
	 * @param server peer中的key
	 * @return
	 */
	public boolean sendAppendEntries(int server, AppendEntriesArgs request) {
		synchronized (RaftCore.this) {
			RaftCall rpc = peers.get(server);
			AppendEntriesReply reply = rpc.appendEntriesCall(request);
			if(reply != null) {
				//当前节点被废黜，或任期号变更了,不对回复值做处理
				if(status != RaftStatus.LEADER) {
					return true;
				}
				if(request.getTerm() != currentTerm) {
					return true;
				}
				//Follower发送了更新的任期号，则将自己降为Follower
				if(reply.getTerm() > currentTerm) {
					currentTerm = reply.getTerm();
					status = RaftStatus.FOLLOWER;
					voteFor = -1;
					return true;
				}
				//附加日志RPC成功
				if(reply.getSuccess()) {
					if(request.getEntriesCount() > 0) {
						//nextIndex的协商(心跳没发包与-1越界)
						int next = request.getEntries(request.getEntriesCount() - 1).getLogIndex() + 1;
						nextIndex.put(server, next);
						matchIndex.put(server, nextIndex.get(server) - 1);
					} else {
						nextIndex.put(server, reply.getNextIndex());
					}
				}
			}
		}
		return true;
	}
	
	/**
	 * 广播投票请求
	 * @author HHX
	 */
	public void broadcastRequestVote() {
		RequestVoteArgs.Builder requsetBuilder = RequestVoteArgs.newBuilder();
		RequestVoteArgs request = null;
		//组织消息并发送
		synchronized (RaftCore.this) {
			requsetBuilder.setTerm(currentTerm);
			requsetBuilder.setCandidateId(id);
			requsetBuilder.setLastLogTerm(RaftCore.this.getLastTerm());
			requsetBuilder.setLastLogIndex(RaftCore.this.getLastIndex());
		}
		request = requsetBuilder.build();
		for(int i : peers.keySet()) {
			if(i != id && status == RaftStatus.CANDIDATE) {
				rpcCallPool.execute(new RequestVoteExecutor(i, request));
			}
		}
	}
	
	/**
	 * 日志复制广播，由Leader调用
	 * @author HHX
	 */
	public void broadcastAppendEntries() {
		synchronized (RaftCore.this) {
			int N = commitIndex;
			int last = RaftCore.this.getLastIndex();
			//第一条日志的索引号
			int baseIndex = log.get(0).getLogIndex();
			// 如果存在一个满足N > commitIndex的 N，并且大多数的 matchIndex[i] ≥ N成立，并且log[N].term == currentTerm成立，
			// 那么令 commitIndex 等于这个 N(论文 5.3 和 5.4 节)
			// 前者是一半节点匹配后才能提交，后者是防止非本此term的日志覆盖(5.4.2)
			for(int i = commitIndex + 1; i <= last; i++) {
				int num = 1;
				for(int j : peers.keySet()) {
					if(j != id && matchIndex.get(j) >= i &&
							log.get(i - baseIndex).getLogTerm() == currentTerm) {
						num++;
					}
					//如果过半节点都已经复制了日志，那么更新N
					if(2 * num > peers.size()) {
						N = i;
					}
				}
			}
			//有新的可以被提交的日志
			if(N != commitIndex) {
				commitIndex = N;
				commitChannel.add(new String("Commit"));
			}
			//Leader组织日志项，借助RPC发送日志
			for(int i : peers.keySet()) {
				if(i != id && status == RaftStatus.LEADER) {
					if(true /*|| nextIndex.get(i) > baseIndex*/) {
						AppendEntriesArgs.Builder requestBuilder = AppendEntriesArgs.newBuilder();
						AppendEntriesArgs request = null;
						//Leader的任期号
						requestBuilder.setTerm(currentTerm);
						//Leader ID编号
						requestBuilder.setLeaderId(id);
						//nextIndex[i]为需要发送的下一条日志
						//PrevLogIndex即为Follower的最后一条日志
						requestBuilder.setPrevLogIndex(nextIndex.get(i) - 1);
						//最后一条日志的任期号
						requestBuilder.setPrevLogTerm(log.get(nextIndex.get(i) - baseIndex - 1).getLogTerm());
						//组织日志
						List<LogEntry> entries = new LinkedList<>();
						for(int k = nextIndex.get(i) - baseIndex; k < log.size(); k++) {
							LogEntryObj entryObj = log.get(k);
							entries.add(LogEntry.newBuilder().setLogIndex(entryObj.getLogIndex()).
															  setLogTerm(entryObj.getLogTerm()).
															  setOp(entryObj.getOp()).
															  setData(entryObj.getData()).
															  build());								 
						}
						requestBuilder.addAllEntries(entries);
						requestBuilder.setLeaderCommit(commitIndex);
						request = requestBuilder.build();
						rpcCallPool.execute(new AppendEntriesExecutor(i, request));
					}
				}
			}
		}	
	}
	
	class RequestVoteExecutor implements Runnable {
		private int server;
		private RequestVoteArgs request;
		
		public RequestVoteExecutor(int server, RequestVoteArgs request) {
			super();
			this.server = server;
			this.request = request;
		}
		@Override
		public void run() {
			sendRequestVote(server, request);
		}
	}
	
	class AppendEntriesExecutor implements Runnable {
		private int server;
		private AppendEntriesArgs request;
		
		public AppendEntriesExecutor(int server, AppendEntriesArgs request) {
			super();
			this.server = server;
			this.request = request;
		}

		@Override
		public void run() {
			sendAppendEntries(server, request);
		}
	}
	
	public class RaftCallHandler extends RaftGrpc.RaftImplBase {
		//请求投票
		@Override
		public void raftRequestVoteRpc(RequestVoteArgs request, StreamObserver<RequestVoteReply> responseObserver) {
			synchronized (RaftCore.this) {
				RequestVoteReply.Builder replyBuilder = RequestVoteReply.newBuilder();
				RequestVoteReply reply = null;
				replyBuilder.setVoteGranted(false);
				//如果term < currentTerm返回 false(5.2 节)
				if(request.getTerm() < currentTerm) {
					replyBuilder.setTerm(currentTerm);
					reply = replyBuilder.build();
					responseObserver.onNext(reply);
					responseObserver.onCompleted();
					return;
				}
				//收到更加新的Term，立刻转换为Follower
				if(request.getTerm() > currentTerm) {
					currentTerm = request.getTerm();
					status = RaftStatus.FOLLOWER;
					voteFor = -1;
				}
				//返回当前Term
				replyBuilder.setTerm(currentTerm);
				
				//当前节点的最新Term和最新日志索引
				int term = RaftCore.this.getLastTerm();
				int index = RaftCore.this.getLastIndex();
				boolean uptoDate = false;
				// 如果VoteFor为null或者为发起投票者的ID，则投票(没有投票则可以投票)
				// 为Term更大，或者Term相同但索引更大的日志投票
				if(request.getLastLogIndex() > term) {
					uptoDate = true;
				}
				if(request.getLastLogTerm() == term && request.getLastLogIndex() >= index) {
					uptoDate = true;
				}
				//一人一票，先到先得
				if(uptoDate && (voteFor == -1 || voteFor == request.getCandidateId())) {
					replyBuilder.setVoteGranted(true);
					voteFor = request.getCandidateId();
				}
				reply = replyBuilder.build();
				responseObserver.onNext(reply);
				responseObserver.onCompleted();
			}
		}

		//附加日志RPC
		@Override
		public void raftAppendEntriesRpc(AppendEntriesArgs request, StreamObserver<AppendEntriesReply> responseObserver) {
			synchronized (RaftCore.this) {
				AppendEntriesReply.Builder replyBuilder = AppendEntriesReply.newBuilder();
				AppendEntriesReply reply = null;
				replyBuilder.setSuccess(false);
				//如果 term < currentTerm 就返回 false (5.1 节)
				if(request.getTerm() < currentTerm) {
					replyBuilder.setTerm(currentTerm);
					replyBuilder.setNextIndex(RaftCore.this.getLastIndex() + 1);
					reply = replyBuilder.build();
					responseObserver.onNext(reply);
					responseObserver.onCompleted();
					return;
				}
				stateChannel.add(new String("HeartBeat"));
				//任期号T大于当前任期号要立刻转换为Follower(出现了新Leader，也就是发送RPC的Leader)
				if(request.getTerm() > currentTerm) {
					currentTerm = request.getTerm();
					status = RaftStatus.FOLLOWER;
					voteFor = -1;
				}
				//参数里的Term，最新Term编号
				replyBuilder.setTerm(request.getTerm());
				
				//日志太新，不能出现空洞
				if(request.getPrevLogIndex() > RaftCore.this.getLastIndex()) {
					replyBuilder.setNextIndex(RaftCore.this.getLastIndex() + 1);
					reply = replyBuilder.build();
					responseObserver.onNext(reply);
					responseObserver.onCompleted();
					return;
				}
				
				//第一条日志的索引
				int baseIndex = log.get(0).getLogIndex();
				if(request.getPrevLogIndex() > baseIndex) {
					int logTerm = log.get(request.getPrevLogIndex() - baseIndex).getLogTerm();
					//如果日志在 prevLogIndex 位置处的日志条目的任期号和 prevLogTerm 不匹配，则返回 false(5.3 节)
					if(request.getPrevLogTerm() != logTerm) {
						for(int i = request.getPrevLogIndex() - 1; i >= baseIndex; i--) {
							if(log.get(i - baseIndex).getLogTerm() != logTerm) {
								replyBuilder.setNextIndex(i + 1);
								break;
							}
						}
						reply = replyBuilder.build();
						responseObserver.onNext(reply);
						responseObserver.onCompleted();
						return;
					}
				}
				
				if(request.getPrevLogIndex() >= baseIndex) {
					//如果已经存在的日志条目和新的产生冲突(索引值相同但是任期号不同)，删除这一条和之后所有的 (5.3 节)
					//附加任何在已有的日志中不存在的条目
					for(int i = request.getPrevLogIndex() + 1 - baseIndex; i < log.size(); i++) {
						log.remove(i);
					}
					List<LogEntry> newLog = request.getEntriesList();
					for(int i = 0; i < newLog.size(); i++) {
						LogEntry rpcEntry = newLog.get(i);
						LogEntryObj entry = new LogEntryObj(rpcEntry.getLogIndex(), rpcEntry.getLogTerm(),
								rpcEntry.getOp(), rpcEntry.getData());
						log.add(entry);
					}
					replyBuilder.setSuccess(true);
					replyBuilder.setNextIndex(RaftCore.this.getLastIndex() + 1);
				}
				
				//如果 leaderCommit > commitIndex，令 commitIndex 等于 leaderCommit 和 新日志条目索引值中较小的一个
				if(request.getLeaderCommit() > commitIndex) {
					int last = RaftCore.this.getLastIndex();
					if(request.getLeaderCommit() > last) {
						commitIndex = last;
					} else {
						commitIndex = request.getLeaderCommit();
					}
					//日志应用条件
					commitChannel.add(new String("Commit"));
				}
				reply = replyBuilder.build();
				responseObserver.onNext(reply);
				responseObserver.onCompleted();
				return;
			}
		}
		
		//其他节点发来请求信息，若为Leader则添加至Log，否则拒绝
		@Override
		public void raftClientSubmitRpc(ClientSubmitRequest request, StreamObserver<ClientSubmitReply> responseObserver) {
			synchronized (RaftCore.this) {
				int index = -1;
				int term = currentTerm;
				boolean isLeader = (status == RaftStatus.LEADER);
				if(isLeader) {
					//Index递增
					index = getLastIndex() + 1;
					log.add(new LogEntryObj(index, term, request.getOp(), request.getData()));
				}
				ClientSubmitReply.Builder replyBuilder = ClientSubmitReply.newBuilder();
				ClientSubmitReply reply = null;
				replyBuilder.setCanSubmit(isLeader);
				reply = replyBuilder.build();
				responseObserver.onNext(reply);
				responseObserver.onCompleted();
				return;
			}
		}
	}
}
