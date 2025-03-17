package it.dmsoft.flowmanager.master.be.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import it.dmsoft.flowmanager.master.be.entities.Customer;
//@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> { }
