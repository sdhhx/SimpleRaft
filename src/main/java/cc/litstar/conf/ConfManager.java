package cc.litstar.conf;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ConfManager {
	private String confPath = "conf/Raft.conf";
	private final static Logger logger = LoggerFactory.getLogger(ConfManager.class);
	
	public static RaftConfig config = null;
	
	public static RaftConfig getConfig() {
		if(config == null) {
			new ConfManager().readConf();
		}
		return config;
	}
	
	private ConfManager() {
		super();
	}
	
	private void readConf() {
		//读取Json字符串
		String json = "";
		String line = null;
		try(BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(confPath), "UTF-8")) ){
			while((line=in.readLine())!=null){
				//注释以;开头
				if(!line.startsWith(";")){
					json += line;
				}	
			}
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
		json = json.replaceAll("\\s+", "");
		Gson gson = new Gson();  
		Type type = new TypeToken<RaftConfig>(){}.getType();  
		try{
			//过滤非法字符
			config = gson.fromJson(json, type);  
		}catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	public static void main(String[] args) {
		ConfManager.getConfig();
	}
}
