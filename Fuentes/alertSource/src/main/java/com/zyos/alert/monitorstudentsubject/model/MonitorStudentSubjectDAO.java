package com.zyos.alert.monitorstudentsubject.model;

import static org.hibernate.criterion.Example.create;

import java.util.List;

import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zyos.alert.studentReport.model.Subject;
import com.zyos.core.common.api.IZyosState;
import com.zyos.core.common.util.ManageDate;
import com.zyos.core.connection.OracleBaseHibernateDAO;

/**
 * A data access object (DAO) providing persistence and search support for MonitorStudentSubject
 * entities. Transaction control of the save(), update() and delete() operations can directly
 * support Spring container-managed transactions or they can be augmented to handle user-managed
 * Spring transactions. Each of these methods provides additional information for how to configure
 * it for the desired type of transaction control.
 * 
 * @see com.zyos.alert.absent.model.MonitorStudentSubject
 * @author MyEclipse Persistence Tools
 */
public class MonitorStudentSubjectDAO extends OracleBaseHibernateDAO {
	private static final Logger log = LoggerFactory.getLogger(MonitorStudentSubjectDAO.class);

	public void save(MonitorStudentSubject transientInstance) {
		log.debug("saving MonitorStudentSubject instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(MonitorStudentSubject persistentInstance) {
		log.debug("deleting MonitorStudentSubject instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public MonitorStudentSubject findById(java.lang.Long id) {
		log.debug("getting MonitorStudentSubject instance with id: " + id);
		try {
			MonitorStudentSubject instance = (MonitorStudentSubject) getSession().get("com.zyos.alert.absent.model.MonitorStudentSubject", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<MonitorStudentSubject> findByExample(MonitorStudentSubject instance) {
		log.debug("finding MonitorStudentSubject instance by example");
		try {
			List<MonitorStudentSubject> results =
				getSession().createCriteria("com.zyos.alert.absent.model.MonitorStudentSubject").add(create(instance))
				.list();
			log.debug("find by example successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding MonitorStudentSubject instance with property: " + propertyName + ", value: " + value);
		try {
			String queryString = "from MonitorStudentSubject as model where model." + propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findAll() {
		log.debug("finding all MonitorStudentSubject instances");
		try {
			String queryString = "from MonitorStudentSubject";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public MonitorStudentSubject merge(MonitorStudentSubject detachedInstance) {
		log.debug("merging MonitorStudentSubject instance");
		try {
			MonitorStudentSubject result = (MonitorStudentSubject) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(MonitorStudentSubject instance) {
		log.debug("attaching dirty MonitorStudentSubject instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(MonitorStudentSubject instance) {
		log.debug("attaching clean MonitorStudentSubject instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	/** @autor jhernandez **/
	public MonitorStudentSubject validateMoreOneMonitor(Subject subjectSelected) throws Exception {
		StringBuilder hql = new StringBuilder();
		Query qo = null;
		try {

			hql.append(" SELECT");
			hql.append(" m.idMonitorStudentSubject, m.idStudentSubject, zu.name, zu.lastName");
			hql.append(" FROM ");
			hql.append(" MonitorStudentSubject m, StudentSubject ss, Student s, ZyosUser zu, AcademicPeriod a");
			hql.append(" WHERE ");
			hql.append(" m.idStudentSubject = ss.idStudentSubject AND ");
			hql.append(" ss.idSubject =:idSubject AND ");
			hql.append(" ss.idGroupSubject =:idGroupSubject AND ss.idStudent = s.idStudent AND s.idZyosUser = zu.idZyosUser AND ");
			hql.append(" ss.idAcademicPeriod = a.id AND  ");
			hql.append(" :currentDate BETWEEN a.startDate AND a.endDate AND ");
			hql.append(" m.state =:state ");
			hql.append(" AND ss.state =:state ");
			hql.append(" AND a.state =:state ");
			hql.append(" AND s.state =:state ");
			hql.append(" AND zu.state =:state ");

			qo =
				getSession().createSQLQuery(hql.toString()).addScalar("idMonitorStudentSubject", StandardBasicTypes.LONG)
					.addScalar("idStudentSubject", StandardBasicTypes.LONG).addScalar("name", StandardBasicTypes.STRING)
					.addScalar("lastName", StandardBasicTypes.STRING).setResultTransformer(Transformers.aliasToBean(MonitorStudentSubject.class));
			qo.setMaxResults(1);

			qo.setParameter("idSubject", subjectSelected.getIdSubject());
			qo.setParameter("idGroupSubject", subjectSelected.getIdGroupSubject());
			qo.setParameter("currentDate", ManageDate.getCurrentDate(ManageDate.YYYY_MM_DD));
			qo.setParameter("state", IZyosState.ACTIVE);

			return (MonitorStudentSubject) qo.uniqueResult();

		} catch (Exception e) {
			throw e;
		} finally {
			hql = null;
			qo = null;
		}
	}


	/** @autor jhernandez **/
	public void deleteMonitorStudentCurrent(MonitorStudentSubject monitor, Subject subjectSelected) throws Exception {
		StringBuilder hql = new StringBuilder();
		Query qo = null;
		try {

			hql.append(" update MonitorStudentSubject set state =:state, userChange=:userChange, dateChange=:dateChange ");
			hql.append(" From StudentSubject, AcademicPeriod ");
			hql.append(" where ");
			hql.append(" MonitorStudentSubject.idStudentSubject =:idStudentSubject AND ");
			hql.append(" StudentSubject.idSubject =:idSubject AND ");
			hql.append(" StudentSubject.idGroupSubject =:idGroupSubject AND ");
			hql.append(" StudentSubject.idAcademicPeriod = AcademicPeriod.id AND  ");
			hql.append(" :currentDate BETWEEN AcademicPeriod.startDate AND AcademicPeriod.endDate AND ");
			hql.append(" MonitorStudentSubject.state =:stateA AND ");
			hql.append(" StudentSubject.state =:stateA AND ");
			hql.append(" AcademicPeriod.state =:stateA ");

			qo = getSession().createSQLQuery(hql.toString());
			qo.setParameter("idStudentSubject", monitor.getIdStudentSubject());
			qo.setParameter("dateChange", monitor.getDateChange());
			qo.setParameter("userChange", monitor.getUserChange());
			qo.setParameter("currentDate", ManageDate.getCurrentDate(ManageDate.YYYY_MM_DD));
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

	/** @autor jhernandez **/
	public void deleteMonitorStudent(MonitorStudentSubject monitor) throws Exception {
		StringBuilder hql = new StringBuilder();
		Query qo = null;
		try {

			hql.append(" update MonitorStudentSubject set state =:state, userChange=:userChange, dateChange=:dateChange ");
			hql.append(" where ");
			hql.append(" idMonitorStudentSubject =:id ");
			qo = getSession().createSQLQuery(hql.toString());

			qo.setParameter("id", monitor.getIdMonitorStudentSubject());
			qo.setParameter("dateChange", monitor.getDateChange());
			qo.setParameter("userChange", monitor.getUserChange());
			qo.setParameter("state", IZyosState.INACTIVE);


			qo.executeUpdate();

		} catch (Exception e) {
			throw e;
		} finally {
			hql = null;
			qo = null;
		}

	}

}
