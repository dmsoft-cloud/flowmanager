package it.dmsoft.flowmanager.be.repositories;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.dmsoft.flowmanager.be.entities.FlowLogDetails;

@Repository
public interface FlowLogDetailsRepository extends JpaRepository<FlowLogDetails, BigDecimal> { }
