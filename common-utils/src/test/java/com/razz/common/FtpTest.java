package com.razz.common;

import static org.junit.Assert.*;

import java.io.File;
import java.nio.file.Paths;
import java.util.List;
import java.util.Properties;

import org.junit.Test;

import com.razz.common.util.config.ConfigManager;
import com.razz.common.util.ftp.Ftp;
import com.razz.common.util.ftp.FtpConfig;

public class FtpTest {
	
	@Test
    public void test() throws Exception {
		final Properties props = ConfigManager.get();
		final FtpConfig ftpConfig = new FtpConfig(props);
		final Ftp ftp = new Ftp();
		ftp.connect(ftpConfig);
		final List<File> fileList = ftp.getFileList(Paths.get("/SHARE_1/MEDIA/TUMBLR/"));
		System.out.println(fileList);
    }
}
