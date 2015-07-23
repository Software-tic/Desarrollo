package com.zyos.alert.query.model;

import java.util.List;

import org.hibernate.LockOptions;
import org.hibernate.Query;

import static org.hibernate.criterion.Example.create;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zyos.core.connection.OracleBaseHibernateDAO;

/**
 * A data access object (DAO) providing persistence and search support for
 * SchoolDegree entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.zyos.alert.query.model.SchoolDegree
 * @author MyEclipse Persistence Tools
 */
public class SchoolDegreeDAO extends OracleBaseHibernateDAO {
	private static final Logger log = LoggerFactory
			.getLogger(SchoolDegreeDAO.class);
	// property constants
	public static final String DATECREATION = "datecreation";
	public static final String USERCREATION = "usercreation";
	public static final String DATECHANGE = "datechange";
	public static final String USERCHANGE = "userchange";
	public static final String STATE = "state";

	public void save(SchoolDegree transientInstance) {
		log.debug("saving SchoolDegree instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(SchoolDegree persistentInstance) {
		log.debug("deleting SchoolDegree instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public SchoolDegree findById(java.lang.Long id) {
		log.debug("getting SchoolDegree instance with id: " + id);
		try {
			SchoolDegree instance = (SchoolDegree) getSession().get(
					"com.zyos.alert.query.model.SchoolDegree", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<SchoolDegree> findByExample(SchoolDegree instance) {
		log.debug("finding SchoolDegree instance by example");
		try {
			List<SchoolDegree> results = (List<SchoolDegree>) getSession()
					.createCriteria("com.zyos.alert.query.model.SchoolDegree")
					.add(create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding SchoolDegree instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from SchoolDegree as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<SchoolDegree> findByDatecreation(Object datecreation) {
		return findByProperty(DATECREATION, datecreation);
	}

	public List<SchoolDegree> findByUsercreation(Object usercreation) {
		return findByProperty(USERCREATION, usercreation);
	}

	public List<SchoolDegree> findByDatechange(Object datechange) {
		return findByProperty(DATECHANGE, datechange);
	}

	public List<SchoolDegree> findByUserchange(Object userchange) {
		return findByProperty(USERCHANGE, userchange);
	}

	public List<SchoolDegree> findByState(Object state) {
		return findByProperty(STATE, state);
	}

	public List findAll() {
		log.debug("finding all SchoolDegree instances");
		try {
			String queryString = "from SchoolDegree";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public SchoolDegree merge(SchoolDegree detachedInstance) {
		log.debug("merging SchoolDegree instance");
		try {
			SchoolDegree result = (SchoolDegree) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(SchoolDegree instance) {
		log.debug("attaching dirty SchoolDegree instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(SchoolDegree instance) {
		log.debug("attaching clean SchoolDegree instance");
		try {
			getSession().buildLockRequest(LockOptions.NONE).lock(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}