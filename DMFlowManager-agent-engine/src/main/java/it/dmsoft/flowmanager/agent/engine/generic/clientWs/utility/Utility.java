package it.dmsoft.flowmanager.agent.engine.generic.clientWs.utility;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import it.dmsoft.flowmanager.agent.engine.generic.clientWs.utility.jaxbAdapter.DateTime;
import it.dmsoft.flowmanager.agent.engine.generic.utility.logger.Logger;

public class Utility {
	protected static Logger logger;
	private static DateFormat formatterYMD;
	private static DateFormat formatterYMDhms;
	private static List<String> aoMustmask;
	private static final String PACKAGE_MUST_MASK;

	static {
		aoMustmask = new ArrayList<String>(28);
		aoMustmask.add("CARTASICLIENTBTL");
		aoMustmask.add("CARTASICLIENTCAI");
		aoMustmask.add("CARTASICLIENTCAS");
		aoMustmask.add("CARTASICLIENTCCA");
		aoMustmask.add("CARTASICLIENTCCL");
		aoMustmask.add("CARTASICLIENTCCP");
		aoMustmask.add("CARTASICLIENTCCS");
		aoMustmask.add("CARTASICLIENTCEN");
		aoMustmask.add("CARTASICLIENTCMP");
		aoMustmask.add("CARTASICLIENTCRI");
		aoMustmask.add("CARTASICLIENTCSA");
		aoMustmask.add("CARTASICLIENTCSL");
		aoMustmask.add("CARTASICLIENTCSM");
		aoMustmask.add("CARTASICLIENTCTI");
		aoMustmask.add("CARTASICLIENTGAD");
		aoMustmask.add("CARTASICLIENTGCD");
		aoMustmask.add("CARTASICLIENTGCL");
		aoMustmask.add("CARTASICLIENTGPD");
		aoMustmask.add("CARTASICLIENTICA");
		aoMustmask.add("CARTASICLIENTIDA");
		aoMustmask.add("CARTASICLIENTIPC");
		aoMustmask.add("CARTASICLIENTISC");
		aoMustmask.add("CARTASICLIENTRC ");
		aoMustmask.add("CARTASICLIENTRCP");
		aoMustmask.add("CARTASICLIENTRLC");
		aoMustmask.add("CARTASICLIENTSRP");
		aoMustmask.add("CARTASICLIENTUTL");
		aoMustmask.add("SIAONLINEAUTHORIZATIONCLIENT");

		PACKAGE_MUST_MASK = "it.ocsnet.cartasi";

		formatterYMD = new SimpleDateFormat("yyyyMMdd");
		formatterYMD.setLenient(false);
		formatterYMDhms = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		formatterYMDhms.setLenient(false);
		logger = Logger.getLogger(Utility.class);
	}

	private Utility() {

	}

	/**
	 * Converte una data da tipo {@link java.util.date} a int
	 * 
	 * @param date
	 *            nel formato <i>yyyyMMdd</i>
	 * @return La data nel formato <i>yyyyMMdd</i>
	 */
	public static int date2int(Date date) {
		logger.debug("Chiamato date2int");
		try {
			return Integer.parseInt(formatterYMD.format(date));
		} catch (NumberFormatException ex) {
			logger.error("Errore in fase di conversione data", ex);
			throw ex;
		}
	}

	/**
	 * Converte una data da int a tipo {@link java.util.date}
	 * 
	 * @param date
	 *            nel formato <i>yyyyMMdd</i>
	 * @return La data nel formato <i>yyyyMMdd</i>
	 * @throws ParseException
	 */
	public static Date int2date(int date) throws ParseException {
		logger.debug("Data input 'int2date': " + date);
		Date dateObj = new Date();
		try {
			dateObj = formatterYMD.parse(String.valueOf(date));
		} catch (ParseException e) {
			logger.error("Errore in fase di parsiong della data: '" + date + "'", e);
			throw e;
		}
		logger.debug("Data output 'int2date': " + dateObj);
		return dateObj;
	}

	/**
	 * Converte una data da int a tipo
	 * {@link it.ocsnet.clientWs.utility.jaxbAdapter.DateTime}
	 * 
	 * @param date
	 *            nel formato <i>yyyyMMddhhmmss</i>
	 * @return La data nel formato <i>yyyyMMddddhhmmss</i>
	 * @throws ParseException
	 * 
	 */
	@Deprecated
	public static DateTime int2dateTime(int date) throws ParseException {
		logger.debug("Data input 'int2dateTime': " + date);
		logger.warn("Metodo deprecato !!");
		return null;
	}

	/**
	 * Converte una data da int a tipo
	 * {@link javax.xml.datatype.XMLGregorianCalendar}
	 * 
	 * @param date
	 *            nel formato <i>yyyyMMdd</i>
	 * @return
	 * @throws ParseException
	 */
	public static XMLGregorianCalendar int2calendar(int date) throws ParseException, DatatypeConfigurationException {
		logger.debug("Data input 'int2calendar': " + date);
		Date dateObj = int2date(date);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateObj);
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(dateObj);
		XMLGregorianCalendar caldendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(gc);
		logger.debug("Data output 'int2calendar': " + calendar);
		return caldendar;
	}

	/**
	 * Converte una data da {@link java.math.BigDecimal} a tipo
	 * {@link java.util.date}
	 * 
	 * @param date
	 *            nel formato <i>yyyyMMdd.HHmmssSSS</i>
	 * @return La data nel formato <i>yyyyMMdd.HHmmssSSS</i>
	 * @throws ParseException
	 */
	public static Date bdec2date(BigDecimal date) throws ParseException {
		logger.debug("Data input 'bdec2date': " + date);
		Date dateObj = new Date();

		// La lunghezza totale del dato di input deve essere di 16 caratteri nel
		// formato AAAAMMGGHHmmssss. Se la stringa è più corta aggiunge in testa
		// tanti zero quanti sono necessari
		StringBuffer dateTmp = new StringBuffer(date.toString());
		while (dateTmp.length() < 16) {
			dateTmp.insert(0, "0");
		}

		try {
			dateObj = formatterYMDhms.parse(dateTmp.toString());
		} catch (ParseException e) {
			logger.error("Errore in fase di parsing della data: '" + date + "'", e);
			throw e;
		}
		logger.debug("Data output 'bdec2date': " + dateObj);
		return dateObj;
	}

	/**
	 * Converte una data da {@link java.math.BigDecimal} a tipo
	 * {@link javax.xml.datatype.XMLGregorianCalendar}
	 * 
	 * @param date
	 *            nel formato <i>yyyyMMdd.HHmmssSSS</i>
	 * @return
	 * @throws ParseException
	 */
	public static XMLGregorianCalendar bdec2calendar(BigDecimal date) throws ParseException, DatatypeConfigurationException {
		logger.debug("Data input 'bdec2calendar': " + date);
		Date dateObj = bdec2date(date);
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(dateObj);
		XMLGregorianCalendar calendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(gc);
		logger.debug("Data output 'bdec2calendar': " + calendar);
		return calendar;
	}

	/**
	 * Converte una data da {@link java.math.BigDecimal} a tipo {@link DateTime}
	 * 
	 * @param date
	 *            nel formato <i>yyyyMMdd.HHmmssSSS</i>
	 * @return
	 * @throws ParseException
	 * @throws DatatypeConfigurationException
	 */
	public static DateTime bdec2datetime(BigDecimal date) throws ParseException, DatatypeConfigurationException {
		return new DateTime(bdec2calendar(date));
	}

	private static final Pattern HEXADECIMAL_PATTERN = Pattern.compile("\\p{XDigit}+");

	public static boolean isHexadecimal(String input) {
		if (input == null) {
			return false;
		}
		final Matcher matcher = HEXADECIMAL_PATTERN.matcher(input);
		return matcher.matches();
	}

	/**
	 * Legge un file da path e restituisce la stringa dei dati letti
	 * 
	 * @param filePath
	 *            path del file
	 * @return String del contenuto del fils
	 * @throws IOException
	 */
	public static String file2String(String filePath) throws IOException {
		return FileUtils.readFileToString(new File(filePath), "UTF-8");
	}

	public static void string2File(String content, String filePath) throws IOException {
		FileUtils.writeStringToFile(new File(filePath), content, "UTF-8");
	}

	public static String defineFilePath(String logPath, String jobNumber, String keyLogTmp) {
		return defineFilePath(logPath, jobNumber, keyLogTmp, "");
	}

	public static String defineFilePath(String logPath, String jobNumber, String keyLogTmp, String logType) {
		if (!"".equals(logType)) {
			logType = "_" + logType;
		}
		StringBuilder builder = new StringBuilder();
		String fileNameWithOutExt = FilenameUtils.getBaseName(logPath);
		String fileExtension = FilenameUtils.getExtension(logPath);
		String dotExtension = ".";
		if (fileExtension == null || "".equals(fileExtension)) {
			dotExtension = "";
		}
		String path = FilenameUtils.getFullPathNoEndSeparator(logPath);
		String date = (new SimpleDateFormat("yyyyMMdd")).format(new Date());
		String dateTime = (new SimpleDateFormat("HHmmss")).format(new Date());
		builder.append(path).append(File.separator).append(date).append(File.separator).append(fileNameWithOutExt).append("_").append(dateTime).append("_").append(jobNumber).append("_")
				.append(keyLogTmp).append(logType).append(dotExtension).append(fileExtension);
		return builder.toString();
	}

	public static String maskMessage(String message) {
		return maskMessage(new StringBuilder(message));
	}

	public static String maskMessage(StringBuilder message, String aoPackage) {
		if (aoMustmask.contains(aoPackage) || (aoPackage != null && aoPackage.startsWith(PACKAGE_MUST_MASK))) {
			return maskMessage(message);
		}
		return message.toString();
	}

	public static String maskMessage(String message, String aoPackage) {
		if (aoMustmask.contains(aoPackage) || (aoPackage != null && aoPackage.startsWith(PACKAGE_MUST_MASK))) {
			return maskMessage(message);
		}
		return message;
	}

	/*
	 * Metodo per oscuramento PAN (PAN composto da 16 cifre) e oscuramento
	 * base64.
	 */
	private static String maskMessage(StringBuilder message) {
		Pattern panPatter = Pattern.compile("([0-9]){16}");

		// Verifica se messaggio è vuoto
		if (message.length() <= 0) {
			return "";
		}
		Matcher m = null;

		// PAN
		long start = System.currentTimeMillis();
		boolean pan = false;
		m = panPatter.matcher(message);

		while (m.find()) {
			if (isValidPan(m.group())) {
				pan = true;
				message.replace(m.start(), m.end(), "****************");
			}
		}

		if (pan) {
			message.insert(0, "(Durata replace PAN: " + (System.currentTimeMillis() - start) + " ms) ");
		}

		/*
		 * TODO: da rivedere perché va a mascherare anche pezzi di codice che
		 * non centrano ad esempio (tratto dal file OcsMailClient.log in
		 * J:\tmp\AO\log\B2B): 15 nov 2022 11:49:38,385 - [...]
		 * allegati=[Allegato [pbase64Data_maskedne, contentId=]] al posto di:
		 * 08 nov 2022 13:00:49,955 - [...] allegati=[Allegato
		 * [path=/home/lucat/mail/AgosCartolarizzazione, contentId=]]
		 * 
		 * 
		 */

		// Base64
		// if (message.length() >= 300) {
		// start = System.currentTimeMillis();
		// m = base64Patter.matcher(message);
		// if (m.find()) {
		// message.replace(0, message.length(),
		// m.replaceAll("base64Data_masked"));
		// message.insert(0, "(Durata replace BASE64: " +
		// (System.currentTimeMillis() - start) + " ms) ");
		// }
		//
		// }
		return message.toString();
	}

	private static boolean isValidPan(String cardNo) {
		int nDigits = cardNo.length();

		int nSum = 0;
		boolean isSecond = false;
		
		// Luhn Alhorithm
		for (int i = nDigits - 1; i >= 0; i--) {

			int d = Integer.parseInt("" + cardNo.charAt(i));

			if (isSecond) {
				d = d * 2;
			}

			// We add two digits to handle
			// cases that make two digits
			// after doubling
			nSum += d / 10;
			nSum += d % 10;

			isSecond = !isSecond;
		}
		
		return (nSum % 10 == 0);
	}
}
