package it.dmsoft.flowmanager.agent.engine.sftp.model;

public class SftpFileDescription {
	private String filePath;
	private String fileName;

	public SftpFileDescription(String filePath, String fileName) {
		super();
		this.filePath = filePath;
		this.fileName = fileName;
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
