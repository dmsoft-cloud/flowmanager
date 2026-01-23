package it.dmsoft.flowmanager.master.app.controller.agent;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.dmsoft.flowmanager.common.model.AgentData;
import it.dmsoft.flowmanager.common.model.InterfaceData;
import it.dmsoft.flowmanager.framework.bulk.BulkServiceInterface;



@RestController
@RequestMapping("/bulk/agents")
@PreAuthorize("hasAnyAuthority('flowmanager_use')")
public class AgentControllerBulk {

	@Autowired
    @Qualifier("agentBulkService")
    private BulkServiceInterface<AgentData> bulk;


    @PostMapping("/deleteSync")
    public ResponseEntity<?> delete(@RequestBody List<AgentData> dtos) {
        var res = bulk.deleteBulk(dtos, false);
        return ResponseEntity.ok(res);
    }
    
    @PostMapping("/activateSync")
    public ResponseEntity<?> activate(@RequestBody List<AgentData> dtos) {
        var res = bulk.activateBulk(dtos, false);
        return ResponseEntity.ok(res);
    }
    
    @PostMapping("/deactivateSync")
    public ResponseEntity<?> deactivate(@RequestBody List<AgentData> dtos) {
        var res = bulk.deactivateBulk(dtos, false);
        return ResponseEntity.ok(res);
    }

}