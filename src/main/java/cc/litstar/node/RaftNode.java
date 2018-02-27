package cc.litstar.node;

public class RaftNode {
	
	private int id;
	private String ipAddress;
	private int port;
	
	public RaftNode(int id, String ipAddress, int port) {
		super();
		this.id = id;
		this.ipAddress = ipAddress;
		this.port = port;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}

	@Override
	public String toString() {
		return "RaftNode [id=" + id + ", ipAddress=" + ipAddress + ", port=" + port + "]";
	}
	
}
