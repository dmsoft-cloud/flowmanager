package it.dmsoft.flowmanager.master.api.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import it.dmsoft.flowmanager.be.entities.User;
import it.dmsoft.flowmanager.be.keys.UserId;
import it.dmsoft.flowmanager.common.model.UserData;
import it.dmsoft.flowmanager.framework.api.base.BaseMapper;
import it.dmsoft.flowmanager.framework.api.base.DefaultBaseService;
import it.dmsoft.flowmanager.master.api.user.mapper.UserMapper;
import it.dmsoft.flowmanager.master.repositories.UserRepository;

@Service("userService")
public class DefaultUserService extends DefaultBaseService<User, UserData, UserId> {

	    private UserRepository userRepository;

	    @Autowired
	    private UserMapper userMapper;


	    public DefaultUserService(UserRepository userRepository) {
			super();
			this.userRepository = userRepository;
		}

		@Override
		protected BaseMapper<User, UserData> getMapper() {
			return userMapper;
		}

		@Override
		protected JpaRepository<User, UserId> getRepository() {
			return userRepository;
		}

		@Override
		public boolean setActive(UserId id, boolean activate) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public UserId extractIdFromModel(UserData modelData) {
			// TODO Auto-generated method stub
			return null;
		}



}
