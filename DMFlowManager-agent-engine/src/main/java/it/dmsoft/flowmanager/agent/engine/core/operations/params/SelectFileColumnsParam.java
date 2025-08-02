package it.dmsoft.flowmanager.agent.engine.core.operations.params;

import it.dmsoft.flowmanager.common.domain.Domains.DbType;

public class SelectFileColumnsParam extends GenericConnectionParams {

	private String pgmLibrary;
	private String targetSchema;
	private String targetTable;
	private String schema;

	public String getPgmLibrary() {
		return pgmLibrary;
	}

	public void setPgmLibrary(String pgmLibrary) {
		this.pgmLibrary = pgmLibrary;
	}
	

	public String getTargetSchema() {
		return targetSchema;
	}

	public void setTargetSchema(String targetSchema) {
		this.targetSchema = targetSchema;
	}

	public String getTargetTable() {
		return targetTable;
	}

	public void setTargetTable(String targetTable) {
		this.targetTable = targetTable;
	}

	public String getSchema() {
		return schema;
	}

	public void setSchema(String schema) {
		this.schema = schema;
	}

	@Override
	public String toString() {
		return "SelectFileColumnsParam [pgmLibrary=" + pgmLibrary + ", targetSchema=" + targetSchema + ", targetTable="
				+ targetTable + ", schema=" + schema + "]";
	}

}
