package com.zyos.alert.studentReport.model;

import static org.hibernate.criterion.Example.create;

import java.util.List;

import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zyos.alert.integration.model.SubjectSAC;
import com.zyos.core.common.api.IZyosGroup;
import com.zyos.core.common.api.IZyosState;
import com.zyos.core.connection.HibernateSessionFactory;
import com.zyos.core.connection.OracleBaseHibernateDAO;

/**
 * A data access object (DAO) providing persistence and search support for Subject entities.
 * Transaction control of the save(), update() and delete() operations can directly support Spring
 * container-managed transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how to configure it for
 * the desired type of transaction control.
 * 
 * @see com.zyos.alert.studentReport.model.Subject
 * @author MyEclipse Persistence Tools
 */
public class SubjectDAO extends OracleBaseHibernateDAO {
	private static final Logger log = LoggerFactory.getLogger(SubjectDAO.class);

	public void save(Subject transientInstance) {
		log.debug("saving Subject instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Subject persistentInstance) {
		log.debug("deleting Subject instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Subject findById(java.lang.Long id) {
		log.debug("getting Subject instance with id: " + id);
		try {
			Subject instance = (Subject) getSession().get("com.zyos.alert.studentReport.model.Subject", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<Subject> findByExample(Subject instance) {
		log.debug("finding Subject instance by example");
		try {
			List<Subject> results =
				(List<Subject>) getSession().createCriteria("com.zyos.alert.studentReport.model.Subject").add(create(instance)).list();
			log.debug("find by example successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding Subject instance with property: " + propertyName + ", value: " + value);
		try {
			String queryString = "from Subject as model where model." + propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<Subject> findAll() {
		log.debug("finding all Subject instances");
		try {
			String queryString = "from Subject  where state > 0";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Subject merge(Subject detachedInstance) {
		log.debug("merging Subject instance");
		try {
			Subject result = (Subject) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Subject instance) {
		log.debug("attaching dirty Subject instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Subject instance) {
		log.debug("attaching clean Subject instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public List<Subject> loadSubjectListByAcademicPeriod(Long idUser, String currentDate, Long idZyosGroup) throws Exception {
		StringBuilder hql = new StringBuilder();
		Query qo = null;
		try {
			hql.append(" SELECT NEW ");
			hql.append(" Subject ");
			hql.append("( s.idSubject, ");
			hql.append(" s.name, ");
			hql.append(" s.moreThanOneTeacher)");
			hql.append(" FROM ");
			hql.append(" Subject s, ");
			hql.append(" Student st, ");// intercambiable
			hql.append(" StudentSubject ss, ");// //intercambiable
			hql.append(" AcademicPeriod a ");
			hql.append(" WHERE ");
			hql.append(" s.state = :state ");
			hql.append(" AND st.state = :state ");
			hql.append(" AND ss.idSubject = s.idSubject ");
			hql.append(" AND ss.idStudent = st.idStudent ");
			hql.append(" AND ss.idAcademicPeriod = a.id ");
			hql.append(" AND st.idZyosUser = :idUser ");
			hql.append(" AND ( :currentDate BETWEEN a.startDate AND a.endDate ) ");
			qo = getSession().createQuery(hql.toString());
			qo.setParameter("idUser", idUser);
			qo.setParameter("currentDate", currentDate);
			qo.setParameter("state", IZyosState.ACTIVE);

			return qo.list();
		} catch (Exception e) {
			throw e;
		} finally {
			hql = null;
			qo = null;
		}
	}

	public List<Subject> loadNonSubjectList(List<Subject> subjectList) {
		StringBuilder hql = new StringBuilder();
		Query qo = null;
		try {
			hql.append(" SELECT NEW Subject(");
			hql.append(" s.idSubject, ");
			hql.append(" s.name, ");
			hql.append(" s.moreThanOneTeacher)");
			hql.append(" FROM ");
			hql.append(" Subject s ");
			hql.append(" WHERE ");
			hql.append(" s NOT IN ");
			hql.append(subjectList.isEmpty() ? "(0)" : ":subjectList");
			hql.append(" and s.state = :state ");

			qo = getSession().createQuery(hql.toString());
			if (!subjectList.isEmpty())
				qo.setParameterList("subjectList", subjectList);
			qo.setParameter("state", IZyosState.ACTIVE);
			qo.setMaxResults(5);

			return qo.list();

		} catch (RuntimeException re) {
			throw re;
		}
	}

	public List<Subject> loadMonitoredSubjectListByAcademicPeriod(Long idUser, String currentDate, Long idZyosGroup) throws Exception {
		StringBuilder hql = new StringBuilder();
		Query qo = null;
		try {
			String table = "";
			String fromTable = "";
			if (idZyosGroup == IZyosGroup.TEACHER) {
				table = "ZyosUser";
				fromTable = "Teacher";
			} else {
				table = "Student";
				fromTable = table;
			}
			hql.append(" SELECT NEW");
			hql.append(" Subject ");
			hql.append("( s.idSubject, ");
			hql.append(" s.name, ");
			hql.append(" s.moreThanOneTeacher, gs.name, gs.idGroupSubject)");
			hql.append(" FROM ");
			hql.append(" Subject s, ");
			hql.append(table);
			hql.append(" zu, ");
			hql.append(fromTable);
			hql.append("Subject zus, ");
			hql.append(" AcademicPeriod a, GroupSubject gs");
			hql.append(" WHERE ");
			hql.append(" zu.state = :state ");
			hql.append(" AND zus.state = :state ");
			hql.append(" AND s.state = :state ");
			hql.append(" AND a.state = :state ");
			hql.append(" AND zus.idSubject = s.idSubject ");
			hql.append(" AND zus.idGroupSubject = gs.idGroupSubject ");
			hql.append(" AND zus.id");
			hql.append(table);
			hql.append("= zu.id");
			hql.append(table);
			hql.append(" AND zus.idAcademicPeriod = a.id ");
			hql.append(" AND zu.idZyosUser = :idUser ");
			hql.append(" AND ( :currentDate BETWEEN a.startDate AND a.endDate ) ");
			qo = getSession().createQuery(hql.toString());
			qo.setParameter("idUser", idUser);
			qo.setParameter("currentDate", currentDate);
			qo.setParameter("state", IZyosState.ACTIVE);
			return qo.list();
		} catch (Exception e) {
			throw e;
		} finally {
			hql = null;
			qo = null;
		}
	}

	public List<SubjectSAC> migrateSubjectListFromSAC() throws Exception {
		try {
			StringBuilder hql = new StringBuilder();
			hql.append("select distinct cast(m.id as long) as idSubject, m.nombre as name, m.intHoraria as hours  ");
			hql.append("from ");
			hql.append("Materias m ");
			hql.append("where ");
			hql.append("m.id not in (select cast(id as string) from Subject)  ");

			Query qo = getSession().createQuery(hql.toString()).setResultTransformer(Transformers.aliasToBean(SubjectSAC.class));

			return qo.list();
		} catch (Exception e) {
			throw e;
		}
	}
}
