package com.razz.common.util.config;

import java.net.URL;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Properties;

import org.apache.commons.configuration2.CompositeConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.fluent.Configurations;

import com.razz.common.helper.FileHelper;
import com.razz.common.util.log.Logger;

public class ConfigManager {

	private static CompositeConfiguration config = null;
	
	synchronized static CompositeConfiguration getPropConfig() {
		if(config == null) {
			config = new CompositeConfiguration();
			loadFromResource();
			loadFromFileSystem();
		}
		return config;
	}
	
	static void loadFromResource() {
		try {
			final URL url = ConfigManager.class.getClassLoader().getResource("connection.properties");
			final Configurations configs = new Configurations();
			final PropertiesConfiguration propConfig = configs.properties(url);
			config.addConfiguration(propConfig);
		} catch(Exception e) {
			final String error = "Unable to from resource.";
			Logger.error(error, e);
		}
	}
	
	static void loadFromFileSystem() {
		try {
			// try config.path property first
			String path = getString("config.path");
			if( !FileHelper.doesFileExist(path) ) {
				Logger.debug(ConfigManager.class, "loadFromFileSystem", 
						"propery config.path does not exist, is empty, or invalid, config.path=%s", path);
				path = null;
			}
			
			// if null, try default path
			if(path == null) {
				path = getDefaultConfigPath();
				if( !FileHelper.doesFileExist(path) ) {
					Logger.debug(ConfigManager.class, "loadFromFileSystem", 
							"default path does not exist, path=%s", path);
					path = null;
				}
			}
			
			// load the path
			if(path != null) {
				final Configurations configs = new Configurations();
				final PropertiesConfiguration propConfig = configs.properties(path);
				config.addConfiguration(propConfig);
			}
		} catch(Exception e) {
			final String error = "Unable to from file system.";
			Logger.error(error, e);
		}
	}
	
	static String getDefaultConfigPath() {
		return Paths.get(System.getProperty("user.home"), "config.properties").toString();
	}
	
	public static String getString(String key) {
		return getPropConfig().getString(key);
	}
	
	public static int getInt(String key) {
		return getPropConfig().getInt(key);
	}
	
	public static Properties get() {
		final Properties properties = new Properties();
		final Iterator<String> iter = getPropConfig().getKeys();
		while(iter.hasNext()) {
			final String key = iter.next();
			final String value = getPropConfig().getString(key);
			properties.setProperty(key, value);
		}
		return properties;
	}
	
}
