package cc.litstar.message;

public class RequestVoteArgsPojo {
	//候选人的任期号
	private int term;
	//请求投票的候选人ID
	private int candidateId;
	//候选人最后的日志条目
	private int lastLogTerm;
	//候选人最后日志条目的任期号
	private int lastLogIndex;
	
	public RequestVoteArgsPojo(int term, int candidateId, int lastLogTerm, int lastLogIndex) {
		super();
		this.term = term;
		this.candidateId = candidateId;
		this.lastLogTerm = lastLogTerm;
		this.lastLogIndex = lastLogIndex;
	}
	
	public int getTerm() {
		return term;
	}
	public void setTerm(int term) {
		this.term = term;
	}
	public int getCandidateId() {
		return candidateId;
	}
	public void setCandidateId(int candidateId) {
		this.candidateId = candidateId;
	}
	public int getLastLogTerm() {
		return lastLogTerm;
	}
	public void setLastLogTerm(int lastLogTerm) {
		this.lastLogTerm = lastLogTerm;
	}
	public int getLastLogIndex() {
		return lastLogIndex;
	}
	public void setLastLogIndex(int lastLogIndex) {
		this.lastLogIndex = lastLogIndex;
	}
	@Override
	public String toString() {
		return "RequestVoteArgsPojo [term=" + term + ", candidateId=" + candidateId + ", lastLogTerm=" + lastLogTerm
				+ ", lastLogIndex=" + lastLogIndex + "]";
	}
}
