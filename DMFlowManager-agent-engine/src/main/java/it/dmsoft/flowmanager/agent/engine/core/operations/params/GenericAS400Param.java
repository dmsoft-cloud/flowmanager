package it.dmsoft.flowmanager.agent.engine.core.operations.params;

public class GenericAS400Param {

	private String jobd;

	private String jobdLibrary;
	
	private String user;
	
	private String password;
	
	public String getJobd() {
		return jobd;
	}

	public void setJobd(String jobd) {
		this.jobd = jobd;
	}

	public String getJobdLibrary() {
		return jobdLibrary;
	}

	public void setJobdLibrary(String jobdLibrary) {
		this.jobdLibrary = jobdLibrary;
	}
	
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((jobd == null) ? 0 : jobd.hashCode());
		result = prime * result + ((jobdLibrary == null) ? 0 : jobdLibrary.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		
		
		if(!(this instanceof GenericAS400Param) || !(obj instanceof GenericAS400Param)) 
			return false;
		
		
		GenericAS400Param other = (GenericAS400Param) obj;
		if (jobd == null) {
			if (other.jobd != null)
				return false;
		} else if (!jobd.equals(other.jobd))
			return false;
		if (jobdLibrary == null) {
			if (other.jobdLibrary != null)
				return false;
		} else if (!jobdLibrary.equals(other.jobdLibrary))
			return false;
		
		
		return true;
	}

	@Override
	public String toString() {
		return "GenericAS400Param [jobd=" + jobd + ", jobdLibrary=" + jobdLibrary + "]";
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	

}
