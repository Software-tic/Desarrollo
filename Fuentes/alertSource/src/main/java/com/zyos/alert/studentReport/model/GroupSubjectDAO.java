package com.zyos.alert.studentReport.model;

import static org.hibernate.criterion.Example.create;

import java.util.List;

import org.hibernate.LockMode;
import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zyos.core.connection.OracleBaseHibernateDAO;

/**
 * @author JRodriguez
 * */
public class GroupSubjectDAO extends OracleBaseHibernateDAO {
	private static final Logger log = LoggerFactory
			.getLogger(GroupSubjectDAO.class);

	public void save(GroupSubject transientInstance) {
		log.debug("saving GroupSubject instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(GroupSubject persistentInstance) {
		log.debug("deleting GroupSubject instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public GroupSubject findById(java.lang.Long id) {
		log.debug("getting GroupSubject instance with id: " + id);
		try {
			GroupSubject instance = (GroupSubject) getSession().get(
					"com.zyos.alert.studentReport.model.GroupSubject", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<GroupSubject> findByExample(GroupSubject instance) {
		log.debug("finding GroupSubject instance by example");
		try {
			List<GroupSubject> results = (List<GroupSubject>) getSession()
					.createCriteria(
							"com.zyos.alert.studentReport.model.GroupSubject")
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
		log.debug("finding GroupSubject instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from GroupSubject as model where model."
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
		log.debug("finding all GroupSubject instances");
		try {
			String queryString = "from GroupSubject";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public GroupSubject merge(GroupSubject detachedInstance) {
		log.debug("merging GroupSubject instance");
		try {
			GroupSubject result = (GroupSubject) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(GroupSubject instance) {
		log.debug("attaching dirty GroupSubject instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(GroupSubject instance) {
		log.debug("attaching clean GroupSubject instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}
