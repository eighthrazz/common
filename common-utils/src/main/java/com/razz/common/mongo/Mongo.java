package com.razz.common.mongo;

import org.bson.Document;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class Mongo {

	private final MongoConfig mongoConfig;
	private MongoClient mongoClient;
	private MongoDatabase mongoDatabase;
	private Morphia morphia;
	private Datastore datastore;
	
	public Mongo() {
		this.mongoConfig = new MongoConfig();
		
		// reset global variables
		close();
	}
	
	public void connect(MongoConfig config) throws Exception {
		// disconnect, ignore errors
		close();
		
		// set the global configuration
		mongoConfig.load(config);
		
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
	
	public MongoCollection<Document> getCollection(String name) {
		return mongoDatabase.getCollection(name);
	}
	
	public Datastore getDatastore() {
		return datastore;
	}
	
}
