// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: raft.proto

package cc.litstar.rpc;

public interface InstallSnapshotArgsOrBuilder extends
    // @@protoc_insertion_point(interface_extends:InstallSnapshotArgs)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>optional int32 Term = 1;</code>
   */
  int getTerm();

  /**
   * <code>optional int32 LeaderId = 2;</code>
   */
  int getLeaderId();

  /**
   * <code>optional int32 LastIncludedIndex = 3;</code>
   */
  int getLastIncludedIndex();

  /**
   * <code>optional int32 LastIncludedTerm = 4;</code>
   */
  int getLastIncludedTerm();

  /**
   * <code>optional string data = 5;</code>
   */
  java.lang.String getData();
  /**
   * <code>optional string data = 5;</code>
   */
  com.google.protobuf.ByteString
      getDataBytes();
}