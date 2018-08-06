package com.razz.common.aws;

import java.util.Properties;

import com.razz.common.util.config.AbstractConfig;

public class AwsConfig extends AbstractConfig<AwsConfigKey> {

	public AwsConfig() {
		super();
	}
	
	public AwsConfig(Properties properties) {
		super(properties);
	}
	
	@Override
	public AwsConfigKey getKey(String key) {
		return AwsConfigKey.parse(key);
	}
	
}
