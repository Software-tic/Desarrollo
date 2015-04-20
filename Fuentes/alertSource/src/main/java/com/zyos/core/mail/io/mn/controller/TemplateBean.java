package com.zyos.core.mail.io.mn.controller;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.zyos.core.common.controller.ErrorNotificacion;
import com.zyos.core.common.controller.ZyosBackingBean;
import com.zyos.core.mail.io.mn.model.EmailTemplate;

@ManagedBean
@ViewScoped
public class TemplateBean extends ZyosBackingBean {

	private boolean showListTemplate = true, showAddTemplate, showEditTemplate,
			showDetailTemplate;

	private boolean loginTemplateExist;
	private boolean documentTemplateExist;

	private String templateSelectedNameList, templateSubject, templateName;
	private Long idTemplateType;

	private EmailTemplate[] selectedTemplateList;

	private EmailTemplateModel templateModel;

	private EmailTemplate selectedTemplate;
	private TemplateController controller = new TemplateController();

	public TemplateBean() throws Exception {
		try {
			templateModel = new EmailTemplateModel(getEmailTemplateList());
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}
	}

	// Handle and validate methods

	// end handle method

	// go Method

	public void goAddTemplate() {
		try {
			selectedTemplate = new EmailTemplate();
			selectedTemplate.setIdEnterprise(getUserSession()
					.getDefaultEnterprise());
			clearData();
			setShowAddTemplate(true);
			setPanelView("addTemplate", "Agregar Plantilla", "TemplateBean");
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}
	}

	public void goEditTemplate() {
		try {
			templateSubject = selectedTemplate.getSubject();
			templateName = selectedTemplate.getName();

			setShowEditTemplate(true);
			setPanelView("editTemplate", "Editar Plantilla", "TemplateBean");
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}
	}

	public void goDeleteTemplate() {
		try {
			if (selectedTemplateList != null && selectedTemplateList.length > 0) {
				templateSelectedNameList = null;
				StringBuilder templateList = new StringBuilder();
				for (EmailTemplate zu : selectedTemplateList) {
					templateList.append(zu.getName());
					templateList.append(", ");
				}
				templateSelectedNameList = templateList.toString();
				getRequestContext().execute("delTemplatePopup.show();");
			} else {
				addWarn( "Borrar Plantilla",
						"Debe seleccionar por lo menos una plantilla para continuar.");
			}
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}
	}

	public void goDetailTemplate() {
		try {
			templateSelectedNameList = "";

			templateSubject = selectedTemplate.getSubject();
			templateName = selectedTemplate.getName();
			idTemplateType = selectedTemplate.getIdEmailTemplateType();

			setShowDetailTemplate(true);
			setPanelView("editTemplate", "Editar Plantilla", "TemplateBean");
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}
	}

	public void goBack() {
		try {
			setShowListTemplate(true);
			setPanelView("addTemplate", "Agregar Plantilla", "TemplateBean");
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}
	}

	// end go method

	// action, save, edit or delete methods

	private void clearData() {
		loginTemplateExist = documentTemplateExist = false;
		templateSelectedNameList = templateSubject = templateName = null;
	}

	public boolean validateTemplate() {
		try {
			if (selectedTemplate != null && templateSubject != null
					&& selectedTemplate.getBody() != null
					&& templateName != null) {
				return true;
			}
			return false;
		} catch (RuntimeException e) {
			throw e;
		}
	}

	public void saveTemplate() {
		saveOrEditProcess(true);
	}

	public void editTemplate() {
		saveOrEditProcess(false);
	}

	private void saveOrEditProcess(boolean isNew) throws RuntimeException {
		try {
			if (validateTemplate()) {
				selectedTemplate.setSubject(templateSubject);
				selectedTemplate.setName(templateName);
				selectedTemplate.setIdEmailTemplateType(idTemplateType);
				selectedTemplate.setNameTemplateType(getNameLabelList(getEmailTemplateTypeList(), idTemplateType));
				selectedTemplate.initializing(getUserSession()
						.getDocumentNumber(), isNew);

				if (isNew) {
					controller.saveEmailTemplate(selectedTemplate);
					getEmailTemplateList().add(selectedTemplate);
				} else {
					controller.editEmailTemplate(selectedTemplate);
				}

				addInfo( "Guardar Plantilla",
						"El plantilla fue guardado exitosamente");
				setShowListTemplate(true);
				setPanelView("TemplateList", "Listar Plantillas",
						"TemplateBean");
			} else {
				addError(
						"Guardar Plantilla",
						"Se presento un error en la validación de plantilla, valide los campos e intente de nuevo");
			}
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
			addError(
					"Editar Plantilla",
					"Se presento un error al guardar la plantilla, por favor contacte al administrador");
		}
	}

	public void deleteTemplate() {
		try {
			if (selectedTemplateList != null && selectedTemplateList.length > 0) {
				controller.deleteEmailTemplate(selectedTemplateList,
						getUserSession().getDocumentNumber());
				for (EmailTemplate e : selectedTemplateList)
					getEmailTemplateList().remove(e);

				addInfo( "Eliminar Plantilla",
						"Se han deshabilitado el/las plantilla(s) seleccionado(s) correctamente");
			}
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
			addError(
					"Eliminar Plantilla",
					"Se presento un error al eliminar el/las plantilla(s), por favor contacte al administrador");
		}
	}

	// show methods

	public boolean isShowListTemplate() {
		return showListTemplate;
	}

	public void setShowListTemplate(boolean showListTemplate) {
		this.showListTemplate = showListTemplate;
		showAddTemplate = showDetailTemplate = showEditTemplate = false;
	}

	public boolean isShowAddTemplate() {
		return showAddTemplate;
	}

	public void setShowAddTemplate(boolean showAddTemplate) {
		this.showAddTemplate = showAddTemplate;
		showDetailTemplate = showEditTemplate = showListTemplate = false;
	}

	public boolean isShowEditTemplate() {
		return showEditTemplate;
	}

	public void setShowEditTemplate(boolean showEditTemplate) {
		this.showEditTemplate = showEditTemplate;
		showAddTemplate = showDetailTemplate = showListTemplate = false;
	}

	public boolean isShowDetailTemplate() {
		return showDetailTemplate;
	}

	public void setShowDetailTemplate(boolean showDetailTemplate) {
		this.showDetailTemplate = showDetailTemplate;
		showAddTemplate = showEditTemplate = showListTemplate = false;
	}

	// end

	public boolean isLoginTemplateExist() {
		return loginTemplateExist;
	}

	public void setLoginTemplateExist(boolean loginTemplateExist) {
		this.loginTemplateExist = loginTemplateExist;
	}

	public boolean isDocumentTemplateExist() {
		return documentTemplateExist;
	}

	public void setDocumentTemplateExist(boolean documentTemplateExist) {
		this.documentTemplateExist = documentTemplateExist;
	}

	public String getTemplateSelectedNameList() {
		return templateSelectedNameList;
	}

	public void setTemplateSelectedNameList(String templateSelectedNameList) {
		this.templateSelectedNameList = templateSelectedNameList;
	}

	public String getTemplateSubject() {
		return templateSubject;
	}

	public void setTemplateSubject(String templateSubject) {
		this.templateSubject = templateSubject;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public EmailTemplate[] getSelectedTemplateList() {
		return selectedTemplateList;
	}

	public void setSelectedTemplateList(EmailTemplate[] selectedTemplateList) {
		this.selectedTemplateList = selectedTemplateList;
	}

	public EmailTemplateModel getTemplateModel() {
		return templateModel;
	}

	public void setTemplateModel(EmailTemplateModel templateModel) {
		this.templateModel = templateModel;
	}

	public EmailTemplate getSelectedTemplate() {
		return selectedTemplate;
	}

	public void setSelectedTemplate(EmailTemplate selectedTemplate) {
		this.selectedTemplate = selectedTemplate;
	}

	public Long getIdTemplateType() {
		return idTemplateType;
	}

	public void setIdTemplateType(Long idTemplateType) {
		this.idTemplateType = idTemplateType;
	}

}
