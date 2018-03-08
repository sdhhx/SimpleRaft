package cc.litstar.conf;

import java.util.List;
import cc.litstar.node.RaftNode;

public class ServerConf {
	private RaftNode localNode;
	private List<RaftNode> remoteNode;
	
	public ServerConf(RaftNode localNode, List<RaftNode> remoteNode) {
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
		return "ServerConf [localNode=" + localNode + ", remoteNode=" + remoteNode + "]";
	}

}
