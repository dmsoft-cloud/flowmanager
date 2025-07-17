package it.dmsoft.flowmanager.agent.engine.generic.authentication.common.output.data;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import it.dmsoft.flowmanager.agent.engine.generic.utility.logger.KeyValueLog;
import it.dmsoft.flowmanager.agent.engine.generic.utility.logger.Logger;

public class TokenRetrieverDataExpirable extends TokenRetrieverData {
	private Timestamp timestampExpire;

	public TokenRetrieverDataExpirable() {
		super(false, null);
	}

	public TokenRetrieverDataExpirable(boolean attivo) {
		this(attivo, null, null, null);
	}

	public TokenRetrieverDataExpirable(boolean attivo, String token, Integer expiresIn, Logger logger) {
		super(attivo, token);
		if (expiresIn != null) {
			calcTimestampExpire(expiresIn, logger);
		}
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
