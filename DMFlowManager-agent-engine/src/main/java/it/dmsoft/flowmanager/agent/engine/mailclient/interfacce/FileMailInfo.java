package it.dmsoft.flowmanager.agent.engine.mailclient.interfacce;

public class FileMailInfo {

	private String pathMail = "";
	private String fileMail = "";

	public FileMailInfo(String pathMail, String fileMail) {
		super();
		this.pathMail = pathMail;
		this.fileMail = fileMail;
	}

	public FileMailInfo() {

	}

	public String getPathMail() {
		if (pathMail == null) {
			pathMail = "";
		}
		return pathMail;
	}

	public void setPathMail(String pathMail) {
		this.pathMail = pathMail;
	}

	public String getFileMail() {
		if (fileMail == null) {
			fileMail = "";
		}
		return fileMail;
	}

	public void setFileMail(String fileMail) {
		this.fileMail = fileMail;
	}

	@Override
	public String toString() {
		return "FileMailInfo [pathMail=" + pathMail + ", fileMail=" + fileMail + "]";
	}
}
