package com.zyos.core.common.controller;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.RowEditEvent;

import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.zyos.alert.facultyDegree.model.FacultyDegree;
import com.zyos.alert.facultyDegree.model.FacultyDegreeModel;
import com.zyos.alert.faculty.model.Faculty;
import com.zyos.alert.studentReport.model.Degree;
import com.zyos.core.common.api.IZyosState;
import com.zyos.core.common.model.AParameter;
import com.zyos.core.common.model.AParameterModel;
import com.zyos.core.common.model.ZyosParameter;
import com.zyos.core.lo.user.model.ZyosUserModel;

@ManagedBean(name = "aParameterBean")
@ViewScoped
@URLMapping(id = "degree", pattern = "/portal/gestorCarreras", viewId = "/pages/degree/degree.jspx")
public class AParameterBean extends ZyosBackingBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private boolean showListDegree = true, showAddDegree, showEditDegree, showDetailDegree;
	
	private Long idZyosParameter;
	private String parameterSelectedNameList, parameterFactorNameList, parameterDegreeNameList, name, description;
	private List<AParameter> parameterList;
	private List<FacultyDegree> FacultyDegreeList;
	private AParameter[] selectedParameterList;
	private FacultyDegree[] selectedFacultyDegreeList;
	
	private AParameter aParameter;
	private FacultyDegree selectedFacultyDegree;
	private AParameterController controller = new AParameterController();
	private AParameterModel aparameterModel;
	private FacultyDegreeModel FacultyDegreeModel;
	private ZyosParameter zyosParameter;

	// default constructor
	public AParameterBean() throws Exception {
		try {
			//loadParameterListByEnterprise(getUserSession().getDefaultEnterprise(), getUserSession().getDefaultGroup());
			//--- no se puede hacer la busqueda de las carreras por sede o seccional, no hay una relación en la base de datos para poder hacerlo
			FacultyDegreeList = controller.loadParameterList();
			setFacultyDegreeModel(new FacultyDegreeModel(FacultyDegreeList));
			
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}
	}

	// action methods

	public void goAddParameter() {
		try {
			if (name != null & !name.isEmpty()) {

				if (zyosParameter != null && zyosParameter.getDescription() != null) {
					if (validateName(name, parameterList)) {
						// using java reflexion for creating java objects
						// dynamic

						if (zyosParameter.getGlobalParameter().intValue() == 0) {
							Class<?> c = Class.forName(zyosParameter
									.getClassName());
							Class[] params = { Long.class };
							Constructor<?> constructor = c
									.getConstructor(params);

							aParameter = (AParameter) constructor.newInstance(getUserSession().getDefaultEnterprise());
						} else {
							aParameter = (AParameter) Class.forName(
									zyosParameter.getClassName()).newInstance();
						}
						parameterList.add(0, aParameter);
						aParameter.setName(name);
						aParameter.setDescription(description);
						saveOrUpdateParameter();
						clean();
						addInfo(
								"Crear parámetro",
								"El parámetro ha sido creado exitosamente");
						reloadControllerList();

					} else {
						addWarn(
								"Crear Parámetro",
								"El parámetro ya se encuentra registrado en el listado");
					}
				} else {
					addError( "Crear Parámetro",
							"Debe seleccionar un tipo de parámetro del listado");
				}
			} else {
				addError( "Adicionar Parámetro",
						"El nombre no puede ser vacio.");
			}

		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}
	}

	public void goAddParameterRiskFactor() {
		try {
			if (zyosParameter != null && zyosParameter.getDescription() != null) {
				if (validateName(name, parameterList)) {
					// using java reflexion for creating java objects dynamic

					if (zyosParameter.getGlobalParameter().intValue() == 0) {
						Class<?> c = Class
								.forName(zyosParameter.getClassName());
						Class[] params = { Long.class };
						Constructor<?> constructor = c.getConstructor(params);

						aParameter = (AParameter) constructor
								.newInstance(getUserSession()
										.getDefaultEnterprise());
					} else {
						aParameter = (AParameter) Class.forName(
								zyosParameter.getClassName()).newInstance();
					}
					parameterList.add(0, aParameter);
					aParameter.setName(name);
					aParameter.setDescription(name);
					saveOrUpdateParameter();
					clean();

					addInfo(
							"Crear Factor de Riesgo",
							"El Factor de Riesgo ha sido creado exitosamente");
					reloadControllerList();
					update("riskFactorForm");

				} else {
					addWarn(
							"Crear Factor de Riesgo",
							"El Factor de Riesgo ya se encuentra registrado en el listado");
				}
			} else {
				addError(
						"Crear Factor de Riesgo",
						"Debe seleccionar un tipo de parámetro del listado");
			}

		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}
	}

	public void goAddParameterCareer() {
		try {
			if (zyosParameter != null && zyosParameter.getDescription() != null) {
				if (validateName(name, parameterList)) {
					// using java reflexion for creating java objects dynamic

					if (zyosParameter.getGlobalParameter().intValue() == 0) {
						Class<?> c = Class.forName(zyosParameter.getClassName());
						Class[] params = { Long.class };
						Constructor<?> constructor = c.getConstructor(params);

						aParameter = (AParameter) constructor
								.newInstance(getUserSession()
										.getDefaultEnterprise());
					} else {
						aParameter = (AParameter) Class.forName(
								zyosParameter.getClassName()).newInstance();
					}
					parameterList.add(0, aParameter);
					aParameter.setName(name);
					aParameter.setDescription(name);
					saveOrUpdateParameter();
					clean();

					addInfo( "Crear Carrera",
							"La Carrera ha sido creada exitosamente");
					reloadControllerList();
					update("careerForm");

				} else {
					addWarn( "Crear Carrera",
							"La carrera ya se encuentra registrada en el listado");
				}
			} else {
				addError( "Crear Carrera",
						"Debe seleccionar un tipo de parámetro del listado");
			}

		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}
	}

	public static boolean validateName(String name2,
			List<AParameter> parameterList2) throws Exception {
		if (parameterList2 != null)
			for (AParameter p : parameterList2)
				if (p.getName().trim().equals(name2.trim()))
					return false;
		return true;
	}

	
	
	public boolean validateDegreeUsed(AParameter[] selectedParameterList2) {
		try {

			List<Long> idDegree = new ArrayList<Long>();			

			for (AParameter l : selectedParameterList2) {
				idDegree.add(l.getId());
			}

			List<String> parameter = controller
					.validateDegreeUsed(idDegree);

			if (parameter == null || parameter.isEmpty()) {
				return false;
			} else {
		
				
				StringBuilder parameterDegreeList = new StringBuilder();
				for (String zu : parameter) {
					parameterDegreeList.append(zu);
					parameterDegreeList.append(", ");
				}
				parameterDegreeNameList = parameterDegreeList.toString();
				getRequestContext().update("degreeForm:delParameterPopup3");
				getRequestContext().execute("delParameterPopupWV3.show();");
			
				return true;

			}
		

		} catch (Exception e) {

			ErrorNotificacion.handleErrorMailNotification(e, this);
		}
		return true;

	}
	
	public void goEditParameter() {
		
	}

	public void goDetailParameter() {
		
	}

	public void goDeleteParameter() {
		try {
			if (selectedParameterList != null && selectedParameterList.length > 0) {			
				boolean validate = false;
			
				if (zyosParameter.getId() == 2) {
					validate = validateDegreeUsed(selectedParameterList);
				}

				if (validate == false) {
					parameterSelectedNameList = null;

					StringBuilder userList = new StringBuilder();

					for (AParameter zu : selectedParameterList) {
						userList.append(zu.getName());
						userList.append(", ");
					}
					parameterSelectedNameList = userList.toString();
					getRequestContext().execute("delParameterPopupWV.show();");
				} else {
					return;
				}

			} else {
				addWarn( "Borrar Parametros", "Debe seleccionar por lo menos un parametro para continuar");

			}
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}
	}

	// save, delete, update and clean methods

	private void saveOrUpdateParameter() throws RuntimeException {
		aParameter.initializing(getUserSession().getDocumentNumber(),
				aParameter.getId() == null ? true : false);
		controller.saveOrUpdateParameter(aParameter);
	}

	public void deleteParameter() {
		try {
			aParameter.setState(IZyosState.INACTIVE);
			saveOrUpdateParameter();
			addInfo( "Eliminar parámetro",
					"El parámetro ha sido eliminado");
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
			addError(
					"Eliminar Parámetro",
					"Se presento un error al eliminar el/los parámetro(s), por favor contacte al administrador");
		}
	}

	private void saveOrUpdateParameter(boolean isNew) throws RuntimeException {
		try {
			if (aParameter.getName() != null && !aParameter.getName().isEmpty()) {

				aParameter.initializing(getUserSession().getId().toString(),
						isNew);
				aParameter.setState(IZyosState.INACTIVE);
				controller.saveOrUpdateParameter(aParameter);

			}
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}
	}

	public void delParameter() {
		try {
			for (AParameter parameter : selectedParameterList) {
				parameterList.remove(parameter);
				aParameter = parameter;
				aParameter.setState(IZyosState.INACTIVE);
				saveOrUpdateParameter(false);
			}
			// update("riskFactorForm:tableRiskFactor");
			addInfo( "Eliminar Parametro",
					"El parametro ha sido eliminado");
			reloadControllerList();

		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
			addError(
					"Eliminar Parametro",
					"Se presento un error al eliminar el/los parametros, por favor contacte al administrador");
		}
	}

	public void clean() {
		name = null;
		description = null;
		aParameter = null;
	}

	// get and set methods

	public AParameter getaParameter() {
		return aParameter;
	}

	public void setaParameter(AParameter aParameter) {
		this.aParameter = aParameter;
	}

	public ZyosParameter getZyosParameter() {
		return zyosParameter;
	}

	public void setZyosParameter(ZyosParameter zyosParameter) {
		this.zyosParameter = zyosParameter;
	}

	public String getParameterSelectedNameList() {
		return parameterSelectedNameList;
	}

	public void setParameterSelectedNameList(String parameterSelectedNameList) {
		this.parameterSelectedNameList = parameterSelectedNameList;
	}

	public List<AParameter> getParameterList() {
		return parameterList;
	}

	public void setParameterList(List<AParameter> parameterList) {
		this.parameterList = parameterList;
	}

	public AParameter[] getSelectedParameterList() {
		return selectedParameterList;
	}

	public void setSelectedParameterList(AParameter[] selectedParameterList) {
		this.selectedParameterList = selectedParameterList;
	}

	public AParameterModel getAparameterModel() {
		return aparameterModel;
	}

	public void setAparameterModel(AParameterModel aparameterModel) {
		this.aparameterModel = aparameterModel;
	}

	public Long getIdZyosParameter() {
		return idZyosParameter;
	}

	public void setIdZyosParameter(Long idZyosParameter) {
		this.idZyosParameter = idZyosParameter;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getParameterFactorNameList() {
		return parameterFactorNameList;
	}

	public void setParameterFactorNameList(String parameterFactorNameList) {
		this.parameterFactorNameList = parameterFactorNameList;
	}

	public String getParameterDegreeNameList() {
		return parameterDegreeNameList;
	}

	public void setParameterDegreeNameList(String parameterDegreeNameList) {
		this.parameterDegreeNameList = parameterDegreeNameList;
	}
		
	public boolean isshowListDegree() {
		return showListDegree;
	}

	public void setshowListDegree(boolean showListDegree) {
		this.showListDegree = showListDegree;
		showAddDegree = showDetailDegree = showEditDegree = false;
	}

	public boolean isshowAddDegree() {
		return showAddDegree;
	}

	public void setshowAddDegree(boolean showAddDegree) {
		this.showAddDegree = showAddDegree;
		showEditDegree = showListDegree = false;
	}

	public boolean isshowEditDegree() {
		return showEditDegree;
	}

	public void setshowEditDegree(boolean showEditDegree) {
		this.showEditDegree = showEditDegree;
		showAddDegree = showDetailDegree = showListDegree = false;
	}

	public boolean isshowDetailDegree() {
		return showDetailDegree;
	}

	public void setshowDetailDegree(boolean showDetailDegree) {
		this.showDetailDegree = showDetailDegree;
		showAddDegree =  showEditDegree = showListDegree = false;
	}

	public FacultyDegreeModel getFacultyDegreeModel() {
		return FacultyDegreeModel;
	}

	public void setFacultyDegreeModel(FacultyDegreeModel facultyDegreeModel) {
		FacultyDegreeModel = facultyDegreeModel;
	}

	public FacultyDegree[] getSelectedFacultyDegreeList() {
		return selectedFacultyDegreeList;
	}

	public void setSelectedFacultyDegreeList(FacultyDegree[] selectedFacultyDegreeList) {
		this.selectedFacultyDegreeList = selectedFacultyDegreeList;
	}
	
	public List<FacultyDegree> getFacultyDegreeList() {
		return FacultyDegreeList;
	}

	public void setFacultyDegreeList(List<FacultyDegree> FacultyDegreeList) {
		this. FacultyDegreeList = FacultyDegreeList;
	}
	
	public FacultyDegree getSelectedFacultyDegree() {
		return selectedFacultyDegree;
	}

	public void setSelectedFacultyDegree(FacultyDegree selectedFacultyDegree) {
		this.selectedFacultyDegree = selectedFacultyDegree;
	}

}
