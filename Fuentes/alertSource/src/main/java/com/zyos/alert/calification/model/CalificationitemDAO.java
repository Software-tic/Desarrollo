package com.zyos.alert.calification.model;

import static org.hibernate.criterion.Example.create;

import java.util.List;

import org.hibernate.LockMode;
import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zyos.core.common.api.IZyosState;
import com.zyos.core.connection.OracleBaseHibernateDAO;

/**
 * A data access object (DAO) providing persistence and search support for
 * Calificationitem entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.zyos.alert.calification.model.CalificationItem
 * @author MyEclipse Persistence Tools
 */
public class CalificationitemDAO extends OracleBaseHibernateDAO {
	private static final Logger log = LoggerFactory
			.getLogger(CalificationitemDAO.class);

	public void save(CalificationItem transientInstance) {
		log.debug("saving Calificationitem instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(CalificationItem persistentInstance) {
		log.debug("deleting Calificationitem instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public CalificationItem findById(java.lang.Long id) {
		log.debug("getting Calificationitem instance with id: " + id);
		try {
			CalificationItem instance = (CalificationItem) getSession().get(
					"com.zyos.alert.calification.model.Calificationitem", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<CalificationItem> findByExample(CalificationItem instance) {
		log.debug("finding Calificationitem instance by example");
		try {
			List<CalificationItem> results = (List<CalificationItem>) getSession()
					.createCriteria(
							"com.zyos.alert.calification.model.Calificationitem")
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
		log.debug("finding Calificationitem instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from Calificationitem as model where model."
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
		log.debug("finding all Calificationitem instances");
		try {
			String queryString = "from Calificationitem";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public CalificationItem merge(CalificationItem detachedInstance) {
		log.debug("merging Calificationitem instance");
		try {
			CalificationItem result = (CalificationItem) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(CalificationItem instance) {
		log.debug("attaching dirty Calificationitem instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(CalificationItem instance) {
		log.debug("attaching clean Calificationitem instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}	
	
	/**@autor jhernandez**/
	public void deleteCalificationItems(Evaluation evaluationToDelete )
			throws Exception {
		StringBuilder hql = new StringBuilder();
		Query qo = null;
		try {
			hql.append(" UPDATE CalificationItem c Set state=:state, dateChange=:dateChange, userChange=:userChange");
			hql.append(" Where idEvaluation=:idEvaluation ");
		
			qo = getSession().createQuery(hql.toString());
			qo.setParameter("idEvaluation", evaluationToDelete.getIdEvaluation());		
			qo.setParameter("dateChange", evaluationToDelete.getDateChange());
			qo.setParameter("userChange", evaluationToDelete.getUserChange());	
			qo.setParameter("state", IZyosState.INACTIVE);

			qo.executeUpdate();
			
			
		} catch (Exception e) {
			throw e;
		} finally {
			hql = null;
			qo = null;
		}

	}
	
	/**@autor jhernandez**/
	public List<Long> loadCalificationItemIdsToDelete(Evaluation evaluation)
			throws Exception {
		StringBuilder hql = new StringBuilder();
		Query qo = null;
		try {
			
			hql.append(" SELECT ");
			hql.append(" ci.idCalificationItem");			
			hql.append(" FROM");
			hql.append(" CalificationItem ci ");			
			hql.append(" WHERE");
			hql.append(" ci.idEvaluation =:idEvaluation  ");			
			hql.append(" AND  ci.state =:state");
			
			qo = getSession().createQuery(hql.toString());								
			qo.setParameter("state", IZyosState.ACTIVE);
			qo.setParameter("idEvaluation", evaluation.getIdEvaluation());

			List<Long> result = qo.list();

			return result;

		} catch (Exception e) {
			throw e;
		} finally {
			hql = null;
			qo = null;
		}
	}
	
	/**@autor jhernandez**/
	public int loadNumberCalificationItems(Evaluation evaluation)
			throws Exception {
		StringBuilder hql = new StringBuilder();
		Query qo = null;
		try {
			
			hql.append(" SELECT Count (*) ");						
			hql.append(" FROM");
			hql.append(" CalificationItem ");			
			hql.append(" WHERE");
			hql.append(" idEvaluation =:idEvaluation  ");			
			hql.append(" AND state =:state");
			
			qo = getSession().createQuery(hql.toString());								
			qo.setParameter("state", IZyosState.ACTIVE);
			qo.setParameter("idEvaluation", evaluation.getIdEvaluation());

			Long result = (Long) qo.uniqueResult();

			return result.intValue();

		} catch (Exception e) {
			throw e;
		} finally {
			hql = null;
			qo = null;
		}
	}
	
	/**@autor jhernandez**/
	public List <Long> loadIdsCalificationItems(Evaluation evaluation)
			throws Exception {
		StringBuilder hql = new StringBuilder();
		Query qo = null;
		try {
			
			hql.append(" SELECT ci.idCalificationItem ");						
			hql.append(" FROM");
			hql.append(" CalificationItem ci ");			
			hql.append(" WHERE ");
			hql.append(" idEvaluation =:idEvaluation  ");			
			hql.append(" AND ci.state =:state");
			hql.append(" order by 1");
			
			qo = getSession().createQuery(hql.toString());								
			qo.setParameter("state", IZyosState.ACTIVE);
			qo.setParameter("idEvaluation", evaluation.getIdEvaluation());

			 List<Long> result =  qo.list();

			return result;

		} catch (Exception e) {
			throw e;
		} finally {
			hql = null;
			qo = null;
		}
	}
	
	/**@autor jhernandez**/
	public void updateCalificationItem(CalificationItem calificationItem )
			throws Exception {
		StringBuilder hql = new StringBuilder();
		Query qo = null;
		try {
			
			if (calificationItem == null)
			{				
			return;	
			}
			
			hql.append(" UPDATE CalificationItem e Set name=:name, percentage=:percentage, dateChange=:dateChange, userChange=:userChange");
			hql.append(" Where idCalificationItem =:idCalificationItem ");
		
			qo = getSession().createQuery(hql.toString());
			
			qo.setParameter("name", calificationItem.getName());			
			qo.setParameter("dateChange", calificationItem.getDateChange());
			qo.setParameter("userChange", calificationItem.getUserChange());
			qo.setParameter("idCalificationItem", calificationItem.getIdCalificationItem());
			qo.setParameter("percentage", calificationItem.getPercentage());	

			qo.executeUpdate();
			
			
		} catch (Exception e) {
			throw e;
		} finally {
			hql = null;
			qo = null;
		}
	}	
	
	/**@autor jhernandez**/
	public void deleteCalificationItem(CalificationItem calificationItemToDelete)
			throws Exception {
		StringBuilder hql = new StringBuilder();
		Query qo = null;
		try {
			if (calificationItemToDelete == null  )
			{				
			return;	
			}
			
			hql.append(" UPDATE CalificationItem ci Set state=:state, dateChange=:dateChange, userChange=:userChange");
			hql.append(" Where idCalificationItem=:idCalificationItem");
					
			qo = getSession().createQuery(hql.toString());			
			qo.setParameter("idCalificationItem", calificationItemToDelete.getIdCalificationItem());	
			qo.setParameter("dateChange", calificationItemToDelete.getDateChange());
			qo.setParameter("userChange", calificationItemToDelete.getUserChange());	
			qo.setParameter("state", IZyosState.INACTIVE);

			qo.executeUpdate();
			
			
		} catch (Exception e) {
			throw e;
		} finally {
			hql = null;
			qo = null;
		}

	}
	/**@author ogarzonm**/
	public List<Long> loadCalificationItemByAcademicPeriod(Long idAcademicPeriod) throws Exception {
		try {
			StringBuilder hql = new StringBuilder();
			hql.append("select distinct ");
			hql.append("e.idMoodle ");
			hql.append("from ");
			hql.append("CalificationItem e ");
			hql.append("where ");
			hql.append("e.idMoodle is not null ");
			hql.append("and e.idAcademicPeriod =:idAP ");
			
			Query qo = getSession().createQuery(hql.toString());
			qo.setParameter("idAP", idAcademicPeriod);
			
			return qo.list();
		} catch (Exception e) {
			throw e;
		}
	}

	public void setIdEvaluationOnCalificationItemFromMoodle(
			Long idAcademicPeriod) throws Exception {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("update  zyos.calificationitem  ");
			sql.append("set idEvaluation=(select e.idEvaluation  from zyos.evaluation e ");
			sql.append("where ");
			sql.append("e.idMoodle=zyos.calificationitem.idMoodleEvaluation and e.idAcademicPeriod=:idAP) ");
			sql.append("where   ");
			sql.append("( idEvaluation is null )");
			sql.append(" and (idMoodleEvaluation is not null) and idAcademicPeriod=:idAP ");
			
			Query qo = getSession().createSQLQuery(sql.toString());
			qo.setParameter("idAP", idAcademicPeriod);
			
			qo.executeUpdate();
		} catch (Exception e) {
			throw e;
		}



	}
	
}