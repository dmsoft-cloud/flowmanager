package it.dmsoft.flowmanager.be.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import it.dmsoft.flowmanager.be.entities.Flow;

@Repository
public interface FlowRepository extends JpaRepository<Flow, String>,  JpaSpecificationExecutor<Flow> { }
