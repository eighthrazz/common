package com.razz.common.mongo;

import java.io.Closeable;

import org.bson.Document;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class Mongo implements Closeable {

	private final MongoConfig mongoConfig;
	private MongoClient mongoClient;
	private MongoDatabase mongoDatabase;
	private Morphia morphia;
	private Datastore datastore;
	
	public Mongo(MongoConfig config) {
		this.mongoConfig = new MongoConfig(config);
		
		// reset global variables
		close();
	}
	
	public void connect() throws Exception {
		// disconnect, ignore errors
		close();
		
		// build ServerAddress
		final String host = mongoConfig.getString(MongoConfigKey.HOST);
		final int port = mongoConfig.getInt(MongoConfigKey.PORT);
		final ServerAddress serverAddress = new ServerAddress(host, port); 
		
		// build MongoCredential
		final String user = mongoConfig.getString(MongoConfigKey.USER);
		final String database = mongoConfig.getString(MongoConfigKey.DATABASE);
		final char[] password = mongoConfig.getCharArray(MongoConfigKey.PASSWORD);
		final MongoCredential mongoCredential = MongoCredential.createCredential(user, database, password);
		
		// build MongoClientOptions
		final MongoClientOptions mongoClientOptions = MongoClientOptions.builder().build();
		
		// connect
		mongoClient = new MongoClient(serverAddress, mongoCredential, mongoClientOptions);
		mongoDatabase = mongoClient.getDatabase(database);
		
		// initialize Morphia
		morphia = new Morphia();
		morphia.mapPackage("com.razz.common.mongo.model");
		
		// initialize datastore
		datastore = morphia.createDatastore(mongoClient, database);
	}
	
	@Override
	public void close() {
		if(mongoClient != null) {
			try {
				mongoClient.close();
			} catch(Exception IGNORE) {}
		}
		mongoClient = null;
		mongoDatabase = null;
		morphia = null;
		datastore = null;
	}
	
	/**
	 * getMongoClient.
	 * @return MongoClient
	 * @throws Exception
	 */
	public MongoClient getMongoClient() throws Exception {
		if(mongoClient == null) {
			connect();
		}
		return mongoClient;
	}
	
	/**
	 * getMongoDatabase.
	 * @return MongoDatabase
	 * @throws Exception
	 */
	public MongoDatabase getMongoDatabase() throws Exception {
		if(mongoDatabase == null) {
			connect();
		}
		return mongoDatabase;
	}
	
	/**
	 * getMorphia.
	 * @return Morphia
	 * @throws Exception
	 */
	public Morphia getMorphia() throws Exception {
		if(morphia == null) {
			connect();
		}
		return morphia;
	}
	
	/**
	 * getDatastore.
	 * @return Datastore
	 * @throws Exception
	 */
	public Datastore getDatastore() throws Exception {
		if(datastore == null) {
			connect();
		}
		return datastore;
	}
	
	/**
	 * getCollection.
	 * @param name
	 * @return MongoCollection<Document>
	 * @throws Exception 
	 */
	public MongoCollection<Document> getCollection(String name) throws Exception {
		return getMongoDatabase().getCollection(name);
	}
	
}
