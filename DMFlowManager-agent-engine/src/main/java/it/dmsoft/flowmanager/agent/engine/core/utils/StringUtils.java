package it.dmsoft.flowmanager.agent.engine.core.utils;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.StringTokenizer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class StringUtils {
	
    // Mappatura dei charset disponibili
    private static final Map<Integer, String> charsetMap = new HashMap<>();
    static {
        charsetMap.put(500, "IBM500");
        charsetMap.put(1047, "IBM1047");
        charsetMap.put(1144, "IBM1144");
        charsetMap.put(280, "IBM280");
        charsetMap.put(37, "IBM037");
        charsetMap.put(819, "ISO-8859-1");
        
        // Nuovi charset aggiunti
        charsetMap.put(819, "ISO-8859-1");  // ISO 8859-1 
        charsetMap.put(923, "ISO-8859-15"); // ASCII euro (ISO-8859-15, variante con il simbolo €)
        charsetMap.put(1208, "UTF-8");
    }
    
    // Mappatura dei charset disponibili
    private static final Map<Integer, String> charsetMapEbcdic = new HashMap<>();
    static {
        charsetMap.put(500, "IBM500");
        charsetMap.put(1047, "IBM1047");
        charsetMap.put(1144, "IBM1144");
        charsetMap.put(280, "IBM280");
        charsetMap.put(37, "IBM037");
        charsetMap.put(819, "ISO-8859-1");

    }
	
	public static boolean isNullOrEmpty(String str) {
		return str == null || str.trim().length() == 0 ? true : false;
	}
	
	public static String setDefault(String str, String def) {
		return isNullOrEmpty(str) ? def : str;
	}

	public static String leftPad(String str, char padChar, int padLength) {
		return String.format("%1$" + padLength + "s", str).replace(' ', padChar);
	}
	
	public static String rightPad(String str, char padChar, int padLength) {
		return String.format("%1$-" + padLength + "s", str).replace(' ', padChar);
	}
	
	public static Timestamp stringToTimeStamp(String str) throws ParseException {
		
		SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.FORMAT_TIMESTAMP);
		Date parsedDate = null; 
		
		
		if(!isNullOrEmpty(str))
		{
			
			 parsedDate = dateFormat.parse(str);
			 return new Timestamp(parsedDate.getTime());
		}
		
		Timestamp t = Timestamp.valueOf(Constants.EMPTY_TIMESTAMP);
		
		return t;
	
	}
	
	public List<String> tokenize(String inputStr, String delimiters) {
		StringTokenizer multiTokenizer = new StringTokenizer(inputStr, delimiters);
		 
		List<String> ret = new ArrayList<String>();
		while (multiTokenizer.hasMoreTokens())
		{
		    ret.add(multiTokenizer.nextToken());
		}
		
		return ret;
	}

	public static String escapeOtherRegexpChars(String inputStr) {
		for (String word : Constants.REGEXP_NOT_SUPPORTED) {
			inputStr = inputStr.replace(word, "\\" + word);
		}
		
		return inputStr;
	}

	public static String removePath(String fileName) {
		return fileName.substring(fileName.lastIndexOf(Constants.PATH_DELIMITER) +1, fileName.length());
	}
	
	public static String convertCharset(int charset, String st) {
		
		//return Convert(st);
		
        if (!charsetMap.containsKey(charset)) {
             return st; // Ritorna la stringa inalterata
        }
		
        Charset ebcdicCharset = Optional.ofNullable(charsetMap.get(charset))
                .map(Charset::forName)
                .orElse(Charset.forName("IBM1047"));

        // Converti la stringa ASCII in un array di byte EBCDIC
        byte[] ebcdicBytes = st.getBytes(ebcdicCharset);
        
        String result = IntStream.range(0, ebcdicBytes.length)
                .mapToObj(i -> (char) (ebcdicBytes[i] & 0xFF))  // Applica il cast
                .map(String::valueOf) // Converte ogni char in una stringa
                .collect(Collectors.joining());  // Combina in una singola stringa
        // Stampa il carattere ASCII
        System.out.println("Carattere ASCII: " + result);
        
        System.out.println("caratteri esadecimali: ");
        result.chars()  // Ottieni lo stream di interi (valori UTF-16 dei caratteri)
        .forEach(c -> System.out.printf("%02X ", c));
        System.out.println("\r\n");
        
        return result;
        
	}
	
	public static Charset getCharset(int charset) {
        Charset cs = Optional.ofNullable(charsetMap.get(charset))
                .map(Charset::forName)
                .orElse(Charset.forName("ASCII"));
        
        return cs;
	}
	
    // Metodo che controlla se l'ID è presente nella mappa
    public static boolean isCharsetSupported(int charsetId) {
        return charsetMapEbcdic.containsKey(charsetId);
    }
	
	
}
