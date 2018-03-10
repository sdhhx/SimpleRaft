package cc.litstar;

import cc.litstar.client.RaftClient;
import cc.litstar.conf.ConfReader;
import cc.litstar.conf.ServerConf;

public class Test {

	public static void main(String[] args) {
		ServerConf conf = ConfReader.getConf();
		RaftClient client = new RaftClient(conf.getRemoteNode());
		System.out.println(client.submit("GET", "X"));
	}
}
