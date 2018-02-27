package cc.litstar.message;

public class RequestVoteReplyPojo {
	//当前任期号，便于候选人更新自己的任期号
	private int term;
	//候选人是否赢得了选票
	private boolean voteGranted;
	public RequestVoteReplyPojo(int term, boolean voteGranted) {
		super();
		this.term = term;
		this.voteGranted = voteGranted;
	}
	public int getTerm() {
		return term;
	}
	public void setTerm(int term) {
		this.term = term;
	}
	public boolean isVoteGranted() {
		return voteGranted;
	}
	public void setVoteGranted(boolean voteGranted) {
		this.voteGranted = voteGranted;
	}
	@Override
	public String toString() {
		return "RequestVoteReplyPojo [term=" + term + ", voteGranted=" + voteGranted + "]";
	}
}
