package it.dmsoft.flowmanager.master.api.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import it.dmsoft.flowmanager.common.model.ModelData;
import it.dmsoft.flowmanager.framework.api.base.BaseMapper;
import it.dmsoft.flowmanager.framework.api.base.DefaultBaseService;
import it.dmsoft.flowmanager.master.api.model.mapper.ModelMapper;
import it.dmsoft.flowmanager.master.repositories.ModelRepository;
import it.dmsoft.flowmanager.be.entities.Model;

@Service("modelService")
public class DefaultModelService extends DefaultBaseService<Model, ModelData, String> {

	    private ModelRepository modelRepository;
	    
	    @Autowired
	    private ModelMapper modelMapper;

	    
	    public DefaultModelService(ModelRepository modelRepository) {
			super();
			this.modelRepository = modelRepository;
		}

		@Override
		protected BaseMapper<Model, ModelData> getMapper() {
			return modelMapper;
		}

		@Override
		protected JpaRepository<Model, String> getRepository() {
			return modelRepository;
		}
	    
}
