package com.zyos.alert.inscriptionStudent.controller;

import java.util.List;

import org.hibernate.Transaction;

import com.zyos.alert.inscriptionStudent.model.InscriptionStudentDAO;
import com.zyos.alert.studentReport.model.Observation;
import com.zyos.alert.studentReport.model.ObservationDAO;
import com.zyos.alert.studentReport.model.ReportStudent;
import com.zyos.alert.studentReport.model.ReportStudentDAO;
import com.zyos.alert.studentReport.model.RiskFactor;
import com.zyos.alert.studentReport.model.RiskFactorDAO;
import com.zyos.alert.studentReport.model.RiskFactorReportStudent;
import com.zyos.alert.studentReport.model.RiskFactorReportStudentDAO;
import com.zyos.alert.studentReport.model.Student;
import com.zyos.core.common.controller.ZyosController;
import com.zyos.core.common.util.ManageDate;
import com.zyos.core.lo.user.model.Profession;
import com.zyos.core.lo.user.model.ZyosUser;
import com.zyos.core.lo.user.model.ZyosUserDAO;

public class InscriptionStudentController extends ZyosController {
	/**
	 * @author mtorres
	 * */
	public List<Profession> loadProfessionList(Long idEnterprise) throws Exception {
		InscriptionStudentDAO dao = new InscriptionStudentDAO();
		Transaction tx = null;
		try {
			tx = dao.getSession().beginTransaction();
			return dao.loadProfessionList(idEnterprise);
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
	 * @author mtorres
	 * */
	public Long inscribeStudent(Student student, String user) throws Exception {
		ZyosUserDAO dao = new ZyosUserDAO();
		Transaction tx = null;
		try {
			student.initializing(user, true);
			tx = dao.getSession().beginTransaction();
			dao.saveStudent(student);
			dao.getSession().flush();
			Long idStudent = student.getIdStudent();
			tx.commit();
			return idStudent;
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
	 * @author mtorres
	 * */
	public boolean validateDocumentExist(String documentNumber) throws Exception {
		ZyosUserDAO dao = new ZyosUserDAO();
		Transaction tx = null;
		try {
			tx = dao.getSession().beginTransaction();
			return dao.validateDocumentExist(documentNumber);
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
	 * @author mtorres
	 * */
	public Long saveReportStudent(ReportStudent reportStudent, String user) throws Exception {
		ReportStudentDAO dao = new ReportStudentDAO();
		Transaction tx = null;
		try {
			tx = dao.getSession().beginTransaction();
			reportStudent.initializing(user, true);
			dao.save(reportStudent);
			dao.getSession().flush();
			Long idReport = reportStudent.getIdReportStudent();
			tx.commit();
			return idReport;
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
	 * @author mtorres edit jhernandez
	 * */
	public void saveRiskFactorReportStudent(Long idReport, ReportStudent reportStudent, String user) throws Exception {
		RiskFactorReportStudentDAO dao = new RiskFactorReportStudentDAO();
		Transaction tx = null;
		try {
			tx = dao.getSession().beginTransaction();

			RiskFactorReportStudent riskFactorAux = new RiskFactorReportStudent();
			riskFactorAux.initializing(user, true);
			riskFactorAux.setIdRiskFactor(reportStudent.getIdRiskFactor());
			riskFactorAux.setIdReportStudent(idReport);

			dao.save(riskFactorAux);

			tx.commit();
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
	 * @author mtorres edit jhernandez
	 * */
	public ZyosUser searchUser(String codeStudent, String emailUser) throws Exception {
		ZyosUserDAO dao = new ZyosUserDAO();
		Transaction tx = null;
		try {
			tx = dao.getSession().beginTransaction();
			return dao.searchUser(codeStudent, emailUser);
		} catch (Exception e) {
			tx.rollback();
			throw e;
		} finally {
			dao.getSession().close();
			dao = null;
			tx = null;
		}
	}

	public void saveReportStudentObservation(ReportStudent reportStudent, String documentNumber) {
		ReportStudentDAO dao = new ReportStudentDAO();
		ObservationDAO daos = new ObservationDAO();

		Transaction tx = dao.getSession().beginTransaction();
		try {

			dao.getSession().flush();

			Observation observation = new Observation();
			observation.initializing(documentNumber, true);

			observation.setIdStatusReportStudent(reportStudent.getIdStatusReportStudent());
			observation.setIdAdviser(reportStudent.getIdAdviser());
			observation.setIdStage(reportStudent.getIdStage());

			observation.setIdReportStudent(reportStudent.getIdReportStudent());
			observation.setDateIntervention(ManageDate.getCurrentDate(ManageDate.YYYY_MM_DD));

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
