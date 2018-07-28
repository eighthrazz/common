package com.razz.common.mongo;

import java.util.Properties;

import com.razz.common.util.config.AbstractConfig;

public class MongoConfig extends AbstractConfig<MongoConfigKey> {

	public MongoConfig() {
		super();
	}
	
	public MongoConfig(Properties properties) {
		super(properties);
	}
	
	@Override
	public MongoConfigKey getKey(String key) {
		return MongoConfigKey.parse(key);
	}
	
}
