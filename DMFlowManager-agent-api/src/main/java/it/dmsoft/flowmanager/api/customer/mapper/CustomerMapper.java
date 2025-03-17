package it.dmsoft.flowmanager.api.customer.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import it.dmsoft.flowmanager.api.customer.model.CustomerData;
import it.dmsoft.flowmanager.be.entities.Customer;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface CustomerMapper {
	//CustomerMapper MAPPER = Mappers.getMapper(CustomerMapper.class);
	
	Customer convert(CustomerData customerData);

	CustomerData convert(Customer sourceCode);
}
