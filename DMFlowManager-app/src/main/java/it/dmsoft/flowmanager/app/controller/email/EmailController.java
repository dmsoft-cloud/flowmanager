package it.dmsoft.flowmanager.app.controller.email;

import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.databind.ObjectMapper;

import it.dmsoft.flowmanager.api.base.BaseService;
import it.dmsoft.flowmanager.api.customer.CustomerService;
import it.dmsoft.flowmanager.api.customer.model.CustomerData;
import it.dmsoft.flowmanager.api.email.model.EmailData;
import it.dmsoft.flowmanager.api.interfaces.model.InterfaceData;
import it.dmsoft.flowmanager.be.entities.Email;
import it.dmsoft.flowmanager.be.entities.Interface;
import jakarta.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/emails")
public class EmailController {

    @Resource(name = "emailService")
    private BaseService<Email, EmailData, String> emailService;

    /**
     * <p>Get all email data in the system.For production system you many want to use
     * pagination.</p>
     * @return List<EmailData>
     */
    @GetMapping
    public List<EmailData> getEmails() {
        return emailService.getAll();
    }

    /**
     * Method to get the email data based on the ID.
     * @param id
     * @return EmailnData
     */
    @GetMapping("/email/{id}")
    public EmailData getEmail(@PathVariable String id) {
        return emailService.getById(id);
    }

    /**
     * Post request to create email information int the system.
     * @param emailData
     * @return
     */
    @PostMapping("/email")
    public EmailData saveEmail(final @RequestBody EmailData emailData) {
    	try {
    	        ObjectMapper objectMapper = new ObjectMapper();
    	        String jsonInput = objectMapper.writeValueAsString(emailData);
    	        System.out.println("Received JSON: " + jsonInput);
    	    } catch (Exception e) {
    	        e.printStackTrace();
    	    }
    	    

        return emailService.save(emailData);
    }

    /**
     * <p>Delete email from the system based on the ID. The method mapping is like the getModel with difference of
     * @DeleteMapping and @GetMapping</p>
     * @param id
     * @return
     */
    @DeleteMapping("/email/{id}")
    public Boolean deleteEmail(@PathVariable String id) {
        return emailService.delete(id);
    }

}