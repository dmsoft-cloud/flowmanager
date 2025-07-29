package it.dmsoft.flowmanager.agent.api.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import it.dmsoft.flowmanager.common.domain.Domains.YesNo;

@Service("agentPropertiesService")
public class AgentPropertiesService {

	@Value("${flowmanager.agent.properties.logPath}")
	private String logPath;
	@Value("${flowmanager.agent.properties.logLevel}")
	private String logLevel;
	@Value("${flowmanager.agent.properties.logSizeMB}")
	private String logSizeMB;
	@Value("${flowmanager.agent.properties.logRotation}")
	private String logRotation;
	@Value("${flowmanager.agent.properties.backupPath}")
	private String backupPath;
	@Value("${flowmanager.agent.properties.mailFrom}")
	private String mailFrom;
	@Value("${flowmanager.agent.properties.customer}")
	private String customer;
	private String executionDateStr;
	@Value("${flowmanager.agent.properties.legacyModernization}")
	private YesNo legacyModernization;
	private String extJob;
	private String extUser;
	private String extNumber;
	private String extTask;

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

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public String getExecutionDateStr() {
		return executionDateStr;
	}

	public void setExecutionDateStr(String executionDateStr) {
		this.executionDateStr = executionDateStr;
	}

	public YesNo getLegacyModernization() {
		return legacyModernization;
	}

	public void setLegacyModernization(YesNo legacyModernization) {
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

}
