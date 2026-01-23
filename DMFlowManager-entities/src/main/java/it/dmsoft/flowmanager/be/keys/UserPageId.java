package it.dmsoft.flowmanager.be.keys;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class UserPageId implements Serializable {
	
	private String username;
	private String customer;
    private String page; 
	
    public UserPageId() {}

    public UserPageId(String username, String customer, String page) {
        this.username = username;
        this.customer = customer;
        this.page = page;
    }
    
    
	
	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	@Override
	public int hashCode() {
		return Objects.hash(page, username);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserPageId other = (UserPageId) obj;
		return Objects.equals(page, other.page) && Objects.equals(username, other.username);
	}

	


}
