package it.dmsoft.flowmanager.master.api.log;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

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
import it.dmsoft.flowmanager.master.repositories.FlowLogRepository;





@Service("flowLogDetailsService")
public class DefaultFlowLogDetailsService extends DefaultBaseService<FlowLogDetails, FlowLogDetailsData, FlowLogDetailsId> {

	@Autowired
	private FlowLogDetailsRepository flowLogDetailsRepository;
    
    @Autowired
    private FlowLogDetailsMapper flowLogDetailsMapper;

    
    public DefaultFlowLogDetailsService(FlowLogRepository flowLogRepository) {
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
	
	public List<FlowLogDetailsData> findByProgrLog(BigDecimal logProgrLog) {
		List<FlowLogDetailsData> lst =
		 flowLogDetailsRepository
            .findByFlowLogDetailsIdLogProgrLog(logProgrLog)
            .stream()
            .map(logDetail -> flowLogDetailsMapper.convertEntity(logDetail))
            .collect(Collectors.toList());
		return lst;
    }

	    
}
