package it.dmsoft.flowmanager.be.entities;

public class FileMetadata {
	private int prog;
	private String description;
	public int getProg() {
		return prog;
	}
	public void setProg(int prog) {
		this.prog = prog;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public String toString() {
		return "FileMetadata [prog=" + prog + ", description=" + description + "]";
	}
	
	
}
