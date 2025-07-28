package it.dmsoft.flowmanager.be.entities.old;

public class Otgffanacf {
	public static enum OtgffanacfCoulmn {

		OTGFANACF_ID  , 
		OTGFANACF_PART,
		OTGFANACF_IDF,
		OTGFANACF_FNAME,
		OTGFANACF_PARM,
		OTGFANACF_EXEC,
		OTGFANACF_EXECE,
		OTGFANACF_NFNAME,
		OTGFANACF_ARCHIFNAME

	}
	
	
	private String otgfanacf_Id;
	private String otgfanacf_Part;
	private String otgfanacf_Idf;
	private String otgfanacf_Fname;
	private String otgfanacf_Parm;
	private String otgfanacf_Exec;
	private String otgfanacf_Exece;
	private String otgfanacf_Nfname;
	private String otgfanacf_Archifname;
	public String getOtgfanacf_Id() {
		return otgfanacf_Id;
	}
	public void setOtgfanacf_Id(String otgfanacf_Id) {
		this.otgfanacf_Id = otgfanacf_Id;
	}
	public String getOtgfanacf_Part() {
		return otgfanacf_Part;
	}
	public void setOtgfanacf_Part(String otgfanacf_Part) {
		this.otgfanacf_Part = otgfanacf_Part;
	}
	public String getOtgfanacf_Idf() {
		return otgfanacf_Idf;
	}
	public void setOtgfanacf_Idf(String otgfanacf_Idf) {
		this.otgfanacf_Idf = otgfanacf_Idf;
	}
	public String getOtgfanacf_Fname() {
		return otgfanacf_Fname;
	}
	public void setOtgfanacf_Fname(String otgfanacf_Fname) {
		this.otgfanacf_Fname = otgfanacf_Fname;
	}
	public String getOtgfanacf_Parm() {
		return otgfanacf_Parm;
	}
	public void setOtgfanacf_Parm(String otgfanacf_Parm) {
		this.otgfanacf_Parm = otgfanacf_Parm;
	}
	public String getOtgfanacf_Exec() {
		return otgfanacf_Exec;
	}
	public void setOtgfanacf_Exec(String otgfanacf_Exec) {
		this.otgfanacf_Exec = otgfanacf_Exec;
	}
	public String getOtgfanacf_Exece() {
		return otgfanacf_Exece;
	}
	public void setOtgfanacf_Exece(String otgfanacf_Exece) {
		this.otgfanacf_Exece = otgfanacf_Exece;
	}
	public String getOtgfanacf_Nfname() {
		return otgfanacf_Nfname;
	}
	public void setOtgfanacf_Nfname(String otgfanacf_Nfname) {
		this.otgfanacf_Nfname = otgfanacf_Nfname;
	}
	public String getOtgfanacf_Archifname() {
		return otgfanacf_Archifname;
	}
	public void setOtgfanacf_Archifname(String otgfanacf_Archifname) {
		this.otgfanacf_Archifname = otgfanacf_Archifname;
	}
	@Override
	public String toString() {
		return "Otgffanacf [otgfanacf_Id=" + otgfanacf_Id + ", otgfanacf_Part=" + otgfanacf_Part + ", otgfanacf_Idf="
				+ otgfanacf_Idf + ", otgfanacf_Fname=" + otgfanacf_Fname + ", otgfanacf_Parm=" + otgfanacf_Parm
				+ ", otgfanacf_Exec=" + otgfanacf_Exec + ", otgfanacf_Exece=" + otgfanacf_Exece + ", otgfanacf_Nfname="
				+ otgfanacf_Nfname + ", otgfanacf_Archifname=" + otgfanacf_Archifname + "]";
	}

}
