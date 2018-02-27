package cc.litstar.conf;

import java.util.List;

import cc.litstar.node.RaftNode;

public class RaftConfig {
	private RaftNode localNode;
	private List<RaftNode> remoteNode;
	
	public RaftConfig(RaftNode localNode, List<RaftNode> remoteNode) {
		super();
		this.localNode = localNode;
		this.remoteNode = remoteNode;
	}
	public RaftNode getLocalNode() {
		return localNode;
	}
	public void setLocalNode(RaftNode localNode) {
		this.localNode = localNode;
	}
	public List<RaftNode> getRemoteNode() {
		return remoteNode;
	}
	public void setRemoteNode(List<RaftNode> remoteNode) {
		this.remoteNode = remoteNode;
	}
	@Override
	public String toString() {
		return "RaftConfig [localNode=" + localNode + ", remoteNode=" + remoteNode + "]";
	}
}
