package it.dmsoft.flowmanager.agent.engine.generic.utility.timed;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import com.fasterxml.jackson.databind.ObjectMapper;

import it.dmsoft.flowmanager.agent.engine.generic.utility.logger.Logger;

/**
 * La classe viene utilizzata solo da InterfacciaExperianClient. Per la
 * rimoazione dei metodi deprecati dobbiamo essere certi che nessun cliente usi
 * più l'applicazione vecchia
 * 
 * @author lucat
 *
 */
public class TimedCacheManager {
	public static final String DATE_PATTERN = "yyyyMMdd";
	private static Map<String, Object> listaDatiOutput = new ConcurrentHashMap<String, Object>();

	private TimedCacheManager() {

	}

	/**
	 * @deprecated
	 */
	@Deprecated
	public static String getSerializedObjPath(String jarPath, String wsdlDirectory) {
		File jarFile = new File(jarPath);
		File directory = jarFile.getParentFile();
		File wsdlDirectoryFile = new File(directory, wsdlDirectory);
		String path = wsdlDirectoryFile.getAbsolutePath();
		return path;
	}

	public static String getSerializedObjPath(String pathTemporaryFile) {
		File pathTmp = new File(pathTemporaryFile);
		return pathTmp.getAbsolutePath();
	}

	public static <T> String saveDataInCache(T data, String path, Logger logger, Long timerCacheRisposta) throws IOException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_PATTERN);
		String dateString = simpleDateFormat.format(new Date());

		String uuid = UUID.randomUUID().toString();
		String uuidDate = dateString + "-" + uuid;
		String filePath = path + File.separator + uuidDate;

		ObjectMapper mapper = new ObjectMapper();
		mapper.writeValue(new File(filePath), data);
		logger.debug("Salvato: " + uuidDate + "; File: " + filePath);
		listaDatiOutput.put(uuidDate, data);

		TimerUtils.initializeTimer(uuidDate, timerCacheRisposta, logger);

		return uuidDate;
	}

	/**
	 * @deprecated
	 */
	@Deprecated
	/*
	public static <T> String saveDataInCache(T data, String path, org.apache.log4j.Logger logger, Long timerCacheRisposta) throws IOException {
		return saveDataInCache(data, path, Logger.log4j2Logger(logger), timerCacheRisposta);
	}
	*/

	@SuppressWarnings("unchecked")
	public static <T> T getCachedData(String uuidDate, String path, Class<T> clazz, Long timerCacheRisposta, Logger logger) throws IOException, ClassNotFoundException {
		if (!listaDatiOutput.containsKey(uuidDate)) {
			String filePath = path + File.separator + uuidDate;
			ObjectMapper mapper = new ObjectMapper();
			T data = mapper.readValue(new File(filePath), clazz);
			listaDatiOutput.put(uuidDate, data);
			logger.debug("Lettura dati da disco [" + uuidDate + "]");
		} else {
			logger.debug("Lettura dati da memoria java [" + uuidDate + "]");
		}
		TimerUtils.handleTimer(uuidDate, timerCacheRisposta, logger);
		return (T) listaDatiOutput.get(uuidDate);
	}

	/**
	 * @deprecated
	 */
	/*
	@Deprecated
	public static <T> T getCachedData(String uuidDate, String path, Class<T> clazz, Long timerCacheRisposta, org.apache.log4j.Logger logger) throws IOException, ClassNotFoundException {
		return getCachedData(uuidDate, path, clazz, timerCacheRisposta, Logger.log4j2Logger(logger));
	}
	*/

	// TODO restituire un ENUM
	public static <T> String removeCachedData(String uuidDate, String path, Logger logger) {
		boolean rimossoMemoria = false;
		if (listaDatiOutput.containsKey(uuidDate)) {
			if (listaDatiOutput.remove(uuidDate) != null) {
				rimossoMemoria = true;
				TimerUtils.cancelTimer(uuidDate, logger);
			}
		}
		File f = new File(path + File.separator + uuidDate);
		boolean rimossoDisco = f.delete();
		if (rimossoMemoria && rimossoDisco) {
			return "RIMOSSO_DISCO_MEMORIA";
		} else if (rimossoMemoria && !rimossoDisco) {
			return "RIMOSSO_MEMORIA";
		} else if (!rimossoMemoria && rimossoDisco) {
			return "RIMOSSO_DISCO";
		} else {
			return "NON_RIMOSSO";
		}
	}

	/**
	 * @deprecated
	 */
	/*
	@Deprecated
	public static <T> String removeCachedData(String uuidDate, String path, org.apache.log4j.Logger logger) {
		return removeCachedData(uuidDate, path, Logger.log4j2Logger(logger));
	}
	*/

	static Map<String, Object> getListaDatiOutput() {
		return listaDatiOutput;
	}

}
