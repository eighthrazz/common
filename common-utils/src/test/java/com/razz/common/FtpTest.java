package com.razz.common;

import static org.junit.Assert.*;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Properties;

import org.junit.Test;

import com.razz.common.util.config.ConfigManager;
import com.razz.common.util.ftp.Ftp;
import com.razz.common.util.ftp.FtpConfig;

public class FtpTest {
	
    public static void main(String args[]) throws Exception {
		final Properties props = ConfigManager.get();
		final Path ftpPath = Paths.get(props.getProperty("ftp.path"));
		final FtpConfig ftpConfig = new FtpConfig(props);
		final Ftp ftp = new Ftp(ftpConfig);
		final List<File> fileList = ftp.getFileList(ftpPath);
		System.out.println(fileList);
    }
}
