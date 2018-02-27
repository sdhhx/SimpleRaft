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

public class ConfReader {
	private String confPath = "conf/RaftList.conf";
	private final static Logger logger = LoggerFactory.getLogger(ConfReader.class);
	
	public static RaftConfig config = null;
	
	public static RaftConfig getConfig() {
		if(config == null) {
			new ConfReader().readConf();
		}
		return config;
	}
	
	private ConfReader() {
		super();
	}
	
	private void readConf(){
		//读取Json字符串
		String json = "";
		String line = null;
		logger.info("Starting reading proxy information from ProxyList.conf");
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
		Type type = new TypeToken<ArrayList<RaftConfig>>(){}.getType();  
		try{
			//过滤非法字符
			config = gson.fromJson(json, type);  
		}catch (Exception e) {
			logger.error("Cannot read configure file -- errors found in the file");
			e.printStackTrace();
			System.exit(0);
		}
	}
}
