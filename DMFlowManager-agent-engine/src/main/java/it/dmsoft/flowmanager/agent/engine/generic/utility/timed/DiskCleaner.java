package it.dmsoft.flowmanager.agent.engine.generic.utility.timed;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
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
public class DiskCleaner {
	public static final long MILLIS_IN_2_DAYS = 172800000;

	private static Map<String, Date> lastChecks = new ConcurrentHashMap<String, Date>();

	private DiskCleaner() {

	}

	private static boolean isCleanNeeded(String path, Logger logger) {
		Date lastChecked = lastChecks.get(path);
		if (lastChecked == null) {
			logger.debug("Mai controllato, aggiungo '" + path + "'");
			lastChecks.put(path, new Date());
			return true;
		} else {
			return isOlderThanTwoDays(lastChecked);
		}
	}

	private static boolean isOlderThanTwoDays(Date date) {
		return System.currentTimeMillis() - date.getTime() > MILLIS_IN_2_DAYS;
	}

	private static Date getDateFromString(String dateString) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(TimedCacheManager.DATE_PATTERN);
		Date date = sdf.parse(dateString);
		return date;
	}

	/**
	 * @deprecated
	 */
	/*
	@Deprecated
	public static void cleanDisk(String path, org.apache.log4j.Logger logger) {
		cleanDisk(path, Logger.log4j2Logger(logger));
	}
	*/

	public static void cleanDisk(String path, Logger logger) {
		if (isCleanNeeded(path, logger)) {
			logger.debug("Avvio la pulizia in " + path);
			File dir = new File(path);
			if (dir != null && dir.listFiles() != null) {
				for (File f : dir.listFiles()) {
					if (!f.isDirectory()) {
						deleteFileIfOlderThanTwoDays(f, logger);
					}
				}
			}
			lastChecks.put(path, new Date());
		} else {
			logger.debug("Pulizia non necessaria, ultimo controllo effettuato in data " + lastChecks.get(path));
		}
	}

	private static void deleteFileIfOlderThanTwoDays(File f, Logger logger) {
		String filename = f.getName();
		try {
			String dateString = filename.substring(0, TimedCacheManager.DATE_PATTERN.length());
			Date date = getDateFromString(dateString);
			if (isOlderThanTwoDays(date)) {
				f.delete();
				logger.debug("Cancellato " + filename);
			}
		} catch (ParseException e) {
			logger.error("Nome file '" + filename + "' non valido", e);
		}
	}
}
