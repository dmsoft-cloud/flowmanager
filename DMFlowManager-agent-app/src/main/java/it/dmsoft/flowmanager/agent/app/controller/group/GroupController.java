package it.dmsoft.flowmanager.agent.app.controller.group;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.dmsoft.flowmanager.common.model.GroupData;
import it.dmsoft.flowmanager.framework.api.base.BaseService;
import it.dmsoft.flowmanager.master.be.entities.ConfigurationGroup;
import jakarta.annotation.Resource;

@RestController
@RequestMapping("/groups")
public class GroupController {
	
	@Value("${cors.allowed.origins}")
    private String allowedGroups;

	@Resource(name = "groupService")
    private BaseService<ConfigurationGroup, GroupData, String> groupService;

    /**
     * <p>Get all origin data in the system.For production system you many want to use
     * pagination.</p>
     * @return List<OriginData>
     */
    @GetMapping
    public List<GroupData> getModels() {
        return groupService.getAll();
    }

    /**
     * Method to get the origin data based on the ID.
     * @param id
     * @return OriginData
     */
    @GetMapping("/group/{id}")
    public GroupData getModel(@PathVariable String id) {
        return groupService.getById(id);
    }

    /**
     * Post request to create origin information int the system.
     * @param originData
     * @return
     */
    @PostMapping("/group")
    public GroupData saveModel(final @RequestBody GroupData groupData) {
        return groupService.save(groupData);
    }

    /**
     * <p>Delete origin from the system based on the ID. The method mapping is like the getModel with difference of
     * @DeleteMapping and @GetMapping</p>
     * @param id
     * @return
     */
    @DeleteMapping("/group/{id}")
    public Boolean deleteModel(@PathVariable String id) {
        return groupService.delete(id);
    }
	
}
