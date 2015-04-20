package com.zyos.alert.absent.model;

import com.zyos.core.connection.OracleBaseHibernateDAO;
import java.util.List;
import org.hibernate.LockMode;
import org.hibernate.Query;
import static org.hibernate.criterion.Example.create;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A data access object (DAO) providing persistence and search support for
 * Daycalendar entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.zyos.alert.absent.model.DayCalendar
 * @author MyEclipse Persistence Tools
 */
public class DayCalendarDAO extends OracleBaseHibernateDAO {
	private static final Logger log = LoggerFactory
			.getLogger(DayCalendarDAO.class);

	public void save(DayCalendar transientInstance) {
		log.debug("saving Daycalendar instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(DayCalendar persistentInstance) {
		log.debug("deleting Daycalendar instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public DayCalendar findById(java.lang.Long id) {
		log.debug("getting Daycalendar instance with id: " + id);
		try {
			DayCalendar instance = (DayCalendar) getSession().get(
					"com.zyos.alert.absent.model.Daycalendar", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<DayCalendar> findByExample(DayCalendar instance) {
		log.debug("finding Daycalendar instance by example");
		try {
			List<DayCalendar> results = (List<DayCalendar>) getSession()
					.createCriteria("com.zyos.alert.absent.model.Daycalendar")
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
		log.debug("finding Daycalendar instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Daycalendar as model where model."
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
		log.debug("finding all Daycalendar instances");
		try {
			String queryString = "from Daycalendar";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public DayCalendar merge(DayCalendar detachedInstance) {
		log.debug("merging Daycalendar instance");
		try {
			DayCalendar result = (DayCalendar) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(DayCalendar instance) {
		log.debug("attaching dirty Daycalendar instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(DayCalendar instance) {
		log.debug("attaching clean Daycalendar instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}