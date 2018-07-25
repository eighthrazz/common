package com.razz.common.util.ftp;

import org.apache.commons.net.ftp.FTPClient;

public class Ftp {

	private final FTPClient ftpClient;
	
	public Ftp() {
		// initialize FTPClient
		ftpClient = new FTPClient();
		ftpClient.enterLocalPassiveMode();
		ftpClient.setAutodetectUTF8(true);
		ftpClient.setBufferSize(1024000);
	}
	
	public void connect(FtpConfig config) throws Exception {
		// disconnect, ignore errors
		close();
		
		// connect
		final String ip = config.getString(FtpConfigKey.IP);
		final int port = config.getInt(FtpConfigKey.PORT);
		ftpClient.connect(ip, port);
		
		// login
		final String user = config.getString(FtpConfigKey.USER);
		final String password = config.getString(FtpConfigKey.PASSWORD);
		ftpClient.login(user, password);
	}
	
	public void close() {
		// close FTP connection
		if( ftpClient.isConnected() ) {
			try {
				ftpClient.logout();
			} catch(Exception IGNORE) {}
			try {
				ftpClient.disconnect();
			} catch(Exception IGNORE) {}
		}
	}
	
}
