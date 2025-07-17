package it.dmsoft.flowmanager.agent.engine.generic.clientWs.utility;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class OcsBufferReader {
	private byte[] xml;
	private int ind;

	public OcsBufferReader(String xml, String encoding) {
		super();
		if (encoding != null) {
			try {
				this.xml = xml.getBytes(encoding);
			} catch (UnsupportedEncodingException e) {
			}
		} else {
			this.xml = xml.getBytes();
		}
		this.ind = 0;
	}

	public OcsBufferReader(String xml) {
		this(xml, null);
	}

	public int read() {
		if (ind >= xml.length) {
			return -1;
		}
		return xml[ind++];
	}

	public void close() throws IOException {
		ind = 0;
	}
}
