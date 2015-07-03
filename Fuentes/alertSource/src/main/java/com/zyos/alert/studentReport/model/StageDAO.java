package com.zyos.alert.studentReport.model;

import com.zyos.alert.calification.model.Evaluation;
import com.zyos.core.common.api.IZyosState;
import com.zyos.core.common.util.ManageDate;
import com.zyos.core.connection.OracleBaseHibernateDAO;
import com.zyos.core.lo.enterprise.model.Enterprise;

import java.util.List;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.hibernate.type.LongType;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.StringType;

import static org.hibernate.criterion.Example.create;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A data access object (DAO) providing persistence and search support for Stage entities.
 * Transaction control of the save(), update() and delete() operations can directly support Spring
 * container-managed transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how to configure it for
 * the desired type of transaction control.
 * 
 * @see com.zyos.alert.studentReport.model.Stage
 * @author MyEclipse Persistence Tools
 */
public class StageDAO extends OracleBaseHibernateDAO {
	private static final Logger log = LoggerFactory.getLogger(StageDAO.class);

	public void save(Stage transientInstance) {
		log.debug("saving Stage instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Stage persistentInstance) {
		log.debug("deleting Stage instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Stage findById(java.lang.Long id) {
		log.debug("getting Stage instance with id: " + id);
		try {
			Stage instance = (Stage) getSession().get("com.zyos.alert.studentReport.model.Stage", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<Stage> findByExample(Stage instance) {
		log.debug("finding Stage instance by example");
		try {
			List<Stage> results = (List<Stage>) getSession().createCriteria("com.zyos.alert.studentReport.model.Stage").add(create(instance)).list();
			log.debug("find by example successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding Stage instance with property: " + propertyName + ", value: " + value);
		try {
			String queryString = "from Stage as model where model." + propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public Stage merge(Stage detachedInstance) {
		log.debug("merging Stage instance");
		try {
			Stage result = (Stage) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	/** jhernandez */
	/*
	 * public List<Stage> findAll() throws Exception { StringBuilder hql = new StringBuilder();
	 * Query qo = null; try {
	 * hql.append(" select s.idStage as idStage, s.name as name, s.description as description, ");
	 * hql.append(
	 * " s.dateCreation as dateCreation, s.userCreation as userCreation, s.dateChange as dateChange, "
	 * );
	 * hql.append(" s.userChange as userChange, s.giveProcessPermission as giveProcessPermission ");
	 * hql.append(" From Stage s "); hql.append(" where s.state = :state order by idStage"); qo =
	 * getSession
	 * ().createQuery(hql.toString()).setResultTransformer(Transformers.aliasToBean(Stage.class));
	 * qo.setParameter("state", IZyosState.ACTIVE);
	 * 
	 * return qo.list(); } catch (Exception e) { throw e; } }
	 */

	public List<Stage> findAll() {
		log.debug("finding all Stage instances");
		try {
			String queryString = "from Stage  where state > 0";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}


	public void attachDirty(Stage instance) {
		log.debug("attaching dirty Stage instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Stage instance) {
		log.debug("attaching clean Stage instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	/** jhernandez */
	public void editStage(Stage stage) throws Exception {
		StringBuilder hql = new StringBuilder();
		Query qo = null;
		try {
			hql.append(" UPDATE Stage s ");
			hql.append(" SET s.name = :name, ");
			hql.append(" s.description = :description, ");
			hql.append(" s.dateChange = :dateChange, ");
			hql.append(" s.userChange = :userChange ");
			hql.append(" WHERE s.id = :idStage ");
			qo = getSession().createQuery(hql.toString());
			qo.setParameter("name", stage.getName());
			qo.setParameter("description", stage.getDescription());
			qo.setParameter("dateChange", stage.getDateChange());
			qo.setParameter("userChange", stage.getUserChange());
			qo.setParameter("id", stage.getId());

			qo.executeUpdate();
		} catch (Exception e) {
			throw e;
		} finally {
			hql = null;
			qo = null;
		}

	}

	/** jhernandez */
	public void deleteStage(String document, List<Long> idStageList) throws Exception {
		StringBuilder hql = new StringBuilder();
		Query qo = null;
		try {
			hql.append(" UPDATE Stage s ");
			hql.append(" SET s.state = :state, ");
			hql.append(" s.dateChange = :dateChange, ");
			hql.append(" s.userChange = :userChange ");
			hql.append(" WHERE s.id IN :idStageList ");
			qo = getSession().createQuery(hql.toString());
			qo.setParameter("state", IZyosState.INACTIVE);
			qo.setParameterList("idStageList", idStageList);
			qo.setParameter("dateChange", ManageDate.getCurrentDate(ManageDate.YYYY_MM_DD_HH_MM_SS));
			qo.setParameter("userChange", document);

			qo.executeUpdate();
		} catch (Exception e) {
			throw e;
		} finally {
			hql = null;
			qo = null;
		}
	}

	/** jhernandez */
	public void updateStagePermission(Stage stage) throws Exception {
		StringBuilder hql = new StringBuilder();
		Query qo = null;
		try {
			hql.append(" UPDATE Stage s ");
			hql.append(" SET s.giveProcessPermission = :permission, ");
			hql.append(" s.dateChange = :dateChange, ");
			hql.append(" s.userChange = :userChange ");
			hql.append(" WHERE s.id = :id ");
			qo = getSession().createQuery(hql.toString());
			qo.setParameter("permission", stage.isPermission() ? 1L : 0L);
			qo.setParameter("dateChange", stage.getDateChange());
			qo.setParameter("userChange", stage.getUserChange());
			qo.setParameter("id", stage.getId());

			qo.executeUpdate();
		} catch (Exception e) {
			throw e;
		} finally {
			hql = null;
			qo = null;
		}

	}

	/** jhernandez */
	public List<Stage> loadStagePermissionList() throws Exception {
		StringBuilder hql = new StringBuilder();
		Query qo = null;
		try {
			hql.append(" select s.idStage as idStage, s.name as name, s.description as description, ");
			hql.append(" s.dateCreation as dateCreation, s.userCreation as userCreation, s.dateChange as dateChange, ");
			hql.append(" s.userChange as userChange, s.giveProcessPermission as giveProcessPermission ");
			hql.append(" From Stage s ");
			hql.append(" where s.state = :state AND s.giveProcessPermission =:state order by idStage");
			qo = getSession().createQuery(hql.toString()).setResultTransformer(Transformers.aliasToBean(Stage.class));
			qo.setParameter("state", IZyosState.ACTIVE);

			return qo.list();
		} catch (Exception e) {
			throw e;
		}
	}
	
	/** SIAT - TUNJA */
	public List<Stage> loadStageListTunja() throws Exception {
		StringBuilder hql = new StringBuilder();
		Query qo = null;
		try {
			hql.append(" SELECT new Stage (s.name, s.description,s.id) FROM Stage s ");
			hql.append(" WHERE s.description LIKE '%Tunja%' ");
			hql.append(" AND s.state = :state order by id");
			qo = getSession().createQuery(hql.toString());
			qo.setParameter("state", IZyosState.ACTIVE);

			return qo.list();
		} catch (Exception e) {
			throw e;
		}
	}
	
}
