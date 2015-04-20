package com.zyos.alert.studentReport.controller;

import java.util.List;

import org.hibernate.Transaction;

import com.zyos.alert.absent.model.Absent;
import com.zyos.alert.absent.model.AbsentDAO;
import com.zyos.alert.absent.model.DayClass;
import com.zyos.alert.absent.model.DayClassDAO;
import com.zyos.alert.calification.model.Calification;
import com.zyos.alert.calification.model.CalificationDAO;
import com.zyos.alert.calification.model.CalificationItem;
import com.zyos.alert.calification.model.CalificationitemDAO;
import com.zyos.alert.calification.model.Evaluation;
import com.zyos.alert.calification.model.EvaluationDAO;
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
import com.zyos.alert.studentReport.model.StudentSubjectDAO;
import com.zyos.alert.studentReport.model.Subject;
import com.zyos.alert.studentReport.model.SubjectDAO;
import com.zyos.core.common.controller.ZyosController;
import com.zyos.core.common.util.ManageDate;

public class SubjectController extends ZyosController {

	public List<Subject> loadSubjectListByAcademicP(Long idUser,
			String currentDate, Long idZyosGroup) throws Exception {
		SubjectDAO dao = new SubjectDAO();
		try {
			return dao.loadSubjectListByAcademicPeriod(idUser, currentDate,
					idZyosGroup);
		} catch (Exception e) {
			throw e;
		} finally {
			dao.getSession().close();
			dao = null;
		}
	}

	public List<Subject> loadSubjectListByAcademicPeriod(Long idUser,
			String currentDate, Long idZyosGroup) throws Exception {
		SubjectDAO dao = new SubjectDAO();
		try {
			return dao.loadMonitoredSubjectListByAcademicPeriod(idUser,
					currentDate, idZyosGroup);
		} catch (Exception e) {
			throw e;
		} finally {
			dao.getSession().close();
			dao = null;
		}
	}

	/** @autor jhernandez */
	public List<Student> loadStudentBySubjectList(Long idSubject,
			Long idGroupSubject, String currenDate) throws Exception {
		StudentDAO dao = new StudentDAO();
		try {
			return dao.loadStudentBySubjectList(idSubject, idGroupSubject,
					currenDate);
		} catch (Exception e) {
			throw e;
		} finally {
			dao.getSession().close();

			dao = null;
		}
	}

	/** @autor jhernandez */
	public void registerAssistance(Long idSubject, List<Student> studentList,
			Long idZyosUser, String documentNumber) {
		AbsentDAO dao = new AbsentDAO();
		Transaction tx = dao.getSession().beginTransaction();
		try {
			int count = 1;

			for (Student s : studentList) {
				Absent a = new Absent();

				if (!s.isSelected()) {

					a.initializing(documentNumber, true);
					a.setIdStudentSubject(s.getIdStudentSubject());
					a.setIdZyosUser(idZyosUser);
					String date = ManageDate
							.getCurrentDate(ManageDate.YYYY_MM_DD);
					a.setDateAbsent(date);

					dao.save(a);

					if (count++ % 10 == 0) {
						dao.getSession().flush();
					}
				}
			}
			tx.commit();
		} catch (RuntimeException e) {
			tx.rollback();
			throw e;
		} finally {
			dao.getSession().close();
			dao = null;
		}
	}

	/** @autor jhernandez */
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

	/** @autor jhernandez */
	public void saveRiskFactor(ReportStudent reportStudent,
			String documentNumber) {
		ReportStudentDAO dao = new ReportStudentDAO();
		RiskFactorReportStudentDAO daos = new RiskFactorReportStudentDAO();

		Transaction tx = dao.getSession().beginTransaction();
		try {

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

	/** @autor jhernandez */
	public void saveReportStudentObservation(ReportStudent reportStudent,
			String documentNumber) {
		ReportStudentDAO dao = new ReportStudentDAO();
		ObservationDAO daos = new ObservationDAO();

		Transaction tx = dao.getSession().beginTransaction();
		try {

			dao.getSession().flush();

			Observation observation = new Observation();

			observation.initializing(documentNumber, true);
			observation.setIdReportStudent(reportStudent.getIdReportStudent());
			observation.setIdAdviser(reportStudent.getIdAdviser());
			observation.setIdStage(reportStudent.getIdStage());
			observation.setIdStatusReportStudent(reportStudent
					.getIdStatusReportStudent());
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

	/** @autor jhernandez */
	public Student loadStudentCurrentActive(Long idZyosUser, Subject subjectSelected) throws Exception {
		StudentDAO dao = new StudentDAO();
		Transaction tx = dao.getSession().beginTransaction();
		try {

			return dao.loadStudentCurrentActive(idZyosUser, subjectSelected);

		} catch (Exception e) {

			tx.rollback();
			throw e;

		} finally {
			dao.getSession().close();
			dao = null;
		}
	}

	/** @autor jhernandez */
	public DayClass validateDayClass(Long currentDay, Long idStudent,
			Long idSubject, Long idGroupSubject, String today, String hour)
			throws Exception {
		DayClassDAO dao = new DayClassDAO();
		Transaction tx = dao.getSession().beginTransaction();
		try {

			return dao.validateDayClass(currentDay, idStudent, idSubject,
					idGroupSubject, today, hour);

		} catch (Exception e) {

			tx.rollback();
			throw e;

		} finally {
			dao.getSession().close();
			dao = null;
		}
	}

	/** @autor jhernandez */
	public List<DayClass> loadDayClassList(Long idStudent, Long idSubject,
			Long idGroupSubject, String today) throws Exception {
		DayClassDAO dao = new DayClassDAO();
		Transaction tx = dao.getSession().beginTransaction();
		try {

			return dao.loadDayClassList(idStudent, idSubject, idGroupSubject,
					today);

		} catch (Exception e) {

			tx.rollback();
			throw e;

		} finally {
			dao.getSession().close();
			dao = null;
		}
	}

	/** @autor jhernandez */
	public List<DayClass> loadDayClassListTeacher(Long idSubject,
			Long idGroupSubject, String today) throws Exception {
		DayClassDAO dao = new DayClassDAO();
		Transaction tx = dao.getSession().beginTransaction();
		try {

			return dao
					.loadDayClassListTeacher(idSubject, idGroupSubject, today);

		} catch (Exception e) {

			tx.rollback();
			throw e;

		} finally {
			dao.getSession().close();
			dao = null;
		}
	}

	/** @autor jhernandez */
	public String validateDuplicateStudentReport(Long idStudent)
			throws Exception {
		ReportStudentDAO dao = new ReportStudentDAO();
		Transaction tx = dao.getSession().beginTransaction();
		try {

			return dao.validateDuplicateStudentReport(idStudent);

		} catch (Exception e) {

			tx.rollback();
			throw e;

		} finally {
			dao.getSession().close();
			dao = null;
		}
	}

	public List<String> loadRiskFactorStudentList(Long idStudent)
			throws Exception {
		ReportStudentDAO dao = new ReportStudentDAO();
		Transaction tx = dao.getSession().beginTransaction();
		try {

			return dao.loadRiskFactorStudentList(idStudent);

		} catch (Exception e) {

			tx.rollback();
			throw e;

		} finally {
			dao.getSession().close();
			dao = null;
		}
	}
	
	/** @autor jhernandez */
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

	/** @autor jhernandez */
	public List<Evaluation> loadEvaluationList(Long idSubject,
			Long idGroupSubject) throws Exception {
		EvaluationDAO dao = new EvaluationDAO();
		Transaction tx = dao.getSession().beginTransaction();
		try {

			return dao.loadEvaluationList(idSubject, idGroupSubject);

		} catch (Exception e) {

			tx.rollback();
			throw e;

		} finally {
			dao.getSession().close();
			dao = null;
		}
	}

	/** @autor jhernandez **/
	public void saveEvaluationItem(Evaluation evaluation,
			Subject subjectSelected, String documentNumber) {
		EvaluationDAO dao = new EvaluationDAO();

		Transaction tx = dao.getSession().beginTransaction();
		try {

			evaluation.initializing(documentNumber, true);
			evaluation.setIdSubject(subjectSelected.getIdSubject());
			evaluation.setIdGroupSubject(subjectSelected.getIdGroupSubject());

			dao.save(evaluation);

			tx.commit();
		} catch (RuntimeException e) {
			tx.rollback();
			throw e;
		} finally {
			dao.getSession().close();
			dao = null;
		}
	}

	/**
	 * @throws Exception
	 * @autor jhernandez
	 **/
	public void updateEvaluationItem(Evaluation evaluationToEdit,
			Long idEvaluation, Long idSubject, Long idGroupSubject,
			String documentNumber) throws Exception {
		EvaluationDAO dao = new EvaluationDAO();

		Transaction tx = dao.getSession().beginTransaction();
		try {

			evaluationToEdit.initializing(documentNumber, false);
			evaluationToEdit.setIdSubject(idSubject);
			evaluationToEdit.setIdGroupSubject(idGroupSubject);

			dao.updateEvaluationItem(evaluationToEdit);

			tx.commit();
		} catch (RuntimeException e) {
			tx.rollback();
			throw e;
		} finally {
			dao.getSession().close();
			dao = null;
		}
	}

	/** @autor jhernandez */
	public List<Student> loadStudentCalificationItemList(
			Evaluation evaluationSelected) throws Exception {
		StudentDAO dao = new StudentDAO();
		Transaction tx = dao.getSession().beginTransaction();
		try {
			return dao.loadStudentCalificationItemList(evaluationSelected);
		} catch (Exception e) {

			tx.rollback();
			throw e;
		} finally {
			dao.getSession().close();
			dao = null;
		}
	}

	/**
	 * @autor jhernandez
	 **/
	public void deleteCalifications(Evaluation evaluationToDelete,
			String documentNumber, List<Long> calificationItemIdsToDelete)
			throws Exception {
		CalificationDAO dao = new CalificationDAO();
		CalificationitemDAO daos = new CalificationitemDAO();
		EvaluationDAO daoc = new EvaluationDAO();

		Transaction tx = dao.getSession().beginTransaction();
		try {

			evaluationToDelete.initializing(documentNumber, false);

			dao.deleteCalifications(evaluationToDelete,
					calificationItemIdsToDelete);
			daos.deleteCalificationItems(evaluationToDelete);
			daoc.deleteEvaluationItem(evaluationToDelete);

			tx.commit();
		} catch (RuntimeException e) {
			tx.rollback();
			throw e;
		} finally {
			dao.getSession().close();
			dao = null;
		}
	}

	/** @autor jhernandez */
	public List<Long> loadCalificationItemIdsToDelete(
			Evaluation evaluationToDelete) throws Exception {
		CalificationitemDAO dao = new CalificationitemDAO();
		Transaction tx = dao.getSession().beginTransaction();
		try {

			return dao.loadCalificationItemIdsToDelete(evaluationToDelete);

		} catch (Exception e) {

			tx.rollback();
			throw e;
		} finally {
			dao.getSession().close();
			dao = null;
		}
	}

	/** @autor jhernandez */
	public int loadNumberCalificationItems(Evaluation evaluationToDelete)
			throws Exception {
		CalificationitemDAO dao = new CalificationitemDAO();
		Transaction tx = dao.getSession().beginTransaction();
		try {
			return dao.loadNumberCalificationItems(evaluationToDelete);

		} catch (Exception e) {

			tx.rollback();
			throw e;
		} finally {
			dao.getSession().close();
			dao = null;
		}
	}

	/** @autor jhernandez **/
	public void saveCalificationItem(Evaluation evaluationSelected,
			CalificationItem calificationItem, String documentNumber,
			List<Student> studentList) {
		CalificationitemDAO dao = new CalificationitemDAO();
		CalificationDAO daos = new CalificationDAO();

		Transaction tx = dao.getSession().beginTransaction();
		try {

			calificationItem.initializing(documentNumber, true);
			calificationItem.setIdEvaluation(evaluationSelected
					.getIdEvaluation());
			dao.save(calificationItem);
			dao.getSession().flush();

			for (Student student : studentList) {

				Calification calification = new Calification();

				calification.initializing(documentNumber, true);
				calification.setGrade(0);
				calification.setIdStudent(student.getIdStudent());
				calification.setIdCalificationItem(calificationItem
						.getIdCalificationItem());

				daos.save(calification);

			}

			tx.commit();
		} catch (RuntimeException e) {
			tx.rollback();
			throw e;
		} finally {
			dao.getSession().close();
			dao = null;
		}
	}

	/** @autor jhernandez */
	public List<Long> loadIdsCalificationItems(Evaluation evaluationSelected)
			throws Exception {
		CalificationitemDAO dao = new CalificationitemDAO();
		Transaction tx = dao.getSession().beginTransaction();
		try {

			return dao.loadIdsCalificationItems(evaluationSelected);

		} catch (Exception e) {

			tx.rollback();
			throw e;
		} finally {
			dao.getSession().close();
			dao = null;
		}
	}

	/**
	 * @throws Exception
	 * @autor jhernandez
	 **/
	public void updateCalification(List<Student> studentCalificationList,
			String documentNumber) throws Exception {
		CalificationDAO dao = new CalificationDAO();

		Transaction tx = dao.getSession().beginTransaction();
		try {
			if (studentCalificationList == null
					|| studentCalificationList.isEmpty()) {
				return;
			}

			for (Student s : studentCalificationList) {

				for (int i = 0; i < s.getStudentGrade().length; i++) {

					Calification calification = new Calification();
					calification.initializing(documentNumber, false);
					calification.setIdStudent(s.getIdStudent());
					calification.setIdCalificationItem(s
							.getIdCalificationItem());
					calification.setGrade(s.getStudentGrade()[i]);
					calification.setIdCalificationItem(s
							.getIdCalificationItemSudent().get(i));
					dao.updateCalification(calification);
				}
			}

			tx.commit();
		} catch (RuntimeException e) {
			tx.rollback();
			throw e;
		} finally {
			dao.getSession().close();
			dao = null;
		}
	}

	/** @autor jhernandez */
	public List<CalificationItem> loadCalificationNameList(
			Evaluation evaluationSelected) throws Exception {
		CalificationDAO dao = new CalificationDAO();
		Transaction tx = dao.getSession().beginTransaction();
		try {

			return dao.loadCalificationNameList(evaluationSelected);

		} catch (Exception e) {

			tx.rollback();
			throw e;

		} finally {
			dao.getSession().close();
			dao = null;
		}
	}

	/**
	 * @throws Exception
	 * @autor jhernandez
	 **/
	public void updateCalificationItem(
			List<CalificationItem> calificationItemNameList,
			String documentNumber) throws Exception {
		CalificationitemDAO dao = new CalificationitemDAO();

		Transaction tx = dao.getSession().beginTransaction();
		try {

			for (CalificationItem s : calificationItemNameList) {

				CalificationItem calificationItem = new CalificationItem();

				calificationItem.initializing(documentNumber, false);

				calificationItem.setIdCalificationItem(s
						.getIdCalificationItem());
				calificationItem.setName(s.getName());
				calificationItem.setPercentage(s.getPercentage());

				dao.updateCalificationItem(calificationItem);

			}

			tx.commit();
		} catch (RuntimeException e) {
			tx.rollback();
			throw e;
		} finally {
			dao.getSession().close();
			dao = null;
		}
	}
	
	/**
	 * @throws Exception
	 * @autor jhernandez
	 **/
	public void deleteCalificationItem(CalificationItem calificationItemToDelete,
			String documentNumber)
			throws Exception {	
		
		CalificationitemDAO dao = new CalificationitemDAO();
		CalificationDAO daos = new CalificationDAO();
	
		Transaction tx = dao.getSession().beginTransaction();
		try {

			calificationItemToDelete.initializing(documentNumber, false);

			dao.deleteCalificationItem(calificationItemToDelete);
			daos.deleteCalificationsFromCalificationItem(calificationItemToDelete);
	

			tx.commit();
		} catch (RuntimeException e) {
			tx.rollback();
			throw e;
		} finally {
			dao.getSession().close();
			dao = null;
		}
	}	
	
	
	/**
	 * @throws Exception
	 * @autor jhernandez
	 **/
	public List<Subject> loadStudentCalifications(Long idUser,
			String currentDate, Long idZyosGroup) throws Exception {
		SubjectDAO dao = new SubjectDAO();
		try {
			return dao.loadSubjectListByAcademicPeriod(idUser, currentDate,
					idZyosGroup);
		} catch (Exception e) {
			throw e;
		} finally {
			dao.getSession().close();
			dao = null;
		}
	}
	
	/**
	 * @throws Exception
	 * @autor jhernandez
	 **/
	public void deleteStudentSubject(Student studentToDelete, Subject subjectSelected , String documentNumber, String currentDate)
			throws Exception {	
		
		CalificationDAO dao = new CalificationDAO();
		StudentSubjectDAO daos = new StudentSubjectDAO();
	
		Transaction tx = dao.getSession().beginTransaction();
		try {

			studentToDelete.initializing(documentNumber, false);

			daos.deleteStudentSubject(studentToDelete);
			dao.deleteCalificationsFromDeleteStudent(studentToDelete, subjectSelected, currentDate);	

			tx.commit();
			
		} catch (RuntimeException e) {
			tx.rollback();
			throw e;
		} finally {
			dao.getSession().close();
			dao = null;
		}
	}	
	
	/** @autor jhernandez */
	public List<Absent> loadStudentAbsentList(List<Long> idStudentSubjectList	) throws Exception {
		AbsentDAO dao = new AbsentDAO();
		try {
			return dao.loadStudentAbsentList(idStudentSubjectList);
		} catch (Exception e) {
			throw e;
		} finally {
			dao.getSession().close();

			dao = null;
		}
	}
	
	/**
	 * @throws Exception
	 * @autor jhernandez
	 **/
	public void deleteAbsent(Absent absentToDelete, String documentNumber)
			throws Exception {	
		
		AbsentDAO dao = new AbsentDAO();
			
		Transaction tx = dao.getSession().beginTransaction();
		try {

			absentToDelete.initializing(documentNumber, false);
			dao.deleteAbsent(absentToDelete);				
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
