package it.dmsoft.flowmanager.common.model;

import java.util.List;

public class UserData extends BaseModel {



	private String username;
	private String keycloakUserId;
	private String customer;
	private String firstName;
	private String lastName;
	private String email;
	private String style;
	private String language;
	private List<UserColumnsConfigData> userColumnConfig;

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getKeycloakUserId() {
		return keycloakUserId;
	}
	public void setKeycloakUserId(String keycloakUserId) {
		this.keycloakUserId = keycloakUserId;
	}

	public String getCustomer() {
		return customer;
	}
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	public String getFirstName() {
		return firstName;
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
	public List<UserColumnsConfigData> getUserColumnConfig() {
		return userColumnConfig;
	}
	public void setUserColumnConfig(List<UserColumnsConfigData> userColumnConfig) {
		this.userColumnConfig = userColumnConfig;
	}




}
