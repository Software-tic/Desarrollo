package com.zyos.alert.query.model;

import java.util.List;

import org.hibernate.LockOptions;
import org.hibernate.Query;

import static org.hibernate.criterion.Example.create;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zyos.core.common.util.ManageDate;
import com.zyos.core.connection.OracleBaseHibernateDAO;

/**
 * A data access object (DAO) providing persistence and search support for Corte
 * entities. Transaction control of the save(), update() and delete() operations
 * can directly support Spring container-managed transactions or they can be
 * augmented to handle user-managed Spring transactions. Each of these methods
 * provides additional information for how to configure it for the desired type
 * of transaction control.
 * 
 * @see com.zyos.alert.query.model.Corte
 * @author MyEclipse Persistence Tools
 */
public class CorteDAO extends OracleBaseHibernateDAO {
	private static final Logger log = LoggerFactory.getLogger(CorteDAO.class);
	// property constants
	public static final String NAME = "name";
	public static final String DATE_START = "dateStart";
	public static final String DATE_END = "dateEnd";
	public static final String STATE = "state";
	public static final String DATECREATION = "datecreation";
	public static final String USERCREATION = "usercreation";
	public static final String DATECHANGE = "datechange";
	public static final String USERCHANGE = "userchange";

	public void save(Corte transientInstance) {
		log.debug("saving Corte instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Corte persistentInstance) {
		log.debug("deleting Corte instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Corte findById(java.lang.Long id) {
		log.debug("getting Corte instance with id: " + id);
		try {
			Corte instance = (Corte) getSession().get(
					"com.zyos.alert.query.model.Corte", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<Corte> findByExample(Corte instance) {
		log.debug("finding Corte instance by example");
		try {
			List<Corte> results = (List<Corte>) getSession()
					.createCriteria("com.zyos.alert.query.model.Corte")
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
		log.debug("finding Corte instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Corte as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<Corte> findByName(Object name) {
		return findByProperty(NAME, name);
	}

	public List<Corte> findByDateStart(Object dateStart) {
		return findByProperty(DATE_START, dateStart);
	}

	public List<Corte> findByDateEnd(Object dateEnd) {
		return findByProperty(DATE_END, dateEnd);
	}

	public List<Corte> findByState(Object state) {
		return findByProperty(STATE, state);
	}

	public List<Corte> findByDatecreation(Object datecreation) {
		return findByProperty(DATECREATION, datecreation);
	}

	public List<Corte> findByUsercreation(Object usercreation) {
		return findByProperty(USERCREATION, usercreation);
	}

	public List<Corte> findByDatechange(Object datechange) {
		return findByProperty(DATECHANGE, datechange);
	}

	public List<Corte> findByUserchange(Object userchange) {
		return findByProperty(USERCHANGE, userchange);
	}

	public List findAll() {
		log.debug("finding all Corte instances");
		try {
			String queryString = "from Corte";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Corte merge(Corte detachedInstance) {
		log.debug("merging Corte instance");
		try {
			Corte result = (Corte) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Corte instance) {
		log.debug("attaching dirty Corte instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Corte instance) {
		log.debug("attaching clean Corte instance");
		try {
			getSession().buildLockRequest(LockOptions.NONE).lock(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
	
	public Integer loadCurrentCorte() throws Exception {
		try {
			StringBuilder hql = new StringBuilder();
			hql.append("select name from Corte c where :currentDate between c.dateStart and c.dateEnd");

			Query qo = getSession().createQuery(hql.toString());
			qo.setParameter("currentDate", ManageDate.getCurrentDate(ManageDate.YYYY_MM_DD));

			Object id = qo.uniqueResult();
			if (id != null)
				return Integer.parseInt(id.toString());
			return null;
		} catch (Exception e) {
			throw e;
		}
	}
}