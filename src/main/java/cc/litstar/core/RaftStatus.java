package cc.litstar.core;

public enum RaftStatus {
	LEADER(0),
	CANDIDATE(1),
	FOLLOWER(2);
	
	private int type;
	private RaftStatus(Integer type) {
		this.type = type;
	}
	public int getType() {
		return type;
	}
}
