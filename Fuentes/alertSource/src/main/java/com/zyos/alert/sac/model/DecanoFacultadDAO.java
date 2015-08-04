package com.zyos.alert.sac.model;

import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zyos.alert.facultyCoordinator.model.FacultyCoordinator;
import com.zyos.alert.query.model.Teacher;
import com.zyos.core.common.api.IZyosState;
import com.zyos.core.connection.OracleBaseHibernateDAO;

/**
 * A data access object (DAO) providing persistence and search support for
 * DocenteMateriaGrupos entities. Transaction control of the save(), update()
 * and delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.zyos.alert.sac.model.DocenteMateriaGrupo
 * @author MyEclipse Persistence Tools
 */
public class DecanoFacultadDAO extends OracleBaseHibernateDAO {
	private static final Logger log = LoggerFactory
			.getLogger(DecanoFacultadDAO.class);

	public void save(DocenteMateriaGrupo transientInstance) {
		log.debug("saving DocenteMateriaGrupos instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(DocenteMateriaGrupo persistentInstance) {
		log.debug("deleting DocenteMateriaGrupos instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

}