package com.zyos.alert.query.controller;

import java.util.List;

import com.zyos.alert.faculty.model.Faculty;
import com.zyos.alert.studentReport.model.AcademicPeriod;
import com.zyos.alert.studentReport.model.AcademicPeriodDAO;
import com.zyos.alert.studentReport.model.Observation;
import com.zyos.alert.studentReport.model.ObservationDAO;
import com.zyos.alert.studentReport.model.Student;
import com.zyos.alert.studentReport.model.StudentDAO;
import com.zyos.alert.studentReport.model.Subject;
import com.zyos.core.common.controller.ZyosController;
import com.zyos.core.common.model.ZyosParameterDAO;
import com.zyos.core.lo.user.model.ZyosUser;
import com.zyos.core.lo.user.model.ZyosUserDAO;

public class RStudentGradesController extends ZyosController {

	/**
	 * SIAT TUNJA
	 */
	private static final long serialVersionUID = 1L;
	
	public List<Student> getStudentList(Long idE,Long idP) throws Exception {
		StudentDAO dao = new StudentDAO();
		try {
			return dao.loadStudentListByEnterprise(idE);
		} catch (Exception e) {
			throw e;
		} finally {
			dao.getSession().close();
			dao = null;
		}
	}
	
	public List<AcademicPeriod> getAcademicPeriodList() throws Exception {
		AcademicPeriodDAO dao = new AcademicPeriodDAO();
		try {
			return dao.findAll();
		} catch (Exception e) {
			throw e;
		} finally {
			dao.getSession().close();
			dao = null;
		}
	}

}
