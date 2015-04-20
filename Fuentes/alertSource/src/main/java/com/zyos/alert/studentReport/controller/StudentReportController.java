package com.zyos.alert.studentReport.controller;

import java.util.List;

import org.hibernate.Transaction;

import com.zyos.alert.studentReport.model.Observation;
import com.zyos.alert.studentReport.model.ObservationDAO;
import com.zyos.alert.studentReport.model.ReportStudent;
import com.zyos.alert.studentReport.model.ReportStudentDAO;
import com.zyos.alert.studentReport.model.RiskFactor;
import com.zyos.alert.studentReport.model.RiskFactorDAO;
import com.zyos.alert.studentReport.model.RiskFactorReportStudent;
import com.zyos.alert.studentReport.model.RiskFactorReportStudentDAO;
import com.zyos.alert.studentReport.model.Student;
import com.zyos.alert.studentReport.model.StudentDAO;
import com.zyos.alert.studentReport.model.StudentDegreeDAO;
import com.zyos.core.common.controller.ZyosController;
import com.zyos.core.common.util.ManageDate;

public class StudentReportController extends ZyosController {

	public void saveReportStudent(ReportStudent reportStudent,
			String documentNumber) {
		ReportStudentDAO dao = new ReportStudentDAO();
		RiskFactorReportStudentDAO daos = new RiskFactorReportStudentDAO();

		Transaction tx = dao.getSession().beginTransaction();
		try {

			reportStudent.initializing(documentNumber, true);
			dao.save(reportStudent);

			tx.commit();
		} catch (RuntimeException e) {
			tx.rollback();
			throw e;
		} finally {
			dao.getSession().close();
			dao = null;
		}
	}
	
	/** @autor jhernandez **/
	public void saveRiskFactor(ReportStudent reportStudent,
			String documentNumber) {
		ReportStudentDAO dao = new ReportStudentDAO();
		RiskFactorReportStudentDAO daos = new RiskFactorReportStudentDAO();

		Transaction tx = dao.getSession().beginTransaction();
		try {

			dao.getSession().flush();

			RiskFactorReportStudent riskFactorAux = new RiskFactorReportStudent();
			riskFactorAux.setIdRiskFactor(reportStudent.getIdRiskFactor());
			riskFactorAux
					.setIdReportStudent(reportStudent.getIdReportStudent());
			riskFactorAux.initializing(documentNumber, true);
			daos.save(riskFactorAux);

			tx.commit();
		} catch (RuntimeException e) {
			tx.rollback();
			throw e;
		} finally {
			dao.getSession().close();
			dao = null;
		}
	}

	/** @throws Exception 
	 * @autor jhernandez **/
	public Student loadStudentCurrent(Long idZyosUser) throws Exception {
		StudentDAO dao = new StudentDAO();
		Transaction tx = dao.getSession().beginTransaction();
		try {

			return dao.loadStudentCurrent(idZyosUser);

		} catch (Exception e) {

			tx.rollback();
			throw e;

		} finally {
			dao.getSession().close();
			dao = null;
		}
	}

	/** @autor jhernandez **/
	public void saveReportStudentObservation(ReportStudent reportStudent,
			String documentNumber) {
		ReportStudentDAO dao = new ReportStudentDAO();
		ObservationDAO daos = new ObservationDAO();

		Transaction tx = dao.getSession().beginTransaction();
		try {

			dao.getSession().flush();

			Observation observation = new Observation();
			observation.initializing(documentNumber, true);

			observation.setIdStatusReportStudent(reportStudent
					.getIdStatusReportStudent());
			observation.setIdAdviser(reportStudent.getIdAdviser());
			observation.setIdStage(reportStudent.getIdStage());

			observation.setIdReportStudent(reportStudent.getIdReportStudent());
			observation.setDateIntervention(ManageDate
					.getCurrentDate(ManageDate.YYYY_MM_DD));
			observation.setDetailObservation(reportStudent.getDetailReport());

			daos.save(observation);

			tx.commit();
		} catch (RuntimeException e) {
			tx.rollback();
			throw e;
		} finally {
			dao.getSession().close();
			dao = null;
		}
	}

	/** @throws Exception 
	 * @autor jhernandez **/
	public Student validateStudent(String documentNumber, String emailUser, String code) throws Exception {
		StudentDAO dao = new StudentDAO();
		Transaction tx = dao.getSession().beginTransaction();
		try {

			return dao.validateStudent(documentNumber, emailUser, code);

		} catch (Exception e) {

			tx.rollback();
			throw e;

		} finally {
			dao.getSession().close();
			dao = null;
		}
	}

	/** @throws Exception 
	 * @autor jhernandez **/
	public Long loadIdAdviser(Long idDegree) throws Exception {
		StudentDAO dao = new StudentDAO();
		Transaction tx = dao.getSession().beginTransaction();
		try {

			return dao.loadIdAdviser(idDegree);

		} catch (Exception e) {
			tx.rollback();
			throw e;

		} finally {
			dao.getSession().close();
			dao = null;
		}
	}
	
	/** @throws Exception 
	 * @autor jhernandez **/
	public Long loadIdStudentDegree(Long idStudent) throws Exception {
		StudentDegreeDAO dao = new StudentDegreeDAO();
		Transaction tx = dao.getSession().beginTransaction();
		try {

			return dao.loadIdStudentDegree(idStudent);

		} catch (Exception e) {
			tx.rollback();
			throw e;

		} finally {
			dao.getSession().close();
			dao = null;
		}
	}
	
	/** jhernandez */
	public List<RiskFactor> loadRiskFactorListByCategory(int idCategory) throws Exception {
		RiskFactorDAO dao = new RiskFactorDAO();
		Transaction tx = null;
		try {
			tx = dao.getSession().beginTransaction();
			return dao.loadRiskFactorListByCategory(idCategory);
		} catch (Exception e) {
			tx.rollback();
			throw e;
		} finally {
			dao.getSession().close();
			dao = null;
			tx = null;
		}
	}

}
