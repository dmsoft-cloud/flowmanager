package it.dmsoft.flowmanager.agent.engine.core.operations.params;

public class DropTmpExportTableParam extends GenericConnectionParams {

	private String table;
	private String library;
	
	public String getTable() {
		return table;
	}
	public void setTable(String table) {
		this.table = table;
	}
	public String getLibrary() {
		return library;
	}
	public void setLibrary(String library) {
		this.library = library;
	}

	@Override
	public String toString() {
		return "DropTmpExportTableParam [table=" + table + ", library=" + library + "]";
	}

}
