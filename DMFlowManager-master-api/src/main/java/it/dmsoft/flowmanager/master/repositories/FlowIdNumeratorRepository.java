package it.dmsoft.flowmanager.master.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.dmsoft.flowmanager.be.entities.FlowIdNumerator;
import it.dmsoft.flowmanager.be.keys.FlowIdNumeratorId;

@Repository
public interface FlowIdNumeratorRepository extends JpaRepository<FlowIdNumerator, FlowIdNumeratorId> { }
