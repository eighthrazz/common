package com.razz.common.util.ftp;


import com.razz.common.util.AbstractConfig;

public class FtpConfig extends AbstractConfig<FtpConfigKey> {

	@Override
	public FtpConfigKey getKey(String key) {
		return FtpConfigKey.parse(key);
	}
	
}
