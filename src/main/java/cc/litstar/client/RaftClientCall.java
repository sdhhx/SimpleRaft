package cc.litstar.client;

import java.util.concurrent.TimeUnit;

import cc.litstar.rpc.ClientSubmitReply;
import cc.litstar.rpc.ClientSubmitRequest;
import cc.litstar.rpc.RaftGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

class RaftClientCall {

	private final ManagedChannel channel;
	private final RaftGrpc.RaftBlockingStub blockingStub;
	
	public RaftClientCall(String host, int port) {
		channel = ManagedChannelBuilder.forAddress(host, port)
									   .usePlaintext(true)
									   .build();
		blockingStub = RaftGrpc.newBlockingStub(channel);
	}
	
	public void init() {
		Runtime.getRuntime().addShutdownHook(new Thread(){
			@Override
			public void run() {
				try {
					RaftClientCall.this.shutdown();
				} catch (InterruptedException e) { }
			}
		});
	}
	
	public void shutdown() throws InterruptedException {
		channel.shutdown().awaitTermination(3, TimeUnit.SECONDS);
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
