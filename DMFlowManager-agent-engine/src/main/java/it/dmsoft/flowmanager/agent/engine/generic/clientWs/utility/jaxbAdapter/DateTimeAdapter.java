package it.dmsoft.flowmanager.agent.engine.generic.clientWs.utility.jaxbAdapter;

import java.text.SimpleDateFormat;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import it.dmsoft.flowmanager.agent.engine.generic.utility.logger.Logger;


public class DateTimeAdapter extends XmlAdapter<String, DateTime> {

	@Override
	public String marshal(DateTime v) throws Exception {
		if (v == null) {
			return "";
		}
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		fmt.setCalendar(v.toGregorianCalendar());
		String dateFormatted = fmt.format(v.toGregorianCalendar().getTime());
		Logger.getLogger(DateTimeAdapter.class).debug("dateFormatted: " + dateFormatted);
		return dateFormatted;
	}

	@Override
	public DateTime unmarshal(String v) throws Exception {
		XMLGregorianCalendar gc = DatatypeFactory.newInstance().newXMLGregorianCalendar(v);
		return new DateTime(gc);
	}

}