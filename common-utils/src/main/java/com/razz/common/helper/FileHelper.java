package com.razz.common.helper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;

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
	
	public static File getUserDir() {
		return Paths.get( System.getProperty("user.home") ).toFile();
	}
	
	public static File getTmpDir() {
		return Paths.get( System.getProperty("java.io.tmpdir") ).toFile();
	}
	
	public static File getTmpFile() throws IOException {
		final boolean deleteOnExit = true;
		final String extension = "tmp";
		return getTmpFile(deleteOnExit, extension);
	}
	
	public static File getTmpFile(String extension) throws IOException {
		final boolean deleteOnExit = true;
		return getTmpFile(deleteOnExit, extension);
	}
	
	public static File getTmpFile(boolean deleteOnExit, String extension) throws IOException {
		final String prefix = "temp_";
		final String suffix = extension == null ? ".tmp" : extension.startsWith(".") ? extension : ".".concat(extension);
		final String tmpFileName = String.format("%s%s%s", prefix, System.currentTimeMillis(), suffix);
		final File tmpFile = Paths.get(getTmpDir().toString(), tmpFileName).toFile();
		if(deleteOnExit) {
			tmpFile.deleteOnExit();
		}
		return tmpFile;
	}
	
	public static File setExtenstion(File file, String extension) {
		String filename = file.getName();
		if( !FilenameUtils.isExtension(filename, extension) ) {
			extension = extension.startsWith(".") ? extension : ".".concat(extension);
			filename = FilenameUtils.removeExtension(filename).concat(extension);
			return Paths.get(file.getParent(), filename).toFile();
		}
		return file;
	}
	
	public static boolean isExtension(File file, String extension) {
		final String filename = file.getName();
		return FilenameUtils.isExtension(filename, extension);
	}
	
}
