package it.dmsoft.flowmanager.agent.engine.core.operations.params;

public class InteractiveCommandCallParam extends GenericConnectionParams {

	private String command;

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	@Override
	public String toString() {
		return "SubmitJobParam [command=" + command + ", getJobd()=" + getJobd() + ", getJobdLibrary()="
				+ getJobdLibrary() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + ", getClass()="
				+ getClass() + "]";
	}

}
