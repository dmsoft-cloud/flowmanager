package it.dmsoft.flowmanager.api.group.model;

import java.time.LocalDateTime;

import it.dmsoft.flowmanager.be.common.BaseEntity.YesNo;

public class GroupData {
	
	private String id;
	private String description;
	private YesNo enabled;
	private String notes;
	private String creationUser ;
	private LocalDateTime  creationTs;
	private String updateUser ;
	private LocalDateTime  updateTs;
	
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
	public String getCreationUser() {
		return creationUser;
	}
	public void setCreationUser(String creationUser) {
		this.creationUser = creationUser;
	}
	public String getUpdateUser() {
		return updateUser;
	}
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
	public LocalDateTime getCreationTs() {
		return creationTs;
	}
	public LocalDateTime getUpdateTs() {
		return updateTs;
	}
	public void setCreationTs(LocalDateTime creationTs) {
		this.creationTs = creationTs;
	}
	public void setUpdateTs(LocalDateTime updateTs) {
		this.updateTs = updateTs;
	}
	
	
	

}
