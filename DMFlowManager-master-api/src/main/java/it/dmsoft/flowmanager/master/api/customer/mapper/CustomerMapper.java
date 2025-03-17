package it.dmsoft.flowmanager.master.api.customer.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import it.dmsoft.flowmanager.master.api.customer.model.CustomerData;
import it.dmsoft.flowmanager.master.be.entities.Customer;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface CustomerMapper {
	//CustomerMapper MAPPER = Mappers.getMapper(CustomerMapper.class);
	
	Customer convert(CustomerData customerData);

	CustomerData convert(Customer sourceCode);
}
