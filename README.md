SimpleRaft
====================	

@Author: hehaoxing   
Raft是一种管理日志的一致性算法，这里参考了raft论文并加以简单实现。   

### 程序说明：
Raft是一种管理日志的一致性算法。相比Paxos，其更加容易理解且构建实际的系统。使用者可以根据其实现一个自定义分布式服务。    
这里实现了Raft协议的领导人选举、日志复制与安全性三个重要模块，并提供了一个简单的KV存储范例，其完整目录在src/main/example目录下。       

### 使用方法：

1. 实现一个状态机，提交完成的日志需要应用到状态机中：    
一个简单的状态机如下：    
```
	public static class KeyValueMachine implements StateMachine {	
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
		}
	}
```

2. 编辑配置文件，填写本地节点与集群所有节点信息，配置文件位于目录conf/Raft.conf：    

```
	;配置文件格式：
	{
		"localNode" : {
			"id" : 1,
			"ipAddress" : "127.0.0.1",
			"port" : 50051
		},
		"remoteNode" : [
			{
				"id" : 1,
				"ipAddress" : "127.0.0.1",
				"port" : 50051
			},
			{
				"id" : 2,
				"ipAddress" : "127.0.0.1",
				"port" : 50052
			},
			{
				"id" : 3,
				"ipAddress" : "127.0.0.1",
				"port" : 50053
			},
			{
				"id" : 4,
				"ipAddress" : "127.0.0.1",
				"port" : 50054
			},
			{
				"id" : 5,
				"ipAddress" : "127.0.0.1",
				"port" : 50055
			}
		]
	}
```

设置心跳间隔与状态机，逐一启动Server：    
```

    public static void main(String[] args) throws IOException {
        Raft raft = new Raft();
        Options options = new Options();
        options.setOption("HbInterval", 1000);
        raft.setOptions(options);
        raft.setStateMachine(new KeyValueMachine());
        raft.start();
    }
    
```
3. 启动Client，向集群轮询发送请求：    
```
	public static void main(String[] args) {
		RaftClient client = new RaftClient(ConfReader.getConf().getRemoteNode());
		client.submit("PUT", "X 1");
		client.submit("GET", "X");
		client.submit("DEL", "X");
	}   
	
```

### TODO：
1. 添加Raft日志压缩RPC的实现(论文第7章)。    
2. 添加Raft动态增减节点的功能(论文第6章)，提高其拓展性。    
3. 优化实现，思考改进点。   

### 参考资料：
1. [In Search of an Understandable Consensus Algorithm(Extended Version)](https://ramcloud.atlassian.net/wiki/download/attachments/6586375/raft.pdf)    
2. [Raft一致性算法论文的中文翻译](https://github.com/maemual/raft-zh_cn/blob/master/raft-zh_cn.md)    
3. [MIT’s distributed systems course(lab2)](http://pdos.csail.mit.edu/6.824/)    
4. [Raft动画展示](http://thesecretlivesofdata.com/raft/)    
