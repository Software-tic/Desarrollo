package com.zyos.alert.sac.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zyos.core.connection.OracleBaseHibernateDAO;

/**
 * A data access object (DAO) providing persistence and search support for
 * MateriasEstudiantess entities. Transaction control of the save(), update()
 * and delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.zyos.alert.sac.model.MateriasEstudiantes
 * @author MyEclipse Persistence Tools
 */
public class MateriasEstudiantesDAO extends OracleBaseHibernateDAO {
	private static final Logger log = LoggerFactory
			.getLogger(MateriasEstudiantesDAO.class);

	public void save(MateriasEstudiantes transientInstance) {
		log.debug("saving MateriasEstudiantess instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(MateriasEstudiantes persistentInstance) {
		log.debug("deleting MateriasEstudiantess instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

}