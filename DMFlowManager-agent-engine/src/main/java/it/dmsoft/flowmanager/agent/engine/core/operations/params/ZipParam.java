package it.dmsoft.flowmanager.agent.engine.core.operations.params;

import java.util.List;

import it.dmsoft.flowmanager.agent.engine.core.flow.builder.FlowBuilder.ZipOperation;

public class ZipParam {

	private List<String> sourceFiles;
	private List<String> destinationFiles;
	private ZipOperation operation;

	public List<String> getSourceFiles() {
		return sourceFiles;
	}

	public void setSourceFiles(List<String> sourceFiles) {
		this.sourceFiles = sourceFiles;
	}

	public List<String> getDestinationFiles() {
		return destinationFiles;
	}

	public void setDestinationFiles(List<String> destinationFiles) {
		this.destinationFiles = destinationFiles;
	}

	public ZipOperation getOperation() {
		return operation;
	}

	public void setOperation(ZipOperation operation) {
		this.operation = operation;
	}

	@Override
	public String toString() {
		return "ZipParam [sourceFiles=" + sourceFiles + ", destinationFiles="
				+ destinationFiles + ", operation=" + operation + "]";
	}


}
