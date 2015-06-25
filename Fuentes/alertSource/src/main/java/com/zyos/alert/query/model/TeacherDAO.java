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
=======
 * Teacher entities. Transaction control of the save(), update() and delete()
>>>>>>> origin/master
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
<<<<<<< HEAD
 * @see com.zyos.alert.moodle.model.MdlCourse
=======
 * @see com.zyos.alert.query.model.Teacher
>>>>>>> origin/master
 * @author MyEclipse Persistence Tools
 */
public class TeacherDAO extends OracleBaseHibernateDAO {
	private static final Logger log = LoggerFactory.getLogger(TeacherDAO.class);
	// property constants
<<<<<<< HEAD
	public static final String NAME_SCHOOL = "nameSchool";
=======
>>>>>>> origin/master
	public static final String DATECREATION = "datecreation";
	public static final String USERCREATION = "usercreation";
	public static final String DATECHANGE = "datechange";
	public static final String USERCHANGE = "userchange";
	public static final String STATE = "state";

<<<<<<< HEAD
	public void save(School transientInstance) {
		log.debug("saving School instance");
=======
	public void save(Teacher transientInstance) {
		log.debug("saving Teacher instance");
>>>>>>> origin/master
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

<<<<<<< HEAD
	public void delete(School persistentInstance) {
		log.debug("deleting School instance");
=======
	public void delete(Teacher persistentInstance) {
		log.debug("deleting Teacher instance");
>>>>>>> origin/master
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

<<<<<<< HEAD
	public School findById(java.lang.Long id) {
		log.debug("getting School instance with id: " + id);
		try {
			School instance = (School) getSession().get(
					"com.zyos.alert.query.model.School", id);
=======
	public Teacher findById(java.lang.Long id) {
		log.debug("getting Teacher instance with id: " + id);
		try {
			Teacher instance = (Teacher) getSession().get(
					"com.zyos.alert.query.model.Teacher", id);
>>>>>>> origin/master
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

<<<<<<< HEAD
	public List<School> findByExample(School instance) {
		log.debug("finding School instance by example");
		try {
			List<School> results = (List<School>) getSession()
					.createCriteria("com.zyos.alert.query.model.School")
=======
	public List<Teacher> findByExample(Teacher instance) {
		log.debug("finding Teacher instance by example");
		try {
			List<Teacher> results = (List<Teacher>) getSession()
					.createCriteria("com.zyos.alert.query.model.Teacher")
>>>>>>> origin/master
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
<<<<<<< HEAD
		log.debug("finding School instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from School as model where model."
=======
		log.debug("finding Teacher instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Teacher as model where model."
>>>>>>> origin/master
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

<<<<<<< HEAD
	public List<School> findByNameSchool(Object nameSchool) {
		return findByProperty(NAME_SCHOOL, nameSchool);
	}

	public List<School> findByDatecreation(Object datecreation) {
		return findByProperty(DATECREATION, datecreation);
	}

	public List<School> findByUsercreation(Object usercreation) {
		return findByProperty(USERCREATION, usercreation);
	}

	public List<School> findByDatechange(Object datechange) {
		return findByProperty(DATECHANGE, datechange);
	}

	public List<School> findByUserchange(Object userchange) {
		return findByProperty(USERCHANGE, userchange);
	}

	public List<School> findByState(Object state) {
=======
	public List<Teacher> findByDatecreation(Object datecreation) {
		return findByProperty(DATECREATION, datecreation);
	}

	public List<Teacher> findByUsercreation(Object usercreation) {
		return findByProperty(USERCREATION, usercreation);
	}

	public List<Teacher> findByDatechange(Object datechange) {
		return findByProperty(DATECHANGE, datechange);
	}

	public List<Teacher> findByUserchange(Object userchange) {
		return findByProperty(USERCHANGE, userchange);
	}

	public List<Teacher> findByState(Object state) {
>>>>>>> origin/master
		return findByProperty(STATE, state);
	}

	public List findAll() {
<<<<<<< HEAD
		log.debug("finding all School instances");
		try {
			String queryString = "from School";
=======
		log.debug("finding all Teacher instances");
		try {
			String queryString = "from Teacher";
>>>>>>> origin/master
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

<<<<<<< HEAD
	public School merge(School detachedInstance) {
		log.debug("merging School instance");
		try {
			School result = (School) getSession().merge(detachedInstance);
=======
	public Teacher merge(Teacher detachedInstance) {
		log.debug("merging Teacher instance");
		try {
			Teacher result = (Teacher) getSession().merge(detachedInstance);
>>>>>>> origin/master
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

<<<<<<< HEAD
	public void attachDirty(School instance) {
		log.debug("attaching dirty School instance");
=======
	public void attachDirty(Teacher instance) {
		log.debug("attaching dirty Teacher instance");
>>>>>>> origin/master
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

<<<<<<< HEAD
	public void attachClean(School instance) {
		log.debug("attaching clean School instance");
=======
	public void attachClean(Teacher instance) {
		log.debug("attaching clean Teacher instance");
>>>>>>> origin/master
		try {
			getSession().buildLockRequest(LockOptions.NONE).lock(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}