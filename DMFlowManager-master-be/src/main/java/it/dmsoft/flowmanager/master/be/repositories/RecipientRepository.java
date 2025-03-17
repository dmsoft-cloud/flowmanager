package it.dmsoft.flowmanager.master.be.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.dmsoft.flowmanager.master.be.entities.Email;
import it.dmsoft.flowmanager.master.be.entities.Recipient;
import it.dmsoft.flowmanager.master.be.keys.RecipientId;

@Repository
public interface RecipientRepository extends JpaRepository<Recipient, RecipientId> { }
