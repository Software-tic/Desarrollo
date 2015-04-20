package com.zyos.alert.sac.model;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zyos.alert.integration.model.DegreeSAC;
import com.zyos.core.connection.OracleBaseHibernateDAO;

/**
 * A data access object (DAO) providing persistence and search support for
 * Carreras entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.zyos.alert.studentReport.model.Carreras
 * @author MyEclipse Persistence Tools
 */
public class IntegraDAO extends OracleBaseHibernateDAO {
	private static final Logger log = LoggerFactory.getLogger(IntegraDAO.class);

	public void save(Carreras transientInstance) {
		log.debug("saving Carreras instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Carreras persistentInstance) {
		log.debug("deleting Carreras instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Carreras findById(java.lang.Long id) {
		log.debug("getting Carreras instance with id: " + id);
		try {
			Carreras instance = (Carreras) getSession().get(
					"com.zyos.alert.studentReport.model.Carreras", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
	/** @author ogarzonm*/
	public List<DegreeSAC> migrateDegreeListFromSAC() throws Exception {
		try {
			StringBuilder hql = new StringBuilder();
			hql.append("select  ");
			hql.append("cast(c.id as long) as  id, c.nombre as name ");
			hql.append("from ");
			hql.append("Carreras c ");
			hql.append("where ");
			hql.append("c.id not in (select id from Degree)) ");
			
			Query qo = getSession().createQuery(hql.toString()).setResultTransformer(Transformers.aliasToBean(DegreeSAC.class));
			return qo.list();
		} catch (Exception e) {
			throw e;
		}
	}

	/** @author ogarzonm*/
	public List<Integra> migrateIntegraDatabase() throws Exception {
		try {
			StringBuilder hql = new StringBuilder();

			hql.append("select ap.id as idAcademicPeriod, tm.id as idCourseMoodle, tm.idSubject as idSubject, tm.idGroup as idGroupSuject, ");
			hql.append("tm.shortname as shortname, tm.groupName as groupName ");
			hql.append("from  ");
			hql.append("TemporalMoodle tm,  ");
			hql.append("AcademicPeriod ap ");
			hql.append("where ");
			hql.append("ap.name = tm.academicPeriodName  ");
			hql.append("and tm.shortname not in (select shortname from Integra) ");

			Query qo = getSession().createQuery(hql.toString()).setResultTransformer(Transformers.aliasToBean(Integra.class));
			return qo.list();
		} catch (Exception e) {
			throw e;
		}
		
	}

	
}