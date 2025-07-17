package it.dmsoft.flowmanager.agent.engine.mailclient.interfacce;

import java.util.ArrayList;

import it.dmsoft.flowmanager.agent.engine.mailclient.utility.Allegato;
import it.dmsoft.flowmanager.agent.engine.mailclient.utility.Mappatura;

public abstract class RequestMail {

	private String hostname;
	private int port;
	private boolean secure;
	private String smtpUsername;
	private String smtpPassword;
	private String from;
	private ArrayList<String> tos;
	private ArrayList<String> ccs;
	private ArrayList<String> bccs;
	private String subject;
	private ArrayList<Allegato> allegati;
	private ArrayList<Mappatura> mappature;
	private int timeout;
	private boolean testoHtml;
	private FileMailInfo pathSaveMail;

	public String getHostname() {
		if (hostname == null) {
			hostname = new String();
		}
		return hostname;

	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public boolean isSecure() {
		return secure;
	}

	public void setSecure(boolean secure) {
		this.secure = secure;
	}

	public String getSmtpUsername() {
		if (smtpUsername == null) {
			smtpUsername = new String();
		}
		return smtpUsername;
	}

	public void setSmtpUsername(String smtpUsername) {
		this.smtpUsername = smtpUsername;
	}

	public String getSmtpPassword() {
		if (smtpPassword == null) {
			smtpPassword = new String();
		}
		return smtpPassword;
	}

	public void setSmtpPassword(String smtpPassword) {
		this.smtpPassword = smtpPassword;
	}

	public String getFrom() {
		if (from == null) {
			from = new String();
		}
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public ArrayList<String> getTos() {
		if (tos == null) {
			tos = new ArrayList<String>();
		}
		return tos;
	}

	public void setTos(ArrayList<String> tos) {
		this.tos = tos;
	}

	public ArrayList<String> getCcs() {
		if (ccs == null) {
			ccs = new ArrayList<String>();
		}
		return ccs;
	}

	public void setCcs(ArrayList<String> ccs) {
		this.ccs = ccs;
	}

	public ArrayList<String> getBccs() {
		if (bccs == null) {
			bccs = new ArrayList<String>();
		}
		return bccs;
	}

	public void setBccs(ArrayList<String> bccs) {
		this.bccs = bccs;
	}

	public String getSubject() {
		if (subject == null) {
			subject = new String();
		}
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public ArrayList<Allegato> getAllegati() {
		if (allegati == null) {
			allegati = new ArrayList<Allegato>();
		}
		return allegati;
	}

	public void setAllegati(ArrayList<Allegato> allegati) {
		this.allegati = allegati;
	}

	public ArrayList<Mappatura> getMappature() {
		if (mappature == null) {
			mappature = new ArrayList<Mappatura>();
		}
		return mappature;
	}

	public void setMappature(ArrayList<Mappatura> mappature) {
		this.mappature = mappature;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public boolean isTestoHtml() {
		return testoHtml;
	}

	public void setTestoHtml(boolean testoHtml) {
		this.testoHtml = testoHtml;
	}

	public FileMailInfo getPathSaveMail() {
		return pathSaveMail;
	}

	public void setPathSaveMail(FileMailInfo pathSaveMail) {
		this.pathSaveMail = pathSaveMail;
	}

	@Override
	public String toString() {
		return "RequestMail [hostname=" + hostname + ", port=" + port + ", secure=" + secure + ", smtpUsername=" + smtpUsername + ", smtpPassword=" + smtpPassword + ", from=" + from + ", tos=" + tos
				+ ", ccs=" + ccs + ", bccs=" + bccs + ", subject=" + subject + ", allegati=" + allegati + ", mappature=" + mappature + ", timeout=" + timeout + ", testoHtml=" + testoHtml
				+ ", pathSaveMail=" + pathSaveMail + "]";
	}
}