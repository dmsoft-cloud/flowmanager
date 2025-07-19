package it.dmsoft.flowmanager.agent.be.entities;


public class FlowConfig {
	private String transactionName;
	private String transactionId;
	private String logPath;
	private String logLevel;
	private String logSizeMB ;
	private String logRotation;
	private String backupPath;
	private String mailFrom;
	private String cliente ;
	private String executionDateStr;
	private String legacyModernization;
	private String extJob;
	private String extUser;
	private String extNumber;
	private String extTask;
	private String masterdataOverrides;
	
	
	
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
	public String getLogPath() {
		return logPath;
	}
	public void setLogPath(String logPath) {
		this.logPath = logPath;
	}
	public String getLogLevel() {
		return logLevel;
	}
	public void setLogLevel(String logLevel) {
		this.logLevel = logLevel;
	}
	public String getLogSizeMB() {
		return logSizeMB;
	}
	public void setLogSizeMB(String logSizeMB) {
		this.logSizeMB = logSizeMB;
	}
	public String getLogRotation() {
		return logRotation;
	}
	public void setLogRotation(String logRotation) {
		this.logRotation = logRotation;
	}
	public String getBackupPath() {
		return backupPath;
	}
	public void setBackupPath(String backupPath) {
		this.backupPath = backupPath;
	}
	public String getMailFrom() {
		return mailFrom;
	}
	public void setMailFrom(String mailFrom) {
		this.mailFrom = mailFrom;
	}
	public String getCliente() {
		return cliente;
	}
	public void setCliente(String cliente) {
		this.cliente = cliente;
	}
	public String getExecutionDateStr() {
		return executionDateStr;
	}
	public void setExecutionDateStr(String executionDateStr) {
		this.executionDateStr = executionDateStr;
	}
	public String getMasterdataOverrides() {
		return masterdataOverrides;
	}
	public void setMasterdataOverrides(String masterdataOverrides) {
		this.masterdataOverrides = masterdataOverrides;
	}
	
	public String getLegacyModernization() {
		return legacyModernization;
	}
	public void setLegacyModernization(String legacyModernization) {
		this.legacyModernization = legacyModernization;
	}
	
	public String getExtJob() {
		return extJob;
	}
	public void setExtJob(String extJob) {
		this.extJob = extJob;
	}
	public String getExtUser() {
		return extUser;
	}
	public void setExtUser(String extUser) {
		this.extUser = extUser;
	}
	public String getExtNumber() {
		return extNumber;
	}
	public void setExtNumber(String extNumber) {
		this.extNumber = extNumber;
	}
	public String getExtTask() {
		return extTask;
	}
	public void setExtTask(String extTask) {
		this.extTask = extTask;
	}
	@Override
	public String toString() {
		return "ConfigDto [transactionName=" + transactionName + ", transactionId=" + transactionId + ", logPath="
				+ logPath + ", logLevel=" + logLevel + ", logSizeMB=" + logSizeMB + ", logRotation=" + logRotation
				+ ", backupPath=" + backupPath + ", mailFrom=" + mailFrom + ", cliente=" + cliente
				+ ", executionDateStr=" + executionDateStr + ", legacyModernization=" + legacyModernization
				+ ", extJob=" + extJob + ", extUser=" + extUser + ", extNumber=" + extNumber + ", extTask=" + extTask
				+ ", masterdataOverrides=" + masterdataOverrides + "]";
	}
	
	
	
}
