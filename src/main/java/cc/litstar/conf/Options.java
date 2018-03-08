package cc.litstar.conf;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author hehaoxing
 * 编程完成的配置项
 */
public class Options {
	//配置Map
	private Map<String, String> optionMap;
	
	public Options() {
		this.optionMap = new HashMap<>();
	}
	
	public void setOption(String key, String value) {
		optionMap.put(key, value);
	}
	
	public void setOption(String key, int value) {
		optionMap.put(key, String.valueOf(value));
	}
	
	public void setOption(String key, double value) {
		optionMap.put(key, String.valueOf(value));
	}
	
	public String getOption(String key) {
		return optionMap.get(key);
	}
	
	public int getIntOption(String key) {
		String value = optionMap.get(key);
		if(isInteger(value)) {
			return Integer.parseInt(value);
		}
		return Integer.MIN_VALUE;
	}
	
	public double getDoubleOption(String key) {
		String value = optionMap.get(key);
		if(isDouble(value)) {
			return Double.parseDouble(value);
		}
		return Double.MIN_VALUE;
	}
	
	private boolean isInteger(String str) {  
		if (null == str || "".equals(str)) {  
			return false;  
		}  
		Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");  
		return pattern.matcher(str).matches();  
	}  
	  
	private boolean isDouble(String str) {  
		if (null == str || "".equals(str)) {  
			return false;  
		}  
		Pattern pattern = Pattern.compile("^[-\\+]?[.\\d]*$");  
		return pattern.matcher(str).matches();  
	}  
	
	public static void main(String[] args) {
		Options options = new Options();
		//options.setOption("ABC", 123);
		System.out.println(options.getIntOption("ABC"));
	}
}
