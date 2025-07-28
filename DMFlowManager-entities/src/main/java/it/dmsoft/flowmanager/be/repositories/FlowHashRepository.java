package it.dmsoft.flowmanager.be.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.dmsoft.flowmanager.be.entities.FlowHash;

@Repository
public interface FlowHashRepository extends JpaRepository<FlowHash, String> { }
