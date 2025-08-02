package it.dmsoft.flowmanager.agent.engine.core.operations.params;

import java.math.BigDecimal;
import java.util.ArrayList;

import it.dmsoft.flowmanager.agent.engine.mailclient.utility.Allegato;

public class SendMailParam /*extends GenericConnectionParams*/{

	private String pgmLibrary;
	private String hostName;
	private BigDecimal port;
	private boolean secure; 
	private String smtpUsername;
	private String smtpPassword;
	private String from;
	private ArrayList<String> tos;
	private ArrayList<String> ccs;
	private ArrayList<String> bccs;
	private String subject;
	private ArrayList<Allegato> allegati;
	private BigDecimal timeout;
	private String body;
	private boolean testoHtml;
	
	
	public BigDecimal getPort() {
		return port;
	}
	public void setPort(BigDecimal port) {
		this.port = port;
	}
	public boolean isSecure() {
		return secure;
	}
	public void setSecure(boolean secure) {
		this.secure = secure;
	}
	public String getSmtpUsername() {
		return smtpUsername;
	}
	public void setSmtpUsername(String smtpUsername) {
		this.smtpUsername = smtpUsername;
	}
	public String getSmtpPassword() {
		return smtpPassword;
	}
	public void setSmtpPassword(String smtpPassword) {
		this.smtpPassword = smtpPassword;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public ArrayList<String> getTos() {
		return tos;
	}
	public void setTos(ArrayList<String> tos) {
		this.tos = tos;
	}
	public ArrayList<String> getCcs() {
		return ccs;
	}
	public void setCcs(ArrayList<String> ccs) {
		this.ccs = ccs;
	}
	public ArrayList<String> getBccs() {
		return bccs;
	}
	public void setBccs(ArrayList<String> bccs) {
		this.bccs = bccs;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public ArrayList<Allegato> getAllegati() {
		return allegati;
	}
	public void setAllegati(ArrayList<Allegato> allegati) {
		this.allegati = allegati;
	}
	public BigDecimal getTimeout() {
		return timeout;
	}
	public void setTimeout(BigDecimal timeout) {
		this.timeout = timeout;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public boolean isTestoHtml() {
		return testoHtml;
	}
	public void setTestoHtml(boolean testoHtml) {
		this.testoHtml = testoHtml;
	}
	
	public String getPgmLibrary() {
		return pgmLibrary;
	}
	public void setPgmLibrary(String pgmLibrary) {
		this.pgmLibrary = pgmLibrary;
	}
	public String getHostName() {
		return hostName;
	}
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}
	@Override
	public String toString() {
		return "SendMailParam [pgmLibrary=" + pgmLibrary + ", hostName=" + hostName + ", port=" + port + ", secure="
				+ secure + ", smtpUsername=" + smtpUsername + ", smtpPassword=" + smtpPassword + ", from=" + from
				+ ", tos=" + tos + ", ccs=" + ccs + ", bccs=" + bccs + ", subject=" + subject + ", allegati=" + allegati
				+ ", timeout=" + timeout + ", body=" + body + ", testoHtml=" + testoHtml + "]";
	}
	
}
