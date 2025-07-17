package it.dmsoft.flowmanager.agent.engine.utility.ftp;

public class FtpParameters {
	private String ftpHost;
	private int ftpPort;
	private String ftpUser;
	private String ftpPassword;
	private boolean passiveMode;

	/**
	 * Adoperato solo da GestoreFlussi
	 */
	private boolean secureConnection;

	public FtpParameters() {
		super();
	}

	public String getFtpHost() {
		return ftpHost;
	}

	public void setFtpHost(String sftpHost) {
		this.ftpHost = sftpHost;
	}

	public int getFtpPort() {
		return ftpPort;
	}

	public void setFtpPort(int sftpPort) {
		this.ftpPort = sftpPort;
	}

	public String getFtpUser() {
		return ftpUser;
	}

	public void setFtpUser(String ftpUser) {
		this.ftpUser = ftpUser;
	}

	public String getFtpPassword() {
		return ftpPassword;
	}

	public void setFtpPassword(String sftpPassword) {
		this.ftpPassword = sftpPassword;
	}

	public void setPassiveMode(boolean passiveMode) {
		this.passiveMode = passiveMode;
	}

	public boolean isPassiveMode() {
		return passiveMode;
	}

	@Override
	public String toString() {
		return "FtpParameters [ftpHost="
				+ ftpHost
				+ ", ftpPort="
				+ ftpPort
				+ ", ftpUser="
				+ ftpUser
				+ ", ftpPassword="
				+ ftpPassword
				+ ", passiveMode="
				+ passiveMode
				+ ", secureConnection="
				+ secureConnection
				+ "]";
	}

	public boolean isSecureConnection() {
		return secureConnection;
	}

	public void setSecureConnection(boolean secureConnection) {
		this.secureConnection = secureConnection;
	}
}
