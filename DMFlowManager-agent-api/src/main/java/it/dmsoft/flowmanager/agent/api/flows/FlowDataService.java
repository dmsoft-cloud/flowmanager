package it.dmsoft.flowmanager.agent.api.flows;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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
import it.dmsoft.flowmanager.framework.json.UtilityJson;

@Service("flowDataService")
public class FlowDataService {

	@Value("${flowmanager.agent.config.data.file}")
	private String dataFile;
	
	@Value("${flowmanager.agent.properties.mailInterfaceId}")
	private String mailInterfaceId;
	
    @Autowired
    private FlowDataMapper flowDataMapper;
	
	private static FullFlowsData flowsData;

	public FullFlowsData storeFullFlowsData(final FullFlowsData fullFlowsData) {

		try {
			File file = new File(dataFile);
			UtilityJson.getMapper().writeValue(file, fullFlowsData);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		flowsData = fullFlowsData;
		return fullFlowsData;
	}
	
	public FullFlowData getFullFlowData(String flowId) {
		//TODO HANDLE BETTER CACHE
		if(flowsData == null) {
			flowsData = loadFullFlowsData();
		}
		
		FlowData flow = flowsData.getFlows().stream().filter(x -> x.getId().equals(flowId)).findAny().get();
		GroupData group = flowsData.getGroups().stream().filter(x -> x.getId().equals(flow.getGroupId())).findAny().orElse(null);
		EmailData emailOk = flowsData.getEmails().stream().filter(x -> x.getId().equals(flow.getNotificationOk())).findAny().orElse(null);
		EmailData emailKo = flowsData.getEmails().stream().filter(x -> x.getId().equals(flow.getNotificationKo())).findAny().orElse(null);
		//flowsData.get
		
		InterfaceData _interface = flowsData.getInterfaces().stream().filter(x -> x.getId().equals(flow.getInterfaceId())).findAny().get();
		InterfaceData mailInterface = flowsData.getInterfaces().stream().filter(x -> x.getId().equals(mailInterfaceId)).findAny().orElse(null);;
		
		
		ModelData model = flowsData.getModels().stream().filter(x -> x.getId().equals(flow.getModel())).findAny().get();
		
		List<OriginData> origins = flowsData.getOrigins();
		OriginData origin = origins.stream().filter(x -> x.getId().equals(flow.getOrigin())).findAny().orElse(null);
		
		FullFlowData fullFlowData = new FullFlowData(flow, group, emailOk, emailKo, _interface, model, origin, mailInterface);
		return fullFlowData;
	}
	
	public ExecutionFlowData getExecutionFlowData(String flowId) {
		return getExecutionFlowData(getFullFlowData(flowId));
	}
	
	public ExecutionFlowData getExecutionFlowData(FullFlowData fullFlowData) {
		return flowDataMapper.convert(fullFlowData);
	}
	
	
	private FullFlowsData loadFullFlowsData() {
		try {
			File file = new File(dataFile);
			return UtilityJson.readValue(file, FullFlowsData.class);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
	}

}
