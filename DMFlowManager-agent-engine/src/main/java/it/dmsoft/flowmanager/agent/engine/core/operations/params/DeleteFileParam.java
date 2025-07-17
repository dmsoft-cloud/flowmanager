package it.dmsoft.flowmanager.agent.engine.core.operations.params;

import java.util.List;

public class DeleteFileParam{

	
	private List<String> sources;

	public List<String> getSources() {
		return sources;
	}

	public void setSources(List<String> sources) {
		this.sources = sources;
	}

	@Override
	public String toString() {
		return "DeleteFileParam [sources=" + sources + "]";
	}

}
