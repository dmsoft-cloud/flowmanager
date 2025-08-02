package it.dmsoft.flowmanager.agent.engine.core.operations.params;

import java.math.BigDecimal;

public class CreateDbFileParam extends GenericConnectionParams{

	
	private String file;
	private String libreria;
	private String membro;
	private String srcFile;
	private String srcLibreria;
	private String srcMembro;
	private BigDecimal recordLength;
	private boolean creaSeNonEsiste;
	
	
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
	public String getMembro() {
		return membro;
	}
	public void setMembro(String membro) {
		this.membro = membro;
	}
	public String getSrcFile() {
		return srcFile;
	}
	public void setSrcFile(String srcFile) {
		this.srcFile = srcFile;
	}
	public String getSrcLibreria() {
		return srcLibreria;
	}
	public void setSrcLibreria(String srcLibreria) {
		this.srcLibreria = srcLibreria;
	}
	public String getSrcMembro() {
		return srcMembro;
	}
	public void setSrcMembro(String srcMembro) {
		this.srcMembro = srcMembro;
	}
	
	public BigDecimal getRecordLength() {
		return recordLength;
	}
	public void setRecordLength(BigDecimal recordLength) {
		this.recordLength = recordLength;
	}
	@Override
	public String toString() {
		return super.toString() + "\n CreateDbFileParam [file=" + file + ", libreria=" + libreria + ", membro=" + membro + ", srcFile="
				+ srcFile + ", srcLibreria=" + srcLibreria + ", srcMembro=" + srcMembro + ", recordLength="
				+ recordLength + "]";
	}
	public boolean getCreaSeNonEsiste() {
		return creaSeNonEsiste;
	}
	public void setCreaSeNonEsiste(boolean creaSeNonEsiste) {
		this.creaSeNonEsiste = creaSeNonEsiste;
	}
	
}
