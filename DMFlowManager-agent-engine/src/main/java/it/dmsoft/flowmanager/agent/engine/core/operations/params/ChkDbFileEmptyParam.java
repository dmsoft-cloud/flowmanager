package it.dmsoft.flowmanager.agent.engine.core.operations.params;

public class ChkDbFileEmptyParam extends GenericAS400Param {

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
		return "ChkDbFileParam [file=" + file + ", libreria=" + libreria + "]";
	}
	

}
