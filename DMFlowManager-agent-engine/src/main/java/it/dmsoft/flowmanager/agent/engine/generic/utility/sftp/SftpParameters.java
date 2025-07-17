package it.dmsoft.flowmanager.agent.engine.generic.utility.sftp;

public class SftpParameters {
	private String knownHostsPath;
	private String hostKeyAlias;
	private String identityPath;
	private String identityPassword;
	private String sftpHost;
	private int sftpPort;
	private String sftpPassword;
	
	public SftpParameters() {
		super();
	}

	public String getKnownHostsPath() {
		return knownHostsPath;
	}

	public void setKnownHostsPath(String knownHostsPath) {
		this.knownHostsPath = knownHostsPath;
	}

	public String getHostKeyAlias() {
		return hostKeyAlias;
	}

	public void setHostKeyAlias(String hostKeyAlias) {
		this.hostKeyAlias = hostKeyAlias;
	}

	public String getIdentityPath() {
		return identityPath;
	}

	public void setIdentityPath(String identityPath) {
		this.identityPath = identityPath;
	}

	public String getIdentityPassword() {
		return identityPassword;
	}

	public void setIdentityPassword(String identityPassword) {
		this.identityPassword = identityPassword;
	}

	public String getSftpHost() {
		return sftpHost;
	}

	public void setSftpHost(String sftpHost) {
		this.sftpHost = sftpHost;
	}

	public int getSftpPort() {
		return sftpPort;
	}

	public void setSftpPort(int sftpPort) {
		this.sftpPort = sftpPort;
	}
	
	public String getSftpPassword() {
		return sftpPassword;
	}

	public void setSftpPassword(String sftpPassword) {
		this.sftpPassword = sftpPassword;
	}

	@Override
	public String toString() {
		return "SftpParameters [knownHostsPath=" + knownHostsPath + ", hostKeyAlias=" + hostKeyAlias + ", identityPath=" + identityPath + ", identityPassword=" + identityPassword + ", sftpHost="
				+ sftpHost + ", sftpPort=" + sftpPort + ", sftpPassword=" + sftpPassword + "]";
	}
}
