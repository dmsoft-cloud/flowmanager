package it.dmsoft.flowmanager.agent.engine.generic.clientWs.utility;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.XMLGregorianCalendar;

import it.dmsoft.flowmanager.agent.engine.generic.clientWs.utility.exceptions.OCSDateNullException;
import it.dmsoft.flowmanager.agent.engine.generic.clientWs.utility.jaxbAdapter.DateTime;

public class NumericManager {
	private static BigDecimal forceDecimal;
	static {
		forceDecimal = new BigDecimal("100000");
	}
	
	public static short parseShort(String valore) {
		return new BigDecimal(valore).shortValue();
	}

	public static int parseInt(String valore) {
		return new BigDecimal(valore).intValue();
	}

	public static long parseLong(String valore) {
		return new BigDecimal(valore).longValue();
	}

	public static float parseFloat(String valore) {
		return new BigDecimal(valore).floatValue();
	}

	public static double parseDouble(String valore) {
		return new BigDecimal(valore).doubleValue();
	}

	public static BigInteger parseBigInteger(String valore) {
		return new BigDecimal(valore).toBigInteger();
	}

	public static XMLGregorianCalendar parseCalendar(String valore) throws ParseException, DatatypeConfigurationException {
		try {
			checkDate(valore);
		} catch (OCSDateNullException e) {
			return null;
		}

		return Utility.int2calendar(new BigDecimal(valore).intValue());
	}

	public static DateTime parseDateTime(String valore) throws ParseException, DatatypeConfigurationException {
		try {
			checkDate(valore);
		} catch (OCSDateNullException e) {
			return null;
		}

		// Aggiunge 2 zero finali sempre per valorizzare i centesimi di secondo
		return Utility.bdec2datetime(new BigDecimal(valore + "00"));
	}

	public static XMLGregorianCalendar parseTimeStamp(String valore) throws ParseException, DatatypeConfigurationException {
		try {
			checkDate(valore);
		} catch (OCSDateNullException e) {
			return null;
		}
		
		return Utility.bdec2calendar(new BigDecimal(valore).multiply(forceDecimal));
	}

	private static void checkDate(String valore) throws OCSDateNullException {
		if (valore == null || "".equals(valore.trim()) || "0".equals(valore.trim())) {
			throw new OCSDateNullException();
		}

		// Se il valore di input è tutto uguale a zero esce con null
		StringBuffer valoreTmp = new StringBuffer();
		for (int i = 0; i < valore.length(); i++) {
			valoreTmp.append("0");
		}
		if (valore.equals(valoreTmp.toString())) {
			throw new OCSDateNullException();
		}
	}
}
