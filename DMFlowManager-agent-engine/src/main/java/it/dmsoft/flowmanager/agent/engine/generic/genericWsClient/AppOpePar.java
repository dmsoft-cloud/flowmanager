package it.dmsoft.flowmanager.agent.engine.generic.genericWsClient;

public class AppOpePar {

	private String truststorePath;
	private String protocol;
	private String keyStoreFileName;
	private String truststorePwd;
	private String keyStorePassword;
	private String proxyIP;
	private String proxyPort;
	private String proxyUser;
	private String proxyPass;

	public AppOpePar(String truststorePath, String protocol, String keyStoreFileName, String truststorePwd, String keyStorePassword, String proxyIP, String proxyPort, String proxyUser,
			String proxyPass) {
		super();
		this.truststorePath = truststorePath;
		this.protocol = protocol;
		this.keyStoreFileName = keyStoreFileName;
		this.truststorePwd = truststorePwd;
		this.keyStorePassword = keyStorePassword;
		this.proxyIP = proxyIP;
		this.proxyPort = proxyPort;
		this.proxyUser = proxyUser;
		this.proxyPass = proxyPass;
	}

	public AppOpePar() {
		super();
	}

	public String getTruststorePath() {
		return truststorePath;
	}

	public void setTruststorePath(String truststorePath) {
		this.truststorePath = truststorePath;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getKeyStoreFileName() {
		return keyStoreFileName;
	}

	public void setKeyStoreFileName(String keyStoreFileName) {
		this.keyStoreFileName = keyStoreFileName;
	}

	public String getTruststorePwd() {
		return truststorePwd;
	}

	public void setTruststorePwd(String truststorePwd) {
		this.truststorePwd = truststorePwd;
	}

	public String getKeyStorePassword() {
		return keyStorePassword;
	}

	public void setKeyStorePassword(String keyStorePassword) {
		this.keyStorePassword = keyStorePassword;
	}

	public String getProxyIP() {
		return proxyIP;
	}

	public void setProxyIP(String proxyIP) {
		this.proxyIP = proxyIP;
	}

	public String getProxyPort() {
		return proxyPort;
	}

	public void setProxyPort(String proxyPort) {
		this.proxyPort = proxyPort;
	}

	public String getProxyUser() {
		return proxyUser;
	}

	public void setProxyUser(String proxyUser) {
		this.proxyUser = proxyUser;
	}

	public String getProxyPass() {
		return proxyPass;
	}

	public void setProxyPass(String proxyPass) {
		this.proxyPass = proxyPass;
	}

	@Override
	public String toString() {
		return "AppOpePar [truststorePath=" + truststorePath + ", protocol=" + protocol + ", keyStoreFileName=" + keyStoreFileName + ", truststorePwd=" + truststorePwd + ", keyStorePassword="
				+ keyStorePassword + ", proxyIP=" + proxyIP + ", proxyPort=" + proxyPort + ", proxyUser=" + proxyUser + ", proxyPass=" + proxyPass + "]";
	}

}
