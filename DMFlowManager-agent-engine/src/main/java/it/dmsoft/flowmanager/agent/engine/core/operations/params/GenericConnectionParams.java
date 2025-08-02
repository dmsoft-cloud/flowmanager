package it.dmsoft.flowmanager.agent.engine.core.operations.params;

import java.math.BigDecimal;
import java.util.Objects;

import it.dmsoft.flowmanager.common.domain.Domains.DbType;
import it.dmsoft.flowmanager.common.domain.Domains.YesNo;

public class GenericConnectionParams {

	private String jobd;

	private String jobdLibrary;
	
	private String curlib;
	
	private String host;
	
	private Integer port;

	private DbType dbType;

	private String user;

	private String password;

	private YesNo secure;

	private String jdbcCustomString;
	
	private YesNo IBMi;
	
	public String getJobd() {
		return jobd;
	}

	public void setJobd(String jobd) {
		this.jobd = jobd;
	}

	public String getJobdLibrary() {
		return jobdLibrary;
	}

	public void setJobdLibrary(String jobdLibrary) {
		this.jobdLibrary = jobdLibrary;
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

	public DbType getDbType() {
		return dbType;
	}

	public void setDbType(DbType dbType) {
		this.dbType = dbType;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}
	
	public YesNo isSecure() {
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

	public YesNo isIBMi() {
		return IBMi;
	}

	public void setIBMi(YesNo iBMi) {
		IBMi = iBMi;
	}
	
	public String getCurlib() {
		return curlib;
	}

	public void setCurlib(String curlib) {
		this.curlib = curlib;
	}

	@Override
	public int hashCode() {
		return Objects.hash(IBMi, curlib, dbType, host, jdbcCustomString, jobd, jobdLibrary, password, port, secure,
				user);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GenericConnectionParams other = (GenericConnectionParams) obj;
		return IBMi == other.IBMi && Objects.equals(curlib, other.curlib) && dbType == other.dbType
				&& Objects.equals(host, other.host) && Objects.equals(jdbcCustomString, other.jdbcCustomString)
				&& Objects.equals(jobd, other.jobd) && Objects.equals(jobdLibrary, other.jobdLibrary)
				&& Objects.equals(password, other.password) && Objects.equals(port, other.port)
				&& secure == other.secure && Objects.equals(user, other.user);
	}

	@Override
	public String toString() {
		return "GenericConnectionParams [jobd=" + jobd + ", jobdLibrary=" + jobdLibrary + ", curlib=" + curlib
				+ ", host=" + host + ", port=" + port + ", dbType=" + dbType + ", user=" + user + ", password="
				+ password + ", secure=" + secure + ", jdbcCustomString=" + jdbcCustomString + ", IBMi=" + IBMi + "]";
	}

}
