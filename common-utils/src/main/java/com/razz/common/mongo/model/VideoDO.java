package com.razz.common.mongo.model;

public class VideoDO extends BaseMongoDO {
	
	public static enum Status {
		unknown, 
		downloading;
		
		public static Status parse(String str) {
			for(Status s : Status.values()) {
				if(s.name().equalsIgnoreCase(str.trim())) {
					return s;
				}
			}
			return null;
		}
	}
	
	private String path;
	private String status;
	
	public VideoDO() {
		setPath(null);
		setStatus(null);
	}
	
	public String getPath() {
		return path;
	}
	
	public void setPath(String path) {
		this.path = path;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((path == null) ? 0 : path.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
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
		if (path == null) {
			if (other.path != null)
				return false;
		} else if (!path.equals(other.path))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "VideoDO [path=" + path + ", status=" + status + "]";
	}
	

}
