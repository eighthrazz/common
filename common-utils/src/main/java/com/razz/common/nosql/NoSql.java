package com.razz.common.nosql;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class NoSql {

	private MongoClient mongoClient;
	private MongoDatabase mongoDatabase;
	
	public NoSql() {
		this.mongoClient = null;
	}
	
	public void connect(NoSqlConfig config) throws Exception {
		// disconnect, ignore errors
		close();
		
		// build ServerAddress
		final String host = config.getString(NoSqlConfigKey.HOST);
		final int port = config.getInt(NoSqlConfigKey.PORT);
		final ServerAddress serverAddress = new ServerAddress(host, port); 
		
		// build MongoCredential
		final String user = config.getString(NoSqlConfigKey.USER);
		final String database = config.getString(NoSqlConfigKey.DATABASE);
		final char[] password = config.getCharArray(NoSqlConfigKey.PASSWORD);
		final MongoCredential mongoCredential = MongoCredential.createCredential(user, database, password);
		
		// build MongoClientOptions
		final MongoClientOptions mongoClientOptions = MongoClientOptions.builder().build();
		
		// connect
		mongoClient = new MongoClient(serverAddress, mongoCredential, mongoClientOptions);
		mongoDatabase = mongoClient.getDatabase(database);
	}
	
	public void close() {
		if(mongoClient != null) {
			try {
				mongoClient.close();
			} catch(Exception IGNORE) {}
		}
		mongoClient = null;
		mongoDatabase = null;
	}
	
	public MongoCollection<Document> getCollection(String name) {
		return mongoDatabase.getCollection(name);
	}
	
}
