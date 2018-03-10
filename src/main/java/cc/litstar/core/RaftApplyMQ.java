package cc.litstar.core;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author HHX
 * 算法与提交解耦
 */
public class RaftApplyMQ {
	private static LinkedBlockingQueue<ApplyMsg> MQ = null;
	private RaftApplyMQ() {}
	
	public static synchronized LinkedBlockingQueue<ApplyMsg> getMQ() {
		if(MQ == null) {
			MQ = new LinkedBlockingQueue<>();
		}
		return MQ;
	}
}
