package com.razz.common;

import java.util.Properties;

import com.razz.common.mongo.Mongo;
import com.razz.common.mongo.MongoConfig;
import com.razz.common.mongo.model.VideoDO;
import com.razz.common.util.config.ConfigManager;

public class MongoTest {

	public static void main(String args[]) {
		final Properties props = ConfigManager.get();
		final MongoConfig config = new MongoConfig(props);
		final Mongo noSql = new Mongo();
		try {
			noSql.connect(config);
			
			final VideoDO video = new VideoDO();
			noSql.getDatastore().save(video);
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			noSql.close();
		}
	}
}
