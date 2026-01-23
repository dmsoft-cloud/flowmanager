package it.dmsoft.flowmanager.framework.exception;

import java.util.Map;

public class BulkDeleteSyncException extends RuntimeException {

    private final int requested;
    private final int deleted;
    private final int failed;
    private final Map<String, String> errors;

    public BulkDeleteSyncException(String message, int requested, int deleted, int failed, Map<String, String> errors) {
        super(message);
        this.requested = requested;
        this.deleted = deleted;
        this.failed = failed;
        this.errors = errors;
    }
    public int getRequested() { return requested; }
    public int getDeleted() { return deleted; }
    public int getFailed() { return failed; }
    public Map<String, String> getErrors() { return errors; }

}
