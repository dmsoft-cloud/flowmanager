package it.dmsoft.flowmanager.api.origin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import it.dmsoft.flowmanager.api.base.BaseMapper;
import it.dmsoft.flowmanager.api.base.DefaultBaseService;
import it.dmsoft.flowmanager.api.origin.mapper.OriginMapper;
import it.dmsoft.flowmanager.api.origin.model.OriginData;
import it.dmsoft.flowmanager.be.entities.Origin;
import it.dmsoft.flowmanager.be.repositories.OriginRepository;

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
