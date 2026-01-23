package it.dmsoft.flowmanager.master.api.model;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import it.dmsoft.flowmanager.be.entities.Model;
import it.dmsoft.flowmanager.be.filters.FlowFilterDTO;
import it.dmsoft.flowmanager.be.filters.ModelFilterDTO;
import it.dmsoft.flowmanager.common.domain.Domains.YesNo;
import it.dmsoft.flowmanager.common.model.FlowData;
import it.dmsoft.flowmanager.common.model.ModelData;
import it.dmsoft.flowmanager.framework.api.base.BaseMapper;
import it.dmsoft.flowmanager.framework.api.base.DefaultBaseService;
import it.dmsoft.flowmanager.master.api.model.mapper.ModelMapper;
import it.dmsoft.flowmanager.master.repositories.ModelRepository;
import it.dmsoft.flowmanager.master.repositories.specification.FlowSpecifications;
import it.dmsoft.flowmanager.master.repositories.specification.ModelSpecifications;

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
		
	    public List<ModelData> getAllFiltered(ModelFilterDTO filter) {
	        return modelRepository.findAll(ModelSpecifications.withFilters(filter)).stream()
	                .map(modelMapper::convertEntity)
	                .collect(Collectors.toList());
	    }


		@Override
		public boolean setActive(String id, boolean activate) {
			YesNo value = activate ? YesNo.YES : YesNo.NO;
			return modelRepository.findById(id)
			        .map(model -> {
			            model.setEnabled(value);
			            modelRepository.save(model);
			            return true;
			        })
			        .orElse(false);
		}

		@Override
		public String extractIdFromModel(ModelData modelData) {
			// TODO Auto-generated method stub
			return modelData.getId();
		}

}
