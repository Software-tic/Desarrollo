package com.zyos.alert.studentReport.model;

import com.zyos.core.connection.OracleBaseHibernateDAO;
import java.util.List;
import org.hibernate.LockMode;
import org.hibernate.Query;
import static org.hibernate.criterion.Example.create;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A data access object (DAO) providing persistence and search support for
 * Statusreportstudent entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.zyos.alert.studentReport.model.StatusReportStudent
 * @author MyEclipse Persistence Tools
 */
public class StatusReportStudentDAO extends OracleBaseHibernateDAO {
	private static final Logger log = LoggerFactory
			.getLogger(StatusReportStudentDAO.class);

	public void save(StatusReportStudent transientInstance) {
		log.debug("saving Statusreportstudent instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(StatusReportStudent persistentInstance) {
		log.debug("deleting Statusreportstudent instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public StatusReportStudent findById(java.lang.Long id) {
		log.debug("getting Statusreportstudent instance with id: " + id);
		try {
			StatusReportStudent instance = (StatusReportStudent) getSession()
					.get("com.zyos.alert.studentReport.model.Statusreportstudent",
							id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<StatusReportStudent> findByExample(StatusReportStudent instance) {
		log.debug("finding Statusreportstudent instance by example");
		try {
			List<StatusReportStudent> results = getSession()
					.createCriteria(
							"com.zyos.alert.studentReport.model.Statusreportstudent")
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
		log.debug("finding Statusreportstudent instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from Statusreportstudent as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<StatusReportStudent> findAll() {
		log.debug("finding all Statusreportstudent instances");
		try {
			String queryString = "from StatusReportStudent where state > 0";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public StatusReportStudent merge(StatusReportStudent detachedInstance) {
		log.debug("merging Statusreportstudent instance");
		try {
			StatusReportStudent result = (StatusReportStudent) getSession()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(StatusReportStudent instance) {
		log.debug("attaching dirty Statusreportstudent instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(StatusReportStudent instance) {
		log.debug("attaching clean Statusreportstudent instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}