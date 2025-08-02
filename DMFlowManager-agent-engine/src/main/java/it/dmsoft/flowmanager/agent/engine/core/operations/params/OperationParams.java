package it.dmsoft.flowmanager.agent.engine.core.operations.params;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import it.dmsoft.flowmanager.agent.engine.core.utils.FormatUtils;
import it.dmsoft.flowmanager.common.domain.Domains.YesNo;

public class OperationParams {

	private BigDecimal transactionId;
	private String transactionName;
	private String pathBackup;
	private String trasmissionFolder;
	private String backupFolder;
	private String library;
	private String tmpLibrary;
	private String memberOptionAddReplace;
	private String outcome;
	//private String mailAccount;
	private List<String> trasmissionFiles;
	private List<String> remoteTrasmissionFiles;
	private List<String> fileNames;
	private List<String> backupFiles;
	private List<String> zipFiles;
	private List<String> deleteFiles;
	private List<String> checkedFiles;
	private String listFile;
	private BigDecimal dayStartProgressiveIndex;
	private String sempahoreFile;
	private BigDecimal scheduleDate;
	private boolean bypassConversion = false;
	private boolean skipCpyFrmFile = false;
	private String listFileFolder;
	private Date executionDate = FormatUtils.todayDate();
	private List<String> overrideMailDests;
	private String overrideMailText;
	private YesNo ibmi;
	private YesNo exportFileHeaders;
	private String exportTempSchema;
	private String exportTempTable;
	private String externalJob;
	private String externalUser;
	private BigDecimal externalNumber;
	private String externalTask;


	public BigDecimal getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(BigDecimal transactionId) {
		this.transactionId = transactionId;
	}

	public String getPathBackup() {
		return pathBackup;
	}

	public void setPathBackup(String pathBackup) {
		this.pathBackup = pathBackup;
	}

	public String getTrasmissionFolder() {
		return trasmissionFolder;
	}

	public void setTrasmissionFolder(String trasmissionFolder) {
		this.trasmissionFolder = trasmissionFolder;
	}

	public List<String> getTrasmissionFiles() {
		return trasmissionFiles;
	}

	public void setTrasmissionFiles(List<String> trasmissionFiles) {
		this.trasmissionFiles = trasmissionFiles;
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

	public String getLibrary() {
		return library;
	}

	public void setLibrary(String library) {
		this.library = library;
	}

	public String getMemberOptionAddReplace() {
		return memberOptionAddReplace;
	}

	public void setMemberOptionAddReplace(String memberOptionAddReplace) {
		this.memberOptionAddReplace = memberOptionAddReplace;
	}

	public String getOutcome() {
		return outcome;
	}

	public void setOutcome(String outcome) {
		this.outcome = outcome;
	}

	/*
	public String getMailAccount() {
		return mailAccount;
	}

	public void setMailAccount(String mailAccount) {
		this.mailAccount = mailAccount;
	}
	*/
	
	public String getTransactionName() {
		return transactionName;
	}

	public void setTransactionName(String transactionName) {
		this.transactionName = transactionName;
	}

	public List<String> getFileNames() {
		return fileNames;
	}

	public void setFileNames(List<String> fileNames) {
		this.fileNames = fileNames;
	}
	
	public List<String> getZipFiles() {
		return zipFiles;
	}

	public void setZipFiles(List<String> zipFiles) {
		this.zipFiles = zipFiles;
	}
	
	public List<String> getDeleteFiles() {
		return deleteFiles;
	}

	public void setDeleteFiles(List<String> deleteFiles) {
		this.deleteFiles = deleteFiles;
	}

	public String getListFile() {
		return listFile;
	}

	public void setListFile(String listFile) {
		this.listFile = listFile;
	}
	
	public List<String> getRemoteTrasmissionFiles() {
		return remoteTrasmissionFiles;
	}

	public void setRemoteTrasmissionFiles(List<String> remoteTrasmissionFiles) {
		this.remoteTrasmissionFiles = remoteTrasmissionFiles;
	}
	
	public BigDecimal getDayStartProgressiveIndex() {
		return dayStartProgressiveIndex;
	}

	public void setDayStartProgressiveIndex(BigDecimal dayStartProgressiveIndex) {
		this.dayStartProgressiveIndex = dayStartProgressiveIndex;
	}
	
	public String getSempahoreFile() {
		return sempahoreFile;
	}

	public void setSempahoreFile(String sempahoreFile) {
		this.sempahoreFile = sempahoreFile;
	}
	
	public BigDecimal getScheduleDate() {
		return scheduleDate;
	}

	public void setScheduleDate(BigDecimal scheduleDate) {
		this.scheduleDate = scheduleDate;
	}
	
	public boolean isBypassConversion() {
		return bypassConversion;
	}

	public void setBypassConversion(boolean bypassConversion) {
		this.bypassConversion = bypassConversion;
	}
	
	public String getListFileFolder() {
		return listFileFolder;
	}

	public void setListFileFolder(String listFileFolder) {
		this.listFileFolder = listFileFolder;
	}
	
	public Date getExecutionDate() {
		return executionDate;
	}

	public void setExecutionDate(Date executionDate) {
		this.executionDate = executionDate;
	}


	public boolean getSkipCpyFrmFile() {
		return skipCpyFrmFile;
	}

	public void setSkipCpyFrmFile(boolean skipCpyFrmFile) {
		this.skipCpyFrmFile = skipCpyFrmFile;
	}

	public List<String> getCheckedFiles() {
		return checkedFiles;
	}

	public void setCheckedFiles(List<String> checkedFiles) {
		this.checkedFiles = checkedFiles;
	}

	
	public List<String> getOverrideMailDests() {
		return overrideMailDests;
	}

	public void setOverrideMailDests(List<String> overrideMailDests) {
		this.overrideMailDests = overrideMailDests;
	}

	
	public String getOverrideMailText() {
		return overrideMailText;
	}

	public void setOverrideMailText(String overrideMailText) {
		this.overrideMailText = overrideMailText;
	}
	

	public YesNo isIBMi() {
		return ibmi;
	}

	public void setIBMi(YesNo ibmi) {
		this.ibmi = ibmi;
	}

	public YesNo getExportFileHeaders() {
		return exportFileHeaders;
	}

	public void setExportFileHeaders(YesNo exportFileHeaders) {
		this.exportFileHeaders = exportFileHeaders;
	}

	
	public String getExportTempSchema() {
		return exportTempSchema;
	}

	public void setExportTempSchema(String exportTempSchema) {
		this.exportTempSchema = exportTempSchema;
	}

	public String getExportTempTable() {
		return exportTempTable;
	}

	public void setExportTempTable(String exportTempTable) {
		this.exportTempTable = exportTempTable;
	}

	public String getTmpLibrary() {
		return tmpLibrary;
	}

	public void setTmpLibrary(String tmpLibrary) {
		this.tmpLibrary = tmpLibrary;
	}

	
	public String getExternalJob() {
		return externalJob;
	}

	public void setExternalJob(String externalJob) {
		this.externalJob = externalJob;
	}

	public String getExternalUser() {
		return externalUser;
	}

	public void setExternalUser(String externalUser) {
		this.externalUser = externalUser;
	}

	public BigDecimal getExternalNumber() {
		return externalNumber;
	}

	public void setExternalNumber(BigDecimal externalNumber) {
		this.externalNumber = externalNumber;
	}

	public String getExternalTask() {
		return externalTask;
	}

	public void setExternalTask(String externalTask) {
		this.externalTask = externalTask;
	}

	@Override
	public String toString() {
		return "OperationParams [transactionId=" + transactionId + ", transactionName=" + transactionName
				+ ", pathBackup=" + pathBackup + ", trasmissionFolder=" + trasmissionFolder + ", backupFolder="
				+ backupFolder + ", library=" + library + ", tmpLibrary=" + tmpLibrary + ", memberOptionAddReplace="
				+ memberOptionAddReplace + ", outcome=" + outcome
				+ ", trasmissionFiles=" + trasmissionFiles + ", remoteTrasmissionFiles=" + remoteTrasmissionFiles
				+ ", fileNames=" + fileNames + ", backupFiles=" + backupFiles + ", zipFiles=" + zipFiles
				+ ", deleteFiles=" + deleteFiles + ", checkedFiles=" + checkedFiles + ", listFile=" + listFile
				+ ", dayStartProgressiveIndex=" + dayStartProgressiveIndex + ", sempahoreFile=" + sempahoreFile
				+ ", scheduleDate=" + scheduleDate + ", bypassConversion=" + bypassConversion + ", skipCpyFrmFile="
				+ skipCpyFrmFile + ", listFileFolder=" + listFileFolder + ", executionDate=" + executionDate
				+ ", overrideMailDests=" + overrideMailDests + ", overrideMailText=" + overrideMailText
				+ ", IBMi=" + ibmi + ", exportFileHeaders=" + exportFileHeaders
				+ ", exportTempSchema=" + exportTempSchema + ", exportTempTable=" + exportTempTable + ", externalJob="
				+ externalJob + ", externalUser=" + externalUser + ", externalNumber=" + externalNumber
				+ ", externalTask=" + externalTask + "]";
	}

	
	
}
