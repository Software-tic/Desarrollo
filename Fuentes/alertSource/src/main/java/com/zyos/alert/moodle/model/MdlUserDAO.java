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
 * MdlUser entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.zyos.alert.moodle.model.MdlUser
 * @author MyEclipse Persistence Tools
 */
public class MdlUserDAO extends MySQLBaseHibernateDAO {
	private static final Logger log = LoggerFactory.getLogger(MdlUserDAO.class);

	public void save(MdlUser transientInstance) {
		log.debug("saving MdlUser instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(MdlUser persistentInstance) {
		log.debug("deleting MdlUser instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public MdlUser findById(java.lang.Long id) {
		log.debug("getting MdlUser instance with id: " + id);
		try {
			MdlUser instance = (MdlUser) getSession().get(
					"com.zyos.alert.moodle.model.MdlUser", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<MdlUser> findByExample(MdlUser instance) {
		log.debug("finding MdlUser instance by example");
		try {
			List<MdlUser> results = (List<MdlUser>) getSession()
					.createCriteria("com.zyos.alert.moodle.model.MdlUser")
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
		log.debug("finding MdlUser instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from MdlUser as model where model."
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
		log.debug("finding all MdlUser instances");
		try {
			String queryString = "from MdlUser";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public MdlUser merge(MdlUser detachedInstance) {
		log.debug("merging MdlUser instance");
		try {
			MdlUser result = (MdlUser) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(MdlUser instance) {
		log.debug("attaching dirty MdlUser instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(MdlUser instance) {
		log.debug("attaching clean MdlUser instance");
		try {
			getSession().buildLockRequest(LockOptions.NONE).lock(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}