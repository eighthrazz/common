package com.razz.common.mongo.dao;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;

import com.razz.common.mongo.model.VideoDO;

public class VideoDAO extends BaseDAO<VideoDO> {

	public VideoDAO(Datastore datastore) {
		super(datastore);
	}

	@Override
	public boolean exists(VideoDO video) {
		boolean exists = getKeyQuery(video).count() > 0;
		return exists;
	}

	@Override
	public void update(VideoDO video) {
		// NOTE: any updates to the DO will have to be included here
		final UpdateOperations<VideoDO> updateOperation = 
				datastore.createUpdateOperations(getType())
				.set("srcPath", video.getSrcPath())
				.set("previewPath", video.getPreviewPath()); 
		datastore.update(getKeyQuery(video), updateOperation);
	}
	
	@Override
	public Query<VideoDO> getKeyQuery(VideoDO video) {
		return datastore.find( getType() ).filter("key ==", video.getKey());
	}

	@Override
	public Class<VideoDO> getType() {
		return VideoDO.class;
	}
	
}
