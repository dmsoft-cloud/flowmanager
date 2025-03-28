package it.dmsoft.flowmanager.api.model.model;

import it.dmsoft.flowmanager.api.base.BaseModel;
import it.dmsoft.flowmanager.be.common.BaseEntity.Direction;
import it.dmsoft.flowmanager.be.common.BaseEntity.FileFormat;
import it.dmsoft.flowmanager.be.common.BaseEntity.Locale;
import it.dmsoft.flowmanager.be.common.BaseEntity.Type;
import it.dmsoft.flowmanager.be.common.BaseEntity.YesNo;

public class ModelData extends BaseModel {

	// NOME
	// DESCRIZIONE
	// NOTE
	// ATTIVO
	// TIPOLOGIA TRASFERIMENTO ORIGIN/FS
	// DIREZIONE I/O
	// DECOMPRESSIONE S/N
	// COMPRESSIONE S/N/I
	// SEND MAIL S/N
	// NUMERI TENTATIVI
	// INTERVALLO DI RETRY
	// RETENTION
	// INTERNAZIONALIZZAZIONE
	// CANCELLA FILE
	// HASH UNIVOCO
	// FETCH SIZE
	// DATABASE
	// SCHEMA
	// SOURCE CHARSET
	// DESTINATION CHARSET
	// FORMAT CSV FIXED
	// TESTATA
	// DELIMITATORE RECORD
	// DELIMITATORE CAMPO
	// DELIMITATORE STRINGA
	// RIMOZIONE SPAZI (DA RECUPERARE DA AS)
	// RIEMPIMENTO NUMERICO 0 o spazi
	// INTEGRITY CHECK
	// CREA SE NON PRESENTE
	// No Space char  *****da aggiungere

	private String id;
	private String description;
	private String note;
	private YesNo enabled;
	private Type type;
	private Direction direction;
	private YesNo decompression;
	private YesNo compression;
	private YesNo sendMail;
	private Integer retry;
	private Integer retryInterval;
	private Integer retention;
	private Locale internationalization;
	private YesNo deleteFile;
	private YesNo uniqueHash;
	private Integer fetchSize;
	private String database;
	private String schema;
	private String sourceCharset;
	private String destCharset;
	private FileFormat fileFormat;
	private YesNo header;
	private String recordDelimiter;
	private String fieldDelimiter;
	private String stringDelimiter;
	private String removingSpaces;
	private String numericFilling;
	private YesNo integrityCheck;
	private YesNo createFile;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public YesNo getEnabled() {
		return enabled;
	}

	public void setEnabled(YesNo enabled) {
		this.enabled = enabled;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public YesNo getDecompression() {
		return decompression;
	}

	public void setDecompression(YesNo decompression) {
		this.decompression = decompression;
	}

	public YesNo getCompression() {
		return compression;
	}

	public void setCompression(YesNo compression) {
		this.compression = compression;
	}

	public YesNo getSendMail() {
		return sendMail;
	}

	public void setSendMail(YesNo sendMail) {
		this.sendMail = sendMail;
	}

	public Integer getRetry() {
		return retry;
	}

	public void setRetry(Integer retry) {
		this.retry = retry;
	}

	public Integer getRetryInterval() {
		return retryInterval;
	}

	public void setRetryInterval(Integer retryInterval) {
		this.retryInterval = retryInterval;
	}

	public Integer getRetention() {
		return retention;
	}

	public void setRetention(Integer retention) {
		this.retention = retention;
	}

	public Locale getInternationalization() {
		return internationalization;
	}

	public void setInternationalization(Locale internationalization) {
		this.internationalization = internationalization;
	}

	public YesNo getDeleteFile() {
		return deleteFile;
	}

	public void setDeleteFile(YesNo deleteFile) {
		this.deleteFile = deleteFile;
	}

	public YesNo getUniqueHash() {
		return uniqueHash;
	}

	public void setUniqueHash(YesNo uniqueHash) {
		this.uniqueHash = uniqueHash;
	}

	public Integer getFetchSize() {
		return fetchSize;
	}

	public void setFetchSize(Integer fetchSize) {
		this.fetchSize = fetchSize;
	}

	public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		this.database = database;
	}

	public String getSchema() {
		return schema;
	}

	public void setSchema(String schema) {
		this.schema = schema;
	}

	public String getSourceCharset() {
		return sourceCharset;
	}

	public void setSourceCharset(String sourceCharset) {
		this.sourceCharset = sourceCharset;
	}

	public String getDestCharset() {
		return destCharset;
	}

	public void setDestCharset(String destCharset) {
		this.destCharset = destCharset;
	}

	public FileFormat getFileFormat() {
		return fileFormat;
	}

	public void setFileFormat(FileFormat fileFormat) {
		this.fileFormat = fileFormat;
	}

	public YesNo getHeader() {
		return header;
	}

	public void setHeader(YesNo header) {
		this.header = header;
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

	public String getRemovingSpaces() {
		return removingSpaces;
	}

	public void setRemovingSpaces(String removingSpaces) {
		this.removingSpaces = removingSpaces;
	}

	public String getNumericFilling() {
		return numericFilling;
	}

	public void setNumericFilling(String numericFilling) {
		this.numericFilling = numericFilling;
	}

	public YesNo getIntegrityCheck() {
		return integrityCheck;
	}

	public void setIntegrityCheck(YesNo integrityCheck) {
		this.integrityCheck = integrityCheck;
	}

	public YesNo getCreateFile() {
		return createFile;
	}

	public void setCreateFile(YesNo createFile) {
		this.createFile = createFile;
	}

}
