package it.dmsoft.flowmanager.be.entities;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import it.dmsoft.flowmanager.be.common.BaseEntity.YesNo;
import it.dmsoft.flowmanager.be.converter.YesNoConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

@Entity
public class ConfigurationGroup {
	
	//id
	//descrizione
	//stato

	@Id
	@Column(length = 20)
	private String id;
	
	@Column(length = 50)
	private String description;
	
	@Convert(converter = YesNoConverter.class)
	@Column(length = 1)
	private YesNo enabled;
	
	@Column(length = 2000)
	private String notes;
	
	@Column(length = 100, name = "creation_user")
	private String creationUser ;

	@CreationTimestamp
	@Column(name = "creation_ts", nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime  creationTs;
	
	@Column(length = 100, name = "update_user", columnDefinition = "VARCHAR(100) DEFAULT 'AAA'" )
	private String updateUser ;
	
	@UpdateTimestamp
	@Column(name = "update_ts", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
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

	public LocalDateTime getCreationTs() {
        return creationTs;
    }

    public LocalDateTime getUpdateTs() {
        return updateTs;
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

	
	@PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        if (this.creationTs == null) {
            this.creationTs = now;
        }
        if (this.updateTs == null) {
            this.updateTs = now;
        }
        
        if (this.creationUser == null) {
        	this.creationUser ="SYSTEM";
        	this.updateUser = "SYSTEM"; 
        }
    }

    @PreUpdate
    protected void onUpdate() {
        this.updateTs = LocalDateTime.now();
        
        if (this.creationUser == null) {
        	this.creationUser = creationUser;
        	this.updateUser = "SYSTEM2"; 
        }
    }
	

}
