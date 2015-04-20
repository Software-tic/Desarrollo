package com.zyos.core.mail.io.mn.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zyos.core.connection.OracleBaseHibernateDAO;

/**
 * A data access object (DAO) providing persistence and search support for
 * EmailError entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.zyos.core.mail.io.mn.model.EmailError
 * @author MyEclipse Persistence Tools
 */

public class EmailErrorDAO extends OracleBaseHibernateDAO {
	private static final Logger log = LoggerFactory
			.getLogger(EmailErrorDAO.class);

	public void save(EmailError transientInstance) {
		log.debug("saving EmailError instance");
		try {
			getSession().save(transientInstance);
			
		} catch (RuntimeException re) {
			
			throw re;
		}
	}

	public void delete(EmailError persistentInstance) {
		log.debug("deleting EmailError instance");
		try {
			getSession().delete(persistentInstance);
			
		} catch (RuntimeException re) {
			
			throw re;
		}
	}

}