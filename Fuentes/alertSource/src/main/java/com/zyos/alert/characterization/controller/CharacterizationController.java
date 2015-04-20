package com.zyos.alert.characterization.controller;

import java.util.List;

import com.zyos.alert.characterization.model.CaracterizacionDAO;
import com.zyos.alert.studentReport.api.IReportType;
import com.zyos.alert.studentReport.api.IRiskFactor;
import com.zyos.alert.studentReport.api.IStatusReportStudent;
import com.zyos.alert.studentReport.model.Observation;
import com.zyos.alert.studentReport.model.ReportStudent;
import com.zyos.core.common.controller.ErrorNotificacion;
import com.zyos.core.common.controller.ZyosController;
import com.zyos.core.common.util.ManageDate;

public class CharacterizationController extends ZyosController {

	/** @author ogarzonm */
	public void loadCharacterizationPollSocialAdaptationList()  {
		CaracterizacionDAO dao = new CaracterizacionDAO();
		try {
			List<ReportStudent> rsl = dao.loadCharacterizationPollSocialAdaptationList();
			saveReportStudentList(dao, rsl, IRiskFactor.SOCIAL_ADAPTATION);
			
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		} finally {
			dao.getSession().close();
			dao = null;
		}
	}

	/** @author ogarzonm */
	private void saveReportStudentList(CaracterizacionDAO dao,
			List<ReportStudent> rsl, Long socialAdaptation) {
		if(rsl != null && !rsl.isEmpty()){
			int i = 0;
			
			for (ReportStudent rs : rsl) {
				rs.initializing("system", true);
				rs.setIdRiskFactor(socialAdaptation);
				rs.setIdReportType(IReportType.AUTOMATIC);
				rs.setDetailReport("Reporte Automatico");
				rs.setIdStatusReportStudent(IStatusReportStudent.REPORT);
				dao.getSession().save(rs);
				
				Observation o = new Observation();
				o.initializing("system", true);
				o.setIdReportStudent(rs.getIdReportStudent());
				o.setIdStatusReportStudent(rs.getIdStatusReportStudent());
				o.setDateIntervention(ManageDate.getCurrentDate(ManageDate.YYYY_MM_DD));
				
				dao.getSession().save(o);
				
				if(i % 10 == 0){
					dao.getSession().flush();
					dao.getSession().clear();
				}
			}
			dao.getSession().beginTransaction().commit();
		}
	}

	/** @author ogarzonm */
	public void loadCharacterizationPollHealthList()  {
		CaracterizacionDAO dao = new CaracterizacionDAO();
		try {
			List<ReportStudent> rsl = dao.loadCharacterizationPollHealthList();
			saveReportStudentList(dao, rsl, IRiskFactor.HEALTH);
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		} finally {
			dao.getSession().close();
			dao = null;
		}
	}

	/** @author ogarzonm */
	public void loadCharacterizationPollVocationalOrientationList()  {
		CaracterizacionDAO dao = new CaracterizacionDAO();
		try {
			List<ReportStudent> rsl = dao.loadCharacterizationPollVocationalOrientationList();
			saveReportStudentList(dao, rsl, IRiskFactor.VOCATIONAL_ORIENTATION);
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		} finally {
			dao.getSession().close();
			dao = null;
		}
	}

	/** @author ogarzonm */
	public void loadCharacterizationPollAcademicList()  {
		CaracterizacionDAO dao = new CaracterizacionDAO();
		try {
			List<ReportStudent> rsl = dao.loadCharacterizationPollAcademicList();
			saveReportStudentList(dao, rsl, IRiskFactor.ACADEMIC);
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		} finally {
			dao.getSession().close();
			dao = null;
		}
	}

	/** @author ogarzonm */
	public void loadCharacterizationPollSupportList()  {
		CaracterizacionDAO dao = new CaracterizacionDAO();
		try {
			List<ReportStudent> rsl = dao.loadCharacterizationPollSupportList();
			saveReportStudentList(dao, rsl, IRiskFactor.SUPPORT);
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		} finally {
			dao.getSession().close();
			dao = null;
		}
	}

	/** @author ogarzonm */
	public void loadCharacterizationPollEconomicList()  {
		CaracterizacionDAO dao = new CaracterizacionDAO();
		try {
			List<ReportStudent> rsl = dao.loadCharacterizationPollEconomicList();
			saveReportStudentList(dao, rsl, IRiskFactor.ECONOMIC);
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		} finally {
			dao.getSession().close();
			dao = null;
		}
	}

}
