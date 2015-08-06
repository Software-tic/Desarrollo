package com.zyos.alert.facultyCoordinator.model;

import static org.hibernate.criterion.Example.create;

import java.util.List;

import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zyos.core.common.api.IZyosState;
import com.zyos.core.connection.OracleBaseHibernateDAO;
import com.zyos.core.lo.user.model.ZyosUser;

/**
 * A data access object (DAO) providing persistence and search support for
 * FacultyCoordinator entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.zyos.alert.absent.model.FamilyStudent
 * @author MyEclipse Persistence Tools
 */
public class FacultyCoordinatorDAO extends OracleBaseHibernateDAO {
	private static final Logger log = LoggerFactory
			.getLogger(FacultyCoordinatorDAO.class);

	public void save(FacultyCoordinator transientInstance) {
		log.debug("saving FacultyCoordinator instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(FacultyCoordinator persistentInstance) {
		log.debug("deleting FacultyCoordinator instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public FacultyCoordinator findById(java.lang.Long id) {
		log.debug("getting FamilyStudent instance with id: " + id);
		try {
			FacultyCoordinator instance = (FacultyCoordinator) getSession().get(
					"com.zyos.alert.absent.model.facultyCoordinator", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<FacultyCoordinator> findByExample(FacultyCoordinator instance) {
		log.debug("finding FamilyStudent instance by example");
		try {
			List<FacultyCoordinator> results = getSession()
					.createCriteria("com.zyos.alert.absent.model.facultyCoordinator")
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
		log.debug("finding FacultyCoordinator instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from FacultyCoordinator as model where model."
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
		log.debug("finding all FacultyCoordinator instances");
		try {
			String queryString = "from FacultyCoordinator";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public FacultyCoordinator merge(FacultyCoordinator detachedInstance) {
		log.debug("merging FacultyCoordinator instance");
		try {
			FacultyCoordinator result = (FacultyCoordinator) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(FacultyCoordinator instance) {
		log.debug("attaching dirty FacultyCoordinator instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(FacultyCoordinator instance) {
		log.debug("attaching clean FacultyCoordinator instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void clearFacultyCoordinatorTable() {
		try {
			Query qo = getSession().createQuery("delete from FacultyCoordinator ");
			qo.executeUpdate();
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public List<String> validateUserExist() {
		try {
			StringBuilder hql = new StringBuilder();
			hql.append("select zu.email ");
			hql.append("from ");
			hql.append("DecanoFacultad df, ZyosUser zu ");
			hql.append("where ");
			hql.append("df.mail = zu.email ");
			hql.append("and zu.state =:state ");
			Query qo = getSession().createQuery(hql.toString());
			qo.setParameter("state", IZyosState.ACTIVE);
			
			return qo.list();
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public List<ZyosUser> createZyosUser(List<String> idList) {
		try {
			StringBuilder hql = new StringBuilder();
			hql.append("select  ");
			hql.append("cast(df.idDecano as string) as documentNumber, df.nomDecano as name, df.mail as email ");
			hql.append("from  ");
			hql.append("DecanoFacultad df ");
			hql.append("where ");
			hql.append("df.mail not in (:idList) ");

			Query qo = getSession().createQuery(hql.toString()).setResultTransformer(Transformers.aliasToBean(ZyosUser.class)); 
			qo.setParameterList("idList", idList);
			
			return qo.list();
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public List<FacultyCoordinator> migrateFacultyCoordinatorFromSAC() {
		try {
			StringBuilder hql = new StringBuilder();
			hql.append("select df.idFacultad as idFaculty, zu.idZyosUser as idZyosUser ");
			hql.append("from ");
			hql.append("DecanoFacultad df, ZyosUser zu ");
			hql.append("where ");
			hql.append("df.mail = zu.email ");
			hql.append("and zu.state =:state ");
			
			Query qo = getSession().createQuery(hql.toString()).setResultTransformer(Transformers.aliasToBean(FacultyCoordinator.class)); 
			qo.setParameter("state", IZyosState.ACTIVE);
			
			return qo.list();
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	
	public Long searchIdDDivision(){
		StringBuilder hql = new StringBuilder();
		Query qo = null;
		try {
			hql.append("SELECT MAX(idFacultyCoordinator) FROM FacultyCoordinator");
			qo = getSession().createQuery(hql.toString());
			return Long.valueOf(qo.uniqueResult().toString());
		} catch (RuntimeException re) {
			throw re;
		} finally {
			hql = null;
			qo = null;
		}
	}
	
	public FacultyCoordinator findDDivision(Long idZyosUser,Long Faculty){
		StringBuilder hql = new StringBuilder();
		Query qo = null;
		try {
			hql.append("SELECT NEW FacultyCoordinator(idFacultyCoordinator, idZyosUser,idFaculty) FROM FacultyCoordinator "
					+ " WHERE idZyosUser=:idZyosUser AND idFaculty=:idFaculty ");
			qo = getSession().createQuery(hql.toString());
			qo.setParameter("idZyosUser", idZyosUser);
			qo.setParameter("idFaculty", Faculty);
			return (FacultyCoordinator)qo.uniqueResult();
		} catch (RuntimeException re) {
			throw re;
		} finally {
			hql = null;
			qo = null;
		}
	}
}