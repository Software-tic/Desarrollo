package com.zyos.alert.controlPanel.model;

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
 * ControlPanel entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.zyos.alert.absent.model.ControlPanel
 * @author MyEclipse Persistence Tools
 */
public class ControlPanelDAO extends OracleBaseHibernateDAO {
	private static final Logger log = LoggerFactory
			.getLogger(ControlPanelDAO.class);

	public void save(ControlPanel transientInstance) {
		log.debug("saving ControlPanel instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(ControlPanel persistentInstance) {
		log.debug("deleting ControlPanel instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public ControlPanel findById(java.lang.Long id) {
		log.debug("getting ControlPanel instance with id: " + id);
		try {
			ControlPanel instance = (ControlPanel) getSession().get(
					"com.zyos.alert.absent.model.controlPanel", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<ControlPanel> findByExample(ControlPanel instance) {
		log.debug("finding ControlPanel instance by example");
		try {
			List<ControlPanel> results = (List<ControlPanel>) getSession()
					.createCriteria("com.zyos.alert.absent.model.controlPanel")
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
		log.debug("finding ControlPanel instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from ControlPanel as model where model."
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
		log.debug("finding all ControlPanel instances");
		try {
			String queryString = "from ControlPanel";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public ControlPanel merge(ControlPanel detachedInstance) {
		log.debug("merging ControlPanel instance");
		try {
			ControlPanel result = (ControlPanel) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(ControlPanel instance) {
		log.debug("attaching dirty ControlPanel instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(ControlPanel instance) {
		log.debug("attaching clean ControlPanel instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
	
	public ControlPanel loadCurrentMarginDays() throws Exception {
		try {
			StringBuilder hql = new StringBuilder();
			hql.append(" select new ControlPanel (cp.idControlPanel, cp.marginHour, cp.percentageRiskFactor, cp.percentageAssistance) ");
			hql.append(" from ControlPanel cp ");
			hql.append(" where ");
			hql.append(" cp.idControlPanel = :state AND ");
			hql.append(" cp.state = :state ");

			Query qo = null;
			qo = getSession().createQuery(hql.toString());
			qo.setParameter("state", IZyosState.ACTIVE);			
			hql = null;
			
			return (ControlPanel) qo.uniqueResult();
		} catch (Exception e) {
			throw e;
		}
	}
	
	public ControlPanel loadCurrentPercentageRisk() throws Exception {
		try {
			StringBuilder hql = new StringBuilder();
			hql.append(" select new ControlPanel (cp.idControlPanel, cp.marginHour, cp.percentageRiskFactor, cp.percentageAssistance) ");
			hql.append(" from ControlPanel cp ");
			hql.append(" where ");
			hql.append(" cp.idControlPanel = 2 AND ");
			hql.append(" cp.state = :state ");

			Query qo = null;
			qo = getSession().createQuery(hql.toString());
			qo.setParameter("state", IZyosState.ACTIVE);			
			hql = null;
			
			return (ControlPanel) qo.uniqueResult();
		} catch (Exception e) {
			throw e;
		}
	}
	
	public ControlPanel loadCurrentPercentageAssistance() throws Exception {
		try {
			StringBuilder hql = new StringBuilder();
			hql.append(" select new ControlPanel (cp.idControlPanel, cp.marginHour,  cp.percentageRiskFactor, cp.percentageAssistance) ");
			hql.append(" from ControlPanel cp ");
			hql.append(" where ");
			hql.append(" cp.idControlPanel = 3 AND ");
			hql.append(" cp.state = :state ");

			Query qo = null;
			qo = getSession().createQuery(hql.toString());
			qo.setParameter("state", IZyosState.ACTIVE);			
			hql = null;
			
			return (ControlPanel) qo.uniqueResult();
		} catch (Exception e) {
			throw e;
		}
	}
	
	/** SIAT-TUNJA */
	public ControlPanel loadCurrentBaseGradeTunja() throws Exception {
		try {
			StringBuilder hql = new StringBuilder();
			hql.append(" select new ControlPanel (cp.idControlPanel, cp.marginHour,  cp.percentageRiskFactor, cp.percentageAssistance) ");
			hql.append(" from ControlPanel cp ");
			hql.append(" where ");
			hql.append(" cp.idControlPanel = 4 AND ");
			hql.append(" cp.state = :state ");

			Query qo = null;
			qo = getSession().createQuery(hql.toString());
			qo.setParameter("state", IZyosState.ACTIVE);			
			hql = null;
			
			return (ControlPanel) qo.uniqueResult();
		} catch (Exception e) {
			throw e;
		}
	}
	
	/** SIAT-TUNJA */
	public ControlPanel loadCurrentBaseGoodGradesTunja() throws Exception {
		try {
			StringBuilder hql = new StringBuilder();
			hql.append(" select new ControlPanel (cp.idControlPanel, cp.marginHour, cp.percentageRiskFactor, cp.percentageAssistance) ");
			hql.append(" from ControlPanel cp ");
			hql.append(" where ");
			hql.append(" cp.idControlPanel = 5 AND ");
			hql.append(" cp.state = :state ");

			Query qo = null;
			qo = getSession().createQuery(hql.toString());
			qo.setParameter("state", IZyosState.ACTIVE);			
			hql = null;
			
			return (ControlPanel) qo.uniqueResult();
		} catch (Exception e) {
			throw e;
		}
	}
	
}