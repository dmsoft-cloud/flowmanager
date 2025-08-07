package it.dmsoft.flowmanager.master.api.log;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import it.dmsoft.flowmanager.common.domain.Domains.Direction;
import it.dmsoft.flowmanager.common.model.FlowLogData;
import it.dmsoft.flowmanager.framework.api.base.BaseMapper;
import it.dmsoft.flowmanager.framework.api.base.DefaultBaseService;
import it.dmsoft.flowmanager.master.api.log.mapper.FlowLogMapper;
import it.dmsoft.flowmanager.be.entities.FlowLog;
import it.dmsoft.flowmanager.be.repositories.FlowLogRepository;




@Service("flowLogService")
public class DefaultFlowLogService extends DefaultBaseService<FlowLog, FlowLogData, BigDecimal> {

    private FlowLogRepository flowLogRepository;
    
    @Autowired
    private FlowLogMapper modelMapper;

    
    public DefaultFlowLogService(FlowLogRepository flowLogRepository) {
		super();
		this.flowLogRepository = flowLogRepository;
	}

	@Override
	protected BaseMapper<FlowLog, FlowLogData> getMapper() {
		return modelMapper;
	}

	@Override
	protected JpaRepository<FlowLog, BigDecimal> getRepository() {
		return flowLogRepository;
	}
	
    

	    
}
