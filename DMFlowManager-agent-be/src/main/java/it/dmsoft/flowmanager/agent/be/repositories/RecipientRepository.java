package it.dmsoft.flowmanager.agent.be.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.dmsoft.flowmanager.agent.be.entities.Email;
import it.dmsoft.flowmanager.agent.be.entities.Recipient;
import it.dmsoft.flowmanager.agent.be.keys.RecipientId;

@Repository
public interface RecipientRepository extends JpaRepository<Recipient, RecipientId> { }
