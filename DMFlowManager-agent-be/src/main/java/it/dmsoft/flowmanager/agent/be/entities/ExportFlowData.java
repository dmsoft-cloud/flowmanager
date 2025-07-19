package it.dmsoft.flowmanager.agent.be.entities;

import java.math.BigDecimal;

public class ExportFlowData {
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

	
	private String exportIde;
	private String exportFile_Type;
	private String exportDest_Type;
	private String exportDest;
	private String exportFile_Name;
	private String exportFile_Des;
	private String exportTestata;
	private String exportDlmt;
	private String exportRmvblk;
	private String exportTitolo;
	private String exportErrato;
	private String exportRim_Spazi_Alfan_Vuoti;
	private String exportFine_Riga;
	private String exportDecpnt;

	
	public String getExportIde() {
		return exportIde;
	}
	public void setExportIde(String exportIde) {
		this.exportIde = exportIde;
	}
	public String getExportFileType() {
		return exportFile_Type;
	}
	public void setExportFileType(String exportFile_Type) {
		this.exportFile_Type = exportFile_Type;
	}
	public String getExportDestType() {
		return exportDest_Type;
	}
	public void setExportDestType(String exportDest_Type) {
		this.exportDest_Type = exportDest_Type;
	}
	public String getExportDest() {
		return exportDest;
	}
	public void setExportDest(String exportDest) {
		this.exportDest = exportDest;
	}
	public String getExportFileName() {
		return exportFile_Name;
	}
	public void setExportFileName(String exportFile_Name) {
		this.exportFile_Name = exportFile_Name;
	}
	public String getExportFileDes() {
		return exportFile_Des;
	}
	public void setExportFileDes(String exportFile_Des) {
		this.exportFile_Des = exportFile_Des;
	}
	public String getExportTestata() {
		return exportTestata;
	}
	public void setExportTestata(String exportTestata) {
		this.exportTestata = exportTestata;
	}
	public String getExportDlmt() {
		return exportDlmt;
	}
	public void setExportDlmt(String exportDlmt) {
		this.exportDlmt = exportDlmt;
	}
	public String getExportRmvblk() {
		return exportRmvblk;
	}
	public void setExportRmvblk(String exportRmvblk) {
		this.exportRmvblk = exportRmvblk;
	}
	public String getExportTitolo() {
		return exportTitolo;
	}
	public void setExportTitolo(String exportTitolo) {
		this.exportTitolo = exportTitolo;
	}
	public String getExportErrato() {
		return exportErrato;
	}
	public void setExportErrato(String exportErrato) {
		this.exportErrato = exportErrato;
	}
	public String getExportRimSpaziAlfanVuoti() {
		return exportRim_Spazi_Alfan_Vuoti;
	}
	public void setExportRimSpaziAlfanVuoti(String exportRim_Spazi_Alfan_Vuoti) {
		this.exportRim_Spazi_Alfan_Vuoti = exportRim_Spazi_Alfan_Vuoti;
	}
	public String getExportFineRiga() {
		return exportFine_Riga;
	}
	public void setExportFineRiga(String exportFine_Riga) {
		this.exportFine_Riga = exportFine_Riga;
	}
	public String getExportDecpnt() {
		return exportDecpnt;
	}
	public void setExportDecpnt(String exportDecpnt) {
		this.exportDecpnt = exportDecpnt;
	}
	@Override
	public String toString() {
		return "Ottsfexpt [exportIde=" + exportIde + ", exportFile_Type=" + exportFile_Type + ", exportDest_Type="
				+ exportDest_Type + ", exportDest=" + exportDest + ", exportFile_Name=" + exportFile_Name
				+ ", exportFile_Des=" + exportFile_Des + ", exportTestata=" + exportTestata + ", exportDlmt=" + exportDlmt
				+ ", exportRmvblk=" + exportRmvblk + ", exportTitolo=" + exportTitolo + ", exportErrato=" + exportErrato
				+ ", exportRim_Spazi_Alfan_Vuoti=" + exportRim_Spazi_Alfan_Vuoti + ", exportFine_Riga=" + exportFine_Riga
				+ ", exportDecpnt=" + exportDecpnt + "]";
	}
	
}
