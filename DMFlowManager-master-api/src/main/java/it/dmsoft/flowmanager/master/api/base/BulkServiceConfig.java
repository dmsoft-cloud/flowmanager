package it.dmsoft.flowmanager.master.api.base;

import java.util.function.BiFunction;
import java.util.function.Function;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import it.dmsoft.flowmanager.common.model.AgentData;
import it.dmsoft.flowmanager.common.model.FlowData;
import it.dmsoft.flowmanager.common.model.GroupData;
import it.dmsoft.flowmanager.common.model.InterfaceData;
import it.dmsoft.flowmanager.common.model.ModelData;
import it.dmsoft.flowmanager.common.model.OriginData;
import it.dmsoft.flowmanager.framework.bulk.BulkServiceInterface;
import it.dmsoft.flowmanager.framework.bulk.DefaultBulkService;
import it.dmsoft.flowmanager.master.api.agent.DefaultAgentService;
import it.dmsoft.flowmanager.master.api.flow.DefaultFlowlService;
import it.dmsoft.flowmanager.master.api.group.DefaultGroupService;
import it.dmsoft.flowmanager.master.api.interfaces.DefaultInterfaceService;
import it.dmsoft.flowmanager.master.api.model.DefaultModelService;
import it.dmsoft.flowmanager.master.api.origin.DefaultOriginService;

@Configuration
public class BulkServiceConfig {


    @Bean(name = "agentBulkService")
    public BulkServiceInterface<AgentData> agentBulkService(DefaultAgentService agentService) {
        var deleteFn = (Function<AgentData, Boolean>) dto -> {
            var id = agentService.extractIdFromModel(dto);
            return agentService.delete(id);
        };

        var activationFn = (BiFunction<AgentData, Boolean, Boolean>) (dto, active) -> {
            var id = agentService.extractIdFromModel(dto);
            return agentService.setActive(id, active);
        };
        return new DefaultBulkService<>(deleteFn, activationFn);
    }

    @Bean(name = "interfaceBulkService")
    public BulkServiceInterface<InterfaceData> interfaceBulkService(DefaultInterfaceService interfaceService) {
        var deleteFn = (Function<InterfaceData, Boolean>) dto -> {
            var id = interfaceService.extractIdFromModel(dto);
            return interfaceService.delete(id);
        };

        var activationFn = (BiFunction<InterfaceData, Boolean, Boolean>) (dto, active) -> {
            var id = interfaceService.extractIdFromModel(dto);
            return interfaceService.setActive(id, active);
        };
        return new DefaultBulkService<>(deleteFn, activationFn);
    }

    @Bean(name = "modelBulkService")
    public BulkServiceInterface<ModelData> modelBulkService(DefaultModelService modelService) {
        var deleteFn = (Function<ModelData, Boolean>) dto -> {
            var id = modelService.extractIdFromModel(dto);
            return modelService.delete(id);
        };

        var activationFn = (BiFunction<ModelData, Boolean, Boolean>) (dto, active) -> {
            var id = modelService.extractIdFromModel(dto);
            return modelService.setActive(id, active);
        };
        return new DefaultBulkService<>(deleteFn, activationFn);
    }

    @Bean(name = "originBulkService")
    public BulkServiceInterface<OriginData> originBulkService(DefaultOriginService originService) {
        var deleteFn = (Function<OriginData, Boolean>) dto -> {
            var id = originService.extractIdFromModel(dto);
            return originService.delete(id);
        };

        var activationFn = (BiFunction<OriginData, Boolean, Boolean>) (dto, active) -> {
            var id = originService.extractIdFromModel(dto);
            return originService.setActive(id, active);
        };
        return new DefaultBulkService<>(deleteFn, activationFn);
    }
    
    @Bean(name = "groupBulkService")
    public BulkServiceInterface<GroupData> groupBulkService(DefaultGroupService groupService) {
        var deleteFn = (Function<GroupData, Boolean>) dto -> {
            var id = groupService.extractIdFromModel(dto);
            return groupService.delete(id);
        };

        var activationFn = (BiFunction<GroupData, Boolean, Boolean>) (dto, active) -> {
            var id = groupService.extractIdFromModel(dto);
            return groupService.setActive(id, active);
        };
        return new DefaultBulkService<>(deleteFn, activationFn);
    }
    
    @Bean(name = "flowBulkService")
    public BulkServiceInterface<FlowData> flowBulkService(DefaultFlowlService flowService) {
        var deleteFn = (Function<FlowData, Boolean>) dto -> {
            var id = flowService.extractIdFromModel(dto);
            return flowService.delete(id);
        };

        var activationFn = (BiFunction<FlowData, Boolean, Boolean>) (dto, active) -> {
            var id = flowService.extractIdFromModel(dto);
            return flowService.setActive(id, active);
        };
        return new DefaultBulkService<>(deleteFn, activationFn);
    }

}
