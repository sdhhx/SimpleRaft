package cc.litstar;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import cc.litstar.conf.Options;
import cc.litstar.core.ApplyMsg;
import cc.litstar.core.Raft;
import cc.litstar.sm.StateMachine;

public class StartServer {
	static class KeyValueMachine implements StateMachine {
		
		private Map<String, String> keyValueMap = new HashMap<>();
		@Override
		public void execute(ApplyMsg msg) {
			String op = msg.getOp();
			String[] kv = msg.getData().split("\\s+");
			if(op == null) {
				return;
			} else if(op.equals("PUT")) {
				if(kv.length == 2) {
					keyValueMap.put(kv[0], kv[1]);
				}
			} else if(op.equals("DEL")) {
				if(kv.length == 1) {
					keyValueMap.remove(kv[0]);
				}
			} else if(op.equals("GET")) {
				if(kv.length == 1) {
					System.out.println(keyValueMap.get(kv[0]));
				}
			}
			printStatus();
		}
		
		public void printStatus() {
			for(Map.Entry<String, String> kv : keyValueMap.entrySet()) {
				System.out.println("Key == " + kv.getKey() + "  ,  Value == " + kv.getValue());
			}
		}
		
	}
	
    public static void main(String[] args) throws IOException {
        Raft raft = new Raft();
        Options options = new Options();
        options.setOption("HbInterval", 1000);
        raft.setOptions(options);
        raft.setStateMachine(new KeyValueMachine());
        raft.start();
    }
}
