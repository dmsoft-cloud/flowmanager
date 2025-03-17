package it.dmsoft.flowmanager.agent.app.controller.flow;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.dmsoft.flowmanager.common.model.FlowData;
import it.dmsoft.flowmanager.framework.api.base.BaseService;
import it.dmsoft.flowmanager.master.be.entities.Flow;
import jakarta.annotation.Resource;

@RestController
@RequestMapping("/flows")
public class FlowController {
	
	@Resource(name = "flowService")
    private BaseService<Flow, FlowData, String> flowService;

    /**
     * <p>Get all Flow data in the system.For production system you many want to use
     * pagination.</p>
     * @return List<FlowData>
     */
    @GetMapping
    public List<FlowData> getFlows() {
        return flowService.getAll();
    }

    /**
     * Method to get the Flow data based on the ID.
     * @param id
     * @return FlowData
     */
    @GetMapping("/flow/{id}")
    public FlowData getFlow(@PathVariable String id) {
        return flowService.getById(id);
    }

    /**
     * Post request to create Flow information int the system.
     * @param FlowData
     * @return
     */
    @PostMapping("/flow")
    public FlowData saveFlow(final @RequestBody FlowData flowData) {
        return flowService.save(flowData);
    }

    /**
     * <p>Delete Flow from the system based on the ID. The method mapping is like the getModel with difference of
     * @DeleteMapping and @GetMapping</p>
     * @param id
     * @return
     */
    @DeleteMapping("/flow/{id}")
    public Boolean deleteFlow(@PathVariable String id) {
        return flowService.delete(id);
    }
	
}
