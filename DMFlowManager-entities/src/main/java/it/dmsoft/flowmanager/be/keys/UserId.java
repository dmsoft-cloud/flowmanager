package it.dmsoft.flowmanager.be.keys;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class UserId implements Serializable {
	
    @Column(length = 255)
    private String username;

    @Column(length = 255)
    private String customer;

    public UserId() {}

    public UserId(String username, String customer) {
        this.username = username;
        this.customer = customer;
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

	@Override
	public int hashCode() {
		return Objects.hash(customer, username);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserId other = (UserId) obj;
		return Objects.equals(customer, other.customer) && Objects.equals(username, other.username);
	}

    
	


}
