package com.zyos.alert.studentReport.model;

import com.zyos.core.common.api.IZyosState;
import com.zyos.core.connection.OracleBaseHibernateDAO;

import java.util.List;

import org.hibernate.LockMode;
import org.hibernate.Query;

import static org.hibernate.criterion.Example.create;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A data access object (DAO) providing persistence and search support for
 * Observation entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.zyos.alert.studentReport.model.Observation
 * @author MyEclipse Persistence Tools
 */
public class ObservationDAO extends OracleBaseHibernateDAO {
	private static final Logger log = LoggerFactory
			.getLogger(ObservationDAO.class);

	public void save(Observation transientInstance) {
		log.debug("saving Observation instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Observation persistentInstance) {
		log.debug("deleting Observation instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Observation findById(java.lang.Long id) {
		log.debug("getting Observation instance with id: " + id);
		try {
			Observation instance = (Observation) getSession().get(
					"com.zyos.alert.studentReport.model.Observation", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<Observation> findByExample(Observation instance) {
		log.debug("finding Observation instance by example");
		try {
			List<Observation> results = (List<Observation>) getSession()
					.createCriteria(
							"com.zyos.alert.studentReport.model.Observation")
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
		log.debug("finding Observation instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Observation as model where model."
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
		log.debug("finding all Observation instances");
		try {
			String queryString = "from Observation";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Observation merge(Observation detachedInstance) {
		log.debug("merging Observation instance");
		try {
			Observation result = (Observation) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Observation instance) {
		log.debug("attaching dirty Observation instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Observation instance) {
		log.debug("attaching clean Observation instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
	
	public List<Observation> loadObservationByTeacher(Long idAdviser) throws Exception {
		StringBuilder hql = new StringBuilder();
		Query qo = null;
		try {
			hql.append(" SELECT NEW Observation(");
			hql.append(" o.idObservation, ");
			hql.append(" o.dateIntervention, ");
			hql.append(" s.name, ");
			hql.append(" zu.name || ' ' || ");
			hql.append(" zu.lastName, ");
			hql.append(" o.detailObservation,"
					+ " o.timestart, o.timefinish) ");
			hql.append(" FROM ");
			hql.append(" Observation o, ");
			hql.append(" ZyosUser zu, ");
			hql.append(" ZyosGroup s ");
			
			hql.append(" WHERE ");
			hql.append(" o.idAdviser = :idAdviser AND ");
			hql.append(" zu.idZyosGroup = s.id AND ");
			hql.append(" zu.state =:state AND ");
			hql.append(" s.state =:state AND ");
			hql.append(" o.state =:state ");
			
			
			qo = getSession().createQuery(hql.toString());
			qo.setParameter("idAdviser", idAdviser);
			qo.setParameter("state", IZyosState.ACTIVE);
		
			return qo.list();
			
		} catch (Exception e) {
			throw e;
		} finally {
			hql = null;
			qo = null;
		}
	}
	
	public List<Observation> loadObservationByStudent(Long idStudent, Long idAdviser) throws Exception {
		StringBuilder hql = new StringBuilder();
		Query qo = null;
		try {
			hql.append(" SELECT NEW Observation(");
			hql.append(" o.idObservation, ");
			hql.append(" o.dateIntervention, ");
			hql.append(" s.name, ");
			hql.append(" zu.name || ' ' || ");
			hql.append(" zu.lastName, ");
			hql.append(" o.detailObservation) ");
			hql.append(" FROM ");
			hql.append(" Observation o, ");
			hql.append(" ZyosUser zu, ");
			hql.append(" ZyosGroup s ");
			
			hql.append(" WHERE ");
			hql.append(" o.idReportStudent = :idStudent AND ");
			hql.append(" o.idAdviser = zu.idZyosUser AND ");
			hql.append(" zu.idZyosGroup = s.id AND ");
			hql.append(" zu.state =:state AND ");
			hql.append(" s.state =:state AND ");
			hql.append(" o.state =:state ");
			hql.append(" o.privacy =:privacy ");
			int privacy=0;
			if (FindAdviserPsic(idAdviser)){
				privacy=1;
			}
			qo = getSession().createQuery(hql.toString());
			qo.setParameter("idStudent", idStudent);
			qo.setParameter("privacy", privacy);
			qo.setParameter("state", IZyosState.ACTIVE);
		
			return qo.list();
			
		} catch (Exception e) {
			throw e;
		} finally {
			hql = null;
			qo = null;
		}
	}
	
	public boolean FindAdviserPsic(Long idAdviser) throws Exception {
		//si el usuario es psicologo o psicopedagogo
		StringBuilder hql = new StringBuilder();
		Query qo = null;
		try {
			hql.append("SELECT u.idzyosuser "
					+ "FROM zyosuser u, zyosusergroup ug "
					+ "WHERE ug.idgroup IN (SELECT id FROM zyosgroup WHERE name LIKE '%sic%') AND "
					+ "ug.idzyosuser=u.idzyosuser AND u.state=:state AND ug.state=:state  AND u.idzyosuser=:idAdviser");
			
			qo = getSession().createQuery(hql.toString());
			qo.setParameter("idAdviser", idAdviser);
			qo.setParameter("state", IZyosState.ACTIVE);
			
			if(qo.uniqueResult()!=null){
				Long a=(Long) qo.uniqueResult();
				if (a==idAdviser){
					return true;
				}
			}
			
		}catch(Exception e){
			throw e;
		} finally {
			hql = null;
			qo = null;
		}
		return false;
	}
	

	public List<Observation> loadObservationByStudent(Long idStudent) throws Exception {
		StringBuilder hql = new StringBuilder();
		Query qo = null;
		try {
			hql.append(" SELECT NEW Observation(");
			hql.append(" o.idObservation, ");
			hql.append(" o.dateIntervention, ");
			hql.append(" s.name, ");
			hql.append(" zu.name || ' ' || ");
			hql.append(" zu.lastName, ");
			hql.append(" o.detailObservation) ");
			hql.append(" FROM ");
			hql.append(" Observation o, ");
			hql.append(" ZyosUser zu, ");
			hql.append(" ZyosGroup s ");
			
			hql.append(" WHERE ");
			hql.append(" o.idReportStudent = :idStudent AND ");
			hql.append(" o.idAdviser = zu.idZyosUser AND ");
			hql.append(" zu.idZyosGroup = s.id AND ");
			hql.append(" zu.state =:state AND ");
			hql.append(" s.state =:state AND ");
			hql.append(" o.state =:state ");
			
			
			qo = getSession().createQuery(hql.toString());
			qo.setParameter("idStudent", idStudent);
			qo.setParameter("state", IZyosState.ACTIVE);
		
			return qo.list();
			
		} catch (Exception e) {
			throw e;
		} finally {
			hql = null;
			qo = null;
		}
	}
}