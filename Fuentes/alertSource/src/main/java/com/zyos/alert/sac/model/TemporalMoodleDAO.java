package com.zyos.alert.sac.model;

import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zyos.core.connection.OracleBaseHibernateDAO;

/**
 * A data access object (DAO) providing persistence and search support for
 * TemporalMoodle entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.zyos.alert.moodle.model.TemporalMoodle
 * @author MyEclipse Persistence Tools
 */
public class TemporalMoodleDAO extends OracleBaseHibernateDAO {
	private static final Logger log = LoggerFactory
			.getLogger(TemporalMoodleDAO.class);

	public void save(TemporalMoodle transientInstance) {
		log.debug("saving TemporalMoodle instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(TemporalMoodle persistentInstance) {
		log.debug("deleting TemporalMoodle instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public void setIdGroupListToTemporalMoodle() {
		log.debug("finding all MdlCourse instances");
		try {
			StringBuilder hql = new StringBuilder("update ");
			hql.append("zyos.TemporalMoodle ");
			hql.append("set ");
			hql.append("idGroup=(select g.ID_GRUPO  ");
			hql.append("from zyos.Grupo g,   zyos.Materias_Grupo mg ");
			hql.append("where ");
			hql.append("g.NOMBRE_GRUPO=zyos.TemporalMoodle.groupName  ");
			hql.append("and g.ID_GRUPO=mg.ID_GRUPO  and mg.ID_MATERIA=zyos.TemporalMoodle.idSubject and rownum <= 1) ");
			
			Query qo = getSession().createSQLQuery(hql.toString());
			qo.executeUpdate();		
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}

	}

	public void cleanTemporalMoodle() {
		try {
			StringBuilder hql = new StringBuilder("delete from TemporalMoodle ");
			Query qo = getSession().createSQLQuery(hql.toString());
			qo.executeUpdate();		
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

}