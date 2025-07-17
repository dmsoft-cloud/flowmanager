package it.dmsoft.flowmanager.agent.engine.mailclient.pec.verificapec.interfacce;

import java.util.ArrayList;

public class CheckResult {
	private String tipo;
	private String consegna;
	private String messageId;
	private boolean valid;
	private ArrayList<String> ricezione;
	private String erroreEsteso;
	private String giorno;
	private String ora;
	private String gestoreEmittente;
	private String errore;
	private String remoteMessageId;

	public String getRemoteMessageId() {
		return remoteMessageId;
	}

	public void setRemoteMessageId(String remoteMessageId) {
		this.remoteMessageId = remoteMessageId;
	}

	public String getErrore() {
		return errore;
	}

	public void setErrore(String errore) {
		this.errore = errore;
	}

	public String getGiorno() {
		return giorno;
	}

	public void setGiorno(String giorno) {
		this.giorno = giorno;
	}

	public String getOra() {
		return ora;
	}

	public void setOra(String ora) {
		this.ora = ora;
	}

	public String getErroreEsteso() {
		return erroreEsteso;
	}

	public void setErroreEsteso(String erroreEsteso) {
		this.erroreEsteso = erroreEsteso;
	}

	public CheckResult() {
		super();
		ricezione = new ArrayList<String>();
	}

	public ArrayList<String> getRicezione() {
		return ricezione;
	}

	public void addRicezione(String ricezione) {
		this.ricezione.add(ricezione);
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getConsegna() {
		return consegna;
	}

	public String getGestoreEmittente() {
		return gestoreEmittente;
	}

	public void setGestoreEmittente(String gestoreEmittente) {
		this.gestoreEmittente = gestoreEmittente;
	}

	public void setConsegna(String consegna) {
		this.consegna = consegna;
	}

	public String toString() {
		return this.tipo + " - " + this.consegna + " - " + this.messageId + " - " + this.valid;
	}
}
