package cc.litstar.core;

import java.util.concurrent.LinkedBlockingQueue;

import cc.litstar.sm.StateMachine;

public class RaftApply implements Runnable {
	//状态机
	private StateMachine stateMachine;
	
	public RaftApply(StateMachine stateMachine) {
		super();
		this.stateMachine = stateMachine;
	}

	@Override
	public void run() {
		LinkedBlockingQueue<ApplyMsg> applyMQ = RaftApplyMQ.getMQ();
		try {
			while(!Thread.currentThread().isInterrupted()) {
				Object applyMsg = applyMQ.take();
				if(applyMsg instanceof ApplyMsg) {
					ApplyMsg msg = (ApplyMsg)applyMsg;
					stateMachine.execute(msg);
				}
			}
		} catch (InterruptedException e) {
			System.exit(0);
		}
	}
}
