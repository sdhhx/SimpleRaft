package cc.litstar.message;

public class AppendEntriesReplyPojo {
	// 当前的任期号，用于领导人去更新自己
	// Leader收到更大的任期号，更新任期号并切换自身(发现新的Leader)
	// 有个问题：更大的任期号可能是由于网络分区造成的
	private int term;
	private boolean success;
	// 恢复值带上自己要的索引号(不用像论文里一个一个向前比对)
	private int nextIndex;
	public AppendEntriesReplyPojo(int term, boolean success, int nextIndex) {
		super();
		this.term = term;
		this.success = success;
		this.nextIndex = nextIndex;
	}
	public int getTerm() {
		return term;
	}
	public void setTerm(int term) {
		this.term = term;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public int getNextIndex() {
		return nextIndex;
	}
	public void setNextIndex(int nextIndex) {
		this.nextIndex = nextIndex;
	}
	@Override
	public String toString() {
		return "AppendEntriesReplyPojo [term=" + term + ", success=" + success + ", nextIndex=" + nextIndex + "]";
	}
}
