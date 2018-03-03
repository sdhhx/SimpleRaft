package cc.litstar.beans;

import java.util.Arrays;

//Raft提交与具体应用的解耦
public class ApplyMsg {
	//索引
	private int index;
	//指令与数据
	private String op;
	private String data;
	//快照
	private boolean useSnapShot;
	private byte[] snapshot;
	
	public ApplyMsg(int index, String op, String data, boolean useSnapShot, byte[] snapshot) {
		super();
		this.index = index;
		this.op = op;
		this.data = data;
		this.useSnapShot = useSnapShot;
		this.snapshot = snapshot;
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
	public boolean isUseSnapShot() {
		return useSnapShot;
	}
	public void setUseSnapShot(boolean useSnapShot) {
		this.useSnapShot = useSnapShot;
	}
	public byte[] getSnapshot() {
		return snapshot;
	}
	public void setSnapshot(byte[] snapshot) {
		this.snapshot = snapshot;
	}
	@Override
	public String toString() {
		return "ApplyMsg [index=" + index + ", op=" + op + ", data=" + data + ", useSnapShot=" + useSnapShot
				+ ", snapshot=" + Arrays.toString(snapshot) + "]";
	}
}
