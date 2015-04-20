package com.zyos.core.lo.enterprise.model;

import java.util.List;

import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zyos.core.common.api.IZyosState;
import com.zyos.core.connection.OracleBaseHibernateDAO;

/**
 * A data access object (DAO) providing persistence and search support for
 * Settings entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.zyos.core.lo.enterprise.model.Settings
 * @author MyEclipse Persistence Tools
 */

public class SettingsDAO extends OracleBaseHibernateDAO {
	private static final Logger log = LoggerFactory
			.getLogger(SettingsDAO.class);

	public void save(Settings transientInstance) {
		log.debug("saving Settings instance");
		try {
			getSession().save(transientInstance);
			
		} catch (RuntimeException re) {
			
			throw re;
		}
	}

	public void delete(Settings persistentInstance) {
		log.debug("deleting Settings instance");
		try {
			getSession().delete(persistentInstance);
			
		} catch (RuntimeException re) {
			
			throw re;
		}
	}

	public List<Settings> loadSettingsByEnterprise() throws Exception {
		StringBuilder hql = new StringBuilder();
		Query qo = null;
		try {
			hql.append("select s from Settings s, Enterprise e where s.idEnterprise = e.id and e.state = :state");

			qo = getSession().createQuery(hql.toString());
			qo.setParameter("state", IZyosState.ACTIVE);
			return qo.list();
		} catch (Exception e) {
			throw e;
		} finally {
			hql = null;
			qo = null;
		}
	}

}