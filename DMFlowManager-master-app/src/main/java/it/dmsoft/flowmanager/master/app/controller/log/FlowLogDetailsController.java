package it.dmsoft.flowmanager.master.app.controller.log;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.dmsoft.flowmanager.common.model.FlowLogDetailsData;
import it.dmsoft.flowmanager.master.api.log.DefaultFlowLogDetailsService;
import jakarta.annotation.Resource;

@RestController
@RequestMapping("/flowLogDetails")
@PreAuthorize("hasAnyAuthority('flowmanager_use')")
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
