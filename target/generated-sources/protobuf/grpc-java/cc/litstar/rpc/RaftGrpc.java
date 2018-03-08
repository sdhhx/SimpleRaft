package cc.litstar.rpc;

import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;

/**
 * <pre>
 * Raft service definition.
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.0.0)",
    comments = "Source: raft.proto")
public class RaftGrpc {

  private RaftGrpc() {}

  public static final String SERVICE_NAME = "Raft";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<cc.litstar.rpc.RequestVoteArgs,
      cc.litstar.rpc.RequestVoteReply> METHOD_RAFT_REQUEST_VOTE_RPC =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "Raft", "RaftRequestVoteRpc"),
          io.grpc.protobuf.ProtoUtils.marshaller(cc.litstar.rpc.RequestVoteArgs.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(cc.litstar.rpc.RequestVoteReply.getDefaultInstance()));
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<cc.litstar.rpc.AppendEntriesArgs,
      cc.litstar.rpc.AppendEntriesReply> METHOD_RAFT_APPEND_ENTRIES_RPC =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "Raft", "RaftAppendEntriesRpc"),
          io.grpc.protobuf.ProtoUtils.marshaller(cc.litstar.rpc.AppendEntriesArgs.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(cc.litstar.rpc.AppendEntriesReply.getDefaultInstance()));
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<cc.litstar.rpc.InstallSnapshotArgs,
      cc.litstar.rpc.InstallSnapshotReply> METHOD_RAFT_INSTALL_SNAPSHOT_RPC =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "Raft", "RaftInstallSnapshotRpc"),
          io.grpc.protobuf.ProtoUtils.marshaller(cc.litstar.rpc.InstallSnapshotArgs.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(cc.litstar.rpc.InstallSnapshotReply.getDefaultInstance()));
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<cc.litstar.rpc.ClientSubmitRequest,
      cc.litstar.rpc.ClientSubmitReply> METHOD_RAFT_CLIENT_SUBMIT_RPC =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "Raft", "RaftClientSubmitRpc"),
          io.grpc.protobuf.ProtoUtils.marshaller(cc.litstar.rpc.ClientSubmitRequest.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(cc.litstar.rpc.ClientSubmitReply.getDefaultInstance()));

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static RaftStub newStub(io.grpc.Channel channel) {
    return new RaftStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static RaftBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new RaftBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary and streaming output calls on the service
   */
  public static RaftFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new RaftFutureStub(channel);
  }

  /**
   * <pre>
   * Raft service definition.
   * </pre>
   */
  public static abstract class RaftImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     *rpc SayHello (HelloRequest) returns (HelloReply) {}
     * </pre>
     */
    public void raftRequestVoteRpc(cc.litstar.rpc.RequestVoteArgs request,
        io.grpc.stub.StreamObserver<cc.litstar.rpc.RequestVoteReply> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_RAFT_REQUEST_VOTE_RPC, responseObserver);
    }

    /**
     */
    public void raftAppendEntriesRpc(cc.litstar.rpc.AppendEntriesArgs request,
        io.grpc.stub.StreamObserver<cc.litstar.rpc.AppendEntriesReply> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_RAFT_APPEND_ENTRIES_RPC, responseObserver);
    }

    /**
     */
    public void raftInstallSnapshotRpc(cc.litstar.rpc.InstallSnapshotArgs request,
        io.grpc.stub.StreamObserver<cc.litstar.rpc.InstallSnapshotReply> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_RAFT_INSTALL_SNAPSHOT_RPC, responseObserver);
    }

    /**
     */
    public void raftClientSubmitRpc(cc.litstar.rpc.ClientSubmitRequest request,
        io.grpc.stub.StreamObserver<cc.litstar.rpc.ClientSubmitReply> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_RAFT_CLIENT_SUBMIT_RPC, responseObserver);
    }

    @java.lang.Override public io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_RAFT_REQUEST_VOTE_RPC,
            asyncUnaryCall(
              new MethodHandlers<
                cc.litstar.rpc.RequestVoteArgs,
                cc.litstar.rpc.RequestVoteReply>(
                  this, METHODID_RAFT_REQUEST_VOTE_RPC)))
          .addMethod(
            METHOD_RAFT_APPEND_ENTRIES_RPC,
            asyncUnaryCall(
              new MethodHandlers<
                cc.litstar.rpc.AppendEntriesArgs,
                cc.litstar.rpc.AppendEntriesReply>(
                  this, METHODID_RAFT_APPEND_ENTRIES_RPC)))
          .addMethod(
            METHOD_RAFT_INSTALL_SNAPSHOT_RPC,
            asyncUnaryCall(
              new MethodHandlers<
                cc.litstar.rpc.InstallSnapshotArgs,
                cc.litstar.rpc.InstallSnapshotReply>(
                  this, METHODID_RAFT_INSTALL_SNAPSHOT_RPC)))
          .addMethod(
            METHOD_RAFT_CLIENT_SUBMIT_RPC,
            asyncUnaryCall(
              new MethodHandlers<
                cc.litstar.rpc.ClientSubmitRequest,
                cc.litstar.rpc.ClientSubmitReply>(
                  this, METHODID_RAFT_CLIENT_SUBMIT_RPC)))
          .build();
    }
  }

  /**
   * <pre>
   * Raft service definition.
   * </pre>
   */
  public static final class RaftStub extends io.grpc.stub.AbstractStub<RaftStub> {
    private RaftStub(io.grpc.Channel channel) {
      super(channel);
    }

    private RaftStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected RaftStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new RaftStub(channel, callOptions);
    }

    /**
     * <pre>
     *rpc SayHello (HelloRequest) returns (HelloReply) {}
     * </pre>
     */
    public void raftRequestVoteRpc(cc.litstar.rpc.RequestVoteArgs request,
        io.grpc.stub.StreamObserver<cc.litstar.rpc.RequestVoteReply> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_RAFT_REQUEST_VOTE_RPC, getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void raftAppendEntriesRpc(cc.litstar.rpc.AppendEntriesArgs request,
        io.grpc.stub.StreamObserver<cc.litstar.rpc.AppendEntriesReply> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_RAFT_APPEND_ENTRIES_RPC, getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void raftInstallSnapshotRpc(cc.litstar.rpc.InstallSnapshotArgs request,
        io.grpc.stub.StreamObserver<cc.litstar.rpc.InstallSnapshotReply> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_RAFT_INSTALL_SNAPSHOT_RPC, getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void raftClientSubmitRpc(cc.litstar.rpc.ClientSubmitRequest request,
        io.grpc.stub.StreamObserver<cc.litstar.rpc.ClientSubmitReply> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_RAFT_CLIENT_SUBMIT_RPC, getCallOptions()), request, responseObserver);
    }
  }

  /**
   * <pre>
   * Raft service definition.
   * </pre>
   */
  public static final class RaftBlockingStub extends io.grpc.stub.AbstractStub<RaftBlockingStub> {
    private RaftBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private RaftBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected RaftBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new RaftBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     *rpc SayHello (HelloRequest) returns (HelloReply) {}
     * </pre>
     */
    public cc.litstar.rpc.RequestVoteReply raftRequestVoteRpc(cc.litstar.rpc.RequestVoteArgs request) {
      return blockingUnaryCall(
          getChannel(), METHOD_RAFT_REQUEST_VOTE_RPC, getCallOptions(), request);
    }

    /**
     */
    public cc.litstar.rpc.AppendEntriesReply raftAppendEntriesRpc(cc.litstar.rpc.AppendEntriesArgs request) {
      return blockingUnaryCall(
          getChannel(), METHOD_RAFT_APPEND_ENTRIES_RPC, getCallOptions(), request);
    }

    /**
     */
    public cc.litstar.rpc.InstallSnapshotReply raftInstallSnapshotRpc(cc.litstar.rpc.InstallSnapshotArgs request) {
      return blockingUnaryCall(
          getChannel(), METHOD_RAFT_INSTALL_SNAPSHOT_RPC, getCallOptions(), request);
    }

    /**
     */
    public cc.litstar.rpc.ClientSubmitReply raftClientSubmitRpc(cc.litstar.rpc.ClientSubmitRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_RAFT_CLIENT_SUBMIT_RPC, getCallOptions(), request);
    }
  }

  /**
   * <pre>
   * Raft service definition.
   * </pre>
   */
  public static final class RaftFutureStub extends io.grpc.stub.AbstractStub<RaftFutureStub> {
    private RaftFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private RaftFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected RaftFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new RaftFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     *rpc SayHello (HelloRequest) returns (HelloReply) {}
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<cc.litstar.rpc.RequestVoteReply> raftRequestVoteRpc(
        cc.litstar.rpc.RequestVoteArgs request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_RAFT_REQUEST_VOTE_RPC, getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<cc.litstar.rpc.AppendEntriesReply> raftAppendEntriesRpc(
        cc.litstar.rpc.AppendEntriesArgs request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_RAFT_APPEND_ENTRIES_RPC, getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<cc.litstar.rpc.InstallSnapshotReply> raftInstallSnapshotRpc(
        cc.litstar.rpc.InstallSnapshotArgs request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_RAFT_INSTALL_SNAPSHOT_RPC, getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<cc.litstar.rpc.ClientSubmitReply> raftClientSubmitRpc(
        cc.litstar.rpc.ClientSubmitRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_RAFT_CLIENT_SUBMIT_RPC, getCallOptions()), request);
    }
  }

  private static final int METHODID_RAFT_REQUEST_VOTE_RPC = 0;
  private static final int METHODID_RAFT_APPEND_ENTRIES_RPC = 1;
  private static final int METHODID_RAFT_INSTALL_SNAPSHOT_RPC = 2;
  private static final int METHODID_RAFT_CLIENT_SUBMIT_RPC = 3;

  private static class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final RaftImplBase serviceImpl;
    private final int methodId;

    public MethodHandlers(RaftImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_RAFT_REQUEST_VOTE_RPC:
          serviceImpl.raftRequestVoteRpc((cc.litstar.rpc.RequestVoteArgs) request,
              (io.grpc.stub.StreamObserver<cc.litstar.rpc.RequestVoteReply>) responseObserver);
          break;
        case METHODID_RAFT_APPEND_ENTRIES_RPC:
          serviceImpl.raftAppendEntriesRpc((cc.litstar.rpc.AppendEntriesArgs) request,
              (io.grpc.stub.StreamObserver<cc.litstar.rpc.AppendEntriesReply>) responseObserver);
          break;
        case METHODID_RAFT_INSTALL_SNAPSHOT_RPC:
          serviceImpl.raftInstallSnapshotRpc((cc.litstar.rpc.InstallSnapshotArgs) request,
              (io.grpc.stub.StreamObserver<cc.litstar.rpc.InstallSnapshotReply>) responseObserver);
          break;
        case METHODID_RAFT_CLIENT_SUBMIT_RPC:
          serviceImpl.raftClientSubmitRpc((cc.litstar.rpc.ClientSubmitRequest) request,
              (io.grpc.stub.StreamObserver<cc.litstar.rpc.ClientSubmitReply>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    return new io.grpc.ServiceDescriptor(SERVICE_NAME,
        METHOD_RAFT_REQUEST_VOTE_RPC,
        METHOD_RAFT_APPEND_ENTRIES_RPC,
        METHOD_RAFT_INSTALL_SNAPSHOT_RPC,
        METHOD_RAFT_CLIENT_SUBMIT_RPC);
  }

}
