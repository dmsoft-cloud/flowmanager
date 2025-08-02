package it.dmsoft.flowmanager.agent.engine.core.operations.params;

import it.dmsoft.flowmanager.common.domain.Domains.DbType;
import it.dmsoft.flowmanager.common.domain.Domains.YesNo;

public class CreateFileParam extends GenericConnectionParams {

	private String folder;
	private String fileName;
	private DbType dbType;

	public String getFolder() {
		return folder;
	}

	public void setFolder(String folder) {
		this.folder = folder;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public DbType getDbType() {
		return dbType;
	}

	public void setDbType(DbType dbType) {
		this.dbType = dbType;
	}

	@Override
	public String toString() {
		return "CreateFileParam [folder=" + folder + ", fileName=" + fileName + ", dbType=" + dbType + "]";
	}

}
