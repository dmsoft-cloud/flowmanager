package it.dmsoft.flowmanager.agent.engine.ftp.model;

import java.util.ArrayList;
import java.util.List;

public class FtpResponse {
	private List<FtpInfo> ftpInfo;
	private List<FtpFileDescription> dowloadedFiles;

	public FtpResponse() {
		super();
		this.ftpInfo = new ArrayList<FtpInfo>();
	}

	public List<FtpInfo> getFtpInfo() {
		return ftpInfo;
	}

	public void setFtpInfo(List<FtpInfo> sftpInfo) {
		this.ftpInfo = sftpInfo;
	}

	public List<FtpFileDescription> getDowloadedFiles() {
		if (dowloadedFiles == null) {
			dowloadedFiles = new ArrayList<FtpFileDescription>();
		}
		return dowloadedFiles;
	}
}
