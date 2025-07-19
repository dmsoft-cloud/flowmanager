package it.dmsoft.flowmanager.agent.be.repositories;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.dmsoft.flowmanager.agent.be.entities.ScheduleDate;

@Repository
public interface ScheduleDateRepository extends JpaRepository<ScheduleDate, BigDecimal> { }
