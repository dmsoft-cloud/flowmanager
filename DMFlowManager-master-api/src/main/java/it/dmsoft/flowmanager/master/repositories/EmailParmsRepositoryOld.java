package it.dmsoft.flowmanager.master.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.dmsoft.flowmanager.be.entities.EmailParmsOld;

@Repository
@Deprecated
public interface EmailParmsRepositoryOld extends JpaRepository<EmailParmsOld, String> { }
