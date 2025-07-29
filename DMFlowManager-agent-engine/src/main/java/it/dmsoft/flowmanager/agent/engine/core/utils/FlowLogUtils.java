package it.dmsoft.flowmanager.agent.engine.core.utils;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import it.dmsoft.flowmanager.be.entities.FlowLog;
import it.dmsoft.flowmanager.be.entities.FlowLogDetails;
import it.dmsoft.flowmanager.be.repositories.FlowLogDetailsRepository;
import it.dmsoft.flowmanager.be.repositories.FlowLogRepository;
import it.dmsoft.flowmanager.agent.engine.core.mapper.FlowLogMapper;
import it.dmsoft.flowmanager.agent.engine.core.model.ExecutionFlowData;
import it.dmsoft.flowmanager.agent.engine.core.operations.params.OperationParams;
import it.dmsoft.flowmanager.agent.engine.core.utils.Constants.OperationType;
import it.dmsoft.flowmanager.common.domain.Domains.Status;

//@Service("logDb")
public class FlowLogUtils {
	
	private static FlowLogUtils instance = null;
	
	@Autowired
    private FlowLogMapper flowLogMapper;
	
	private OperationParams operationParams;
	
	private FlowLogRepository flowLogRepository;
	
	private FlowLogDetailsRepository flowLogDetailsRepository;
	
	private BigDecimal phaseProg;
	
	public static void instantiate(OperationParams operationParams, FlowLogRepository flowLogRepository, FlowLogDetailsRepository flowLogDetailsRepository) {
		if (instance == null) {
			instance = new FlowLogUtils(operationParams);
		}
		
		instance.flowLogDetailsRepository = flowLogDetailsRepository;
		instance.flowLogRepository = flowLogRepository;
	}

	private FlowLogUtils(OperationParams operationParams) {
		this.operationParams = operationParams;
		phaseProg = BigDecimal.ZERO;
	}
	
	public static FlowLog insertHeadLog(ExecutionFlowData executionFlowData, String logFile) {
		return instance.writeFlowLog(executionFlowData, logFile);
	}
	
	public static void startDetail(OperationType operation) throws Exception {		
		instance.writeFlowLogDetails(operation, Constants.START_PHASE_DESCR, Constants.OK);
	}
	
	public static void endDetail(OperationType operation) throws Exception {
		instance.writeFlowLogDetails(operation, Constants.END_PHASE_DESCR, Constants.OK);
	}
	
	public static void koDetail() throws Exception {
		instance.writeFlowLogDetails(null, Constants.KO_DESCR, Constants.KO);
	}
	
	public static void updateBackupPath(BigDecimal transactionId, String destFile) {
		FlowLog flowLog = instance.flowLogRepository.getReferenceById(transactionId);
		flowLog.setLogBackup(destFile);
		instance.flowLogRepository.save(flowLog);
	}

	private void writeFlowLogDetails(OperationType operation, String phaseDescr, String outcome) throws Exception {
		FlowLogDetails flowDetails = getFlowLogDetail(operation, phaseDescr, outcome);
		flowLogDetailsRepository.save(flowDetails);
	}

	private FlowLogDetails getFlowLogDetail(OperationType operation, String phaseDescr, String outcome) {
		Date date= new Date();
		
		phaseProg = phaseProg.add(BigDecimal.ONE);
		FlowLogDetails flowLogDetails = new FlowLogDetails();
		flowLogDetails.setLogDetEsito(Status.getStatus(outcome));
		flowLogDetails.setLogDetFase(operation != null ? operation.name() : Constants.SPACE);
		flowLogDetails.setLogDetNote(phaseDescr + (operation != null ? Constants.SPACE + operation.getDescription() : ""));
		flowLogDetails.setLogDetProgrFase(phaseProg);
		flowLogDetails.setLogDetProgrLog(operationParams.getTransactionId());
		flowLogDetails.setLogDetTs(new Timestamp(date.getTime()));
		
		return flowLogDetails;
	}
	
	private FlowLog writeFlowLog(ExecutionFlowData executionFlowData, String logFile) {
		FlowLog flowLog = getFlowLog(executionFlowData);
		flowLog.setLogFile(logFile);
		return flowLogRepository.save(flowLog);
	}
	
	private FlowLog getFlowLog(ExecutionFlowData executionFlowData) {
		FlowLog flowLog = flowLogMapper.convert(executionFlowData);
		return flowLog;
	}

}
