// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: raft.proto

package cc.litstar.rpc;

/**
 * <pre>
 * 请求投票RPC回复
 * </pre>
 *
 * Protobuf type {@code RequestVoteReply}
 */
public  final class RequestVoteReply extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:RequestVoteReply)
    RequestVoteReplyOrBuilder {
  // Use RequestVoteReply.newBuilder() to construct.
  private RequestVoteReply(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private RequestVoteReply() {
    term_ = 0;
    voteGranted_ = false;
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return com.google.protobuf.UnknownFieldSet.getDefaultInstance();
  }
  private RequestVoteReply(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    this();
    int mutable_bitField0_ = 0;
    try {
      boolean done = false;
      while (!done) {
        int tag = input.readTag();
        switch (tag) {
          case 0:
            done = true;
            break;
          default: {
            if (!input.skipField(tag)) {
              done = true;
            }
            break;
          }
          case 8: {

            term_ = input.readInt32();
            break;
          }
          case 16: {

            voteGranted_ = input.readBool();
            break;
          }
        }
      }
    } catch (com.google.protobuf.InvalidProtocolBufferException e) {
      throw e.setUnfinishedMessage(this);
    } catch (java.io.IOException e) {
      throw new com.google.protobuf.InvalidProtocolBufferException(
          e).setUnfinishedMessage(this);
    } finally {
      makeExtensionsImmutable();
    }
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return cc.litstar.rpc.RaftProto.internal_static_RequestVoteReply_descriptor;
  }

  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return cc.litstar.rpc.RaftProto.internal_static_RequestVoteReply_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            cc.litstar.rpc.RequestVoteReply.class, cc.litstar.rpc.RequestVoteReply.Builder.class);
  }

  public static final int TERM_FIELD_NUMBER = 1;
  private int term_;
  /**
   * <code>optional int32 Term = 1;</code>
   */
  public int getTerm() {
    return term_;
  }

  public static final int VOTEGRANTED_FIELD_NUMBER = 2;
  private boolean voteGranted_;
  /**
   * <code>optional bool VoteGranted = 2;</code>
   */
  public boolean getVoteGranted() {
    return voteGranted_;
  }

  private byte memoizedIsInitialized = -1;
  public final boolean isInitialized() {
    byte isInitialized = memoizedIsInitialized;
    if (isInitialized == 1) return true;
    if (isInitialized == 0) return false;

    memoizedIsInitialized = 1;
    return true;
  }

  public void writeTo(com.google.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
    if (term_ != 0) {
      output.writeInt32(1, term_);
    }
    if (voteGranted_ != false) {
      output.writeBool(2, voteGranted_);
    }
  }

  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (term_ != 0) {
      size += com.google.protobuf.CodedOutputStream
        .computeInt32Size(1, term_);
    }
    if (voteGranted_ != false) {
      size += com.google.protobuf.CodedOutputStream
        .computeBoolSize(2, voteGranted_);
    }
    memoizedSize = size;
    return size;
  }

  private static final long serialVersionUID = 0L;
  @java.lang.Override
  public boolean equals(final java.lang.Object obj) {
    if (obj == this) {
     return true;
    }
    if (!(obj instanceof cc.litstar.rpc.RequestVoteReply)) {
      return super.equals(obj);
    }
    cc.litstar.rpc.RequestVoteReply other = (cc.litstar.rpc.RequestVoteReply) obj;

    boolean result = true;
    result = result && (getTerm()
        == other.getTerm());
    result = result && (getVoteGranted()
        == other.getVoteGranted());
    return result;
  }

  @java.lang.Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptorForType().hashCode();
    hash = (37 * hash) + TERM_FIELD_NUMBER;
    hash = (53 * hash) + getTerm();
    hash = (37 * hash) + VOTEGRANTED_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashBoolean(
        getVoteGranted());
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static cc.litstar.rpc.RequestVoteReply parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static cc.litstar.rpc.RequestVoteReply parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static cc.litstar.rpc.RequestVoteReply parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static cc.litstar.rpc.RequestVoteReply parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static cc.litstar.rpc.RequestVoteReply parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static cc.litstar.rpc.RequestVoteReply parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static cc.litstar.rpc.RequestVoteReply parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static cc.litstar.rpc.RequestVoteReply parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static cc.litstar.rpc.RequestVoteReply parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static cc.litstar.rpc.RequestVoteReply parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  public Builder newBuilderForType() { return newBuilder(); }
  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(cc.litstar.rpc.RequestVoteReply prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }
  public Builder toBuilder() {
    return this == DEFAULT_INSTANCE
        ? new Builder() : new Builder().mergeFrom(this);
  }

  @java.lang.Override
  protected Builder newBuilderForType(
      com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
    Builder builder = new Builder(parent);
    return builder;
  }
  /**
   * <pre>
   * 请求投票RPC回复
   * </pre>
   *
   * Protobuf type {@code RequestVoteReply}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:RequestVoteReply)
      cc.litstar.rpc.RequestVoteReplyOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return cc.litstar.rpc.RaftProto.internal_static_RequestVoteReply_descriptor;
    }

    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return cc.litstar.rpc.RaftProto.internal_static_RequestVoteReply_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              cc.litstar.rpc.RequestVoteReply.class, cc.litstar.rpc.RequestVoteReply.Builder.class);
    }

    // Construct using cc.litstar.rpc.RequestVoteReply.newBuilder()
    private Builder() {
      maybeForceBuilderInitialization();
    }

    private Builder(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      super(parent);
      maybeForceBuilderInitialization();
    }
    private void maybeForceBuilderInitialization() {
      if (com.google.protobuf.GeneratedMessageV3
              .alwaysUseFieldBuilders) {
      }
    }
    public Builder clear() {
      super.clear();
      term_ = 0;

      voteGranted_ = false;

      return this;
    }

    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return cc.litstar.rpc.RaftProto.internal_static_RequestVoteReply_descriptor;
    }

    public cc.litstar.rpc.RequestVoteReply getDefaultInstanceForType() {
      return cc.litstar.rpc.RequestVoteReply.getDefaultInstance();
    }

    public cc.litstar.rpc.RequestVoteReply build() {
      cc.litstar.rpc.RequestVoteReply result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    public cc.litstar.rpc.RequestVoteReply buildPartial() {
      cc.litstar.rpc.RequestVoteReply result = new cc.litstar.rpc.RequestVoteReply(this);
      result.term_ = term_;
      result.voteGranted_ = voteGranted_;
      onBuilt();
      return result;
    }

    public Builder clone() {
      return (Builder) super.clone();
    }
    public Builder setField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        Object value) {
      return (Builder) super.setField(field, value);
    }
    public Builder clearField(
        com.google.protobuf.Descriptors.FieldDescriptor field) {
      return (Builder) super.clearField(field);
    }
    public Builder clearOneof(
        com.google.protobuf.Descriptors.OneofDescriptor oneof) {
      return (Builder) super.clearOneof(oneof);
    }
    public Builder setRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        int index, Object value) {
      return (Builder) super.setRepeatedField(field, index, value);
    }
    public Builder addRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        Object value) {
      return (Builder) super.addRepeatedField(field, value);
    }
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof cc.litstar.rpc.RequestVoteReply) {
        return mergeFrom((cc.litstar.rpc.RequestVoteReply)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(cc.litstar.rpc.RequestVoteReply other) {
      if (other == cc.litstar.rpc.RequestVoteReply.getDefaultInstance()) return this;
      if (other.getTerm() != 0) {
        setTerm(other.getTerm());
      }
      if (other.getVoteGranted() != false) {
        setVoteGranted(other.getVoteGranted());
      }
      onChanged();
      return this;
    }

    public final boolean isInitialized() {
      return true;
    }

    public Builder mergeFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      cc.litstar.rpc.RequestVoteReply parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (cc.litstar.rpc.RequestVoteReply) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }

    private int term_ ;
    /**
     * <code>optional int32 Term = 1;</code>
     */
    public int getTerm() {
      return term_;
    }
    /**
     * <code>optional int32 Term = 1;</code>
     */
    public Builder setTerm(int value) {
      
      term_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>optional int32 Term = 1;</code>
     */
    public Builder clearTerm() {
      
      term_ = 0;
      onChanged();
      return this;
    }

    private boolean voteGranted_ ;
    /**
     * <code>optional bool VoteGranted = 2;</code>
     */
    public boolean getVoteGranted() {
      return voteGranted_;
    }
    /**
     * <code>optional bool VoteGranted = 2;</code>
     */
    public Builder setVoteGranted(boolean value) {
      
      voteGranted_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>optional bool VoteGranted = 2;</code>
     */
    public Builder clearVoteGranted() {
      
      voteGranted_ = false;
      onChanged();
      return this;
    }
    public final Builder setUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return this;
    }

    public final Builder mergeUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return this;
    }


    // @@protoc_insertion_point(builder_scope:RequestVoteReply)
  }

  // @@protoc_insertion_point(class_scope:RequestVoteReply)
  private static final cc.litstar.rpc.RequestVoteReply DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new cc.litstar.rpc.RequestVoteReply();
  }

  public static cc.litstar.rpc.RequestVoteReply getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<RequestVoteReply>
      PARSER = new com.google.protobuf.AbstractParser<RequestVoteReply>() {
    public RequestVoteReply parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
        return new RequestVoteReply(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<RequestVoteReply> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<RequestVoteReply> getParserForType() {
    return PARSER;
  }

  public cc.litstar.rpc.RequestVoteReply getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

