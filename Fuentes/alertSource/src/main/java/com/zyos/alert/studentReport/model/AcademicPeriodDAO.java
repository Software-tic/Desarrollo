package com.zyos.alert.studentReport.model;

import com.zyos.alert.integration.model.GroupSubjectSAC;
import com.zyos.core.common.util.ManageDate;
import com.zyos.core.connection.OracleBaseHibernateDAO;
import java.util.List;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;

import static org.hibernate.criterion.Example.create;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A data access object (DAO) providing persistence and search support for
 * Academicperiod entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.zyos.alert.studentReport.model.AcademicPeriod
 * @author MyEclipse Persistence Tools
 */
public class AcademicPeriodDAO extends OracleBaseHibernateDAO {
	private static final Logger log = LoggerFactory
			.getLogger(AcademicPeriodDAO.class);

	public void save(AcademicPeriod transientInstance) {
		log.debug("saving Academicperiod instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(AcademicPeriod persistentInstance) {
		log.debug("deleting Academicperiod instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public AcademicPeriod findById(java.lang.Long id) {
		log.debug("getting Academicperiod instance with id: " + id);
		try {
			AcademicPeriod instance = (AcademicPeriod) getSession().get(
					"com.zyos.alert.studentReport.model.Academicperiod", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<AcademicPeriod> findByExample(AcademicPeriod instance) {
		log.debug("finding Academicperiod instance by example");
		try {
			List<AcademicPeriod> results = (List<AcademicPeriod>) getSession()
					.createCriteria(
							"com.zyos.alert.studentReport.model.Academicperiod")
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
		log.debug("finding Academicperiod instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from Academicperiod as model where model."
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
		log.debug("finding all Academicperiod instances");
		try {
			String queryString = "from Academicperiod";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public String loadCurrentAcademicPeriodDescription() throws Exception {
		try {
			StringBuilder hql = new StringBuilder();
			hql.append("select description from AcademicPeriod ac where :currentDate between ac.startDate and ac.endDate");

			Query qo = getSession().createQuery(hql.toString());
			qo.setParameter("currentDate", ManageDate.getCurrentDate(ManageDate.YYYY_MM_DD));

			Object id = qo.uniqueResult();
			if (id != null)
				return (String) id;
			return null;
		} catch (Exception e) {
			throw e;
		}
	}
	

	public Long loadCurrentAcademicPeriod() throws Exception {
		try {
			StringBuilder hql = new StringBuilder();
			hql.append("select id from AcademicPeriod ac where :currentDate between ac.startDate and ac.endDate");

			Query qo = getSession().createQuery(hql.toString());
			qo.setParameter("currentDate", ManageDate.getCurrentDate(ManageDate.YYYY_MM_DD));

			Object id = qo.uniqueResult();
			if (id != null)
				return (Long) id;
			return null;
		} catch (Exception e) {
			throw e;
		}
	}
	
	/** @author ogarzonm*/
	public String loadCurrentAcademicPeriodName() throws Exception {
		try {
			StringBuilder hql = new StringBuilder();
			hql.append("select name from AcademicPeriod ac where :currentDate between ac.startDate and ac.endDate");

			Query qo = getSession().createQuery(hql.toString());
			qo.setParameter("currentDate", ManageDate.getCurrentDate(ManageDate.YYYY_MM_DD));

			Object id = qo.uniqueResult();
			if (id != null)
				return (String) id;
			return null;
		} catch (Exception e) {
			throw e;
		}
	}
}