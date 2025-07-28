package it.dmsoft.flowmanager.be.entities;

import java.math.BigDecimal;
import java.util.List;

public class FileColumnsStruct {

	private String description;
	private int prog;
	private int seq;
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getProg() {
		return prog;
	}
	public void setProg(int prog) {
		this.prog = prog;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	@Override
	public String toString() {
		return "FileColumnsStruct [description=" + description + ", prog=" + prog + ", seq=" + seq + "]";
	}
	

	
}
