package it.dmsoft.flowmanager.agent.engine.core.operations.params;

import it.dmsoft.flowmanager.common.domain.Domains.DbType;

public class ChkDbFileEmptyParam extends GenericConnectionParams {

	private String file;
	private String libreria;


	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public String getLibreria() {
		return libreria;
	}

	public void setLibreria(String libreria) {
		this.libreria = libreria;
	}

	@Override
	public String toString() {
		return "ChkDbFileEmptyParam [file=" + file + ", libreria=" + libreria + "]";
	}

}
