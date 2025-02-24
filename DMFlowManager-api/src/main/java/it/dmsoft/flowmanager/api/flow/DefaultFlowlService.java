package it.dmsoft.flowmanager.api.flow;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import it.dmsoft.flowmanager.api.base.BaseMapper;
import it.dmsoft.flowmanager.api.base.DefaultBaseService;
import it.dmsoft.flowmanager.api.flow.mapper.FlowMapper;
import it.dmsoft.flowmanager.api.flow.model.FlowData;
import it.dmsoft.flowmanager.be.entities.Flow;
import it.dmsoft.flowmanager.be.repositories.FlowRepository;


@Service("flowService")
public class DefaultFlowlService extends DefaultBaseService<Flow, FlowData, String> {

	    private FlowRepository flowRepository;
	    
	    @Autowired
	    private FlowMapper modelMapper;

	    
	    public DefaultFlowlService(FlowRepository flowRepository) {
			super();
			this.flowRepository = flowRepository;
		}

		@Override
		protected BaseMapper<Flow, FlowData> getMapper() {
			return modelMapper;
		}

		@Override
		protected JpaRepository<Flow, String> getRepository() {
			return flowRepository;
		}
	    
}
