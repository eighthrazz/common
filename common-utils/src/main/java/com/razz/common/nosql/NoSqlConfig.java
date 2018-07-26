package com.razz.common.nosql;

import java.util.Properties;

import com.razz.common.util.config.AbstractConfig;

public class NoSqlConfig extends AbstractConfig<NoSqlConfigKey> {

	public NoSqlConfig() {
		super();
	}
	
	public NoSqlConfig(Properties properties) {
		super(properties);
	}
	
	@Override
	public NoSqlConfigKey getKey(String key) {
		return NoSqlConfigKey.parse(key);
	}
	
}
