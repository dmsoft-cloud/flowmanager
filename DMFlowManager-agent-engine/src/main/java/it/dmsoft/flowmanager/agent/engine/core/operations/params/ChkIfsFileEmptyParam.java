package it.dmsoft.flowmanager.agent.engine.core.operations.params;

import java.util.List;

public class ChkIfsFileEmptyParam extends GenericAS400Param {

	private List<String> checkedFiles;


	public List<String> getCheckedFiles() {
		return checkedFiles;
	}

	public void setCheckedFiles(List<String> checkedFiles) {
		this.checkedFiles = checkedFiles;
	}

	@Override
	public String toString() {
		return "ChkIfsFileEmptyParam [checkedFiles=" + checkedFiles + "]";
	}


	

}
