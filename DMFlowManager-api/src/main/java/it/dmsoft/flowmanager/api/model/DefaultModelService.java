package it.dmsoft.flowmanager.api.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import it.dmsoft.flowmanager.api.base.BaseMapper;
import it.dmsoft.flowmanager.api.base.DefaultBaseService;
import it.dmsoft.flowmanager.api.model.mapper.ModelMapper;
import it.dmsoft.flowmanager.api.model.model.ModelData;
import it.dmsoft.flowmanager.be.entities.Model;
import it.dmsoft.flowmanager.be.repositories.ModelRepository;

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
