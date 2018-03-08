package cc.litstar.core;

import java.io.IOException;

import cc.litstar.conf.Options;
import cc.litstar.sm.StateMachine;

/**
 * @author hehaoxing
 * 程序的主类，用于加载启动
 */
public class Raft {
	//Raft参数
	private Options options;
	//状态机
	private StateMachine sm;
	
	public Raft() {};
	
	public void setOptions(Options options) {
		this.options = options;
	}
	
	public void setStateMachine(StateMachine sm) {
		this.sm = sm;
	}
	
	public boolean start() throws IOException {
		if(options == null || sm == null) {
			return false;
		}
		RaftCore raft = new RaftCore();
		raft.setOptions(options);
		raft.setStateMachine(sm);
		
        raft.init();
        raft.start();
        return true;
	}
}
