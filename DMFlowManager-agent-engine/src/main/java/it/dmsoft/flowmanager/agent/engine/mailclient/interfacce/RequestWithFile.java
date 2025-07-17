package it.dmsoft.flowmanager.agent.engine.mailclient.interfacce;

public class RequestWithFile extends RequestMail {

	private String pathBody;

	public String getPathBody() {
		return pathBody;
	}

	public void setPathBody(String pathBody) {
		this.pathBody = pathBody;
	}
	
	
	@Override
	public String toString() {
		return "RequestWithBody [pathBody=" + pathBody + ", toString()=" + super.toString() + "]";
	}
}
