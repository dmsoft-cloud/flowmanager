package it.dmsoft.flowmanager.agent.engine.core.operations.params;

public class SelectFileColumnsParam extends GenericAS400Param {

	private String pgmLibrary;
	private String targetSchema;
	private String targetTable;

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

	@Override
	public String toString() {
		return "SelectFileColumnsParam [pgmLibrary=" + pgmLibrary + ", targetSchema=" + targetSchema + ", targetTable="
				+ targetTable + "]";
	}


	
	
}
