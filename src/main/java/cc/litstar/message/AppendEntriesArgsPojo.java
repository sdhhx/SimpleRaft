package cc.litstar.message;

import java.util.List;

public class AppendEntriesArgsPojo {
	//领导人的任期号
	private int term;
	//领导人的ID，以便跟随者(重定向)请求
	private int leaderId;
	// 新的日志条目紧随之前的任期号和索引值
	// PrevLogIndex用于判断由Leader发来的日志是否可以被应用(是否匹配)
	// PrevLogTerm(相同的索引但不同的任期号(日志冲突))，删掉这一条和之后所有的
	private int prevLogTerm;
	private int prevLogIndex;
	// 准备存储的日志条目(可以发多条提高效率)
	private List<LogEntryPojo> entries;
	// 领导人已经提交的日志条目
	// 令commitIndex等于commitIndex和新日志条目索引中较小的一个
	// 可以被提交应用的日志(心跳有时会带上这个)
	private int leaderCommit;
	
	public AppendEntriesArgsPojo(int term, int leaderId, int prevLogTerm, int prevLogIndex, List<LogEntryPojo> entries,
			int leaderCommit) {
		super();
		this.term = term;
		this.leaderId = leaderId;
		this.prevLogTerm = prevLogTerm;
		this.prevLogIndex = prevLogIndex;
		this.entries = entries;
		this.leaderCommit = leaderCommit;
	}

	public int getTerm() {
		return term;
	}

	public void setTerm(int term) {
		this.term = term;
	}

	public int getLeaderId() {
		return leaderId;
	}

	public void setLeaderId(int leaderId) {
		this.leaderId = leaderId;
	}

	public int getPrevLogTerm() {
		return prevLogTerm;
	}

	public void setPrevLogTerm(int prevLogTerm) {
		this.prevLogTerm = prevLogTerm;
	}

	public int getPrevLogIndex() {
		return prevLogIndex;
	}

	public void setPrevLogIndex(int prevLogIndex) {
		this.prevLogIndex = prevLogIndex;
	}

	public List<LogEntryPojo> getEntries() {
		return entries;
	}

	public void setEntries(List<LogEntryPojo> entries) {
		this.entries = entries;
	}

	public int getLeaderCommit() {
		return leaderCommit;
	}

	public void setLeaderCommit(int leaderCommit) {
		this.leaderCommit = leaderCommit;
	}

	@Override
	public String toString() {
		return "AppendEntriesArgsPojo [term=" + term + ", leaderId=" + leaderId + ", prevLogTerm=" + prevLogTerm
				+ ", prevLogIndex=" + prevLogIndex + ", entries=" + entries + ", leaderCommit=" + leaderCommit + "]";
	}
	
}
