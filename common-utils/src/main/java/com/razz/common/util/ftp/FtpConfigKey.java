package com.razz.common.util.ftp;

import java.util.Map;

public enum FtpConfigKey {
	IP("ftp.ip"), 
	PORT("ftp.port"), 
	USER("ftp.user"), 
	PASSWORD("ftp.password");

	private final String key;

	private FtpConfigKey(String key) {
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
	
	public static FtpConfigKey parse(String keyStr) {
		for(FtpConfigKey k : FtpConfigKey.values()) {
			if(k.key.equalsIgnoreCase(keyStr)) {
				return k;
			}
		}
		return null;
	}
}
