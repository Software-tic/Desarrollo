package com.zyos.alert.studentReport.model;

import com.zyos.alert.faculty.model.Faculty;
import com.zyos.alert.studentReport.api.IReportType;
import com.zyos.alert.studentReport.api.IStatusReportStudent;
import com.zyos.core.common.api.IZyosGroup;
import com.zyos.core.common.api.IZyosState;
import com.zyos.core.connection.OracleBaseHibernateDAO;
import com.zyos.core.lo.user.model.ZyosGroup;
import com.zyos.core.lo.user.model.ZyosUser;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.StringType;

import static org.hibernate.criterion.Example.create;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A data access object (DAO) providing persistence and search support for
 * Reportstudent entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.zyos.alert.studentReport.model.ReportStudent
 * @author MyEclipse Persistence Tools
 */
public class ReportStudentDAO extends OracleBaseHibernateDAO {
	private static final Logger log = LoggerFactory
			.getLogger(ReportStudentDAO.class);

	public void save(ReportStudent transientInstance) {
		log.debug("saving Reportstudent instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(ReportStudent persistentInstance) {
		log.debug("deleting Reportstudent instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public ReportStudent findById(java.lang.Long id) {
		log.debug("getting Reportstudent instance with id: " + id);
		try {
			ReportStudent instance = (ReportStudent) getSession().get(
					"com.zyos.alert.studentReport.model.Reportstudent", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<ReportStudent> findByExample(ReportStudent instance) {
		log.debug("finding Reportstudent instance by example");
		try {
			List<ReportStudent> results = getSession()
					.createCriteria(
							"com.zyos.alert.studentReport.model.Reportstudent")
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
		log.debug("finding Reportstudent instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from Reportstudent as model where model."
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
		log.debug("finding all Reportstudent instances");
		try {
			String queryString = "from Reportstudent";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public ReportStudent merge(ReportStudent detachedInstance) {
		log.debug("merging Reportstudent instance");
		try {
			ReportStudent result = (ReportStudent) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(ReportStudent instance) {
		log.debug("attaching dirty Reportstudent instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(ReportStudent instance) {
		log.debug("attaching clean Reportstudent instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	/** @author oortiz */
	public Object[] loadReportStudentData() throws Exception {
		StringBuilder hql = new StringBuilder();
		Query qo = null;
		try {
			hql.append(" SELECT ");
			hql.append(" COUNT(CASE WHEN rs.idReportType = :manual THEN 1 END) AS manual, ");
			hql.append(" COUNT(CASE WHEN rs.idReportType = :auto THEN 1 END) AS auto ");
			hql.append(" FROM ReportStudent rs  ");
			qo = getSession().createSQLQuery(hql.toString());
			qo.setParameter("manual", IReportType.MANUAL);
			qo.setParameter("auto", IReportType.AUTOMATIC);
			return (Object[]) qo.uniqueResult();
		} catch (Exception e) {
			throw e;
		} finally {

		}
	}

	/** @author oortiz */
	public Long loadTotalStudent(String currentDate, String currentHour,
			Long currentDay) throws Exception {
		StringBuilder hql = new StringBuilder();
		Query qo = null;
		try {
			hql.append(" SELECT ");
			hql.append(" COUNT(a.idZyosUser) ");
			hql.append(" FROM ");
			hql.append(" Absent a, ");
			hql.append(" StudentSubject ss, ");
			hql.append(" AcademicPeriod ap, ");
			hql.append(" DayClass d, ");
			hql.append(" DayCalendar dc ");
			hql.append(" WHERE ");
			hql.append(" a.idStudentSubject = ss.idStudentSubject ");
			hql.append(" AND d.idStudentSubject = ss.idStudentSubject ");
			hql.append(" AND d.idDayCalendar = dc.id ");
			hql.append(" AND ss.idAcademicPeriod = ap.id ");
			hql.append(" AND dc.id = :currentDay ");
			hql.append(" AND :currentHour BETWEEN d.hourStart AND d.hourEnd ");
			hql.append(" AND :currentDate BETWEEN ap.startDate AND ap.endDate ");
			qo = getSession().createQuery(hql.toString());
			qo.setParameter("currentDate", currentDate);
			qo.setParameter("currentHour", currentHour);
			qo.setParameter("currentDay", currentDay);
			return (Long) qo.uniqueResult();
		} catch (Exception e) {
			throw e;
		} finally {
			hql = null;
			qo = null;
		}
	}
	
	
	/** jhernandez*/
	/*postgres*/
	public List<ReportStudent> loadSearchReportStudentListPostgres(Long idZyosGroup, Long idZyosUser, ReportStudent reportSearch) throws Exception {
		StringBuilder sql = new StringBuilder();
		Query qo = null;
		try {
			reportSearch.setIdRiskFactor(reportSearch.getIdRiskFactor() == null?0L:reportSearch.getIdRiskFactor());
			sql.append(" SELECT ");
			sql.append(" rs.idReportStudent, ");
			sql.append(" rs.idStudent, ");
			sql.append(" rs.dateCreation, ");
			sql.append(" rs.detailReport, ");
			sql.append(" rs.idReportType, ");
			sql.append(" rs.idStatusReportStudent, ");
			sql.append(" rs.idStage, ");
			sql.append(" rs.firstIntervention, ");
			sql.append(" rs.idAdviser, ");
			sql.append(" rs.idSolicitor, ");
			sql.append(" rf.name riskFactorName, ");
			sql.append(" zu.name zyosUserName, ");
			sql.append(" zu.lastname zyosUserLastName, zu.idZyosUser, zu.documentNumber, s.code, zu.phone, zu.mobilePhone, zu.email as emailStudent, rs.detailReport, rf.idRiskFactorCategory, ");
			sql.append(" CASE WHEN (rs.idReportType = :idReportType OR rs.idReportType = :idReportTypeA) and rs.idSolicitor is not null then array(select (z.name || ' ' || z.lastname, zg.name) from ZyosUser z, ZyosGroup zg where rs.idSolicitor ");
			sql.append(" = z.idZyosUser and zg.id = z.idZyosGroup) else ");
			sql.append(" array(select (fs.name, fs.phone, fs.mobilePhone, fs.email) from FamilyStudent fs where fs.idStudent = s.idStudent and rs.idRiskFactor = fs.idRiskFactor) end solicitorData, ");
			sql.append(" CASE rs.idAdviser ");
			sql.append(" WHEN :idZyosUser  ");
			sql.append(" THEN 1 ");
			sql.append(" ELSE 0 ");
			sql.append(" END AS isButton, ");
			sql.append(" CASE zu.idZyosUser ");
			sql.append(" WHEN :idZyosUser ");
			sql.append(" THEN 1 ");
			sql.append(" ELSE 0 ");
			sql.append(" END AS isButtonCase ");
			sql.append(" FROM ");
			sql.append(" ReportStudent rs, ");
			sql.append(" RiskFactor rf, ");
			sql.append(" Student s, ");
			sql.append(" ZyosUser zu ");			
			if(!reportSearch.getIdFaculty().equals(0L))
			{				
				sql.append(" ,StudentDegree sd, Degree d, FacultyDegree fd, Faculty f ");				
			}						
			if (!reportSearch.getIdSolicitor().equals(0L))
			{
				sql.append(" ,ZyosUser zu2 ");			
			}				
			sql.append(" WHERE ");
			
			if (idZyosGroup.equals(IZyosGroup.STUDENT) || idZyosGroup.equals(IZyosGroup.CLASS_MATE)) {	
				
				sql.append(" (s.idZyosUser = :idZyosUser OR rs.idSolicitor =:idZyosUser) AND ");
				if (reportSearch.getCode() != null && !reportSearch.getCode().isEmpty())
				{
					sql.append(" rs.idStudent = s.idStudent AND s.code =:code  AND ");	
				}else
				{
					sql.append(" rs.idStudent = s.idStudent AND ");
				}				
				if (reportSearch.getDateFrom() != null && reportSearch.getDateTo() != null) {
					sql.append(" rs.dateCreation BETWEEN :dateFrom AND :dateTo AND ");
				} else {
					if (reportSearch.getDateFrom() != null && reportSearch.getDateTo() == null) {
						sql.append(" rs.dateCreation >= :dateFrom AND ");
					} else
					{
						if (reportSearch.getDateFrom() == null && reportSearch.getDateTo() != null) {
							sql.append(" rs.dateCreation <= :dateTo AND ");
						}
					}
				}				
				if (!reportSearch.getIdSolicitor().equals(0L)) {
					sql.append(" rs.idSolicitor = zu2.idZyosUser AND ");
					sql.append(" zu2.idZyosGroup =:idSolicitor AND ");
				}
				if (!reportSearch.getIdStatusReportStudent().equals(0L))
				{
					sql.append(" rs.idStatusReportStudent =:idStatusReportStudent AND ");
				}				
				if (!reportSearch.getIdReportType().equals(0L))
				{
					sql.append(" rs.idReportType =:idReportType AND ");
				}					
				sql.append(" rs.idStudent = s.idStudent AND ");				
				if(!reportSearch.getIdFaculty().equals(0L))
				{
					sql.append(" s.idStudent = sd.idStudent AND sd.idDegree = d.id AND d.id = fd.idDegree AND fd.idFaculty = f.idFaculty AND f.idFaculty =:idFaculty  AND ");
				}
				sql.append(" s.idZyosUser = zu.idZyosUser AND ");					
				if (!reportSearch.getIdRiskFactorCategory().equals(0L) && reportSearch.getIdRiskFactor().equals(0L))
				{
					sql.append(" rs.idRiskFactor = rf.idRiskFactor AND ");
					sql.append(" rf.idRiskFactorCategory =:idRiskFactorCategory AND ");
				}else{			
				if (!reportSearch.getIdRiskFactorCategory().equals(0L) && !reportSearch.getIdRiskFactor().equals(0L))
				{
					sql.append(" rs.idRiskFactor = rf.idRiskFactor AND rf.idRiskFactor =:idRiskFactor AND rf.idRiskFactorCategory =:idRiskFactorCategory AND ");	
				}else
				{
					if (reportSearch.getIdRiskFactorCategory().equals(0L) && !reportSearch.getIdRiskFactor().equals(0L))
					{
						sql.append(" rs.idRiskFactor = rf.idRiskFactor AND rf.idRiskFactor=:idRiskFactor AND ");					
					}else
					{						
						sql.append(" rs.idRiskFactor = rf.idRiskFactor AND ");	
					}
				}				
				}				
				sql.append(" rs.state = :state ");
				
				} else
					if (idZyosGroup.equals(IZyosGroup.TEACHER)) {
						
				sql.append(" (rs.idAdviser =:idZyosUser OR rs.idSolicitor =:idZyosUser) AND  ");
				
				if (reportSearch.getDateFrom() != null && reportSearch.getDateTo() != null) {
					sql.append(" rs.dateCreation BETWEEN :dateFrom AND :dateTo AND ");
				} else {
					if (reportSearch.getDateFrom() != null && reportSearch.getDateTo() == null) {
						sql.append(" rs.dateCreation >= :dateFrom AND ");
					} else
					{
						if (reportSearch.getDateFrom() == null && reportSearch.getDateTo() != null) {
							sql.append(" rs.dateCreation <= :dateTo AND ");
						}
					}
				}	
				
				if (!reportSearch.getIdSolicitor().equals(0L)) {
					sql.append(" rs.idSolicitor = zu2.idZyosUser AND ");
					sql.append(" zu2.idZyosGroup =:idSolicitor AND ");
				}
				if (!reportSearch.getIdStatusReportStudent().equals(0L))
				{
					sql.append(" rs.idStatusReportStudent =:idStatusReportStudent AND ");
				}				
				if (!reportSearch.getIdReportType().equals(0L))
				{
					sql.append(" rs.idReportType =:idReportType AND ");
				}					
				if (reportSearch.getCode() != null && !reportSearch.getCode().isEmpty())
				{
					sql.append(" rs.idStudent = s.idStudent AND s.code =:code  AND ");	
				}else
				{
					sql.append(" rs.idStudent = s.idStudent AND ");
				}			
				if(!reportSearch.getIdFaculty().equals(0L))
				{
					sql.append(" s.idStudent = sd.idStudent AND sd.idDegree = d.id AND d.id = fd.idDegree AND fd.idFaculty = f.idFaculty AND f.idFaculty =:idFaculty  AND ");
				}
				
				sql.append(" s.idZyosUser = zu.idZyosUser AND ");	
				
				if (!reportSearch.getIdRiskFactorCategory().equals(0L) && reportSearch.getIdRiskFactor().equals(0L))
				{
					sql.append(" rs.idRiskFactor = rf.idRiskFactor AND ");
					sql.append(" rf.idRiskFactorCategory =:idRiskFactorCategory AND ");
				}else{			
				if (!reportSearch.getIdRiskFactorCategory().equals(0L) && !reportSearch.getIdRiskFactor().equals(0L))
				{
					sql.append(" rs.idRiskFactor = rf.idRiskFactor AND rf.idRiskFactor =:idRiskFactor AND rf.idRiskFactorCategory =:idRiskFactorCategory AND ");	
				}else
				{
					if (reportSearch.getIdRiskFactorCategory().equals(0L) && !reportSearch.getIdRiskFactor().equals(0L))
					{
						sql.append(" rs.idRiskFactor = rf.idRiskFactor AND rf.idRiskFactor=:idRiskFactor AND ");					
					}else
					{						
						sql.append(" rs.idRiskFactor = rf.idRiskFactor AND ");	
					}
				}				
				}				
				sql.append(" rs.state = :state ");	
				
			} else if (idZyosGroup.equals(IZyosGroup.ADMINISTRATOR)) {	
				
				if (reportSearch.getDateFrom() != null && reportSearch.getDateTo() != null) {					
					sql.append(" rs.dateCreation BETWEEN :dateFrom AND :dateTo AND ");
				} else {
					if (reportSearch.getDateFrom() != null && reportSearch.getDateTo() == null) {
						sql.append(" rs.dateCreation >= :dateFrom AND ");
					} else
					{
						if (reportSearch.getDateFrom() == null && reportSearch.getDateTo() != null) {
							sql.append(" rs.dateCreation <= :dateTo AND ");
						}
					}
				}				
				if (!reportSearch.getIdSolicitor().equals(0L)) {
					sql.append(" rs.idSolicitor = zu2.idZyosUser AND ");
					sql.append(" zu2.idZyosGroup =:idSolicitor AND ");
				}
				if (!reportSearch.getIdStatusReportStudent().equals(0L))
				{
					sql.append(" rs.idStatusReportStudent =:idStatusReportStudent AND ");
				}				
				if (!reportSearch.getIdReportType().equals(0L))
				{
					sql.append(" rs.idReportType =:idReportType AND ");
				}					
				if (reportSearch.getCode() != null && !reportSearch.getCode().isEmpty())
				{
					sql.append(" rs.idStudent = s.idStudent AND s.code =:code  AND ");	
				}else
				{
					sql.append(" rs.idStudent = s.idStudent AND ");
				}
				if(!reportSearch.getIdFaculty().equals(0L))
				{
					sql.append(" s.idStudent = sd.idStudent AND sd.idDegree = d.id AND d.id = fd.idDegree AND fd.idFaculty = f.idFaculty AND f.idFaculty =:idFaculty  AND ");
				}
				
				sql.append(" s.idZyosUser = zu.idZyosUser AND ");	
				
				if (!reportSearch.getIdRiskFactorCategory().equals(0L) && reportSearch.getIdRiskFactor().equals(0L))
				{
					sql.append(" rs.idRiskFactor = rf.idRiskFactor AND ");
					sql.append(" rf.idRiskFactorCategory =:idRiskFactorCategory AND ");
				}else{			
				if (!reportSearch.getIdRiskFactorCategory().equals(0L) && !reportSearch.getIdRiskFactor().equals(0L))
				{
					sql.append(" rs.idRiskFactor = rf.idRiskFactor AND rf.idRiskFactor =:idRiskFactor AND rf.idRiskFactorCategory =:idRiskFactorCategory AND ");	
				}else
				{
					if (reportSearch.getIdRiskFactorCategory().equals(0L) && !reportSearch.getIdRiskFactor().equals(0L))
					{
						sql.append(" rs.idRiskFactor = rf.idRiskFactor AND rf.idRiskFactor=:idRiskFactor AND ");					
					}else
					{						
						sql.append(" rs.idRiskFactor = rf.idRiskFactor AND ");	
					}
				}				
				}				
				sql.append(" rs.state = :state ");
			} else {
				
				sql.append(" (rs.idAdviser =:idZyosUser OR rs.idSolicitor =:idZyosUser OR rs.idZyosUserAdviserFaculty =:idZyosUser ) AND ");
				
				if (reportSearch.getDateFrom() != null && reportSearch.getDateTo() != null) {
					sql.append(" rs.dateCreation BETWEEN :dateFrom AND :dateTo AND ");
				} else {
					if (reportSearch.getDateFrom() != null && reportSearch.getDateTo() == null) {
						sql.append(" rs.dateCreation >= :dateFrom AND ");
					} else
					{
						if (reportSearch.getDateFrom() == null && reportSearch.getDateTo() != null) {
							sql.append(" rs.dateCreation <= :dateTo AND ");
						}
					}
				}
				
				if (!reportSearch.getIdSolicitor().equals(0L)) {
					sql.append(" rs.idSolicitor = zu2.idZyosUser AND ");
					sql.append(" zu2.idZyosGroup =:idSolicitor AND ");
				}
				if (!reportSearch.getIdStatusReportStudent().equals(0L))
				{
					sql.append(" rs.idStatusReportStudent =:idStatusReportStudent AND ");
				}				
				if (!reportSearch.getIdReportType().equals(0L))
				{
					sql.append(" rs.idReportType =:idReportType AND ");
				}					
				if (reportSearch.getCode() != null && !reportSearch.getCode().isEmpty())
				{
					sql.append(" rs.idStudent = s.idStudent AND s.code =:code  AND ");	
				}else
				{
					sql.append(" rs.idStudent = s.idStudent AND ");
				}			
				if(!reportSearch.getIdFaculty().equals(0L))
				{
					sql.append(" s.idStudent = sd.idStudent AND sd.idDegree = d.id AND d.id = fd.idDegree AND fd.idFaculty = f.idFaculty AND f.idFaculty =:idFaculty  AND ");
				}
				sql.append(" s.idZyosUser = zu.idZyosUser AND ");					
				if (!reportSearch.getIdRiskFactorCategory().equals(0L) && reportSearch.getIdRiskFactor().equals(0L))
				{
					sql.append(" rs.idRiskFactor = rf.idRiskFactor AND ");
					sql.append(" rf.idRiskFactorCategory =:idRiskFactorCategory AND ");
				}else{			
				if (!reportSearch.getIdRiskFactorCategory().equals(0L) && !reportSearch.getIdRiskFactor().equals(0L))
				{
					sql.append(" rs.idRiskFactor = rf.idRiskFactor AND rf.idRiskFactor =:idRiskFactor AND rf.idRiskFactorCategory =:idRiskFactorCategory AND ");	
				}else
				{
					if (reportSearch.getIdRiskFactorCategory().equals(0L) && !reportSearch.getIdRiskFactor().equals(0L))
					{
						sql.append(" rs.idRiskFactor = rf.idRiskFactor AND rf.idRiskFactor=:idRiskFactor AND ");					
					}else
					{						
						sql.append(" rs.idRiskFactor = rf.idRiskFactor AND ");	
					}
				}				
				}				
				sql.append(" rs.state = :state ");
				}

			qo =
				getSession().createSQLQuery(sql.toString()).addScalar("idReportStudent", StandardBasicTypes.LONG)
					.addScalar("idStudent", StandardBasicTypes.BIG_DECIMAL).addScalar("dateCreation", StandardBasicTypes.STRING)
					.addScalar("detailReport", StandardBasicTypes.STRING).addScalar("idReportType", StandardBasicTypes.LONG)
					.addScalar("idStatusReportStudent", StandardBasicTypes.LONG).addScalar("idStage", StandardBasicTypes.LONG)
					.addScalar("firstIntervention", StandardBasicTypes.LONG).addScalar("idAdviser", StandardBasicTypes.LONG)
					.addScalar("idSolicitor", StandardBasicTypes.LONG).addScalar("riskFactorName", StandardBasicTypes.STRING)
					.addScalar("zyosUserName", StandardBasicTypes.STRING).addScalar("zyosUserLastName", StandardBasicTypes.STRING)
					.addScalar("idZyosUser", StandardBasicTypes.LONG).addScalar("code",  StandardBasicTypes.STRING)
					.addScalar("phone",  StandardBasicTypes.STRING)
					.addScalar("mobilePhone",  StandardBasicTypes.STRING)
					.addScalar("emailStudent",  StandardBasicTypes.STRING)
					.addScalar("detailReport",  StandardBasicTypes.STRING)	
					.addScalar("idRiskFactorCategory",  StandardBasicTypes.LONG)
					.addScalar("documentNumber", StandardBasicTypes.STRING).addScalar("solicitorData", StandardBasicTypes.STRING)
					.addScalar("isButton", StandardBasicTypes.LONG).addScalar("isButtonCase", StandardBasicTypes.LONG)
					.setResultTransformer(Transformers.aliasToBean(ReportStudent.class));
			qo.setParameter("state", IZyosState.ACTIVE);
			qo.setParameter("idReportType", IReportType.MANUAL);
			qo.setParameter("idReportTypeA", IReportType.AUTOMATIC);
			qo.setParameter("idZyosUser", idZyosUser);
			if (reportSearch.getCode() != null && !reportSearch.getCode().isEmpty())
			{
				qo.setParameter("code", reportSearch.getCode());
			}			
			
			if (reportSearch.getDateFrom() != null && reportSearch.getDateTo() != null) {
				qo.setParameter("dateFrom", reportSearch.getDateFrom());
				qo.setParameter("dateTo", reportSearch.getDateTo());
			} else {
				if (reportSearch.getDateFrom() != null && reportSearch.getDateTo() == null) {
					qo.setParameter("dateFrom", reportSearch.getDateFrom());
				} else
				{
					if (reportSearch.getDateFrom() == null && reportSearch.getDateTo() != null) {
						qo.setParameter("dateTo", reportSearch.getDateTo());
					}
				}
			}
			if(!reportSearch.getIdSolicitor().equals(0L))
			{
				qo.setParameter("idSolicitor", reportSearch.getIdSolicitor());
			}
			if (!reportSearch.getIdSolicitor().equals(0L))
			{
				qo.setParameter("idSolicitor", reportSearch.getIdSolicitor());			
			}
			if (!reportSearch.getIdStatusReportStudent().equals(0L))
			{
				qo.setParameter("idStatusReportStudent", reportSearch.getIdStatusReportStudent());	
			}
			if (!reportSearch.getIdReportType().equals(0L))
			{
				qo.setParameter("idReportType", reportSearch.getIdReportType());	
			}
			if(!reportSearch.getIdFaculty().equals(0L))
			{				
				qo.setParameter("idFaculty", reportSearch.getIdFaculty());	
			}			
			if (!reportSearch.getIdRiskFactorCategory().equals(0L) && reportSearch.getIdRiskFactor().equals(0L))
			{
				qo.setParameter("idRiskFactorCategory", reportSearch.getIdRiskFactorCategory());				
			}			
			if (!reportSearch.getIdRiskFactor().equals(0L))
			{
				qo.setParameter("idRiskFactor", reportSearch.getIdRiskFactor() == null?0L:reportSearch.getIdRiskFactor());	
			}
			
			if (!reportSearch.getIdRiskFactorCategory().equals(0L) && reportSearch.getIdRiskFactor().equals(0L))
			{
				qo.setParameter("idRiskFactorCategory", reportSearch.getIdRiskFactorCategory());	
			}else{			
			if (!reportSearch.getIdRiskFactorCategory().equals(0L) && !reportSearch.getIdRiskFactor().equals(0L))
			{
				qo.setParameter("idRiskFactor", reportSearch.getIdRiskFactor());
				qo.setParameter("idRiskFactorCategory", reportSearch.getIdRiskFactorCategory());	
			}else
			{
				if (reportSearch.getIdRiskFactorCategory().equals(0L) && !reportSearch.getIdRiskFactor().equals(0L))
				{
					qo.setParameter("idRiskFactor", reportSearch.getIdRiskFactor());				
				}					
			}				
			}			
			return qo.list();
		} catch (Exception e) {
			throw e;
		} finally {
			sql = null;
			qo = null;
		}
	}
	
	/* postgrest */
	public List<ReportStudent> loadReportStudentListPostgres(Long idZyosGroup,
		Long idZyosUser) throws Exception {
	StringBuilder sql = new StringBuilder();
	Query qo = null;
	try {
		sql.append(" SELECT ");		
		sql.append(" rs.idReportStudent, ");
		sql.append(" rs.idStudent, ");
		sql.append(" rs.dateCreation, ");
		sql.append(" rs.detailReport, ");
		sql.append(" rs.idReportType, ");
		sql.append(" rs.idStatusReportStudent, ");
		sql.append(" rs.idStage, ");
		sql.append(" rs.firstIntervention, ");
		sql.append(" rs.idAdviser, ");
		sql.append(" rs.idSolicitor, ");
		sql.append(" rf.name riskFactorName, ");
		sql.append(" zu.name zyosUserName, ");
		sql.append(" zu.lastname zyosUserLastName, zu.idZyosUser, zu.documentNumber, s.code, zu.phone, zu.mobilePhone, zu.email as emailStudent, rs.detailReport, rf.idRiskFactorCategory, ");
		sql.append(" CASE WHEN (rs.idReportType = :idReportType OR rs.idReportType = :idReportTypeA) and rs.idSolicitor is not null then array(select (z.name || ' ' || z.lastname, zg.name) from ZyosUser z, ZyosGroup zg where rs.idSolicitor ");
		sql.append(" = z.idZyosUser and rs.idZyosGroup = zg.id) else ");
		sql.append(" array(select (fs.name, fs.phone, fs.mobilePhone, fs.email) from FamilyStudent fs where fs.idStudent = s.idStudent and rs.idRiskFactor = fs.idRiskFactor) end solicitorData, ");
		sql.append(" CASE rs.idAdviser ");
		sql.append(" WHEN :idZyosUser  ");
		sql.append(" THEN 1 ");
		sql.append(" ELSE 0 ");
		sql.append(" END AS isButton, ");
		sql.append(" CASE zu.idZyosUser ");
		sql.append(" WHEN :idZyosUser ");
		sql.append(" THEN 1 "); 
		sql.append(" ELSE 0 ");
		sql.append(" END AS isButtonCase ");
		sql.append(" FROM ");
		sql.append(" ReportStudent rs, ");
		sql.append(" RiskFactor rf, ");
		sql.append(" Student s, ");
		sql.append(" ZyosUser zu ");
		sql.append(" WHERE ");

		if (idZyosGroup.equals(IZyosGroup.STUDENT) || idZyosGroup.equals(IZyosGroup.CLASS_MATE)) {
			
			sql.append(" rs.idStudent = s.idStudent AND ");
			sql.append(" (s.idZyosUser = :idZyosUser OR (rs.idSolicitor =:idZyosUser AND rs.idZyosGroup =:idZyosGroup) ) AND ");
			sql.append(" s.idZyosUser = zu.idZyosUser AND");
			sql.append(" rs.idRiskFactor = rf.idRiskFactor AND ");
			sql.append(" rs.state = :state ");
			sql.append(" order by rs.idReportStudent desc ");
			
		} else if (idZyosGroup.equals(IZyosGroup.TEACHER)) {
			sql.append(" (rs.idAdviser =:idZyosUser OR (rs.idSolicitor =:idZyosUser AND rs.idZyosGroup =:idZyosGroup) ) AND  ");
			sql.append(" rs.idStudent = s.idStudent AND ");
			sql.append(" s.idZyosUser = zu.idZyosUser AND ");
			sql.append(" rs.idRiskFactor = rf.idRiskFactor AND ");
			sql.append(" rs.state = :state ");		
			sql.append(" order by rs.idReportStudent desc ");
			
		} else
		if (idZyosGroup.equals(IZyosGroup.ADMINISTRATOR)) {
			sql.append(" rs.idStudent = s.idStudent AND ");
			sql.append(" s.idZyosUser = zu.idZyosUser AND ");
			sql.append(" rs.idRiskFactor = rf.idRiskFactor AND ");
			sql.append(" rs.state = :state ");
			sql.append(" order by rs.idReportStudent desc ");
			
		} else
		{
			sql.append(" (rs.idAdviser =:idZyosUser OR (rs.idSolicitor =:idZyosUser AND rs.idZyosGroup =:idZyosGroup) OR rs.idZyosUserAdviserFaculty =:idZyosUser ) AND ");
			sql.append(" rs.idStudent = s.idStudent AND ");
			sql.append(" s.idZyosUser = zu.idZyosUser AND ");
			sql.append(" rs.idRiskFactor = rf.idRiskFactor AND ");
			sql.append(" rs.state = :state ");
			sql.append(" order by rs.idReportStudent desc ");
		}

		qo = getSession()
			.createSQLQuery(sql.toString())
			.addScalar("idReportStudent", StandardBasicTypes.LONG)
			.addScalar("idStudent", StandardBasicTypes.BIG_DECIMAL)
			.addScalar("dateCreation", StandardBasicTypes.STRING)
			.addScalar("detailReport", StandardBasicTypes.STRING)
			.addScalar("idReportType", StandardBasicTypes.LONG)
			.addScalar("idStatusReportStudent", StandardBasicTypes.LONG)
			.addScalar("idStage", StandardBasicTypes.LONG)
			.addScalar("firstIntervention", StandardBasicTypes.LONG)
			.addScalar("idAdviser", StandardBasicTypes.LONG)
			.addScalar("idSolicitor", StandardBasicTypes.LONG)
			.addScalar("riskFactorName", StandardBasicTypes.STRING)
			.addScalar("zyosUserName", StandardBasicTypes.STRING)
			.addScalar("zyosUserLastName", StandardBasicTypes.STRING)
			.addScalar("idZyosUser", StandardBasicTypes.LONG)
			.addScalar("code",  StandardBasicTypes.STRING)
			.addScalar("phone",  StandardBasicTypes.STRING)
			.addScalar("mobilePhone",  StandardBasicTypes.STRING)
			.addScalar("emailStudent",  StandardBasicTypes.STRING)
			.addScalar("detailReport",  StandardBasicTypes.STRING)	
			.addScalar("idRiskFactorCategory",  StandardBasicTypes.LONG)
			.addScalar("documentNumber", StandardBasicTypes.STRING)
			.addScalar("solicitorData", StandardBasicTypes.STRING)
			.addScalar("isButton", StandardBasicTypes.LONG)
			.addScalar("isButtonCase", StandardBasicTypes.LONG)
			.setResultTransformer(
					Transformers.aliasToBean(ReportStudent.class));
			qo.setParameter("state", IZyosState.ACTIVE);
			qo.setParameter("idReportType", IReportType.MANUAL);
			qo.setParameter("idReportTypeA", IReportType.AUTOMATIC);		
			qo.setParameter("idZyosUser", idZyosUser); 
			if(!idZyosGroup.equals(IZyosGroup.ADMINISTRATOR))
			qo.setParameter("idZyosGroup", idZyosGroup);
			
		return qo.list();
	} catch (Exception e) {
		throw e;
	} finally {
		sql = null;
		qo = null;
	}
}

	/* jhernandez */
	// TODO Validar con la consulta anterior (arriba) cuando es ORACLE o Postgres
	/*oracle*/
	public List<ReportStudent> loadReportStudentList(Long idZyosGroup, Long idZyosUser) throws Exception {
		StringBuilder sql = new StringBuilder();
		Query qo = null;
		try {
			sql.append(" SELECT ");
			sql.append(" rs.idReportStudent,");
			sql.append(" CAST (rs.idStudent as integer) idStudent, ");
			sql.append(" rs.dateCreation, ");
			sql.append(" rs.detailReport, ");
			sql.append(" rs.idReportType, ");
			sql.append(" rs.idStatusReportStudent, ");
			sql.append(" rs.idStage, ");
			sql.append(" rs.firstIntervention, ");
			sql.append(" rs.idAdviser, ");
			sql.append(" rs.idSolicitor, ");
			sql.append(" rf.name     riskFactorName, ");
			sql.append(" zu.name     zyosUserName, ");
			sql.append(" zu.lastname zyosUserLastName, ");
			sql.append(" zu.idZyosUser, zu.documentNumber, s.code, zu.phone, zu.mobilePhone, zu.email as emailStudent, rs.detailReport, rf.idRiskFactorCategory,  ");
			sql.append(" CASE ");
			sql.append(" WHEN (rs.idReportType =:idReportType ");
			sql.append(" OR  rs.idReportType =:idReportTypeA) ");
			sql.append(" AND rs.idSolicitor IS NOT NULL ");
			sql.append(" THEN ");
			sql.append(" ( ");
			sql.append(" SELECT DISTINCT ");
			sql.append(" LISTAGG((z.name||' '||z.lastname ||';'|| zg.name),',') WITHIN GROUP (ORDER BY ");
			sql.append(" z.idZyosGroup) ");
			sql.append(" FROM ");
			sql.append(" ZyosUser z, ");
			sql.append(" ZyosGroup zg ");
			sql.append(" WHERE ");
			sql.append(" rs.idSolicitor = z.idZyosUser ");
			sql.append(" AND zg.id = z.idZyosGroup ");
			sql.append(" GROUP BY ");
			sql.append(" z.idZyosGroup) ");
			sql.append(" ELSE ");
			sql.append(" ( ");
			sql.append(" SELECT DISTINCT ");
			sql.append(" LISTAGG((fs.name||';'|| fs.phone||';'||fs.mobilePhone||';'||fs.email), ',') ");
			sql.append(" WITHIN GROUP (ORDER BY fs.idFamilyStudent) ");
			sql.append(" FROM ");
			sql.append(" FamilyStudent fs ");
			sql.append(" WHERE ");
			sql.append(" fs.idStudent = s.idStudent ");
			sql.append(" AND rs.idRiskFactor = fs.idRiskFactor ");
			sql.append(" GROUP BY ");
			sql.append(" fs.idFamilyStudent) ");
			sql.append(" END solicitorData, ");
			sql.append(" CASE rs.idAdviser ");
			sql.append(" WHEN :idZyosUser  ");
			sql.append(" THEN 1 ");
			sql.append(" ELSE 0 ");
			sql.append(" END AS isButton, ");
			sql.append(" CASE zu.idZyosUser ");
			sql.append(" WHEN :idZyosUser ");
			sql.append(" THEN 1 ");
			sql.append(" ELSE 0 ");
			sql.append(" END AS isButtonCase ");
			sql.append(" FROM ");
			sql.append(" ReportStudent rs, ");
			sql.append(" RiskFactor rf, ");
			sql.append(" Student s, ");
			sql.append(" ZyosUser zu ");
			sql.append(" WHERE ");

			if (idZyosGroup.equals(IZyosGroup.STUDENT) || idZyosGroup.equals(IZyosGroup.CLASS_MATE)) {
				sql.append(" rs.idStudent = s.idStudent AND ");
				sql.append(" (s.idZyosUser = :idZyosUser OR (rs.idSolicitor =:idZyosUser AND rs.idZyosGroup =:idZyosGroup) ) AND  ");
				sql.append(" s.idZyosUser = zu.idZyosUser AND");
				sql.append(" rs.idRiskFactor = rf.idRiskFactor AND ");
				sql.append(" rs.state = :state ");
				sql.append(" order by rs.idReportStudent desc ");

			} else if (idZyosGroup.equals(IZyosGroup.TEACHER)) {
				sql.append(" (rs.idAdviser =:idZyosUser OR (rs.idSolicitor =:idZyosUser AND rs.idZyosGroup =:idZyosGroup) ) AND ");
				sql.append(" rs.idStudent = s.idStudent AND ");
				sql.append(" s.idZyosUser = zu.idZyosUser AND ");
				sql.append(" rs.idRiskFactor = rf.idRiskFactor AND ");
				sql.append(" rs.state = :state ");
				sql.append(" order by rs.idReportStudent desc ");

			} else if (idZyosGroup.equals(IZyosGroup.ADMINISTRATOR)) {
				sql.append(" rs.idStudent = s.idStudent AND ");
				sql.append(" s.idZyosUser = zu.idZyosUser AND ");
				sql.append(" rs.idRiskFactor = rf.idRiskFactor AND ");
				sql.append(" rs.state = :state ");
				sql.append(" order by rs.idReportStudent desc ");

			} else {
				sql.append(" (rs.idAdviser =:idZyosUser OR (rs.idSolicitor =:idZyosUser AND rs.idZyosGroup =:idZyosGroup) OR rs.idZyosUserAdviserFaculty =:idZyosUser ) AND  ");
				sql.append(" rs.idStudent = s.idStudent AND ");
				sql.append(" s.idZyosUser = zu.idZyosUser AND ");
				sql.append(" rs.idRiskFactor = rf.idRiskFactor AND ");
				sql.append(" rs.state = :state ");
				sql.append(" order by rs.idReportStudent desc ");

			}

			qo =
				getSession().createSQLQuery(sql.toString()).addScalar("idReportStudent", StandardBasicTypes.LONG)
					.addScalar("idStudent", StandardBasicTypes.BIG_DECIMAL).addScalar("dateCreation", StandardBasicTypes.STRING)
					.addScalar("detailReport", StandardBasicTypes.STRING).addScalar("idReportType", StandardBasicTypes.LONG)
					.addScalar("idStatusReportStudent", StandardBasicTypes.LONG).addScalar("idStage", StandardBasicTypes.LONG)
					.addScalar("firstIntervention", StandardBasicTypes.LONG).addScalar("idAdviser", StandardBasicTypes.LONG)
					.addScalar("idSolicitor", StandardBasicTypes.LONG).addScalar("riskFactorName", StandardBasicTypes.STRING)
					.addScalar("zyosUserName", StandardBasicTypes.STRING).addScalar("zyosUserLastName", StandardBasicTypes.STRING)
					.addScalar("idZyosUser", StandardBasicTypes.LONG).addScalar("documentNumber", StandardBasicTypes.STRING)
					.addScalar("code", StandardBasicTypes.STRING).addScalar("phone", StandardBasicTypes.STRING)
					.addScalar("mobilePhone", StandardBasicTypes.STRING).addScalar("emailStudent", StandardBasicTypes.STRING)
					.addScalar("detailReport", StandardBasicTypes.STRING).addScalar("idRiskFactorCategory", StandardBasicTypes.LONG)
					.addScalar("documentNumber", StandardBasicTypes.STRING).addScalar("solicitorData", StandardBasicTypes.STRING)
					.addScalar("isButton", StandardBasicTypes.LONG).addScalar("isButtonCase", StandardBasicTypes.LONG)
					.setResultTransformer(Transformers.aliasToBean(ReportStudent.class));
			qo.setParameter("state", IZyosState.ACTIVE);
			qo.setParameter("idReportType", IReportType.MANUAL);
			qo.setParameter("idReportTypeA", IReportType.AUTOMATIC);
			qo.setParameter("idZyosUser", idZyosUser);
			if (!idZyosGroup.equals(IZyosGroup.ADMINISTRATOR))
				qo.setParameter("idZyosGroup", idZyosGroup);

			return qo.list();
		} catch (Exception e) {
			throw e;
		} finally {
			sql = null;
			qo = null;
		}
	}
	
	/** jhernandez*/
	/*oracle*/
	public List<ReportStudent> loadSearchReportStudentList(Long idZyosGroup, Long idZyosUser, ReportStudent reportSearch) throws Exception {
		StringBuilder sql = new StringBuilder();
		Query qo = null;
		try {
			reportSearch.setIdRiskFactor(reportSearch.getIdRiskFactor() == null?0L:reportSearch.getIdRiskFactor());
			sql.append(" SELECT ");
			sql.append(" rs.idReportStudent,");
			sql.append(" CAST (rs.idStudent as integer) idStudent, ");
			sql.append(" rs.dateCreation, ");
			sql.append(" rs.detailReport, ");
			sql.append(" rs.idReportType, ");
			sql.append(" rs.idStatusReportStudent, ");
			sql.append(" rs.idStage, ");
			sql.append(" rs.firstIntervention, ");
			sql.append(" rs.idAdviser, ");
			sql.append(" rs.idSolicitor, ");
			sql.append(" rf.name     riskFactorName, ");
			sql.append(" zu.name     zyosUserName, ");
			sql.append(" zu.lastname zyosUserLastName, ");
			sql.append(" zu.idZyosUser, zu.documentNumber, s.code, zu.phone, zu.mobilePhone, zu.email as emailStudent, rs.detailReport, rf.idRiskFactorCategory,  ");
			sql.append(" CASE ");
			sql.append(" WHEN (rs.idReportType =:idReportType ");
			sql.append(" OR  rs.idReportType =:idReportTypeA) ");
			sql.append(" AND rs.idSolicitor IS NOT NULL ");
			sql.append(" THEN ");
			sql.append(" ( ");
			sql.append(" SELECT DISTINCT ");
			sql.append(" LISTAGG((z.name||' '||z.lastname ||';'|| zg.name),',') WITHIN GROUP (ORDER BY ");
			sql.append(" z.idZyosGroup) ");
			sql.append(" FROM ");
			sql.append(" ZyosUser z, ");
			sql.append(" ZyosGroup zg ");
			sql.append(" WHERE ");
			sql.append(" rs.idSolicitor = z.idZyosUser ");
			sql.append(" AND zg.id = z.idZyosGroup ");
			sql.append(" GROUP BY ");
			sql.append(" z.idZyosGroup) ");
			sql.append(" ELSE ");
			sql.append(" ( ");
			sql.append(" SELECT DISTINCT ");
			sql.append(" LISTAGG((fs.name||';'|| fs.phone||';'||fs.mobilePhone||';'||fs.email), ',') ");
			sql.append(" WITHIN GROUP (ORDER BY fs.idFamilyStudent) ");
			sql.append(" FROM ");
			sql.append(" FamilyStudent fs ");
			sql.append(" WHERE ");
			sql.append(" fs.idStudent = s.idStudent ");
			sql.append(" AND rs.idRiskFactor = fs.idRiskFactor ");
			sql.append(" GROUP BY ");
			sql.append(" fs.idFamilyStudent) ");
			sql.append(" END solicitorData, ");
			sql.append(" CASE rs.idAdviser ");
			sql.append(" WHEN :idZyosUser  ");
			sql.append(" THEN 1 ");
			sql.append(" ELSE 0 ");
			sql.append(" END AS isButton, ");
			sql.append(" CASE zu.idZyosUser ");
			sql.append(" WHEN :idZyosUser ");
			sql.append(" THEN 1 "); 
			sql.append(" ELSE 0 ");
			sql.append(" END AS isButtonCase ");
			sql.append(" FROM ");
			sql.append(" ReportStudent rs, ");
			sql.append(" RiskFactor rf, ");
			sql.append(" Student s, ");
			sql.append(" ZyosUser zu ");			
			if(!reportSearch.getIdFaculty().equals(0L))
			{				
				sql.append(" ,StudentDegree sd, Degree d, FacultyDegree fd, Faculty f ");				
			}						
			if (!reportSearch.getIdSolicitor().equals(0L))
			{
				sql.append(" ,ZyosUser zu2 ");			
			}				
			sql.append(" WHERE ");
			
			if (idZyosGroup.equals(IZyosGroup.STUDENT) || idZyosGroup.equals(IZyosGroup.CLASS_MATE)) {	
				
				sql.append(" (s.idZyosUser = :idZyosUser OR rs.idSolicitor =:idZyosUser) AND ");
				if (reportSearch.getCode() != null && !reportSearch.getCode().isEmpty())
				{
					sql.append(" rs.idStudent = s.idStudent AND s.code =:code  AND ");	
				}else
				{
					sql.append(" rs.idStudent = s.idStudent AND ");
				}				
				if (reportSearch.getDateFrom() != null && reportSearch.getDateTo() != null) {
					sql.append(" rs.dateCreation BETWEEN :dateFrom AND :dateTo AND ");
				} else {
					if (reportSearch.getDateFrom() != null && reportSearch.getDateTo() == null) {
						sql.append(" rs.dateCreation >= :dateFrom AND ");
					} else
					{
						if (reportSearch.getDateFrom() == null && reportSearch.getDateTo() != null) {
							sql.append(" rs.dateCreation <= :dateTo AND ");
						}
					}
				}				
				if (!reportSearch.getIdSolicitor().equals(0L)) {
					sql.append(" rs.idSolicitor = zu2.idZyosUser AND ");
					sql.append(" zu2.idZyosGroup =:idSolicitor AND ");
				}
				if (!reportSearch.getIdStatusReportStudent().equals(0L))
				{
					sql.append(" rs.idStatusReportStudent =:idStatusReportStudent AND ");
				}				
				if (!reportSearch.getIdReportType().equals(0L))
				{
					sql.append(" rs.idReportType =:idReportType AND ");
				}					
				sql.append(" rs.idStudent = s.idStudent AND ");				
				if(!reportSearch.getIdFaculty().equals(0L))
				{
					sql.append(" s.idStudent = sd.idStudent AND sd.idDegree = d.id AND d.id = fd.idDegree AND fd.idFaculty = f.idFaculty AND f.idFaculty =:idFaculty  AND ");
				}
				sql.append(" s.idZyosUser = zu.idZyosUser AND ");					
				if (!reportSearch.getIdRiskFactorCategory().equals(0L) && reportSearch.getIdRiskFactor().equals(0L))
				{
					sql.append(" rs.idRiskFactor = rf.idRiskFactor AND ");
					sql.append(" rf.idRiskFactorCategory =:idRiskFactorCategory AND ");
				}else{			
				if (!reportSearch.getIdRiskFactorCategory().equals(0L) && !reportSearch.getIdRiskFactor().equals(0L))
				{
					sql.append(" rs.idRiskFactor = rf.idRiskFactor AND rf.idRiskFactor =:idRiskFactor AND rf.idRiskFactorCategory =:idRiskFactorCategory AND ");	
				}else
				{
					if (reportSearch.getIdRiskFactorCategory().equals(0L) && !reportSearch.getIdRiskFactor().equals(0L))
					{
						sql.append(" rs.idRiskFactor = rf.idRiskFactor AND rf.idRiskFactor=:idRiskFactor AND ");					
					}else
					{						
						sql.append(" rs.idRiskFactor = rf.idRiskFactor AND ");	
					}
				}				
				}				
				sql.append(" rs.state = :state ");
				
				} else
					if (idZyosGroup.equals(IZyosGroup.TEACHER)) {
						
				sql.append(" (rs.idAdviser =:idZyosUser OR rs.idSolicitor =:idZyosUser) AND  ");
				
				if (reportSearch.getDateFrom() != null && reportSearch.getDateTo() != null) {
					sql.append(" rs.dateCreation BETWEEN :dateFrom AND :dateTo AND ");
				} else {
					if (reportSearch.getDateFrom() != null && reportSearch.getDateTo() == null) {
						sql.append(" rs.dateCreation >= :dateFrom AND ");
					} else
					{
						if (reportSearch.getDateFrom() == null && reportSearch.getDateTo() != null) {
							sql.append(" rs.dateCreation <= :dateTo AND ");
						}
					}
				}	
				
				if (!reportSearch.getIdSolicitor().equals(0L)) {
					sql.append(" rs.idSolicitor = zu2.idZyosUser AND ");
					sql.append(" zu2.idZyosGroup =:idSolicitor AND ");
				}
				if (!reportSearch.getIdStatusReportStudent().equals(0L))
				{
					sql.append(" rs.idStatusReportStudent =:idStatusReportStudent AND ");
				}				
				if (!reportSearch.getIdReportType().equals(0L))
				{
					sql.append(" rs.idReportType =:idReportType AND ");
				}					
				if (reportSearch.getCode() != null && !reportSearch.getCode().isEmpty())
				{
					sql.append(" rs.idStudent = s.idStudent AND s.code =:code  AND ");	
				}else
				{
					sql.append(" rs.idStudent = s.idStudent AND ");
				}			
				if(!reportSearch.getIdFaculty().equals(0L))
				{
					sql.append(" s.idStudent = sd.idStudent AND sd.idDegree = d.id AND d.id = fd.idDegree AND fd.idFaculty = f.idFaculty AND f.idFaculty =:idFaculty  AND ");
				}
				
				sql.append(" s.idZyosUser = zu.idZyosUser AND ");	
				
				if (!reportSearch.getIdRiskFactorCategory().equals(0L) && reportSearch.getIdRiskFactor().equals(0L))
				{
					sql.append(" rs.idRiskFactor = rf.idRiskFactor AND ");
					sql.append(" rf.idRiskFactorCategory =:idRiskFactorCategory AND ");
				}else{			
				if (!reportSearch.getIdRiskFactorCategory().equals(0L) && !reportSearch.getIdRiskFactor().equals(0L))
				{
					sql.append(" rs.idRiskFactor = rf.idRiskFactor AND rf.idRiskFactor =:idRiskFactor AND rf.idRiskFactorCategory =:idRiskFactorCategory AND ");	
				}else
				{
					if (reportSearch.getIdRiskFactorCategory().equals(0L) && !reportSearch.getIdRiskFactor().equals(0L))
					{
						sql.append(" rs.idRiskFactor = rf.idRiskFactor AND rf.idRiskFactor=:idRiskFactor AND ");					
					}else
					{						
						sql.append(" rs.idRiskFactor = rf.idRiskFactor AND ");	
					}
				}				
				}				
				sql.append(" rs.state = :state ");	
				
			} else if (idZyosGroup.equals(IZyosGroup.ADMINISTRATOR)) {	
				
				if (reportSearch.getDateFrom() != null && reportSearch.getDateTo() != null) {					
					sql.append(" rs.dateCreation BETWEEN :dateFrom AND :dateTo AND ");
				} else {
					if (reportSearch.getDateFrom() != null && reportSearch.getDateTo() == null) {
						sql.append(" rs.dateCreation >= :dateFrom AND ");
					} else
					{
						if (reportSearch.getDateFrom() == null && reportSearch.getDateTo() != null) {
							sql.append(" rs.dateCreation <= :dateTo AND ");
						}
					}
				}				
				if (!reportSearch.getIdSolicitor().equals(0L)) {
					sql.append(" rs.idSolicitor = zu2.idZyosUser AND ");
					sql.append(" zu2.idZyosGroup =:idSolicitor AND ");
				}
				if (!reportSearch.getIdStatusReportStudent().equals(0L))
				{
					sql.append(" rs.idStatusReportStudent =:idStatusReportStudent AND ");
				}				
				if (!reportSearch.getIdReportType().equals(0L))
				{
					sql.append(" rs.idReportType =:idReportType AND ");
				}					
				if (reportSearch.getCode() != null && !reportSearch.getCode().isEmpty())
				{
					sql.append(" rs.idStudent = s.idStudent AND s.code =:code  AND ");	
				}else
				{
					sql.append(" rs.idStudent = s.idStudent AND ");
				}
				if(!reportSearch.getIdFaculty().equals(0L))
				{
					sql.append(" s.idStudent = sd.idStudent AND sd.idDegree = d.id AND d.id = fd.idDegree AND fd.idFaculty = f.idFaculty AND f.idFaculty =:idFaculty  AND ");
				}
				
				sql.append(" s.idZyosUser = zu.idZyosUser AND ");	
				
				if (!reportSearch.getIdRiskFactorCategory().equals(0L) && reportSearch.getIdRiskFactor().equals(0L))
				{
					sql.append(" rs.idRiskFactor = rf.idRiskFactor AND ");
					sql.append(" rf.idRiskFactorCategory =:idRiskFactorCategory AND ");
				}else{			
				if (!reportSearch.getIdRiskFactorCategory().equals(0L) && !reportSearch.getIdRiskFactor().equals(0L))
				{
					sql.append(" rs.idRiskFactor = rf.idRiskFactor AND rf.idRiskFactor =:idRiskFactor AND rf.idRiskFactorCategory =:idRiskFactorCategory AND ");	
				}else
				{
					if (reportSearch.getIdRiskFactorCategory().equals(0L) && !reportSearch.getIdRiskFactor().equals(0L))
					{
						sql.append(" rs.idRiskFactor = rf.idRiskFactor AND rf.idRiskFactor=:idRiskFactor AND ");					
					}else
					{						
						sql.append(" rs.idRiskFactor = rf.idRiskFactor AND ");	
					}
				}				
				}				
				sql.append(" rs.state = :state ");
			} else {
				
				sql.append(" (rs.idAdviser =:idZyosUser OR rs.idSolicitor =:idZyosUser OR rs.idZyosUserAdviserFaculty =:idZyosUser ) AND ");
				
				if (reportSearch.getDateFrom() != null && reportSearch.getDateTo() != null) {
					sql.append(" rs.dateCreation BETWEEN :dateFrom AND :dateTo AND ");
				} else {
					if (reportSearch.getDateFrom() != null && reportSearch.getDateTo() == null) {
						sql.append(" rs.dateCreation >= :dateFrom AND ");
					} else
					{
						if (reportSearch.getDateFrom() == null && reportSearch.getDateTo() != null) {
							sql.append(" rs.dateCreation <= :dateTo AND ");
						}
					}
				}
				
				if (!reportSearch.getIdSolicitor().equals(0L)) {
					sql.append(" rs.idSolicitor = zu2.idZyosUser AND ");
					sql.append(" zu2.idZyosGroup =:idSolicitor AND ");
				}
				if (!reportSearch.getIdStatusReportStudent().equals(0L))
				{
					sql.append(" rs.idStatusReportStudent =:idStatusReportStudent AND ");
				}				
				if (!reportSearch.getIdReportType().equals(0L))
				{
					sql.append(" rs.idReportType =:idReportType AND ");
				}					
				if (reportSearch.getCode() != null && !reportSearch.getCode().isEmpty())
				{
					sql.append(" rs.idStudent = s.idStudent AND s.code =:code  AND ");	
				}else
				{
					sql.append(" rs.idStudent = s.idStudent AND ");
				}			
				if(!reportSearch.getIdFaculty().equals(0L))
				{
					sql.append(" s.idStudent = sd.idStudent AND sd.idDegree = d.id AND d.id = fd.idDegree AND fd.idFaculty = f.idFaculty AND f.idFaculty =:idFaculty  AND ");
				}
				sql.append(" s.idZyosUser = zu.idZyosUser AND ");					
				if (!reportSearch.getIdRiskFactorCategory().equals(0L) && reportSearch.getIdRiskFactor().equals(0L))
				{
					sql.append(" rs.idRiskFactor = rf.idRiskFactor AND ");
					sql.append(" rf.idRiskFactorCategory =:idRiskFactorCategory AND ");
				}else{			
				if (!reportSearch.getIdRiskFactorCategory().equals(0L) && !reportSearch.getIdRiskFactor().equals(0L))
				{
					sql.append(" rs.idRiskFactor = rf.idRiskFactor AND rf.idRiskFactor =:idRiskFactor AND rf.idRiskFactorCategory =:idRiskFactorCategory AND ");	
				}else
				{
					if (reportSearch.getIdRiskFactorCategory().equals(0L) && !reportSearch.getIdRiskFactor().equals(0L))
					{
						sql.append(" rs.idRiskFactor = rf.idRiskFactor AND rf.idRiskFactor=:idRiskFactor AND ");					
					}else
					{						
						sql.append(" rs.idRiskFactor = rf.idRiskFactor AND ");	
					}
				}				
				}				
				sql.append(" rs.state = :state ");
				}

			qo =
				getSession().createSQLQuery(sql.toString()).addScalar("idReportStudent", StandardBasicTypes.LONG)
					.addScalar("idStudent", StandardBasicTypes.BIG_DECIMAL).addScalar("dateCreation", StandardBasicTypes.STRING)
					.addScalar("detailReport", StandardBasicTypes.STRING).addScalar("idReportType", StandardBasicTypes.LONG)
					.addScalar("idStatusReportStudent", StandardBasicTypes.LONG).addScalar("idStage", StandardBasicTypes.LONG)
					.addScalar("firstIntervention", StandardBasicTypes.LONG).addScalar("idAdviser", StandardBasicTypes.LONG)
					.addScalar("idSolicitor", StandardBasicTypes.LONG).addScalar("riskFactorName", StandardBasicTypes.STRING)
					.addScalar("zyosUserName", StandardBasicTypes.STRING).addScalar("zyosUserLastName", StandardBasicTypes.STRING)
					.addScalar("idZyosUser", StandardBasicTypes.LONG).addScalar("code", StandardBasicTypes.STRING).addScalar("phone", StandardBasicTypes.STRING)
					.addScalar("mobilePhone", StandardBasicTypes.STRING).addScalar("emailStudent", StandardBasicTypes.STRING)
					.addScalar("detailReport", StandardBasicTypes.STRING).addScalar("idRiskFactorCategory", StandardBasicTypes.LONG)
					.addScalar("documentNumber", StandardBasicTypes.STRING).addScalar("solicitorData", StandardBasicTypes.STRING)
					.addScalar("isButton", StandardBasicTypes.LONG).addScalar("isButtonCase", StandardBasicTypes.LONG)
					.setResultTransformer(Transformers.aliasToBean(ReportStudent.class));
			qo.setParameter("state", IZyosState.ACTIVE);
			qo.setParameter("idReportType", IReportType.MANUAL);
			qo.setParameter("idReportTypeA", IReportType.AUTOMATIC);
			qo.setParameter("idZyosUser", idZyosUser);
			if (reportSearch.getCode() != null && !reportSearch.getCode().isEmpty())
			{
				qo.setParameter("code", reportSearch.getCode());
			}			
			
			if (reportSearch.getDateFrom() != null && reportSearch.getDateTo() != null) {
				qo.setParameter("dateFrom", reportSearch.getDateFrom());
				qo.setParameter("dateTo", reportSearch.getDateTo());
			} else {
				if (reportSearch.getDateFrom() != null && reportSearch.getDateTo() == null) {
					qo.setParameter("dateFrom", reportSearch.getDateFrom());
				} else
				{
					if (reportSearch.getDateFrom() == null && reportSearch.getDateTo() != null) {
						qo.setParameter("dateTo", reportSearch.getDateTo());
					}
				}
			}
			if(!reportSearch.getIdSolicitor().equals(0L))
			{
				qo.setParameter("idSolicitor", reportSearch.getIdSolicitor());
			}
			if (!reportSearch.getIdSolicitor().equals(0L))
			{
				qo.setParameter("idSolicitor", reportSearch.getIdSolicitor());			
			}
			if (!reportSearch.getIdStatusReportStudent().equals(0L))
			{
				qo.setParameter("idStatusReportStudent", reportSearch.getIdStatusReportStudent());	
			}
			if (!reportSearch.getIdReportType().equals(0L))
			{
				qo.setParameter("idReportType", reportSearch.getIdReportType());	
			}
			if(!reportSearch.getIdFaculty().equals(0L))
			{				
				qo.setParameter("idFaculty", reportSearch.getIdFaculty());	
			}			
			if (!reportSearch.getIdRiskFactorCategory().equals(0L) && reportSearch.getIdRiskFactor().equals(0L))
			{
				qo.setParameter("idRiskFactorCategory", reportSearch.getIdRiskFactorCategory());				
			}			
			if (!reportSearch.getIdRiskFactor().equals(0L))
			{
				qo.setParameter("idRiskFactor", reportSearch.getIdRiskFactor() == null?0L:reportSearch.getIdRiskFactor());	
			}
			
			if (!reportSearch.getIdRiskFactorCategory().equals(0L) && reportSearch.getIdRiskFactor().equals(0L))
			{
				qo.setParameter("idRiskFactorCategory", reportSearch.getIdRiskFactorCategory());	
			}else{			
			if (!reportSearch.getIdRiskFactorCategory().equals(0L) && !reportSearch.getIdRiskFactor().equals(0L))
			{
				qo.setParameter("idRiskFactor", reportSearch.getIdRiskFactor());
				qo.setParameter("idRiskFactorCategory", reportSearch.getIdRiskFactorCategory());	
			}else
			{
				if (reportSearch.getIdRiskFactorCategory().equals(0L) && !reportSearch.getIdRiskFactor().equals(0L))
				{
					qo.setParameter("idRiskFactor", reportSearch.getIdRiskFactor());				
				}					
			}				
			}			
			return qo.list();
		} catch (Exception e) {
			throw e;
		} finally {
			sql = null;
			qo = null;
		}
	}
	
	
	/** postgrest */
	/** @author jhernandez 10/10/2014 9:57:33 */
	public List<ReportStudent> loadReportStudentRiskList(Long idZyosGroup,
		Long idZyosUser) throws Exception {
	StringBuilder sql = new StringBuilder();
	Query qo = null;
	try {
		sql.append(" SELECT ");		
		sql.append(" rs.idReportStudent, ");
		sql.append(" rs.idStudent, ");
		sql.append(" rs.dateCreation, ");
		sql.append(" rs.detailReport, ");
		sql.append(" rs.idReportType, ");
		sql.append(" rs.idStatusReportStudent, ");
		sql.append(" rs.idStage, ");
		sql.append(" rs.firstIntervention, ");
		sql.append(" rs.idAdviser, ");
		sql.append(" rs.idSolicitor, ");
		sql.append(" rf.name riskFactorName, ");
		sql.append(" zu.name zyosUserName, ");
		sql.append(" zu.lastname zyosUserLastName, zu.idZyosUser, zu.documentNumber, s.code, zu.phone, zu.mobilePhone, zu.email, rs.detailReport ");
		sql.append(" CASE WHEN (rs.idReportType = :idReportType OR rs.idReportType = :idReportTypeA) and rs.idSolicitor is not null then array(select (z.name || ' ' || z.lastname, zg.name) from ZyosUser z, ZyosGroup zg where rs.idSolicitor ");
		sql.append(" = z.idZyosUser and rs.idZyosGroup = zg.id) else ");
		sql.append(" array(select (fs.name, fs.phone, fs.mobilePhone, fs.email) from FamilyStudent fs where fs.idStudent = s.idStudent and rs.idRiskFactor = fs.idRiskFactor) end solicitorData, ");
		sql.append(" CASE rs.idAdviser ");
		sql.append(" WHEN :idZyosUser  ");
		sql.append(" THEN 1 ");
		sql.append(" ELSE 0 ");
		sql.append(" END AS isButton, ");
		sql.append(" CASE zu.idZyosUser ");
		sql.append(" WHEN :idZyosUser ");
		sql.append(" THEN 1 "); 
		sql.append(" ELSE 0 ");
		sql.append(" END AS isButtonCase ");
		sql.append(" FROM ");
		sql.append(" ReportStudent rs, ");
		sql.append(" RiskFactor rf, ");
		sql.append(" Student s, ");
		sql.append(" ZyosUser zu ");
		sql.append(" WHERE ");

		if (idZyosGroup.equals(IZyosGroup.STUDENT) || idZyosGroup.equals(IZyosGroup.CLASS_MATE)) {
			
			sql.append(" rs.idStudent = s.idStudent AND ");
			sql.append(" (s.idZyosUser = :idZyosUser OR (rs.idSolicitor =:idZyosUser AND rs.idZyosGroup =:idZyosGroup) ) AND ");
			sql.append(" s.idZyosUser = zu.idZyosUser AND");
			sql.append(" rs.idRiskFactor = rf.idRiskFactor AND ");
			sql.append(" rs.state = :state ");
			sql.append(" order by rs.idReportStudent desc ");
			
		} else if (idZyosGroup.equals(IZyosGroup.TEACHER)) {
			sql.append(" (rs.idAdviser =:idZyosUser OR (rs.idSolicitor =:idZyosUser AND rs.idZyosGroup =:idZyosGroup) ) AND  ");
			sql.append(" rs.idStudent = s.idStudent AND ");
			sql.append(" s.idZyosUser = zu.idZyosUser AND ");
			sql.append(" rs.idRiskFactor = rf.idRiskFactor AND ");
			sql.append(" rs.state = :state ");		
			sql.append(" order by rs.idReportStudent desc ");
			
		} else
		if (idZyosGroup.equals(IZyosGroup.ADMINISTRATOR)) {
			sql.append(" rs.idStudent = s.idStudent AND ");
			sql.append(" s.idZyosUser = zu.idZyosUser AND ");
			sql.append(" rs.idRiskFactor = rf.idRiskFactor AND ");
			sql.append(" rs.state = :state ");
			sql.append(" order by rs.idReportStudent desc ");
			
		} else
		{
			sql.append(" (rs.idAdviser =:idZyosUser OR (rs.idSolicitor =:idZyosUser AND rs.idZyosGroup =:idZyosGroup) OR rs.idZyosUserAdviserFaculty =:idZyosUser ) AND ");
			sql.append(" rs.idStudent = s.idStudent AND ");
			sql.append(" s.idZyosUser = zu.idZyosUser AND ");
			sql.append(" rs.idRiskFactor = rf.idRiskFactor AND ");
			sql.append(" rs.state = :state ");
			sql.append(" order by rs.idReportStudent desc ");
		}

		qo = getSession()
			.createSQLQuery(sql.toString())
			.addScalar("idReportStudent", StandardBasicTypes.LONG)
			.addScalar("idStudent", StandardBasicTypes.BIG_DECIMAL)
			.addScalar("dateCreation", StandardBasicTypes.STRING)
			.addScalar("detailReport", StandardBasicTypes.STRING)
			.addScalar("idReportType", StandardBasicTypes.LONG)
			.addScalar("idStatusReportStudent", StandardBasicTypes.LONG)
			.addScalar("idStage", StandardBasicTypes.LONG)
			.addScalar("firstIntervention", StandardBasicTypes.LONG)
			.addScalar("idAdviser", StandardBasicTypes.LONG)
			.addScalar("idSolicitor", StandardBasicTypes.LONG)
			.addScalar("riskFactorName", StandardBasicTypes.STRING)
			.addScalar("zyosUserName", StandardBasicTypes.STRING)
			.addScalar("zyosUserLastName", StandardBasicTypes.STRING)
			.addScalar("idZyosUser", StandardBasicTypes.LONG)
			.addScalar("code",  StandardBasicTypes.STRING)
			.addScalar("documentNumber", StandardBasicTypes.STRING)
			.addScalar("solicitorData", StandardBasicTypes.STRING)
			.addScalar("isButton", StandardBasicTypes.LONG)
			.addScalar("isButtonCase", StandardBasicTypes.LONG)
			.setResultTransformer(
					Transformers.aliasToBean(ReportStudent.class));
			qo.setParameter("state", IZyosState.ACTIVE);
			qo.setParameter("idReportType", IReportType.MANUAL);
			qo.setParameter("idReportTypeA", IReportType.AUTOMATIC);		
			qo.setParameter("idZyosUser", idZyosUser); 
			if(!idZyosGroup.equals(IZyosGroup.ADMINISTRATOR))
			qo.setParameter("idZyosGroup", idZyosGroup);
			
		return qo.list();
	} catch (Exception e) {
		throw e;
	} finally {
		sql = null;
		qo = null;
	}
}
	

	public String validateDuplicateStudentReport(Long idStudent)
			throws Exception {
		StringBuilder hql = new StringBuilder();
		Query qo = null;
		try {
			hql.append(" SELECT ");
			hql.append(" rs.idReportStudent from ReportStudent");
			hql.append(" where ");
			hql.append(" rs.idStudent =:idStudent and ");
			hql.append(" rs.state=:state ");

			qo = getSession().createQuery(hql.toString());
			qo.setParameter("idStudent", idStudent);
			qo.setParameter("state", IZyosState.ACTIVE);

			return (String) qo.uniqueResult();

		} catch (Exception e) {
			throw e;
		} finally {
			hql = null;
			qo = null;
		}
	}

	/*jhernandez*/
	public List<String> loadRiskFactorStudentList(Long idStudent)
			throws Exception {
		StringBuilder hql = new StringBuilder();
		Query qo = null;
		try {
			hql.append(" SELECT ");
			hql.append(" rs.idRiskFactor from ReportStudent");
			hql.append(" where ");
			hql.append(" rs.idStudent =:idStudent and ");
			hql.append(" rs.state=:state ");

			qo = getSession().createQuery(hql.toString());
			qo.setParameter("idStudent", idStudent);
			qo.setParameter("state", IZyosState.ACTIVE);

			List<String> result = qo.list();

			return result;

		} catch (Exception e) {
			throw e;
		} finally {
			hql = null;
			qo = null;
		}
	}

	public void updateReportStudent(ReportStudent reportStudent)
			throws Exception {
		StringBuilder hql = new StringBuilder();
		Query qo = null;
		try {
			hql.append(" UPDATE ReportStudent rs ");
			hql.append(" SET rs.detailReport = :detailReport, ");
			hql.append(" rs.idStatusReportStudent = :idStatusReportStudent, ");
			hql.append(" rs.idAdviser = :idAdviser, ");
			hql.append(" rs.idStage = :idStage ");
			hql.append(" WHERE rs.idReportStudent = :idReportStudent ");
			qo = getSession().createQuery(hql.toString());
			qo.setParameter("detailReport", reportStudent.getDetailReport());
			qo.setParameter("idStatusReportStudent",
					reportStudent.getIdStatusReportStudent());
			qo.setParameter("idAdviser", reportStudent.getIdAdviser());
			qo.setParameter("idStage", reportStudent.getIdStage());
			qo.setParameter("idReportStudent",
					reportStudent.getIdReportStudent());
			qo.executeUpdate();
		} catch (Exception e) {
			throw e;
		} finally {
			hql = null;
			qo = null;
		}

	}

	public void closeProcess(Long idReportStudent) throws Exception {
		StringBuilder hql = new StringBuilder();
		Query qo;
		try {
			hql.append(" UPDATE ReportStudent SET state = :state ");
			hql.append(" WHERE idReportStudent = :idReportStudent ");
			qo = getSession().createQuery(hql.toString());
			qo.setParameter("idReportStudent", idReportStudent);
			qo.setParameter("state", IZyosState.INACTIVE);
			qo.executeUpdate();
		} catch (Exception e) {
			throw e;
		} finally {
			hql = null;
			qo = null;
		}
	}

	public ReportStudent loadReportStudent() throws Exception {
		StringBuilder sql = new StringBuilder();
		Query qo = null;
		try {
			sql.append(" SELECT ");
			sql.append(" rs.idReportStudent, ");
			sql.append(" rs.idStatusReportStudent, ");
			sql.append(" rs.idStage, ");
			sql.append(" rs.idAdviser, ");
			sql.append(" CAST(array(SELECT rf.id FROM RiskFactorReportStudent"
					+ " rfs, RiskFactor rf where rfs.idRiskFactor "
					+ "= rf.id and rfs.idReportStudent = rs.idReportStudent "
					+ "and rs.state = :state and rfs.state =:state ORDER BY rf.name) AS VARCHAR)riskFactor");
			sql.append(" FROM ");
			sql.append(" ReportStudent rs ");
			sql.append(" WHERE ");
			sql.append(" rs.state = :state ");
			qo = getSession()
					.createSQLQuery(sql.toString())
					.addScalar("idReportStudent", StandardBasicTypes.LONG)
					.addScalar("idStatusReportStudent", StandardBasicTypes.LONG)
					.addScalar("idStage", StandardBasicTypes.LONG)
					.addScalar("idAdviser", StandardBasicTypes.LONG)
					.addScalar("riskFactor", StandardBasicTypes.STRING)
					.setResultTransformer(
							Transformers.aliasToBean(ReportStudent.class));
			qo.setParameter("state", IZyosState.ACTIVE);

			return (ReportStudent) qo.list();

		} catch (Exception e) {
			throw e;
		} finally {
			sql = null;
			qo = null;
		}
	}

	/**
	 * @author jhernandez
	 * @throws Exception
	 */
	public BigDecimal loadReportStudentManualDataClassMate() throws Exception {
		StringBuilder hql = new StringBuilder();
		Query qo = null;
		try {
			
			hql.append(" SELECT ");
			hql.append(" COUNT(rs.idStudent) AS classmate ");
			hql.append(" FROM ");
			hql.append(" ZyosUser zu, ");
			hql.append(" ReportStudent rs ");
			hql.append(" WHERE ");
			hql.append(" rs.idSolicitor = zu.idZyosUser ");
			hql.append(" AND zu.idZyosGroup =:classmate ");
						
			qo = getSession().createSQLQuery(hql.toString());
			qo.setParameter("classmate", IZyosGroup.CLASS_MATE);
						return (java.math.BigDecimal) qo.uniqueResult() ;
		} catch (Exception e) {
			throw e;
		} finally {

		}
	}
	
	
	
	/** jhernandez*/
	public BigDecimal loadReportStudentManualDataSelf() throws Exception {
		StringBuilder hql = new StringBuilder();
		Query qo = null;
		try {
			
			hql.append(" SELECT ");
			hql.append(" COUNT (CASE WHEN rs.idStudent = ( Select s.idStudent AS self ");
			hql.append(" FROM ");
			hql.append(" Student s, ");
			hql.append(" ZyosUser zu ");
			hql.append(" WHERE ");
			hql.append(" s.idZyosUser = zu.idZyosUser ");
			hql.append(" AND rs.idSolicitor = zu.idZyosUser ) THEN 1 END) as self FROM ReportStudent rs");
						
			qo = getSession().createSQLQuery(hql.toString());
			return (BigDecimal) qo.uniqueResult();
		} catch (Exception e) {
			throw e;
		} finally {

		}
	}
	
	/** jhernandez*/
	public BigDecimal loadReportStudentManualDataTeacher() throws Exception {
		StringBuilder hql = new StringBuilder();
		Query qo = null;
		try {
			
			hql.append(" SELECT ");
			hql.append(" COUNT(rs.idStudent) AS teacher ");
			hql.append(" FROM ");
			hql.append(" ZyosUser zu, ");
			hql.append(" ReportStudent rs ");
			hql.append(" WHERE ");
			hql.append(" rs.idSolicitor = zu.idZyosUser ");
			hql.append(" AND zu.idZyosGroup =:teacher ");
						
			qo = getSession().createSQLQuery(hql.toString());
			qo.setParameter("teacher", IZyosGroup.TEACHER);
			return (BigDecimal) qo.uniqueResult();
		} catch (Exception e) {
			throw e;
		} finally {

		}
	}
	
	/** jhernandez*/
	public BigDecimal loadReportStudentManualDataFreeUser() throws Exception {
		StringBuilder hql = new StringBuilder();
		Query qo = null;
		try {
			
			hql.append(" SELECT ");
			hql.append(" COUNT(CASE WHEN rs.idReportType =:manualReport AND rs.idSolicitor IS NULL THEN 1 END) AS freeuser FROM ReportStudent rs");		
						
			qo = getSession().createSQLQuery(hql.toString());
			qo.setParameter("manualReport", IReportType.MANUAL);
			return (BigDecimal) qo.uniqueResult();
		} catch (Exception e) {
			throw e;
		} finally {

		}
	}
	
	/** @author jhernandez */
	public ReportStudent validateReportDuplicate(Long idStudent,
			Long idRiskFactor) throws Exception {
		StringBuilder hql = new StringBuilder();
		Query qo = null;
		try {
			hql.append(" SELECT ");
			hql.append(" rs.idReportStudent as idReportStudent, ");
			hql.append(" rs.idStatusReportStudent as idStatusReportStudent, ");
			hql.append(" rs.idStudent as idStudent, ");
			hql.append(" rs.idStage as idStage, ");
			hql.append(" rs.idSolicitor as idSolicitor, ");
			hql.append(" rs.idAdviser as idAdviser, ");
			hql.append(" rs.detailReport as detailReport, ");
			hql.append(" rs.idRiskFactor as idRiskFactor  ");
			hql.append(" FROM ");
			hql.append(" ReportStudent rs ");
			hql.append(" WHERE ");
			hql.append(" rs.idStudent = :idStudent and ");
			hql.append(" rs.idRiskFactor = :idRiskFactor and ");
			hql.append(" rs.state = :state ");

			qo = getSession().createSQLQuery(hql.toString())
					.addScalar("idReportStudent", StandardBasicTypes.LONG)
					.addScalar("idStatusReportStudent", StandardBasicTypes.LONG)
					.addScalar("idStudent", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("idStage", StandardBasicTypes.LONG)
					.addScalar("idSolicitor", StandardBasicTypes.LONG)
					.addScalar("detailReport", StringType.INSTANCE)
					.addScalar("idRiskFactor", StandardBasicTypes.LONG)
					.setResultTransformer(Transformers.aliasToBean(ReportStudent.class));	
			
			qo.setParameter("state", IZyosState.ACTIVE);
			qo.setParameter("idStudent", idStudent);
			qo.setParameter("idRiskFactor", idRiskFactor);

			return (ReportStudent) qo.uniqueResult();

		} catch (Exception e) {
			throw e;
		} finally {
			hql = null;
			qo = null;
		}
	}

	/** @author jhernandez */
	public Student loadDataStudentReport(ReportStudent reportStudentSelected)
			throws Exception {
		StringBuilder sql = new StringBuilder();
		Query qo = null;
		try {
			sql.append(" SELECT ");
			sql.append(" s.idStudent, zu.name, zu.documentNumber, zu.email, ");
			sql.append(" zu.secondEmail, zu.phone, zu.mobilePhone, zu.address, d.id, ");
			sql.append(" s.nameResponsible, s.phoneResponsible, s.mobilePhoneResponsible ");
			sql.append(" FROM ");
			sql.append(" ZyosUser zu, Student s, StudentDegree sd, Degree d ");
			sql.append(" WHERE ");
			sql.append(" zu.idZyosUser = s.idZyosUser AND ");
			sql.append(" s.idStudent =:idStudent AND ");
			sql.append(" sd.idStudent = s.idStudent AND ");
			sql.append(" sd.idDegree = d.id AND ");
			sql.append(" zu.state = :state AND ");
			sql.append(" s.state = :state AND ");
			sql.append(" sd.state = :state AND ");
			sql.append(" d.state = :state ");

			qo = getSession()
					.createSQLQuery(sql.toString())
					.addScalar("idStudent", StandardBasicTypes.LONG)
					.addScalar("name", StandardBasicTypes.STRING)
					.addScalar("documentNumber", StandardBasicTypes.STRING)
					.addScalar("email", StandardBasicTypes.STRING)
					.addScalar("secondEmail", StandardBasicTypes.STRING)
					.addScalar("phone", StandardBasicTypes.STRING)
					.addScalar("mobilePhone", StandardBasicTypes.STRING)
					.addScalar("address", StandardBasicTypes.STRING)
					.addScalar("id", StandardBasicTypes.LONG)
					.addScalar("nameResponsible", StandardBasicTypes.STRING)
					.addScalar("phoneResponsible", StandardBasicTypes.STRING)
					.addScalar("mobilePhoneResponsible",
							StandardBasicTypes.STRING)
					.setResultTransformer(
							Transformers.aliasToBean(Student.class));

			qo.setParameter("state", IZyosState.ACTIVE);
			qo.setMaxResults(1);
			qo.setParameter("idStudent", reportStudentSelected.getIdStudent());

			return (Student) qo.uniqueResult();

		} catch (Exception e) {
			throw e;
		} finally {
			sql = null;
			qo = null;
		}
	}

	/**
	 * @author jhernandez
	 * @throws Exception
	 */
	public void updateFirstIntervention(ReportStudent reportStudentSelected,
			Student student) throws Exception {
		StringBuilder sql = new StringBuilder();
		Query qo = null;
		try {
			sql.append(" UPDATE ReportStudent set firstIntervention=:state, idStatusReportStudent=:idStatusReportStudent, ");
			sql.append(" dateChange=:dateChange, userChange=:userChange ");
			sql.append(" Where ");
			sql.append(" idReportStudent =:idReportStudent AND ");
			sql.append(" state = :state ");

			qo = getSession().createSQLQuery(sql.toString());
			qo.setParameter("idReportStudent",
					reportStudentSelected.getIdReportStudent());
			qo.setParameter("dateChange", student.getDateChange());
			qo.setParameter("userChange", student.getUserChange());
			qo.setParameter("state", IZyosState.ACTIVE);
			qo.setParameter("idStatusReportStudent",
					IStatusReportStudent.PROGRESS_INTERVENTION);

			qo.executeUpdate();

		} catch (Exception e) {
			throw e;
		} finally {
			sql = null;
			qo = null;
		}
	}
	
	/**@autor jhernandez**/
	public void deleteReportStudent(ReportStudent reportStudent)
			throws Exception {
		StringBuilder hql = new StringBuilder();
		Query qo = null;
		try {
						
			hql.append(" UPDATE ReportStudent Set state=:state, dateChange=:dateChange, userChange=:userChange ");
			hql.append(" Where idReportStudent=:idStudentSubject ");
							
			qo = getSession().createQuery(hql.toString());			
			qo.setParameter("idStudentSubject", reportStudent.getIdReportStudent());	
			qo.setParameter("dateChange", reportStudent.getDateChange());
			qo.setParameter("userChange", reportStudent.getUserChange());			
			qo.setParameter("state", IZyosState.INACTIVE);

			qo.executeUpdate();
			
			
		} catch (Exception e) {
			throw e;
		} finally {
			hql = null;
			qo = null;
		}

	}
	
	/** jhernandez */
	public List<Faculty> loadReportFacultyStudentList() throws Exception {
		StringBuilder hql = new StringBuilder();
		Query qo = null;
		try {
			hql.append(" Select DISTINCT (f.idFaculty) as idFaculty, f.name as name ");			
			hql.append(" from ReportStudent rs, Student s, StudentDegree sd, Degree d, FacultyDegree fd, Faculty f ");				
			hql.append(" where ");
			hql.append(" rs.idStudent = s.idStudent AND  ");	
			hql.append(" s.idStudent = sd.idStudent AND  ");	
			hql.append(" sd.idDegree = d.id AND ");	
			hql.append(" d.id = fd.idDegree AND  ");
			hql.append(" fd.idFaculty = f.idFaculty AND ");	
			hql.append(" rs.state = :state AND ");	
			hql.append(" s.state = :state AND ");
			hql.append(" sd.state = :state AND ");
			hql.append(" d.state = :state AND ");
			hql.append(" fd.state = :state AND ");
			hql.append(" f.state = :state ");
					
			qo = getSession().createQuery(hql.toString()).setResultTransformer(Transformers.aliasToBean(Faculty.class));
			qo.setParameter("state", IZyosState.ACTIVE );	
			return qo.list();
			
		} catch (Exception e) {
			throw e;
		} finally {
			hql = null;
			qo = null;
		}
	}
	
	
	/** jhernandez */
	public List<RiskFactorCategory> loadReportRiskFactorCategoryList() throws Exception {
		StringBuilder hql = new StringBuilder();
		Query qo = null;
		try {
			hql.append(" Select DISTINCT (rfc.id) as id, rfc.name as name  ");			
			hql.append(" from ReportStudent rs, RiskFactor rf, RiskFactorCategory rfc ");				
			hql.append(" where ");
			hql.append(" rs.idRiskFactor = rf.idRiskFactor AND  ");	
			hql.append(" rf.idRiskFactorCategory = rfc.id AND  ");	
			hql.append(" rs.state = :state AND ");
			hql.append(" rf.state = :state AND ");	
			hql.append(" rfc.state = :state ");	
					
			qo = getSession().createQuery(hql.toString()).setResultTransformer(Transformers.aliasToBean(RiskFactorCategory.class));
			qo.setParameter("state", IZyosState.ACTIVE );	
			return qo.list();
			
		} catch (Exception e) {
			throw e;
		} finally {
			hql = null;
			qo = null;
		} 
	}
	
	/** jhernandez */
	public List<RiskFactor> loadReporStudentRiskFactorList(Long idRiskFactorCategory) throws Exception {
		StringBuilder hql = new StringBuilder();
		Query qo = null;
		try {
			hql.append(" Select DISTINCT (rf.idRiskFactor) as idRiskFactor, rf.name as name ");			
			hql.append(" from ReportStudent rs, RiskFactor rf ");				
			hql.append(" where ");
			hql.append(" rs.idRiskFactor = rf.idRiskFactor AND  ");	
			hql.append(" rf.idRiskFactorCategory =:idRiskFactorCategory AND  ");	
			hql.append(" rs.state = :state AND ");
			hql.append(" rf.state = :state ");	
						
			qo = getSession().createQuery(hql.toString()).setResultTransformer(Transformers.aliasToBean(RiskFactor.class));
			qo.setParameter("state", IZyosState.ACTIVE );	
			qo.setParameter("idRiskFactorCategory", idRiskFactorCategory );
						
			return qo.list();
			
		} catch (Exception e) {
			throw e;
		} finally {
			hql = null;
			qo = null;
		}
	}
	
	
	/** jhernandez */
	public List<ZyosGroup> loadReportZyosGroupList() throws Exception {
		StringBuilder hql = new StringBuilder();
		Query qo = null;
		try {
			hql.append(" Select DISTINCT (zg.id) as id, zg.name as name  ");			
			hql.append(" from ReportStudent rs, ZyosUser zu, ZyosUserGroup zug, ZyosGroup zg ");				
			hql.append(" where ");
			hql.append(" rs.idSolicitor = zu.idZyosUser AND  ");	
			hql.append(" zu.idZyosUser = zug.idZyosUser AND ");	
			hql.append(" zug.idGroup = zg.id AND ");					
			hql.append(" rs.state = :state AND ");	
			hql.append(" zu.state = :state AND ");
			hql.append(" zug.state = :state AND ");
			hql.append(" zg.state = :state ");
					
			qo = getSession().createQuery(hql.toString()).setResultTransformer(Transformers.aliasToBean(ZyosGroup.class));
			
			qo.setParameter("state", IZyosState.ACTIVE );	
			
			return qo.list();
			
		} catch (Exception e) {
			throw e;
		} finally {
			hql = null;
			qo = null;
		}
	}
	
	/** jhernandez */
	public List<StatusReportStudent> loadReportStatusList() throws Exception {
		StringBuilder hql = new StringBuilder();
		Query qo = null;
		try {
			hql.append(" Select DISTINCT (st.id) as id, st.name as name  ");			
			hql.append(" from ReportStudent rs, StatusReportStudent st ");				
			hql.append(" where ");
			hql.append(" rs.idStatusReportStudent = st.id  AND ");	
			hql.append(" rs.state = :state AND st.state = :state ");	
					
			qo = getSession().createQuery(hql.toString()).setResultTransformer(Transformers.aliasToBean(RiskFactorCategory.class));
			qo.setParameter("state", IZyosState.ACTIVE );	
			return qo.list();
			
		} catch (Exception e) {
			throw e;
		} finally {
			hql = null;
			qo = null;
		}
	}
	
	
	/** jhernandez */
	public List<ReportType> loadReporStudentReportType() throws Exception {
		StringBuilder hql = new StringBuilder();
		Query qo = null;
		try {
			hql.append(" Select DISTINCT (rt.id) as id, rt.name as name   ");			
			hql.append(" from ReportStudent rs, ReportType rt ");				
			hql.append(" where ");
			hql.append(" rs.idReportType = rt.id  AND ");	
			hql.append(" rs.state = :state AND rt.state = :state ");	
					
			qo = getSession().createQuery(hql.toString()).setResultTransformer(Transformers.aliasToBean(ReportType.class));
			qo.setParameter("state", IZyosState.ACTIVE );	
			return qo.list();
			
		} catch (Exception e) {
			throw e;
		} finally {
			hql = null;
			qo = null;
		}
	}
	
	/** SIAT - TUNJA */
	public List<ZyosUser> loadTeacherByFacultyListTunja(Long idZyosUser, Long idGroup, Long idStudent) throws Exception{
		StringBuilder sql = new StringBuilder();
		Query qo = null;
		try {
			sql.append(" SELECT zu.idZyosUser,zu.name,zu.lastName "
					+ " FROM Teacher t,ZyosUser zu,School s,FacultySchool fs,Faculty f,ZyosGroup zg,ZyosUserGroup zug "
					+ " WHERE t.idZyosUser=zu.idZyosUser ");
					
			if (idGroup.equals(IZyosGroup.ADMINISTRATOR)) {
				sql.append(" AND t.idSchool IN (SELECT sl.idschool "
						+ " FROM Student s, StudentDegree sd, Degree d, School_Degree fd, School sl "
						+ " WHERE sd.idStudent = s.idStudent "
						+ " AND sd.idDegree = d.id "
						+ " AND d.id = fd.id_Degree "
						+ " AND fd.id_School = sl.idschool "
						+ " AND s.idStudent=:idStudent "
						+ " AND sl.state = :state "
						+ " AND s.state = :state "
						+ " AND sd.state = :state "
						+ " AND d.state = :state "
						+ " AND fd.state = :state) ");
			} else {
				sql.append(" AND t.idSchool IN (SELECT idSchool FROM Teacher WHERE idZyosUser=:idZyosUser AND state=:state) ");
			}
			sql.append(" AND zu.idZyosUser <> :idZyosUser "
					+ " AND zg.id = :idZyosGroup "
					//+ " AND zg.id IN (SELECT id FROM ZyosGroup zg WHERE name LIKE '%PAAI%') "
					+ " AND zug.idZyosUser = zu.idZyosUser "
					+ " AND zug.idGroup = zg.id "
					+ " AND t.idSchool = s.idschool "
					+ " AND fs.idSchool = s.idschool "
					+ " AND fs.idFaculty = f.idFaculty "
					+ " AND zu.state = :state "
					+ " AND f.state = :state "
					+ " AND zg.state = :state "
					+ " AND zug.state = :state "
					+ " AND t.state = :state "
					+ " AND s.state = :state "
					+ " AND fs.state = :state "
					+ " ORDER BY zu.idZyosUser ");

			qo = getSession().createSQLQuery(sql.toString())
					.addScalar("idZyosUser", StandardBasicTypes.LONG)
					.addScalar("name", StandardBasicTypes.STRING)
					.addScalar("lastName", StandardBasicTypes.STRING)
					.setResultTransformer(Transformers.aliasToBean(ZyosUser.class));
			
			qo.setParameter("state", IZyosState.ACTIVE);
			qo.setParameter("idZyosUser", idZyosUser);
			qo.setParameter("idZyosGroup", IZyosGroup.TUNJA_TEACHER_PAAI);
			if (idGroup.equals(IZyosGroup.ADMINISTRATOR)) {
				qo.setParameter("idStudent", idStudent);
			}
			return qo.list();
		} catch (Exception e) {
			throw e;
		} finally {
			sql = null;
			qo = null;
		}
	}
	
	/**SIAT TUNJA*/
	public List<ReportStudent> loadReportStudentListTunja(Long idZyosGroup, Long idZyosUser) throws Exception {
		StringBuilder sql = new StringBuilder();
		Query qo = null;
		try {
			sql.append(" SELECT ");		
			sql.append(" rs.idReportStudent, ");
			sql.append(" rs.idStudent, ");
			sql.append(" rs.dateCreation, ");
			sql.append(" rs.detailReport, ");
			sql.append(" rs.idReportType, ");
			sql.append(" rs.idStatusReportStudent, ");
			sql.append(" rs.idStage, ");
			sql.append(" rs.firstIntervention, ");
			sql.append(" rs.idAdviser, ");
			sql.append(" rs.idSolicitor, ");
			sql.append(" rf.name AS riskFactorName, ");
			sql.append(" zu.name AS zyosUserName, ");
			sql.append(" zu.lastName AS zyosUserLastName, zu.idZyosUser, zu.documentNumber, s.code, zu.phone, zu.mobilePhone, zu.email as emailStudent, rs.detailReport, rf.idRiskFactorCategory, ");
			sql.append(" CASE WHEN (rs.idReportType = :idReportType OR rs.idReportType = :idReportTypeA) and rs.idSolicitor is not null ");
			sql.append(" THEN array(select (z.name || ' ' || z.lastname, zg.name) from ZyosUser z, ZyosGroup zg where rs.idSolicitor = z.idZyosUser and rs.idZyosGroup = zg.id) ");
			sql.append(" ELSE array(select (fs.name, fs.phone, fs.mobilePhone, fs.email) from FamilyStudent fs where fs.idStudent = s.idStudent and rs.idRiskFactor = fs.idRiskFactor) ");
			sql.append(" END AS solicitorData, ");
			sql.append(" CASE rs.idAdviser ");
			sql.append(" WHEN :idZyosUser  ");
			sql.append(" THEN 1 ");
			sql.append(" ELSE 0 ");
			sql.append(" END AS isButton, ");
			sql.append(" CASE zu.idZyosUser ");
			sql.append(" WHEN :idZyosUser ");
			sql.append(" THEN 1 "); 
			sql.append(" ELSE 0 ");
			sql.append(" END AS isButtonCase ");
			sql.append(" FROM ");
			sql.append(" ReportStudent rs, ");
			sql.append(" RiskFactor rf, ");
			sql.append(" Student s, ");
			sql.append(" ZyosUser zu ");
			sql.append(" WHERE ");
			
			if (idZyosGroup.equals(IZyosGroup.TUNJA_TEACHER)) {
				sql.append(" (rs.idAdviser =:idZyosUser OR (rs.idSolicitor =:idZyosUser AND rs.idZyosGroup =:idZyosGroup) ) AND  ");
				sql.append(" rs.idStudent = s.idStudent AND ");
				sql.append(" s.idZyosUser = zu.idZyosUser AND ");
				sql.append(" rs.idRiskFactor = rf.idRiskFactor AND ");
				sql.append(" rs.state = :state ");		
				sql.append(" order by rs.idReportStudent desc ");
				
			} else if (idZyosGroup.equals(IZyosGroup.ADMINISTRATOR)) {
				sql.append(" rs.idStudent = s.idStudent AND ");
				sql.append(" s.idZyosUser = zu.idZyosUser AND ");
				sql.append(" rs.idRiskFactor = rf.idRiskFactor AND ");
				sql.append(" rs.state = :state ");
				sql.append(" order by rs.idReportStudent desc ");
				
			} else if (idZyosGroup.equals(IZyosGroup.TUNJA_TEACHER_COORD_PAAI)) { //TODOS LOS REPORTES DE ESTUDIANTES DE LA FACULTAD del docente 
				
				sql.append(" rs.idStudent = s.idStudent "
						+ " AND s.idStudent IN ( SELECT e.idStudent "
						+ " FROM Degree d,School s,Teacher t,SchoolDegree fd, "
						+ " StudentDegree sd,Student e,ZyosUser zu "
						+ " WHERE d.id=fd.idDegree "
						+ " AND fd.idSchool = s.idschool "
						+ " AND t.idZyosUser= :idZyosUser "
						+ " AND t.idSchool = s.idschool "
						+ " AND sd.idDegree = d.id "
						+ " AND sd.idStudent = e.idStudent "
						+ " AND zu.idZyosUser = e.idZyosUser "
						+ " AND d.state = :state "
						+ " AND s.state = :state "
						+ " AND fd.state = :state "
						+ " AND sd.state = :state "
						+ " AND e.state = :state "
						+ " AND zu.state = :state "
						+ " AND s.idZyosUser = zu.idZyosUser "
						+ " AND rs.idRiskFactor = rf.idRiskFactor "
						+ " AND rs.state = :state "
						+ " ORDER BY rs.idReportStudent desc ");
				
			} else if (idZyosGroup.equals(IZyosGroup.TUNJA_DECAN_FACULTY)) { //TODOS LOS REPORTES DE ESTUDIANTES DE LA FACULTAD del decano
				
				sql.append(" rs.idStudent = s.idStudent "
						+ " AND s.idStudent IN ( SELECT e.idStudent "
						+ " FROM Degree d,School s, School_Coordinador sc, SchoolDegree fd, "
						+ " StudentDegree sd, Student e, ZyosUser zu "
						+ " WHERE d.id=fd.idDegree "
						+ " AND fd.idFaculty = s.idschool "
						+ " AND sc.idZyosuser= :idZyosUser "
						+ " AND sc.idSchool = s.idschool "
						+ " AND sd.idDegree = d.id "
						+ " AND sd.idStudent = e.idStudent "
						+ " AND zu.idZyosUser = e.idZyosUser "
						+ " AND d.state = :state "
						+ " AND s.state = :state "
						+ " AND fd.state = :state "
						+ " AND sd.state = :state "
						+ " AND e.state = :state "
						+ " AND zu.state = :state "
						+ " AND s.idZyosUser = zu.idZyosUser "
						+ " AND rs.idRiskFactor = rf.idRiskFactor "
						+ " AND rs.state = :state "
						+ " ORDER BY rs.idReportStudent desc ");
				
			} else if (idZyosGroup.equals(IZyosGroup.TUNJA_TEACHER_PAAI) //TODOS LOS REGISTROS DE ESTUDIANTES ASIGNADOS AL DOCENTE PAAI 
					|| idZyosGroup.equals(IZyosGroup.TUNJA_PSICOLOGY) //O AL PSICOLOGO 
					|| idZyosGroup.equals(IZyosGroup.TUNJA_PSYCHOPEDAGOGY) //O AL PSICOPEDAGOGO
					|| idZyosGroup.equals(IZyosGroup.TUNJA_DIVISION_SECRETARY)) {  //O AL SECRETARIO DE DIVISION
				sql.append(" (rs.idAdviser =:idZyosUser OR (rs.idSolicitor =:idZyosUser AND rs.idZyosGroup =:idZyosGroup) ) AND ");
				sql.append(" rs.idStudent = s.idStudent AND ");
				sql.append(" s.idZyosUser = zu.idZyosUser AND ");
				sql.append(" rs.idRiskFactor = rf.idRiskFactor AND ");
				sql.append(" rs.state = :state ");		
				sql.append(" order by rs.idReportStudent desc ");
			} else {
				sql.append(" (rs.idAdviser =:idZyosUser OR (rs.idSolicitor =:idZyosUser AND rs.idZyosGroup =:idZyosGroup) OR rs.idZyosUserAdviserFaculty =:idZyosUser ) AND ");
				sql.append(" rs.idStudent = s.idStudent AND ");
				sql.append(" s.idZyosUser = zu.idZyosUser AND ");
				sql.append(" rs.idRiskFactor = rf.idRiskFactor AND ");
				sql.append(" rs.state = :state ");
				sql.append(" order by rs.idReportStudent desc ");
			}
			qo = getSession()
					.createSQLQuery(sql.toString())
					.addScalar("idReportStudent", StandardBasicTypes.LONG)
					.addScalar("idStudent", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("dateCreation", StandardBasicTypes.STRING)
					.addScalar("detailReport", StandardBasicTypes.STRING)
					.addScalar("idReportType", StandardBasicTypes.LONG)
					.addScalar("idStatusReportStudent", StandardBasicTypes.LONG)
					.addScalar("idStage", StandardBasicTypes.LONG)
					.addScalar("firstIntervention", StandardBasicTypes.LONG)
					.addScalar("idAdviser", StandardBasicTypes.LONG)
					.addScalar("idSolicitor", StandardBasicTypes.LONG)
					.addScalar("riskFactorName", StandardBasicTypes.STRING)
					.addScalar("zyosUserName", StandardBasicTypes.STRING)
					.addScalar("zyosUserLastName", StandardBasicTypes.STRING)
					.addScalar("idZyosUser", StandardBasicTypes.LONG)
					.addScalar("code",  StandardBasicTypes.STRING)
					.addScalar("phone",  StandardBasicTypes.STRING)
					.addScalar("mobilePhone",  StandardBasicTypes.STRING)
					.addScalar("emailStudent",  StandardBasicTypes.STRING)
					.addScalar("detailReport",  StandardBasicTypes.STRING)	
					.addScalar("idRiskFactorCategory",  StandardBasicTypes.LONG)
					.addScalar("documentNumber", StandardBasicTypes.STRING)
					.addScalar("solicitorData", StandardBasicTypes.STRING)
					.addScalar("isButton", StandardBasicTypes.LONG)
					.addScalar("isButtonCase", StandardBasicTypes.LONG)
					.setResultTransformer(
							Transformers.aliasToBean(ReportStudent.class));
			
			qo.setParameter("state", IZyosState.ACTIVE);
			qo.setParameter("idReportType", IReportType.MANUAL);
			qo.setParameter("idReportTypeA", IReportType.AUTOMATIC);		
			qo.setParameter("idZyosUser", idZyosUser);
			if(!idZyosGroup.equals(IZyosGroup.ADMINISTRATOR) && !idZyosGroup.equals(IZyosGroup.TUNJA_TEACHER_COORD_PAAI) && !idZyosGroup.equals(IZyosGroup.TUNJA_DECAN_FACULTY))
				qo.setParameter("idZyosGroup", idZyosGroup);
			
			return qo.list();
		} catch (Exception e) {
			throw e;
		} finally {
			sql = null;
			qo = null;
		}
	}
	/**SIAT TUNJA*/
	public List<ReportStudent> loadReportStudentListTunja(Long idStudent) throws Exception {
		StringBuilder sql = new StringBuilder();
		Query qo = null;
		try {
			sql.append(" SELECT ");		
			sql.append(" rs.idReportStudent, ");
			sql.append(" rs.idStudent, ");
			sql.append(" rs.dateCreation, ");
			sql.append(" rs.detailReport, ");
			sql.append(" rs.idReportType, ");
			sql.append(" rs.idStatusReportStudent, ");
			sql.append(" rs.idStage, ");
			sql.append(" rs.firstIntervention, ");
			sql.append(" rs.idAdviser, ");
			sql.append(" rs.idSolicitor, ");
			sql.append(" rf.name AS riskFactorName, ");
			sql.append(" zu.name AS zyosUserName, ");
			
			sql.append(" zu.lastName AS zyosUserLastName, zu.idZyosUser, zu.documentNumber, s.code, zu.phone, zu.mobilePhone, zu.email as emailStudent, rs.detailReport, rf.idRiskFactorCategory, ");
			sql.append(" CASE WHEN (rs.idReportType = :idReportType OR rs.idReportType = :idReportTypeA) and rs.idSolicitor is not null ");
			sql.append(" THEN array(select (z.name || ' ' || z.lastname, zg.name) from ZyosUser z, ZyosGroup zg where rs.idSolicitor = z.idZyosUser and rs.idZyosGroup = zg.id) ");
			sql.append(" ELSE array(select (fs.name, fs.phone, fs.mobilePhone, fs.email) from FamilyStudent fs where fs.idStudent = s.idStudent and rs.idRiskFactor = fs.idRiskFactor) ");
			sql.append(" END AS solicitorData ");
			
			sql.append(" FROM ");
			sql.append(" ReportStudent rs, ");
			sql.append(" RiskFactor rf, ");
			sql.append(" Student s, ");
			sql.append(" ZyosUser zu ");
			sql.append(" WHERE rs.idStudent = s.idStudent "
					+ " AND s.idStudent = :idStudent "
					+ " AND s.idZyosUser = zu.idZyosUser "
					+ " AND rs.idRiskFactor = rf.idRiskFactor "
					+ " AND rs.state = :state "
					+ " ORDER BY rs.idReportStudent desc  ");
				
			qo = getSession()
					.createSQLQuery(sql.toString())
					.addScalar("idReportStudent", StandardBasicTypes.LONG)
					.addScalar("idStudent", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("dateCreation", StandardBasicTypes.STRING)
					.addScalar("detailReport", StandardBasicTypes.STRING)
					.addScalar("idReportType", StandardBasicTypes.LONG)
					.addScalar("idStatusReportStudent", StandardBasicTypes.LONG)
					.addScalar("idStage", StandardBasicTypes.LONG)
					.addScalar("firstIntervention", StandardBasicTypes.LONG)
					.addScalar("idAdviser", StandardBasicTypes.LONG)
					.addScalar("idSolicitor", StandardBasicTypes.LONG)
					.addScalar("riskFactorName", StandardBasicTypes.STRING)
					.addScalar("zyosUserName", StandardBasicTypes.STRING)
					.addScalar("zyosUserLastName", StandardBasicTypes.STRING)
					.addScalar("idZyosUser", StandardBasicTypes.LONG)
					.addScalar("code",  StandardBasicTypes.STRING)
					.addScalar("phone",  StandardBasicTypes.STRING)
					.addScalar("mobilePhone",  StandardBasicTypes.STRING)
					.addScalar("emailStudent",  StandardBasicTypes.STRING)
					.addScalar("detailReport",  StandardBasicTypes.STRING)	
					.addScalar("idRiskFactorCategory",  StandardBasicTypes.LONG)
					.addScalar("documentNumber", StandardBasicTypes.STRING)
					.addScalar("solicitorData", StandardBasicTypes.STRING)
					.setResultTransformer(Transformers.aliasToBean(ReportStudent.class));
			
			qo.setParameter("state", IZyosState.ACTIVE);
			qo.setParameter("idReportType", IReportType.MANUAL);
			qo.setParameter("idReportTypeA", IReportType.AUTOMATIC);
			qo.setParameter("idStudent", idStudent);
			
			return qo.list();
		} catch (Exception e) {
			throw e;
		} finally {
			sql = null;
			qo = null;
		}
	}
	
	/**
	 * @author jhernandez
	 * @author SIAT-TUNJA
	 * @throws Exception
	 */
	public BigDecimal loadReportStudentManualDataClassMateTunja() throws Exception {
		StringBuilder hql = new StringBuilder();
		Query qo = null;
		try {
			
			hql.append(" SELECT ");
			hql.append(" COUNT(rs.idStudent) AS classmate ");
			hql.append(" FROM ");
			hql.append(" ZyosUser zu, ");
			hql.append(" ReportStudent rs ");
			hql.append(" WHERE ");
			hql.append(" rs.idSolicitor = zu.idZyosUser ");
			hql.append(" AND zu.idZyosGroup =:classmate ");
						
			qo = getSession().createSQLQuery(hql.toString());
			qo.setParameter("classmate", IZyosGroup.CLASS_MATE);
						return (BigDecimal.valueOf(Long.valueOf(qo.uniqueResult().toString())));
		} catch (Exception e) {
			throw e;
		} finally {

		}
	}
	
	/** @author jhernandez
	 * @author SIAT-TUNJA */
	public BigDecimal loadReportStudentManualDataFreeUserTunja() throws Exception {
		StringBuilder hql = new StringBuilder();
		Query qo = null;
		try {
			
			hql.append(" SELECT ");
			hql.append(" COUNT(CASE WHEN rs.idReportType =:manualReport AND rs.idSolicitor IS NULL THEN 1 END) AS freeuser FROM ReportStudent rs");		
						
			qo = getSession().createSQLQuery(hql.toString());
			qo.setParameter("manualReport", IReportType.MANUAL);
			return (BigDecimal.valueOf(Long.valueOf(qo.uniqueResult().toString())));
		} catch (Exception e) {
			throw e;
		} finally {

		}
	}
	
	/** @author jhernandez
	 * @author SIAT-TUNJA */
	public BigDecimal loadReportStudentManualDataSelfTunja() throws Exception {
		StringBuilder hql = new StringBuilder();
		Query qo = null;
		try {
			
			hql.append(" SELECT ");
			hql.append(" COUNT (CASE WHEN rs.idStudent = ( Select s.idStudent AS self ");
			hql.append(" FROM ");
			hql.append(" Student s, ");
			hql.append(" ZyosUser zu ");
			hql.append(" WHERE ");
			hql.append(" s.idZyosUser = zu.idZyosUser ");
			hql.append(" AND rs.idSolicitor = zu.idZyosUser ) THEN 1 END) as self FROM ReportStudent rs");
						
			qo = getSession().createSQLQuery(hql.toString());
			return (BigDecimal.valueOf(Long.valueOf(qo.uniqueResult().toString())));
		} catch (Exception e) {
			throw e;
		} finally {

		}
	}
	
	/**@author jhernandez
	 * @author SIAT-TUNJA*/
	public BigDecimal loadReportStudentManualDataTeacherTunja() throws Exception {
		StringBuilder hql = new StringBuilder();
		Query qo = null;
		try {
			
			hql.append(" SELECT ");
			hql.append(" COUNT(rs.idStudent) AS teacher ");
			hql.append(" FROM ");
			hql.append(" ZyosUser zu, ");
			hql.append(" ReportStudent rs ");
			hql.append(" WHERE ");
			hql.append(" rs.idSolicitor = zu.idZyosUser ");
			hql.append(" AND zu.idZyosGroup =:teacher ");
						
			qo = getSession().createSQLQuery(hql.toString());
			qo.setParameter("teacher", IZyosGroup.TEACHER);
			return (BigDecimal.valueOf(Long.valueOf(qo.uniqueResult().toString())));
		} catch (Exception e) {
			throw e;
		} finally {

		}
	}
}