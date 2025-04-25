package it.dmsoft.flowmanager.be.repositories.specification;

import org.springframework.data.jpa.domain.Specification;

import it.dmsoft.flowmanager.be.common.BaseEntity.Direction;
import it.dmsoft.flowmanager.be.common.BaseEntity.YesNo;

import it.dmsoft.flowmanager.be.entities.Flow;
import it.dmsoft.flowmanager.be.filters.FlowFilterDTO;
import jakarta.persistence.criteria.Predicate;

public class FlowSpecifications {

	public static Specification<Flow> withFilters(FlowFilterDTO filter) {
        return (root, query, cb) -> {
            Predicate predicate = cb.conjunction();

            if (filter.getId() != null && !filter.getId().isEmpty()) {
                predicate = cb.and(predicate, cb.equal(root.get("id"), filter.getId()));
            }
            if (filter.getModel() != null && !filter.getModel().isEmpty()) {
                predicate = cb.and(predicate, cb.equal(root.get("model"), filter.getModel()));
            }
            if (filter.getOrigin() != null && !filter.getOrigin().isEmpty()) {
                predicate = cb.and(predicate, cb.equal(root.get("origin"), filter.getOrigin()));
            }
            if (filter.getInterfaceId() != null && !filter.getInterfaceId().isEmpty()) {
                predicate = cb.and(predicate, cb.equal(root.get("interfaceId"), filter.getInterfaceId()));
            }
            /*
            if (filter.getEnabled() != null && !filter.getEnabled().trim().isEmpty()) {            	
            	Direction directionEnum = Direction.getDirection(filter.getDirection().trim());
        		predicate = cb.and(predicate, cb.equal(root.get("direction"), filter.getDirection()));
            }*/
            if (filter.getEnabled() != null && !filter.getEnabled().trim().isEmpty()) {            	
            		YesNo enabledEnum = YesNo.getYesNo(filter.getEnabled().trim());
            		predicate = cb.and(predicate, cb.equal(root.get("enabled"), filter.getEnabled()));
            }

            return predicate;
        };
    }
}
