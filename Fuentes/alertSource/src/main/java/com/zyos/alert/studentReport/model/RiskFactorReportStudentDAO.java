package com.zyos.alert.studentReport.model;

import com.zyos.alert.faculty.model.Faculty;
import com.zyos.core.common.api.IRiskFactorCategory;
import com.zyos.core.common.api.IZyosState;
import com.zyos.core.connection.OracleBaseHibernateDAO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.hibernate.type.DoubleType;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.StringType;

import static org.hibernate.criterion.Example.create;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A data access object (DAO) providing persistence and search support for Riskfactorreportstudent
 * entities. Transaction control of the save(), update() and delete() operations can directly
 * support Spring container-managed transactions or they can be augmented to handle user-managed
 * Spring transactions. Each of these methods provides additional information for how to configure
 * it for the desired type of transaction control.
 * 
 * @see com.zyos.alert.studentReport.model.RiskFactorReportStudent
 * @author MyEclipse Persistence Tools
 */
public class RiskFactorReportStudentDAO extends OracleBaseHibernateDAO {
	private static final Logger log = LoggerFactory.getLogger(RiskFactorReportStudentDAO.class);

	public void save(RiskFactorReportStudent transientInstance) {
		log.debug("saving Riskfactorreportstudent instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(RiskFactorReportStudent persistentInstance) {
		log.debug("deleting Riskfactorreportstudent instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public RiskFactorReportStudent findById(java.lang.Long id) {
		log.debug("getting Riskfactorreportstudent instance with id: " + id);
		try {
			RiskFactorReportStudent instance =
				(RiskFactorReportStudent) getSession().get("com.zyos.alert.studentReport.model.Riskfactorreportstudent", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<RiskFactorReportStudent> findByExample(RiskFactorReportStudent instance) {
		log.debug("finding Riskfactorreportstudent instance by example");
		try {
			List<RiskFactorReportStudent> results =
				getSession().createCriteria("com.zyos.alert.studentReport.model.Riskfactorreportstudent")
				.add(create(instance)).list();
			log.debug("find by example successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding Riskfactorreportstudent instance with property: " + propertyName + ", value: " + value);
		try {
			String queryString = "from Riskfactorreportstudent as model where model." + propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findAll() {
		log.debug("finding all Riskfactorreportstudent instances");
		try {
			String queryString = "from Riskfactorreportstudent";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public RiskFactorReportStudent merge(RiskFactorReportStudent detachedInstance) {
		log.debug("merging Riskfactorreportstudent instance");
		try {
			RiskFactorReportStudent result = (RiskFactorReportStudent) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(RiskFactorReportStudent instance) {
		log.debug("attaching dirty Riskfactorreportstudent instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(RiskFactorReportStudent instance) {
		log.debug("attaching clean Riskfactorreportstudent instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	/** jhernandez */
	public List<GraphicData> loadStudentByRiskData() throws Exception {
		StringBuilder hql = new StringBuilder();
		Query qo = null;
		try {

			hql.append("  Select COUNT(rs.idReportStudent) as studentReports,  ");
			hql.append("  (SELECT COUNT(rs.idRiskFactor) FROM  ReportStudent rs, RiskFactor rf, RiskFactorCategory rfc");
			hql.append("  WHERE rs.idRiskFactor = rf.idRiskFactor AND rf.idRiskFactorCategory = rfc.id and rfc.id =:academic AND rs.state =:state )  as academicReports, ");
			hql.append("  (SELECT COUNT(rs.idRiskFactor) FROM ReportStudent rs, RiskFactor rf, RiskFactorCategory rfc ");
			hql.append("  WHERE rs.idRiskFactor = rf.idRiskFactor  AND rf.idRiskFactorCategory = rfc.id and rfc.id =:socioeconomic AND rs.state =:state ) as socioeconomicReports, ");
			hql.append("  (SELECT COUNT(rs.idRiskFactor) FROM ReportStudent rs,	RiskFactor rf, RiskFactorCategory rfc ");
			hql.append("  WHERE rs.idRiskFactor = rf.idRiskFactor	AND rf.idRiskFactorCategory = rfc.id and rfc.id =:institutional AND rs.state =:state ) as institutionalReports, ");
			hql.append("  (SELECT COUNT(rs.idRiskFactor) FROM ReportStudent rs, RiskFactor rf, RiskFactorCategory rfc ");
			hql.append("  WHERE rs.idRiskFactor = rf.idRiskFactor AND rf.idRiskFactorCategory = rfc.id and rfc.id =:personal AND rs.state =:state ) as personalReports 	 ");
			hql.append("  From ReportStudent rs Where rs.state =:state group by 1,2,3,4,5");

			qo = getSession().createSQLQuery(hql.toString()).addScalar("studentReports", StandardBasicTypes.DOUBLE)
					.addScalar("academicReports", DoubleType.INSTANCE).addScalar("socioeconomicReports", DoubleType.INSTANCE)
					.addScalar("institutionalReports", DoubleType.INSTANCE).addScalar("personalReports", DoubleType.INSTANCE)
					.setResultTransformer(Transformers.aliasToBean(GraphicData.class));;
			qo.setParameter("academic", IRiskFactorCategory.ACADEMIC );
			qo.setParameter("socioeconomic", IRiskFactorCategory.SOCIOECONOMIC );
			qo.setParameter("institutional", IRiskFactorCategory.INSTITUTIONAL );
			qo.setParameter("personal", IRiskFactorCategory.PERSONAL );
			qo.setParameter("state", IZyosState.ACTIVE );			
					
					
			List<GraphicData> result = new ArrayList<GraphicData>();
			result = qo.list();

			return result;
		} catch (Exception e) {
			throw e;
		} finally {
			hql = null;
			qo = null;
		}
	}

	/** jhernandez */
	public HashMap<String, Float> loadDetailRiskFactor(Long idRiskFactorCategorySelected) throws Exception {
		StringBuilder hql = new StringBuilder();
		Query qo = null;
		try {
			hql.append(" SELECT ");
			hql.append(" rf.name, ");
			hql.append(" ((CAST(count(rs.idReportStudent) as float) / (select CAST(count(rs.idRiskFactor) AS float) ");
			hql.append(" from RiskFactorReportStudent rs,  RiskFactor rf, RiskFactorCategory rfc ");
			hql.append(" WHERE rs.idRiskFactor = rf.idRiskFactor AND rf.idRiskFactorCategory = rfc.id ");
			hql.append(" AND rfc.id=:idRiskFactorCategorySelected ))*100) ");			
			hql.append(" from ReportStudent rs, RiskFactor rf, RiskFactorCategory rfcc ");
			hql.append(" where rs.idRiskFactor = rf.idRiskFactor AND rf.idRiskFactorCategory = rfcc.id ");
			hql.append(" AND rfcc.id =:idRiskFactorCategorySelected AND rs.state =:state GROUP BY rf.name ");
			
			qo = getSession().createQuery(hql.toString());
			qo.setParameter("state", IZyosState.ACTIVE );
			qo.setParameter("idRiskFactorCategorySelected", idRiskFactorCategorySelected);
			
			List<Object[]> tmp = qo.list();
			HashMap<String, Float> result = new HashMap<String, Float>();
			for (Object[] obj : tmp) {
				result.put((String) obj[0], (Float) obj[1]);
			}
			return result;
		} catch (Exception e) {
			throw e;
		} finally {
			hql = null;
			qo = null;
		}
	}
	
	/** jhernandez */
	public HashMap<String, Float> loadDetailStudentReportDegree(Long idRiskFactorCategorySelected) throws Exception {
		StringBuilder hql = new StringBuilder();
		Query qo = null;
		try {
			hql.append(" SELECT ");
			hql.append(" d.name, ");
			hql.append(" ((CAST(count(rs.idReportStudent) as float) / (select CAST(count(rs.idRiskFactor) AS float) ");
			hql.append(" from RiskFactorReportStudent rs,  RiskFactor rf, RiskFactorCategory rfc ");
			hql.append(" WHERE rs.idRiskFactor = rf.idRiskFactor AND rf.idRiskFactorCategory = rfc.id ");
			hql.append(" AND rfc.id=:idRiskFactorCategorySelected ))*100) ");			
			hql.append(" from ReportStudent rs, RiskFactor rf, RiskFactorCategory rfcc, StudentDegree sd, Degree d ");
			hql.append(" where rs.idRiskFactor = rf.idRiskFactor AND rf.idRiskFactorCategory = rfcc.id ");
			hql.append(" AND rfcc.id =:idRiskFactorCategorySelected AND rs.idStudent = sd.idStudent AND sd.idDegree = d.id AND rs.state =:state GROUP BY d.name ");
			
			qo = getSession().createQuery(hql.toString());
			qo.setParameter("state", IZyosState.ACTIVE );
			qo.setParameter("idRiskFactorCategorySelected", idRiskFactorCategorySelected);
			
			List<Object[]> tmp = qo.list();
			HashMap<String, Float> result = new HashMap<String, Float>();
			for (Object[] obj : tmp) {
				result.put((String) obj[0], (Float) obj[1]);
			}
			return result;
		} catch (Exception e) {
			throw e;
		} finally {
			hql = null;
			qo = null;
		}
	}
	
	/** jhernandez */
	public HashMap<String, Float> loadDetailDegreeList(Long idRiskFactorCategorySelected, Long idDegreeSelected) throws Exception {
		StringBuilder hql = new StringBuilder();
		Query qo = null;
		try {
			hql.append(" SELECT ");
			hql.append(" rf.name, ");
			hql.append(" ((CAST(count(rs.idReportStudent) as float) / (select CAST(count(rs.idRiskFactor) AS float) ");
			hql.append(" from RiskFactorReportStudent rs,  RiskFactor rf, RiskFactorCategory rfc ");
			hql.append(" WHERE rs.idRiskFactor = rf.idRiskFactor AND rf.idRiskFactorCategory = rfc.id ");
			hql.append(" ))*100) ");			
			hql.append(" from ReportStudent rs, RiskFactor rf, RiskFactorCategory rfcc, StudentDegree sd, Degree d ");
			hql.append(" where rs.idRiskFactor = rf.idRiskFactor AND rf.idRiskFactorCategory = rfcc.id ");
			hql.append(" AND rfcc.id=:idRiskFactorCategorySelected  ");			
			hql.append(" AND rs.idStudent = sd.idStudent AND sd.idDegree = d.id  ");
			hql.append(" AND d.id =:idDegreeSelected ");
			hql.append(" AND rs.state =:state GROUP BY rf.name ");
			
			qo = getSession().createQuery(hql.toString());
			qo.setParameter("state", IZyosState.ACTIVE );
			qo.setParameter("idRiskFactorCategorySelected", idRiskFactorCategorySelected);
			qo.setParameter("idDegreeSelected", idDegreeSelected);
			
			List<Object[]> tmp = qo.list();
			HashMap<String, Float> result = new HashMap<String, Float>();
			for (Object[] obj : tmp) {
				result.put((String) obj[0], (Float) obj[1]);
			}
			return result;
		} catch (Exception e) {
			throw e;
		} finally {
			hql = null;
			qo = null;
		}
	}
	
	/** jhernandez */
	public HashMap<String, Float> loadDetailFacultyList(Long idRiskFactorCategorySelected, Long idFacultySelected) throws Exception {
		StringBuilder hql = new StringBuilder();
		Query qo = null;
		try {
			hql.append(" SELECT ");
			hql.append(" rf.name, ");
			hql.append(" ((CAST(count(rs.idReportStudent) as float) / (select CAST(count(rs.idRiskFactor) AS float) ");
			hql.append(" from RiskFactorReportStudent rs,  RiskFactor rf, RiskFactorCategory rfc ");
			hql.append(" WHERE rs.idRiskFactor = rf.idRiskFactor AND rf.idRiskFactorCategory = rfc.id ");
			hql.append(" ))*100) ");			
			hql.append(" from ReportStudent rs, RiskFactor rf, RiskFactorCategory rfcc, StudentDegree sd, Degree d, FacultyDegree fd, Faculty f ");
			hql.append(" where rs.idRiskFactor = rf.idRiskFactor AND rf.idRiskFactorCategory = rfcc.id ");
			hql.append(" AND rfcc.id=:idRiskFactorCategorySelected  ");			
			hql.append(" AND rs.idStudent = sd.idStudent AND sd.idDegree = d.id  ");
			hql.append(" AND fd.idDegree = d.id AND fd.idFaculty =:idFacultySelected ");
			hql.append(" AND rs.state =:state GROUP BY rf.name ");
			
			qo = getSession().createQuery(hql.toString());
			qo.setParameter("state", IZyosState.ACTIVE );
			qo.setParameter("idRiskFactorCategorySelected", idRiskFactorCategorySelected);
			qo.setParameter("idFacultySelected", idFacultySelected);
			
			List<Object[]> tmp = qo.list();
			HashMap<String, Float> result = new HashMap<String, Float>();
			for (Object[] obj : tmp) {
				result.put((String) obj[0], (Float) obj[1]);
			}
			return result;
		} catch (Exception e) {
			throw e;
		} finally {
			hql = null;
			qo = null;
		}
	}
	
	/** jhernandez */
	public List<Degree> loadStudentReportDetailDegree(Long idRiskFactorCategoryDegree) throws Exception {
		StringBuilder hql = new StringBuilder();
		Query qo = null;
		try {
			hql.append(" SELECT ");
			hql.append(" d.id as id, d.name as name");
			hql.append(" from ReportStudent rs, RiskFactor rf, RiskFactorCategory rfcc, StudentDegree sd, Degree d ");
			hql.append(" where rs.idRiskFactor = rf.idRiskFactor AND rf.idRiskFactorCategory = rfcc.id ");
			hql.append(" AND rfcc.id =:idRiskFactorCategoryDegree AND rs.idStudent = sd.idStudent AND sd.idDegree = d.id AND rs.state =:state GROUP BY d.id, d.name ");
			
			qo = getSession().createSQLQuery(hql.toString())
				.addScalar("id", StandardBasicTypes.LONG)
				.addScalar("name", StringType.INSTANCE)
				.setResultTransformer(Transformers.aliasToBean(Degree.class));
				
			qo.setParameter("state", IZyosState.ACTIVE );
			qo.setParameter("idRiskFactorCategoryDegree", idRiskFactorCategoryDegree);
			
			List<Degree> result = qo.list();
			
			return result;
		} catch (Exception e) {
			throw e;
		} finally {
			hql = null;
			qo = null;
		}
	}
	
	/** jhernandez */
	public List<Faculty> loadStudentReportDetailFaculty(Long idRiskFactorCategoryFaculty) throws Exception {
		StringBuilder hql = new StringBuilder();
		Query qo = null;
		try {
			hql.append(" SELECT ");
			hql.append(" f.idFaculty as idFaculty, f.name as name");
			hql.append(" from ReportStudent rs, RiskFactor rf, RiskFactorCategory rfcc, StudentDegree sd, Degree d, FacultyDegree fd, Faculty f ");
			hql.append(" where rs.idRiskFactor = rf.idRiskFactor AND rf.idRiskFactorCategory = rfcc.id ");
			hql.append(" AND rfcc.id =:idRiskFactorCategoryFaculty AND rs.idStudent = sd.idStudent AND sd.idDegree = d.id ");
			hql.append(" AND d.id = fd.idDegree AND fd.idFaculty = f.idFaculty ");
			hql.append(" AND rs.state =:state GROUP BY f.idFaculty, f.name ");		
			
			
			qo = getSession().createSQLQuery(hql.toString())
				.addScalar("idFaculty", StandardBasicTypes.LONG)
				.addScalar("name", StringType.INSTANCE)
				.setResultTransformer(Transformers.aliasToBean(Faculty.class));
				
			qo.setParameter("state", IZyosState.ACTIVE );
			qo.setParameter("idRiskFactorCategoryFaculty", idRiskFactorCategoryFaculty);
			
			List<Faculty> result = qo.list();
			
			return result;
		} catch (Exception e) {
			throw e;
		} finally {
			hql = null;
			qo = null;
		}
	}
	
	
	/** jhernandez */
	public HashMap<String, Float> loadStudentReportDegree() throws Exception {
		StringBuilder hql = new StringBuilder();
		Query qo = null;
		try {
			hql.append(" SELECT ");
			hql.append(" d.name, ");
			hql.append(" ((CAST(count(rs.idReportStudent) as float) / (select CAST(count(rs.idRiskFactor) AS float) ");
			hql.append(" from RiskFactorReportStudent rs,  RiskFactor rf, RiskFactorCategory rfc ");
			hql.append(" WHERE rs.idRiskFactor = rf.idRiskFactor AND rf.idRiskFactorCategory = rfc.id ");
			hql.append(" ))*100) ");			
			hql.append(" from ReportStudent rs, RiskFactor rf, RiskFactorCategory rfcc, StudentDegree sd, Degree d ");
			hql.append(" where rs.idRiskFactor = rf.idRiskFactor AND rf.idRiskFactorCategory = rfcc.id ");
			hql.append(" AND rs.idStudent = sd.idStudent AND sd.idDegree = d.id AND rs.state =:state GROUP BY d.name ");
			
			qo = getSession().createQuery(hql.toString());
			qo.setParameter("state", IZyosState.ACTIVE );			
			
			List<Object[]> tmp = qo.list();
			HashMap<String, Float> result = new HashMap<String, Float>();
			for (Object[] obj : tmp) {
				result.put((String) obj[0], (Float) obj[1]);
			}
			return result;
		} catch (Exception e) {
			throw e;
		} finally {
			hql = null;
			qo = null;
		}
	}
	
	/** jhernandez */
	public HashMap<String, Float> loadDetailStudentReportFaculty() throws Exception {
		StringBuilder hql = new StringBuilder();
		Query qo = null;
		try {
			hql.append(" SELECT ");
			hql.append(" f.name, ");
			hql.append(" ((CAST(count(rs.idReportStudent) as float) / (select CAST(count(rs.idRiskFactor) AS float) ");
			hql.append(" from RiskFactorReportStudent rs,  RiskFactor rf, RiskFactorCategory rfc ");
			hql.append(" WHERE rs.idRiskFactor = rf.idRiskFactor AND rf.idRiskFactorCategory = rfc.id ");
			hql.append(" ))*100) ");			
			hql.append(" from ReportStudent rs, RiskFactor rf, RiskFactorCategory rfcc, StudentDegree sd, Degree d, FacultyDegree fd, Faculty f ");
			hql.append(" where rs.idRiskFactor = rf.idRiskFactor AND rf.idRiskFactorCategory = rfcc.id ");
			hql.append(" AND rs.idStudent = sd.idStudent AND sd.idDegree = d.id  ");
			hql.append(" AND fd.idDegree = d.id AND fd.idFaculty = f.idFaculty ");
			hql.append(" AND rs.state =:state GROUP BY f.name ");
			
			qo = getSession().createQuery(hql.toString());
			qo.setParameter("state", IZyosState.ACTIVE );
			
			
			List<Object[]> tmp = qo.list();
			HashMap<String, Float> result = new HashMap<String, Float>();
			for (Object[] obj : tmp) {
				result.put((String) obj[0], (Float) obj[1]);
			}
			return result;
		} catch (Exception e) {
			throw e;
		} finally {
			hql = null;
			qo = null;
		}
	}
	
	/** jhernandez */
	public HashMap<String, Float> loadDetailStudentReportFaculty(Long idRiskFactorCategorySelected) throws Exception {
		StringBuilder hql = new StringBuilder();
		Query qo = null;
		try {
			hql.append(" SELECT ");
			hql.append(" f.name, ");
			hql.append(" ((CAST(count(rs.idReportStudent) as float) / (select CAST(count(rs.idRiskFactor) AS float) ");
			hql.append(" from RiskFactorReportStudent rs,  RiskFactor rf, RiskFactorCategory rfc ");
			hql.append(" WHERE rs.idRiskFactor = rf.idRiskFactor AND rf.idRiskFactorCategory = rfc.id ");
			hql.append(" AND rfc.id=:idRiskFactorCategorySelected ))*100) ");			
			hql.append(" from ReportStudent rs, RiskFactor rf, RiskFactorCategory rfcc, StudentDegree sd, Degree d, FacultyDegree fd, Faculty f ");
			hql.append(" where rs.idRiskFactor = rf.idRiskFactor AND rf.idRiskFactorCategory = rfcc.id AND rfcc.id=:idRiskFactorCategorySelected  ");
			hql.append(" AND rs.idStudent = sd.idStudent AND sd.idDegree = d.id  ");
			hql.append(" AND fd.idDegree = d.id AND fd.idFaculty = f.idFaculty ");
			hql.append(" AND rs.state =:state GROUP BY f.name ");
			
			qo = getSession().createQuery(hql.toString());
			qo.setParameter("state", IZyosState.ACTIVE );
			qo.setParameter("idRiskFactorCategorySelected", idRiskFactorCategorySelected);
			
			List<Object[]> tmp = qo.list();
			HashMap<String, Float> result = new HashMap<String, Float>();
			for (Object[] obj : tmp) {
				result.put((String) obj[0], (Float) obj[1]);
			}
			return result;
		} catch (Exception e) {
			throw e;
		} finally {
			hql = null;
			qo = null;
		}
	}
	
	/** jhernandez */
	public Long loadCountStudentReport() throws Exception {
		StringBuilder hql = new StringBuilder();
		Query qo = null;
		try {
			hql.append(" SELECT ");			
			hql.append(" (Count(rs.idReportStudent) )  ");				
			hql.append(" from ReportStudent rs ");
			hql.append(" where rs.state =:state  ");			
			
			qo = getSession().createQuery(hql.toString());
			qo.setParameter("state", IZyosState.ACTIVE );	
			
			Long result = (Long) qo.uniqueResult();
			
			return result;
		} catch (Exception e) {
			throw e;
		} finally {
			hql = null;
			qo = null;
		}
	}

	/**@author jhernandez
	 * @author SIAT-TUNJA */
	public List<GraphicData> loadStudentByRiskDataTunja() throws Exception {
		StringBuilder hql = new StringBuilder();
		Query qo = null;
		try {

			hql.append("  Select ");
			hql.append("  (SELECT COUNT(rs.idRiskFactor) FROM  ReportStudent rs, RiskFactor rf, RiskFactorCategory rfc");
			hql.append("  WHERE rs.idRiskFactor = rf.idRiskFactor AND rf.idRiskFactorCategory = rfc.id and rfc.id =:academic AND rs.state =:state )  as academicReports, ");
			hql.append("  (SELECT COUNT(rs.idRiskFactor) FROM ReportStudent rs, RiskFactor rf, RiskFactorCategory rfc ");
			hql.append("  WHERE rs.idRiskFactor = rf.idRiskFactor  AND rf.idRiskFactorCategory = rfc.id and rfc.id =:socioeconomic AND rs.state =:state ) as socioeconomicReports, ");
			hql.append("  (SELECT COUNT(rs.idRiskFactor) FROM ReportStudent rs,	RiskFactor rf, RiskFactorCategory rfc ");
			hql.append("  WHERE rs.idRiskFactor = rf.idRiskFactor	AND rf.idRiskFactorCategory = rfc.id and rfc.id =:institutional AND rs.state =:state ) as institutionalReports, ");
			hql.append("  (SELECT COUNT(rs.idRiskFactor) FROM ReportStudent rs, RiskFactor rf, RiskFactorCategory rfc ");
			hql.append("  WHERE rs.idRiskFactor = rf.idRiskFactor AND rf.idRiskFactorCategory = rfc.id and rfc.id =:personal AND rs.state =:state ) as personalReports	 ");
			hql.append("  From ReportStudent rs Where rs.state =:state group by 1,2,3,4");

			qo = getSession().createSQLQuery(hql.toString())
					.addScalar("academicReports", DoubleType.INSTANCE).addScalar("socioeconomicReports", DoubleType.INSTANCE)
					.addScalar("institutionalReports", DoubleType.INSTANCE).addScalar("personalReports", DoubleType.INSTANCE)
					.setResultTransformer(Transformers.aliasToBean(GraphicData.class));;
			qo.setParameter("academic", IRiskFactorCategory.ACADEMIC );
			qo.setParameter("socioeconomic", IRiskFactorCategory.SOCIOECONOMIC );
			qo.setParameter("institutional", IRiskFactorCategory.INSTITUTIONAL );
			qo.setParameter("personal", IRiskFactorCategory.PERSONAL );
			qo.setParameter("state", IZyosState.ACTIVE );			
					
					
			List<GraphicData> result = new ArrayList<GraphicData>();
			result = qo.list();

			return result;
		} catch (Exception e) {
			throw e;
		} finally {
			hql = null;
			qo = null;
		}
	}
}
