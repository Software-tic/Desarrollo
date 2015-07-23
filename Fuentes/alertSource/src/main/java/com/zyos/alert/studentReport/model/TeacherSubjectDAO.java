package com.zyos.alert.studentReport.model;

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
 * Teachersubject entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.zyos.alert.studentReport.model.TeacherSubject
 * @author MyEclipse Persistence Tools
 */
public class TeacherSubjectDAO extends OracleBaseHibernateDAO {
	private static final Logger log = LoggerFactory
			.getLogger(TeacherSubjectDAO.class);

	public void save(TeacherSubject transientInstance) {
		log.debug("saving Teachersubject instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(TeacherSubject persistentInstance) {
		log.debug("deleting Teachersubject instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public TeacherSubject findById(java.lang.Long id) {
		log.debug("getting Teachersubject instance with id: " + id);
		try {
			TeacherSubject instance = (TeacherSubject) getSession().get(
					"com.zyos.alert.studentReport.model.Teachersubject", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding Teachersubject instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from Teachersubject as model where model."
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
		log.debug("finding all Teachersubject instances");
		try {
			String queryString = "from Teachersubject";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public TeacherSubject merge(TeacherSubject detachedInstance) {
		log.debug("merging Teachersubject instance");
		try {
			TeacherSubject result = (TeacherSubject) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(TeacherSubject instance) {
		log.debug("attaching dirty Teachersubject instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public List<TeacherSubject> migrateTeacherSubjectListFromSAC(Long idAcademicPeriod) throws Exception {
		try {
			StringBuilder hql = new StringBuilder();
			hql.append("select zu.id as idZyosUser, dmg.idGrupo as idGroupSubject, cast(dmg.idMateria as long) as idSubject, ");
			hql.append("(zu.id ||'_'|| m.idSubject ||'_'|| dmg.idGrupo) as idSAC ");
			hql.append("from    ");
			hql.append("DocenteMateriaGrupo dmg, ZyosUser zu, Docente d, Subject m, GroupSubject gs ");
			hql.append("where     ");
			hql.append("(zu.id ||'_'|| m.idSubject ||'_'|| dmg.idGrupo) not in (select idSAC from TeacherSubject where idAcademicPeriod =:idAP and idSAC is not null) ");
			hql.append("and dmg.idDocente = d.id    ");
			hql.append("and d.documento = zu.documentNumber  ");
			hql.append("and CAST(dmg.idMateria AS long) = m.id ");
			hql.append("and dmg.idGrupo = gs.idGroupSubject ");

			Query qo = getSession().createQuery(hql.toString()).setResultTransformer(Transformers.aliasToBean(TeacherSubject.class)); 
			qo.setParameter("idAP", idAcademicPeriod);
			
			 return qo.list();
		} catch (Exception e) {
			throw e;
		}
	}
}