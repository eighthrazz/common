package com.razz.common.aws;

import java.util.Map;

public enum AwsConfigKey {

	ACCESS_KEY("aws.accessKey"),
	SECRET_KEY("aws.secretKey"),
	REGION("aws.region"), 
	COLLECTION("aws.collection"),
	BUCKET("aws.bucket"),
	PROFILE_NAME("aws.profileName"),
	END_POINT("aws.endPoint");
	
	private final String key;

	private AwsConfigKey(String key) {
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
	
	public static AwsConfigKey parse(String keyStr) {
		for(AwsConfigKey k : AwsConfigKey.values()) {
			if(k.key.equalsIgnoreCase(keyStr)) {
				return k;
			}
		}
		return null;
	}
	
}
