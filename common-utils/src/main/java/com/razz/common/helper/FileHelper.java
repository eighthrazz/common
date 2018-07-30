package com.razz.common.helper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.UUID;

import com.razz.common.util.log.Logger;

public class FileHelper {

	public static UUID getUUID(File file) {
		final String name = getName(file);
		return UUID.nameUUIDFromBytes(name.getBytes());
	}
	
	public static String getName(File file) {
		final String name = file.getName();
		return name;
	}
	
	public static boolean doesFileExist(String filePath) {
		// false, if null
		if(filePath == null) {
			Logger.debug(FileHelper.class, "doesFileExist", "is null, filePath=%s", filePath);
			return false;
		}
		
		// create File object
		final File file = Paths.get(filePath).toFile();
		
		// false, if directory
		if(file.isDirectory()) {
			Logger.debug(FileHelper.class, "doesFileExist", "is directory, file=%s", file);
			return false;
		}
		
		// false, if file does not exist
		if( !file.exists() ) {
			Logger.debug(FileHelper.class, "doesFileExist", "does not exist, file=%s", file);
			return false;
		}
		
		// else, true
		return true;
	}
	
	public static File getTmpDir() {
		return Paths.get( System.getProperty("java.io.tmpdir") ).toFile();
	}
	
	public static File getTmpFile() throws IOException {
		final String prefix = "temp";
		final String suffix = "tmp";
		final File directory = getTmpDir();
		final File tmpFile = File.createTempFile(prefix, suffix, directory);
		tmpFile.deleteOnExit();
		return tmpFile;
	}
	
}
