package com.zyos.alert.query.model;

import java.util.List;

import org.hibernate.LockOptions;
import org.hibernate.Query;
<<<<<<< HEAD
import static org.hibernate.criterion.Example.create;
=======

import static org.hibernate.criterion.Example.create;

>>>>>>> origin/master
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zyos.core.connection.OracleBaseHibernateDAO;

/**
 * A data access object (DAO) providing persistence and search support for
<<<<<<< HEAD
 * MdlCourse entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.zyos.alert.moodle.model.MdlCourse
=======
 * FacultySchool entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.zyos.alert.query.model.FacultySchool
>>>>>>> origin/master
 * @author MyEclipse Persistence Tools
 */
public class FacultySchoolDAO extends OracleBaseHibernateDAO {
	private static final Logger log = LoggerFactory
			.getLogger(FacultySchoolDAO.class);
<<<<<<< HEAD

=======
	// property constants
>>>>>>> origin/master
	public static final String DATECREATION = "datecreation";
	public static final String DATECHANGE = "datechange";
	public static final String USERCREATION = "usercreation";
	public static final String USERCHANGE = "userchange";
	public static final String STATE = "state";

	public void save(FacultySchool transientInstance) {
		log.debug("saving FacultySchool instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(FacultySchool persistentInstance) {
		log.debug("deleting FacultySchool instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public FacultySchool findById(java.lang.Long id) {
		log.debug("getting FacultySchool instance with id: " + id);
		try {
			FacultySchool instance = (FacultySchool) getSession().get(
					"com.zyos.alert.query.model.FacultySchool", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<FacultySchool> findByExample(FacultySchool instance) {
		log.debug("finding FacultySchool instance by example");
		try {
			List<FacultySchool> results = (List<FacultySchool>) getSession()
					.createCriteria("com.zyos.alert.query.model.FacultySchool")
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
		log.debug("finding FacultySchool instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from FacultySchool as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<FacultySchool> findByDatecreation(Object datecreation) {
		return findByProperty(DATECREATION, datecreation);
	}

	public List<FacultySchool> findByDatechange(Object datechange) {
		return findByProperty(DATECHANGE, datechange);
	}

	public List<FacultySchool> findByUsercreation(Object usercreation) {
		return findByProperty(USERCREATION, usercreation);
	}

	public List<FacultySchool> findByUserchange(Object userchange) {
		return findByProperty(USERCHANGE, userchange);
	}

	public List<FacultySchool> findByState(Object state) {
		return findByProperty(STATE, state);
	}

	public List findAll() {
		log.debug("finding all FacultySchool instances");
		try {
			String queryString = "from FacultySchool";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public FacultySchool merge(FacultySchool detachedInstance) {
		log.debug("merging FacultySchool instance");
		try {
			FacultySchool result = (FacultySchool) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(FacultySchool instance) {
		log.debug("attaching dirty FacultySchool instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(FacultySchool instance) {
		log.debug("attaching clean FacultySchool instance");
		try {
			getSession().buildLockRequest(LockOptions.NONE).lock(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}