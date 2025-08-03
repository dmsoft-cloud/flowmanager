package it.dmsoft.flowmanager.common.model;

import it.dmsoft.flowmanager.common.domain.Domains.Direction;

public class FlowDataWithDirection extends FlowData {

    private Direction direction;

    // Costruttore che copia tutti i campi da FlowData
    public FlowDataWithDirection(FlowData flowData, Direction direction) {
        this.setId(flowData.getId());
        this.setDescription(flowData.getDescription());
        this.setGroupId(flowData.getGroupId());
        this.setNote(flowData.getNote());
        this.setEnabled(flowData.getEnabled());
        this.setModel(flowData.getModel());
        this.setOrigin(flowData.getOrigin());
        this.setInterfaceId(flowData.getInterfaceId());
        this.setNotificationFlow(flowData.getNotificationFlow());
        this.setNotificationOk(flowData.getNotificationOk());
        this.setNotificationKo(flowData.getNotificationKo());
        this.setIntegrityFileName(flowData.getIntegrityFileName());
        this.setDbTable(flowData.getDbTable());
        this.setFolder(flowData.getFolder());
        this.setFile(flowData.getFile());
        this.setRemoteFolder(flowData.getRemoteFolder());
        this.setRemoteFile(flowData.getRemoteFile());
        this.setLengthFixed(flowData.getLengthFixed());
        
        this.direction = direction;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
	
}
