package it.dmsoft.flowmanager.agent.engine.core.operations.params;

public class ReadSpoolFilesParams extends GenericAS400Param {

	private String folder;
	private String listFile;
	private String fileName;
	private String defaultShell;
	private OperationParams operationParams;
	private boolean launchErrorIfNoFileFound = false;

	public String getFolder() {
		return folder;
	}

	public void setFolder(String folder) {
		this.folder = folder;
	}

	public String getListFile() {
		return listFile;
	}

	public void setListFile(String listFile) {
		this.listFile = listFile;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public OperationParams getOperationParams() {
		return operationParams;
	}

	public void setOperationParams(OperationParams operationParams) {
		this.operationParams = operationParams;
	}
	
	public void setLaunchErrorIfNoFileFound(boolean launchErrorIfNoFileFound) {
		this.launchErrorIfNoFileFound = launchErrorIfNoFileFound;
	}

	public boolean isLaunchErrorIfNoFileFound() {
		return launchErrorIfNoFileFound;
	}
	

	public String getDefaultShell() {
		return defaultShell;
	}

	public void setDefaultShell(String defaultShell) {
		this.defaultShell = defaultShell;
	}

	@Override
	public String toString() {
		return "ReadSpoolFilesParams [folder=" + folder + ", listFile=" + listFile + ", fileName=" + fileName
				+ ", defaultShell=" + defaultShell + ", operationParams=" + operationParams
				+ ", launchErrorIfNoFileFound=" + launchErrorIfNoFileFound + "]";
	}



	


}
