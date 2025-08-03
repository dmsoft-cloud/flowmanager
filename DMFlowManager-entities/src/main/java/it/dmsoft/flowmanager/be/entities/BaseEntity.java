package it.dmsoft.flowmanager.be.entities;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

@MappedSuperclass
public abstract class BaseEntity {
	
	   @Column(length = 100, name = "creation_user",  updatable = false)
	    private String creationUser;

	    @CreationTimestamp
	    @Column(name = "creation_ts", nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	    private LocalDateTime creationTs;

	    @Column(length = 100, name = "update_user", columnDefinition = "VARCHAR(100) DEFAULT 'SYSTEM'")
	    private String updateUser;

	    @UpdateTimestamp
	    @Column(name = "update_ts", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
	    private LocalDateTime updateTs;

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
	            this.creationUser = "SYSTEM";
	            this.updateUser = "SYSTEM";
	        }
	    }

	    @PreUpdate
	    protected void onUpdate() {
	        this.updateTs = LocalDateTime.now();
	        this.updateUser = "SYSTEM2";

	    }

}
