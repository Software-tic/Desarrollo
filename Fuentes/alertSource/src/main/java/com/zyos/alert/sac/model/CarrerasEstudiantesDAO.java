package com.zyos.alert.sac.model;

import static org.hibernate.criterion.Example.create;

import java.util.List;

import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zyos.core.connection.OracleBaseHibernateDAO;

/**
 * A data access object (DAO) providing persistence and search support for
 * CarrerasEstudiantes entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.zyos.alert.studentReport.model.CarrerasEstudiantes
 * @author MyEclipse Persistence Tools
 */
public class CarrerasEstudiantesDAO extends OracleBaseHibernateDAO {
	private static final Logger log = LoggerFactory
			.getLogger(CarrerasEstudiantesDAO.class);

	public void save(Carreras transientInstance) {
		log.debug("saving CarrerasEstudiantes instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Carreras persistentInstance) {
		log.debug("deleting CarrerasEstudiantes instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Carreras findById(java.lang.Long id) {
		log.debug("getting CarrerasEstudiantes instance with id: " + id);
		try {
			Carreras instance = (Carreras) getSession().get(
					"com.zyos.alert.studentReport.model.CarrerasEstudiantes",
					id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<Carreras> findByExample(Carreras instance) {
		log.debug("finding CarrerasEstudiantes instance by example");
		try {
			List<Carreras> results = (List<Carreras>) getSession()
					.createCriteria(
							"com.zyos.alert.studentReport.model.CarrerasEstudiantes")
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
		log.debug("finding CarrerasEstudiantes instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from CarrerasEstudiantes as model where model."
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
		log.debug("finding all CarrerasEstudiantes instances");
		try {
			String queryString = "from CarrerasEstudiantes";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Carreras merge(Carreras detachedInstance) {
		log.debug("merging CarrerasEstudiantes instance");
		try {
			Carreras result = (Carreras) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Carreras instance) {
		log.debug("attaching dirty CarrerasEstudiantes instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

}