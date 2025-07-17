package it.dmsoft.flowmanager.agent.engine.core.operations.params;

import it.dmsoft.flowmanager.agent.engine.core.utils.Constants;

public class ChkObjParam extends GenericAS400Param {

	private String obj;
	private String libreria;
	private String objType = Constants.STAR + Constants.FILE; //"*FILE";
	private String mbr;
	private String aut;

	public String getObj() {
		return obj;
	}

	public void setObj(String obj) {
		this.obj = obj;
	}

	public String getLibreria() {
		return libreria;
	}

	public void setLibreria(String libreria) {
		this.libreria = libreria;
	}

	public String getObjType() {
		return objType;
	}

	public void setObjType(String objType) {
		this.objType = objType;
	}

	public String getMbr() {
		return mbr;
	}

	public void setMbr(String mbr) {
		this.mbr = mbr;
	}

	public String getAut() {
		return aut;
	}

	public void setAut(String aut) {
		this.aut = aut;
	}

	@Override
	public String toString() {
		return super.toString() + "\n ChkObjParam [obj=" + obj + ", libreria=" + libreria + ", objType=" + objType + ", mbr=" + mbr + ", aut="
				+ aut + "]";
	}

}
