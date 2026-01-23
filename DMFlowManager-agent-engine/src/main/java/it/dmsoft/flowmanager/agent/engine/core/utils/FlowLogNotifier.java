package it.dmsoft.flowmanager.agent.engine.core.utils;

import java.math.BigDecimal;

public interface FlowLogNotifier {
	void notify(BigDecimal flowProgr, Object payload);
}
