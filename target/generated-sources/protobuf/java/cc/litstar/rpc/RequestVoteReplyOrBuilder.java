// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: raft.proto

package cc.litstar.rpc;

public interface RequestVoteReplyOrBuilder extends
    // @@protoc_insertion_point(interface_extends:RequestVoteReply)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>optional int32 Term = 1;</code>
   */
  int getTerm();

  /**
   * <code>optional bool VoteGranted = 2;</code>
   */
  boolean getVoteGranted();
}
