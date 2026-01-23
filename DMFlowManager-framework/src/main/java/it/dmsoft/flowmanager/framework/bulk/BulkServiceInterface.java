package it.dmsoft.flowmanager.framework.bulk;

import java.util.List;

public interface BulkServiceInterface<T> {

    BulkResult<T> deleteBulk(List<T> items, boolean failFast);
    BulkResult<T> activateBulk(List<T> items, boolean failFast);
    BulkResult<T> deactivateBulk(List<T> items, boolean failFast);

}
