package com.zyos.alert.monitorstudentsubject.controller;

import org.hibernate.Transaction;

import com.zyos.alert.monitorstudentsubject.model.MonitorStudentSubject;
import com.zyos.alert.monitorstudentsubject.model.MonitorStudentSubjectDAO;
import com.zyos.alert.studentReport.model.Student;
import com.zyos.alert.studentReport.model.Subject;
import com.zyos.core.common.controller.ZyosController;

public class MonitorStudentSubjectController extends ZyosController {

	/** @autor jhernandez */
	public MonitorStudentSubject validateMoreOneMonitor(Subject subjectSelected) throws Exception {
		MonitorStudentSubjectDAO dao = new MonitorStudentSubjectDAO();
		Transaction tx = dao.getSession().beginTransaction();
		try {

			return dao.validateMoreOneMonitor(subjectSelected);

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
	 */
	public void saveAndDeleteMonitorStudentSubject(MonitorStudentSubject monitorStudent, Subject subjecSelected, Student studentToAsignedMonitor,
		String documentNumber) throws Exception {
		MonitorStudentSubjectDAO dao = new MonitorStudentSubjectDAO();

		Transaction tx = dao.getSession().beginTransaction();
		try {
			MonitorStudentSubject monitor = new MonitorStudentSubject();

			monitor.initializing(documentNumber, true);
			monitorStudent.initializing(documentNumber, false);

			monitor.setIdStudentSubject(studentToAsignedMonitor.getIdStudentSubject());


			dao.deleteMonitorStudentCurrent(monitorStudent, subjecSelected);
			dao.save(monitor);

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
	 */
	public void saveMonitorStudentSubject(Subject subjecSelected, Student studentToAsignedMonitor, String documentNumber) throws Exception {
		MonitorStudentSubjectDAO dao = new MonitorStudentSubjectDAO();

		Transaction tx = dao.getSession().beginTransaction();
		try {
			MonitorStudentSubject monitor = new MonitorStudentSubject();

			monitor.initializing(documentNumber, true);

			monitor.setIdStudentSubject(studentToAsignedMonitor.getIdStudentSubject());

			dao.save(monitor);

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
	 */
	public void deleteMonitorStudent(MonitorStudentSubject monitorStudent, String documentNumber) throws Exception {
		MonitorStudentSubjectDAO dao = new MonitorStudentSubjectDAO();
		Transaction tx = null;
		try {
			tx = dao.getSession().beginTransaction();
			monitorStudent.initializing(documentNumber, false);
			dao.deleteMonitorStudent(monitorStudent);

			tx.commit();
		} catch (RuntimeException e) {
			tx.rollback();
			throw e;
		} finally {
			dao.getSession().close();
			dao = null;
		}
	}



}
