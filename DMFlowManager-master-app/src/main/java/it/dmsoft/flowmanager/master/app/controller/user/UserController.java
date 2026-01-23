package it.dmsoft.flowmanager.master.app.controller.user;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.dmsoft.flowmanager.be.entities.User;
import it.dmsoft.flowmanager.be.entities.UserColumnsConfig;
import it.dmsoft.flowmanager.be.keys.UserId;
import it.dmsoft.flowmanager.be.keys.UserPageId;
import it.dmsoft.flowmanager.common.model.UserColumnsConfigData;
import it.dmsoft.flowmanager.common.model.UserData;
import it.dmsoft.flowmanager.framework.api.base.BaseService;
import jakarta.annotation.Resource;

@RestController
@RequestMapping("/users")
@PreAuthorize("hasAnyAuthority('flowmanager_use')")
public class UserController {

    @Resource(name = "userService")
    private BaseService<User, UserData, UserId> userService;
    

    @Resource(name = "userColumnsConfigService")
    private BaseService<UserColumnsConfig, UserColumnsConfigData, UserPageId> userColumnsConfigService;

    /**
     * <p>Get all user data in the system.For production system you many want to use
     * pagination.</p>
     * @return List<ModelData>
     */
    @GetMapping
    public List<UserData> getUsers() {
        return userService.getAll();
    }

    /**
     * Method to get the user data based on the ID.
     * @param id
     * @return ModelData
     */
    @GetMapping("/user/{customer}/{username}")
    public UserData getUser(@PathVariable String customer,
            				@PathVariable String username) {
    	UserId id = new UserId(username, customer);
        return userService.getById(id);
    }

    /**
     * Post request to create user information int the system.
     * @param modelData
     * @return
     */
    @PostMapping("/user")
    public UserData saveUser(final @RequestBody UserData userData) {
        return userService.save(userData);
    }

    /**
     * <p>Delete user from the system based on the ID. The method mapping is like the getUser  with difference of
     * @DeleteMapping and @GetMapping</p>
     * @param id
     * @return
     */
    @DeleteMapping("/user/{customer}/{username}")
    public Boolean deleteUser(@PathVariable String customer,
							  @PathVariable String username) {
    	UserId id = new UserId(username, customer);
        return userService.delete(id);
    }
    
    /**
     * <p>Update user columns preferences for a specific table
     * @param modelData
     * @return
     */
    @PostMapping("/table-preferences")
    public UserColumnsConfigData saveTablePreferences(final @RequestBody UserColumnsConfigData tabPref) {
        return userColumnsConfigService.save(tabPref);
    }

}