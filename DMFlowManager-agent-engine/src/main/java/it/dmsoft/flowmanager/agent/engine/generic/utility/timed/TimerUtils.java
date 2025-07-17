package it.dmsoft.flowmanager.agent.engine.generic.utility.timed;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

import it.dmsoft.flowmanager.agent.engine.generic.utility.logger.Logger;

/**
 * La classe viene utilizzata solo da InterfacciaExperianClient. Per la
 * rimoazione dei metodi deprecati dobbiamo essere certi che nessun cliente usi
 * più l'applicazione vecchia
 * 
 * @author lucat
 *
 */
public class TimerUtils {
	private static final long DEFAULT_TIMEOUT = 10000L; // milliseconds
	private static Map<String, Timer> timers = new ConcurrentHashMap<String, Timer>();

	private TimerUtils() {

	}

	static void initializeTimer(String uuidDate, Long timerCacheRisposta, Logger logger) {
		Timer timer = new Timer();
		startTimer(timer, uuidDate, timerCacheRisposta, logger);
		timers.put(uuidDate, timer);
		logger.debug("Timer avviato [" + uuidDate + "]");
	}

	private static void startTimer(Timer timer, final String uuidDate, Long timerCacheRisposta, final Logger logger) {
		if (timerCacheRisposta == null) {
			logger.debug("Uso DEFAULT_TIMEOUT");
			timerCacheRisposta = DEFAULT_TIMEOUT;
		}
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				TimedCacheManager.getListaDatiOutput().remove(uuidDate);
				cancelTimer(uuidDate, logger);
			}
		}, timerCacheRisposta);
	}

	private static void refreshTimer(String uuidDate, Long timerCacheRisposta, Logger logger) {
		cancelTimer(uuidDate, logger);
		initializeTimer(uuidDate, timerCacheRisposta, logger);
		logger.debug("Timer refreshato [" + uuidDate + "]");
	}

	/**
	 * @deprecated
	 */
	@Deprecated
	static void cancelTimer(String uuidDate, org.apache.log4j.Logger logger) {
		cancelTimer(uuidDate, Logger.log4j2Logger(logger));
	}

	static void cancelTimer(String uuidDate, Logger logger) {
		Timer timer = timers.remove(uuidDate);
		if (timer != null) {
			try {
				timer.cancel();
			} catch (IllegalStateException e) {
				logger.error("Timer già cancellato", e);
			}
		}
	}

	/**
	 * @deprecated
	 */
	@Deprecated
	static void handleTimer(String uuidDate, Long timerCacheRisposta, org.apache.log4j.Logger logger) {
		handleTimer(uuidDate, timerCacheRisposta, Logger.log4j2Logger(logger));
	}

	static void handleTimer(String uuidDate, Long timerCacheRisposta, Logger logger) {
		if (!timers.containsKey(uuidDate)) {
			initializeTimer(uuidDate, timerCacheRisposta, logger);
		} else {
			refreshTimer(uuidDate, timerCacheRisposta, logger);
		}
	}
}
