package com.razz.common.mongo.dao;

import java.util.List;

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
	
	public List<T> get() {
		final Query<T> query = datastore.createQuery( getType() );
		final List<T> list = query.asList();
		return list;
	}
	
	public List<T> get(Query<T> query) {
		final List<T> list = query.asList();
		return list;
	}
	
	public abstract boolean exists(T entity);
	
	public abstract void update(T entity);
	
	public abstract Query<T> getKeyQuery(T entity);
	
	public abstract Class<T> getType();
	
}
