package it.dmsoft.flowmanager.api.interfaces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import it.dmsoft.flowmanager.api.interfaces.mapper.InterfaceMapper;
import it.dmsoft.flowmanager.be.entities.Interface;
import it.dmsoft.flowmanager.be.repositories.InterfaceRepository;
import it.dmsoft.flowmanager.common.model.InterfaceData;
import it.dmsoft.flowmanager.framework.api.base.BaseMapper;
import it.dmsoft.flowmanager.framework.api.base.DefaultBaseService;

@Service("interfaceService")
public class DefaultInterfaceService extends DefaultBaseService<Interface, InterfaceData, String> {

	    private InterfaceRepository interfaceRepository;
	    
	    @Autowired
	    private InterfaceMapper interfaceMapper;

	    
	    public DefaultInterfaceService(InterfaceRepository interfaceRepository) {
			super();
			this.interfaceRepository = interfaceRepository;
		}


		@Override
		protected BaseMapper<Interface, InterfaceData> getMapper() {
			return interfaceMapper;
		}


		@Override
		protected JpaRepository<Interface, String> getRepository() {
			return interfaceRepository;
		}
	    
}
