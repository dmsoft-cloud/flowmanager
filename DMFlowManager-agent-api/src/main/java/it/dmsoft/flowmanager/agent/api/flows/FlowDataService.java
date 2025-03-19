package it.dmsoft.flowmanager.agent.api.flows;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import it.dmsoft.flowmanager.agent.api.flows.model.FullFlowData;
import it.dmsoft.flowmanager.common.model.FlowData;
import it.dmsoft.flowmanager.common.model.FullFlowsData;

@Service("synchService")
public class FlowDataService {

	@Value("${flowmanager.agent.config.data.file}")
	private String dataFile;
	
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
		FullFlowData fullFlowData = new FullFlowData();
		
		if(flowsData == null) {
			flowsData = loadFullFlowsData();
		}
		
		FlowData flowData = flowsData.getFlows().stream().filter(x -> x.getId().equals(flowId)).findAny().get();
		fullFlowData.setFlow(flowData);
		fullFlowData.setGroup(flowsData.getGroups().stream().filter(x -> x.getId().equals(flowData.getGroupId())).findAny().get());
		fullFlowData.setEmailOk(flowsData.getEmails().stream().filter(x -> x.getId().equals(flowData.getNotificationOk())).findAny().get());
		fullFlowData.setEmailKo(flowsData.getEmails().stream().filter(x -> x.getId().equals(flowData.getNotificationKo())).findAny().get());
		//flowsData.get
		fullFlowData.setInterface(flowsData.getInterfaces().stream().filter(x -> x.getId().equals(flowData.getInterfaceId())).findAny().get());
		fullFlowData.setModel(flowsData.getModels().stream().filter(x -> x.getId().equals(flowData.getModel())).findAny().get());
		fullFlowData.setOrigin(flowsData.getOrigins().stream().filter(x -> x.getId().equals(flowData.getOrigin())).findAny().get());
		
		return fullFlowData;
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
