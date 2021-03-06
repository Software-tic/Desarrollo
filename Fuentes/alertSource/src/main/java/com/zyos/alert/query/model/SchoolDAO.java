package com.zyos.alert.query.model;

import java.util.List;
import org.hibernate.LockOptions;
import org.hibernate.Query;
import static org.hibernate.criterion.Example.create;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zyos.core.common.api.IZyosState;
import com.zyos.core.connection.OracleBaseHibernateDAO;

/**
 * A data access object (DAO) providing persistence and search support for
 * School entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.zyos.alert.query.model.School
 * @author MyEclipse Persistence Tools
 */
public class SchoolDAO extends OracleBaseHibernateDAO {
	private static final Logger log = LoggerFactory.getLogger(SchoolDAO.class);
	// property constants
	public static final String NAME_SCHOOL = "nameSchool";
	public static final String DATECREATION = "datecreation";
	public static final String USERCREATION = "usercreation";
	public static final String DATECHANGE = "datechange";
	public static final String USERCHANGE = "userchange";
	public static final String STATE = "state";

	public void save(School transientInstance) {
		log.debug("saving School instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(School persistentInstance) {
		log.debug("deleting School instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public School findById(java.lang.Long id) {
		log.debug("getting School instance with id: " + id);
		try {
			School instance = (School) getSession().get(
					"com.zyos.alert.query.model.School", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<School> findByExample(School instance) {
		log.debug("finding School instance by example");
		try {
			List<School> results = getSession()
					.createCriteria("com.zyos.alert.query.model.School")
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
		log.debug("finding School instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from School as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<School> findByNameSchool(Object nameSchool) {
		return findByProperty(NAME_SCHOOL, nameSchool);
	}

	public List<School> findByDatecreation(Object datecreation) {
		return findByProperty(DATECREATION, datecreation);
	}

	public List<School> findByUsercreation(Object usercreation) {
		return findByProperty(USERCREATION, usercreation);
	}

	public List<School> findByDatechange(Object datechange) {
		return findByProperty(DATECHANGE, datechange);
	}

	public List<School> findByUserchange(Object userchange) {
		return findByProperty(USERCHANGE, userchange);
	}

	public List<School> findByState(Object state) {
		return findByProperty(STATE, state);
	}

	public List findAll() {
		log.debug("finding all School instances");
		try {
			String queryString = "from School WHERE state=:state";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter("state", IZyosState.ACTIVE);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public School merge(School detachedInstance) {
		log.debug("merging School instance");
		try {
			School result = (School) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(School instance) {
		log.debug("attaching dirty School instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(School instance) {
		log.debug("attaching clean School instance");
		try {
			getSession().buildLockRequest(LockOptions.NONE).lock(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
	
	public List<School> loadFacultyByDivision(Long idDivision){
		StringBuilder hql = new StringBuilder();
		Query qo = null;
		try {
			hql.append(" SELECT NEW School(s.idschool,s.nameSchool) FROM FacultySchool fs, School s "
					+ " WHERE fs.idSchool= s.idschool "
					+ " AND fs.idFaculty = :idFaculty "
					+ " AND fs.state = :state AND s.state = :state ");

			qo = getSession().createQuery(hql.toString());		
			qo.setParameter("idFaculty", idDivision);
			qo.setParameter("state", IZyosState.ACTIVE);
			return qo.list();
			
		} catch (RuntimeException re) {
			throw re;
		} finally {
			hql = null;
			qo = null;
		}
	}
	
	public int deleteFaculty(Long idSchool){
		StringBuilder hql = new StringBuilder();
		Query qo = null;
		try {
			hql.append(" UPDATE School SET state=:state WHERE idschool = :idschool ");

			qo = getSession().createQuery(hql.toString());		
			qo.setParameter("idschool", idSchool);
			qo.setParameter("state", IZyosState.INACTIVE);
			return qo.executeUpdate();
			
		} catch (RuntimeException re) {
			throw re;
		} finally {
			hql = null;
			qo = null;
		}
	}
	
	public int updateFaculty(School school){
		StringBuilder hql = new StringBuilder();
		Query qo = null;
		try {
			hql.append(" UPDATE School SET name=:name WHERE idschool = :idschool ");

			qo = getSession().createQuery(hql.toString());		
			qo.setParameter("idschool", school.getIdschool());
			qo.setParameter("name", school.getNameSchool());
			return qo.executeUpdate();
			
		} catch (RuntimeException re) {
			throw re;
		} finally {
			hql = null;
			qo = null;
		}
	}
	
	public School findByNameFaculty(School faculty) {
		StringBuilder hql = new StringBuilder();
		Query qo = null;
		try {
			hql.append(" SELECT NEW School(f.idschool,f.nameSchool) FROM School f WHERE f.nameSchool=:name ");

			qo = getSession().createQuery(hql.toString());
			qo.setParameter("name", faculty.getNameSchool());
			return (School)qo.uniqueResult();
			
		} catch (RuntimeException re) {
			throw re;
		} finally {
			hql = null;
			qo = null;
		}
	}
	
	public Long findMaxIDFaculty() {
		StringBuilder hql = new StringBuilder();
		Query qo = null;
		try {
			hql.append(" SELECT MAX(f.idschool) FROM School f ");

			qo = getSession().createQuery(hql.toString());
			return (Long)qo.uniqueResult();
			
		} catch (RuntimeException re) {
			throw re;
		} finally {
			hql = null;
			qo = null;
		}
	}
	
}