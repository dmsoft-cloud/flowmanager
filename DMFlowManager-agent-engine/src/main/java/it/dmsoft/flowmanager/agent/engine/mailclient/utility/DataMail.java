package it.dmsoft.flowmanager.agent.engine.mailclient.utility;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.regex.Matcher;

import it.dmsoft.flowmanager.agent.engine.mailclient.interfacce.FileMailInfo;
import it.dmsoft.flowmanager.agent.engine.mailclient.interfacce.RequestMail;
import it.dmsoft.flowmanager.agent.engine.mailclient.interfacce.RequestWithBody;
import it.dmsoft.flowmanager.agent.engine.mailclient.interfacce.RequestWithFile;

public class DataMail {
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
	private int timeout;
	private String body;
	private boolean testoHtml;
	private FileMailInfo pathSaveMail;

	private void popolaDati(RequestMail requestMail) {
		this.setHostname(requestMail.getHostname());
		this.setPort(requestMail.getPort());
		this.setSecure(requestMail.isSecure());
		this.setSmtpUsername(requestMail.getSmtpUsername());
		this.setSmtpPassword(requestMail.getSmtpPassword());
		this.setFrom(requestMail.getFrom());
		this.setTos(requestMail.getTos());
		this.setCcs(requestMail.getCcs());
		this.setBccs(requestMail.getBccs());
		this.setSubject(requestMail.getSubject());
		this.setAllegati(requestMail.getAllegati());
		this.setTimeout(requestMail.getTimeout());
		this.setTestoHtml(requestMail.isTestoHtml());
		this.setPathSaveMail(requestMail.getPathSaveMail());
	}

	public DataMail() {
		super();
	}

	public DataMail(RequestWithBody requestMail) {
		super();
		this.popolaDati(requestMail);
		mappaBody(requestMail.getMappature(), requestMail.getBody());
	}

	public DataMail(RequestWithFile requestMail) throws IOException {
		super();
		this.popolaDati(requestMail);
		mappaBody(requestMail.getMappature(), new String(Files.readAllBytes(Paths.get(requestMail.getPathBody())), StandardCharsets.UTF_8));
	}

	private void mappaBody(ArrayList<Mappatura> mappature, String body) {
		this.body = body;
		for (Mappatura mappatura : mappature) {
			if (mappatura.getAnchor() != null && !"".equals(mappatura.getAnchor().trim())) {
				this.body = this.body.replaceAll(Matcher.quoteReplacement(mappatura.getAnchor()), mappatura.getValore());
				this.subject = this.subject.replaceAll(Matcher.quoteReplacement(mappatura.getAnchor()), mappatura.getValore());
			}
		}
		this.body = new String(this.getBody().getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getHostname() {
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
		if (tos == null) {
			tos = new ArrayList<String>();
		}
		return tos;
	}

	public void setTos(ArrayList<String> tos) {
		this.tos = new ArrayList<String>();
		if (tos == null) {
			return;
		}
		for (String to : tos) {
			if (!"".equals(to.trim())) {
				this.tos.add(to);
			}
		}
	}

	public ArrayList<String> getCcs() {
		if (ccs == null) {
			ccs = new ArrayList<String>();
		}
		return ccs;
	}

	public void setCcs(ArrayList<String> ccs) {
		this.ccs = new ArrayList<String>();
		if (ccs == null) {
			return;
		}
		for (String cc : ccs) {
			if (!"".equals(cc.trim())) {
				this.ccs.add(cc);
			}
		}
	}

	public ArrayList<String> getBccs() {
		if (bccs == null) {
			bccs = new ArrayList<String>();
		}
		return bccs;
	}

	public void setBccs(ArrayList<String> bccs) {
		this.bccs = new ArrayList<String>();
		if (bccs == null) {
			return;
		}
		for (String bcc : bccs) {
			if (!"".equals(bcc.trim())) {
				this.bccs.add(bcc);
			}
		}
	}

	public String getSubject() {
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
		this.allegati = new ArrayList<Allegato>();
		if (allegati == null) {
			return;
		}
		for (Allegato allegato : allegati) {
			if (!"".equals(allegato.getPath().trim())) {
				this.allegati.add(allegato);
			}
		}
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

	public boolean esistonoAllegati() {
		if (allegati == null) {
			return false;
		}
		for (Allegato allegato : allegati) {
			if (allegato != null && !"".equals(allegato.getPath().trim())) {
				return true;
			}
		}
		return false;
	}

	public FileMailInfo getPathSaveMail() {
		if (pathSaveMail == null) {
			pathSaveMail = new FileMailInfo();
		}
		return pathSaveMail;
	}

	public void setPathSaveMail(FileMailInfo pathSaveMail) {
		this.pathSaveMail = pathSaveMail;
	}

	@Override
	public String toString() {
		return "DataMail [hostname=" + hostname + ", port=" + port + ", secure=" + secure + ", smtpUsername=" + smtpUsername + ", smtpPassword=" + smtpPassword + ", from=" + from + ", tos=" + tos
				+ ", ccs=" + ccs + ", bccs=" + bccs + ", subject=" + subject + ", allegati=" + allegati + ", timeout=" + timeout + ", body=" + body + ", testoHtml=" + testoHtml + ", pathSaveMail="
				+ pathSaveMail + "]";
	}

}
