package it.dmsoft.flowmanager.master.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.dmsoft.flowmanager.be.entities.Recipient;
import it.dmsoft.flowmanager.be.keys.RecipientId;

@Repository
public interface RecipientRepository extends JpaRepository<Recipient, RecipientId> { }
