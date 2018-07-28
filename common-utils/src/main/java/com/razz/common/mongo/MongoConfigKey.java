package com.razz.common.mongo;

import java.util.Map;

public enum MongoConfigKey {

	USER("nosql.user"),
	PASSWORD("nosql.password"),
	HOST("nosql.host"), 
	PORT("nosql.port"),
	DATABASE("nosql.database");
	
	private final String key;

	private MongoConfigKey(String key) {
		this.key = key;
	}

	public String getValue(Map<String, String> map) {
		return map.get(key);
	}

	public void setValue(Map<String, String> map, String value) {
		map.put(key, value);
	}
	
	@Override
	public String toString() {
		return key;
	}
	
	public static MongoConfigKey parse(String keyStr) {
		for(MongoConfigKey k : MongoConfigKey.values()) {
			if(k.key.equalsIgnoreCase(keyStr)) {
				return k;
			}
		}
		return null;
	}
	
}
