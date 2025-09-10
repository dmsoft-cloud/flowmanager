package it.dmsoft.flowmanager.master.repositories;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.dmsoft.flowmanager.be.entities.ScheduleDate;

@Repository
public interface ScheduleDateRepository extends JpaRepository<ScheduleDate, BigDecimal> { }
