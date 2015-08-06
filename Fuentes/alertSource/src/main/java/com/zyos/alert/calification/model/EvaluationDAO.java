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
 * Evaluation entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.zyos.alert.calification.model.Evaluation
 * @author MyEclipse Persistence Tools
 */
public class EvaluationDAO extends OracleBaseHibernateDAO {
	private static final Logger log = LoggerFactory
			.getLogger(EvaluationDAO.class);

	public void save(Evaluation transientInstance) {
		log.debug("saving Evaluation instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Evaluation persistentInstance) {
		log.debug("deleting Evaluation instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Evaluation findById(java.lang.Long id) {
		log.debug("getting Evaluation instance with id: " + id);
		try {
			Evaluation instance = (Evaluation) getSession().get(
					"com.zyos.alert.calification.model.Evaluation", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<Evaluation> findByExample(Evaluation instance) {
		log.debug("finding Evaluation instance by example");
		try {
			List<Evaluation> results = getSession()
					.createCriteria(
							"com.zyos.alert.calification.model.Evaluation")
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
		log.debug("finding Evaluation instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Evaluation as model where model."
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
		log.debug("finding all Evaluation instances");
		try {
			String queryString = "from Evaluation";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Evaluation merge(Evaluation detachedInstance) {
		log.debug("merging Evaluation instance");
		try {
			Evaluation result = (Evaluation) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Evaluation instance) {
		log.debug("attaching dirty Evaluation instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Evaluation instance) {
		log.debug("attaching clean Evaluation instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
	
	/**@autor jhernandez**/
	public List<Evaluation> loadEvaluationList(Long idSubject, Long idGroupSubject)
			throws Exception {
		StringBuilder hql = new StringBuilder();
		Query qo = null;
		try {
			hql.append(" SELECT New Evaluation (");
			hql.append(" e.idEvaluation, e.idSubject, e.idGroupSubject, e.name, e.percentage ) ");
			hql.append(" From ");
			hql.append(" Evaluation e ");
			hql.append(" where ");			
			hql.append(" e.idSubject =:idSubject and ");
			hql.append(" e.idGroupSubject =:idGroupSubject and ");				
			hql.append(" e.state =:state ");		
			hql.append(" order by idEvaluation ");	

			qo = getSession().createQuery(hql.toString());			
			qo.setParameter("state", IZyosState.ACTIVE);
			qo.setParameter("idSubject", idSubject);
			qo.setParameter("idGroupSubject", idGroupSubject);

			List<Evaluation> result = qo.list();

			return result;

		} catch (Exception e) {
			throw e;
		} finally {
			hql = null;
			qo = null;
		}
	}
	
	/**@autor jhernandez**/
	public void updateEvaluationItem(Evaluation evaluationToEdit )
			throws Exception {
		StringBuilder hql = new StringBuilder();
		Query qo = null;
		try {
			hql.append(" UPDATE Evaluation e Set name=:name, percentage=:percentage, dateChange=:dateChange, userChange=:userChange");
			hql.append(" Where idEvaluation=:idEvaluation ");
		
			qo = getSession().createQuery(hql.toString());
			qo.setParameter("idEvaluation", evaluationToEdit.getIdEvaluation());			
			qo.setParameter("name", evaluationToEdit.getName());
			qo.setParameter("percentage", evaluationToEdit.getPercentage());
			qo.setParameter("dateChange", evaluationToEdit.getDateChange());
			qo.setParameter("userChange", evaluationToEdit.getUserChange());	

			qo.executeUpdate();
			
			
		} catch (Exception e) {
			throw e;
		} finally {
			hql = null;
			qo = null;
		}

	}
	

	/**@autor jhernandez**/
	public void deleteEvaluationItem(Evaluation evaluationToDelete )
			throws Exception {
		StringBuilder hql = new StringBuilder();
		Query qo = null;
		try {
			hql.append(" UPDATE Evaluation e Set state=:state, dateChange=:dateChange, userChange=:userChange");
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

	public List<Long> loadEvaluationByAcademicPeriod(Long idAcademicPeriod) throws Exception {
		try {
			StringBuilder hql = new StringBuilder();
			hql.append("select distinct ");
			hql.append("e.idMoodle ");
			hql.append("from ");
			hql.append("Evaluation e ");
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

	public void setIdSubjectAndGroupSubjectOnEvaluationFromMoodleSubject(
			Long idAcademicPeriod) throws Exception {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("update zyos.evaluation  ");
			sql.append("set idSubject=(select i2.idSubject from zyos.Integra i2, zyos.Subject s ");
			sql.append("where ");
			sql.append("i2.idCourseMoodle=zyos.evaluation.idMoodleCourse and zyos.evaluation.idAcademicPeriod=:idAP and rownum = 1 and s.idSubject = i2.idSubject) ");
			sql.append("where ");
			sql.append("(idMoodle is not null) and idAcademicPeriod=:idAP and idGroupSubject is null and idSubject is null  ");
			
			Query qo = getSession().createSQLQuery(sql.toString());
			qo.setParameter("idAP", idAcademicPeriod);
			
			qo.executeUpdate();
		} catch (Exception e) {
			throw e;
		}
	}
	
	public void setIdSubjectAndGroupSubjectOnEvaluationFromMoodleGroupSubject(
			Long idAcademicPeriod) throws Exception {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("update zyos.evaluation  ");
			sql.append("set idGroupSubject=(select i.idGroupSuject from zyos.Integra i  ");
			sql.append("where ");
			sql.append("i.idCourseMoodle=zyos.evaluation.idMoodleCourse and zyos.evaluation.idAcademicPeriod=:idAP and rownum = 1), ");
			sql.append("where ");
			sql.append("(idMoodle is not null) and idAcademicPeriod=:idAP and idGroupSubject is null and idSubject is null  ");
			
			Query qo = getSession().createSQLQuery(sql.toString());
			qo.setParameter("idAP", idAcademicPeriod);
			
			qo.executeUpdate();
		} catch (Exception e) {
			throw e;
		}
	}


}