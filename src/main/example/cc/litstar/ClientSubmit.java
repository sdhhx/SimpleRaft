package cc.litstar;

import cc.litstar.client.RaftClient;
import cc.litstar.conf.ConfReader;

public class ClientSubmit {

	public static void main(String[] args) {
		RaftClient client = new RaftClient(ConfReader.getConf().getRemoteNode());
		client.submit("PUT", "X 1");
		client.submit("GET", "X");
		client.submit("DEL", "X");
	}
}
