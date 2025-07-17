package it.dmsoft.flowmanager.agent.engine.core.operations.params;

import java.util.List;

public class BackupParam{

	
	String path;
	String transactionName;
	String anno;
	String data;
	String transactionId;
	String backupFolder;
	List<String> backupFiles;
	
	
	
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getTransactionName() {
		return transactionName;
	}
	public void setTransactionName(String transactionName) {
		this.transactionName = transactionName;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public String getAnno() {
		return anno;
	}
	public void setAnno(String anno) {
		this.anno = anno;
	}
	
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getBackupFolder() {
		return backupFolder;
	}
	public void setBackupFolder(String backupFolder) {
		this.backupFolder = backupFolder;
	}
	
	
	public List<String> getBackupFiles() {
		return backupFiles;
	}
	public void setBackupFiles(List<String> backupFiles) {
		this.backupFiles = backupFiles;
	}
	
	@Override
	public String toString() {
		return "BackupParam [path=" + path + ", transactionName=" + transactionName + ", anno=" + anno + ", data="
				+ data + ", transactionId=" + transactionId + ", backupFolder=" + backupFolder + ", backupFile="
				+ backupFiles + "]";
	}
	
	
	
	

	
	
	
	
	
	
	

}
