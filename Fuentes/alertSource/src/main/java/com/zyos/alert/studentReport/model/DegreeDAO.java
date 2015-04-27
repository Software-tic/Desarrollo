package com.zyos.alert.studentReport.model;

import com.zyos.core.common.api.IZyosState;
import com.zyos.core.common.util.ManageDate;
import com.zyos.core.connection.OracleBaseHibernateDAO;
import com.zyos.core.lo.user.model.ZyosUser;

import java.util.List;

import org.hibernate.LockMode;
import org.hibernate.Query;

import static org.hibernate.criterion.Example.create;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A data access object (DAO) providing persistence and search support for
 * Degree entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.zyos.alert.studentReport.model.Degree
 * @author MyEclipse Persistence Tools
 */
public class DegreeDAO extends OracleBaseHibernateDAO {
	private static final Logger log = LoggerFactory.getLogger(DegreeDAO.class);

	public void save(Degree transientInstance) {
		log.debug("saving Degree instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Degree persistentInstance) {
		log.debug("deleting Degree instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Degree findById(java.lang.Long id) {
		log.debug("getting Degree instance with id: " + id);
		try {
			Degree instance = (Degree) getSession().get(
					"com.zyos.alert.studentReport.model.Degree", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<Degree> findByExample(Degree instance) {
		log.debug("finding Degree instance by example");
		try {
			List<Degree> results = (List<Degree>) getSession()
					.createCriteria("com.zyos.alert.studentReport.model.Degree")
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
		log.debug("finding Degree instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Degree as model where model."
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
		log.debug("finding all Degree instances");
		try {
			String queryString = "from Degree";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Degree merge(Degree detachedInstance) {
		log.debug("merging Degree instance");
		try {
			Degree result = (Degree) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Degree instance) {
		log.debug("attaching dirty Degree instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Degree instance) {
		log.debug("attaching clean Degree instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
	
	
	/**@autor jhernandez**/
	public List<String> validateDegreeUsed(List<Long> idDegree)
			throws Exception {
		StringBuilder hql = new StringBuilder();
		Query qo = null;
		try {
			
			hql.append(" SELECT distinct ");
			hql.append(" d.name ");			
			hql.append(" FROM ");
			hql.append(" StudentDegree sd, Degree d ");			
			hql.append(" WHERE ");
			hql.append(" sd.idDegree = d.id and ");		
			hql.append(" sd.idDegree IN :idDegree ");			
			hql.append(" AND  sd.state =:state ");
			hql.append(" AND  d.state =:state ");
			
			qo = getSession().createQuery(hql.toString());								
			qo.setParameter("state", IZyosState.ACTIVE);
			qo.setParameterList("idDegree", idDegree);
		

			List<String> result = qo.list();

			return result;

		} catch (Exception e) {
			throw e;
		} finally {
			hql = null;
			qo = null;
		}
	}
	
	public List<Degree> loadDegreeList() {
		StringBuilder hql = new StringBuilder();
		Query qo = null;
		try {
			hql.append(" FROM Degree p ");
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
	
	/*public List<Degree> loadDegreeList() throws Exception {
		StringBuilder hql = new StringBuilder();
		Query qo = null;
		try {
			hql.append("select new Degree( d.id, d.name, d.description, f.idfaculty, f.name )"
			+ " from zyos.degree d, zyos.faculty f, zyos.facultydegree fd "
			+ "where fd.idfaculty=f.idfaculty AND fd.iddegree=d.id "
			+ "AND f.state=1 AND fd.state=:state AND d.state=:state");

			qo = getSession().createQuery(hql.toString());

			qo.setParameter("state", IZyosState.ACTIVE);

			return qo.list();
		} catch (Exception e) {
			throw e;
		} finally {
			hql = null;
			qo = null;
		}
	}*/
	
	public void changeStateDegree(String idDegreeList, String documentNumber,Long state) {
		StringBuilder hql = new StringBuilder();
		Query qo = null;
		try {
			hql.append("update Degree ");
			hql.append("set state = :state ,");
			hql.append("userChange = :userChange ,");
			hql.append("dateChange = :newdateChange ");
			hql.append("where idFacultyDegree in (");
			hql.append(idDegreeList);
			hql.append(" )");

			qo = getSession().createQuery(hql.toString());

			qo.setParameter("state", state);
			qo.setParameter("userChange", documentNumber);
			qo.setParameter("newdateChange",ManageDate.getCurrentDate(ManageDate.YYYY_MM_DD_HH_MM_SS));

			qo.executeUpdate();
		} catch (RuntimeException e) {
			throw e;
		} finally {
			hql = null;
			qo = null;
		}
	}
	
}