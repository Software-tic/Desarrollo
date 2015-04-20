package com.zyos.alert.sac.model;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zyos.alert.integration.model.GroupSubjectSAC;
import com.zyos.core.connection.OracleBaseHibernateDAO;

/**
 * A data access object (DAO) providing persistence and search support for
 * Grupos entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.zyos.alert.sac.model.Grupo
 * @author MyEclipse Persistence Tools
 */
public class GrupoDAO extends OracleBaseHibernateDAO {
	private static final Logger log = LoggerFactory.getLogger(GrupoDAO.class);

	public void save(Grupo transientInstance) {
		log.debug("saving Grupos instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Grupo persistentInstance) {
		log.debug("deleting Grupos instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public List<GroupSubjectSAC> migrateGroupListFromSAC(Long idAcademicPeriod) throws Exception {
		try {
			StringBuilder hql = new StringBuilder();
			hql.append("select DISTINCT g.idGrupo as idGroupSubject, g.nombreGrupo as name, cast(mg.idMateria as long) as idSubject ");
			hql.append("from ");
			hql.append("Grupo g, MateriasGrupo mg, Subject s ");
			hql.append("where ");
			hql.append("s.idSubject =  cast(mg.idMateria as long) and ");
			hql.append("mg.idGrupo = g.idGrupo and g.idGrupo not in (select id from GroupSubject gs where idAcademicPeriod =:idAP ) ");
			
			Query qo = getSession().createQuery(hql.toString()).setResultTransformer(Transformers.aliasToBean(GroupSubjectSAC.class)); 
			qo.setParameter("idAP", idAcademicPeriod);
			 return  qo.list();
		} catch (Exception e) {
			throw e;
		}
	}

}