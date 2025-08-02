package it.dmsoft.flowmanager.master.api.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import it.dmsoft.flowmanager.be.entities.EmailParms;
import it.dmsoft.flowmanager.be.repositories.EmailParmsRepository;
import it.dmsoft.flowmanager.common.model.EmailParmsData;
import it.dmsoft.flowmanager.framework.api.base.BaseMapper;
import it.dmsoft.flowmanager.framework.api.base.DefaultBaseService;
import it.dmsoft.flowmanager.master.api.email.mapper.EmailParmsMapper;

@Service("emailParmsService")
public class DefaultEmailParmsService extends DefaultBaseService<EmailParms, EmailParmsData, String> {

	
    @Autowired
    private EmailParmsRepository emailParmsRepository;

    @Autowired
    private EmailParmsMapper emailParmsMapper;

    @Override
    protected BaseMapper<EmailParms, EmailParmsData> getMapper() {
        return emailParmsMapper;
    }

    @Override
    protected JpaRepository<EmailParms, String> getRepository() {
        return emailParmsRepository;
    }
}
