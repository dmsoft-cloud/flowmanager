package it.dmsoft.flowmanager.agent.engine.generic.clientWs.utility;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * OcsInputStream serve per gestire la possibilit� che alcuni flussi sepa vegono consegnati come
 * un insieme di documenti xml su uno stesso file. Questo rende il file un documento xml non valido
 * dal punto di vista formale in quanto non � presente un unico nodo radice.
 * OcsInputStream aggiunge un tag all'inizio e alla fine del file eliminando le eventuali
 * xml declaration dei singoli documenti contenuti.
 * 
 * @author giovanni
 *
 */
public class OcsInputStream extends InputStream {

	private OcsBufferReader br;
	private int count;
	//End of file
	private boolean eof;
	//Beginning of file
	private boolean bof;
	private boolean readFromBuffer;
	private static final String OPEN_TAG = "<root>";
	private static final String CLOSE_TAG = "</root>";
	private final char buffer[];

	public OcsInputStream(String xml, String encoding) throws FileNotFoundException {
		super();
		if(encoding != null){
			this.br = new OcsBufferReader(xml, encoding);
		} else {
			this.br = new OcsBufferReader(xml);
		}
		this.count = 0;
		this.eof = false;
		this.bof = false;
		this.readFromBuffer = false;
		//all'interno di un documento xml non possono trovarsi <?xml?> e <?xml ... ?>, ma sono possibili ad esempio <?xmle?> o <?xmlp?>
		this.buffer = new char["<?xml".length() + 1];
	}

	public OcsInputStream(String xml) throws FileNotFoundException {
		this(xml, null);
	}

	@Override
	public int read() throws IOException {
		if(!this.bof){
			if(count < OPEN_TAG.length()){
				return OPEN_TAG.charAt(count++);
			}
			this.bof = true;
			count = 0;
		}
		
		if(this.readFromBuffer){
			char c = this.buffer[this.count++];
			if((int) c == -1 || (int) c == 65535){
				this.eof = true;
				this.readFromBuffer = false;
				this.count = 0;
			} else {
				if(this.count >= this.buffer.length){
					this.readFromBuffer = false;
				}
				return c;
			}
		}
		
		if(!this.eof){
			int b = this.br.read();
			char c = (char)b;
			
			if(c == '<'){
				this.buffer[0] = c;
				for(int i = 1; i < this.buffer.length; i++){
					this.buffer[i] = (char)this.br.read();
				}
				//So gi� che il primo elemento [0] del buffer � '<'
				if(this.buffer[1] == '?' &&
						(this.buffer[2] == 'x' || this.buffer[2] == 'X') &&
						(this.buffer[3] == 'm' || this.buffer[3] == 'M') &&
						(this.buffer[4] == 'l' || this.buffer[4] == 'L') &&
						(this.buffer[5] == ' ' || this.buffer[5] == '?')){
					while((c = (char)this.br.read()) != '>'){
						continue;
					}
					b = this.br.read();
				} else {
					this.readFromBuffer = true;
					this.count = 1;
					//arrivati a questo punto b risulta essere il primo carattere del buffer
					return b;
				}
			}
			
			if(b != -1){
				return b;
			}
			this.eof = true;
			this.count = 0;
		}
		
		if(this.count < CLOSE_TAG.length()){
			return CLOSE_TAG.charAt(count++);
		}
		
		return -1;
	}

	@Override
	public void close() throws IOException {
		try {
			if (this.br != null) {
				this.br.close();
			}
		} catch (IOException e) {
			throw e;
		} finally {
			super.close();
		}
	}
	
	public static void main(String args[]) throws Exception{
		OcsInputStream sis = new OcsInputStream("<a>aaa</a><b>bbb</b>");
		int c;
		long start = System.currentTimeMillis();
		while((c = sis.read()) != -1){
			System.out.print((char)c);
		}
		System.out.println("\ntempo: " + (System.currentTimeMillis() - start));
		sis.close();
	}

}
