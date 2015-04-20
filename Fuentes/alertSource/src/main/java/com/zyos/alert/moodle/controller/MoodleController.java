package com.zyos.alert.moodle.controller;

import java.util.List;

import com.zyos.alert.calification.model.Calification;
import com.zyos.alert.calification.model.CalificationDAO;
import com.zyos.alert.calification.model.CalificationItem;
import com.zyos.alert.calification.model.CalificationitemDAO;
import com.zyos.alert.calification.model.Evaluation;
import com.zyos.alert.calification.model.EvaluationDAO;
import com.zyos.alert.moodle.model.MdlGradeCategoriesDAO;
import com.zyos.alert.moodle.model.MdlGradeGradesDAO;
import com.zyos.core.common.controller.ZyosController;

public class MoodleController extends ZyosController {

	public void migrateEvaluationFromMoodle(Long idAcademicPeriod) throws Exception {
		EvaluationDAO dao = new EvaluationDAO();
		MdlGradeCategoriesDAO dao2 = new MdlGradeCategoriesDAO();
		try {
			List<Long> idEL = dao.loadEvaluationByAcademicPeriod(idAcademicPeriod);
			
			List<Evaluation> newEL = dao2.migrateEvaluationFromMoodle(idEL);

			for (Evaluation e : newEL) {
				e.setIdAcademicPeriod(idAcademicPeriod);
				e.initializing("systemFromMoodle", true);
				e.setPercentage(e.getPercentage() * 100);
				
				dao.save(e);
			}
			
			dao.getSession().beginTransaction().commit();
			
			dao.setIdSubjectAndGroupSubjectOnEvaluationFromMoodleGroupSubject(idAcademicPeriod);
			dao.getSession().beginTransaction().commit();
			
			dao.setIdSubjectAndGroupSubjectOnEvaluationFromMoodleSubject(idAcademicPeriod);
			dao.getSession().beginTransaction().commit();
			
		} catch (Exception e) {
			throw e;
		} finally {
			dao.getSession().close();
			dao = null;
			dao2.getSession().close();
			dao2 = null;
		}
	}

	public void migrateCalificationItemFromMoodle(Long idAcademicPeriod) throws Exception {
		CalificationitemDAO dao = new CalificationitemDAO();
		MdlGradeCategoriesDAO dao2 = new MdlGradeCategoriesDAO();
		try {
			List<Long> idEL = dao.loadCalificationItemByAcademicPeriod(idAcademicPeriod);
			
			List<CalificationItem> newEL = dao2.migrateCalificationItemFromMoodle(idEL);

			for (CalificationItem e : newEL) {
				e.setIdAcademicPeriod(idAcademicPeriod);
				e.initializing("systemFromMoodle", true);
				e.setPercentage(e.getPercentage() * 100);
				
				dao.save(e);
			}
			
			dao.getSession().beginTransaction().commit();
			
			dao.setIdEvaluationOnCalificationItemFromMoodle(idAcademicPeriod);
			dao.getSession().beginTransaction().commit();
			
		} catch (Exception e) {
			throw e;
		} finally {
			dao.getSession().close();
			dao = null;
			dao2.getSession().close();
			dao2 = null;
		}
	}

	public void migrateCalificationFromMoodle(Long idAcademicPeriod) throws Exception {
		CalificationDAO dao = new CalificationDAO();
		MdlGradeGradesDAO dao2 = new MdlGradeGradesDAO();
		try {
			List<Long> idEL = dao.loadCalificationByAcademicPeriod(idAcademicPeriod);
			
			List<Calification> newEL = dao2.migrateCalificationFromMoodle(idEL);

			for (Calification e : newEL) {
				e.setIdAcademicPeriod(idAcademicPeriod);
				e.initializing("systemFromMoodle", true);
				
				dao.save(e);
			}
			
			dao.getSession().beginTransaction().commit();
			
			dao.setIdStudentAndCalificationItemOnCalificationFromMoodleZyosUser(idAcademicPeriod);
			dao.getSession().beginTransaction().commit();
			
			dao.setIdStudentAndCalificationItemOnCalificationFromMoodleCalificationItem(idAcademicPeriod);
			dao.getSession().beginTransaction().commit();			
		} catch (Exception e) {
			throw e;
		} finally {
			dao.getSession().close();
			dao = null;
			dao2.getSession().close();
			dao2 = null;
		}
	}

}
