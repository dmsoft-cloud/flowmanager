package it.dmsoft.flowmanager.master.app.controller.group;

import java.net.URI;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.dmsoft.flowmanager.common.bulk.BulkDeleteJobResponse;
import it.dmsoft.flowmanager.common.bulk.BulkDeleteResult;
import it.dmsoft.flowmanager.common.model.GroupData;
import it.dmsoft.flowmanager.common.model.InterfaceData;
import it.dmsoft.flowmanager.common.model.OriginData;
import it.dmsoft.flowmanager.framework.bulk.BulkServiceInterface;
import it.dmsoft.flowmanager.framework.exception.BulkDeleteSyncException;



@RestController
@RequestMapping("/bulk/groups")
@PreAuthorize("hasAnyAuthority('flowmanager_use')")
public class GroupControllerBulk {

    @Autowired
    private DefaultGroupServiceBulkSupport bulkService;
    
	@Autowired
    @Qualifier("groupBulkService")
    private BulkServiceInterface<GroupData> bulk;

    /**
     * Avvia una cancellazione asincrona di più group passando la lista di GroupData.
     * Ritorna 202 Accepted con un jobId da interrogare e un topic WebSocket per aggiornamenti live.
     */
    @PostMapping("/delete")
    public ResponseEntity<BulkDeleteJobResponse> bulkDelete(@RequestBody List<GroupData> groups) {
        String jobId = bulkService.startBulkDelete(groups);
        String statusPath = "/bulk/groups/delete/" + jobId;
        String topic = "/topic/groups-bulk-delete/" + jobId;
        return ResponseEntity
            .accepted()
            .location(URI.create(statusPath))
            .body(new BulkDeleteJobResponse(jobId, statusPath, topic));
    }

    /**
     * Ritorna lo stato e le metriche del job asincrono di delete.
     * Fallback o per verifiche puntuali senza WebSocket.
     */
    @GetMapping("/delete/{jobId}")
    public ResponseEntity<?> getBulkDeleteStatus(@PathVariable String jobId) {
        BulkDeleteResult job = bulkService.getJob(jobId);
        if (job == null) {
			return ResponseEntity.notFound().build();
		}
        return ResponseEntity.ok(job.snapshot());
    }


    /**
     * cancella la lista di GroupData.
     */
    @PostMapping("/deleteSync")
    public ResponseEntity<?> bulkDeleteSync(@RequestBody List<GroupData> groups) {
    	try {
	    	int deleted = bulkService.runBulkDeleteSync(groups);
	    	return ResponseEntity.ok(Map.of("requested", groups.size(), "deleted", deleted, "failed", 0));
    	}
    	catch (BulkDeleteSyncException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                "message", ex.getMessage(),
                "requested", ex.getRequested(),
                "deleted", ex.getDeleted(),
                "failed", ex.getFailed(),
                "errors", ex.getErrors()
            ));
        }
    }
    
    @PostMapping("/activateSync")
    public ResponseEntity<?> activate(@RequestBody List<GroupData> dtos) {
        var res = bulk.activateBulk(dtos, false);
        return ResponseEntity.ok(res);
    }
    
    @PostMapping("/deactivateSync")
    public ResponseEntity<?> deactivate(@RequestBody List<GroupData> dtos) {
        var res = bulk.deactivateBulk(dtos, false);
        return ResponseEntity.ok(res);
    }
    
}