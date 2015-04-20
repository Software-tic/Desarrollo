package com.zyos.alert.sac.model;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zyos.alert.studentReport.model.Degree;
import com.zyos.core.connection.OracleBaseHibernateDAO;
import com.zyos.core.lo.user.model.ZyosUser;

/**
 * A data access object (DAO) providing persistence and search support for
 * Materias entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.zyos.alert.sac.model.Materias
 * @author MyEclipse Persistence Tools
 */
public class MateriasDAO extends OracleBaseHibernateDAO {
	private static final Logger log = LoggerFactory
			.getLogger(MateriasDAO.class);

	public void save(Materias transientInstance) {
		log.debug("saving Materias instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Materias persistentInstance) {
		log.debug("deleting Materias instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

}