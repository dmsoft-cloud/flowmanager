package it.dmsoft.flowmanager.agent.engine.core.operations.params;

public class CopyFileParam extends GenericAS400Param {

	private String fromFile;
	private String fromLibrary;
	private String toFile;
	private String toLibrary;
	private String mbrOpt;
	private String formatOption;


	public String getFromFile() {
		return fromFile;
	}
	public void setFromFile(String fromFile) {
		this.fromFile = fromFile;
	}
	public String getFromLibrary() {
		return fromLibrary;
	}
	public void setFromLibrary(String fromLibrary) {
		this.fromLibrary = fromLibrary;
	}
	public String getToFile() {
		return toFile;
	}
	public void setToFile(String toFile) {
		this.toFile = toFile;
	}
	public String getToLibrary() {
		return toLibrary;
	}
	public void setToLibrary(String toLibrary) {
		this.toLibrary = toLibrary;
	}
	public String getMbrOpt() {
		return mbrOpt;
	}
	public void setMbrOpt(String mbrOpt) {
		this.mbrOpt = mbrOpt;
	}
	public String getFormatOption() {
		return formatOption;
	}
	public void setFormatOption(String formatOption) {
		this.formatOption = formatOption;
	}
	@Override
	public String toString() {
		return "CopyFileParam [fromFile=" + fromFile + ", fromLibrary=" + fromLibrary + ", toFile=" + toFile
				+ ", toLibrary=" + toLibrary + ", mbrOpt=" + mbrOpt + ", formatOption=" + formatOption + "]";
	}
	
	
}
