package it.dmsoft.flowmanager.be.entities;

import it.dmsoft.flowmanager.be.converter.DirectionConverter;
import it.dmsoft.flowmanager.be.converter.FileFormatConverter;
import it.dmsoft.flowmanager.be.converter.LocaleConverter;
import it.dmsoft.flowmanager.be.converter.TypeConverter;
import it.dmsoft.flowmanager.be.converter.YesNoConverter;
import it.dmsoft.flowmanager.common.domain.Domains.Direction;
import it.dmsoft.flowmanager.common.domain.Domains.FileFormat;
import it.dmsoft.flowmanager.common.domain.Domains.Locale;
import it.dmsoft.flowmanager.common.domain.Domains.Type;
import it.dmsoft.flowmanager.common.domain.Domains.YesNo;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Model {

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

	@Id
	@Column(length = 20)
	private String id;
	@Column(length = 255)
	private String description;
	@Column(length = 2000)
	private String note;
	@Convert(converter = YesNoConverter.class)
	@Column(length = 1)
	private YesNo enabled;
	@Convert(converter = TypeConverter.class)
	@Column(length = 1)
	private Type type;
	@Convert(converter = DirectionConverter.class)
	@Column(length = 1)
	private Direction direction;
	@Convert(converter = YesNoConverter.class)
	@Column(length = 1)
	private YesNo decompression;
	@Convert(converter = YesNoConverter.class)
	@Column(length = 1)
	private YesNo compression;
	@Convert(converter = YesNoConverter.class)
	@Column(length = 1)
	private YesNo sendMail;
	private Integer retry;
	private Integer retryInterval;
	private Integer retention;
	@Convert(converter = LocaleConverter.class)
	@Column(length = 2)
	private Locale internationalization;

	@Convert(converter = YesNoConverter.class)
	@Column(length = 1)
	private YesNo deleteFile;
	@Convert(converter = YesNoConverter.class)
	@Column(length = 1)
	private YesNo uniqueHash;
	private Integer fetchSize;
	@Column(length = 64)
	private String db;
	@Column(length = 64)
	private String dbSchema;
	@Column(length = 10)
	private String sourceCharset;
	@Column(length = 10)
	private String destCharset;
	@Convert(converter = FileFormatConverter.class)
	@Column(length = 6)
	private FileFormat fileFormat;
	@Convert(converter = YesNoConverter.class)
	@Column(length = 1)
	private YesNo header;
	@Column(length = 6)
	private String recordDelimiter;
	@Column(length = 6)
	private String fieldDelimiter;
	@Column(length = 9)
	private String stringDelimiter;

	@Column(length = 9)
	private String removingSpaces;
	@Column(length = 6)
	private String numericFilling;
	@Convert(converter = YesNoConverter.class)
	@Column(length = 1)
	private YesNo integrityCheck;
	@Convert(converter = YesNoConverter.class)
	@Column(length = 1)
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

	public String getDb() {
		return db;
	}

	public void setDb(String db) {
		this.db = db;
	}

	public String getDbSchema() {
		return dbSchema;
	}

	public void setDbSchema(String dbSchema) {
		this.dbSchema = dbSchema;
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
