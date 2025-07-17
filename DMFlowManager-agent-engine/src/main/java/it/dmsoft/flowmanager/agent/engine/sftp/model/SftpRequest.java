package it.dmsoft.flowmanager.agent.engine.sftp.model;

import it.dmsoft.flowmanager.agent.engine.generic.utility.sftp.SftpParameters;

public class SftpRequest {
	private String localPath;
	private String remotePath;
	private String remoteFile;
	private boolean removeAfter;
	private SftpParameters sftpParameters; 

	public SftpRequest() {
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

	public SftpParameters getSftpParameters() {
		return sftpParameters;
	}

	public void setSftpParameters(SftpParameters sftpParameters) {
		this.sftpParameters = sftpParameters;
	}

	public boolean isRemoveAfter() {
		return removeAfter;
	}

	public void setRemoveAfter(boolean removeAfter) {
		this.removeAfter = removeAfter;
	}

	@Override
	public String toString() {
		return "SftpRequest [localPath=" + localPath + ", remotePath=" + remotePath + ", remoteFile=" + remoteFile + ", removeAfter=" + removeAfter + ", sftpParameters=" + sftpParameters + "]";
	}

	public void setRemoteFile(String remoteFile) {
		this.remoteFile = remoteFile;
	}
	
	public String getRemoteFile() {
		return this.remoteFile;
	}
}
