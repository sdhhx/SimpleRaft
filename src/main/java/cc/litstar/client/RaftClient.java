package cc.litstar.client;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cc.litstar.node.RaftNode;
import cc.litstar.rpc.ClientSubmitReply;
import cc.litstar.rpc.ClientSubmitRequest;

/**
 * 向Raft集群提交操作
 * @author HHX
 */
public class RaftClient {

	private List<RaftNode> cluster;
	private Map<Integer, RaftClientCall> rpcs;
	
	public RaftClient(List<RaftNode> cluster) {
		this.cluster = cluster;
		this.rpcs = new HashMap<>();
		for(RaftNode node : cluster) {
			RaftClientCall clientCall = new RaftClientCall(node.getIpAddress(), node.getPort());
			clientCall.init();
			rpcs.put(node.getId(), clientCall);
		}
	}
	
	/**
	 * 向集群提交操作
	 * 即轮询集群所有节点，找到Leader。若找不到Leader则失败
	 * @return
	 */
	public boolean submit(String op, String data) {
		ClientSubmitRequest.Builder requestBuilder = ClientSubmitRequest.newBuilder();
		ClientSubmitRequest request = null;
		requestBuilder.setOp(op);
		requestBuilder.setData(data);
		request = requestBuilder.build();
		for(Map.Entry<Integer, RaftClientCall> server : rpcs.entrySet()) {
			int id = server.getKey();
			RaftClientCall call = server.getValue();
			ClientSubmitReply reply = call.clientSubmitCall(request);
			if(reply != null && reply.getCanSubmit() == true) {
				return true;
			}
		}
		return false;
	}
	
}
