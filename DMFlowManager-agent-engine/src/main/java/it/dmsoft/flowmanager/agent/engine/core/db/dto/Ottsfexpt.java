package it.dmsoft.flowmanager.agent.engine.core.db.dto;

import java.math.BigDecimal;

public class Ottsfexpt {
	public static enum OttsfexptColumn {
		EXPT_IDE,
		EXPT_FILE_TYPE,
		EXPT_DEST_TYPE,
		EXPT_DEST,
		EXPT_FILE_NAME,
		EXPT_FILE_DES,
		EXPT_TESTATA,
		EXPT_DLMT,
		EXPT_RMVBLK,
		EXPT_TITOLO,
		EXPT_ERRATO,
		EXPT_RIM_SPAZI_ALFAN_VUOTI,
		EXPT_FINE_RIGA,
		EXPT_DECPNT
	}

	
	private String expt_Ide;
	private String expt_File_Type;
	private String expt_Dest_Type;
	private String expt_Dest;
	private String expt_File_Name;
	private String expt_File_Des;
	private String expt_Testata;
	private String expt_Dlmt;
	private String expt_Rmvblk;
	private String expt_Titolo;
	private String expt_Errato;
	private String expt_Rim_Spazi_Alfan_Vuoti;
	private String expt_Fine_Riga;
	private String expt_Decpnt;

	
	public String getExpt_Ide() {
		return expt_Ide;
	}
	public void setExpt_Ide(String expt_Ide) {
		this.expt_Ide = expt_Ide;
	}
	public String getExpt_File_Type() {
		return expt_File_Type;
	}
	public void setExpt_File_Type(String expt_File_Type) {
		this.expt_File_Type = expt_File_Type;
	}
	public String getExpt_Dest_Type() {
		return expt_Dest_Type;
	}
	public void setExpt_Dest_Type(String expt_Dest_Type) {
		this.expt_Dest_Type = expt_Dest_Type;
	}
	public String getExpt_Dest() {
		return expt_Dest;
	}
	public void setExpt_Dest(String expt_Dest) {
		this.expt_Dest = expt_Dest;
	}
	public String getExpt_File_Name() {
		return expt_File_Name;
	}
	public void setExpt_File_Name(String expt_File_Name) {
		this.expt_File_Name = expt_File_Name;
	}
	public String getExpt_File_Des() {
		return expt_File_Des;
	}
	public void setExpt_File_Des(String expt_File_Des) {
		this.expt_File_Des = expt_File_Des;
	}
	public String getExpt_Testata() {
		return expt_Testata;
	}
	public void setExpt_Testata(String expt_Testata) {
		this.expt_Testata = expt_Testata;
	}
	public String getExpt_Dlmt() {
		return expt_Dlmt;
	}
	public void setExpt_Dlmt(String expt_Dlmt) {
		this.expt_Dlmt = expt_Dlmt;
	}
	public String getExpt_Rmvblk() {
		return expt_Rmvblk;
	}
	public void setExpt_Rmvblk(String expt_Rmvblk) {
		this.expt_Rmvblk = expt_Rmvblk;
	}
	public String getExpt_Titolo() {
		return expt_Titolo;
	}
	public void setExpt_Titolo(String expt_Titolo) {
		this.expt_Titolo = expt_Titolo;
	}
	public String getExpt_Errato() {
		return expt_Errato;
	}
	public void setExpt_Errato(String expt_Errato) {
		this.expt_Errato = expt_Errato;
	}
	public String getExpt_Rim_Spazi_Alfan_Vuoti() {
		return expt_Rim_Spazi_Alfan_Vuoti;
	}
	public void setExpt_Rim_Spazi_Alfan_Vuoti(String expt_Rim_Spazi_Alfan_Vuoti) {
		this.expt_Rim_Spazi_Alfan_Vuoti = expt_Rim_Spazi_Alfan_Vuoti;
	}
	public String getExpt_Fine_Riga() {
		return expt_Fine_Riga;
	}
	public void setExpt_Fine_Riga(String expt_Fine_Riga) {
		this.expt_Fine_Riga = expt_Fine_Riga;
	}
	public String getExpt_Decpnt() {
		return expt_Decpnt;
	}
	public void setExpt_Decpnt(String expt_Decpnt) {
		this.expt_Decpnt = expt_Decpnt;
	}
	@Override
	public String toString() {
		return "Ottsfexpt [expt_Ide=" + expt_Ide + ", expt_File_Type=" + expt_File_Type + ", expt_Dest_Type="
				+ expt_Dest_Type + ", expt_Dest=" + expt_Dest + ", expt_File_Name=" + expt_File_Name
				+ ", expt_File_Des=" + expt_File_Des + ", expt_Testata=" + expt_Testata + ", expt_Dlmt=" + expt_Dlmt
				+ ", expt_Rmvblk=" + expt_Rmvblk + ", expt_Titolo=" + expt_Titolo + ", expt_Errato=" + expt_Errato
				+ ", expt_Rim_Spazi_Alfan_Vuoti=" + expt_Rim_Spazi_Alfan_Vuoti + ", expt_Fine_Riga=" + expt_Fine_Riga
				+ ", expt_Decpnt=" + expt_Decpnt + "]";
	}
	
}
