package com.zyos.alert.faculty.model;

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
 * Faculty entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.zyos.alert.absent.model.FamilyStudent
 * @author MyEclipse Persistence Tools
 */
public class FacultyDAO extends OracleBaseHibernateDAO {
	private static final Logger log = LoggerFactory
			.getLogger(FacultyDAO.class);

	public void save(Faculty transientInstance) {
		log.debug("saving FamilyStudent instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Faculty persistentInstance) {
		log.debug("deleting FamilyStudent instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Faculty findById(java.lang.Long id) {
		log.debug("getting FamilyStudent instance with id: " + id);
		try {
			Faculty instance = (Faculty) getSession().get(
					"com.zyos.alert.absent.model.familyStudent", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
	
	public Faculty findFacultyById(java.lang.Long id) {
		log.debug("getting Faculty instance with id: " + id);
		try {
			Faculty instance = (Faculty) getSession().get(
					"com.zyos.alert.faculty.model.Faculty", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<Faculty> findByExample(Faculty instance) {
		log.debug("finding FamilyStudent instance by example");
		try {
			List<Faculty> results = (List<Faculty>) getSession()
					.createCriteria("com.zyos.alert.absent.model.faculty")
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
		log.debug("finding Faculty instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Faculty as model where model."
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
		log.debug("finding all Faculty instances");
		try {
			String queryString = "from Faculty";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Faculty merge(Faculty detachedInstance) {
		log.debug("merging FamilyStudent instance");
		try {
			Faculty result = (Faculty) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Faculty instance) {
		log.debug("attaching dirty Faculty instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Faculty instance) {
		log.debug("attaching clean Faculty instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
	
	/** SIAT-TUNJA */
	public List<Faculty> loadFacultyList() {
		StringBuilder hql = new StringBuilder();
		Query qo = null;
		try {
			hql.append(" FROM Faculty p ");
			hql.append(" WHERE p.state = :state ");

			qo = getSession().createQuery(hql.toString());		
			qo.setParameter("state", IZyosState.ACTIVE);
			return qo.list();
			
		} catch (RuntimeException re) {
			throw re;
		} finally {
			hql = null;
			qo = null;
		}
	}
	
	/** SIAT-TUNJA */
	public int deleteDivision(Long idFaculty) {
		StringBuilder hql = new StringBuilder();
		Query qo = null;
		try {
			hql.append(" UPDATE Faculty SET state=:state "
					+ " WHERE idFaculty = :idFaculty AND idFaculty NOT IN"
					+ " (SELECT f.idFaculty "
					+ " FROM Faculty f, FacultySchool fs, School s "
					+ " WHERE f.idFaculty = fs.idFaculty AND "
					+ " s.idschool = fs.idSchool "
					+ " GROUP BY f.idFaculty) "
					+ " AND idFaculty NOT IN "
					+ " (SELECT fc.idFaculty FROM FacultyCoordinator fc "
					+ " GROUP BY fc.idFaculty) ");

			qo = getSession().createQuery(hql.toString());
			qo.setParameter("idFaculty", idFaculty );
			qo.setParameter("state", IZyosState.INACTIVE );
			return qo.executeUpdate();
			
		} catch (RuntimeException re) {
			throw re;
		} finally {
			hql = null;
			qo = null;
		}
	}
	
	/** SIAT-TUNJA */
	public int updateDivision(Faculty faculty) {
		StringBuilder hql = new StringBuilder();
		Query qo = null;
		try {
			hql.append(" UPDATE Faculty SET name=:name WHERE idFaculty = :idFaculty ");

			qo = getSession().createQuery(hql.toString());
			qo.setParameter("idFaculty", faculty.getIdFaculty());
			qo.setParameter("name", faculty.getName());
			return qo.executeUpdate();
			
		} catch (RuntimeException re) {
			throw re;
		} finally {
			hql = null;
			qo = null;
		}
	}
	
	public Faculty findByNameDivision(Faculty faculty) {
		StringBuilder hql = new StringBuilder();
		Query qo = null;
		try {
			hql.append(" SELECT NEW Faculty(f.idFaculty,f.name) FROM Faculty f WHERE f.name=:name ");

			qo = getSession().createQuery(hql.toString());
			qo.setParameter("name", faculty.getName());
			return (Faculty)qo.uniqueResult();
			
		} catch (RuntimeException re) {
			throw re;
		} finally {
			hql = null;
			qo = null;
		}
	}
	
	public List<Faculty> findAllDivision() {
		StringBuilder hql = new StringBuilder();
		Query qo = null;
		try {
			hql.append(" SELECT NEW Faculty(f.idFaculty,f.name) FROM Faculty f WHERE f.state=:state ");

			qo = getSession().createQuery(hql.toString());
			qo.setParameter("state", IZyosState.ACTIVE);
			return qo.list();
			
		} catch (RuntimeException re) {
			throw re;
		} finally {
			hql = null;
			qo = null;
		}
	}
	
	public Long findMaxIdDivision() {
		StringBuilder hql = new StringBuilder();
		Query qo = null;
		try {
			hql.append(" SELECT MAX(f.idFaculty) FROM Faculty f ");

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