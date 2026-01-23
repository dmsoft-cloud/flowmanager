package it.dmsoft.flowmanager.common.bulk;

import java.time.Instant;
import java.util.Map;

public class BulkDeleteSnapshot {
    private String jobId;
    private int requested;
    private int deleted;
    private int failed;
    private DeleteJobStatus status;
    private Map<String, String> errors;
    private Instant startedAt;
    private Instant updatedAt;

    public BulkDeleteSnapshot(String jobId, int requested, int deleted, int failed, DeleteJobStatus status,
                              Map<String, String> errors, Instant startedAt, Instant updatedAt) {
        this.jobId = jobId;
        this.requested = requested;
        this.deleted = deleted;
        this.failed = failed;
        this.status = status;
        this.errors = errors;
        this.startedAt = startedAt;
        this.updatedAt = updatedAt;
    }

    public String getJobId() { return jobId; }
    public int getRequested() { return requested; }
    public int getDeleted() { return deleted; }
    public int getFailed() { return failed; }
    public DeleteJobStatus getStatus() { return status; }
    public Map<String, String> getErrors() { return errors; }
    public Instant getStartedAt() { return startedAt; }
    public Instant getUpdatedAt() { return updatedAt; }
}