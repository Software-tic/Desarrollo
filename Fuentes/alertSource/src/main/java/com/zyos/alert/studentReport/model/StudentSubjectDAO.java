package com.zyos.alert.studentReport.model;

import com.zyos.core.common.api.IZyosState;
import com.zyos.core.connection.OracleBaseHibernateDAO;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;

import static org.hibernate.criterion.Example.create;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A data access object (DAO) providing persistence and search support for
 * Studentsubject entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.zyos.alert.studentReport.model.StudentSubject
 * @author MyEclipse Persistence Tools
 */
public class StudentSubjectDAO extends OracleBaseHibernateDAO {
	private static final Logger log = LoggerFactory
			.getLogger(StudentSubjectDAO.class);

	public void save(StudentSubject transientInstance) {
		log.debug("saving Studentsubject instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(StudentSubject persistentInstance) {
		log.debug("deleting Studentsubject instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public StudentSubject findById(java.lang.Long id) {
		log.debug("getting Studentsubject instance with id: " + id);
		try {
			StudentSubject instance = (StudentSubject) getSession().get(
					"com.zyos.alert.studentReport.model.Studentsubject", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<StudentSubject> findByExample(StudentSubject instance) {
		log.debug("finding Studentsubject instance by example");
		try {
			List<StudentSubject> results = getSession()
					.createCriteria(
							"com.zyos.alert.studentReport.model.Studentsubject")
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
		log.debug("finding Studentsubject instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from Studentsubject as model where model."
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
		log.debug("finding all Studentsubject instances");
		try {
			String queryString = "from Studentsubject";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public StudentSubject merge(StudentSubject detachedInstance) {
		log.debug("merging Studentsubject instance");
		try {
			StudentSubject result = (StudentSubject) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(StudentSubject instance) {
		log.debug("attaching dirty Studentsubject instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	
	/**@author jhernandez**/
	public void deleteStudentSubject(Student studentToDelete)
			throws Exception {
		StringBuilder hql = new StringBuilder();
		Query qo = null;
		try {
						
			hql.append(" UPDATE StudentSubject ss Set state=:state, dateChange=:dateChange, userChange=:userChange");
			hql.append(" Where idStudentSubject=:idStudentSubject ");
							
			qo = getSession().createQuery(hql.toString());			
			qo.setParameter("idStudentSubject", studentToDelete.getIdStudentSubject());	
			qo.setParameter("dateChange", studentToDelete.getDateChange());
			qo.setParameter("userChange", studentToDelete.getUserChange());
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
	public List<StudentSubject> migrateStudentSubjectListFromSAC(Long idAcademicPeriod) throws Exception {
		try {
			StringBuilder hql = new StringBuilder();
			hql.append("select gs.idGroupSubject as idGroupSubject, s.idStudent as idStudent, sb.idSubject as idSubject,");
			hql.append("nrs.idSAC as  idSAC ");
			hql.append("from   ");
			hql.append("NotRecordStudentSubject nrs, Student s, GroupSubject gs, Subject sb ");
			hql.append("where  ");
			hql.append("nrs.idStudent = s.idStudentSAC  ");
			hql.append("and nrs.idGroupSubject = gs.idGroupSubject  ");
			hql.append("and cast(nrs.idSubject as long) = sb.idSubject ");

			Query qo = getSession().createQuery(hql.toString()).setResultTransformer(Transformers.aliasToBean(StudentSubject.class)); 
			
			return qo.list();
		} catch (Exception e) {
			throw e;
		}
	}


}