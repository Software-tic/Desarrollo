package com.zyos.core.connection;

import org.hibernate.Session;


/**
 * Data access interface for domain model
 * @author MyEclipse Persistence Tools
 */
public interface IMySQLBaseHibernateDAO {
	public Session getSession();
}