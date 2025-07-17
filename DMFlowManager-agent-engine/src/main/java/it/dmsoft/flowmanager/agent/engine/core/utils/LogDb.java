package it.dmsoft.flowmanager.agent.engine.core.utils;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import it.dmsoft.flowmanager.agent.engine.core.db.dao.OtgfflogdDAO;
import it.dmsoft.flowmanager.agent.engine.core.db.dto.Otgfflogd;
import it.dmsoft.flowmanager.agent.engine.core.operations.params.OperationParams;
import it.dmsoft.flowmanager.agent.engine.core.utils.Constants.OperationType;

public class LogDb {
	
	private static LogDb instance = null;
	
	private OperationParams operationParams;
	
	private BigDecimal phaseProg;
	
	public static void instantiate(OperationParams operationParams) {
		if (instance == null) {
			instance = new LogDb(operationParams);
		}
		
	}
	
	private LogDb(OperationParams operationParams) {
		this.operationParams = operationParams;
		phaseProg = BigDecimal.ZERO;
	}
	
	public static void start(OperationType operation) throws Exception {		
		instance.writeOtgfflogd(operation, Constants.START_PHASE_DESCR, Constants.OK);
	}
	
	public static void end(OperationType operation) throws Exception {
		instance.writeOtgfflogd(operation, Constants.END_PHASE_DESCR, Constants.OK);
	}
	
	public static void ko() throws Exception {
		instance.writeOtgfflogd(null, Constants.KO_DESCR, Constants.KO);
	}

	private void writeOtgfflogd(OperationType operation, String phaseDescr, String outcome) throws Exception {
		Otgfflogd otgfflogd = getOtgfflogd(operation, phaseDescr, outcome);
		OtgfflogdDAO.insert(otgfflogd);		
	}

	private Otgfflogd getOtgfflogd(OperationType operation, String phaseDescr, String outcome) {
		Date date= new Date();
		
		phaseProg = phaseProg.add(BigDecimal.ONE);
		Otgfflogd otgfflogd = new Otgfflogd();
		otgfflogd.setOtgflogd_Esito(outcome);
		otgfflogd.setOtgflogd_Fase(operation != null ? operation.name() : Constants.SPACE);
		otgfflogd.setOtgflogd_Note(phaseDescr + (operation != null ? Constants.SPACE + operation.getDescription() : ""));
		otgfflogd.setOtgflogd_Progr_Fase(phaseProg);
		otgfflogd.setOtgflogd_Progr_Log(operationParams.getTransactionId());
		otgfflogd.setOtgflogd_Ts(new Timestamp(date.getTime()));
		
		return otgfflogd;
	}
}
