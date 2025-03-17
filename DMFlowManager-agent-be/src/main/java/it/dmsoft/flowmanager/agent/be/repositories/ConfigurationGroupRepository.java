package it.dmsoft.flowmanager.agent.be.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.dmsoft.flowmanager.agent.be.entities.ConfigurationGroup;
@Repository
public interface ConfigurationGroupRepository extends JpaRepository<ConfigurationGroup,String> { }
