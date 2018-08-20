package com.razz.common.mongo.dao;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;

import com.razz.common.mongo.model.FaceDO;

public class FaceDAO extends BaseDAO<FaceDO> {

	public FaceDAO(Datastore datastore) {
		super(datastore);
	}

	@Override
	public boolean exists(FaceDO face) {
		boolean exists = getKeyQuery(face).count() > 0;
		return exists;
	}

	@Override
	public void update(FaceDO face) {
		// NOTE: any updates to the DO will have to be included here
		final UpdateOperations<FaceDO> updateOperation = 
				datastore.createUpdateOperations(getType())
				.set("faceDetection", face.getFaceDetection())
				.set("srcPath", face.getSrcPath()); 
		datastore.update(getKeyQuery(face), updateOperation);
	}
	
	@Override
	public Query<FaceDO> getKeyQuery(FaceDO face) {
		return datastore.find( getType() ).filter("key ==", face.getKey()).filter("index ==", face.getIndex());
	}

	@Override
	public Class<FaceDO> getType() {
		return FaceDO.class;
	}
	
}
