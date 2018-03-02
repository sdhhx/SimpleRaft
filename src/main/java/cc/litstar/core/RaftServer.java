package cc.litstar.core;

import java.io.IOException;

import cc.litstar.rpc.RaftGrpc.RaftImplBase;
import io.grpc.Server;
import io.grpc.ServerBuilder;

public class RaftServer {

	private int port = 50050;
	private Server server;
	private RaftImplBase handler;
	
	public RaftServer(int port, RaftImplBase handler) {
		this.port = port;
		this.handler = handler;
	}
	
	private void start() throws IOException {
		server = ServerBuilder.forPort(port)
							  .addService(handler)
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
}
