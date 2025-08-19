package it.dmsoft.flowmanager.master.api.flow;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import it.dmsoft.flowmanager.be.entities.Flow;
import it.dmsoft.flowmanager.be.entities.Model;
import it.dmsoft.flowmanager.be.filters.FlowFilterDTO;
import it.dmsoft.flowmanager.common.domain.Domains.Direction;
import it.dmsoft.flowmanager.common.model.FlowData;
import it.dmsoft.flowmanager.common.model.FlowDataWithDirection;
import it.dmsoft.flowmanager.framework.api.base.BaseMapper;
import it.dmsoft.flowmanager.framework.api.base.DefaultBaseService;
import it.dmsoft.flowmanager.master.api.flow.mapper.FlowMapper;
import it.dmsoft.flowmanager.master.repositories.FlowRepository;
import it.dmsoft.flowmanager.master.repositories.ModelRepository;
import it.dmsoft.flowmanager.master.repositories.specification.FlowSpecifications;


@Service("flowService")
public class DefaultFlowlService extends DefaultBaseService<Flow, FlowData, String> {

    private FlowRepository flowRepository;
    //
    private ModelRepository modelRepository;
    
    @Autowired
    private FlowMapper modelMapper;

    
    public DefaultFlowlService(FlowRepository flowRepository, ModelRepository modelRepository) {
		super();
		this.flowRepository = flowRepository;
		//
		this.modelRepository = modelRepository;
	}

	@Override
	protected BaseMapper<Flow, FlowData> getMapper() {
		return modelMapper;
	}

	@Override
	protected JpaRepository<Flow, String> getRepository() {
		return flowRepository;
	}
	
    public List<FlowDataWithDirection> getAllFlowsWithDirection() {
        List<FlowData> flows = getAll(); // Usa il metodo esistente
        
        return flows.stream()
            .map(flow -> {
                Direction direction = null;
                if (flow.getModel() != null) {
                    Model model = modelRepository.findById(flow.getModel()).orElse(null);
                    if (model != null) {
                        direction = model.getDirection();
                    }
                }
                return new FlowDataWithDirection(flow, direction);
            })
            .collect(Collectors.toList());
    }
    
    public List<FlowData> getFilteredFlows(FlowFilterDTO filter) {
        return flowRepository.findAll(FlowSpecifications.withFilters(filter)).stream()
                .map(modelMapper::convertEntity)
                .collect(Collectors.toList());
    }
    
    
    public List<FlowDataWithDirection> getFilteredFlowsWithDirection(FlowFilterDTO filter) {
        return getFilteredFlows(filter).stream()
                .map(flow -> {
                    Direction direction = null;
                    if (flow.getModel() != null) {
                        Model model = modelRepository.findById(flow.getModel()).orElse(null);
                        if (model != null) {
                            direction = model.getDirection();
                        }
                    }
                    return new FlowDataWithDirection(flow, direction);
                })
                .collect(Collectors.toList());
    }
    

	    
}
