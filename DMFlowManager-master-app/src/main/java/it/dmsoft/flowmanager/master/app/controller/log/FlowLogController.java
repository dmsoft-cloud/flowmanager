package it.dmsoft.flowmanager.master.app.controller.log;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.dmsoft.flowmanager.common.model.FlowData;
import it.dmsoft.flowmanager.common.model.FlowDataWithDirection;
import it.dmsoft.flowmanager.common.model.FlowLogData;
import it.dmsoft.flowmanager.framework.api.base.BaseService;
import it.dmsoft.flowmanager.master.api.flow.DefaultFlowlService;
import it.dmsoft.flowmanager.master.api.log.DefaultFlowLogService;
import it.dmsoft.flowmanager.be.entities.Flow;
import it.dmsoft.flowmanager.be.filters.FlowFilterDTO;
import jakarta.annotation.Resource;

@RestController
@RequestMapping("/flowLogs")
public class FlowLogController {
	
	@Resource(name = "flowLogService")
	private DefaultFlowLogService flowLogService;
    //private BaseService<Flow, FlowData, String> flowService;	

    /**
     * <p>Get all FlowLog data in the system.For production system you many want to use
     * pagination.</p>
     * @return List<FlowLogData>
     */
    @GetMapping
    public List<FlowLogData> getFlowLogs() {
        return flowLogService.getAll();
    }
    

    /**
     * Method to get the FlowLog data based on the ID.
     * @param id
     * @return FlowLogData
     */
    @GetMapping("/flowLog/{id}")
    public FlowLogData getFlow(@PathVariable String id) {
        return flowLogService.getById(new BigDecimal(id));
    }

	
}
