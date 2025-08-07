package it.dmsoft.flowmanager.master.app.controller.log;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.dmsoft.flowmanager.common.domain.Domains.Direction;
import it.dmsoft.flowmanager.common.model.FlowData;
import it.dmsoft.flowmanager.common.model.FlowDataWithDirection;
import it.dmsoft.flowmanager.common.model.FlowLogData;
import it.dmsoft.flowmanager.common.model.FlowLogDetailsData;
import it.dmsoft.flowmanager.framework.api.base.BaseService;
import it.dmsoft.flowmanager.master.api.flow.DefaultFlowlService;
import it.dmsoft.flowmanager.master.api.log.DefaultFlowLogDetailsService;
import it.dmsoft.flowmanager.master.api.log.DefaultFlowLogService;
import it.dmsoft.flowmanager.be.entities.Flow;
import it.dmsoft.flowmanager.be.entities.Model;
import it.dmsoft.flowmanager.be.filters.FlowFilterDTO;
import jakarta.annotation.Resource;

@RestController
@RequestMapping("/flowLogDetails")
public class FlowLogDetailsController {
	
	@Resource(name = "flowLogDetailsService")
	private DefaultFlowLogDetailsService flowLogDetailsService;	

    /**
     * <p>Get all FlowLogDetails data in the system.For production system you many want to use
     * pagination.</p>
     * @return List<FlowLogDetailsData>
     */
    @GetMapping
    public List<FlowLogDetailsData> getFlowLogsDetails() {
        return flowLogDetailsService.getAll();
    }
    

    /**
     * Method to get the FlowLogDetails data based on the ID.
     * @param id
     * @return FlowLogDetailsData
     */
    @GetMapping("/details/{id}")
    public List<FlowLogDetailsData> getFlowLogsDetailsById(@PathVariable String id) {
    	BigDecimal idDec = new BigDecimal(id);
        return   flowLogDetailsService.findByProgrLog(idDec);
    }

	
}
