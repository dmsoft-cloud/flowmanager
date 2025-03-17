package it.dmsoft.flowmanager.master.api.customer;

import java.util.List;

import it.dmsoft.flowmanager.master.api.customer.model.CustomerData;

public interface CustomerService {

	/**
	 * Create a customer based on the data sent to the service class.
	 * @param customer
	 * @return DTO representation of the customer
	 */
	CustomerData saveCustomer(CustomerData customer);

	/**
	 * Delete customer based on the customer ID.We can also use other option to delete customer
	 * based on the entity (passing JPA entity class as method parameter)
	 * @param customerId
	 * @return boolean flag showing the request status
	 */
	boolean deleteCustomer(Long customerId);

	/**
	 * Method to return the list of all the customers in the system.This is a simple
	 * implementation but use pagination in the real world example.
	 * @return list of customer
	 */
	List<CustomerData> getAllCustomers();

	/**
	 * Get customer by ID. The service will send the customer data else will throw the exception. 
	 * @param customerId
	 * @return CustomerData
	 */
	CustomerData getCustomerById(Long customerId);

}
