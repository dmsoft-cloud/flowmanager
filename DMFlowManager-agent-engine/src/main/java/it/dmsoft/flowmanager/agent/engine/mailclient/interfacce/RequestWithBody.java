package it.dmsoft.flowmanager.agent.engine.mailclient.interfacce;

public class RequestWithBody extends RequestMail {
	private String body;

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	@Override
	public String toString() {
		return "RequestWithBody [body=" + body + ", toString()=" + super.toString() + "]";
	}
}
