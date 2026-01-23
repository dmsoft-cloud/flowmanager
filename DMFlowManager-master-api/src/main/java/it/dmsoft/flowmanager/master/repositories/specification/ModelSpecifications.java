package it.dmsoft.flowmanager.master.repositories.specification;



import org.springframework.data.jpa.domain.Specification;

import it.dmsoft.flowmanager.be.entities.Model;
import it.dmsoft.flowmanager.be.filters.ModelFilterDTO;
import jakarta.persistence.criteria.Predicate;

public class ModelSpecifications {

	public static Specification<Model> withFilters(ModelFilterDTO filter) {
        return (root, query, cb) -> {
            Predicate predicate = cb.conjunction();

            if (filter.getId() != null && !filter.getId().isEmpty()) {
            	predicate = cb.and(predicate,
                        cb.like(root.get("id"), "%" + filter.getId() + "%"));
            }
            if (filter.getDirection() != null && !filter.getDirection().isEmpty()) {
                predicate = cb.and(predicate, cb.equal(root.get("direction"), filter.getDirection()));
            }
            if (filter.getType() != null && !filter.getType().isEmpty()) {
                predicate = cb.and(predicate, cb.equal(root.get("type"), filter.getType()));
            }
            if (filter.getEnabled() != null && !filter.getEnabled().trim().isEmpty()) {
            		//YesNo enabledEnum = YesNo.getYesNo(filter.getEnabled().trim());
            		predicate = cb.and(predicate, cb.equal(root.get("enabled"), filter.getEnabled()));
            }

            return predicate;
        };
    }
}
