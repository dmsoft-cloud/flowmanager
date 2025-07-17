package it.dmsoft.flowmanager.agent.engine.generic.clientWs.utility.jaxbAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

public class DateTime {

	private XMLGregorianCalendar date;

	public DateTime() {

	}

	public DateTime(String dateString) throws ParseException, DatatypeConfigurationException {
		Date date;
		SimpleDateFormat simpleDateFormat;
		GregorianCalendar gregorianCalendar;

		simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		date = simpleDateFormat.parse(dateString);
		gregorianCalendar = (GregorianCalendar) GregorianCalendar.getInstance();
		gregorianCalendar.setTime(date);
		this.date = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar);
	}

	public static DateTime factory(String date) throws Exception {
		return (new DateTimeAdapter()).unmarshal(date);
	}

	public void setDate(XMLGregorianCalendar date) {
		this.date = date;
	}

	public DateTime(XMLGregorianCalendar date) {
		super();
		this.date = date;
	}

	public GregorianCalendar toGregorianCalendar() {
		if (this.date == null) {
			return null;
		}
		return this.date.toGregorianCalendar();
	}

	@Override
	public String toString() {
		return "DateTime [date=" + date + "]";
	}
}
