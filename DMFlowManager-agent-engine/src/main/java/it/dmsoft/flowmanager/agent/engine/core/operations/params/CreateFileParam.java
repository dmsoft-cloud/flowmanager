package it.dmsoft.flowmanager.agent.engine.core.operations.params;

public class CreateFileParam extends GenericAS400Param {

	private String folder;
	private String fileName;

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

	@Override
	public String toString() {
		return super.toString() + "\n CreateFileParam [folder=" + folder + ", fileName=" + fileName + "]";
	}

}
