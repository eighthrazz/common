package com.razz.common.util.ftp;


import java.util.Properties;

import com.razz.common.util.config.AbstractConfig;

public class FtpConfig extends AbstractConfig<FtpConfigKey> {

	public FtpConfig() {
		super();
	}
	
	public FtpConfig(Properties properties) {
		super(properties);
	}
	
	@Override
	public FtpConfigKey getKey(String key) {
		return FtpConfigKey.parse(key);
	}
	
}
