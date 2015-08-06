package com.zyos.alert.absent.model;

import static org.hibernate.criterion.Example.create;

import java.util.List;

import org.hibernate.LockMode;
import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zyos.core.common.api.IZyosState;
import com.zyos.core.common.util.ManageDate;
import com.zyos.core.connection.OracleBaseHibernateDAO;

/**
 * A data access object (DAO) providing persistence and search support for
 * Absent entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.zyos.alert.absent.model.Absent
 * @author MyEclipse Persistence Tools
 */
public class AbsentDAO extends OracleBaseHibernateDAO {
	private static final Logger log = LoggerFactory.getLogger(AbsentDAO.class);

	public void save(Absent transientInstance) {
		log.debug("saving Absent instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Absent persistentInstance) {
		log.debug("deleting Absent instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Absent findById(java.lang.Long id) {
		log.debug("getting Absent instance with id: " + id);
		try {
			Absent instance = (Absent) getSession().get(
					"com.zyos.alert.absent.model.Absent", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<Absent> findByExample(Absent instance) {
		log.debug("finding Absent instance by example");
		try {
			List<Absent> results = getSession()
					.createCriteria("com.zyos.alert.absent.model.Absent")
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
		log.debug("finding Absent instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Absent as model where model."
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
		log.debug("finding all Absent instances");
		try {
			String queryString = "from Absent";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Absent merge(Absent detachedInstance) {
		log.debug("merging Absent instance");
		try {
			Absent result = (Absent) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Absent instance) {
		log.debug("attaching dirty Absent instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Absent instance) {
		log.debug("attaching clean Absent instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
	
	/** jhernandez */
	public List<Absent> loadStudentAbsentList(List<Long> idStudentSubjectList) throws Exception {
		StringBuilder hql = new StringBuilder();
		Query qo = null;
		try {
			hql.append(" SELECT New Absent");
			hql.append(" (ab.idAbsent, ab.idZyosUser, ab.idStudentSubject, ab.dateAbsent, zu.name, zu.lastName, zu.documentNumber, s.code )");
			hql.append(" FROM ");
			hql.append(" Absent ab, StudentSubject ss, Student s, ZyosUser zu, AcademicPeriod a ");			
			hql.append(" WHERE ");	
			
			hql.append(" ab.idStudentSubject IN (:studentList)");			
			hql.append(" AND ab.idStudentSubject = ss.idStudentSubject ");
			hql.append(" AND ss.idStudent = s.idStudent ");
			hql.append(" AND s.idZyosUser = zu.idZyosUser ");
			hql.append(" AND ss.idAcademicPeriod = a.id");
			hql.append(" AND :currentDate BETWEEN a.startDate AND a.endDate ");
			hql.append(" AND ab.state = :state");
			hql.append(" AND ss.state = :state");			
			hql.append(" AND s.state = :state");
			hql.append(" AND a.state = :state ");
			hql.append(" AND zu.state = :state ");
			hql.append(" order by ab.dateAbsent Desc ");
			
			qo = getSession().createQuery(hql.toString());
			qo.setParameterList("studentList", idStudentSubjectList);
			qo.setParameter("currentDate", ManageDate.getCurrentDate(ManageDate.YYYY_MM_DD));
			qo.setParameter("state", IZyosState.ACTIVE);

			List <Absent> result = qo.list();
			
			
			return result;
		} catch (Exception e) {
			throw e;
		} finally {
			hql = null;
			qo = null;
		}
	}
	
	/**@autor jhernandez**/
	public void deleteAbsent(Absent absentToDelete)
			throws Exception {
		StringBuilder hql = new StringBuilder();
		Query qo = null;
		try {
						
			hql.append(" UPDATE Absent ss Set state=:state, dateChange=:dateChange, userChange=:userChange, observationAbsent =:observationAbsent");
			hql.append(" Where idAbsent=:idStudentSubject ");
							
			qo = getSession().createQuery(hql.toString());			
			qo.setParameter("idStudentSubject", absentToDelete.getIdAbsent());	
			qo.setParameter("dateChange", absentToDelete.getDateChange());
			qo.setParameter("userChange", absentToDelete.getUserChange());
			qo.setParameter("observationAbsent", absentToDelete.getObservationAbsent());
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