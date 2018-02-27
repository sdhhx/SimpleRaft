package cc.litstar.core;

import java.io.IOException;

import cc.litstar.rpc.AppendEntriesArgs;
import cc.litstar.rpc.AppendEntriesReply;
import cc.litstar.rpc.RaftGrpc;
import cc.litstar.rpc.RequestVoteArgs;
import cc.litstar.rpc.RequestVoteReply;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

public class RaftServer {

	private int port = 50050;
	private Server server;
	
	public RaftServer(int port) {
		this.port = port;
	}
	
	private void start() throws IOException {
		server = ServerBuilder.forPort(port)
							  .addService(new RaftResponse())
							  .build()
							  .start();
		System.out.println("raft start...");
		Runtime.getRuntime().addShutdownHook(new Thread(){
			@Override
			public void run() {
				System.err.println("*** shutting down gRPC server since JVM is shutting down");
				RaftServer.this.stop();
				System.err.println("*** server shut down");
			}
		});
	}
	
	private void stop() {
		if(server != null) {
			server.shutdown();
		}
	}

	private void blockUtilShutdown() throws InterruptedException {
		if(server != null) {
			server.awaitTermination();
		}
	}
	
	//Rpc Response
	private class RaftResponse extends RaftGrpc.RaftImplBase {
		@Override
		public void raftRequestVoteRpc(RequestVoteArgs request, StreamObserver<RequestVoteReply> responseObserver) {
			System.out.println(request.getTerm());
			RequestVoteReply reply = RequestVoteReply.newBuilder().setTerm(100).build();
			responseObserver.onNext(reply);
			responseObserver.onCompleted();
		}

		@Override
		public void raftAppendEntriesRpc(AppendEntriesArgs request, StreamObserver<AppendEntriesReply> responseObserver) {
			System.out.println(request.getTerm());
			AppendEntriesReply reply = AppendEntriesReply.newBuilder().setTerm(100).build();
			responseObserver.onNext(reply);
			responseObserver.onCompleted();
		}
	}
}
