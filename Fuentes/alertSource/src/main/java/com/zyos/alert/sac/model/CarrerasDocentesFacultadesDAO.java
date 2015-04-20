package com.zyos.alert.sac.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zyos.core.connection.OracleBaseHibernateDAO;

/**
 * A data access object (DAO) providing persistence and search support for
 * CarrerasDocentesFacultades entities. Transaction control of the save(), update()
 * and delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.zyos.alert.sac.model.DocenteMateriaGrupo
 * @author MyEclipse Persistence Tools
 */
public class CarrerasDocentesFacultadesDAO extends OracleBaseHibernateDAO {
	private static final Logger log = LoggerFactory
			.getLogger(CarrerasDocentesFacultadesDAO.class);

	public void save(DocenteMateriaGrupo transientInstance) {
		log.debug("saving CarrerasDocentesFacultades instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(DocenteMateriaGrupo persistentInstance) {
		log.debug("deleting CarrerasDocentesFacultades instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

}