package com.zyos.alert.familyStudent.model;

import static org.hibernate.criterion.Example.create;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import org.hibernate.LockMode;
import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zyos.core.common.api.IZyosState;
import com.zyos.core.connection.OracleBaseHibernateDAO;

/**
 * A data access object (DAO) providing persistence and search support for
 * FamilyStudent entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.zyos.alert.absent.model.FamilyStudent
 * @author MyEclipse Persistence Tools
 */
public class FamilyStudentDAO extends OracleBaseHibernateDAO {
	private static final Logger log = LoggerFactory
			.getLogger(FamilyStudentDAO.class);

	public void save(FamilyStudent transientInstance) {
		log.debug("saving FamilyStudent instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(FamilyStudent persistentInstance) {
		log.debug("deleting FamilyStudent instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public FamilyStudent findById(java.lang.Long id) {
		log.debug("getting FamilyStudent instance with id: " + id);
		try {
			FamilyStudent instance = (FamilyStudent) getSession().get(
					"com.zyos.alert.absent.model.familyStudent", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<FamilyStudent> findByExample(FamilyStudent instance) {
		log.debug("finding FamilyStudent instance by example");
		try {
			List<FamilyStudent> results = getSession()
					.createCriteria("com.zyos.alert.absent.model.familyStudent")
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
		log.debug("finding FamilyStudent instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from FamilyStudent as model where model."
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
		log.debug("finding all FamilyStudent instances");
		try {
			String queryString = "from FamilyStudent";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public FamilyStudent merge(FamilyStudent detachedInstance) {
		log.debug("merging FamilyStudent instance");
		try {
			FamilyStudent result = (FamilyStudent) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(FamilyStudent instance) {
		log.debug("attaching dirty FamilyStudent instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(FamilyStudent instance) {
		log.debug("attaching clean FamilyStudent instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
	
	public ArrayList<SelectItem> loadRelationshipTypeList() throws Exception {
		try {
			StringBuilder hql = new StringBuilder();
			hql.append(" select distinct new javax.faces.model.SelectItem(ct.id, ct.name) ");
			hql.append(" from Relationship ct ");
			hql.append(" where ");
			hql.append(" ct.state = :state order by ct.id ");

			Query qo = null;
			qo = getSession().createQuery(hql.toString());
			qo.setParameter("state", IZyosState.ACTIVE);			
			hql = null;
			return (ArrayList<SelectItem>) qo.list();
		} catch (Exception e) {
			throw e;
		}
	}
	
	
}