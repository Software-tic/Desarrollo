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
 * SchoolCoordinador entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.zyos.alert.query.model.SchoolCoordinador
 * @author MyEclipse Persistence Tools
 */
public class SchoolCoordinadorDAO extends OracleBaseHibernateDAO {
	private static final Logger log = LoggerFactory
			.getLogger(SchoolCoordinadorDAO.class);
	// property constants
	public static final String DATECREATION = "datecreation";
	public static final String USERCREATION = "usercreation";
	public static final String DATECHANGE = "datechange";
	public static final String USERCHANGE = "userchange";
	public static final String STATE = "state";

	public void save(SchoolCoordinador transientInstance) {
		log.debug("saving SchoolCoordinador instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(SchoolCoordinador persistentInstance) {
		log.debug("deleting SchoolCoordinador instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public SchoolCoordinador findById(java.lang.Long id) {
		log.debug("getting SchoolCoordinador instance with id: " + id);
		try {
			SchoolCoordinador instance = (SchoolCoordinador) getSession().get(
					"com.zyos.alert.query.model.SchoolCoordinador", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<SchoolCoordinador> findByExample(SchoolCoordinador instance) {
		log.debug("finding SchoolCoordinador instance by example");
		try {
			List<SchoolCoordinador> results = getSession()
					.createCriteria(
							"com.zyos.alert.query.model.SchoolCoordinador")
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
		log.debug("finding SchoolCoordinador instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from SchoolCoordinador as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<SchoolCoordinador> findByDatecreation(Object datecreation) {
		return findByProperty(DATECREATION, datecreation);
	}

	public List<SchoolCoordinador> findByUsercreation(Object usercreation) {
		return findByProperty(USERCREATION, usercreation);
	}

	public List<SchoolCoordinador> findByDatechange(Object datechange) {
		return findByProperty(DATECHANGE, datechange);
	}

	public List<SchoolCoordinador> findByUserchange(Object userchange) {
		return findByProperty(USERCHANGE, userchange);
	}

	public List<SchoolCoordinador> findByState(Object state) {
		return findByProperty(STATE, state);
	}

	public List findAll() {
		log.debug("finding all SchoolCoordinador instances");
		try {
			String queryString = "from SchoolCoordinador";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public SchoolCoordinador merge(SchoolCoordinador detachedInstance) {
		log.debug("merging SchoolCoordinador instance");
		try {
			SchoolCoordinador result = (SchoolCoordinador) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(SchoolCoordinador instance) {
		log.debug("attaching dirty SchoolCoordinador instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(SchoolCoordinador instance) {
		log.debug("attaching clean SchoolCoordinador instance");
		try {
			getSession().buildLockRequest(LockOptions.NONE).lock(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
	
	public Long searchIdDFacultad(){
		StringBuilder hql = new StringBuilder();
		Query qo = null;
		try {
			hql.append("SELECT MAX(idSchoolCoord) FROM SchoolCoordinador");
			qo = getSession().createQuery(hql.toString());
			return Long.valueOf(qo.uniqueResult().toString());
		} catch (RuntimeException re) {
			throw re;
		} finally {
			hql = null;
			qo = null;
		}
	}
	
	public SchoolCoordinador FindDFacultad(Long idZyosUser,Long School){
		StringBuilder hql = new StringBuilder();
		Query qo = null;
		try {
			hql.append("SELECT NEW SchoolCoordinador(sc.idSchoolCoord,sc.idZyosuser,sc.idSchool) FROM SchoolCoordinador sc "
					+ " WHERE sc.idZyosuser=:idZyosUser AND sc.idSchool=:idSchool ");
			qo = getSession().createQuery(hql.toString());
			qo.setParameter("idZyosUser", idZyosUser);
			qo.setParameter("idSchool", School);
			return (SchoolCoordinador)qo.uniqueResult();
		} catch (RuntimeException re) {
			throw re;
		} finally {
			hql = null;
			qo = null;
		}
	}
}