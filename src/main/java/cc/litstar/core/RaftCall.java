package cc.litstar.core;

import java.util.concurrent.TimeUnit;

import cc.litstar.rpc.AppendEntriesArgs;
import cc.litstar.rpc.RaftGrpc;
import cc.litstar.rpc.RequestVoteArgs;
import cc.litstar.rpc.RequestVoteReply;
import cc.litstar.rpc.AppendEntriesReply;
import cc.litstar.rpc.ClientSubmitReply;
import cc.litstar.rpc.ClientSubmitRequest;
import cc.litstar.rpc.InstallSnapshotArgs;
import cc.litstar.rpc.InstallSnapshotReply;
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
	
	public synchronized AppendEntriesReply appendEntriesCall(AppendEntriesArgs request) {
		try {
			//重复创建小对象，否则会有奇怪bug
			AppendEntriesReply reply = blockingStub.withDeadlineAfter(1, TimeUnit.SECONDS)
												   .raftAppendEntriesRpc(request);
			return reply;
		} catch (Exception e) {
			//Status status = Status.fromThrowable(e);
	        //status.asException().printStackTrace();
			return null;
		}
	}
	
	public synchronized RequestVoteReply requestVoteCall(RequestVoteArgs request) {
		try {
			RequestVoteReply reply = blockingStub.withDeadlineAfter(1, TimeUnit.SECONDS)
												 .raftRequestVoteRpc(request);
			return reply;
		} catch (Exception e) {
			//Status status = Status.fromThrowable(e);
	        //status.asException().printStackTrace();
			return null;
		}
	}

	public synchronized InstallSnapshotReply installSnapshotCall(InstallSnapshotArgs request) {
		try {
			InstallSnapshotReply reply = blockingStub.withDeadlineAfter(1, TimeUnit.SECONDS)
													 .raftInstallSnapshotRpc(request);
			return reply;
		} catch (Exception e) {
			//Status status = Status.fromThrowable(e);
	        //status.asException().printStackTrace();
			return null;
		}
	}
	
	public synchronized ClientSubmitReply clientSubmitCall(ClientSubmitRequest request) {
		try {
			ClientSubmitReply reply = blockingStub.withDeadlineAfter(1, TimeUnit.SECONDS)
												  .raftClientSubmitRpc(request);
			return reply;
		} catch (Exception e) {
			//Status status = Status.fromThrowable(e);
	        //status.asException().printStackTrace();
			return null;
		}
	}
}
