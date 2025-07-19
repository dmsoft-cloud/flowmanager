package it.dmsoft.flowmanager.agent.be.repositories;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.dmsoft.flowmanager.agent.be.entities.FlowLog;

@Repository
public interface FlowLogRepository extends JpaRepository<FlowLog, BigDecimal> { }
