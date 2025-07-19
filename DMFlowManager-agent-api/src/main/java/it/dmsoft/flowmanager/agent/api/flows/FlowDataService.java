package it.dmsoft.flowmanager.agent.api.flows;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import it.dmsoft.flowmanager.agent.api.flows.mapper.FlowDataMapper;
import it.dmsoft.flowmanager.agent.engine.core.model.ExecutionFlowData;
import it.dmsoft.flowmanager.common.model.EmailData;
import it.dmsoft.flowmanager.common.model.FlowData;
import it.dmsoft.flowmanager.common.model.FullFlowData;
import it.dmsoft.flowmanager.common.model.FullFlowsData;
import it.dmsoft.flowmanager.common.model.GroupData;
import it.dmsoft.flowmanager.common.model.InterfaceData;
import it.dmsoft.flowmanager.common.model.ModelData;
import it.dmsoft.flowmanager.common.model.OriginData;

@Service("synchService")
public class FlowDataService {

	@Value("${flowmanager.agent.config.data.file}")
	private String dataFile;
	
    @Autowired
    private FlowDataMapper flowDataMapper;
	
	private static FullFlowsData flowsData;

	public FullFlowsData storeFullFlowsData(final FullFlowsData fullFlowsData) {
		ObjectMapper om = new ObjectMapper();

		try {
			File file = new File(dataFile);
			om.writeValue(file, fullFlowsData);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		return fullFlowsData;
	}
	
	public FullFlowData getFullFlowData(String flowId) {
		if(flowsData == null) {
			flowsData = loadFullFlowsData();
		}
		
		FlowData flow = flowsData.getFlows().stream().filter(x -> x.getId().equals(flowId)).findAny().get();
		GroupData group = flowsData.getGroups().stream().filter(x -> x.getId().equals(flow.getGroupId())).findAny().get();
		EmailData emailOk = flowsData.getEmails().stream().filter(x -> x.getId().equals(flow.getNotificationOk())).findAny().get();
		EmailData emailKo = flowsData.getEmails().stream().filter(x -> x.getId().equals(flow.getNotificationKo())).findAny().get();
		//flowsData.get
		InterfaceData _interface = flowsData.getInterfaces().stream().filter(x -> x.getId().equals(flow.getInterfaceId())).findAny().get();
		ModelData model = flowsData.getModels().stream().filter(x -> x.getId().equals(flow.getModel())).findAny().get();
		OriginData origin = flowsData.getOrigins().stream().filter(x -> x.getId().equals(flow.getOrigin())).findAny().get();
		
		FullFlowData fullFlowData = new FullFlowData(flow, group, emailOk, emailKo, _interface, model, origin);
		return fullFlowData;
	}
	
	public ExecutionFlowData getExecutionFlowData(String flowId) {
		return getExecutionFlowData(getFullFlowData(flowId));
	}
	
	public ExecutionFlowData getExecutionFlowData(FullFlowData fullFlowData) {
		return flowDataMapper.convert(fullFlowData);
	}
	
	
	private FullFlowsData loadFullFlowsData() {
		ObjectMapper om = new ObjectMapper();

		try {
			File file = new File(dataFile);
			return om.readValue(file, FullFlowsData.class);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
	}

}
