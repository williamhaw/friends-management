package com.williamhaw.friendmanagement.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Add some helper methods on top of Java Properties
 * @author williamhaw
 *
 */
public class PropertiesHelper{
	
	private final Properties props;
	
	public PropertiesHelper(Properties props) {
		if(props == null)
			throw new RuntimeException("Properties cannot be null");
		this.props = props;
	}
	
	public String getString(final String key, final String defaultValue) {
		String val = props.getProperty(key);
		if (val == null || "".equals(val))
			return defaultValue;
		return val;
	}
	
	public int getInt(final String key, int defaultValue) {
		String val = props.getProperty(key);
		if (val == null)
			return defaultValue;
		try {
			return Integer.valueOf(val);
		} catch (Exception e) {
			System.err.println("Invalid integer value when processing property " + key + ": " + val);
			return defaultValue;
		}
	}
	
	/**
	 * Return a Map<String,String> of key value pairs where
	 * each key matches the specific prefix provided
	 * <p>
	 * If no keys match this will return an empty Map 
	 * 
	 * @param prefix
	 * @return
	 */
	public Map<String,String> getStrings(final String prefix ) {

		if (prefix == null)
			throw new IllegalArgumentException("Prefix cannot be empty");

		HashMap<String,String> ret = new HashMap<String,String>();

		for (Object o: props.keySet()) {
			String key = (String)o; 
			if (key.startsWith(prefix))
				ret.put(key, props.getProperty(key));
		}
		
		return ret;
	}
}
