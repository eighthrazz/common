package com.razz.common.util.ftp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.NotDirectoryException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import com.razz.common.helper.FileHelper;

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
		final String host = config.getString(FtpConfigKey.HOST);
		final int port = config.getInt(FtpConfigKey.PORT);
		ftpClient.connect(host, port);
		
		// login
		final String user = config.getString(FtpConfigKey.USER);
		final String password = config.getString(FtpConfigKey.PASSWORD);
		ftpClient.login(user, password);
		
		// set file type
		ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
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
	
	public List<File> getFileList(Path path) throws IOException {
		final List<File> fileList = new ArrayList<>();
		final String rootPath = ftpPath( path.toString() );
		final FTPFile ftpFiles[] = ftpClient.listFiles(rootPath);
		for(FTPFile ftpFile : ftpFiles) {
			final File file = toFile(ftpFile, rootPath);
			fileList.add(file);
		}
		return fileList;
	}
	
	public File copy(File remoteFile) throws IOException {
		final boolean deleteOnExit = true;
		return copy(remoteFile, deleteOnExit);
	}
	
	public File copy(File remoteFile, boolean deleteOnExit) throws IOException {
		final File tmpDir = FileHelper.getTmpDir();
		final File localFile = copy(remoteFile, tmpDir);
		if(deleteOnExit) {
			localFile.deleteOnExit();
		}
		return localFile;
	}
	
	public File copy(File remoteFile, File localDir) throws IOException {
		// verify localDir is a directory
		if( !localDir.isDirectory() ) {
			final String message = String.format("localDir must be a directory, localDir=%s", localDir);
			throw new NotDirectoryException(message);
		}
			
		// retrieve and write to localDir
		final String remote = ftpPath( remoteFile.getPath() );
		final File localFile = Paths.get(localDir.toString(), remoteFile.getName()).toFile();
		try(OutputStream localFileOut = new FileOutputStream(localFile)) {
			ftpClient.retrieveFile(remote, localFileOut);
		}
		return localFile;
	}
	
	public static File toFile(FTPFile ftpFile, String rootPath) {
		final Path path = Paths.get(rootPath, ftpFile.getName());
		final File file = new File(path.toString()) {
			private static final long serialVersionUID = -5319472955414649252L;

			@Override
			public boolean isDirectory() {
				return ftpFile.isDirectory();
			}
			
			@Override
			public boolean isFile() {
				return ftpFile.isFile();
			}
		};
		return file;
	}
	
	public static String ftpPath(String path) {
		if(path == null) {
			return null;
		}
		return FilenameUtils.separatorsToUnix(path);
	}
	
}
