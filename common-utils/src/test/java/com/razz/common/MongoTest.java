package com.razz.common;

import java.io.File;
import java.util.Properties;

import org.mongodb.morphia.Datastore;

import com.razz.common.mongo.Mongo;
import com.razz.common.mongo.MongoConfig;
import com.razz.common.mongo.dao.VideoDAO;
import com.razz.common.mongo.model.FactoryDO;
import com.razz.common.mongo.model.VideoDO;
import com.razz.common.util.config.ConfigManager;

public class MongoTest {

	public static void main(String args[]) {
		final Properties props = ConfigManager.get();
		final MongoConfig config = new MongoConfig(props);
		final Mongo noSql = new Mongo(config);
		try {
			noSql.connect();
			
			final String path = "/1/2/3/4.txt";
			final File file = new File(path);
			final VideoDO video = FactoryDO.createVideoDO(file);
			final Datastore datastore = noSql.getDatastore();
			final VideoDAO videoDAO = new VideoDAO(datastore);
			System.out.format("exists=%s%n", videoDAO.exists(video));
			videoDAO.save(video);
			System.out.format("exists=%s%n", videoDAO.exists(video));
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			noSql.close();
		}
	}
}
