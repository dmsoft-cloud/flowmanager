package it.dmsoft.flowmanager.api.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import it.dmsoft.flowmanager.api.email.mapper.EmailMapper;
import it.dmsoft.flowmanager.be.entities.Email;
import it.dmsoft.flowmanager.be.repositories.EmailRepository;
import it.dmsoft.flowmanager.common.model.EmailData;
import it.dmsoft.flowmanager.framework.api.base.BaseMapper;
import it.dmsoft.flowmanager.framework.api.base.DefaultBaseService;


@Service("emailService")
public class DefaultEmailService extends DefaultBaseService<Email, EmailData, String> {

	
    @Autowired
    private EmailRepository emailRepository;

    @Autowired
    private EmailMapper emailMapper;

    @Override
    protected BaseMapper<Email, EmailData> getMapper() {
        return emailMapper;
    }

    @Override
    protected JpaRepository<Email, String> getRepository() {
        return emailRepository;
    }
}
