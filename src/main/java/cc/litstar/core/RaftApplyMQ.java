package cc.litstar.core;

import java.util.concurrent.LinkedBlockingQueue;

import cc.litstar.message.ApplyMsg;

/**
 * @author HHX
 * 算法与提交解耦
 */
public class RaftApplyMQ {
	private static LinkedBlockingQueue<ApplyMsg> MQ = null;
	//空构造方法
	private RaftApplyMQ() {}
	
	//获取消息的过程并不是线程安全的
	public static synchronized LinkedBlockingQueue<ApplyMsg> getMQ() {
		if(MQ == null) {
			MQ = new LinkedBlockingQueue<>();
		}
		return MQ;
	}
}
