package it.dmsoft.flowmanager.master.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.dmsoft.flowmanager.be.entities.User;
import it.dmsoft.flowmanager.be.keys.UserId;

@Repository
public interface UserRepository extends JpaRepository<User, UserId> { }
