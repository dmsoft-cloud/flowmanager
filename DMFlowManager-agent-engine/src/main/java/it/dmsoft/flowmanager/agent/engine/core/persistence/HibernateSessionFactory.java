package it.dmsoft.flowmanager.agent.engine.core.persistence;

import java.util.Arrays;
import java.util.List;

import org.hibernate.SessionFactory;

import it.dmsoft.flowmanager.agent.engine.core.operations.params.GenericConnectionParams;
import it.dmsoft.flowmanager.agent.engine.core.utils.HibernateUtils;
import it.dmsoft.flowmanager.agent.engine.core.utils.StringUtils;
import it.dmsoft.flowmanager.be.entities.Email;
import it.dmsoft.flowmanager.be.entities.Recipient;
import it.dmsoft.flowmanager.be.entities.ScheduleDate;
import it.dmsoft.flowmanager.common.engine.FlowConfig;

public class HibernateSessionFactory {

	private static SessionFactory instance;
	
	public static void init(FlowConfig flowConfig) {
		if (StringUtils.isNullOrEmpty(flowConfig.getDatasourceDriverClassName())) {
			return;
		}

		HibernateUtils hu = HibernateUtils.getHibernateUtils(flowConfig.getDatasourceDriverClassName());
		List<Class<?>> entityClasses = Arrays.asList(Email.class, Recipient.class, ScheduleDate.class);
		instance = hu.getSessionFactory(entityClasses, getGenericConnectionParams(flowConfig), null);
	}
	
	public static SessionFactory get() {
		if(instance == null) {
			throw new RuntimeException("Session Factory must be initiliazed");
		}
		
		return instance;
	}
	
	private static GenericConnectionParams getGenericConnectionParams(FlowConfig flowConfig) {
		GenericConnectionParams gcp = new GenericConnectionParams();
		
		gcp.setJdbcCustomString(flowConfig.getDatasourceUrl());
		gcp.setUser(flowConfig.getDatasourceUsername());
		gcp.setPassword(flowConfig.getDatasourcePassword());
		
		return gcp;
	}
}
