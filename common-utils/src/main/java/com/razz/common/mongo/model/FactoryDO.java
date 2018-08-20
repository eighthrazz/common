package com.razz.common.mongo.model;

import java.io.File;

import com.amazonaws.services.rekognition.model.FaceDetection;

public class FactoryDO {

	public static VideoDO createVideoDO(File file) {
		final String key = file.getName();
		final String path = file.getPath();
		final VideoDO videoDO = new VideoDO(key, path);
		return videoDO;
	}
	
	public static FaceDO createFaceDO(String key, int index, FaceDetection faceDetection) {
		final FaceDO faceDO = new FaceDO(key, index, faceDetection);
		return faceDO;
	}
	
}
