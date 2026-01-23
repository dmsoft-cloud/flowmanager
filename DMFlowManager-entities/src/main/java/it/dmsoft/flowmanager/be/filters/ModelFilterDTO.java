package it.dmsoft.flowmanager.be.filters;

import it.dmsoft.flowmanager.common.domain.Domains.YesNo;

public class ModelFilterDTO {
    private String id;
    private String direction;
    private String  type;
    private String  enabled;
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getEnabled() {
		return enabled;
	}
	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}
	@Override
	public String toString() {
		return "ModelFilterDTO [id=" + id + ", direction=" + direction + ", type=" + type + ", enabled=" + enabled
				+ "]";
	}
    

	



    
}
