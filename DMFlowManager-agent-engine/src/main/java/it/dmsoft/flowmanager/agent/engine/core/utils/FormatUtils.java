package it.dmsoft.flowmanager.agent.engine.core.utils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class FormatUtils {
	
	
	
	/**
     * Restituisce la data odierna in formato Date
     * 
     * @return la data odierna <i>(Date)</i>
     */
    public static Date todayDate() {
        return new Date();
    }
    
    /**
     * Converte la data di tipo Date in BigDecimal
     * 
     * @param d <i>(Date)</i>:
     * @return la data convertita e di tipo yyyyMMdd <i>(BigDecimal)</i>
     */
    public static BigDecimal date(Date d) {
        return dateToBigDec(d, "yyyyMMdd");
    }
    
    public static BigDecimal date6(Date d) {
        return dateToBigDec(d, "yyMMdd");
    }
    
    /**
     * Restituisce la data convertita dal formato yyyyMMdd a dataNumero
     * 
     * @param data8 <i>(String)</i>: data da convertire.
     * @return la data convertita <i>(long)</i>
     */
    public static Date date(BigDecimal dataBD) {
        return cvtDate(dataBD.toString(), "LI");
    }
    
    private enum DateFormats {
    	LN("ddMMyyyy"),
    	LI("yyyyMMdd"),
    	LLI("yyyy/MM/dd"),
    	LUN("yyyy_MM_dd"),
    	LDN("yyyy-MM-dd"),
    	LUI("dd_MM_yyyy"),
    	LDI("dd-MM-yyyy"),
    	LLN("dd/MM/yyyy"),
    	NN("ddMMyy"),
    	NNN("dd/MM/yy"),
    	NI("yyMMdd"),
    	NNI("yy/MM/dd"),
    	MA("MMyyyy"),
    	MAU("MM_yyyy"),
    	MAD("MM-yyyy"),
    	AM("yyyyMM"),
    	AMU("yyyy_MM"),
    	AMD("yyyy-MM"),
    	AMS("yyMM"),
    	MD("MMdd"),
    	MDU("MM_dd"),
    	MDD("MM-dd"),
    	DM("ddMM"),
    	DMU("dd_MM"),
    	DMD("dd-MM");
    	
    	private String format;
    	
    	private DateFormats(String format) {
    		this.format = format;
    	}
    	
    	public String getFormat() {
    		return this.format;
    	}
    	
    	public static DateFormats getDateFormats(String code) {
    		for (DateFormats dateFormats : DateFormats.values()) {
    			if (dateFormats.name().equals(code)) {
    				return dateFormats;
    			}
    		}
    		
    		return null;
    	}
    }
    
    public static String formatDate(Date date, String formatoinp) {
        String fi = "";
        
        DateFormats df = DateFormats.getDateFormats(formatoinp);
        
        if (df != null) {
            fi = df.getFormat();
        } else {
            fi = formatoinp;
        }
        
        try {
            return new SimpleDateFormat(fi).format(date);
        } catch (Exception e) {
            return null;
        }
    }
    
    private static Date cvtDate(String d, String formatoinp) {
        String fi = "";

        DateFormats df = DateFormats.getDateFormats(formatoinp);
        
        if (df != null) {
            fi = df.getFormat();
        } else {
            fi = formatoinp;
        }
        
        try {
            return new SimpleDateFormat(fi).parse(d);
        } catch (Exception e) {
            return null;
        }
    }

    
    /**
     * Converte una data in un BigDecimal formattato
     * 
     * @param d <i>(Date)</i>: data da formattare.
     * @param formatoOut <i>(String)</i>: formato per la variabile di output.
     * @return la data formattata <i>(BigDecimal)</i>
     */
    public static BigDecimal dateToBigDec(Date d, String formatoOut) {
        SimpleDateFormat cvS = new SimpleDateFormat(formatoOut);
        /*
         * for (int i=0;i <formatoOut.length();i++) if (formatoOut.charAt(i)!='M' && formatoOut.charAt(i)!= 'd' && formatoOut.charAt(i)!='y') return stringToBigDecInt("0");
         */
        if (d == null) {
            return null;//stringToBigDecInt("0");
        } else {
            String s = cvS.format(d);
            return stringToBigDecInt(s);
        }
    }
    
    /**
     * Converte un numero intero dal formato String al formato BigDecimal
     * 
     * @param f <i>(String)</i>: numero da convertire.
     * @return il numero convertito <i>(BigDecimal)</i>. Se l'operazione non va a buon fine la funzione ritorna 0.
     */
    public static BigDecimal stringToBigDecInt(String f) {
        BigDecimal bd = null;
        DecimalFormat df = new DecimalFormat();

        try {
            bd = new BigDecimal(df.parse(f).longValue());
        } catch (Exception e) {
            bd = new BigDecimal(0);
        }
        
        return bd;

    }
    
    /**
     * Restituisce la data odierna in formato BigDecimal
     * 
     * @return la data odierna <i>(BigDecimal)</i>
     */
    public static BigDecimal todayDateBigDec() {
        return date(FormatUtils.todayDate());
    }
    
    /**
     * Restituisce la data odierna in formato BigDecimal
     * 
     * @return la data odierna <i>(BigDecimal)</i>
     */
    public static BigDecimal todayDate6BigDec() {
        return date6(FormatUtils.todayDate());
    }
    
    
    /**
     * Restituisce l'ora in formato BigDecimal
     * 
     * @return l'ora  <i>(BigDecimal)</i>
     */
    public static BigDecimal actualTimeBigDec() {
        GregorianCalendar data = new GregorianCalendar();
        data.setTime(todayDate());
        String ms = "00";
        try {
            ms = fillDigits(data.get(Calendar.MILLISECOND), 3).substring(0, 2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new BigDecimal(actualTimeBigDec6().intValue() + ms);
        //BigDecimal ora = dataViToBigDec(Formati.dataOggi(), "HHmmssSS");
        //return new BigDecimal(ora.toString().substring(0, 8));
    }
    
    
    /**
     * Aggiunge degli zeri prima del valore finche non viene raggiunta la lunghezza impostata.
     * 
     * <p><em>Esempio</em>:
     * <blockquote>
     * BigDecimal valore = 1234;<br>
     * int lunghezza = 6;<br>
     * String res = slrNumDigits(valore,lunghezza);<br>
     * la variabile <i>res</i> avra come valore "001234"
     * </blockquote>
     * @param value <i>(long)</i>: valore da formattare.
     * @param length <i>(int)</i>: lunghezza della stringa in output.
     * @return il valore formattato <i>(String)</i>
     */
    public static String fillDigits(long value, int length) {
        DecimalFormat df = new DecimalFormat();
        df.setGroupingUsed(false);
        df.setMinimumIntegerDigits(length);
        return df.format(value);
    }
    
    /**
     * Restituisce l'ora in formato BigDecimal
     * 
     * @return l'ora  <i>(BigDecimal)</i>
     */
    public static BigDecimal actualTimeBigDec6() {
        return dateToBigDec(FormatUtils.todayDate(), "HHmmss");
        //BigDecimal ora = dataViToBigDec(Formati.dataOggi(), "HHmmssSS");
        //return new BigDecimal(ora.toString().substring(0, 6));
    }
    
    /**
     * Restituisce l'ora in formato BigDecimal
     * 
     * @return l'ora  <i>(BigDecimal)</i>
     */
    public static BigDecimal actualTimeBigDec2() {
        return dateToBigDec(FormatUtils.todayDate(), "HH");
    }
    
    public static BigDecimal actualTimeBigDec4() {
        return dateToBigDec(FormatUtils.todayDate(), "HHmm");
        //BigDecimal ora = dataViToBigDec(Formati.dataOggi(), "HHmmssSS");
        //return new BigDecimal(ora.toString().substring(0, 6));
    }
    
    public static String removeInvalidCharacters(String str){
    	return str != null ? str.replaceAll("[^\\x20-\\x7e]", " ") : null;
    }
    
    
    public static String fillBlank(String value, int i) {

        if (value == null) {
            return "";
        }
        else if (value.length() > i) {
            return value.substring(0, i);
        }

        return String.format("%1$-" + i + "s", value);
    }
	public static BigDecimal time(Date date) {
		return null;
	}
	
	public static BigInteger toBigInt(BigDecimal val) {
		return val != null ? val.toBigInteger() : null;
	}
	
	public static BigDecimal toBigDec(BigInteger val) {
		return val != null ? new BigDecimal(val) : null;
	}
    /*
	public Date toDateFromBigDec(BigDecimal data8) {
		try {
			return data8 != null ? new SimpleDateFormat("yyyyMMdd").parse(data8.toString()) : null;
		} catch (ParseException e) {
			logger.error("Date 8 parser Exception", e);
		}
		
		return null;
	}
	
	public BigDecimal fromDateToBigDec(Date data) {
		return data != null ? new BigDecimal(new SimpleDateFormat("yyyyMMdd").format(data)) : null;
	}
	*/
	
	public static String formatTimestamp() {
		return formatTimestamp(new Date(System.currentTimeMillis()));
	}
	
	public static String formatTimestamp(Date date) {
		return formatTimestamp(date, Constants.FORMAT_TIMESTAMP);
	}
	
	public static String formatTimestamp(Date date, String format) {
	    SimpleDateFormat sdf = new SimpleDateFormat(format);
	    sdf.setTimeZone(TimeZone.getDefault()); // Set the TimeZone to whatever you are expecting.
	    return sdf.format(date);
	}
	
	//Ritorna fine mese precedente/successivo/attuale
	public static String formatEndMonth(Date date, int deltaMonth) {
		return formatDeltaMonth(date, deltaMonth, null);
	}
	
	private static String formatDeltaMonth(Date date, int deltaMonth, Integer dayOfMonth) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MONTH, deltaMonth);
		
		dayOfMonth = dayOfMonth == null ? c.getActualMaximum(Calendar.DAY_OF_MONTH) : dayOfMonth;
		c.set(Calendar.DAY_OF_MONTH,  dayOfMonth);
	    
        return date(c.getTime()).toString();
	}
	
	//Ritorna inizio mese precedente/successivo/attuale
	public static String formatStartMonth(Date date, int deltaMonth) {
		return formatDeltaMonth(date, deltaMonth, 1);
	}
	
}
