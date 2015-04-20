package com.zyos.alert.absent.model;

import com.zyos.alert.studentReport.model.Student;
import com.zyos.alert.studentReport.model.StudentSubject;
import com.zyos.alert.studentReport.model.Subject;
import com.zyos.core.common.api.IZyosState;
import com.zyos.core.connection.OracleBaseHibernateDAO;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;

import static org.hibernate.criterion.Example.create;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A data access object (DAO) providing persistence and search support for
 * Dayclass entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.zyos.alert.absent.model.DayClass
 * @author MyEclipse Persistence Tools
 */
public class DayClassDAO extends OracleBaseHibernateDAO {
	private static final Logger log = LoggerFactory
			.getLogger(DayClassDAO.class);

	public void save(DayClass transientInstance) {
		log.debug("saving Dayclass instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(DayClass persistentInstance) {
		log.debug("deleting Dayclass instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public DayClass findById(java.lang.Long id) {
		log.debug("getting Dayclass instance with id: " + id);
		try {
			DayClass instance = (DayClass) getSession().get(
					"com.zyos.alert.absent.model.Dayclass", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<DayClass> findByExample(DayClass instance) {
		log.debug("finding Dayclass instance by example");
		try {
			List<DayClass> results = (List<DayClass>) getSession()
					.createCriteria("com.zyos.alert.absent.model.Dayclass")
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
		log.debug("finding Dayclass instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Dayclass as model where model."
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
		log.debug("finding all Dayclass instances");
		try {
			String queryString = "from Dayclass";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public DayClass merge(DayClass detachedInstance) {
		log.debug("merging Dayclass instance");
		try {
			DayClass result = (DayClass) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(DayClass instance) {
		log.debug("attaching dirty Dayclass instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(DayClass instance) {
		log.debug("attaching clean Dayclass instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public DayClass validateDayClass(Long currentDay, Long idStudent,
			Long idSubject, Long idGroupSubject, String today, String hour)
			throws Exception {
		StringBuilder hql = new StringBuilder();
		Query qo = null;
		try {

			hql.append(" select new DayClass (d.idDayClass, d.idDayCalendar, d.hourStart, d.hourEnd )");
			hql.append(" from ");
			hql.append(" DayClass d, StudentSubject ss, AcademicPeriod a ");
			hql.append(" where ");
			hql.append(" d.idDayCalendar =:currentDay and ");
			hql.append(" d.idStudentSubject = ss.idStudentSubject and ");
			hql.append(" ss.idStudent =:idStudent and ");
			hql.append(" ss.idSubject =:idSubject and ");
			hql.append(" ss.idGroupSubject =:idGroupSubject and ");
			hql.append(" (:today BETWEEN a.startDate AND a.endDate ) and  ");
			hql.append(" (:hour BETWEEN d.hourStart AND d.hourEnd ) and ");
			hql.append(" d.state=:state and  ");
			hql.append(" ss.state=:state and  ");
			hql.append(" a.state=:state ");

			qo = getSession().createQuery(hql.toString());
			qo.setParameter("idGroupSubject", idGroupSubject);
			qo.setParameter("currentDay", currentDay);
			qo.setParameter("idStudent", idStudent);
			qo.setParameter("idSubject", idSubject);
			qo.setParameter("today", today);
			qo.setParameter("hour", hour);
			qo.setParameter("state", IZyosState.ACTIVE);

			DayClass result = (DayClass) qo.uniqueResult();

			return result;

		} catch (Exception e) {
			throw e;
		} finally {
			hql = null;
			qo = null;
		}

	}

	public List<DayClass> loadDayClassList(Long idStudent, Long idSubject,
			Long idGroupSubject, String today) throws Exception {
		StringBuilder hql = new StringBuilder();
		Query qo = null;
		try {

			hql.append(" Select NEW DayClass (d.idDayClass, d.idDayCalendar, d.hourStart, d.hourEnd, ss.idStudent )");
			hql.append(" from ");
			hql.append(" DayClass d, StudentSubject ss, AcademicPeriod a ");
			hql.append(" where ");
			hql.append(" d.idStudentSubject = ss.idStudentSubject and ");
			hql.append(" ss.idStudent =:idStudent and ");
			hql.append(" ss.idSubject =:idSubject and ");
			hql.append(" ss.idGroupSubject =:idGroupSubject and ");
			hql.append(" (:today BETWEEN a.startDate AND a.endDate ) and  ");
			hql.append(" d.state=:state and  ");
			hql.append(" ss.state=:state and  ");
			hql.append(" a.state=:state ");
			hql.append(" order by idDayCalendar ");

			qo = getSession().createQuery(hql.toString());
			qo.setParameter("idGroupSubject", idGroupSubject);
			qo.setParameter("idStudent", idStudent);
			qo.setParameter("idSubject", idSubject);
			qo.setParameter("today", today);
			qo.setParameter("state", IZyosState.ACTIVE);

			ArrayList<DayClass> result = (ArrayList<DayClass>) qo.list();

			return result;

		} catch (Exception e) {
			throw e;
		} finally {
			hql = null;
			qo = null;
		}

	}

	public List<DayClass> loadDayClassListTeacher(Long idSubject,
			Long idGroupSubject, String today)
			throws Exception {
		StringBuilder hql = new StringBuilder();
		Query qo = null;
		try {

			hql.append(" SELECT new DayClass( ");
			hql.append(" d.idDayClass, d.idDayCalendar, d.hourStart, d.hourEnd, ss.idStudent ) ");
			hql.append(" FROM ");
			hql.append(" TeacherSubject ts, ");
			hql.append(" DayClass d, ");
			hql.append(" StudentSubject ss, ");
			hql.append(" AcademicPeriod a ");
			hql.append(" WHERE ");
			hql.append(" ts.idSubject = ss.idSubject and ");
			hql.append(" ts.idAcademicPeriod = a.id and ");
			hql.append(" ts.idGroupSubject = ss.idGroupSubject  and ");
			hql.append(" d.idStudentSubject = ss.idStudentSubject and ");
			hql.append(" ss.idSubject = :idSubject and ");
			hql.append(" ss.idGroupSubject = :idGroupSubject and ");
			hql.append(" (:today BETWEEN a.startDate AND a.endDate ) ");
			hql.append(" AND d.state=:state ");
			hql.append(" AND ss.state=:state ");
			hql.append(" AND a.state=:state ");
			hql.append(" AND ts.state=:state ");
			hql.append(" ORDER BY ");
			hql.append(" idDayCalendar ");

			qo = getSession().createQuery(hql.toString());                                       
			qo.setParameter("idGroupSubject", idGroupSubject);
			qo.setParameter("idSubject", idSubject);
			qo.setParameter("today", today);			
			qo.setParameter("state", IZyosState.ACTIVE);

			return  qo.list();

		} catch (Exception e) {
			throw e;
		} finally {
			hql = null;
			qo = null;
		}
	}

	public List<DayClass> migrateDayClassFromSAC() throws Exception {
		try {
			StringBuilder hql = new StringBuilder();
			hql.append("select nrs.idSAC as idSAC, nrs.hourEnd as hourEnd, nrs.hourStart as hourStart, ss.idStudentSubject as idStudentSubject, nrs.idDayCalendar as idDayCalendar ");
			hql.append("from    ");
			hql.append("NotRecordStudentSubjectDay nrs, StudentSubject ss, DayCalendar gs, Student s ");
			hql.append("where   ");
			hql.append("nrs.idStudent = s.idStudentSAC   ");
			hql.append("and s.idStudent = ss.idStudent ");
			hql.append("and nrs.idSubject = ss.idSubject   ");
			hql.append("and gs.id = nrs.idDayCalendar ");

			Query qo = getSession().createQuery(hql.toString()).setResultTransformer(Transformers.aliasToBean(DayClass.class)); 
			
			return qo.list();
		} catch (Exception e) {
			throw e;
		}
	}

}
