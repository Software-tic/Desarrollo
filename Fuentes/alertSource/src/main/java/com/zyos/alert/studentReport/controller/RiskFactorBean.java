package com.zyos.alert.studentReport.controller;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.RowEditEvent;

import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.zyos.alert.studentReport.model.RiskFactor;
import com.zyos.core.common.controller.ErrorNotificacion;
import com.zyos.core.common.controller.ZyosBackingBean;


@ManagedBean
@ViewScoped
@URLMapping(id = "riskFactor", pattern = "/portal/gestorFactores", viewId = "/pages/riskFactor/riskFactor.jspx")
public class RiskFactorBean extends ZyosBackingBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private ReportStudentController controller;
	private RiskFactor riskFactor;
	private RiskFactor riskFactorSelected;
	private RiskFactor validateRiskFactor;
	private int idRiskFactorCategory;

	private String parameterSelectedName;

	public RiskFactorBean() throws Exception {
		try {

			riskFactor = new RiskFactor();
			controller = new ReportStudentController();

		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}
	}

	public void handleEditParameter(RowEditEvent event) {
		try {
			riskFactor = (RiskFactor) event.getObject();
			if (riskFactor != null) {
				saveOrUpdateParameter();
				reloadControllerList();
				update("riskFactorForm:tableRiskFactor");
				addInfo("Editar Factor de Riesgo", "El Factor de Riesgo ha sido editado exitosamente");

			}
		} catch (Exception e) {
			addError("Editar Factor de Riesgo ", "Se presento un error al intentar editar el parámetro, intente de nuevo o contacte al administrador");
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}
	}

	public void handleCancelParameter(RowEditEvent event) {
		riskFactor = (RiskFactor) event.getObject();
		update("riskFactorForm:tableRiskFactor");
	}

	private void saveOrUpdateParameter() throws RuntimeException {

		riskFactor.initializing(getUserSession().getDocumentNumber(), riskFactor.getIdRiskFactor() == null ? true : false);
		controller.saveOrUpdateParameter(riskFactor);
	}

	public void goDeleteRiskFactor() {
		try {
			if (riskFactorSelected != null) {
				boolean validate = false;

				if (riskFactorSelected.getIdRiskFactor() == 1 || riskFactorSelected.getIdRiskFactor() == 2
					|| riskFactorSelected.getIdRiskFactor() == 3 || riskFactorSelected.getIdRiskFactor() == 4
					|| riskFactorSelected.getIdRiskFactor() == 5 || riskFactorSelected.getIdRiskFactor() == 6
					|| riskFactorSelected.getIdRiskFactor() == 7 || riskFactorSelected.getIdRiskFactor() == 8) {
					addWarn("Eliminar Factor de Riesgo",
						"No se puede eliminar el factor de riesgo seleccionado ya que es un factor de riesgo predeterminado del sistema.");


				} else {

					validateRiskFactor = controller.validateRiskFactorUsed(riskFactorSelected.getIdRiskFactor());

					if (validateRiskFactor == null) {
						parameterSelectedName = null;

						StringBuilder userList = new StringBuilder();
						userList.append(riskFactorSelected.getName());


						parameterSelectedName = userList.toString();
						getRequestContext().update("riskFactorForm:delParameterPopup");
						getRequestContext().execute("delParameterPopupWV.show();");
					} else {
						addWarn("Eliminar Factor de Riesgo",
							"El Factor de Riesgo no se puede eliminar debido a que existen estudiantes reportados por dicho factor.");
						return;

					}
				}
			}
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}
	}

	public void deleteRiskFactor() {
		try {

			if (riskFactorSelected != null) {

				controller.deleteRiskFactor(getUserSession().getDocumentNumber(), riskFactorSelected);
				addInfo("Eliminar factor de riesgo", "El factor de riesgo ha sido eliminado!");
				reloadControllerList();
				update("riskFactorForm:tableRiskFactor");

			} else {
				addError("Eliminar factor de riesgo", "Se presento un error al seleccionar el factor de riesgo. Intente de nuevo por favor.");
			}


		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
			addError("Eliminar Parametro", "Se presento un error al eliminar el/los parametros, por favor contacte al administrador");
		}
	}


	public void saveRiskFactor() {
		if (riskFactor.getName() == null || riskFactor.getName() == "" || riskFactor.getDescription() == null || riskFactor.getDescription() == ""
			|| riskFactor.getIdRiskFactorCategory() == 0) {
			addWarn("Agregar factor de riesgo", "Todos los campos son obligatorios.");
		} else {
			saveOrUpdateParameter();
			addInfo("Agregar factor de riesgo", "Factor de riesgo creado con éxito!");
			clean();
			reloadControllerList();
		}

	}

	public void clean() {
		riskFactor = new RiskFactor();
		idRiskFactorCategory = 0;
	}



	/*************** setters and getters ****************** */


	public RiskFactor getRiskFactor() {
		return riskFactor;
	}

	public void setRiskFactor(RiskFactor riskFactor) {
		this.riskFactor = riskFactor;
	}

	public RiskFactor getRiskFactorSelected() {
		return riskFactorSelected;
	}

	public void setRiskFactorSelected(RiskFactor riskFactorSelected) {
		this.riskFactorSelected = riskFactorSelected;
	}

	public RiskFactor getValidateRiskFactor() {
		return validateRiskFactor;
	}

	public void setValidateRiskFactor(RiskFactor validateRiskFactor) {
		this.validateRiskFactor = validateRiskFactor;
	}

	public String getParameterSelectedName() {
		return parameterSelectedName;
	}

	public void setParameterSelectedName(String parameterSelectedName) {
		this.parameterSelectedName = parameterSelectedName;
	}

	public int getIdRiskFactorCategory() {
		return idRiskFactorCategory;
	}

	public void setIdRiskFactorCategory(int idRiskFactorCategory) {
		this.idRiskFactorCategory = idRiskFactorCategory;
	}



}
