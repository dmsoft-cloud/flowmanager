package it.dmsoft.flowmanager.agent.engine.generic.authentication.compass.output.data;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import it.dmsoft.flowmanager.agent.engine.generic.authentication.common.output.data.TokenRetrieverData;
import it.dmsoft.flowmanager.agent.engine.generic.utility.logger.KeyValueLog;
import it.dmsoft.flowmanager.agent.engine.generic.utility.logger.Logger;

public class TokenRetrieverDataCompass extends TokenRetrieverData {
	private static final long serialVersionUID = 1354315944587735793L;
	private Timestamp timestampExpire;

	public TokenRetrieverDataCompass() {
		super(false, null);
	}

	public TokenRetrieverDataCompass(boolean attivo, String token, Integer expiresIn, Logger logger) {
		super(attivo, token);
		calcTimestampExpire(expiresIn, logger);
	}

	public Timestamp getTimestampExpire() {
		return timestampExpire;
	}

	public void setTimestampExpire(Timestamp timestampExpire) {
		this.timestampExpire = timestampExpire;
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

	private void calcTimestampExpire(Integer expiresIn, Logger logger) {
		logger.debug("Calcola timestampExpire");
		if (expiresIn == null) {
			logger.warn("Impossibile calcolare timestampExpire");
			return;
		}
		String expString = Integer.toString(expiresIn);
		logger.debug(new KeyValueLog("exp per calcolo scadenza token: ", expString));
		timestampExpire = new Timestamp((new Date(expiresIn * 1000l)).getTime());
		Date date = new Date();
		date.setTime(timestampExpire.getTime());
		String timestampString = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(date);
		logger.debug(new KeyValueLog("TimestampExpire calcolato", timestampString));
	}
}
