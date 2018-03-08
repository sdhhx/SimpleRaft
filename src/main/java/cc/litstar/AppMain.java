package cc.litstar;

import java.io.IOException;

import cc.litstar.beans.ApplyMsg;
import cc.litstar.conf.Options;
import cc.litstar.core.Raft;
import cc.litstar.sm.StateMachine;

/**
 * Hello world!
 *
 */
public class AppMain {
	
	static class KeyValueMachine implements StateMachine {

		@Override
		public void execute(ApplyMsg msg) {
			System.out.println("Op : " + msg.getOp() + " Data : " + msg.getData());
		}
		
	}
	
    public static void main(String[] args) throws IOException {
        Raft raft = new Raft();
        Options options = new Options();
        options.setOption("HbInterval", 500);
        raft.setOptions(options);
        raft.setStateMachine(new KeyValueMachine());
        raft.start();
    }
}
