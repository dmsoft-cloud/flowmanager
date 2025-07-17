package it.dmsoft.flowmanager.agent.engine.core.operations.params;

import java.util.List;

public class HashCheckParam {

    private List<String> fileNames;
    private boolean write;

    public List<String> getFileNames() {
        return fileNames;
    }

    public void setFileNames(List<String> fileNames) {
        this.fileNames = fileNames;
    }

	public boolean isWrite() {
		return write;
	}

	public void setWrite(boolean write) {
		this.write = write;
	}
}