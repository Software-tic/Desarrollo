package com.zyos.alert.executionsHistorical.model;

import static org.hibernate.criterion.Example.create;

import java.util.List;

import org.hibernate.LockMode;
import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zyos.core.connection.OracleBaseHibernateDAO;

/**
 * A data access object (DAO) providing persistence and search support for
 * ExecutionsHistorical entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.zyos.alert.absent.model.ExecutionsHistorical
 * @author MyEclipse Persistence Tools
 */
public class ExecutionHistoricalTypeDAO extends OracleBaseHibernateDAO {
	private static final Logger log = LoggerFactory
			.getLogger(ExecutionHistoricalTypeDAO.class);

	public void save(ExecutionsHistorical transientInstance) {
		log.debug("saving ExecutionsHistorical instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(ExecutionsHistorical persistentInstance) {
		log.debug("deleting ExecutionsHistorical instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public ExecutionsHistorical findById(java.lang.Long id) {
		log.debug("getting ExecutionsHistorical instance with id: " + id);
		try {
			ExecutionsHistorical instance = (ExecutionsHistorical) getSession().get(
					"com.zyos.alert.absent.model.executionsHistorical", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<ExecutionsHistorical> findByExample(ExecutionsHistorical instance) {
		log.debug("finding ExecutionsHistorical instance by example");
		try {
			List<ExecutionsHistorical> results = getSession()
					.createCriteria("com.zyos.alert.absent.model.executionsHistorical")
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
		log.debug("finding ExecutionsHistorical instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from ExecutionsHistorical as model where model."
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
		log.debug("finding all ExecutionsHistorical instances");
		try {
			String queryString = "from ExecutionsHistorical";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public ExecutionsHistorical merge(ExecutionsHistorical detachedInstance) {
		log.debug("merging ExecutionsHistorical instance");
		try {
			ExecutionsHistorical result = (ExecutionsHistorical) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(ExecutionsHistorical instance) {
		log.debug("attaching dirty ExecutionsHistorical instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(ExecutionsHistorical instance) {
		log.debug("attaching clean ExecutionsHistorical instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
	
		
	
}