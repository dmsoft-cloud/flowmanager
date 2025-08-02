package it.dmsoft.flowmanager.agent.engine.core.operations.params;

public class SpFileParam extends GenericConnectionParams {

	private String id;
	private String spmqm;
	private String spqName;
	private String stmdb;
	private String stmf;
	private String dbfenc;
	private String sender;
	private String corrid;
	private String spuserid;
	private String sppwd;
	private String mode;
	private String ccsid;
	private String stmeor;
	private String stmtype;
	private String usrcls;
	private String stmfopt;
	private String spfentry;
	private String stmenc;
	private String stmrecl;
	private String file;
	private String mbr;
	
	private boolean launchErrorIfNoFileFound = false;

	public String getStmeor() {
		return stmeor;
	}

	public void setStmeor(String stmeor) {
		this.stmeor = stmeor;
	}

	public String getStmtype() {
		return stmtype;
	}

	public void setStmtype(String stmtype) {
		this.stmtype = stmtype;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSpmqm() {
		return spmqm;
	}

	public void setSpmqm(String spmqm) {
		this.spmqm = spmqm;
	}

	public String getSpqName() {
		return spqName;
	}

	public void setSpqName(String spqName) {
		this.spqName = spqName;
	}

	public String getStmdb() {
		return stmdb;
	}

	public void setStmdb(String stmdb) {
		this.stmdb = stmdb;
	}
	
	public String getStmf() {
		return stmf;
	}

	public void setStmf(String stmf) {
		this.stmf = stmf;
	}

	public String getDbfenc() {
		return dbfenc;
	}

	public void setDbfenc(String dbfenc) {
		this.dbfenc = dbfenc;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getCorrid() {
		return corrid;
	}

	public void setCorrid(String corrid) {
		this.corrid = corrid;
	}

	public String getSpuserid() {
		return spuserid;
	}

	public void setSpuserid(String spuserid) {
		this.spuserid = spuserid;
	}

	public String getSppwd() {
		return sppwd;
	}

	public void setSppwd(String sppwd) {
		this.sppwd = sppwd;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getCcsid() {
		return ccsid;
	}

	public void setCcsid(String ccsid) {
		this.ccsid = ccsid;
	}
	
	public String getUsrcls() {
		return usrcls;
	}

	public void setUsrcls(String usrcls) {
		this.usrcls = usrcls;
	}

	public String getStmfopt() {
		return stmfopt;
	}

	public void setStmfopt(String stmfopt) {
		this.stmfopt = stmfopt;
	}

	public String getSpfentry() {
		return spfentry;
	}

	public void setSpfentry(String spfentry) {
		this.spfentry = spfentry;
	}
	
	public boolean isLaunchErrorIfNoFileFound() {
		return launchErrorIfNoFileFound;
	}

	public void setLaunchErrorIfNoFileFound(boolean launchErrorIfNoFileFound) {
		this.launchErrorIfNoFileFound = launchErrorIfNoFileFound;
	}

	public String getStmenc() {
		return stmenc;
	}

	public void setStmenc(String stmenc) {
		this.stmenc = stmenc;
	}

	public String getStmrecl() {
		return stmrecl;
	}

	public void setStmrecl(String stmrecl) {
		this.stmrecl = stmrecl;
	}
	
	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public String getMbr() {
		return mbr;
	}

	public void setMbr(String mbr) {
		this.mbr = mbr;
	}

	@Override
	public String toString() {
		return "SpFileParam [id=" + id + ", spmqm=" + spmqm + ", spqName=" + spqName + ", stmdb=" + stmdb + ", stmf="
				+ stmf + ", dbfenc=" + dbfenc + ", sender=" + sender + ", corrid=" + corrid + ", spuserid=" + spuserid
				+ ", sppwd=" + sppwd + ", mode=" + mode + ", ccsid=" + ccsid + ", stmeor=" + stmeor + ", stmtype="
				+ stmtype + ", usrcls=" + usrcls + ", stmfopt=" + stmfopt + ", spfentry=" + spfentry + ", stmenc="
				+ stmenc + ", stmrecl=" + stmrecl + ", file=" + file + ", mbr=" + mbr + ", launchErrorIfNoFileFound="
				+ launchErrorIfNoFileFound + "]";
	}

}
