package com.razz.common.util.config;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public abstract class AbstractConfig<K> {
	
	private final Map<K,Object> map;
	
	public AbstractConfig() {
		map = new HashMap<>();
	}
	
	public AbstractConfig(Properties properties) {
		this();
		load(properties);
	}
	
	public void load(Properties properties) {
		for(Object keyObj : properties.keySet()) {
			final String keyStr = keyObj.toString();
			final String valueStr = properties.getProperty(keyStr, "");
			final K key = getKey(keyStr);
			if(key != null) {
				set(key, valueStr);
			}
		}
	}
	
	public void load(AbstractConfig<K> config) {
		map.putAll(config.map);
	}
	
	public abstract K getKey(String key);
	
	public void set(K key, Object value) {
		map.put(key, value);
	}
	
	public String getString(K key) {
		return map.get(key).toString().trim();
	}
	
	public char[] getCharArray(K key) {
		return getString(key).toCharArray();
	}
	
	public int getInt(K key) {
		return Integer.parseInt( getString(key) );
	}
	
	public long getLong(K key) {
		return Long.parseLong( getString(key) );
	}
	
	public double getDouble(K key) {
		return Double.parseDouble( getString(key) );
	}

}
