package com.zyos.alert.controlPanel.controller;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.ocpsoft.pretty.faces.annotation.URLMappings;
import com.zyos.alert.controlPanel.model.ControlPanel;
import com.zyos.core.common.api.IZyosGroup;
import com.zyos.core.common.controller.ErrorNotificacion;
import com.zyos.core.common.controller.ZyosBackingBean;

@ManagedBean(name = "controlPanelBean")
@ViewScoped
@URLMappings(
	mappings = @URLMapping(id = "controlPanelBean", pattern = "/portal/panelConfiguracion", viewId = "/pages/controlPanel/controlPanel.jspx"))
public class ControlPanelBean extends ZyosBackingBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private ControlPanel controlPanel;
	private ControlPanel controlPanelRisk;
	private ControlPanel controlPanelAssistance;
	private ControlPanelController controller;

	// private int marginHour;

	public ControlPanelBean() throws Exception {

		try {
			controller = new ControlPanelController();
			controlPanel = controller.loadCurrentMarginDays();
			controlPanelRisk = controller.loadCurrentPercentageRisk();
			controlPanelAssistance = controller.loadCurrentPercentageAssistance();
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}
	}

	public void registerMarginHour() {
		try {

			if (controlPanel.getMarginHour() == 0) {
				ZyosBackingBean.addWarn("Registrar Hora Margen", "Seleccione un número de dias por favor");
			} else {
				controller.saveOrUpdateMarginHour(controlPanel, getUserSession().getDocumentNumber());
				ZyosBackingBean.addInfo("Registrar dias Margen", "Margen de dias de asistencia modificada con éxito");
				update("controlPanelForm:marginHourTable");
			}

		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}

	}

	public void registerPercentageRiskFactor() {
		try {

			if (controlPanelRisk.getPercentageRiskFactor() == 0) {
				ZyosBackingBean.addWarn("Registrar Porcentaje", "Ingrese un porcentaje válido mayor a 0");
			} else {
				controller.saveOrUpdatePercentage(controlPanelRisk, getUserSession().getDocumentNumber());
				ZyosBackingBean.addInfo("Registrar Porcentaje", "Porcentaje de Factor de Riesgo registrado con éxito!");
				update("controlPanelForm:percentageRiskFactorTable");
			}

		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}

	}

	public void registerPercentageAssistance() {
		try {

			if (controlPanelAssistance.getPercentageAssistance() == 0) {
				ZyosBackingBean.addWarn("Registrar Porcentaje", "Ingrese un porcentaje válido mayor a 0");
			} else {
				controller.saveOrUpdatePercentageAssistance(controlPanelAssistance, getUserSession().getDocumentNumber());
				ZyosBackingBean.addInfo("Registrar Porcentaje", "Porcentaje de Factor de Riesgo registrado con éxito!");
				update("controlPanelForm:controlPanelPercentageAssistanceTable");
			}

		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}

	}

	public ControlPanelController getController() {
		return controller;
	}

	public void setController(ControlPanelController controller) {
		this.controller = controller;
	}

	public ControlPanel getControlPanel() {
		return controlPanel;
	}

	public ControlPanel getControlPanelRisk() {
		return controlPanelRisk;
	}

	public void setControlPanelRisk(ControlPanel controlPanelRisk) {
		this.controlPanelRisk = controlPanelRisk;
	}

	public void setControlPanel(ControlPanel controlPanel) {
		this.controlPanel = controlPanel;
	}

	public ControlPanel getControlPanelAssistance() {
		return controlPanelAssistance;
	}

	public void setControlPanelAssistance(ControlPanel controlPanelAssistance) {
		this.controlPanelAssistance = controlPanelAssistance;
	}



}
