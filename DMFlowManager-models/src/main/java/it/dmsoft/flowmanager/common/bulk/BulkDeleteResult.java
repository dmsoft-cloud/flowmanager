package it.dmsoft.flowmanager.common.bulk;

import java.time.Instant;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class BulkDeleteResult {
    private final String jobId;
    private final int requested;
    private volatile int deleted;
    private volatile int failed;
    private volatile DeleteJobStatus status;
    private final Map<String, String> errors = Collections.synchronizedMap(new LinkedHashMap<>());
    private final Instant startedAt;
    private volatile Instant updatedAt;

    public BulkDeleteResult(String jobId, int requested) {
        this.jobId = jobId;
        this.requested = requested;
        this.status = DeleteJobStatus.STARTED;
        this.startedAt = Instant.now();
        this.updatedAt = this.startedAt;
    }

    public String getJobId() { return jobId; }
    public int getRequested() { return requested; }
    public int getDeleted() { return deleted; }
    public int getFailed() { return failed; }
    public DeleteJobStatus getStatus() { return status; }
    public Map<String, String> getErrors() { return errors; }
    public Instant getStartedAt() { return startedAt; }
    public Instant getUpdatedAt() { return updatedAt; }

    public synchronized void incDeleted() { this.deleted++; touch(); }
    public synchronized void incFailed(String id, String message) { this.failed++; this.errors.put(id, message); touch(); }
    public synchronized void setStatus(DeleteJobStatus status) { this.status = status; touch(); }

    public synchronized BulkDeleteSnapshot snapshot() {
        return new BulkDeleteSnapshot(jobId, requested, deleted, failed, status,
                new LinkedHashMap<>(errors), startedAt, updatedAt);
    }

    private void touch() { this.updatedAt = Instant.now(); }
}