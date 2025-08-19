package it.dmsoft.flowmanager.master.api.execute;

import java.io.IOException;
import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import it.dmsoft.flowmanager.be.entities.Agent;
import it.dmsoft.flowmanager.be.entities.Flow;
import it.dmsoft.flowmanager.be.entities.FlowLog;
import it.dmsoft.flowmanager.be.entities.FlowLogDetails;
import it.dmsoft.flowmanager.be.keys.FlowLogDetailsId;
import it.dmsoft.flowmanager.common.model.AgentData;
import it.dmsoft.flowmanager.common.model.FlowData;
import it.dmsoft.flowmanager.common.model.FlowExecutionOutcome;
import it.dmsoft.flowmanager.common.model.FlowLogData;
import it.dmsoft.flowmanager.common.model.FlowLogDetailsData;
import it.dmsoft.flowmanager.common.model.FullFlowData;
import it.dmsoft.flowmanager.framework.api.base.BaseService;
import it.dmsoft.flowmanager.framework.rest.RestClientHelper;
import it.dmsoft.flowmanager.master.api.websocket.WebSocketHelper;
import jakarta.annotation.Resource;

@Service("executeService")
public class ExecuteService {
	
	@Resource(name = "flowService")
    private BaseService<Flow, FlowData, String> flowService;
	
    @Resource(name = "agentService")
    private BaseService<Agent, AgentData, String> agentService;
    
    @Resource(name = "flowLogService")
    private BaseService<FlowLog, FlowLogData, String> flowLogService;
    
    @Resource(name = "flowLogDetailsService")
    private BaseService<FlowLogDetails, FlowLogDetailsData, FlowLogDetailsId> flowLogDetailsService;

    @Autowired
    private ExecuteWebSocketClient executeWebSocketClient;
    
    public FlowExecutionOutcome synch(String flowId, FullFlowData fullFlowData) {
    	return execute(flowId, fullFlowData, false);
    }
    
    public FlowExecutionOutcome asynch(String flowId, FullFlowData fullFlowData) {
    	return execute(flowId, fullFlowData, true);
    }
    
	private FlowExecutionOutcome execute(String flowId, FullFlowData fullFlowData, boolean asynch) {
		FlowData flowData = flowService.getById(flowId);
		AgentData agent = flowData.getAgentId() != null ? agentService.getById(flowData.getAgentId()) : null;
		
		FlowLogData fld = new FlowLogData();
		fld.setLogId(flowId);
		fld = flowLogService.save(fld);
		BigDecimal flowProgr = fld.getLogProgrLog();
		
		FlowExecutionOutcome feo = null;
		if(asynch) {
			feo = new FlowExecutionOutcome();
			feo.setLogData(fld);
			
			if (agent != null) {
				WebSocketHelper.openSocket(executeWebSocketClient, agent);
			} else {
				WebSocketHelper.openSocket(executeWebSocketClient);
			}
			
			try {
				executeWebSocketClient.sendMessage(flowId, flowProgr, fullFlowData);
			} catch (InterruptedException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			feo = rest(agent, flowId, flowProgr);
			flowLogService.save(feo.getLogData());
			
			for (FlowLogDetailsData fldd : feo.getLogDetailsData()) {
				flowLogDetailsService.save(fldd);
			}
		}
		
		
		return feo;
	}
	
	private FlowExecutionOutcome rest(AgentData agent, String flowId, BigDecimal flowProgr) {
		RestClient rc = agent != null ? RestClientHelper.getAgentRestClient(agent) : RestClientHelper.getDefaultAgentRestClient();
		
		FlowExecutionOutcome outcome = rc.post()
				.uri(uriBuilder -> uriBuilder
			      .path("/execute/synch/")
			      .path(flowId)
			      .path("/")
			      .path(flowProgr.toString())
			      .build())
				.contentType(MediaType.APPLICATION_JSON)
				//.body(fullFlowData)
				.retrieve()
				.body(FlowExecutionOutcome.class);
		
		storeLogs(outcome);
		
		return outcome;
	}

	private void storeLogs(FlowExecutionOutcome outcome) {
		flowLogService.save(outcome.getLogData());
		
		for(FlowLogDetailsData fldd : outcome.getLogDetailsData()) {
			flowLogDetailsService.save(fldd);
		}
	}

}
