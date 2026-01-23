package it.dmsoft.flowmanager.master.app.controller.interfaces;

import java.util.function.BiFunction;
import java.util.function.Function;

import org.springframework.stereotype.Service;

import it.dmsoft.flowmanager.common.model.InterfaceData;
import it.dmsoft.flowmanager.framework.bulk.DefaultBulkService;
import it.dmsoft.flowmanager.master.api.interfaces.DefaultInterfaceService;

@Service("interfaceBulkService2")
public class InterfaceBulkService extends DefaultBulkService<InterfaceData> {

	public InterfaceBulkService(DefaultInterfaceService interfaceService) {
        super(
            (Function<InterfaceData, Boolean>) dto -> {
                var id = interfaceService.extractIdFromModel(dto);
                return interfaceService.delete(id);
            },
            (BiFunction<InterfaceData, Boolean, Boolean>) (dto, active) -> {
                var id = interfaceService.extractIdFromModel(dto);
                return interfaceService.setActive(id, active);
            }
        );
    }
}
