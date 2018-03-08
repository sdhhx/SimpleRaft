package cc.litstar.beans;

//Raft提交与具体应用的解耦
public class ApplyMsg {
	//索引
	private int index;
	//指令与数据
	private String op;
	private String data;
	public ApplyMsg(int index, String op, String data) {
		super();
		this.index = index;
		this.op = op;
		this.data = data;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
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
		return "ApplyMsg [index=" + index + ", op=" + op + ", data=" + data + "]";
	}
	
}
