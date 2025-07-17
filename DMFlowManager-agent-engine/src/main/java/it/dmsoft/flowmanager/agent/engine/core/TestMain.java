package it.dmsoft.flowmanager.agent.engine.core;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.bouncycastle.util.encoders.BufferedDecoder;

import com.fasterxml.jackson.databind.util.ByteBufferBackedInputStream;

import it.dmsoft.flowmanager.agent.engine.core.utils.Constants;

public class TestMain {

	public static void main(String[] args) throws IOException {
		
		 // Stringa ASCII di input
        String asciiString = "OM";
        Charset charset_out = Charset.forName("IBM1144");
        Charset charset_in = Charset.forName("ASCII");
        


        // Codifica in EBCDIC (cp037 è uno dei charset per EBCDIC)
        Charset ebcdicCharset = Charset.forName("Cp037");
        
        // Conversione da ASCII a EBCDIC
        byte[] ebcdicBytes = asciiString.getBytes(charset_out);
        // Array di stringhe esadecimali
        
        
        String result = IntStream.range(0, ebcdicBytes.length)
                .mapToObj(i -> (char) (ebcdicBytes[i] & 0xFF))  // Applica il cast
                .map(String::valueOf) // Converte ogni char in una stringa
                .collect(Collectors.joining());  // Combina in una singola stringa
        // Stampa il carattere ASCII
        System.out.println("Carattere ASCII: " + result);
        
     // Scrittura su file con buffering
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream("C:\\Users\\diegoa\\logs\\DIE1\\test.txt"));
     String st = "OM";
     bos.write(st.getBytes(charset_out));
     bos.flush();
     byte[] endline = {(0x0D),(0x25)}  ;
     bos.write(endline);  
     bos.write(st.getBytes(charset_out));
     bos.flush();

	}

}
