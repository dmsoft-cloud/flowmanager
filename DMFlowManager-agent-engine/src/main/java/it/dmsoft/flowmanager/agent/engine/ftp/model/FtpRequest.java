package it.dmsoft.flowmanager.agent.engine.ftp.model;

import it.dmsoft.flowmanager.agent.engine.utility.ftp.FtpParameters;

public class FtpRequest {
	private String localPath;
	private String remotePath;
	private String remoteFile;
	private boolean removeAfter;
	private FtpParameters ftpParameters; 

	public FtpRequest() {
		super();
	}

	public String getLocalPath() {
		return localPath;
	}

	public void setLocalPath(String localPath) {
		this.localPath = localPath;
	}

	public String getRemotePath() {
		return remotePath;
	}

	public void setRemotePath(String remotePath) {
		this.remotePath = remotePath;
	}

	public FtpParameters getFtpParameters() {
		return ftpParameters;
	}

	public void setFtpParameters(FtpParameters ftpParameters) {
		this.ftpParameters = ftpParameters;
	}

	public boolean isRemoveAfter() {
		return removeAfter;
	}

	public void setRemoveAfter(boolean removeAfter) {
		this.removeAfter = removeAfter;
	}

	public String getRemoteFile() {
		return remoteFile;
	}

	public void setRemoteFile(String remoteFile) {
		this.remoteFile = remoteFile;
	}

	@Override
	public String toString() {
		return "FtpRequest [localPath=" + localPath + ", remotePath=" + remotePath + ", remoteFile=" + remoteFile + ", removeAfter=" + removeAfter + ", ftpParameters=" + ftpParameters + "]";
	}
	
	
}
