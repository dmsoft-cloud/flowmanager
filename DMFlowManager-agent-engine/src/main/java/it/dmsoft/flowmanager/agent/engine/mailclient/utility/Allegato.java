package it.dmsoft.flowmanager.agent.engine.mailclient.utility;

public class Allegato {
	private String path;
	private String contentId;

	public String getPath() {
		if (path == null) {
			path = new String();
		}
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getContentId() {
		if (contentId == null) {
			contentId = new String();
		}
		return contentId;
	}

	public void setContentId(String contentId) {
		this.contentId = contentId;
	}

	@Override
	public String toString() {
		return "Allegato [path=" + path + ", contentId=" + contentId + "]";
	}
}
