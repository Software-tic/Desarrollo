package com.zyos.alert.studentReport.model;

import static org.hibernate.criterion.Example.create;

import java.util.List;

import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.hibernate.type.LongType;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.StringType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zyos.alert.calification.model.Evaluation;
import com.zyos.core.common.api.IZyosGroup;
import com.zyos.core.common.api.IZyosState;
import com.zyos.core.common.util.ManageDate;
import com.zyos.core.connection.OracleBaseHibernateDAO;

/**
 * A data access object (DAO) providing persistence and search support for
 * Student entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.zyos.alert.studentReport.model.Student
 * @author MyEclipse Persistence Tools
 */
public class StudentDAO extends OracleBaseHibernateDAO {
	private static final Logger log = LoggerFactory.getLogger(StudentDAO.class);

	public void save(Student transientInstance) {
		log.debug("saving Student instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Student persistentInstance) {
		log.debug("deleting Student instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Student findById(java.lang.Long id) {
		log.debug("getting Student instance with id: " + id);
		try {
			Student instance = (Student) getSession().get(
					"com.zyos.alert.studentReport.model.Student", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<Student> findByExample(Student instance) {
		log.debug("finding Student instance by example");
		try {
			List<Student> results = (List<Student>) getSession()
					.createCriteria(
							"com.zyos.alert.studentReport.model.Student")
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
		log.debug("finding Student instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Student as model where model."
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
		log.debug("finding all Student instances");
		try {
			String queryString = "from Student";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Student merge(Student detachedInstance) {
		log.debug("merging Student instance");
		try {
			Student result = (Student) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Student instance) {
		log.debug("attaching dirty Student instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Student instance) {
		log.debug("attaching clean Student instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	/** @autor jhernandez **/
	public List<Student> loadStudentBySubjectList(Long idSubject,
			Long idGroupSubject, String currentDate) throws Exception {
		StringBuilder hql = new StringBuilder();
		Query qo = null;
		try {
			hql.append(" SELECT ");
			hql.append(" s.idStudent as idStudent, s.code as code, s.idZyosUser as idZyoUser, z.name as name, z.lastName as lastName, z.documentNumber as documentNumber, z.email as email, ss.idStudentSubject as idStudentSubject, "); // subQuery
			hql.append(" (SELECT COUNT(*) AS studentAbsent ");
			hql.append(" FROM Absent ab ");
			hql.append(" WHERE ab.idStudentSubject = ss.idStudentSubject) as studentAbsent, su.hours  as hours ");
			hql.append(" FROM ");
			hql.append(" Student s, ZyosUser z, StudentSubject ss, Subject su, AcademicPeriod a, GroupSubject gs ");
			hql.append(" WHERE ");
			hql.append(" s.idZyosUser = z.idZyosUser ");
			hql.append(" AND ss.idStudent = s.idStudent ");
			hql.append(" AND (z.idZyosGroup = :idZyosGroup  OR z.idZyosGroup =:idZyosGroupClassmate) ");
			hql.append(" AND ss.idSubject = su.idSubject ");
			hql.append(" AND ss.idGroupSubject = gs.idGroupSubject ");
			hql.append(" AND ss.idAcademicPeriod = a.id ");
			hql.append(" AND gs.idAcademicPeriod = a.id ");
			hql.append(" AND ss.idGroupSubject =:idGroupSubject ");
			hql.append(" AND ss.idSubject =:idSubject ");

			hql.append(" AND z.state = :state ");
			hql.append(" AND s.state = :state ");
			hql.append(" AND ss.state = :state ");
			hql.append(" AND su.state = :state ");
			hql.append(" AND a.state = :state ");
			hql.append(" AND gs.state = :state ");

			hql.append(" AND ( :currentDate BETWEEN a.startDate AND a.endDate ) ");
			qo = getSession()
					.createSQLQuery(hql.toString())
					.addScalar("idStudent", StandardBasicTypes.LONG)
					.addScalar("code", StringType.INSTANCE)
					.addScalar("name", StringType.INSTANCE)
					.addScalar("lastName", StringType.INSTANCE)
					.addScalar("documentNumber", StringType.INSTANCE)
					.addScalar("email", StringType.INSTANCE)
					.addScalar("idStudentSubject", LongType.INSTANCE)
					.addScalar("studentAbsent", LongType.INSTANCE)
					.addScalar("hours", LongType.INSTANCE)
					.setResultTransformer(
							Transformers.aliasToBean(Student.class));

			qo.setParameter("idSubject", idSubject);
			qo.setParameter("idGroupSubject", idGroupSubject);
			qo.setParameter("currentDate", currentDate);
			qo.setParameter("state", IZyosState.ACTIVE);
			qo.setParameter("idZyosGroupClassmate", IZyosGroup.CLASS_MATE);
			qo.setParameter("idZyosGroup", IZyosGroup.STUDENT);
			qo.setParameter("idSubject", idSubject);
			qo.setParameter("idGroupSubject", idGroupSubject);
			qo.setParameter("currentDate", currentDate);
			qo.setParameter("state", IZyosState.ACTIVE);
			qo.setParameter("idZyosGroupClassmate", IZyosGroup.CLASS_MATE);
			qo.setParameter("idZyosGroup", IZyosGroup.STUDENT);

			return qo.list();
		} catch (Exception e) {
			throw e;
		} finally {
			hql = null;
			qo = null;
		}
	}

	/** @autor jhernandez **/
	public Student loadStudentCurrentActive(Long idZyosUser,
			Subject subjectSelected) throws Exception {
		StringBuilder hql = new StringBuilder();
		Query qo = null;
		try {
			hql.append(" SELECT Distinct NEW ");
			hql.append(" Student ");
			hql.append("( s.idStudent, s.idZyosUser, z.name, z.documentNumber, z.email, d.id, d.name, ss.idStudentSubject ) ");
			hql.append(" FROM ");
			hql.append(" StudentSubject ss, AcademicPeriod a, Student s, ZyosUser z, StudentDegree ds, Degree d ");
			hql.append(" WHERE ");
			hql.append(" ss.idStudent = s.idStudent and ");
			hql.append(" ss.idSubject =:idSubject and ");
			hql.append(" ss.idGroupSubject =:idGroupSubject and ");
			hql.append(" ss.idAcademicPeriod = a.id AND  ");
			hql.append(" :currentDate BETWEEN a.startDate AND a.endDate AND ");
			hql.append(" s.idZyosUser =:idZyosUser ");
			hql.append(" and z.idZyosUser =:idZyosUser ");
			hql.append(" and (z.idZyosGroup = :idZyosGroup  OR z.idZyosGroup =:idZyosGroupClassmate) ");
			hql.append(" and ds.idStudent = s.idStudent and ");
			hql.append(" ds.idDegree = d.id ");
			hql.append(" AND z.state = :state ");
			hql.append(" AND s.state = :state ");
			hql.append(" AND d.state = :state ");
			hql.append(" AND ds.state = :state ");
			hql.append(" AND ss.state = :state ");
			hql.append(" AND a.state = :state ");

			qo = getSession().createQuery(hql.toString());
			qo.setMaxResults(1);
			qo.setParameter("idZyosUser", idZyosUser);
			qo.setParameter("idSubject", subjectSelected.getIdSubject());
			qo.setParameter("idGroupSubject",
					subjectSelected.getIdGroupSubject());
			qo.setParameter("idZyosGroup", IZyosGroup.STUDENT);
			qo.setParameter("state", IZyosState.ACTIVE);
			qo.setParameter("idZyosGroupClassmate", IZyosGroup.CLASS_MATE);
			qo.setParameter("currentDate",
					ManageDate.getCurrentDate(ManageDate.YYYY_MM_DD));

			return (Student) qo.uniqueResult();

		} catch (Exception e) {
			throw e;
		} finally {
			hql = null;
			qo = null;
		}
	}

	/** @autor jhernandez **/
	public Student loadStudentCurrent(Long idZyosUser) throws Exception {
		StringBuilder hql = new StringBuilder();
		Query qo = null;
		try {
			hql.append(" SELECT Distinct NEW ");
			hql.append("Student ");
			hql.append("( s.idStudent, s.idZyosUser, z.name, z.lastName, z.documentNumber, z.email, d.id, d.name ) ");
			hql.append(" FROM ");
			hql.append(" Student s, ZyosUser z, StudentDegree ds, Degree d ");
			hql.append(" WHERE ");
			hql.append(" s.idZyosUser =:idZyosUser ");
			hql.append(" and z.idZyosUser =:idZyosUser ");
			hql.append(" and (z.idZyosGroup = :idZyosGroup  OR z.idZyosGroup =:idZyosGroupClassmate OR z.idZyosGroup =:idZyosGroupMonitor) ");
			hql.append(" and ds.idStudent = s.idStudent and ");
			hql.append(" ds.idDegree = d.id ");
			hql.append(" AND z.state = :state ");
			hql.append(" AND s.state = :state ");
			hql.append(" AND d.state = :state ");
			hql.append(" AND ds.state = :state ");

			qo = getSession().createQuery(hql.toString());
			qo.setMaxResults(1);
			qo.setParameter("idZyosUser", idZyosUser);
			qo.setParameter("idZyosGroup", IZyosGroup.STUDENT);
			qo.setParameter("state", IZyosState.ACTIVE);
			qo.setParameter("idZyosGroupClassmate", IZyosGroup.CLASS_MATE);
			qo.setParameter("idZyosGroupMonitor", IZyosGroup.MONITOR);

			return (Student) qo.uniqueResult();

		} catch (Exception e) {
			throw e;
		} finally {
			hql = null;
			qo = null;
		}
	}

	/** @autor jhernandez **/
	public Student validateStudent(String documentNumber, String emailUser,
			String codeStudent) throws Exception {
		StringBuilder hql = new StringBuilder();
		Query qo = null;
		try {

			hql.append(" SELECT Distinct NEW ");
			hql.append(" Student ");
			hql.append(" ( s.idStudent, s.idZyosUser, z.name,z.lastName, z.documentNumber, z.email, d.id, d.name  ) ");
			hql.append(" FROM ");
			hql.append(" Student s, ZyosUser z, Degree d, StudentDegree ds ");

			if ((emailUser != "" && !emailUser.isEmpty())) {
				hql.append(" WHERE z.email=:emailUser ");
			} else {
				if ((codeStudent != "" && !codeStudent.isEmpty())) {
					hql.append(" WHERE s.code=:codeStudent ");
				} else
					hql.append(" WHERE z.documentNumber=:documentNumber ");
			}

			hql.append(" and (z.idZyosGroup = :idZyosGroup  OR z.idZyosGroup =:idZyosGroupClassmate) ");
			hql.append(" and z.idZyosUser = s.idZyosUser ");
			hql.append(" and ds.idStudent = s.idStudent and ");
			hql.append(" ds.idDegree = d.id ");
			hql.append(" AND z.state = :state ");
			hql.append(" AND s.state = :state ");
			hql.append(" AND d.state = :state ");
			hql.append(" AND ds.state = :state ");

			qo = getSession().createQuery(hql.toString());
			if ((emailUser != "" && !emailUser.isEmpty())) {
				qo.setParameter("emailUser", emailUser);
			} else {
				if ((codeStudent != "" && !codeStudent.isEmpty())) {
					qo.setParameter("codeStudent", codeStudent);
				} else {
					qo.setParameter("documentNumber", documentNumber);

				}

			}

			qo.setParameter("state", IZyosState.ACTIVE);
			qo.setParameter("idZyosGroup", IZyosGroup.STUDENT);
			qo.setParameter("idZyosGroupClassmate", IZyosGroup.CLASS_MATE);
			qo.setMaxResults(1);

			return (Student) qo.uniqueResult();

		} catch (Exception e) {
			throw e;
		} finally {
			hql = null;
			qo = null;
		}
	}

	/** @autor jhernandez **/
	public Long loadIdAdviser(Long idDegree) throws Exception {
		StringBuilder sql = new StringBuilder();
		Query qo = null;
		try {
			sql.append(" SELECT fc.idZyosUser ");
			sql.append(" From  ");
			sql.append(" FacultyDegree fd, FacultyCoordinator fc ");
			sql.append(" Where  fd.idDegree =:idDegree AND ");
			sql.append(" fd.idFaculty = fc.idFaculty AND ");
			sql.append(" fd.state =:state AND ");
			sql.append(" fc.state =:state ");

			qo = getSession().createQuery(sql.toString());
			qo.setMaxResults(1);
			qo.setParameter("idDegree", idDegree);
			qo.setParameter("state", IZyosState.ACTIVE);

			return (Long) qo.uniqueResult();

		} catch (Exception e) {
			throw e;
		} finally {
			sql = null;
			qo = null;
		}
	}

	/** @autor jhernandez **/
	public List<Student> loadStudentCalificationItemList(
			Evaluation evaluationSelected) throws Exception {
		StringBuilder hql = new StringBuilder();
		Query qo = null;
		try {
			hql.append(" SELECT ");
			hql.append(" s.idStudent AS idStudent, ");
			hql.append(" zu.name AS name, ");
			hql.append(" zu.lastName AS lastName, ");
			hql.append(" s.code AS code, ");
			hql.append(" LISTAGG(c.grade , ',') WITHIN GROUP (ORDER BY c.idcalificationitem)  AS grade, ");
			hql.append(" LISTAGG(c.idCalificationItem , ',') WITHIN GROUP (ORDER BY c.idcalificationitem) idCalificationItemStudent,");
			// subQuery
			hql.append(" (SELECT COUNT(*)  ");
			hql.append(" FROM Absent ab ");
			hql.append(" WHERE ab.idStudentSubject = ss.idStudentSubject) AS studentAbsent  ");

			hql.append(" FROM ");
			hql.append(" Student s, ");
			hql.append(" ZyosUser zu, ");
			hql.append(" Evaluation e, ");
			hql.append(" StudentSubject ss, ");
			hql.append(" AcademicPeriod a, ");
			hql.append(" CalificationItem ci, ");
			hql.append(" Calification c ");
			hql.append(" WHERE ");
			hql.append(" s.idZyosUser = zu.idZyosUser ");
			hql.append(" AND e.idEvaluation =:idEvaluation ");
			hql.append(" AND e.idSubject =:idSubject ");
			hql.append(" AND e.idGroupSubject =:idGroupSubject ");
			hql.append(" AND e.idSubject = ss.idSubject ");
			hql.append(" AND e.idGroupSubject = ss.idGroupSubject ");
			hql.append(" AND ss.idAcademicPeriod = a.id ");
			hql.append(" AND :currentDate BETWEEN a.startDate AND a.endDate  ");
			hql.append(" AND ci.idEvaluation = e.idEvaluation ");
			hql.append(" AND c.idStudent = s.idStudent ");
			hql.append(" AND s.idStudent = ss.idStudent ");
			hql.append(" AND c.idCalificationItem = ci.idCalificationItem ");
			hql.append(" AND s.state =:state ");
			hql.append(" AND zu.state =:state ");
			hql.append(" AND s.state =:state ");
			hql.append(" AND e.state =:state ");
			hql.append(" AND ci.state =:state ");
			hql.append(" AND ss.state =:state ");
			hql.append(" AND a.state =:state ");
			hql.append(" GROUP BY ");
			hql.append(" ss.idStudentSubject, ");
			hql.append(" s.idStudent, ");
			hql.append(" zu.name, ");
			hql.append(" zu.lastName, ");
			hql.append(" s.code ");

			qo = getSession()
					.createSQLQuery(hql.toString())
					.addScalar("idStudent", StandardBasicTypes.LONG)
					.addScalar("code", StringType.INSTANCE)
					.addScalar("name", StringType.INSTANCE)
					.addScalar("lastName", StringType.INSTANCE)
					.addScalar("grade", StringType.INSTANCE)
					.addScalar("idCalificationItemStudent", StringType.INSTANCE)
					.addScalar("studentAbsent", LongType.INSTANCE)
					.setResultTransformer(
							Transformers.aliasToBean(Student.class));

			qo.setParameter("state", IZyosState.ACTIVE);
			qo.setParameter("idEvaluation",
					evaluationSelected.getIdEvaluation());
			qo.setParameter("idGroupSubject",
					evaluationSelected.getIdGroupSubject());
			qo.setParameter("idSubject", evaluationSelected.getIdSubject());
			qo.setParameter("currentDate",
					ManageDate.getCurrentDate(ManageDate.YYYY_MM_DD));
			List<Student> result = qo.list();

			return result;

		} catch (Exception e) {
			throw e;
		} finally {
			hql = null;
			qo = null;
		}
	}

	/** @autor jhernandez **/
	/*
	 * public List<Student> loadStudentCalificationItemList( Evaluation
	 * evaluationSelected) throws Exception { StringBuilder hql = new
	 * StringBuilder(); Query qo = null; try { hql.append(" SELECT ");
	 * hql.append(" s.idStudent AS idStudent, ");
	 * hql.append(" zu.name AS name, ");
	 * hql.append(" zu.lastName AS lastName, ");
	 * hql.append(" s.code AS code, "); hql.append(
	 * " array_to_string (array_agg (c.grade ORDER BY c.idcalificationitem ), ',' ) AS grade, "
	 * ); hql.append(
	 * " array_to_string (array_agg (c.idCalificationItem ORDER BY c.idcalificationitem ), ',' ) AS idCalificationItemStudent, "
	 * ); // subQuery hql.append(" (SELECT COUNT(*)  ");
	 * hql.append(" FROM Absent ab "); hql.append(
	 * " WHERE ab.idStudentSubject = ss.idStudentSubject) AS studentAbsent ");
	 * 
	 * hql.append(" FROM "); hql.append(" Student s, ");
	 * hql.append(" ZyosUser zu, "); hql.append(" Evaluation e, ");
	 * hql.append(" StudentSubject ss, "); hql.append(" AcademicPeriod a, ");
	 * hql.append(" CalificationItem ci, "); hql.append(" Calification c ");
	 * hql.append(" WHERE "); hql.append(" s.idZyosUser = zu.idZyosUser ");
	 * hql.append(" AND e.idEvaluation =:idEvaluation ");
	 * hql.append(" AND e.idSubject =:idSubject ");
	 * hql.append(" AND e.idGroupSubject =:idGroupSubject ");
	 * hql.append(" AND e.idSubject = ss.idSubject ");
	 * hql.append(" AND e.idGroupSubject = ss.idGroupSubject ");
	 * hql.append(" AND ss.idAcademicPeriod = a.id ");
	 * hql.append(" AND :currentDate BETWEEN a.startDate AND a.endDate  ");
	 * hql.append(" AND ci.idEvaluation = e.idEvaluation ");
	 * hql.append(" AND c.idStudent = s.idStudent ");
	 * hql.append(" AND s.idStudent = ss.idStudent ");
	 * hql.append(" AND c.idCalificationItem = ci.idCalificationItem ");
	 * hql.append(" AND s.state =:state ");
	 * hql.append(" AND zu.state =:state ");
	 * hql.append(" AND s.state =:state "); hql.append(" AND e.state =:state ");
	 * hql.append(" AND ci.state =:state ");
	 * hql.append(" AND ss.state =:state ");
	 * hql.append(" AND a.state =:state "); hql.append(" GROUP BY ");
	 * hql.append(" ss.idStudentSubject, "); hql.append(" s.idStudent, ");
	 * hql.append(" zu.name, "); hql.append(" zu.lastName, ");
	 * hql.append(" s.code ");;
	 * 
	 * qo = getSession() .createSQLQuery(hql.toString()) .addScalar("idStudent",
	 * StandardBasicTypes.LONG) .addScalar("code", StringType.INSTANCE)
	 * .addScalar("name", StringType.INSTANCE) .addScalar("lastName",
	 * StringType.INSTANCE) .addScalar("grade", StringType.INSTANCE)
	 * .addScalar("idCalificationItemStudent", StringType.INSTANCE)
	 * .addScalar("studentAbsent",LongType.INSTANCE) .setResultTransformer(
	 * Transformers.aliasToBean(Student.class));
	 * 
	 * qo.setParameter("state", IZyosState.ACTIVE);
	 * qo.setParameter("idEvaluation", evaluationSelected.getIdEvaluation());
	 * qo.setParameter("idGroupSubject",
	 * evaluationSelected.getIdGroupSubject()); qo.setParameter("idSubject",
	 * evaluationSelected.getIdSubject()); qo.setParameter("currentDate",
	 * ManageDate.getCurrentDate(ManageDate.YYYY_MM_DD)); List<Student> result =
	 * qo.list();
	 * 
	 * return result;
	 * 
	 * } catch (Exception e) { throw e; } finally { hql = null; qo = null; } }
	 */

	/**
	 * @author jhernandez
	 * @throws Exception
	 */
	public void updateReportStudentData(Student student) throws Exception {
		StringBuilder sql = new StringBuilder();
		Query qo = null;
		try {
			sql.append(" UPDATE Student set nameResponsible=:nameResponsible, mobilePhoneResponsible=:mobilePhoneResponsible, phoneResponsible=:phoneResponsible, ");
			sql.append(" dateChange=:dateChange, userChange=:userChange ");
			sql.append(" Where ");
			sql.append(" Student.idStudent =:idStudent AND ");
			sql.append(" Student.state = :state ");

			qo = getSession().createSQLQuery(sql.toString());
			qo.setParameter("idStudent", student.getIdStudent());
			qo.setParameter("nameResponsible", student.getNameResponsible());
			qo.setParameter("mobilePhoneResponsible",
					student.getMobilePhoneResponsible());
			qo.setParameter("phoneResponsible", student.getPhoneResponsible());
			qo.setParameter("dateChange", student.getDateChange());
			qo.setParameter("userChange", student.getUserChange());
			qo.setParameter("state", IZyosState.ACTIVE);

			qo.executeUpdate();

		} catch (Exception e) {
			throw e;
		} finally {
			sql = null;
			qo = null;
		}
	}
}