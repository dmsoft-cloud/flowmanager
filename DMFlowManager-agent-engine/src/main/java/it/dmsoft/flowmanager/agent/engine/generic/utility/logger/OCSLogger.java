package it.dmsoft.flowmanager.agent.engine.generic.utility.logger;

import it.dmsoft.flowmanager.agent.engine.generic.genericWsClient.GenericWsManager;

/**
 * Logger personalizzato OCS basato su log4j
 * 
 * @author Torri Luca
 * @version 1.0
 * @deprecated usare {@link it.ocsnet.utility.logger.Logger}
 */
@Deprecated
public class OCSLogger {

	private OCSLogger() {

	}

	/**
	 * Imposta il logger per la classe passata solo se non possiede appender e
	 * modifica la chiave. Se la classe non viene passata, o è <i>null</i>,
	 * viene impostato il rootLogger. Se uno dei valori tra {@code size},
	 * {@code maxBackupIndex}, {@code path}, {@code level} non è impostata il
	 * metodo non setta il logger.
	 * 
	 * @param size
	 *            dimensione massima del logger in MB
	 * @param maxBackupIndex
	 *            indice massimo per i logger
	 * @param path
	 *            path del file di log
	 * @param level
	 *            livello di log
	 * @param key
	 *            chiave indicativa del log
	 * @param clazz
	 *            classe sulla quale impostare il logger
	 * @param sysout
	 *            se <code>true</code> aggiunge un appender per lo standard
	 *            output.
	 * @throws Exception
	 *             In caso di errore in fase di generazione dell'appender
	 */
	public static void setLoggerOneAppender(String size, String maxBackupIndex, String path, String level, String key, String clazz, boolean sysout) throws Exception {
		Logger.getLogger(size, maxBackupIndex, path, level, key, clazz, GenericWsManager.getCliente(), null);
	}

	/**
	 * Richiama
	 * {@link #setLoggerOneAppender(String, String, String, String, String, String, boolean)}
	 * impostando {@code sysout} a <code>false</code>
	 * 
	 * @param size
	 *            dimensione massima del logger in MB
	 * @param maxBackupIndex
	 *            indice massimo per i logger
	 * @param path
	 *            path del file di log
	 * @param level
	 *            livello di log
	 * @param key
	 *            chiave indicativa del log
	 * @param clazz
	 *            classe sulla quale impostare il logger
	 * @throws Exception
	 *             In caso di errore in fase di generazione dell'appender
	 */
	public static void setLoggerOneAppender(String size, String maxBackupIndex, String path, String level, String key, String clazz) throws Exception {
		setLoggerOneAppender(size, maxBackupIndex, path, level, key, clazz, false);
	}

	/**
	 * Imposta il logger per la classe passata solo se non possiede appender. Se
	 * la classe non viene passata, o è <i>null</i>, viene impostato il
	 * rootLogger. Se uno dei valori tra {@code size}, {@code maxBackupIndex},
	 * {@code path}, {@code level} non è impostata il metodo non setta il
	 * logger.
	 * 
	 * @param size
	 *            dimensione massima del logger in MB
	 * @param maxBackupIndex
	 *            indice massimo per i logger
	 * @param path
	 *            path del file di log
	 * @param level
	 *            livello di log
	 * @param key
	 *            chiave indicativa del log
	 * @param clazz
	 *            classe sulla quale impostare il logger
	 * @param sysout
	 *            se <code>true</code> aggiunge un appender per lo standard
	 *            output.
	 * @throws Exception
	 *             In caso di errore in fase di generazione dell'appender
	 */
	public static void setLoggerOneAppender(String size, String maxBackupIndex, String path, String level, String key, Class<?> clazz, boolean sysout) throws Exception {
		setLoggerOneAppender(size, maxBackupIndex, path, level, key, clazz.getName(), sysout);
	}

	/**
	 * Richiama
	 * {@link #setLoggerOneAppender(String, String, String, String, String, Class, boolean)}
	 * impostando {@code sysout} a <code>false</code>
	 * 
	 * @param size
	 *            dimensione massima del logger in MB
	 * @param maxBackupIndex
	 *            indice massimo per i logger
	 * @param path
	 *            path del file di log
	 * @param level
	 *            livello di log
	 * @param key
	 *            chiave indicativa del log
	 * @param clazz
	 *            classe sulla quale impostare il logger
	 * @throws Exception
	 *             In caso di errore in fase di generazione dell'appender
	 */
	public static void setLoggerOneAppender(String size, String maxBackupIndex, String path, String level, String key, Class<?> clazz) throws Exception {
		setLoggerOneAppender(size, maxBackupIndex, path, level, key, clazz, false);
	}

}
