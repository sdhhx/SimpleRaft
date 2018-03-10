package cc.litstar.core;

public class LogEntryObj {
	//Log Index and Term
	private int logIndex;
	private int logTerm;
	//Op and data
	private String op;
	private String data;
	
	public LogEntryObj() {
		super();
	}
	public LogEntryObj(int logIndex, int logTerm, String op, String data) {
		super();
		this.logIndex = logIndex;
		this.logTerm = logTerm;
		this.op = op;
		this.data = data;
	}
	public int getLogIndex() {
		return logIndex;
	}
	public void setLogIndex(int logIndex) {
		this.logIndex = logIndex;
	}
	public int getLogTerm() {
		return logTerm;
	}
	public void setLogTerm(int logTerm) {
		this.logTerm = logTerm;
	}
	public String getOp() {
		return op;
	}
	public void setOp(String op) {
		this.op = op;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	@Override
	public String toString() {
		return "LogEntryPojo [logIndex=" + logIndex + ", logTerm=" + logTerm + ", op=" + op + ", data=" + data + "]";
	}
}
