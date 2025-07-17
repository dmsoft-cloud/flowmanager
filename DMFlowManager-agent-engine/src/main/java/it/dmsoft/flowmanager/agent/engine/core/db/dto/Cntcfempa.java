package it.dmsoft.flowmanager.agent.engine.core.db.dto;

import java.math.BigDecimal;

public class Cntcfempa {
	public static enum CntcfempaCoulmn {

		CNTCEMPA_ACCOUNT,
		CNTCEMPA_SERVER_U,
		CNTCEMPA_PORTA_U,
		CNTCEMPA_UTENTE_U,
		CNTCEMPA_PASSWORD_U,
		CNTCEMPA_AUTENTICAZ,
		CNTCEMPA_PROTOCOLLO_U,
		CNTCEMPA_FLAG_SSL_U,
		CNTCEMPA_PATH_AL_AS,
		CNTCEMPA_PATH_AL_PC,
		CNTCEMPA_PATH_EMAIL,
		CNTCEMPA_TIPO_EMAIL,
		CNTCEMPA_SERVER_I,
		CNTCEMPA_PORTA_I,
		CNTCEMPA_UTENTE_I,
		CNTCEMPA_PASSWORD_I,
		CNTCEMPA_PROTOCOLLO_I,
		CNTCEMPA_FLAG_SSL_I,
		CNTCEMPA_PATH_SAVE

	}
	
	private String cntcempa_Account;
	private String cntcempa_Server_U;
	private BigDecimal cntcempa_Porta_U;
	private String cntcempa_Utente_U;
	private byte[] cntcempa_Password_U;
	private String cntcempa_Autenticaz;
	private String cntcempa_Protocollo_U;
	private String cntcempa_Flag_Ssl_U;
	private String cntcempa_Path_Al_As;
	private String cntcempa_Path_Al_Pc;
	private String cntcempa_Path_Email;
	private String cntcempa_Tipo_Email;
	private String cntcempa_Server_I;
	private BigDecimal cntcempa_Porta_I;
	private String cntcempa_Utente_I;
	private byte[] cntcempa_Password_I;
	private String cntcempa_Protocollo_I;
	private String cntcempa_Flag_Ssl_I;
	private String cntcempa_Path_Save;
	
	public String getCntcempa_Account() {
		return cntcempa_Account;
	}
	public void setCntcempa_Account(String cntcempa_Account) {
		this.cntcempa_Account = cntcempa_Account;
	}
	public String getCntcempa_Server_U() {
		return cntcempa_Server_U;
	}
	public void setCntcempa_Server_U(String cntcempa_Server_U) {
		this.cntcempa_Server_U = cntcempa_Server_U;
	}
	public BigDecimal getCntcempa_Porta_U() {
		return cntcempa_Porta_U;
	}
	public void setCntcempa_Porta_U(BigDecimal cntcempa_Porta_U) {
		this.cntcempa_Porta_U = cntcempa_Porta_U;
	}
	public String getCntcempa_Utente_U() {
		return cntcempa_Utente_U;
	}
	public void setCntcempa_Utente_U(String cntcempa_Utente_U) {
		this.cntcempa_Utente_U = cntcempa_Utente_U;
	}
	public String getCntcempa_Autenticaz() {
		return cntcempa_Autenticaz;
	}
	public void setCntcempa_Autenticaz(String cntcempa_Autenticaz) {
		this.cntcempa_Autenticaz = cntcempa_Autenticaz;
	}
	public String getCntcempa_Protocollo_U() {
		return cntcempa_Protocollo_U;
	}
	public void setCntcempa_Protocollo_U(String cntcempa_Protocollo_U) {
		this.cntcempa_Protocollo_U = cntcempa_Protocollo_U;
	}
	public String getCntcempa_Flag_Ssl_U() {
		return cntcempa_Flag_Ssl_U;
	}
	public void setCntcempa_Flag_Ssl_U(String cntcempa_Flag_Ssl_U) {
		this.cntcempa_Flag_Ssl_U = cntcempa_Flag_Ssl_U;
	}
	public String getCntcempa_Path_Al_As() {
		return cntcempa_Path_Al_As;
	}
	public void setCntcempa_Path_Al_As(String cntcempa_Path_Al_As) {
		this.cntcempa_Path_Al_As = cntcempa_Path_Al_As;
	}
	public String getCntcempa_Path_Al_Pc() {
		return cntcempa_Path_Al_Pc;
	}
	public void setCntcempa_Path_Al_Pc(String cntcempa_Path_Al_Pc) {
		this.cntcempa_Path_Al_Pc = cntcempa_Path_Al_Pc;
	}
	public String getCntcempa_Path_Email() {
		return cntcempa_Path_Email;
	}
	public void setCntcempa_Path_Email(String cntcempa_Path_Email) {
		this.cntcempa_Path_Email = cntcempa_Path_Email;
	}
	public String getCntcempa_Tipo_Email() {
		return cntcempa_Tipo_Email;
	}
	public void setCntcempa_Tipo_Email(String cntcempa_Tipo_Email) {
		this.cntcempa_Tipo_Email = cntcempa_Tipo_Email;
	}
	public String getCntcempa_Server_I() {
		return cntcempa_Server_I;
	}
	public void setCntcempa_Server_I(String cntcempa_Server_I) {
		this.cntcempa_Server_I = cntcempa_Server_I;
	}
	public BigDecimal getCntcempa_Porta_I() {
		return cntcempa_Porta_I;
	}
	public void setCntcempa_Porta_I(BigDecimal cntcempa_Porta_I) {
		this.cntcempa_Porta_I = cntcempa_Porta_I;
	}
	public String getCntcempa_Utente_I() {
		return cntcempa_Utente_I;
	}
	public void setCntcempa_Utente_I(String cntcempa_Utente_I) {
		this.cntcempa_Utente_I = cntcempa_Utente_I;
	}
	public String getCntcempa_Flag_Ssl_I() {
		return cntcempa_Flag_Ssl_I;
	}
	public void setCntcempa_Flag_Ssl_I(String cntcempa_Flag_Ssl_I) {
		this.cntcempa_Flag_Ssl_I = cntcempa_Flag_Ssl_I;
	}
	public String getCntcempa_Path_Save() {
		return cntcempa_Path_Save;
	}
	public void setCntcempa_Path_Save(String cntcempa_Path_Save) {
		this.cntcempa_Path_Save = cntcempa_Path_Save;
	}
	public byte[] getCntcempa_Password_U() {
		return cntcempa_Password_U;
	}
	public void setCntcempa_Password_U(byte[] cntcempa_Password_U) {
		this.cntcempa_Password_U = cntcempa_Password_U;
	}
	public byte[] getCntcempa_Password_I() {
		return cntcempa_Password_I;
	}
	public void setCntcempa_Password_I(byte[] cntcempa_Password_I) {
		this.cntcempa_Password_I = cntcempa_Password_I;
	}
	public String getCntcempa_Protocollo_I() {
		return cntcempa_Protocollo_I;
	}
	public void setCntcempa_Protocollo_I(String cntcempa_Protocollo_I) {
		this.cntcempa_Protocollo_I = cntcempa_Protocollo_I;
	}
	@Override
	public String toString() {
		return "Cntcfempa [cntcempa_Account=" + cntcempa_Account + ", cntcempa_Server_U=" + cntcempa_Server_U
				+ ", cntcempa_Porta_U=" + cntcempa_Porta_U + ", cntcempa_Utente_U=" + cntcempa_Utente_U
				+ ", cntcempa_Password_U=" + cntcempa_Password_U + ", cntcempa_Autenticaz=" + cntcempa_Autenticaz
				+ ", cntcempa_Protocollo_U=" + cntcempa_Protocollo_U + ", cntcempa_Flag_Ssl_U=" + cntcempa_Flag_Ssl_U
				+ ", cntcempa_Path_Al_As=" + cntcempa_Path_Al_As + ", cntcempa_Path_Al_Pc=" + cntcempa_Path_Al_Pc
				+ ", cntcempa_Path_Email=" + cntcempa_Path_Email + ", cntcempa_Tipo_Email=" + cntcempa_Tipo_Email
				+ ", cntcempa_Server_I=" + cntcempa_Server_I + ", cntcempa_Porta_I=" + cntcempa_Porta_I
				+ ", cntcempa_Utente_I=" + cntcempa_Utente_I + ", cntcempa_Password_I=" + cntcempa_Password_I
				+ ", cntcempa_Protocollo_I=" + cntcempa_Protocollo_I + ", cntcempa_Flag_Ssl_I=" + cntcempa_Flag_Ssl_I
				+ ", cntcempa_Path_Save=" + cntcempa_Path_Save + "]";
	}

	
	
	
	
	
	
	
	

	
	
	

}
