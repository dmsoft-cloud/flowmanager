package it.dmsoft.flowmanager.master.api.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import it.dmsoft.flowmanager.be.entities.User;
import it.dmsoft.flowmanager.be.entities.UserColumnsConfig;
import it.dmsoft.flowmanager.be.keys.UserId;
import it.dmsoft.flowmanager.be.keys.UserPageId;
import it.dmsoft.flowmanager.common.model.UserColumnsConfigData;
import it.dmsoft.flowmanager.common.model.UserData;
import it.dmsoft.flowmanager.framework.api.base.BaseMapper;
import it.dmsoft.flowmanager.framework.api.base.DefaultBaseService;
import it.dmsoft.flowmanager.master.api.user.mapper.UserColumnsConfigMapper;
import it.dmsoft.flowmanager.master.api.user.mapper.UserMapper;
import it.dmsoft.flowmanager.master.repositories.UserColumnsConfigRepository;
import it.dmsoft.flowmanager.master.repositories.UserRepository;

@Service("userColumnsConfigService")
public class UserColumnsConfigService extends DefaultBaseService<UserColumnsConfig, UserColumnsConfigData, UserPageId> {

	    private UserColumnsConfigRepository userColumnsConfigRepository;

	    @Autowired
	    private UserColumnsConfigMapper userColumnsConfigMapper;


	    public UserColumnsConfigService(UserColumnsConfigRepository userColumnsConfigRepository) {
			super();
			this.userColumnsConfigRepository = userColumnsConfigRepository;
		}

		@Override
		protected BaseMapper<UserColumnsConfig, UserColumnsConfigData> getMapper() {
			return userColumnsConfigMapper;
		}

		@Override
		protected JpaRepository<UserColumnsConfig, UserPageId> getRepository() {
			return userColumnsConfigRepository;
		}

		@Override
		public boolean setActive(UserPageId id, boolean activate) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public UserPageId extractIdFromModel(UserColumnsConfigData modelData) {
			// TODO Auto-generated method stub
			return null;
		}



}
