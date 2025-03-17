package it.dmsoft.flowmanager.agent.be.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.dmsoft.flowmanager.agent.be.entities.Interface;
@Repository
public interface InterfaceRepository extends JpaRepository<Interface, String> { }
