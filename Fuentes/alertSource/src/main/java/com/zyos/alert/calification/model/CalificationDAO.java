package com.zyos.alert.calification.model;

import static org.hibernate.criterion.Example.create;

import java.util.List;

import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.hibernate.type.DoubleType;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zyos.alert.studentReport.model.Student;
import com.zyos.alert.studentReport.model.Subject;
import com.zyos.core.common.api.IZyosState;
import com.zyos.core.connection.OracleBaseHibernateDAO;

/**
 * A data access object (DAO) providing persistence and search support for
 * Calification entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.zyos.alert.calification.model.Calification
 * @author MyEclipse Persistence Tools
 */
public class CalificationDAO extends OracleBaseHibernateDAO {
	private static final Logger log = LoggerFactory
			.getLogger(CalificationDAO.class);

	public void save(Calification transientInstance) {
		log.debug("saving Calification instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Calification persistentInstance) {
		log.debug("deleting Calification instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Calification findById(java.lang.Long id) {
		log.debug("getting Calification instance with id: " + id);
		try {
			Calification instance = (Calification) getSession().get(
					"com.zyos.alert.calification.model.Calification", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<Calification> findByExample(Calification instance) {
		log.debug("finding Calification instance by example");
		try {
			List<Calification> results = (List<Calification>) getSession()
					.createCriteria(
							"com.zyos.alert.calification.model.Calification")
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
		log.debug("finding Calification instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from Calification as model where model."
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
		log.debug("finding all Calification instances");
		try {
			String queryString = "from Calification";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Calification merge(Calification detachedInstance) {
		log.debug("merging Calification instance");
		try {
			Calification result = (Calification) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Calification instance) {
		log.debug("attaching dirty Calification instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Calification instance) {
		log.debug("attaching clean Calification instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	/** @autor jhernandez **/
	public void deleteCalificationsFromCalificationItem(
			CalificationItem calificationItemToDelete) throws Exception {
		StringBuilder hql = new StringBuilder();
		Query qo = null;
		try {

			hql.append(" UPDATE Calification c Set state=:state, dateChange=:dateChange, userChange=:userChange");
			hql.append(" Where idCalificationItem=:idCalificationItem");

			qo = getSession().createQuery(hql.toString());
			qo.setParameter("idCalificationItem",
					calificationItemToDelete.getIdCalificationItem());
			qo.setParameter("dateChange",
					calificationItemToDelete.getDateChange());
			qo.setParameter("userChange",
					calificationItemToDelete.getUserChange());
			qo.setParameter("state", IZyosState.INACTIVE);

			qo.executeUpdate();

		} catch (Exception e) {
			throw e;
		} finally {
			hql = null;
			qo = null;
		}

	}

	/** @autor jhernandez **/
	public void updateCalification(Calification calification) throws Exception {
		StringBuilder hql = new StringBuilder();
		Query qo = null;
		try {

			if (calification == null) {
				return;
			}

			hql.append(" UPDATE Calification e Set grade =:grade, dateChange=:dateChange, userChange=:userChange");
			hql.append(" Where idCalificationItem =:idCalificationItem AND idStudent =:idStudent ");

			qo = getSession().createQuery(hql.toString());

			qo.setParameter("grade", calification.getGrade());
			qo.setParameter("dateChange", calification.getDateChange());
			qo.setParameter("userChange", calification.getUserChange());
			qo.setParameter("idCalificationItem",
					calification.getIdCalificationItem());
			qo.setParameter("idStudent", calification.getIdStudent());

			qo.executeUpdate();

		} catch (Exception e) {
			throw e;
		} finally {
			hql = null;
			qo = null;
		}
	}

	/** @autor jhernandez **/
	public List<CalificationItem> loadCalificationNameList(
			Evaluation evaluationSelected) throws Exception {
		StringBuilder hql = new StringBuilder();
		Query qo = null;
		try {

			hql.append(" SELECT");
			hql.append(" ci.name,");
			hql.append(" ci.percentage,");
			hql.append(" c.idCalificationItem");
			hql.append(" FROM");
			hql.append(" Student s, ");
			hql.append(" ZyosUser zu,   ");
			hql.append(" Evaluation e,");
			hql.append(" CalificationItem ci,");
			hql.append(" Calification c");
			hql.append(" WHERE");
			hql.append(" s.idZyosUser = zu.idZyosUser  ");
			hql.append(" AND  e.idEvaluation =:idEvaluation");
			hql.append(" AND  e.idSubject =:idSubject ");
			hql.append(" AND  e.idGroupSubject =:idGroupSubject");
			hql.append(" AND  ci.idEvaluation = e.idEvaluation");
			hql.append(" AND  c.idStudent = s.idStudent ");
			hql.append(" AND  c.idCalificationItem = ci.idCalificationItem");
			hql.append(" AND  s.state =:state ");
			hql.append(" AND  zu.state =:state ");
			hql.append(" AND  s.state =:state");
			hql.append(" AND  e.state =:state");
			hql.append(" AND  ci.state =:state");
			hql.append(" GROUP BY ");
			hql.append(" ci.name, ci.percentage, c.idCalificationItem ");
			hql.append(" order by c.idCalificationItem");

			qo = getSession()
					.createSQLQuery(hql.toString())
					.addScalar("name", StringType.INSTANCE)
					.addScalar("percentage", DoubleType.INSTANCE)
					.addScalar("idCalificationItem", LongType.INSTANCE)
					.setResultTransformer(
							Transformers.aliasToBean(CalificationItem.class));

			qo.setParameter("state", IZyosState.ACTIVE);
			qo.setParameter("idEvaluation",
					evaluationSelected.getIdEvaluation());
			qo.setParameter("idGroupSubject",
					evaluationSelected.getIdGroupSubject());
			qo.setParameter("idSubject", evaluationSelected.getIdSubject());

			List<CalificationItem> result = qo.list();

			return result;

		} catch (Exception e) {
			throw e;
		} finally {
			hql = null;
			qo = null;
		}
	}

	/** @autor jhernandez **/
	public void deleteCalifications(Evaluation evaluationToDelete,
			List<Long> calificationItemIdsToDelete) throws Exception {
		StringBuilder hql = new StringBuilder();
		Query qo = null;
		try {
			if (calificationItemIdsToDelete == null
					|| calificationItemIdsToDelete.isEmpty()) {
				return;
			}

			hql.append(" UPDATE Calification c Set state=:state, dateChange=:dateChange, userChange=:userChange");
			hql.append(" Where idCalificationItem IN :calificationItemIdsToDelete");

			qo = getSession().createQuery(hql.toString());
			qo.setParameterList("calificationItemIdsToDelete",
					calificationItemIdsToDelete);
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

	/** @autor jhernandez **/

	public void deleteCalificationsFromDeleteStudent(Student studentToDelete,
			Subject subjectSelected, String currentDate) throws Exception {
		StringBuilder hql = new StringBuilder();
		Query qo = null;
		try {

			hql.append(" UPDATE ");
			hql.append(" Calification ");
			hql.append(" SET ");
			hql.append(" state=:state, ");
			hql.append(" userChange=:userChange, ");
			hql.append(" dateChange=:dateChange ");
			hql.append(" FROM ");
			hql.append(" Student, ");
			hql.append(" CalificationItem, ");
			hql.append(" Evaluation,  ");
			hql.append(" StudentSubject, ");
			hql.append(" AcademicPeriod  ");
			hql.append(" WHERE ");
			hql.append(" Calification.idStudent = Student.idStudent AND ");
			hql.append(" Calification.idStudent =:idStudent AND ");
			hql.append(" Calification.idCalificationItem = CalificationItem.idCalificationItem  AND ");
			hql.append(" CalificationItem.idEvaluation = Evaluation.idEvaluation AND ");
			hql.append(" Evaluation.idSubject = StudentSubject.idStudentSubject AND ");
			hql.append(" Evaluation.idSubject =:idSubject AND ");
			hql.append(" Evaluation.idGroupSubject = StudentSubject.idGroupSubject AND ");
			hql.append(" Evaluation.idGroupSubject =:idGroupSubject AND ");
			hql.append(" StudentSubject.idAcademicPeriod = AcademicPeriod.id AND ");
			hql.append(" :currentDate BETWEEN AcademicPeriod.startDate AND AcademicPeriod.endDate  ");
			hql.append(" AND CalificationItem.state =:stateA ");
			hql.append(" AND Evaluation.state =:stateA ");
			hql.append(" AND StudentSubject.state =:stateA ");
			hql.append(" AND AcademicPeriod.state =:stateA ");

			qo = getSession().createSQLQuery(hql.toString());
			qo.setParameter("idStudent", studentToDelete.getIdStudent());
			qo.setParameter("dateChange", studentToDelete.getDateChange());
			qo.setParameter("userChange", studentToDelete.getUserChange());
			qo.setParameter("currentDate", currentDate);
			qo.setParameter("idSubject", subjectSelected.getIdSubject());
			qo.setParameter("idGroupSubject", subjectSelected.getIdGroupSubject());
			qo.setParameter("state", IZyosState.INACTIVE);
			qo.setParameter("stateA", IZyosState.ACTIVE);

			qo.executeUpdate();

		} catch (Exception e) {
			throw e;
		} finally {
			hql = null;
			qo = null;
		}

	}

	public List<Long> loadCalificationByAcademicPeriod(Long idAcademicPeriod) throws Exception {
		try {
			StringBuilder hql = new StringBuilder();
			hql.append("select distinct ");
			hql.append("e.idMoodleItem ");
			hql.append("from ");
			hql.append("Calification e ");
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

	public void setIdStudentAndCalificationItemOnCalificationFromMoodleZyosUser(Long idAcademicPeriod) throws Exception {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("update zyos.calification  set ");
			sql.append("idStudent=(select s.idStudent  ");
			sql.append("from zyos.student s, zyos.zyosUser zu  ");
			sql.append("where zu.documentNumber=zyos.calification.observation  and zu.idZyosUser=s.idZyosUser and rownum = 1) ");
			sql.append("where idAcademicPeriod=:idAP ");
			sql.append("and ( idMoodle is not null ) ");
			
			Query qo = getSession().createSQLQuery(sql.toString());
			qo.setParameter("idAP", idAcademicPeriod);
			
			qo.executeUpdate();
		} catch (Exception e) {
			throw e;
		}
		
	}
	
	public void setIdStudentAndCalificationItemOnCalificationFromMoodleCalificationItem(Long idAcademicPeriod) throws Exception {
		try {
			StringBuilder sql = new StringBuilder();
			
			sql.append("update zyos.calification set idCalificationItem = (select ci.idCalificationItem ");
			sql.append("from  zyos.calificationitem ci  ");
			sql.append("where  ci.idMoodle=zyos.calification.idMoodle  and ci.idAcademicPeriod=:idAP and rownum = 1) ");
			sql.append("where idAcademicPeriod=:idAP ");
			sql.append("and ( idMoodle is not null ) ");

			Query qo = getSession().createSQLQuery(sql.toString());
			qo.setParameter("idAP", idAcademicPeriod);
			
			qo.executeUpdate();
		} catch (Exception e) {
			throw e;
		}
		
	}

}