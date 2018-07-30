package com.razz.common.mongo.dao;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;

public abstract class BaseDAO<T> {

	final protected Datastore datastore;
	
	public BaseDAO(Datastore datastore) {
		this.datastore = datastore;
	}
	
	public void save(T entity) {
		if( exists(entity) ) {
			update(entity);
		} else {
			datastore.save(entity);
		}
	}
	
	public abstract boolean exists(T entity);
	
	public abstract void update(T entity);
	
	public abstract Query<T> getKeyQuery(T entity);
	
}
