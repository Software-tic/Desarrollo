package com.zyos.alert.characterization.controller;

import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.zyos.alert.executionsHistorical.api.IExecutionHistorialType;
import com.zyos.alert.executionsHistorical.model.ExecutionsHistorical;
import com.zyos.core.common.controller.ErrorNotificacion;
import com.zyos.core.common.controller.ZyosBackingBean;
import com.zyos.core.common.util.ManageDate;

@ManagedBean
@ViewScoped
@URLMapping(id = "characterizationBean", pattern = "/portal/caracterizacion", viewId = "/pages/characterization/characterization.jspx")
public class CharacterizationBean extends ZyosBackingBean {

	private CharacterizationController controller = new CharacterizationController();
	
	public CharacterizationBean() throws Exception {
		try {
			processCharacterizationPoll();
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}
	}
	
	public void processCharacterizationPoll(){
		try {

			Long start = System.currentTimeMillis();
			
			controller.loadCharacterizationPollSocialAdaptationList();
			
			controller.loadCharacterizationPollHealthList();
			controller.loadCharacterizationPollVocationalOrientationList();
			controller.loadCharacterizationPollAcademicList();
			controller.loadCharacterizationPollSupportList();
			controller.loadCharacterizationPollEconomicList();
			
			Long end = System.currentTimeMillis();
			Long finaltime = end - start;
			Date executeTime = parseDate(finaltime);
			String execute = ManageDate.formatDate(executeTime, ManageDate.HH_MM_SS);
			
			ExecutionsHistorical eh = new ExecutionsHistorical();
			eh.initializing("integrationAuto", true);
			eh.setIdExecutionHistoricalType(IExecutionHistorialType.CHARACTERIZATION);
			eh.setInformation("Registrando elementos nuevos de la encuesta de caracterizacion de forma automatica");
			eh.setExecutionTime(execute);
			
			controller.saveExecution(eh);
			
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}
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

	private void printMessage(String string) {
		System.out.println(string + ManageDate.getCurrentDate(ManageDate.YYYY_MM_DD_HH_MM_SS));
	}
	
}
