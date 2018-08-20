package com.razz.common.mongo.model;

import java.util.List;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Property;

import com.amazonaws.services.rekognition.model.FaceDetection;

@Entity("video")
public class VideoDO extends BaseDO {
	
	@Property("path")
	private String path;
	@Property("active")
	private boolean active;
	@Property("faces")
	private List<FaceDetection> faceList;
	
	public VideoDO() {
		setPath("");
		setActive(true);
	}
	
	public VideoDO(String path) {
		this();
		setPath(path);
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public List<FaceDetection> getFaceList() {
		return faceList;
	}

	public void setFaceList(List<FaceDetection> faceList) {
		this.faceList = faceList;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (active ? 1231 : 1237);
		result = prime * result + ((faceList == null) ? 0 : faceList.hashCode());
		result = prime * result + ((path == null) ? 0 : path.hashCode());
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
		VideoDO other = (VideoDO) obj;
		if (active != other.active)
			return false;
		if (faceList == null) {
			if (other.faceList != null)
				return false;
		} else if (!faceList.equals(other.faceList))
			return false;
		if (path == null) {
			if (other.path != null)
				return false;
		} else if (!path.equals(other.path))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "VideoDO [path=" + path + ", active=" + active + ", faceList=" + faceList + "]";
	}
	
}
