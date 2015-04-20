package com.zyos.alert.studentReport.model;

import com.zyos.alert.calification.model.CalificationitemDAO;
import com.zyos.alert.calification.model.Evaluation;
import com.zyos.core.common.api.IZyosGroup;
import com.zyos.core.common.api.IZyosState;
import com.zyos.core.connection.OracleBaseHibernateDAO;

import java.util.Collection;
import java.util.List;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;

import static org.hibernate.criterion.Example.create;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A data access object (DAO) providing persistence and search support for
 * Riskfactor entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.zyos.alert.studentReport.model.RiskFactor
 * @author MyEclipse Persistence Tools
 */
public class RiskFactorDAO extends OracleBaseHibernateDAO {
	private static final Logger log = LoggerFactory
			.getLogger(RiskFactorDAO.class);

	public void save(RiskFactor transientInstance) {
		log.debug("saving Riskfactor instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(RiskFactor persistentInstance) {
		log.debug("deleting Riskfactor instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public RiskFactor findById(java.lang.Long id) {
		log.debug("getting Riskfactor instance with id: " + id);
		try {
			RiskFactor instance = (RiskFactor) getSession().get(
					"com.zyos.alert.studentReport.model.Riskfactor", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<RiskFactor> findByExample(RiskFactor instance) {
		log.debug("finding Riskfactor instance by example");
		try {
			List<RiskFactor> results = (List<RiskFactor>) getSession()
					.createCriteria(
							"com.zyos.alert.studentReport.model.Riskfactor")
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
		log.debug("finding Riskfactor instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Riskfactor as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}


	public List<RiskFactor> findAll() throws Exception {
		StringBuilder hql = new StringBuilder();
		Query qo = null;
		try {
			hql.append(" select rf.idRiskFactor as idRiskFactor, rf.name as name, rf.description as description, rf.idRiskFactorCategory as idRiskFactorCategory, rc.name as nameRiskFactorCategory,  " );  
			hql.append(" rf.dateCreation as dateCreation, rf.dateChange as dateChange, rf.userCreation as userCreation, rf.userChange as userChange, rf.state as state  " ); 
			hql.append(" from RiskFactor rf, RiskFactorCategory rc  " ); 
			hql.append(" where rf.idRiskFactorCategory = rc.id AND " ); 
			hql.append(" rf.state =:state AND rc.state =:state ORDER BY rf.name " ); 

			qo = getSession().createSQLQuery(hql.toString())
				.addScalar("idRiskFactor", StandardBasicTypes.LONG)
				.addScalar("name", StandardBasicTypes.STRING)
				.addScalar("description", StandardBasicTypes.STRING)
				.addScalar("idRiskFactorCategory", StandardBasicTypes.LONG)
				.addScalar("nameRiskFactorCategory", StandardBasicTypes.STRING)
				.addScalar("dateCreation", StandardBasicTypes.STRING)
				.addScalar("dateChange", StandardBasicTypes.STRING)
				.addScalar("userCreation", StandardBasicTypes.STRING)
				.addScalar("userChange", StandardBasicTypes.STRING)
				.addScalar("idRiskFactor", StandardBasicTypes.LONG)
				
				.setResultTransformer(Transformers.aliasToBean(RiskFactor.class));
					
			qo.setParameter("state", IZyosState.ACTIVE);		
			 List<RiskFactor> result = qo.list();
			 return result;

		} catch (Exception e) {
			throw e;
		} finally {
			hql = null;
			qo = null;
		}
	}
	
	public List findAllRiskFactorCategory() {
		log.debug("finding all RiskFactorCategory instances");
		try {
			String queryString = "from RiskFactorCategory";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public RiskFactor merge(RiskFactor detachedInstance) {
		log.debug("merging Riskfactor instance");
		try {
			RiskFactor result = (RiskFactor) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(RiskFactor instance) {
		log.debug("attaching dirty Riskfactor instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(RiskFactor instance) {
		log.debug("attaching clean Riskfactor instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
	
	/**@autor jhernandez**/
	public RiskFactor validateRiskFactorUsed(Long idRiskFactor)
			throws Exception {
		StringBuilder hql = new StringBuilder();
		Query qo = null;
		try {
			
			hql.append(" SELECT distinct rf");					
			hql.append(" FROM ");
			hql.append(" ReportStudent r, RiskFactor rf ");			
			hql.append(" WHERE ");
			hql.append(" r.idRiskFactor = rf.idRiskFactor and ");		
			hql.append(" r.idRiskFactor =:idRiskFactor  ");			
			hql.append(" AND  r.state =:state ");
			hql.append(" AND  rf.state =:state ");
			
			qo = getSession().createQuery(hql.toString());								
			qo.setParameter("state", IZyosState.ACTIVE);
			qo.setParameter("idRiskFactor", idRiskFactor);
		

			RiskFactor result = (RiskFactor) qo.uniqueResult();

			return result;

		} catch (Exception e) {
			throw e;
		} finally {
			hql = null;
			qo = null;
		}
	}
	
	public void deleteRiskFactor(RiskFactor riskFactor) throws Exception {
		StringBuilder hql = new StringBuilder();
		Query qo;
		try {
			hql.append(" UPDATE RiskFactor SET state = :state ");
			hql.append(" WHERE idRiskFactor = :idRiskFactor ");
			qo = getSession().createQuery(hql.toString());
			qo.setParameter("idRiskFactor", riskFactor.getIdRiskFactor());
			qo.setParameter("state", IZyosState.INACTIVE);
			qo.executeUpdate();
		} catch (Exception e) {
			throw e;
		} finally {
			hql = null;
			qo = null;
		}
	}
	
	public List<RiskFactor> loadRiskFactorListByCategory(int idCategory) throws Exception {
		StringBuilder hql = new StringBuilder();
		Query qo = null;
		try {
			hql.append(" select rf.idRiskFactor as idRiskFactor, rf.name as name, rf.description as description, rf.idRiskFactorCategory as idRiskFactorCategory, " );  
			hql.append(" rf.dateCreation as dateCreation, rf.dateChange as dateChange, rf.userCreation as userCreation, rf.userChange as userChange, rf.state as state  " ); 
			hql.append(" from RiskFactor rf " ); 
			hql.append(" where rf.idRiskFactorCategory =:idCategory AND " ); 
			hql.append(" rf.state =:state ORDER BY rf.name " ); 

			qo = getSession().createSQLQuery(hql.toString())
				.addScalar("idRiskFactor", StandardBasicTypes.LONG)
				.addScalar("name", StandardBasicTypes.STRING)
				.addScalar("description", StandardBasicTypes.STRING)
				.addScalar("idRiskFactorCategory", StandardBasicTypes.LONG)
				.addScalar("dateCreation", StandardBasicTypes.STRING)
				.addScalar("dateChange", StandardBasicTypes.STRING)
				.addScalar("userCreation", StandardBasicTypes.STRING)
				.addScalar("userChange", StandardBasicTypes.STRING)
				.addScalar("idRiskFactor", StandardBasicTypes.LONG)
				
				.setResultTransformer(Transformers.aliasToBean(RiskFactor.class));
					
			qo.setParameter("state", IZyosState.ACTIVE);	
			qo.setParameter("idCategory", idCategory);
			 List<RiskFactor> result = qo.list();
			 return result;

		} catch (Exception e) {
			throw e;
		} finally {
			hql = null;
			qo = null;
		}
	}
	
	
	
}