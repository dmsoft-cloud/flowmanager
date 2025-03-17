package it.dmsoft.flowmanager.master.be.entities;

import it.dmsoft.flowmanager.common.domain.Domains.YesNo;
import it.dmsoft.flowmanager.master.be.converter.YesNoConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

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
