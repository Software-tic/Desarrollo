package com.zyos.alert.moodle.model;

import static org.hibernate.criterion.Example.create;

import java.util.List;

import org.hibernate.LockOptions;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zyos.alert.calification.model.CalificationItem;
import com.zyos.alert.calification.model.Evaluation;
import com.zyos.core.connection.MySQLBaseHibernateDAO;

/**
 * A data access object (DAO) providing persistence and search support for
 * MdlGradeGrades entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.zyos.alert.moodle.model.MdlGradeGrades
 * @author MyEclipse Persistence Tools
 */
public class MdlGradeCategoriesDAO extends MySQLBaseHibernateDAO {
	private static final Logger log = LoggerFactory
			.getLogger(MdlGradeCategoriesDAO.class);

	public void save(MdlGradeGrades transientInstance) {
		log.debug("saving MdlGradeGrades instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(MdlGradeGrades persistentInstance) {
		log.debug("deleting MdlGradeGrades instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public MdlGradeGrades findById(java.lang.Long id) {
		log.debug("getting MdlGradeGrades instance with id: " + id);
		try {
			MdlGradeGrades instance = (MdlGradeGrades) getSession().get(
					"com.zyos.alert.moodle.model.MdlGradeGrades", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<MdlGradeGrades> findByExample(MdlGradeGrades instance) {
		log.debug("finding MdlGradeGrades instance by example");
		try {
			List<MdlGradeGrades> results = (List<MdlGradeGrades>) getSession()
					.createCriteria(
							"com.zyos.alert.moodle.model.MdlGradeGrades")
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
		log.debug("finding MdlGradeGrades instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from MdlGradeGrades as model where model."
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
		log.debug("finding all MdlGradeGrades instances");
		try {
			String queryString = "from MdlGradeGrades";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public MdlGradeGrades merge(MdlGradeGrades detachedInstance) {
		log.debug("merging MdlGradeGrades instance");
		try {
			MdlGradeGrades result = (MdlGradeGrades) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(MdlGradeGrades instance) {
		log.debug("attaching dirty MdlGradeGrades instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(MdlGradeGrades instance) {
		log.debug("attaching clean MdlGradeGrades instance");
		try {
			getSession().buildLockRequest(LockOptions.NONE).lock(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public List<Evaluation> migrateEvaluationFromMoodle(List<Long> idEL) throws Exception {
		try {
			StringBuilder hql = new StringBuilder();
			hql.append("select ");
			hql.append("mgc.id as idMoodle, mgc.fullname as name, mgi.aggregationcoef as percentage, mgc.courseid as idMoodleCourse ");
			hql.append("from ");
			hql.append("MdlGradeCategories mgc, ");
			hql.append("MdlGradeItems mgi ");
			hql.append("where  ");
			hql.append("mgc.id = mgi.iteminstance ");
			hql.append("and mgi.categoryid is null ");
			hql.append("and mgi.aggregationcoef > 0  ");
			
			if(idEL != null && !idEL.isEmpty())
				hql.append("and mgc.id not in (:idMoodleList) ");
			
			Query qo = getSession().createQuery(hql.toString()).setResultTransformer(Transformers.aliasToBean(Evaluation.class));
			
			if(idEL != null && !idEL.isEmpty())
				qo.setParameterList("idMoodleList", idEL);
			
			return qo.list();
		} catch (Exception e) {
			throw e;
		}
	}

	public List<CalificationItem> migrateCalificationItemFromMoodle(
			List<Long> idEL) throws Exception {
		try {
			StringBuilder hql = new StringBuilder();
			hql.append("select  ");
			hql.append("mgi.id as idMoodle, mgi.categoryid as idMoodleEvaluation, mgi.aggregationcoef as percentage, ");
			hql.append("mgi.itemname as name ");
			hql.append("from ");
			hql.append("MdlGradeItems mgi, MdlGradeCategories mgc ");
			hql.append("where ");
			hql.append("mgi.categoryid is not null ");
			hql.append("and mgi.categoryid = mgc.id ");
			hql.append("and mgi.aggregationcoef > 0  ");
			hql.append("and mgi.itemtype !=:iT ");
			hql.append("and mgi.itemmodule !=:iM ");
			
			if(idEL != null && !idEL.isEmpty())
				hql.append("and mgi.id not in (:idMoodleList) ");
			
			Query qo = getSession().createQuery(hql.toString()).setResultTransformer(Transformers.aliasToBean(CalificationItem.class));
			qo.setParameter("iT", "manual");
			qo.setParameter("iM", "attendance");
			
			if(idEL != null && !idEL.isEmpty())
				qo.setParameterList("idMoodleList", idEL);
			
			return qo.list();
		} catch (Exception e) {
			throw e;
		}
	}
}