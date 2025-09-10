package it.dmsoft.flowmanager.master.api.log;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import it.dmsoft.flowmanager.be.entities.FlowLog;
import it.dmsoft.flowmanager.common.model.FlowLogData;
import it.dmsoft.flowmanager.framework.api.base.BaseMapper;
import it.dmsoft.flowmanager.framework.api.base.DefaultBaseService;
import it.dmsoft.flowmanager.master.api.log.mapper.FlowLogMapper;
import it.dmsoft.flowmanager.master.repositories.FlowLogRepository;

@Service("flowLogService")
public class DefaultFlowLogService extends DefaultBaseService<FlowLog, FlowLogData, BigDecimal> {

	    private FlowLogRepository flowLogRepository;
	    
	    @Autowired
	    private FlowLogMapper flowLogMapper;

	    
	    public DefaultFlowLogService(FlowLogRepository flowLogRepository) {
			super();
			this.flowLogRepository = flowLogRepository;
		}


		@Override
		protected BaseMapper<FlowLog, FlowLogData> getMapper() {
			return flowLogMapper;
		}


		@Override
		protected JpaRepository<FlowLog, BigDecimal> getRepository() {
			return flowLogRepository;
		}
	    
}
