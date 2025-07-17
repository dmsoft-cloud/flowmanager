package it.dmsoft.flowmanager.agent.engine.generic.clientWs.utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.XMLGregorianCalendar;

import it.dmsoft.flowmanager.agent.engine.generic.clientWs.utility.jaxbAdapter.DateTime;

public class CalendarManager {
	private Date date;
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMdd");
	private static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("HHmmssSS");
	private static final SimpleDateFormat YEAR_FORMAT = new SimpleDateFormat("yyyy");
	private static final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("yyyyMMddHHmmss");
	private static final SimpleDateFormat TIS_FORMAT = new SimpleDateFormat("yyyyMMddHHmm.ssSSS");

	public CalendarManager(XMLGregorianCalendar xmlGregorianCalendar) {
		if (xmlGregorianCalendar == null) {
			this.date = null;
		} else {
			this.date = xmlGregorianCalendar.toGregorianCalendar().getTime();
		}
	}

	public CalendarManager(DateTime dateTime) {
		if (dateTime == null) {
			this.date = null;
		} else {
			this.date = dateTime.toGregorianCalendar().getTime();
		}
	}

	public int getDateInt() {
		try {
			return Integer.parseInt(getDateString());
		} catch (NumberFormatException e) {
			return 0;
		} catch (Exception e) {
			return 0;
		}
	}

	public String getDateString() {
		String t = DATE_FORMAT.format(this.date);
		if (t.length() > 8) {
			return t.substring(0, 8);
		} else {
			return t;
		}
	}

	public String getDateTimeString() {
		return DATE_TIME_FORMAT.format(this.date);
	}

	public String getTimeString() {
		return TIME_FORMAT.format(this.date);
	}

	public int getTimeInt() {
		try {
			String time = TIME_FORMAT.format(this.date);
			if (time.length() > 8) {
				time = time.substring(0, 8);
			}
			return Integer.parseInt(time);
		} catch (NumberFormatException e) {
			return 0;
		} catch (Exception e) {
			return 0;
		}
	}

	public int getYear() {
		try {
			return Integer.parseInt(YEAR_FORMAT.format(this.date));
		} catch (NumberFormatException e) {
			return 0;
		} catch (Exception e) {
			return 0;
		}
	}

	public String getTimeStampString() {
		return TIS_FORMAT.format(date);
	}

	/**
	 * Metodo per convertire data in formato Epoch in millisecondi a data
	 * standard OCS
	 * 
	 * @param date
	 * @return stringa in formato standard OCS (yyyyMMdd) Note : Dopo vari
	 *         tentativi di restituzione della data come Long abbiamo deciso di
	 *         restituirla come int per alcune problematiche legate alla
	 *         gestione dei Long su COBOL
	 * @throws DatatypeConfigurationException
	 * @throws ParseException
	 */
	public static DateTime epochMSToDate(Long epochLongTime) throws ParseException, DatatypeConfigurationException {
		// epochLongTime è in millisecondi
		if (epochLongTime == null) {
			return null;
		}
		return NumericManager.parseDateTime(DATE_TIME_FORMAT.format(new Date(epochLongTime)));
	}
}
