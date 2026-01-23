package it.dmsoft.flowmanager.framework.bulk;

import java.util.Map;

public final class BulkResult<T> {
	private final int requested;
    private final int succeeded;
    private final Map<T, String> errors;

    public BulkResult(int requested, int succeeded, Map<T, String> errors) {
        this.requested = requested;
        this.succeeded = succeeded;
        this.errors = errors;
    }
    public int getRequested() { return requested; }
    public int getSucceeded() { return succeeded; }
    public int getFailed() { return errors.size(); }
    public Map<T, String> getErrors() { return errors; }
}
