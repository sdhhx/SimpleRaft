package cc.litstar.core;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import cc.litstar.message.AppendEntriesArgsPojo;
import cc.litstar.message.AppendEntriesReplyPojo;
import cc.litstar.message.LogEntryPojo;
import cc.litstar.message.RequestVoteArgsPojo;
import cc.litstar.message.RequestVoteReplyPojo;
import cc.litstar.rpc.AppendEntriesArgs;
import cc.litstar.rpc.RaftGrpc;
import cc.litstar.rpc.RequestVoteArgs;
import cc.litstar.rpc.RequestVoteReply;
import cc.litstar.rpc.AppendEntriesArgs.LogEntry;
import cc.litstar.rpc.AppendEntriesReply;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class RaftCall {

	private final ManagedChannel channel;
	private final RaftGrpc.RaftBlockingStub blockingStub;
	
	public RaftCall(String host, int port) {
		channel = ManagedChannelBuilder.forAddress(host, port)
									   .usePlaintext(true)
									   .build();
		blockingStub = RaftGrpc.newBlockingStub(channel);
	}
	
	public void shutdown() throws InterruptedException {
		channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
	}
	
	public AppendEntriesReply appendEntriesCall(AppendEntriesArgs request) {
		try {
			AppendEntriesReply reply = blockingStub.raftAppendEntriesRpc(request);
			return reply;
		} catch (Exception e) {
			return null;
		}
	}
	
	public RequestVoteReply requestVoteCall(RequestVoteArgs request) {
		try {
			RequestVoteReply reply = blockingStub.raftRequestVoteRpc(request);
			return reply;
		} catch (Exception e) {
			return null;
		}
	}
	
	public AppendEntriesReplyPojo appendEntriesCall(AppendEntriesArgsPojo args) {
		List<LogEntry> entries = new LinkedList<>();
		for(LogEntryPojo entry : args.getEntries()) {
			entries.add(LogEntry.newBuilder().setLogIndex(entry.getLogIndex())
											 .setLogTerm(entry.getLogTerm())
											 .setOp(entry.getOp())
											 .setData(entry.getData())
											 .build());
		}
		AppendEntriesArgs request = AppendEntriesArgs.newBuilder().setTerm(args.getTerm())
																  .setLeaderId(args.getLeaderId())
																  .setPrevLogTerm(args.getPrevLogTerm())
																  .setPrevLogIndex(args.getPrevLogIndex())
																  .addAllEntries(entries)
																  .setLeaderCommit(args.getLeaderCommit())
																  .build();
		try {
			AppendEntriesReply reply = blockingStub.raftAppendEntriesRpc(request);
			return new AppendEntriesReplyPojo(reply.getTerm(), reply.getSuccess(), reply.getNextIndex());
		} catch (Exception e) {
			return null;
		}
		
	}
	
	public RequestVoteReplyPojo requestVoteCall(RequestVoteArgsPojo args) {
		RequestVoteArgs request = RequestVoteArgs.newBuilder().setTerm(args.getTerm())
															  .setCandidateId(args.getCandidateId())
															  .setLastLogTerm(args.getLastLogTerm())
															  .setLastLogIndex(args.getLastLogIndex())
															  .build();
		try {
			RequestVoteReply reply = blockingStub.raftRequestVoteRpc(request);
			return new RequestVoteReplyPojo(reply.getTerm(), reply.getVoteGranted());
		} catch (Exception e) {
			return null;
		}
	}
	
}
