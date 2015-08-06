package com.zyos.alert.characterization.model;

import static org.hibernate.criterion.Example.create;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zyos.core.connection.OracleBaseHibernateDAO;

/**
 * A data access object (DAO) providing persistence and search support for
 * Caracterizacion entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.zyos.alert.studentReport.model.Caracterizacion
 * @author MyEclipse Persistence Tools
 */
public class CaracterizacionOpcionesDAO extends OracleBaseHibernateDAO {
	private static final Logger log = LoggerFactory
			.getLogger(CaracterizacionOpcionesDAO.class);

	public void save(Caracterizacion transientInstance) {
		log.debug("saving Caracterizacion instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Caracterizacion persistentInstance) {
		log.debug("deleting Caracterizacion instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Caracterizacion findById(java.lang.Long id) {
		log.debug("getting Caracterizacion instance with id: " + id);
		try {
			Caracterizacion instance = (Caracterizacion) getSession().get(
					"com.zyos.alert.studentReport.model.Caracterizacion", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<Caracterizacion> findByExample(Caracterizacion instance) {
		log.debug("finding Caracterizacion instance by example");
		try {
			List<Caracterizacion> results = getSession()
					.createCriteria(
							"com.zyos.alert.studentReport.model.Caracterizacion")
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
		log.debug("finding Caracterizacion instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from Caracterizacion as model where model."
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
		log.debug("finding all Caracterizacion instances");
		try {
			String queryString = "from Caracterizacion";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Caracterizacion merge(Caracterizacion detachedInstance) {
		log.debug("merging Caracterizacion instance");
		try {
			Caracterizacion result = (Caracterizacion) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Caracterizacion instance) {
		log.debug("attaching dirty Caracterizacion instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public List<Caracterizacion> loadCharacterizationPollSocialAdaptationList()
			throws Exception {
		try {
			StringBuilder hql = new StringBuilder();
			hql.append("select id_estudiante, count(*) from (select id_estudiante from ");

			hql.append("(SELECT caracterizacion.id_estudiante ");
			hql.append("FROM zyos.caracterizacion ");
			hql.append("WHERE caracterizacion.cod_pregunta = '3' AND caracterizacion.respuesta in ('4', '5', '6')) tt ");

			hql.append("union all ");

			hql.append("(SELECT caracterizacion.id_estudiante ");
			hql.append("FROM zyos.caracterizacion ");
			hql.append("WHERE ");
			hql.append("caracterizacion.cod_pregunta = '14' and caracterizacion.respuesta = '1') ");

			hql.append("union all ");

			hql.append("(SELECT caracterizacion.id_estudiante ");
			hql.append("FROM zyos.caracterizacion ");
			hql.append("WHERE caracterizacion.cod_pregunta = '23' and caracterizacion.respuesta = '2') ");

			hql.append(") t group by t.id_estudiante ");
			
			SQLQuery qo = getSession().createSQLQuery(hql.toString());
			return qo.list();
		} catch (Exception e) {
			throw e;
		}
	}

	public List<Caracterizacion> loadCharacterizationPollHealthList() {
		// TODO Auto-generated method stub
		return null;
	}

}