package it.dmsoft.flowmanager.agent.be.entities;

import it.dmsoft.flowmanager.agent.be.converter.DbTypeConverter;
import it.dmsoft.flowmanager.agent.be.converter.YesNoConverter;
import it.dmsoft.flowmanager.common.domain.Domains.DbType;
import it.dmsoft.flowmanager.common.domain.Domains.YesNo;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Origin {

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

	@Id
	@Column(length = 20)
	private String id;
	@Column(length = 64)
	private String ip;
	private Integer port;
	@Column(length = 255)
	private String description;
	@Column(length = 2000)
	private String note;
	@Convert(converter = YesNoConverter.class)
	@Column(length = 1)
	private YesNo enabled;
	@Convert(converter = DbTypeConverter.class)
	@Column(length = 10)
	private DbType dbType;
	@Column(length = 64)
	private String user;
	@Column(length = 64)
	private String password;
	@Convert(converter = YesNoConverter.class)
	@Column(length = 1)
	private YesNo secure;
	@Column(length = 255)
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
