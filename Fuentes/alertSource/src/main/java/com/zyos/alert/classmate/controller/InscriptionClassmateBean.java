package com.zyos.alert.classmate.controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.context.RequestContext;

import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.zyos.alert.studentReport.controller.SubjectController;
import com.zyos.alert.studentReport.model.Subject;
import com.zyos.core.common.controller.ErrorNotificacion;
import com.zyos.core.common.controller.ZyosBackingBean;
import com.zyos.core.common.util.ManageDate;
import com.zyos.core.common.util.security.RSA;
import com.zyos.core.lo.user.model.ZyosUser;
import com.zyos.core.lo.user.model.ZyosUserGroup;
import com.zyos.core.login.model.ZyosLogin;
import com.zyos.core.mail.io.mn.api.IEmailTemplate;
import com.zyos.core.mail.io.mn.aws.MailGeneratorFunction;
import com.zyos.core.mail.io.mn.aws.SMTPEmail;
import com.zyos.core.mail.io.mn.model.EmailTemplate;

@ManagedBean
@ViewScoped
@URLMapping(id = "inscriptionClassmateBean", pattern = "/portal/registroCompanero", viewId = "/pages/inscriptionClassmate/inscriptionClassmate.jspx")
public class InscriptionClassmateBean {

	private List<Subject> subjectList;
	private List<Subject> subjectNonList;
	private List<ZyosUser> zyosUserList;
	private List<String> selectedSubjectList;

	private ZyosUser user;
	private ZyosUser zyosUser;
	private ZyosLogin zyosLogin;
	private ZyosUserGroup zyosUserGroup;
	private SubjectController controllerSubject;
	private InscriptionClassmateController controller;

	private boolean validateClassmate = false;

	public InscriptionClassmateBean() throws Exception {

		user = new ZyosUser();
		zyosLogin = new ZyosLogin();
		zyosUserGroup = new ZyosUserGroup();
	}

	/**
	 * @author jhernandez
	 * */
	public void validateClassmateUser() {
		try {

			if (user.getName() == null || user.getName().isEmpty()) {
				ZyosBackingBean.addWarn(" Nombre Inválido", " El Campo Nombre Se Encuentra Vacio. ");
				return;
			}

			if (user.getDocumentNumber() == null || user.getDocumentNumber().isEmpty()) {
				ZyosBackingBean.addWarn(" Documento Inválido", " El Campo Documento Se Encuentra Vacio. ");
				return;
			}

			if (user.getEmail() == null || user.getEmail().isEmpty()) {
				ZyosBackingBean.addWarn(" Email Inválido", " El Campo Correo Eléctronico Se Encuentra Vacio. ");
				return;
			}

			if (zyosLogin.getPassword() == null || zyosLogin.getPassword().isEmpty()) {
				ZyosBackingBean.addWarn(" Contraseña Inválida", " El Campo Contraseña Se Encuentra Vacio.");
				return;
			}

			boolean val = validateClassmateAccountUDIES();

			if (val == true) {

				controllerSubject = new SubjectController();
				controller = new InscriptionClassmateController();
				zyosUserList = new ArrayList<ZyosUser>();
				zyosUser = new ZyosUser();

				String validateDuplicateUsuer = controller.validateDuplicateUser(user.getEmail());

				if (validateDuplicateUsuer == null) {

					zyosUser = controller.validateZyosUser(user.getDocumentNumber());

					if (zyosUser == null) {
						ZyosBackingBean.addWarn(" Error ", " El estudiante NO se encuentra registrado en la plataforma. Consulte al administrador. ");
						return;

					} else {

						subjectList = new ArrayList<Subject>();
						subjectNonList = new ArrayList<Subject>();
						subjectList =
							controllerSubject.loadSubjectListByAcademicP(zyosUser.getIdZyosUser(), ManageDate.getCurrentDate(ManageDate.YYYY_MM_DD),
								zyosUser.getIdZyosGroup());
						subjectNonList = controller.loadNonSubjectList(subjectList);

						RequestContext.getCurrentInstance().execute("validateInsClassm.show(); ");

					}

				} else {
					ZyosBackingBean.addWarn(" Email Inválido ", " El correo ingresado ya se encuentra registrado. ");
					return;

				}
			} else {
				RequestContext.getCurrentInstance().execute("validateFailClassmate.show(); ");

			}

		}

		catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}
	}

	/**
	 * @author jhernandez
	 * */
	public void sendEmailInscriptionClassmate() {
		try {

			EmailTemplate t = MailGeneratorFunction.getEmailTemplate(IEmailTemplate.CREATE_USER, (long) 1);

			if (t != null) {
				SMTPEmail e = new SMTPEmail();
				e.sendProcessEmail(null, t.getSubject(),
					MailGeneratorFunction.createGenericMessage(t.getBody(), t.getAnalyticsCode(), user.getEmail(), zyosLogin.getPassword()),
					user.getEmail());
			} else {
				ZyosBackingBean.addWarn("Alerta", "Error al enviar correo eléctronico. ");

			}

		} catch (Exception e) {
			ZyosBackingBean.addWarn("Enviar Correo", "Error al enviar correo");
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}
	}

	// VALIDACION UDIES se realiza de esta manera para ser cambiado
	// posteriormente. Default true
	public boolean validateClassmateAccountUDIES() {
		return true;
	}

	/**
	 * @author jhernandez
	 * */
	public void saveClassmate() throws Exception {
		int cont = 0;

		if (!selectedSubjectList.isEmpty()) {

			if (subjectList.size() == selectedSubjectList.size()) {
				for (Subject s : subjectList) {

					for (String ss : selectedSubjectList) {

						if (s.getName().equalsIgnoreCase(ss)) {
							cont++;
						}
					}
				}

				if (cont == subjectList.size()) {

					zyosLogin.setPassword(zyosLogin.getPassword());
					zyosLogin.setPasswordMD5(RSA.encrypt(zyosLogin.getPassword()));
					zyosLogin.setFirstLogin(0);
					zyosLogin.setUserLogin(user.getEmail());
					zyosLogin.setIdZyosUser(zyosUser.getIdZyosUser());

					controller.saveClassmateUser(zyosLogin, zyosUser, user.getDocumentNumber());

					sendEmailInscriptionClassmate();

					RequestContext.getCurrentInstance().execute("validateInsClassm.hide(); ");

					ZyosBackingBean.getRequestContext().execute("validateInsClassmate.show();");

					clearDataInsClassmate();

				} else {
					ZyosBackingBean.addWarn("Materias Seleccionadas", "Las materias Seleccionadas NO coinciden con las cursadas actualmente.");
					return;
				}

			} else {

				ZyosBackingBean.addWarn("Materias Seleccionadas", "Las materias Seleccionadas no coinciden con las cursadas actualmente.");
				return;

			}
		} else {

			ZyosBackingBean.addWarn("Validación Registro", "No se encuentran materias seleccionadas.");
			return;

		}

	}

	/**
	 * @author jhernandez
	 * */
	public void clearDataInsClassmate() {
		try {
			user = new ZyosUser();
			zyosLogin = new ZyosLogin();

		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}
	}

	public ZyosUser getUser() {
		return user;
	}

	public void setUser(ZyosUser user) {
		this.user = user;
	}

	public ZyosLogin getZyosLogin() {
		return zyosLogin;
	}

	public void setZyosLogin(ZyosLogin zyosLogin) {
		this.zyosLogin = zyosLogin;
	}

	public List<Subject> getSubjectList() {
		return subjectList;
	}

	public void setSubjectList(List<Subject> subjectList) {
		this.subjectList = subjectList;
	}

	public List<Subject> getSubjectNonList() {
		return subjectNonList;
	}

	public void setSubjectNonList(List<Subject> subjectNonList) {
		this.subjectNonList = subjectNonList;
	}

	public List<String> getSelectedSubjectList() {
		return selectedSubjectList;
	}

	public void setSelectedSubjectList(List<String> selectedSubjectList) {
		this.selectedSubjectList = selectedSubjectList;
	}

	public boolean isValidateClassmate() {
		return validateClassmate;
	}

	public void setValidateClassmate(boolean validateClassmate) {
		this.validateClassmate = validateClassmate;
	}

	public List<ZyosUser> getZyosUserList() {
		return zyosUserList;
	}

	public void setZyosUserList(List<ZyosUser> zyosUserList) {
		this.zyosUserList = zyosUserList;
	}

	public ZyosUser getZyosUser() {
		return zyosUser;
	}

	public void setZyosUser(ZyosUser zyosUser) {
		this.zyosUser = zyosUser;
	}

	public ZyosUserGroup getZyosUserGroup() {
		return zyosUserGroup;
	}

	public void setZyosUserGroup(ZyosUserGroup zyosUserGroup) {
		this.zyosUserGroup = zyosUserGroup;
	}



}
