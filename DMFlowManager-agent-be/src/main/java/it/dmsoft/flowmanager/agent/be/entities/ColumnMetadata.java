package it.dmsoft.flowmanager.agent.be.entities;

public class ColumnMetadata {
	private String name;
	private String lable;
	private String typeName;
	private int precision;
	private int scale;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLable() {
		return lable;
	}

	public void setLable(String lable) {
		this.lable = lable;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public int getPrecision() {
		return precision;
	}

	public void setPrecision(int precision) {
		this.precision = precision;
	}

	public int getScale() {
		return scale;
	}

	public void setScale(int scale) {
		this.scale = scale;
	}

	@Override
	public String toString() {
		return "ColumnMetadata [name=" + name + ", lable=" + lable + ", typeName=" + typeName + ", precision="
				+ precision + ", scale=" + scale + "]";
	}

}
