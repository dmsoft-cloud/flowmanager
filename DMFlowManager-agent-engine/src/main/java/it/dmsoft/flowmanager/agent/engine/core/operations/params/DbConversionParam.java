package it.dmsoft.flowmanager.agent.engine.core.operations.params;

import java.math.BigDecimal;
import java.util.List;

import it.dmsoft.flowmanager.common.domain.Domains.YesNo;

public class DbConversionParam extends GenericConnectionParams{
	
	private String id;

	private String library;
	
	private String file;
	
	private String member;
	
	private String folderIfs;
	
	private String fileNameIfs;
	
	private String recordDelimiter;
	
	private String fieldDelimiter;
	
	private String stringDelimiter;
	
	private BigDecimal codepage;
	
	private String memberOptionAddReplace;
	
	private String removeBlanks;
	
	private BigDecimal fromCcsid;
	
	private String columnName;

	private String decimalPointer;
	
	private String tabexpn;
	
	private String fieldFilling;
	
	private String replaceNullVal;
	
	private YesNo removeColName;
	
	private String internazDelimiter;
	
	private List<String> downloadedFiles;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDecimalPointer() {
		return decimalPointer;
	}

	public void setDecimalPointer(String format) {
		this.decimalPointer = format;
	}


	public String getLibrary() {
		return library;
	}

	public void setLibrary(String library) {
		this.library = library;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public String getMember() {
		return member;
	}

	public void setMember(String member) {
		this.member = member;
	}

	public String getFolderIfs() {
		return folderIfs;
	}

	public void setFolderIfs(String folderIfs) {
		this.folderIfs = folderIfs;
	}

	public String getFileNameIfs() {
		return fileNameIfs;
	}

	public void setFileNameIfs(String fileNameIfs) {
		this.fileNameIfs = fileNameIfs;
	}

	public String getRecordDelimiter() {
		return recordDelimiter;
	}

	public void setRecordDelimiter(String recordDelimiter) {
		this.recordDelimiter = recordDelimiter;
	}

	public String getFieldDelimiter() {
		return fieldDelimiter;
	}

	public void setFieldDelimiter(String fieldDelimiter) {
		this.fieldDelimiter = fieldDelimiter;
	}

	public String getStringDelimiter() {
		return stringDelimiter;
	}

	public void setStringDelimiter(String stringDelimiter) {
		this.stringDelimiter = stringDelimiter;
	}

	public BigDecimal getCodepage() {
		return codepage;
	}

	public void setCodepage(BigDecimal codepage) {
		this.codepage = codepage;
	}
	
	public String getMemberOptionAddReplace() {
		return memberOptionAddReplace;
	}

	public void setMemberOptionAddReplace(String memberOptionAddReplace) {
		this.memberOptionAddReplace = memberOptionAddReplace;
	}

	public String getRemoveBlanks() {
		return removeBlanks;
	}

	public void setRemoveBlanks(String removeBlanks) {
		this.removeBlanks = removeBlanks;
	}

	public BigDecimal getFromCcsid() {
		return fromCcsid;
	}

	public void setFromCcsid(BigDecimal fromCcsid) {
		this.fromCcsid = fromCcsid;
	}
	
	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	
	public String getTabexpn() {
		return tabexpn;
	}

	public void setTabexpn(String tabexpn) {
		this.tabexpn = tabexpn;
	}
	
	public String getFieldFilling() {
		return fieldFilling;
	}

	public void setFieldFilling(String fieldFilling) {
		this.fieldFilling = fieldFilling;
	}
	
	public String getReplaceNullVal() {
		return replaceNullVal;
	}

	public void setReplaceNullVal(String replaceNullVal) {
		this.replaceNullVal = replaceNullVal;
	}
	
	public YesNo getRemoveColName() {
		return removeColName;
	}

	public void setRemoveColName(YesNo removeColName) {
		this.removeColName = removeColName;
	}
	
	public String getInternazDelimiter() {
		return internazDelimiter;
	}

	public void setInternazDelimiter(String internazDelimiter) {
		this.internazDelimiter = internazDelimiter;
	}

	public List<String> getDownloadedFiles() {
		return downloadedFiles;
	}

	public void setDownloadedFiles(List<String> downloadedFiles) {
		this.downloadedFiles = downloadedFiles;
	}

	@Override
	public String toString() {
		return "DbConversionParam [id=" + id + ", library=" + library + ", file=" + file + ", member=" + member
				+ ", folderIfs=" + folderIfs + ", fileNameIfs=" + fileNameIfs + ", recordDelimiter=" + recordDelimiter
				+ ", fieldDelimiter=" + fieldDelimiter + ", stringDelimiter=" + stringDelimiter + ", codepage="
				+ codepage + ", memberOptionAddReplace=" + memberOptionAddReplace + ", removeBlanks=" + removeBlanks
				+ ", fromCcsid=" + fromCcsid + ", columnName=" + columnName + ", decimalPointer=" + decimalPointer
				+ ", tabexpn=" + tabexpn + ", fieldFilling=" + fieldFilling + ", replaceNullVal=" + replaceNullVal
				+ ", removeColName=" + removeColName + ", internazDelimiter=" + internazDelimiter + ", downloadedFiles="
				+ downloadedFiles + "]";
	}

}
