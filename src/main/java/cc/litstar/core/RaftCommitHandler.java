package cc.litstar.core;

import java.util.concurrent.LinkedBlockingQueue;

import cc.litstar.message.ApplyMsg;

public class RaftCommitHandler implements Runnable {

	@Override
	public void run() {
		LinkedBlockingQueue<ApplyMsg> applyMQ = RaftApplyMQ.getMQ();
		try {
			while(!Thread.currentThread().isInterrupted()) {
				Object applyMsg = applyMQ.take();
				if(applyMsg instanceof ApplyMsg) {
					ApplyMsg msg = (ApplyMsg)applyMsg;
					System.out.println("Op:" + msg.getOp() + " Data:" + msg.getData());
				}
			}
		} catch (InterruptedException e) {
			System.exit(0);
		}
	}
}
