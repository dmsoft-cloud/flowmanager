package it.dmsoft.flowmanager.api.base;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import jakarta.persistence.EntityNotFoundException;

public abstract class DefaultBaseService<X, Y, Z> implements BaseService<X, Y, Z>{
	
	/**
     * Create a customer based on the data sent to the service class.
     * @param customer
     * @return DTO representation of the customer
     */
    @Override
    public Y save(Y modelData) {
        X entity = populateEntity(modelData);
        return populateData(getRepository().save(entity));
    }

    /**
     * Delete customer based on the customer ID.We can also use other option to delete customer
     * based on the entity (passing JPA entity class as method parameter)
     * @param id
     * @return boolean flag showing the request status
     */
    @Override
    public boolean delete(Z key) {
        getRepository().deleteById(key);
        return true;
    }

    /**
     * Method to return the list of all the customers in the system.This is a simple
     * implementation but use pagination in the real world example.
     * @return list of customer
     */
    @Override
    public List<Y> getAll() {
        List<Y> models = new ArrayList < > ();
        List<X> entities = getRepository().findAll();
        entities.forEach(entity -> {
            models.add(populateData(entity));
        });
        return models;
    }

    /**
     * Get customer by ID. The service will send the customer data else will throw the exception. 
     * @param customerId
     * @return CustomerData
     */
    @Override
    public Y getById(Z id) {
        try {
            return getRepository().findById(id)
                .map(this::populateData)
                .orElse(null); // Se non trovato, ritorna null
        } catch (RuntimeException e) {
            // Se c'è un errore DB o altro problema, rilancia
            throw new RuntimeException("Error getting the entity", e);
        }
   }

    /**
     * Internal method to convert Customer JPA entity to the DTO object
     * for frontend data
     * @param customer
     * @return CustomerData
     */
    private Y populateData(final X entity) {
        return getMapper().convertEntity(entity);
    }

    /**
     * Method to map the front end customer object to the JPA customer entity.
     * @param customerData
     * @return Customer
     */
    private X populateEntity(Y model) {
        return getMapper().convertModel(model);
    }
    
    protected abstract BaseMapper<X, Y> getMapper();
    
    protected abstract JpaRepository<X, Z> getRepository();

}
