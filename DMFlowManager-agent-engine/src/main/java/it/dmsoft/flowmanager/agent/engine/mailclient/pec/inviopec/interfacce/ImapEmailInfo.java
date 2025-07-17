package it.dmsoft.flowmanager.agent.engine.mailclient.pec.inviopec.interfacce;

public class ImapEmailInfo {
	private String pathMail = "";
	private String fileMail = "";

	public ImapEmailInfo(String pathMail, String fileMail) {
		super();
		this.pathMail = pathMail;
		this.fileMail = fileMail;
	}

	public ImapEmailInfo() {

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

}
