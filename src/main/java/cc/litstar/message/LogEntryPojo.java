package cc.litstar.message;

public class LogEntryPojo {
	//操作
	private String op;
	//信息
	private String data;
	public LogEntryPojo(String op, String data) {
		super();
		this.op = op;
		this.data = data;
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
		return "LogEntryPojo [op=" + op + ", data=" + data + "]";
	}
}
