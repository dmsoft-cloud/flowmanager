package it.dmsoft.flowmanager.agent.engine.core.utils;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;

import it.dmsoft.flowmanager.agent.engine.core.mapper.FlowLogMapper;
import it.dmsoft.flowmanager.agent.engine.core.model.ExecutionFlowData;
import it.dmsoft.flowmanager.agent.engine.core.utils.Constants.OperationType;
import it.dmsoft.flowmanager.agent.engine.zip.model.ZipResponse.Outcome;
import it.dmsoft.flowmanager.common.domain.Domains.Status;
import it.dmsoft.flowmanager.common.engine.FlowConfig;
import it.dmsoft.flowmanager.common.model.FlowExecutionOutcome;
import it.dmsoft.flowmanager.common.model.FlowLogData;
import it.dmsoft.flowmanager.common.model.FlowLogDetailsData;
import it.dmsoft.flowmanager.framework.json.UtilityJson;

@Service("flowLogUtils")
public class FlowLogUtils {
	
	private static FlowLogUtils instance = null;
	
	@Autowired
    private FlowLogMapper flowLogMapper;	
	
	private static ThreadLocal<FlowExecutionOutcome> threadLocalExecStatus = new ThreadLocal<>();
	
	private static ThreadLocal<WebSocketSession> threadLocalWebSocket = new ThreadLocal<>();
	
	public FlowLogUtils() {
		instance = this;
	}
	
	public static FlowLogData headLog(BigDecimal flowProg, ExecutionFlowData executionFlowData, FlowConfig flowConfig, WebSocketSession webSocketSession) {
		FlowLogData fld = instance.getFlowLog(executionFlowData);
		fld.setLogProgrLog(flowProg);
		
		FlowExecutionOutcome fes = new FlowExecutionOutcome();
		fes.setLogData(fld);
		fes.setLogDetailsData(new ArrayList<FlowLogDetailsData>());
		
		threadLocalExecStatus.set(fes);
		threadLocalWebSocket.set(webSocketSession);
		
		return fld;
	}

	public static void startDetail(OperationType operation) throws Exception {		
		instance.addFlowLogDetail(operation, Constants.START_PHASE_DESCR, Constants.OK);
	}
	
	public static void endDetail(OperationType operation) throws Exception {
		instance.addFlowLogDetail(operation, Constants.END_PHASE_DESCR, Constants.OK);
	}
	
	public static void koDetail() throws Exception {
		instance.addFlowLogDetail(null, Constants.KO_DESCR, Constants.KO);
	}
	
	public static void updateLogPath(String logFile) throws StreamWriteException, DatabindException, IOException {
		FlowLogData fld = threadLocalExecStatus.get().getLogData();
		fld.setLogLogFile(logFile);
		
		//CREATE FOLDER WITH FLOW PROGR
		String logDirectory = logFile + Constants.UNDERSCORE;
		Files.createDirectories(Paths.get(logDirectory));
		File file = new File(logDirectory + Constants.PATH_DELIMITER + FlowLogData.class.getSimpleName());
		UtilityJson.getMapper().writeValue(file, fld);
		
		notifyOnWebSocketIfNecessary(fld);
	}
	
	public static void updateBackupPath(BigDecimal transactionId, String destFile) {
		FlowLogData fld = threadLocalExecStatus.get().getLogData();
		fld.setLogBackup(destFile);
	}

	private FlowLogDetailsData addFlowLogDetail(OperationType operation, String phaseDescr, String outcome) throws StreamWriteException, DatabindException, IOException {
		Date date= new Date();
		
		FlowExecutionOutcome fes = threadLocalExecStatus.get();
		FlowLogData fld = fes.getLogData();
		List<FlowLogDetailsData> flowDetailsData = fes.getLogDetailsData();
		
		BigDecimal lastProg = flowDetailsData.size() == 0 ? BigDecimal.ZERO : flowDetailsData.get(flowDetailsData.size() - 1).getLogProgrFase();
		
		BigDecimal phaseProg = lastProg.add(BigDecimal.ONE);
		FlowLogDetailsData fldd = new FlowLogDetailsData();
		
		fldd.setLogProgrLog(fld.getLogProgrLog());
		fldd.setLogProgrFase(phaseProg);
		fldd.setLogDetEsito(Status.getStatus(outcome));
		fldd.setLogDetFase(operation != null ? operation.name() : Constants.SPACE);
		fldd.setLogDetNote(phaseDescr + (operation != null ? Constants.SPACE + operation.getDescription() : ""));
		fldd.setLogDetTs(new Timestamp(date.getTime()));
		
		threadLocalExecStatus.get().getLogDetailsData().add(fldd);
		
		//CREATE FOLDER WITH FLOW PROGR
		String logDirectory = fld.getLogLogFile() + Constants.UNDERSCORE;
		File file = new File(logDirectory + Constants.PATH_DELIMITER + FlowLogDetailsData.class.getSimpleName() + phaseDescr + operation);
		UtilityJson.getMapper().writeValue(file, fld);
		
		notifyOnWebSocketIfNecessary(fldd);
		return fldd;
	}

	private static void notifyOnWebSocketIfNecessary(Object data) {
		notifyOnWebSocketIfNecessary(data, false);
	}
	private static void notifyOnWebSocketIfNecessary(Object data, boolean lastMsg) {
		WebSocketSession wss = threadLocalWebSocket.get();
		
		if(wss == null)
			return;
		
		try {
    		TextMessage textMessage = new TextMessage(UtilityJson.writeValueAsString(data));
			wss.sendMessage(textMessage);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	//TODO ADD WRITE FLOWLOGDATA OF THE FLOW OUTCOME EXECUTION AND NOTIFY IT ON WEBSOCKET SO YOU CAN CLOSE THE WEBSOCKET
	//LAST ADD SYNCH METHOD FOR LOGS RETRIEVE
	public static void updateOutcome(Outcome outcome) throws StreamWriteException, DatabindException, IOException {
		FlowLogData fld = threadLocalExecStatus.get().getLogData();
		
		String logDirectory = fld.getLogLogFile() + Constants.UNDERSCORE;
		File file = new File(logDirectory + Constants.PATH_DELIMITER + FlowLogData.class.getSimpleName());
		UtilityJson.getMapper().writeValue(file, fld);
		notifyOnWebSocketIfNecessary(fld, true);
	}

	private FlowLogData getFlowLog(ExecutionFlowData executionFlowData) {
		return flowLogMapper.convert(executionFlowData);
	}
	
	public static FlowExecutionOutcome getFlowExecutionOutcome() {
		return threadLocalExecStatus.get();
	}

}
