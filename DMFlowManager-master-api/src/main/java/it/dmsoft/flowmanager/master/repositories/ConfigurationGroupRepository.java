package it.dmsoft.flowmanager.master.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.dmsoft.flowmanager.be.entities.ConfigurationGroup;
@Repository
public interface ConfigurationGroupRepository extends JpaRepository<ConfigurationGroup,String> { }
