package it.dmsoft.flowmanager.agent.engine.sftp.model;

import java.util.ArrayList;

public class SftpResponse {
	private ArrayList<SftpInfo> sftpInfo;
	private ArrayList<SftpFileDescription> dowloadedFiles;

	public SftpResponse() {
		super();
		this.sftpInfo = new ArrayList<SftpInfo>();
	}

	public ArrayList<SftpInfo> getSftpInfo() {
		return sftpInfo;
	}

	public void setSftpInfo(ArrayList<SftpInfo> sftpInfo) {
		this.sftpInfo = sftpInfo;
	}

	public ArrayList<SftpFileDescription> getDowloadedFiles() {
		if (dowloadedFiles == null) {
			dowloadedFiles = new ArrayList<SftpFileDescription>();
		}
		return dowloadedFiles;
	}

}
