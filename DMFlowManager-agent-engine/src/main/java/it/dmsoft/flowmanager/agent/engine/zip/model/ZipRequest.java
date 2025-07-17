package it.dmsoft.flowmanager.agent.engine.zip.model;

public class ZipRequest {
	public enum Operation {
		ZIP, UNZIP
	}
	
	private String source;
	private String destination;
	private Operation operation;
	
	public ZipRequest() {}
	
	public ZipRequest(String source, String destination, String operation) {
		this.source = source;
		this.destination = destination;
		this.operation = Operation.valueOf(operation.toUpperCase());
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}
	
	public void setOperation(String operation) {
		this.operation = Operation.valueOf(operation.toUpperCase());
	}
	
	public Operation getOperation() {
		return operation;
	}

	@Override
	public String toString() {
		return "ZipRequest [source=" + source + ", destination=" + destination + ", operation=" + operation + "]";
	}
	
}
