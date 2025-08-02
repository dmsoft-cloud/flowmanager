package it.dmsoft.flowmanager.agent.engine.core.operations.params;

import java.math.BigDecimal;

public class CftFileParam extends GenericConnectionParams {

	private String id;
	private String partner;
	private String idf;
	private String fName;
	private String parm;
	private String exec;
	private String execE;
	private String nFname;
	private String archiFname;
	private BigDecimal transactionId;
	
	private boolean launchErrorIfNoFileFound = false;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPartner() {
		return partner;
	}

	public void setPartner(String partner) {
		this.partner = partner;
	}

	public String getIdf() {
		return idf;
	}

	public void setIdf(String idf) {
		this.idf = idf;
	}

	public String getfName() {
		return fName;
	}

	public void setfName(String fName) {
		this.fName = fName;
	}

	public String getParm() {
		return parm;
	}

	public void setParm(String parm) {
		this.parm = parm;
	}

	public String getExec() {
		return exec;
	}

	public void setExec(String exec) {
		this.exec = exec;
	}

	public String getExecE() {
		return execE;
	}

	public void setExecE(String execE) {
		this.execE = execE;
	}

	public String getnFname() {
		return nFname;
	}

	public void setnFname(String nFname) {
		this.nFname = nFname;
	}

	public String getArchiFname() {
		return archiFname;
	}

	public void setArchiFname(String archiFname) {
		this.archiFname = archiFname;
	}

	public boolean isLaunchErrorIfNoFileFound() {
		return launchErrorIfNoFileFound;
	}

	public void setLaunchErrorIfNoFileFound(boolean launchErrorIfNoFileFound) {
		this.launchErrorIfNoFileFound = launchErrorIfNoFileFound;
	}
	
	public BigDecimal getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(BigDecimal transactionId) {
		this.transactionId = transactionId;
	}

	@Override
	public String toString() {
		return "CftFileParam [id=" + id + ", partner=" + partner + ", idf=" + idf + ", fName=" + fName + ", parm="
				+ parm + ", exec=" + exec + ", execE=" + execE + ", nFname=" + nFname + ", archiFname=" + archiFname
				+ ", transactionId=" + transactionId + ", launchErrorIfNoFileFound=" + launchErrorIfNoFileFound + "]";
	}


}
