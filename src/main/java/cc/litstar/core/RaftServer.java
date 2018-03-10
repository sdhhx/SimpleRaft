package cc.litstar.core;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cc.litstar.rpc.RaftGrpc.RaftImplBase;
import io.grpc.Server;
import io.grpc.ServerBuilder;

public class RaftServer {

	private int port = 50050;
	private Server server;
	private RaftImplBase handler;
	
	private final static Logger logger = LoggerFactory.getLogger(RaftServer.class);
	
	public RaftServer(int port, RaftImplBase handler) {
		this.port = port;
		this.handler = handler;
	}
	
	public void start() throws IOException {
		server = ServerBuilder.forPort(port)
							  .addService(handler)
							  .build()
							  .start();
		logger.info("raft start...");
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				logger.warn("*** shutting down gRPC server since JVM is shutting down");
				RaftServer.this.stop();
				logger.warn("*** server shut down");
			}
		});
	}
	
	public void stop() {
		if(server != null) {
			server.shutdown();
		}
	}

	public void blockUtilShutdown() throws InterruptedException {
		if(server != null) {
			server.awaitTermination();
		}
	}
}
