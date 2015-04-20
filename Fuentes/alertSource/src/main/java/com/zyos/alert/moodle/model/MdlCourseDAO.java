package com.zyos.alert.moodle.model;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zyos.core.connection.MySQLBaseHibernateDAO;

/**
 * A data access object (DAO) providing persistence and search support for
 * MdlCourse entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.zyos.alert.moodle.model.MdlCourse
 * @author MyEclipse Persistence Tools
 */
public class MdlCourseDAO extends MySQLBaseHibernateDAO{
	private static final Logger log = LoggerFactory
			.getLogger(MdlCourseDAO.class);

	public void save(MdlCourse transientInstance) {
		log.debug("saving MdlCourse instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(MdlCourse persistentInstance) {
		log.debug("deleting MdlCourse instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	public List<MdlCourse> loadMoodleDatabaseCourse() {
		log.debug("finding all MdlCourse instances");
		try {
			String hql = "select distinct id as id, shortname as shortname from MdlCourse where visible is true";
			Query qo = getSession().createQuery(hql.toString()).setResultTransformer(Transformers.aliasToBean(MdlCourse.class)); 
			return qo.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	/**
	 * ogarzonm
	 * @return
	 */
	public boolean validateMoodleConnection() {
		try {
			String hql = "select 1 ";
			Query qo = getSession().createSQLQuery(hql.toString());
			Object o = qo.uniqueResult();
			if(o != null)
				return true;
			return false;
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			return false;
		}
	}
}