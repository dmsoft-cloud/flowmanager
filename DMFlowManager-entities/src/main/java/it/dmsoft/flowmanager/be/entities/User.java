package it.dmsoft.flowmanager.be.entities;

import java.util.List;

import it.dmsoft.flowmanager.be.keys.UserId;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.OneToMany;

@Entity
@IdClass(UserId.class)
public class User extends BaseEntity {

    @Id
    @Column(length = 255)
    private String username;

    @Id
    @Column(length = 255)
    private String customer;
	
	@Column(length = 255)
	private String keycloakUserId;   	
	@Column(length = 255)
	private String firstName;
	@Column(length = 255)
	private String lastName;
	@Column(length = 255)
	private String email;
	@Column(length = 100)
	private String style;
	@Column(length = 50)
	private String language;
	
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserColumnsConfig> userColumnConfig;


	public User() {
	}
	// get & set methods

	public String getFirstName() {
		return firstName;
	}

	public String getKeycloakUserId() {
		return keycloakUserId;
	}

	public void setKeycloakUserId(String keycloakUserId) {
		this.keycloakUserId = keycloakUserId;
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

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public List<UserColumnsConfig> getUserColumnConfig() {
		return userColumnConfig;
	}

	public void setUserColumnConfig(List<UserColumnsConfig> userColumnConfig) {
		this.userColumnConfig = userColumnConfig;
	}
	

}
