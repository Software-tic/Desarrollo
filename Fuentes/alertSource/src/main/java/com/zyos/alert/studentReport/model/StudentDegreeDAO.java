package com.zyos.alert.studentReport.model;

import com.zyos.core.common.api.IZyosState;
import com.zyos.core.connection.OracleBaseHibernateDAO;
import java.util.List;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;

import static org.hibernate.criterion.Example.create;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A data access object (DAO) providing persistence and search support for
 * Studentdegree entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.zyos.alert.studentReport.model.StudentDegree
 * @author MyEclipse Persistence Tools
 */
public class StudentDegreeDAO extends OracleBaseHibernateDAO {
	private static final Logger log = LoggerFactory
			.getLogger(StudentDegreeDAO.class);

	public void save(StudentDegree transientInstance) {
		log.debug("saving Studentdegree instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(StudentDegree persistentInstance) {
		log.debug("deleting Studentdegree instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public StudentDegree findById(java.lang.Long id) {
		log.debug("getting Studentdegree instance with id: " + id);
		try {
			StudentDegree instance = (StudentDegree) getSession().get(
					"com.zyos.alert.studentReport.model.Studentdegree", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<StudentDegree> findByExample(StudentDegree instance) {
		log.debug("finding Studentdegree instance by example");
		try {
			List<StudentDegree> results = (List<StudentDegree>) getSession()
					.createCriteria(
							"com.zyos.alert.studentReport.model.Studentdegree")
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
		log.debug("finding Studentdegree instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from Studentdegree as model where model."
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
		log.debug("finding all Studentdegree instances");
		try {
			String queryString = "from Studentdegree";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public StudentDegree merge(StudentDegree detachedInstance) {
		log.debug("merging Studentdegree instance");
		try {
			StudentDegree result = (StudentDegree) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(StudentDegree instance) {
		log.debug("attaching dirty Studentdegree instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	/** @autor ogarzonm **/
	public List<StudentDegree> migrateStudentDegreeFromSAC() throws Exception {
		try {
            StringBuilder hql = new StringBuilder();
			hql.append("select s.idStudent as idStudent, cast(me.idCarrera as long) as idDegree ");
			hql.append("from CarrerasEstudiantes me, Student s, Degree d ");
			hql.append("where  ");
			hql.append("cast(me.idCarrera as long) = d.id and s.code = me.codAlumno and (me.codAlumno) not in   ");
			hql.append("( select s.code from  StudentDegree sd, Student s where sd.idStudent = s.idStudent and s.code is not null) ");

			Query qo = getSession().createQuery(hql.toString()).setResultTransformer(Transformers.aliasToBean(StudentDegree.class)); 
			
			return qo.list();
		} catch (Exception e) {
			throw e;
		}
	}
	
	
	/** @autor jhernandez **/
	public Long loadIdStudentDegree(Long idStudent) throws Exception {
		StringBuilder sql = new StringBuilder();
		Query qo = null;
		try {
			sql.append(" SELECT sd.idDegree From StudentDegree sd Where sd.idStudent=:idStudent ");
			sql.append(" AND sd.state = :state ");

			qo = getSession().createQuery(sql.toString());
			qo.setMaxResults(1);
			qo.setParameter("idStudent", idStudent);
			qo.setParameter("state", IZyosState.ACTIVE);

			return (Long) qo.uniqueResult();

		} catch (Exception e) {
			throw e;
		} finally {
			sql = null;
			qo = null;
		}
	}

}