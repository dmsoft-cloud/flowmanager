package it.dmsoft.flowmanager.agent.engine.generic.genericWsClient;

import java.lang.reflect.Method;

public class MetodoClasse {
	public MetodoClasse(Method m, Class<?> c) {
		super();
		this.m = m;
		this.c = c;
	}

	private Method m;
	private Class<?> c;

	public Method getM() {
		return m;
	}

	public void setM(Method m) {
		this.m = m;
	}

	public Class<?> getC() {
		return c;
	}

	public void setC(Class<?> c) {
		this.c = c;
	}

}
