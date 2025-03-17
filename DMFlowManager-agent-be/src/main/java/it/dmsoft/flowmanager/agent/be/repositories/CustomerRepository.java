package it.dmsoft.flowmanager.agent.be.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import it.dmsoft.flowmanager.agent.be.entities.Customer;
//@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> { }
