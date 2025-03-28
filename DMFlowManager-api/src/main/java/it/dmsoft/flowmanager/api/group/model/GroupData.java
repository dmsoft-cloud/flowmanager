package it.dmsoft.flowmanager.api.group.model;

import java.time.LocalDateTime;

import it.dmsoft.flowmanager.api.base.BaseModel;
import it.dmsoft.flowmanager.be.common.BaseEntity.YesNo;

public class GroupData extends BaseModel {
	
	private String id;
	private String description;
	private YesNo enabled;
	private String notes;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public YesNo getEnabled() {
		return enabled;
	}
	public void setEnabled(YesNo enabled) {
		this.enabled = enabled;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}	

}
