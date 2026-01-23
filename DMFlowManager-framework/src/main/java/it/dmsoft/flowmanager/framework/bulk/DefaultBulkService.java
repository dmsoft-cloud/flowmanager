package it.dmsoft.flowmanager.framework.bulk;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

public class DefaultBulkService<T> implements BulkServiceInterface<T> {

	 	private final Function<T, Boolean> deleteFn;
	    private final BiFunction<T, Boolean, Boolean> activationFn;

	    /**
	     * @param deleteFn     qualcosa tipo: dto -> groupService.delete(dto)  (ritorna true/false)
	     * @param activationFn qualcosa tipo: (dto, active) -> groupService.setActive(dto, active) (ritorna true/false)
	     */
	    public DefaultBulkService(java.util.function.Function<T, Boolean> deleteFn,
                java.util.function.BiFunction<T, Boolean, Boolean> activationFn) {
			this.deleteFn = deleteFn;
			this.activationFn = activationFn;
		}

		@Override
		public BulkResult<T> deleteBulk(List<T> items, boolean failFast) {
			return run(items, deleteFn, failFast);
		}

		@Override
		public BulkResult<T> activateBulk(List<T> items, boolean failFast) {
			return run(items, it -> activationFn.apply(it, true), failFast);
		}

		@Override
		public BulkResult<T> deactivateBulk(List<T> items, boolean failFast) {
			return run(items, it -> activationFn.apply(it, false), failFast);
		}

		private BulkResult<T> run(List<T> items, java.util.function.Function<T, Boolean> op, boolean failFast) {
			int ok = 0;
			Map<T, String> errors = new LinkedHashMap<>();
			for (T item : items) {
				try {
				  boolean success = Boolean.TRUE.equals(op.apply(item));
				  if (success) {
					ok++;
				} else {
				      errors.put(item, "Returned false");
				      if (failFast) {
						break;
					}
				  }
				} catch (Exception ex) {
				  errors.put(item, ex.getMessage() != null ? item + ex.getMessage() : item + ex.getClass().getSimpleName());
				  	if (failFast) {
						break;
					}
				}
			}
		return new BulkResult<>(items.size(), ok, errors);
		}
}

