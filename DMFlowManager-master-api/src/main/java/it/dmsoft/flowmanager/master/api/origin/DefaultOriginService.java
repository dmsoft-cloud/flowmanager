package it.dmsoft.flowmanager.master.api.origin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import it.dmsoft.flowmanager.common.model.OriginData;
import it.dmsoft.flowmanager.framework.api.base.BaseMapper;
import it.dmsoft.flowmanager.framework.api.base.DefaultBaseService;
import it.dmsoft.flowmanager.master.api.origin.mapper.OriginMapper;
import it.dmsoft.flowmanager.master.repositories.OriginRepository;
import it.dmsoft.flowmanager.be.entities.Origin;

@Service("originService")
public class DefaultOriginService extends DefaultBaseService<Origin, OriginData, String> {

	    private OriginRepository originRepository;
	    
	    @Autowired
	    private OriginMapper originMapper;

	    
	    public DefaultOriginService(OriginRepository originRepository) {
			super();
			this.originRepository = originRepository;
		}


		@Override
		protected BaseMapper<Origin, OriginData> getMapper() {
			return originMapper;
		}


		@Override
		protected JpaRepository<Origin, String> getRepository() {
			return originRepository;
		}
	    
}
