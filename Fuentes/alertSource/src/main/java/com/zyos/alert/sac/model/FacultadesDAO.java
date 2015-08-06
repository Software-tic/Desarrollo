package com.zyos.alert.sac.model;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zyos.alert.faculty.model.Faculty;
import com.zyos.core.connection.OracleBaseHibernateDAO;

/**
 * A data access object (DAO) providing persistence and search support for
 * Facultades entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.zyos.alert.sac.model.Facultades
 * @author MyEclipse Persistence Tools
 */
public class FacultadesDAO extends OracleBaseHibernateDAO {
	private static final Logger log = LoggerFactory
			.getLogger(FacultadesDAO.class);

	public void save(Facultades transientInstance) {
		log.debug("saving Facultades instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Facultades persistentInstance) {
		log.debug("deleting Facultades instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public List<Faculty> migrateFacultyListFromSAC() throws Exception {
		try {
			StringBuilder hql = new StringBuilder();
			hql.append("select  ");
			hql.append("c.identificador as idFaculty, c.facultad as name ");
			hql.append("from ");
			hql.append("Facultades c ");
			hql.append("where ");
			hql.append("c.id not in (select id from Faculty)) ");
			
			Query qo = getSession().createQuery(hql.toString()).setResultTransformer(Transformers.aliasToBean(Faculty.class));
			return qo.list();
		} catch (Exception e) {
			throw e;
		}
	}

}