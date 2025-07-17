package it.dmsoft.flowmanager.agent.engine.generic.clientWs.utility;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.activation.DataSource;

public class OcsDataSource implements DataSource {

	private InputStream is;
	private String name, contentType;

	@Override
	public String getContentType() {
		return contentType;
	}

	public void setContentType(String ct) {
		this.contentType = ct;
	}

	@Override
	public InputStream getInputStream() throws IOException {
		return is;
	}

	public void setInputStream(InputStream is) {
		this.is = is;
	}

	@Override
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public OutputStream getOutputStream() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}
}