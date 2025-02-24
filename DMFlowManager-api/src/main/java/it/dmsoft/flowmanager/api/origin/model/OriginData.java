package it.dmsoft.flowmanager.api.origin.model;

import it.dmsoft.flowmanager.be.common.BaseEntity.DbType;
import it.dmsoft.flowmanager.be.common.BaseEntity.YesNo;

public class OriginData {

	// IP
	// PORTA
	// NOME
	// DESCRIZIONE
	// NOTE
	// ATTIVO
	// TIPOLOGIA DB
	// USER
	// PASSWORD
	// SECURE
	// JDBC CUSTOM STRING

	private String id;
	private String ip;
	private Integer port;
	private String description;
	private String note;
	private YesNo enabled;
	private DbType dbType;
	private String user;
	private String password;
	private YesNo secure;
	private String jdbcCustomString;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
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

	public DbType getDbType() {
		return dbType;
	}

	public void setDbType(DbType dbType) {
		this.dbType = dbType;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public YesNo getSecure() {
		return secure;
	}

	public void setSecure(YesNo secure) {
		this.secure = secure;
	}

	public String getJdbcCustomString() {
		return jdbcCustomString;
	}

	public void setJdbcCustomString(String jdbcCustomString) {
		this.jdbcCustomString = jdbcCustomString;
	}

}
