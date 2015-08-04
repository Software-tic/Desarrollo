package com.zyos.alert.query.model;

import java.util.List;

import org.hibernate.LockOptions;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;

import static org.hibernate.criterion.Example.create;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zyos.alert.facultyCoordinator.model.FacultyCoordinator;
import com.zyos.core.common.api.IZyosState;
import com.zyos.core.common.util.ManageDate;
import com.zyos.core.connection.OracleBaseHibernateDAO;

/**
 * A data access object (DAO) providing persistence and search support for
 * GradesPeriodSubject entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.zyos.alert.query.model.GradesPeriodSubject
 * @author MyEclipse Persistence Tools
 */
public class GradesPeriodSubjectDAO extends OracleBaseHibernateDAO {
	private static final Logger log = LoggerFactory.getLogger(GradesPeriodSubjectDAO.class);
	// property constants
	public static final String FIRSTCORTE = "firstcorte";
	public static final String SECONDCORTE = "secondcorte";
	public static final String THIRDCORTE = "thirdcorte";
	public static final String FINALGRADE = "finalgrade";
	public static final String DATECREATION = "datecreation";
	public static final String USERCREATION = "usercreation";
	public static final String DATECHANGE = "datechange";
	public static final String USERCHANGE = "userchange";
	public static final String STATE = "state";

	public void save(GradesPeriodSubject transientInstance) {
		log.debug("saving GradesPeriodSubject instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(GradesPeriodSubject persistentInstance) {
		log.debug("deleting GradesPeriodSubject instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public GradesPeriodSubject findById(java.lang.Long id) {
		log.debug("getting GradesPeriodSubject instance with id: " + id);
		try {
			GradesPeriodSubject instance = (GradesPeriodSubject) getSession()
					.get("com.zyos.alert.query.model.GradesPeriodSubject", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<GradesPeriodSubject> findByExample(GradesPeriodSubject instance) {
		log.debug("finding GradesPeriodSubject instance by example");
		try {
			List<GradesPeriodSubject> results = (List<GradesPeriodSubject>) getSession()
					.createCriteria(
							"com.zyos.alert.query.model.GradesPeriodSubject")
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
		log.debug("finding GradesPeriodSubject instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from GradesPeriodSubject as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<GradesPeriodSubject> findByFirstcorte(Object firstcorte) {
		return findByProperty(FIRSTCORTE, firstcorte);
	}

	public List<GradesPeriodSubject> findBySecondcorte(Object secondcorte) {
		return findByProperty(SECONDCORTE, secondcorte);
	}

	public List<GradesPeriodSubject> findByThirdcorte(Object thirdcorte) {
		return findByProperty(THIRDCORTE, thirdcorte);
	}

	public List<GradesPeriodSubject> findByFinalgrade(Object finalgrade) {
		return findByProperty(FINALGRADE, finalgrade);
	}

	public List<GradesPeriodSubject> findByDatecreation(Object datecreation) {
		return findByProperty(DATECREATION, datecreation);
	}

	public List<GradesPeriodSubject> findByUsercreation(Object usercreation) {
		return findByProperty(USERCREATION, usercreation);
	}

	public List<GradesPeriodSubject> findByDatechange(Object datechange) {
		return findByProperty(DATECHANGE, datechange);
	}

	public List<GradesPeriodSubject> findByUserchange(Object userchange) {
		return findByProperty(USERCHANGE, userchange);
	}

	public List<GradesPeriodSubject> findByState(Object state) {
		return findByProperty(STATE, state);
	}

	public List findAll() {
		log.debug("finding all GradesPeriodSubject instances");
		try {
			String queryString = "from GradesPeriodSubject";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public GradesPeriodSubject merge(GradesPeriodSubject detachedInstance) {
		log.debug("merging GradesPeriodSubject instance");
		try {
			GradesPeriodSubject result = (GradesPeriodSubject) getSession()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(GradesPeriodSubject instance) {
		log.debug("attaching dirty GradesPeriodSubject instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(GradesPeriodSubject instance) {
		log.debug("attaching clean GradesPeriodSubject instance");
		try {
			getSession().buildLockRequest(LockOptions.NONE).lock(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
	
	/** SIAT-TUNJA */
	public Long selectId() {
		try {
			StringBuilder hql = new StringBuilder();
			hql.append("select count(gps.idgradesPeriodSubject) from GradesPeriodSubject gps ");
			
			Query qo = getSession().createQuery(hql.toString());
			
			return longOf( qo.uniqueResult() );
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	/** SIAT-TUNJA */
	public List<GradesPeriodSubject> migrateGradesPeriodSubjectFromSAC(Long idAcademicPeriod) {
		try {
			StringBuilder hql = new StringBuilder();
			hql.append(" SELECT gps.idgradesPeriodSubject, np.perCorte, np.sdoCorte, np.terCorte, np.NFinal, sb.idStudentSubject "
					+ " FROM notas_periodo np, AcademicPeriod ap, StudentSubject sb, GradesPeriodSubject gps " 
					+ " WHERE ap.id = :idPeriod " 
					+ " AND np.periodo = ap.name "
					+ " AND sb.idSubject = np.idMateria " 
					+ " AND sb.idStudent = np.idEstudiante "
					+ " AND sb.idAcademicPeriod = ap.id "
					+ " AND sb.idStudentSubject = gps.studentsubject "
					+ " AND ap.state = :state "
					+ " AND gps.state = :state " 
					+ " AND sb.state = :state ");
			
			Query qo = getSession().createQuery(hql.toString());
			qo.setParameter("state", IZyosState.ACTIVE);
			qo.setParameter("idPeriod",idAcademicPeriod);
			qo.setResultTransformer(Transformers.aliasToBean(GradesPeriodSubject.class));
			
			return qo.list();
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	/** SIAT-TUNJA */
	public List<GradesPeriodSubject> generateStudentAlertTunja( Long AcademicPeriod, Integer Corte ) {
		try {
			StringBuilder hql = new StringBuilder();
			hql.append(" SELECT NEW GradesPeriodSubject(g.idgradesPeriodSubject, g.studentsubject, ss.idstudent, p.id) "
					+ " FROM GradesPeriodSubject g,StudentSubject ss, AcademicPeriod p, Corte c "
					+ " WHERE c.academicperiod=p.id "
					+ " AND ss.idAcademicPeriod = p.id "
					+ " AND ss.idStudentSubject = g.studentsubject AND ");
			if(Corte == 1)
				hql.append(" cast(g.firstcorte as float) > (SELECT percentageAssistance FROM ControlPanel WHERE idControlPanel = 4) ");
			if(Corte == 2)
				hql.append(" cast(g.secondcorte as float) > (SELECT percentageAssistance FROM ControlPanel WHERE idControlPanel = 4) ");
			if(Corte == 3)
				hql.append(" cast(g.thirdcorte as float) > (SELECT percentageAssistance FROM ControlPanel WHERE idControlPanel = 4) ");
			if(Corte == 4)
				hql.append(" cast(g.finalgrade as float) > (SELECT percentageAssistance FROM ControlPanel WHERE idControlPanel = 4) ");
			
			Query qo = getSession().createQuery(hql.toString());
			qo.setParameter("AcademicPeriod", AcademicPeriod);
			
			return qo.list();
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	/** SIAT-TUNJA */
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
}