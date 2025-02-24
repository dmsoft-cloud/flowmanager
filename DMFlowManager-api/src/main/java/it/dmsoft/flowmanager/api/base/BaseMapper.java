package it.dmsoft.flowmanager.api.base;

public interface BaseMapper<X, Y> {
	
	X convertModel(Y model);

	Y convertEntity(X entity);

}
