package it.dmsoft.flowmanager.be.entities;

import it.dmsoft.flowmanager.be.keys.RecipientId;
import it.dmsoft.flowmanager.common.domain.Domains.RecipientType;
import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import it.dmsoft.flowmanager.be.keys.UserPageId;

@Entity
@IdClass(UserPageId.class) // Chiave composta
public class UserColumnsConfig {

	
	@Id
    @Column(name = "username", length = 255)
    private String username;
	
	@Id
    @Column(name = "customer", length = 255)
    private String customer;

    @Id
    @Column(name = "page", length = 255)
    private String page;	

	@Column(name = "columnConfigs", nullable = false, length = 6000)
    private String columnConfigs;


	@ManyToOne
	@JoinColumns({
	    @JoinColumn(name = "username", referencedColumnName = "username", insertable = false, updatable = false),
	    @JoinColumn(name = "customer", referencedColumnName = "customer", insertable = false, updatable = false)
	})
	private User user; 



	public String getPage() {
		return page;
	}


	public void setPage(String page) {
		this.page = page;
	}


	public String getColumnConfigs() {
		return columnConfigs;
	}


	public void setColumnConfigs(String columnConfigs) {
		this.columnConfigs = columnConfigs;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getCustomer() {
		return customer;
	}


	public void setCustomer(String customer) {
		this.customer = customer;
	}




	

}
