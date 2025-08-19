package it.dmsoft.flowmanager.master.api.log;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import it.dmsoft.flowmanager.be.entities.FlowLogDetails;
import it.dmsoft.flowmanager.be.keys.FlowLogDetailsId;
import it.dmsoft.flowmanager.common.model.FlowLogDetailsData;
import it.dmsoft.flowmanager.framework.api.base.BaseMapper;
import it.dmsoft.flowmanager.framework.api.base.DefaultBaseService;
import it.dmsoft.flowmanager.master.api.log.mapper.FlowLogDetailsMapper;
import it.dmsoft.flowmanager.master.repositories.FlowLogDetailsRepository;

@Service("flowLogDetailsService")
public class DefaultFlowLogDetailsService extends DefaultBaseService<FlowLogDetails, FlowLogDetailsData, FlowLogDetailsId> {

	    private FlowLogDetailsRepository flowLogDetailsRepository;
	    
	    @Autowired
	    private FlowLogDetailsMapper flowLogDetailsMapper;

	    
	    public DefaultFlowLogDetailsService(FlowLogDetailsRepository flowLogDetailsRepository) {
			super();
			this.flowLogDetailsRepository = flowLogDetailsRepository;
		}


		@Override
		protected BaseMapper<FlowLogDetails, FlowLogDetailsData> getMapper() {
			return flowLogDetailsMapper;
		}


		@Override
		protected JpaRepository<FlowLogDetails, FlowLogDetailsId> getRepository() {
			return flowLogDetailsRepository;
		}
	    
}
