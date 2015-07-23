package com.zyos.alert.executionsHistorical.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.zyos.alert.executionsHistorical.model.ExecutionsHistorical;
import com.zyos.core.common.controller.ErrorNotificacion;
import com.zyos.core.common.controller.ZyosBackingBean;

@ManagedBean
@ViewScoped
@URLMapping(id = "executionsHistoricalBean", pattern = "/portal/historicoEjecuciones", viewId = "/pages/executionsHistorical/executionsHistorical.jspx")
public class ExecutionsHistoricalBean extends ZyosBackingBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private int executionSelected;
	private boolean showAutomaticAlert;
	private boolean showExecutionsMoodle;
	private List<ExecutionsHistorical> executionsHistoricalListFiltered;
	private List<ExecutionsHistorical> executionsHistoricalListAutomaticFiltered;
	private List<ExecutionsHistorical> executionsHistoricalList;
	private ExecutionsHistorical eh;
	private ExecutionsHistoricalController controller;
	
		
	public ExecutionsHistoricalBean() throws Exception {

			executionsHistoricalList = new ArrayList<ExecutionsHistorical>();	
			clearAllExecutionTables();			
			controller = new ExecutionsHistoricalController();		
	}
	
	public void clearAllExecutionTables()
	{
		showAutomaticAlert=false;
		showExecutionsMoodle=false;		
	}

	public void loadExecutionsHistoricalList() {		
		try {			
			if(executionSelected == 1)
			{
				executionsHistoricalList = null;
				executionsHistoricalListFiltered = null;
				executionsHistoricalList = controller.loadAutomaticAlertList();
				showAutomaticAlert=true;				
				showExecutionsMoodle=false;
			}else{					
				if(executionSelected == 2)
				{					
					executionsHistoricalList = null;
					executionsHistoricalListFiltered = null;
					executionsHistoricalList = controller.loadExecutionCalificatioMoodleList();
					showAutomaticAlert=false;
					showExecutionsMoodle=true;
				}
			}			
			
		} catch (Exception e) {			
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}			
	}

	/*-------------------Setters and Getters ----------- */



	public boolean isShowAutomaticAlert() {
		return showAutomaticAlert;
	}

	public int getExecutionSelected() {
		return executionSelected;
	}

	public void setExecutionSelected(int executionSelected) {
		this.executionSelected = executionSelected;
	}

	public void setShowAutomaticAlert(boolean showAutomaticAlert) {
		this.showAutomaticAlert = showAutomaticAlert;
	}

	public boolean isShowExecutionsMoodle() {
		return showExecutionsMoodle;
	}

	public void setShowExecutionsMoodle(boolean showExecutionsMoodle) {
		this.showExecutionsMoodle = showExecutionsMoodle;
	}

	public List<ExecutionsHistorical> getExecutionsHistoricalList() {
		return executionsHistoricalList;
	}

	public void setExecutionsHistoricalList(
			List<ExecutionsHistorical> executionsHistoricalList) {
		this.executionsHistoricalList = executionsHistoricalList;
	}

	public ExecutionsHistorical getEh() {
		return eh;
	}

	public void setEh(ExecutionsHistorical eh) {
		this.eh = eh;
	}

	public List<ExecutionsHistorical> getExecutionsHistoricalListFiltered() {
		return executionsHistoricalListFiltered;
	}

	public void setExecutionsHistoricalListFiltered(
			List<ExecutionsHistorical> executionsHistoricalListFiltered) {
		this.executionsHistoricalListFiltered = executionsHistoricalListFiltered;
	}

	public List<ExecutionsHistorical> getExecutionsHistoricalListAutomaticFiltered() {
		return executionsHistoricalListAutomaticFiltered;
	}

	public void setExecutionsHistoricalListAutomaticFiltered(
			List<ExecutionsHistorical> executionsHistoricalListAutomaticFiltered) {
		this.executionsHistoricalListAutomaticFiltered = executionsHistoricalListAutomaticFiltered;
	}
	
	
	

}
