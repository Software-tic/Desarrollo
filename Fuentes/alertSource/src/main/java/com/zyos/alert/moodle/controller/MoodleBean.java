package com.zyos.alert.moodle.controller;

import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.zyos.alert.executionsHistorical.api.IExecutionHistorialType;
import com.zyos.alert.executionsHistorical.model.ExecutionsHistorical;
import com.zyos.core.common.controller.ZyosBackingBean;
import com.zyos.core.common.util.ManageDate;

@ManagedBean
@ViewScoped
@URLMapping(id = "moodleBean", pattern = "/portal/moodle", viewId = "/pages/moodle/moodle.jspx")
public class MoodleBean extends ZyosBackingBean {

	MoodleController controller = new MoodleController();
	
	public MoodleBean() throws Exception {
		super();
		
		Long start = System.currentTimeMillis();
		//loading academic period
		Long idAcademicPeriod = controller.loadCurrentAcademicPeriod();
		
		controller.migrateEvaluationFromMoodle(idAcademicPeriod);
		controller.migrateCalificationItemFromMoodle(idAcademicPeriod);
		controller.migrateCalificationFromMoodle(idAcademicPeriod);
		
		Long end = System.currentTimeMillis();
		Long finaltime = end - start;
		Date executeTime = parseDate(finaltime);
		String execute = ManageDate.formatDate(executeTime, ManageDate.HH_MM_SS);
		
		ExecutionsHistorical eh = new ExecutionsHistorical();
		eh.initializing("autoMoodle", true);
		eh.setIdExecutionHistoricalType(IExecutionHistorialType.MOODLE);
		eh.setInformation("Registrando notas cargadas de Moodle de forma automatica");		
		eh.setExecutionTime(execute);		
		
		
		controller.saveExecution(eh);
	}
	
	private void printMessage(String string) {
		System.out.println(string + ManageDate.getCurrentDate(ManageDate.YYYY_MM_DD_HH_MM_SS));
	}
	
	/** @autor jhernandez */
	private static Date parseDate(Long milisecond) throws Exception {
		try {
			if (milisecond == null)
				return ManageDate.formatDate("00:00:00", ManageDate.HH_MM_SS);
			int seconds = milisecond.intValue() / 1000;
			int h = seconds / 3600;
			int m = (seconds % 3600) / 60;
			int s = ((seconds % 3600) % 60);

			return ManageDate.formatDate((h > 9 ? "" : "0") + h + ":"
					+ (m > 9 ? "" : "0") + m + ":" + (s > 9 ? "" : "0") + s,
					ManageDate.HH_MM_SS);
		} catch (Exception e) {
			throw e;
		}
	}

}
