package it.dmsoft.flowmanager.agent.engine.ftp.model;

import java.io.File;

public class FtpFileDescription {
	private String filePath;
	private String fileName;

	public FtpFileDescription(String absolutePath) {
		super();
		File f = new File(absolutePath);
		this.filePath = f.getParent();
		this.fileName = f.getName();
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Override
	public String toString() {
		return "SftpFileDescription [filePath=" + filePath + ", fileName=" + fileName + "]";
	}
}
