package it.dmsoft.flowmanager.api.base;

import java.util.List;

public interface BaseService<X, Y, Z> {

	/**
	 * Create a customer based on the data sent to the service class.
	 * @param data
	 * @return DTO representation of the data
	 */
	Y save(Y data);

	/**
	 * Delete customer based on the customer ID.We can also use other option to delete customer
	 * based on the entity (passing JPA entity class as method parameter)
	 * @param id
	 * @return boolean flag showing the request status
	 */
	boolean delete(Z id);

	/**
	 * Method to return the list of all the customers in the system.This is a simple
	 * implementation but use pagination in the real world example.
	 * @return list of data
	 */
	List<Y> getAll();

	/**
	 * Get by ID. The service will send the data else will throw the exception. 
	 * @param id
	 * @return CustomerData
	 */
	Y getById(Z id);

}
