package com.razz.common.mongo.model;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Property;

@Entity("video")
public class VideoDO extends BaseDO {
	
	@Property("key")
	private String key;
	@Property("srcPath")
	private String srcPath;
	@Property("previewPath")
	private String previewPath;
	
	public VideoDO() {
		setKey("");
		setSrcPath("");
		setPreviewPath("");
	}
	
	public VideoDO(String key, String srcPath) {
		this();
		setKey(key);
		setSrcPath(srcPath);
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getSrcPath() {
		return srcPath;
	}

	public void setSrcPath(String srcPath) {
		this.srcPath = srcPath;
	}

	public String getPreviewPath() {
		return previewPath;
	}

	public void setPreviewPath(String previewPath) {
		this.previewPath = previewPath;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		result = prime * result + ((previewPath == null) ? 0 : previewPath.hashCode());
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
		VideoDO other = (VideoDO) obj;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		if (previewPath == null) {
			if (other.previewPath != null)
				return false;
		} else if (!previewPath.equals(other.previewPath))
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
		return "VideoDO [key=" + key + ", srcPath=" + srcPath + ", previewPath=" + previewPath + "]";
	}

}
