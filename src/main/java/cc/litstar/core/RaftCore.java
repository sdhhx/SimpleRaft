package cc.litstar.core;

import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

import cc.litstar.conf.RaftConfig;
import cc.litstar.message.ApplyMsg;
import cc.litstar.message.LogEntryPojo;
import cc.litstar.rpc.AppendEntriesArgs;
import cc.litstar.rpc.AppendEntriesArgs.LogEntry;
import cc.litstar.rpc.AppendEntriesReply;
import cc.litstar.rpc.RaftGrpc;
import cc.litstar.rpc.RequestVoteArgs;
import cc.litstar.rpc.RequestVoteReply;
import io.grpc.stub.StreamObserver;

public class RaftCore {
	//RPC调用
	private RaftCallHandler handler;
	//RPC句柄
	private Map<Integer, RaftCall> peers;
	//当前编号
	private int number;
	//当前状态
	private RaftStatus status;
	//投票数
	private int voteCount;
	//服务器最后一次知道的任期号
	private int currentTerm;
	//在当时获得选票的候选人ID
	private int voteFor;
	//日志条目集，每一条日志包含一个用户状态机执行的指令，和收到的任期号
	private List<LogEntryPojo> log;
	//最大的已知被提交的日志条目集
	private int commitIndex;
	//最后被应用到状态机的日志条目索引值
	private int lastApplied;
	
	//复制状态机Channel
	private LinkedBlockingQueue<String> smChannel;
	//提交与应用解耦
	private LinkedBlockingQueue<ApplyMsg> applyChannel;
	
	//Leader相关变量
	//对于每一个服务器，需要发送给他的下一条日志的索引值
	private Map<Integer, Integer> nextIndex;
	//对于每一个服务器，已经复制给他的日志的最高索引值
	private Map<Integer, Integer> matchIndex;
	
	//Config
	private RaftConfig config;
	
	//先不急
	public RaftCore() {
		
	}
	
	//返回当前任期
	public int getCurrentTerm() {
		return currentTerm;
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
	
	//初始化操作
	public void init() {
		;
	}
	
	/**
	 * 启动Raft
	 */
	public void start() {
		
	}
	
	/**
	 * 日志提交?
	 */
	public void append() {
		
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
				//候选态不做处理
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
					//persist()
				}
				//收到了来自Follower的选票
				if(reply.getVoteGranted()) {
					voteCount++;
					//票数过半
					if(status == RaftStatus.CANDIDATE && voteCount > peers.size() / 2) {
						status = RaftStatus.FOLLOWER;
						try {
							smChannel.put("Leader");
						} catch (InterruptedException e) {
							//unused
							e.printStackTrace();
						}
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
					currentTerm = reply.getTerm();//?
					status = RaftStatus.FOLLOWER;
					voteFor = -1;
					return true;
				}
				//附加日志RPC成功
				if(reply.getSuccess()) {
					if(log.size() > 0) {
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
		;
	}
	
	/**
	 * 日志复制
	 * @author HHX
	 */
	public void broadcastAppendEntries() {
		
	}
	
	public class RaftCallHandler extends RaftGrpc.RaftImplBase {
		//请求投票
		@Override
		public void raftRequestVoteRpc(RequestVoteArgs request, StreamObserver<RequestVoteReply> responseObserver) {
			synchronized (RaftCore.this) {
				RequestVoteReply.Builder replyBuilder = RequestVoteReply.newBuilder();
				RequestVoteReply reply = null;
				replyBuilder.setVoteGranted(false);
				//候选者Term比当前Term旧，不投票
				if(request.getTerm() < currentTerm) {
					replyBuilder.setTerm(currentTerm);
					reply = replyBuilder.build();
					responseObserver.onNext(reply);
					responseObserver.onCompleted();
					return;
				}
				//收到更新的Term，立刻转换为Follower
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
				// 过期日志的两种情况：
				//	   1.如果日志的最后一条是不同的日志条目，则日期更新的日志将更新
				//     2.如果任期号相同，则最优一条日志索引大的日志更新
				if(request.getLastLogIndex() > term) {
					uptoDate = true;
				}
				if(request.getLastLogTerm() == term && request.getLastLogIndex() >= index) {
					uptoDate = true;
				}
				//一人一票，先到先得
				if(uptoDate && (voteFor == -1 || voteFor == request.getCandidateId())) {
					try {
						smChannel.put("GrantVote");
						status = RaftStatus.FOLLOWER;
						replyBuilder.setVoteGranted(true);
						voteFor = request.getCandidateId();
					} catch (InterruptedException e) {
						//unused
						e.printStackTrace();
					}
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
				//由任期号更小的Leader发来的日志，回复当前任期号并标记失败
				if(request.getTerm() < currentTerm) {
					replyBuilder.setTerm(currentTerm);
					replyBuilder.setNextIndex(RaftCore.this.getLastIndex() + 1);
					reply = replyBuilder.build();
					responseObserver.onNext(reply);
					responseObserver.onCompleted();
					return;
				}
				try {
					smChannel.put("HeartBeat");
				} catch (InterruptedException e) {
					//unused
					e.printStackTrace();
				}
				//任期号T大于当前任期号要立刻转换为Follower(出现了新Leader，也就是发送RPC的Leader)
				if(request.getTerm() > currentTerm) {
					currentTerm = request.getTerm();
					status = RaftStatus.FOLLOWER;
					voteFor = -1;
				}
				//参数里的Term，最新Term编号
				replyBuilder.setTerm(request.getTerm());
				//最后一条日志数量小于PrevLogIndex，则需要补充的日志不足
				if(request.getPrevLogIndex() > RaftCore.this.getLastIndex()) {
					replyBuilder.setNextIndex(RaftCore.this.getLastIndex() + 1);
					reply = replyBuilder.build();
					responseObserver.onNext(reply);
					responseObserver.onCompleted();
					return;
				}
				//第一条日志的索引
				int baseIndex = log.get(0).getLogIndex();
				//如果Leader和Follower的日志不一致，则需要协商
				
				//如果日志在 prevLogIndex 位置处的日志条目的任期号和 prevLogTerm不匹配，则返回 false 
				//如果已经存在的日志条目和新的产生冲突（索引值相同但是任期号不同），删除这一条和之后所有的
				if(request.getPrevLogIndex() > baseIndex) {
					int term = log.get(request.getPrevLogIndex() - baseIndex).getLogTerm();
					//任期号与实际任期号不一致(数据不一致)
					if(request.getPrevLogTerm() != term) {
						for(int i = request.getPrevLogIndex() - 1; i >= baseIndex; i--) {
							//已经存在的日志条目索引值相同但任期号不同
							//每次找到第一个任期号不一致项，或者找不到
							if(log.get(i - baseIndex).getLogTerm() != term) {
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
				
				if(request.getPrevLogIndex() > baseIndex) {
					//不做处理
				} else {
					//日志不冲突，追加日志
					for(int i = request.getPrevLogIndex() + 1 - baseIndex; i < log.size(); i++) {
						log.remove(i);
					}
					List<LogEntry> newLog = request.getEntriesList();
					for(int i = 0; i < newLog.size(); i++) {
						LogEntry rpcEntry = newLog.get(i);
						LogEntryPojo entry = new LogEntryPojo(rpcEntry.getLogIndex(), rpcEntry.getLogTerm(),
								rpcEntry.getOp(), rpcEntry.getData());
						log.add(entry);
					}
					replyBuilder.setSuccess(true);
					replyBuilder.setNextIndex(RaftCore.this.getLastIndex() + 1);
				}
				//如果leaderCommit > commitIndex，则设置commitIndex为LeaderCommit与最后一个新条目的最小值
				//所以Leader提交日志后，在Follower收到下一个附加日志RPC时(心跳/附加日志RPC)，Follower更新自己
				if(request.getLeaderCommit() > commitIndex) {
					int last = RaftCore.this.getLastIndex();
					if(request.getLeaderCommit() > last) {
						commitIndex = last;
					} else {
						commitIndex = request.getLeaderCommit();
					}
					try {
						smChannel.put("Commit");
					} catch (InterruptedException e) {
						//unused
						e.printStackTrace();
					}
				}
				reply = replyBuilder.build();
				responseObserver.onNext(reply);
				responseObserver.onCompleted();
				return;
			}
		}
	}
}
