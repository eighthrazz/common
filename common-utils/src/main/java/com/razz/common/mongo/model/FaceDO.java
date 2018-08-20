package com.razz.common.mongo.model;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Property;

import com.amazonaws.services.rekognition.model.FaceDetection;

@Entity("face")
public class FaceDO extends BaseDO {
	
	@Property("key")
	private String key;
	@Property("index")
	private int index;
	@Property("srcPath")
	private String srcPath;
	private FaceDetection faceDetection;
	
	public FaceDO() {
		setKey("");
		setIndex(0);
		setFaceDetection(null);
	}
	
	public FaceDO(String key, int index, FaceDetection faceDetection) {
		this();
		setKey(key);
		setIndex(index);
		setFaceDetection(faceDetection);
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getSrcPath() {
		return srcPath;
	}

	public void setSrcPath(String srcPath) {
		this.srcPath = srcPath;
	}

	public FaceDetection getFaceDetection() {
		return faceDetection;
	}

	public void setFaceDetection(FaceDetection faceDetection) {
		this.faceDetection = faceDetection;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((faceDetection == null) ? 0 : faceDetection.hashCode());
		result = prime * result + index;
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		result = prime * result + ((srcPath == null) ? 0 : srcPath.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FaceDO other = (FaceDO) obj;
		if (faceDetection == null) {
			if (other.faceDetection != null)
				return false;
		} else if (!faceDetection.equals(other.faceDetection))
			return false;
		if (index != other.index)
			return false;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		if (srcPath == null) {
			if (other.srcPath != null)
				return false;
		} else if (!srcPath.equals(other.srcPath))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "FaceDO [key=" + key + ", index=" + index + ", srcPath=" + srcPath + ", faceDetection=" + faceDetection
				+ "]";
	}

}
