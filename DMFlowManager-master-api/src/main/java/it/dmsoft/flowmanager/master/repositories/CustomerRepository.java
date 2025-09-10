package it.dmsoft.flowmanager.master.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import it.dmsoft.flowmanager.be.entities.Customer;
//@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> { }
