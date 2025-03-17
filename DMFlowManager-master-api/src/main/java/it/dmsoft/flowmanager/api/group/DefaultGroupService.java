package it.dmsoft.flowmanager.api.group;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import it.dmsoft.flowmanager.api.group.mapper.GroupMapper;
import it.dmsoft.flowmanager.be.entities.ConfigurationGroup;
import it.dmsoft.flowmanager.be.repositories.ConfigurationGroupRepository;
import it.dmsoft.flowmanager.common.model.GroupData;
import it.dmsoft.flowmanager.framework.api.base.BaseMapper;
import it.dmsoft.flowmanager.framework.api.base.DefaultBaseService;


@Service("groupService")
public class DefaultGroupService extends DefaultBaseService<ConfigurationGroup, GroupData, String> {

	private ConfigurationGroupRepository groupRepository;
    
    @Autowired
    private GroupMapper groupMapper;

    
    public DefaultGroupService(ConfigurationGroupRepository groupRepository) {
		super();
		this.groupRepository = groupRepository;
	}


	@Override
	protected BaseMapper<ConfigurationGroup, GroupData> getMapper() {
		return groupMapper;
	}


	@Override
	protected JpaRepository<ConfigurationGroup, String> getRepository() {
		return groupRepository;
	}
}
