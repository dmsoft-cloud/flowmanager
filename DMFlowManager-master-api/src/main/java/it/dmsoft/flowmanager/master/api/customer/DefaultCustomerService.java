package it.dmsoft.flowmanager.master.api.customer;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.dmsoft.flowmanager.master.api.customer.mapper.CustomerMapper;
import it.dmsoft.flowmanager.master.api.customer.model.CustomerData;
import it.dmsoft.flowmanager.master.be.entities.Customer;
import it.dmsoft.flowmanager.master.be.repositories.CustomerRepository;
import jakarta.persistence.EntityNotFoundException;

@Service("customerService")
public class DefaultCustomerService implements CustomerService {

    //@Autowired
    private CustomerRepository customerRepository;
    
    @Autowired
    private CustomerMapper customerMapper;

    
    public DefaultCustomerService(CustomerRepository customerRepository) {
		super();
		this.customerRepository = customerRepository;
	}

	/**
     * Create a customer based on the data sent to the service class.
     * @param customer
     * @return DTO representation of the customer
     */
    @Override
    public CustomerData saveCustomer(CustomerData customer) {
        Customer customerModel = populateCustomerEntity(customer);
        return populateCustomerData(customerRepository.save(customerModel));
    }

    /**
     * Delete customer based on the customer ID.We can also use other option to delete customer
     * based on the entity (passing JPA entity class as method parameter)
     * @param customerId
     * @return boolean flag showing the request status
     */
    @Override
    public boolean deleteCustomer(Long customerId) {
        customerRepository.deleteById(customerId);
        return true;
    }

    /**
     * Method to return the list of all the customers in the system.This is a simple
     * implementation but use pagination in the real world example.
     * @return list of customer
     */
    @Override
    public List<CustomerData> getAllCustomers() {
        List<CustomerData> customers = new ArrayList < > ();
        List<Customer> customerList = customerRepository.findAll();
        customerList.forEach(customer -> {
            customers.add(populateCustomerData(customer));
        });
        return customers;
    }

    /**
     * Get customer by ID. The service will send the customer data else will throw the exception. 
     * @param customerId
     * @return CustomerData
     */
    @Override
    public CustomerData getCustomerById(Long customerId) {
        return populateCustomerData(customerRepository.findById(customerId).orElseThrow(() -> new EntityNotFoundException("Customer not found")));
    }

    /**
     * Internal method to convert Customer JPA entity to the DTO object
     * for frontend data
     * @param customer
     * @return CustomerData
     */
    private CustomerData populateCustomerData(final Customer customer) {
        return customerMapper.convert(customer);
    }

    /**
     * Method to map the front end customer object to the JPA customer entity.
     * @param customerData
     * @return Customer
     */
    private Customer populateCustomerEntity(CustomerData customerData) {
        return customerMapper.convert(customerData);
    }

}
