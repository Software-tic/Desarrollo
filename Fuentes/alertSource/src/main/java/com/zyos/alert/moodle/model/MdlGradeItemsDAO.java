package com.zyos.alert.moodle.model;

import static org.hibernate.criterion.Example.create;

import java.util.List;

import org.hibernate.LockOptions;
import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zyos.core.connection.MySQLBaseHibernateDAO;

/**
 * A data access object (DAO) providing persistence and search support for
 * MdlGradeItems entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.zyos.alert.moodle.model.MdlGradeItems
 * @author MyEclipse Persistence Tools
 */
public class MdlGradeItemsDAO extends MySQLBaseHibernateDAO {
	private static final Logger log = LoggerFactory
			.getLogger(MdlGradeItemsDAO.class);

	public void save(MdlGradeItems transientInstance) {
		log.debug("saving MdlGradeItems instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(MdlGradeItems persistentInstance) {
		log.debug("deleting MdlGradeItems instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public MdlGradeItems findById(java.lang.Long id) {
		log.debug("getting MdlGradeItems instance with id: " + id);
		try {
			MdlGradeItems instance = (MdlGradeItems) getSession().get(
					"com.zyos.alert.moodle.model.MdlGradeItems", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<MdlGradeItems> findByExample(MdlGradeItems instance) {
		log.debug("finding MdlGradeItems instance by example");
		try {
			List<MdlGradeItems> results = getSession()
					.createCriteria("com.zyos.alert.moodle.model.MdlGradeItems")
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
		log.debug("finding MdlGradeItems instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from MdlGradeItems as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findAll() {
		log.debug("finding all MdlGradeItems instances");
		try {
			String queryString = "from MdlGradeItems";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public MdlGradeItems merge(MdlGradeItems detachedInstance) {
		log.debug("merging MdlGradeItems instance");
		try {
			MdlGradeItems result = (MdlGradeItems) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(MdlGradeItems instance) {
		log.debug("attaching dirty MdlGradeItems instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(MdlGradeItems instance) {
		log.debug("attaching clean MdlGradeItems instance");
		try {
			getSession().buildLockRequest(LockOptions.NONE).lock(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}