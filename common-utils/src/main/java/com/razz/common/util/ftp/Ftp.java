package com.razz.common.util.ftp;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
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

public class Ftp implements Closeable {

	private final FtpConfig ftpConfig;
	private FTPClient ftpClient;
	
	public Ftp(FtpConfig ftpConfig) {
		this.ftpConfig = new FtpConfig(ftpConfig);
		
		// reset global variables
		close();
	}
	
	public void connect() throws Exception {
		// disconnect, ignore errors
		close();
		
		// initialize FTPClient
		ftpClient = new FTPClient();
		ftpClient.enterLocalPassiveMode();
		ftpClient.setAutodetectUTF8(true);
		ftpClient.setBufferSize(1024000);
		
		// connect
		final String host = ftpConfig.getString(FtpConfigKey.HOST);
		final int port = ftpConfig.getInt(FtpConfigKey.PORT);
		ftpClient.connect(host, port);
		
		// login
		final String user = ftpConfig.getString(FtpConfigKey.USER);
		final String password = ftpConfig.getString(FtpConfigKey.PASSWORD);
		ftpClient.login(user, password);
		
		// set file type
		ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
	}
	
	@Override
	public void close() {
		// close FTP connection
		if( ftpClient != null ) {
			try {
				ftpClient.logout();
			} catch(Exception IGNORE) {}
			try {
				ftpClient.disconnect();
			} catch(Exception IGNORE) {}
		}
		ftpClient = null;
	}
	
	/**
	 * getFTPClient.
	 * @return getFTPClient
	 * @throws Exception
	 */
	public FTPClient getFTPClient() throws Exception {
		if(ftpClient == null) {
			connect();
		}
		return ftpClient;
	}
	
	/**
	 * getFileList.
	 * @param path
	 * @return List<File>
	 * @throws Exception
	 */
	public List<File> getFileList(Path path) throws Exception {
		final List<File> fileList = new ArrayList<>();
		final String rootPath = ftpPath( path.toString() );
		final FTPFile ftpFiles[] = getFTPClient().listFiles(rootPath);
		for(FTPFile ftpFile : ftpFiles) {
			final File file = toFile(ftpFile, rootPath);
			fileList.add(file);
		}
		return fileList;
	}
	
	/**
	 * copy.
	 * @param remoteFileStr
	 * @return
	 * @throws Exception
	 */
	public File copy(String remoteFileStr) throws Exception {
		final File remoteFile = new File(remoteFileStr);
		return copy(remoteFile);
	}
	
	/**
	 * copy.
	 * @param remoteFile
	 * @return File
	 * @throws Exception
	 */
	public File copy(File remoteFile) throws Exception {
		final boolean deleteOnExit = true;
		return copy(remoteFile, deleteOnExit);
	}
	
	/**
	 * copy.
	 * @param remoteFile
	 * @param deleteOnExit
	 * @return File
	 * @throws Exception
	 */
	public File copy(File remoteFile, boolean deleteOnExit) throws Exception {
		final File tmpDir = FileHelper.getTmpDir();
		final File localFile = copy(remoteFile, tmpDir);
		if(deleteOnExit) {
			localFile.deleteOnExit();
		}
		return localFile;
	}
	
	/**
	 * copy.
	 * @param remoteFile
	 * @param localDir
	 * @return File
	 * @throws Exception
	 */
	public File copy(File remoteFile, File localDir) throws Exception {
		// verify localDir is a directory
		if( !localDir.isDirectory() ) {
			final String message = String.format("localDir must be a directory, localDir=%s", localDir);
			throw new NotDirectoryException(message);
		}
			
		// retrieve and write to localDir
		final String remote = ftpPath( remoteFile.getPath() );
		final File localFile = Paths.get(localDir.toString(), remoteFile.getName()).toFile();
		try(OutputStream localFileOut = new FileOutputStream(localFile)) {
			getFTPClient().retrieveFile(remote, localFileOut);
		}
		return localFile;
	}
	
	/**
	 * store.
	 * @param localFile
	 * @param remoteFile
	 * @throws Exception
	 */
	public void store(File localFile, File remoteFile) throws Exception {
		final String remote = ftpPath( remoteFile.getPath() );
		try(InputStream remoteFileIn = new FileInputStream(localFile)) {
			getFTPClient().storeFile(remote, remoteFileIn);
		}
	}
	
	/**
	 * toFile.
	 * @param ftpFile
	 * @param rootPath
	 * @return File
	 */
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
	
	/**
	 * ftpPath.
	 * @param path
	 * @return String
	 */
	public static String ftpPath(String path) {
		if(path == null) {
			return null;
		}
		return FilenameUtils.separatorsToUnix(path);
	}
	
}
