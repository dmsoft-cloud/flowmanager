package it.dmsoft.flowmanager.master.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.dmsoft.flowmanager.be.entities.User;
import it.dmsoft.flowmanager.be.entities.UserColumnsConfig;
import it.dmsoft.flowmanager.be.keys.UserId;
import it.dmsoft.flowmanager.be.keys.UserPageId;

@Repository
public interface UserColumnsConfigRepository extends JpaRepository<UserColumnsConfig, UserPageId> { }
