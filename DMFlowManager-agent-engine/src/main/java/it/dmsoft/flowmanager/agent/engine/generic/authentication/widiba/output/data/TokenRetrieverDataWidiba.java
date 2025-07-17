package it.dmsoft.flowmanager.agent.engine.generic.authentication.widiba.output.data;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import it.dmsoft.flowmanager.agent.engine.generic.authentication.common.output.data.TokenRetrieverData;
import it.dmsoft.flowmanager.agent.engine.generic.authentication.widiba.interfaccie.Errore;
import it.dmsoft.flowmanager.agent.engine.generic.clientWs.utility.OcsBase64;
import it.dmsoft.flowmanager.agent.engine.generic.utility.logger.KeyValueLog;
import it.dmsoft.flowmanager.agent.engine.generic.utility.logger.Logger;

public class TokenRetrieverDataWidiba extends TokenRetrieverData {
	private static final long serialVersionUID = 6282347413596509082L;
	private Errore errore;
	private Timestamp timestampExpire;

	public TokenRetrieverDataWidiba(String token, boolean attivo) {
		this(token, attivo, null);
	}

	public TokenRetrieverDataWidiba(String token, boolean attivo, Errore errore) {
		super(attivo, token);
		this.errore = errore;
	}

	public TokenRetrieverDataWidiba() {
		super(false, "");
		timestampExpire = null;
	}

	public Errore getErrore() {
		return errore;
	}

	public void setErrore(Errore errore) {
		this.errore = errore;
	}

	public boolean scaduto(Logger logger) {

		if (timestampExpire == null) {
			logger.info("timestampExpire impostato a null");
			return true;
		}

		Timestamp timestampNow = new Timestamp(System.currentTimeMillis());
		logger.info("timestampNow:    " + timestampNow.toString());
		logger.info("timestampExpire: " + timestampExpire.toString());
		if ((timestampNow.compareTo(timestampExpire) > 0)) {
			logger.info("Scaduto");
			return true;
		}
		logger.info("Non scaduto");
		return false;
	}

	public void calcTimestampExpire(Logger logger) {
		logger.debug("Calcola timestampExpire");
		if (this.getToken() == null) {
			logger.warn("Impossibile calcolare timestampExpire");
			return;
		}

		String[] chunks = this.getToken().split("\\.");
		String payload = new String(OcsBase64.decodeBase64(chunks[1]));

		JSONObject jsonPayload = (JSONObject) JSONValue.parse(payload);
		if (jsonPayload != null) {
			Long exp = null;
			try {
				exp = (Long) jsonPayload.get("exp");
			} catch (Exception e1) {
				logger.warn("exp non è un Long. jsonPayload: " + jsonPayload);
				return;
			}

			if (exp != null) {
				String expString = Long.toString(exp);
				logger.debug(new KeyValueLog("exp per calcolo scadenza token: ", expString));
				timestampExpire = new Timestamp((new Date(exp * 1000)).getTime());
				Date date = new Date();
				date.setTime(timestampExpire.getTime());
				String timestampString = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(date);
				logger.debug(new KeyValueLog("TimestampExpire calcolato", timestampString));
			}
		} else {
			logger.warn("Payload non valorizzato, impostazione scadenza token interrotta");
		}
	}

	@Override
	public String toString() {
		return "TokenRetrieverDataWidiba [errore=" + errore + ", timestampExpire=" + timestampExpire + "]";
	}
}
