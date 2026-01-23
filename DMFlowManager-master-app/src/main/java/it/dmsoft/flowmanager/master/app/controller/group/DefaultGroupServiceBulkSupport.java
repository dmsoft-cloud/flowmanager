package it.dmsoft.flowmanager.master.app.controller.group;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import it.dmsoft.flowmanager.common.bulk.BulkDeleteResult;
import it.dmsoft.flowmanager.common.bulk.DeleteJobStatus;
import it.dmsoft.flowmanager.common.model.GroupData;
import it.dmsoft.flowmanager.framework.exception.BulkDeleteSyncException;
import it.dmsoft.flowmanager.master.api.group.DefaultGroupService;


@Service
public class DefaultGroupServiceBulkSupport {

    @Autowired
    private DefaultGroupService delegate;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    // In-memory job store (sostituibile con DB se vuoi persistenza)
    private final Map<String, BulkDeleteResult> jobs = new ConcurrentHashMap<>();

    public String startBulkDelete(List<GroupData> groups) {
        String jobId = UUID.randomUUID().toString();
        int requested = groups == null ? 0 : groups.size();
        BulkDeleteResult result = new BulkDeleteResult(jobId, requested);
        result.setStatus(DeleteJobStatus.RUNNING);
        jobs.put(jobId, result);
        runBulkDeleteAsync(jobId, groups);
        return jobId;
    }


    public int runBulkDeleteSync(List<GroupData> groups) {
    	if (groups == null || groups.isEmpty()) {
			return 0;
		}
    	int deletedItems = 0;
    	int failedItems = 0;
    	Map<String, String> errors = new LinkedHashMap<>();

        List<String> ids = groups == null ? List.of()
                : groups.stream()
                        .map(this::safeGroupId)
                        .filter(id -> id != null && !id.isBlank())
                        .distinct()
                        .collect(Collectors.toList());

        for (String id : ids) {
            try {
                boolean ok = delegate.delete(id);
                if (ok) {
                	deletedItems++;
                } else {
                	failedItems++;
                	errors.put(id, "delete() returned false");
                }
            } catch (Exception ex) {
            	failedItems++;
            	errors.put(id, ex.getMessage() != null ? ex.getMessage() : ex.getClass().getSimpleName());
            }
            if (failedItems > 0) {
            	throw new BulkDeleteSyncException(
                        String.format("Bulk delete failed for %d/%d items", failedItems, ids.size()),
                        ids.size(), deletedItems, failedItems, errors
                    );
        	}
        }
        return deletedItems;
    }

    
    private String safeGroupId(GroupData g) {
        try { return g.getId(); } catch (Exception e) { return null; }
    }

    @Async("bulkTaskExecutor")
    protected void runBulkDeleteAsync(String jobId, List<GroupData> groups) {
        BulkDeleteResult res = jobs.get(jobId);
        if (res == null) {
			return;
		}
        try {
            if (groups != null) {
                for (GroupData g : groups) {
                    String id = safeId(g);
                    try {
                        Boolean ok = delegate.delete(id);
                        if (Boolean.TRUE.equals(ok)) {
                            res.incDeleted();
                        } else {
                            res.incFailed(id, "Delete returned false");
                        }
                    } catch (Exception ex) {
                        res.incFailed(id, ex.getMessage() == null ? ex.toString() : ex.getMessage());
                    }
                    // push snapshot dopo ogni item
                    messagingTemplate.convertAndSend(topic(jobId), res.snapshot());
                }
            }
            res.setStatus(DeleteJobStatus.COMPLETED);
        } catch (Exception e) {
            res.setStatus(DeleteJobStatus.FAILED);
        } finally {
            // push finale
            messagingTemplate.convertAndSend(topic(jobId), res.snapshot());
        }
    }

    private String topic(String jobId) {
        return "/topic/bulk-delete/" + jobId;
    }

    private String safeId(GroupData g) {
        // Assumiamo che GroupData estenda BaseModel con getId()
        // Adatta se il tuo identificativo è diverso
        try {
            return g.getId();
        } catch (Exception e) {
            return null;
        }
    }

    public BulkDeleteResult getJob(String jobId) {
        return jobs.get(jobId);
    }

    public String getTopicForJob(String jobId) {
        return topic(jobId);
    }
}