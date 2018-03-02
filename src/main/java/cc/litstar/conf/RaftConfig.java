package cc.litstar.conf;

import java.util.List;
import cc.litstar.node.RaftNode;

public class RaftConfig {
	private RaftNode localNode;
	private int HbInterval;
	private List<RaftNode> remoteNode;
	
	public RaftConfig(RaftNode localNode, int hbInterval, List<RaftNode> remoteNode) {
		super();
		this.localNode = localNode;
		HbInterval = hbInterval;
		this.remoteNode = remoteNode;
	}
	public RaftNode getLocalNode() {
		return localNode;
	}
	public void setLocalNode(RaftNode localNode) {
		this.localNode = localNode;
	}
	public int getHbInterval() {
		return HbInterval;
	}
	public void setHbInterval(int hbInterval) {
		HbInterval = hbInterval;
	}
	public List<RaftNode> getRemoteNode() {
		return remoteNode;
	}
	public void setRemoteNode(List<RaftNode> remoteNode) {
		this.remoteNode = remoteNode;
	}
	@Override
	public String toString() {
		return "RaftConfig [localNode=" + localNode + ", HbInterval=" + HbInterval + ", remoteNode=" + remoteNode + "]";
	}
}
