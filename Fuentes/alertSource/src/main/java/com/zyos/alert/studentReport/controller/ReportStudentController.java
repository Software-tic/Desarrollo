package com.zyos.alert.studentReport.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import org.hibernate.Transaction;

import com.zyos.alert.faculty.model.Faculty;
import com.zyos.alert.studentReport.model.Degree;
import com.zyos.alert.studentReport.model.DegreeDAO;
import com.zyos.alert.studentReport.model.GraphicData;
import com.zyos.alert.studentReport.model.Observation;
import com.zyos.alert.studentReport.model.ObservationDAO;
import com.zyos.alert.studentReport.model.ReportStudent;
import com.zyos.alert.studentReport.model.ReportStudentDAO;
import com.zyos.alert.studentReport.model.ReportType;
import com.zyos.alert.studentReport.model.RiskFactor;
import com.zyos.alert.studentReport.model.RiskFactorCategory;
import com.zyos.alert.studentReport.model.RiskFactorDAO;
import com.zyos.alert.studentReport.model.RiskFactorReportStudentDAO;
import com.zyos.alert.studentReport.model.StatusReportStudent;
import com.zyos.alert.studentReport.model.Student;
import com.zyos.alert.studentReport.model.StudentDAO;
import com.zyos.core.common.controller.ZyosController;
import com.zyos.core.lo.user.model.ZyosGroup;
import com.zyos.core.lo.user.model.ZyosUser;
import com.zyos.core.lo.user.model.ZyosUserDAO;

public class ReportStudentController extends ZyosController {

	/**@author oortiz*/
	public Object[] loadReportStudentData() throws Exception {
		ReportStudentDAO dao = new ReportStudentDAO();
		try {
			return dao.loadReportStudentData();
		} catch (Exception e) {
			throw e;
		} finally {
			dao.getSession().close();
			dao = null;
		}
	}

	/**@author oortiz*/
	public List<GraphicData> loadStudentByRiskData() throws Exception {
		RiskFactorReportStudentDAO dao = new RiskFactorReportStudentDAO();
		try {
			return dao.loadStudentByRiskData();
		} catch (Exception e) {
			throw e;
		} finally {
			dao.getSession().close();
			dao = null;
		}
	}

	/**@author oortiz*/
	public Long loadTotalStudent(String currentDate, String currentHour, Long currentDay) throws Exception {
		ReportStudentDAO dao = new ReportStudentDAO();
		try {
			return dao.loadTotalStudent(currentDate, currentHour, currentDay);
		} catch (Exception e) {
			throw e;
		} finally {
			dao.getSession().close();
			dao = null;
		}
	}

	/**@author oortiz
	 * @throws Exception */
	public List<ReportStudent> loadReportStudentList(Long idZyosGroup, Long ZyosUser, ReportStudent reportSearch) throws Exception {
		ReportStudentDAO dao = new ReportStudentDAO();
		try {
			return dao.loadReportStudentListPostgres(idZyosGroup, ZyosUser); //loadReportStudentList(idZyosGroup, ZyosUser);
		} catch (Exception e) {
			throw e;
		} finally {
			dao.getSession().close();
			dao = null;
		}
	}
	
	/**@author jhernandez
	 * @throws Exception */
	public List<ReportStudent> loadSearchReportStudentList(Long idZyosGroup, Long ZyosUser, ReportStudent reportSearch) throws Exception {
		ReportStudentDAO dao = new ReportStudentDAO();
		try {
			return dao.loadSearchReportStudentList(idZyosGroup, ZyosUser, reportSearch);
		} catch (Exception e) {
			throw e;
		} finally {
			dao.getSession().close();
			dao = null;
		}
	}

	/**@author oortiz*/
	public List<ZyosUser> loadResponsibleListByRole(Long idRole) throws Exception {
		ZyosUserDAO dao = new ZyosUserDAO();
		try {
			return dao.loadResponsibleListByRole(idRole);
		} catch (Exception e) {
			throw e;
		} finally {
			dao.getSession().close();
			dao = null;
		}
	}

	/**@author oortiz*/
	public void saveObservation(Observation observation, String document) throws Exception {
		ObservationDAO dao = new ObservationDAO();
		Transaction tx = null;
		try {
			tx = dao.getSession().beginTransaction();
			observation.initializing(document, true);
			dao.save(observation);
			tx.commit();
			
		} catch (Exception e) {
			throw e;
		} finally {
			dao.getSession().close();
			tx = null;
			dao = null;
		}
	}

	/**@author oortiz*/
	public void updateReportStudent(ReportStudent reportStudentSelected, String document) throws Exception {
		ReportStudentDAO dao = new ReportStudentDAO();
		Transaction tx = null;
		try {
			tx = dao.getSession().beginTransaction();
			reportStudentSelected.initializing(document, false);
			dao.updateReportStudent(reportStudentSelected);
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			throw e;
		} finally {
			dao.getSession().close();
			tx = null;
			dao = null;
		}
	}

	/**@author oortiz*/
	public List<Observation> loadObservationByStudent(Long idStudent) throws Exception {
		ObservationDAO dao = new ObservationDAO();
		try {
			return dao.loadObservationByStudent(idStudent);
		} catch (Exception e) {
			throw e;
		} finally {
			dao.getSession().close();
			dao = null;
		}
	}

	/**@author oortiz*/
	public void closeProcess(Long idReportStudent) throws Exception {
		ReportStudentDAO dao = new ReportStudentDAO();
		Transaction tx = null;
		try {
			tx = dao.getSession().beginTransaction();
			dao.closeProcess(idReportStudent);
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			throw e;
		} finally {
			dao.getSession().close();
			tx = null;
			dao = null;
		}
	}
	
	/**@author oortiz*/
	public ReportStudent loadReportStudent() throws Exception {
		ReportStudentDAO dao = new ReportStudentDAO();
		try {
			return dao.loadReportStudent();
		} catch (Exception e) {
			throw e;
		} finally {
			dao.getSession().close();
			dao = null;
		}
	}

	/**@author jhernandez
	 * @throws Exception */
	public BigDecimal loadReportStudentManualDataClassMate() throws Exception {
		ReportStudentDAO dao = new ReportStudentDAO();
		try {
			return dao.loadReportStudentManualDataClassMate();
		} catch (Exception e) {
			throw e;
		} finally {
			dao.getSession().close();
			dao = null;
		}	
	}
	
	/**@author jhernandez
	 * @throws Exception */
	public BigDecimal loadReportStudentManualDataTeacher() throws Exception {
		ReportStudentDAO dao = new ReportStudentDAO();
		try {
			return dao.loadReportStudentManualDataTeacher();
		} catch (Exception e) {
			throw e;
		} finally {
			dao.getSession().close();
			dao = null;
		}	
	}
	
	/**@author jhernandez
	 * @throws Exception */
	public BigDecimal loadReportStudentManualDataSelf() throws Exception {
		ReportStudentDAO dao = new ReportStudentDAO();
		try {
			return dao.loadReportStudentManualDataSelf();
		} catch (Exception e) {
			throw e;
		} finally {
			dao.getSession().close();
			dao = null;
		}	
	}
	
	/**@author jhernandez
	 * @throws Exception */
	public BigDecimal loadReportStudentManualDataFreeUser() throws Exception {
		ReportStudentDAO dao = new ReportStudentDAO();
		try {
			return dao.loadReportStudentManualDataFreeUser();
		} catch (Exception e) {
			throw e;
		} finally {
			dao.getSession().close();
			dao = null;
		}	
	}
	
	

	
	public String loadAdminEmailList() throws Exception {
		ZyosUserDAO dao = new ZyosUserDAO();
		try {
			return dao.loadAdminEmailList();
		} catch (Exception e) {
			throw e;
		} finally {
			dao.getSession().close();
			dao = null;
		}
	}
	
	/**@author jhernandez*/
	public ReportStudent validateReportDuplicate(Long idStudent, Long idRiskFactor) throws Exception {
		ReportStudentDAO dao = new ReportStudentDAO();
		try {
			return dao.validateReportDuplicate(idStudent,idRiskFactor);
		} catch (Exception e) {
			throw e;
		} finally {
			dao.getSession().close();
			dao = null;
		}
	} 
	
	/**
	 * @author jhernandez
	 * */
	public List<Degree> loadDegreeList()
			throws Exception {
		DegreeDAO dao = new DegreeDAO();
		Transaction tx = null;
		try {
			tx = dao.getSession().beginTransaction();
			return dao.loadDegreeList();
		} catch (Exception e) {
			tx.rollback();
			throw e;
		} finally {
			dao.getSession().close();
			dao = null;
			tx = null;
		}
	}
	
	
	/**
	 * @author jhernandez
	 * */
	public Student loadDataStudentReport(ReportStudent reportStudentSelected)
			throws Exception {
		ReportStudentDAO dao = new ReportStudentDAO();
		Transaction tx = null;
		try {
			tx = dao.getSession().beginTransaction();
			return dao.loadDataStudentReport(reportStudentSelected);
		} catch (Exception e) {
			tx.rollback();
			throw e;
		} finally {
			dao.getSession().close();
			dao = null;
			tx = null;
		}
	}
	

	/**
	 * @throws Exception
	 * @autor jhernandez
	 **/
	public void updateStudentData(Student student, String documentNumber, ReportStudent reportStudentSelected) throws Exception {
		ZyosUserDAO dao = new ZyosUserDAO();
		StudentDAO daos = new StudentDAO();
		ReportStudentDAO daoc= new ReportStudentDAO();

		Transaction tx = dao.getSession().beginTransaction();
		try {

			student.initializing(documentNumber, false);

			dao.updateStudentData(student);
			daos.updateReportStudentData(student);
			daoc.updateFirstIntervention(reportStudentSelected, student);

			tx.commit();
		} catch (RuntimeException e) {
			tx.rollback();
			throw e;
		} finally {
			dao.getSession().close();
			dao = null;
		}
	}
	
	public void saveOrUpdateParameter(RiskFactor riskFactor) {
		RiskFactorDAO dao = new RiskFactorDAO();
		try {
			dao.getSession().saveOrUpdate(riskFactor);
			dao.getSession().beginTransaction().commit();
		} catch (RuntimeException e) {
			dao.getSession().beginTransaction().rollback();
			throw e;
		} finally {
			dao.getSession().close();
			dao = null;
		}
	} 
	
	/**
	 * @author jhernandez
	 * */
	public RiskFactor validateRiskFactorUsed(Long idRiskFactor)
			throws Exception {
		RiskFactorDAO dao = new RiskFactorDAO();
		Transaction tx = null;
		try {
			tx = dao.getSession().beginTransaction();
			return dao.validateRiskFactorUsed(idRiskFactor);
		} catch (Exception e) {
			tx.rollback();
			throw e;
		} finally {
			dao.getSession().close();
			dao = null;
			tx = null;
		}
	}
	
	public void saveRiskFactor(String documentNumber, RiskFactor riskFactor) {
	RiskFactorDAO dao = new RiskFactorDAO();
	Transaction tx = null;
	
	try {
		tx = dao.getSession().beginTransaction();
		riskFactor.initializing(documentNumber, true);
		dao.save(riskFactor);

		tx.commit();
	} catch (RuntimeException e) {
		tx.rollback();
		throw e;
	} finally {
		dao.getSession().close();
		dao = null;
	}
}
	

	/**@author jhernandez*/
	public void deleteRiskFactor (String document, RiskFactor riskFactor) throws Exception {
		RiskFactorDAO dao = new RiskFactorDAO();
		Transaction tx = null;
		try {
			tx = dao.getSession().beginTransaction();
			riskFactor.initializing(document, false);
			dao.deleteRiskFactor(riskFactor);
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			throw e;
		} finally {
			dao.getSession().close();
			tx = null;
			dao = null;
		}
	}
	
	/**
	 * @throws Exception
	 * @autor jhernandez
	 **/
	public void deleteReportStudent(ReportStudent reportStudentSelected, String documentNumber)
			throws Exception {	
		
		ReportStudentDAO dao = new ReportStudentDAO();
			
		Transaction tx = dao.getSession().beginTransaction();
		try {

			reportStudentSelected.initializing(documentNumber, false);
			dao.deleteReportStudent(reportStudentSelected);			
			
			tx.commit();
			
		} catch (RuntimeException e) {
			tx.rollback();
			throw e;
		} finally {
			dao.getSession().close();
			dao = null;
		}
	}
	
	
	/**@author jhernandez*/
	public HashMap<String, Float> loadDetailRiskFactor(Long idRiskFactorCategorySelected) throws Exception {
		RiskFactorReportStudentDAO dao = new RiskFactorReportStudentDAO();
		try {
			return dao.loadDetailRiskFactor(idRiskFactorCategorySelected);
		} catch (Exception e) {
			throw e;
		} finally {
			dao.getSession().close();
			dao = null;
		}
	} 
	
	/**@author jhernandez*/
	public HashMap<String, Float> loadDetailStudentReportDegree(Long idRiskFactorCategorySelected) throws Exception {
		RiskFactorReportStudentDAO dao = new RiskFactorReportStudentDAO();
		try {
			return dao.loadDetailStudentReportDegree(idRiskFactorCategorySelected);
		} catch (Exception e) {
			throw e;
		} finally {
			dao.getSession().close();
			dao = null;
		}
	} 
	

	/**@author jhernandez*/
	public HashMap<String, Float> loadDetailFacultyList(Long idRiskFactorCategorySelected, Long idDegreeSelected) throws Exception {
		RiskFactorReportStudentDAO dao = new RiskFactorReportStudentDAO();
		try {
			return dao.loadDetailFacultyList(idRiskFactorCategorySelected,idDegreeSelected );
		} catch (Exception e) {
			throw e;
		} finally {
			dao.getSession().close();
			dao = null;
		}
	} 
	
	/**@author jhernandez*/
	public HashMap<String, Float> loadDetailDegreeList(Long idRiskFactorCategorySelected, Long idDegreeSelected) throws Exception {
		RiskFactorReportStudentDAO dao = new RiskFactorReportStudentDAO();
		try {
			return dao.loadDetailDegreeList(idRiskFactorCategorySelected,idDegreeSelected );
		} catch (Exception e) {
			throw e;
		} finally {
			dao.getSession().close();
			dao = null;
		}
	} 
	
	/**@author jhernandez*/
	public List<Degree> loadStudentReportDetailDegree(Long idRiskFactorCategoryDegree) throws Exception {
		RiskFactorReportStudentDAO dao = new RiskFactorReportStudentDAO();
		try {
			return dao.loadStudentReportDetailDegree(idRiskFactorCategoryDegree);
		} catch (Exception e) {
			throw e;
		} finally {
			dao.getSession().close();
			dao = null;
		}
	} 
	
	/**@author jhernandez*/
	public List<Faculty> loadStudentReportDetailFaculty(Long idRiskFactorCategoryFaculty) throws Exception {
		RiskFactorReportStudentDAO dao = new RiskFactorReportStudentDAO();
		try {
			return dao.loadStudentReportDetailFaculty(idRiskFactorCategoryFaculty);
		} catch (Exception e) {
			throw e;
		} finally {
			dao.getSession().close();
			dao = null;
		}
	}
	
	/**@author jhernandez*/
	public HashMap<String, Float> loadStudentReportDegree() throws Exception {
		RiskFactorReportStudentDAO dao = new RiskFactorReportStudentDAO();
		try {
			return dao.loadStudentReportDegree();
		} catch (Exception e) {
			throw e;
		} finally {
			dao.getSession().close();
			dao = null;
		}
	}
	
	/**@author jhernandez*/
	public HashMap<String, Float> loadDetailStudentReportFaculty() throws Exception {
		RiskFactorReportStudentDAO dao = new RiskFactorReportStudentDAO();
		try {
			return dao.loadDetailStudentReportFaculty();
		} catch (Exception e) {
			throw e;
		} finally {
			dao.getSession().close();
			dao = null;
		}
	} 
	
	/**@author jhernandez*/
	public HashMap<String, Float> loadDetailStudentReportFaculty(Long idRiskFactorCategorySelected) throws Exception {
		RiskFactorReportStudentDAO dao = new RiskFactorReportStudentDAO();
		try {
			return dao.loadDetailStudentReportFaculty(idRiskFactorCategorySelected);
		} catch (Exception e) {
			throw e;
		} finally {
			dao.getSession().close();
			dao = null;
		}
	} 
	
	/**@author jhernandez*/
	public Long loadCountReportStudent() throws Exception {
		RiskFactorReportStudentDAO dao = new RiskFactorReportStudentDAO();
		try {
			return dao.loadCountStudentReport();
		} catch (Exception e) {
			throw e;
		} finally {
			dao.getSession().close();
			dao = null;
		}
	} 
	
	/**@author jhernandez*/
	public List<RiskFactorCategory> loadReportRiskFactorCategoryList() throws Exception {
		ReportStudentDAO dao = new ReportStudentDAO();
		try {
			return dao.loadReportRiskFactorCategoryList();
		} catch (Exception e) {
			throw e;
		} finally {
			dao.getSession().close();
			dao = null;
		}
	} 
	
	/**@author jhernandez*/
	public List<ZyosGroup> loadReportZyosGroupList() throws Exception {
		ReportStudentDAO dao = new ReportStudentDAO();
		try {
			return dao.loadReportZyosGroupList();
		} catch (Exception e) {
			throw e;
		} finally {
			dao.getSession().close();
			dao = null;
		}
	} 
	
	/**@author jhernandez*/
	public List<Faculty> loadReportFacultyStudentList() throws Exception {
		ReportStudentDAO dao = new ReportStudentDAO();
		try {
			return dao.loadReportFacultyStudentList();
		} catch (Exception e) {
			throw e;
		} finally {
			dao.getSession().close();
			dao = null;
		}
	}
	
	/**@author jhernandez*/
	public List<StatusReportStudent> loadReportStatusList() throws Exception {
		ReportStudentDAO dao = new ReportStudentDAO();
		try {
			return dao.loadReportStatusList();
		} catch (Exception e) {
			throw e;
		} finally {
			dao.getSession().close();
			dao = null;
		}
	} 
	
	/**@author jhernandez*/
	public List<ReportType> loadReporStudentReportType() throws Exception {
		ReportStudentDAO dao = new ReportStudentDAO();
		try {
			return dao.loadReporStudentReportType();
		} catch (Exception e) {
			throw e;
		} finally {
			dao.getSession().close();
			dao = null;
		}
	} 
	
	/**@author jhernandez*/
	public List<RiskFactor> loadReporStudentRiskFactorList(Long idRiskFactorCategory) throws Exception {
		ReportStudentDAO dao = new ReportStudentDAO();
		try {
			return dao.loadReporStudentRiskFactorList(idRiskFactorCategory);
		} catch (Exception e) {
			throw e;
		} finally {
			dao.getSession().close();
			dao = null;
		}
	}

	/**
	 * @author Miguel 19/06/2014 16:00:55
	 * @param idAdviser
	 * @return
	 */
	public ZyosUser loadAdvisorById(Long idAdviser) throws Exception {
		ZyosUserDAO dao = new ZyosUserDAO();
		Transaction tx = null;
		try {
			tx = dao.getSession().beginTransaction();
			return dao.loadZyosUser(idAdviser);
		} catch (Exception e) {
			if (tx != null && tx.isActive())
				tx.rollback();
			throw e;
		}finally{
			dao.getSession().close();
			dao = null;
			tx = null;
		}
	} 	
}
