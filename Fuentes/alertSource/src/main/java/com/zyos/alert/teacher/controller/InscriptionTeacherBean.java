package com.zyos.alert.teacher.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.context.RequestContext;

import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.zyos.core.common.controller.ErrorNotificacion;
import com.zyos.core.common.controller.ZyosBackingBean;
import com.zyos.core.common.util.security.RSA;
import com.zyos.core.lo.user.model.ZyosUser;
import com.zyos.core.login.model.ZyosLogin;
import com.zyos.core.mail.io.mn.api.IEmailTemplate;
import com.zyos.core.mail.io.mn.aws.MailGeneratorFunction;
import com.zyos.core.mail.io.mn.aws.SMTPEmail;
import com.zyos.core.mail.io.mn.model.EmailTemplate;

@ManagedBean
@ViewScoped
@URLMapping(id = "inscriptionTeacherBean", pattern = "/portal/registroDocente", viewId = "/pages/inscriptionTeacher/inscriptionTeacher.jspx")
public class InscriptionTeacherBean {

	private ZyosUser user;
	private ZyosUser zyosUser;
	private ZyosLogin zyosLogin;
	private InscriptionTeacherController controller;

	public InscriptionTeacherBean() throws Exception {
		user = new ZyosUser();
		zyosLogin = new ZyosLogin();
	}

	/**
	 * @author jhernandez
	 * */
	public void saveTeacherUser() {
		try {

			if (user.getName() == null || user.getName().isEmpty()) {
				ZyosBackingBean.addWarn(" Nombre Inválido", " El campo de Nombre se encuentra vacio. ");
				return;
			}

			if (user.getDocumentNumber() == null || user.getDocumentNumber().isEmpty()) {
				ZyosBackingBean.addWarn(" Documento Inválido", " El campo de documento se encuentra vacio. ");
				return;
			}

			if (user.getEmail() == null || user.getEmail().isEmpty()) {
				ZyosBackingBean.addWarn(" Email Inválido", " El campo de correo eléctronico se encuentra vacio. ");
				return;
			}
			if (zyosLogin.getPassword() == null || zyosLogin.getPassword().isEmpty()) {
				ZyosBackingBean.addWarn(" Contraseña Invalida", " El campo de contraseña se encuentra vacio. ");
				return;
			}

			boolean val = validationTeacherUDIES();

			if (val == true) {

				controller = new InscriptionTeacherController();
				String validateDuplicateUsuer = controller.validateDuplicateUser(user.getEmail());

				if (validateDuplicateUsuer == null) {

					zyosUser = new ZyosUser();
					zyosUser = controller.validateZyosUser(user.getDocumentNumber());

					if (zyosUser == null) {
						ZyosBackingBean.addWarn(" Registro Inválido",
							" El docente NO se encuentra registrado en la plataforma. Consulte al administrador. ");
						return;

					} else {
						zyosLogin.setPassword(zyosLogin.getPassword());
						zyosLogin.setPasswordMD5(RSA.encrypt(zyosLogin.getPassword()));
						zyosLogin.setFirstLogin(0);
						zyosLogin.setUserLogin(user.getEmail());
						zyosLogin.setIdZyosUser(zyosUser.getIdZyosUser());

						controller.saveTeacherZyosLogin(zyosLogin, user.getDocumentNumber(), zyosUser);

						sendEmailInscriptionTeacher();

						ZyosBackingBean.getRequestContext().execute("validateInsTeacher.show();");

						clearDataInsTeacher();
					}

				} else {
					ZyosBackingBean.addWarn(" Email Inválido ", " El correo ingresado ya se encuentra registrado. ");
					return;

				}

			}

			else {
				RequestContext.getCurrentInstance().execute("validateFail.show(); ");
			}
		}

		catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);

		}
	}

	// VALIDACION UDIES - se realiza de esta manera para ser cambiado
	// posteriormente. Default true
	public boolean validationTeacherUDIES() {

		return true;
	}

	/**
	 * @author jhernandez
	 * */
	public void clearDataInsTeacher() {
		try {
			user = new ZyosUser();
			zyosLogin = new ZyosLogin();

		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}
	}

	/**
	 * @author jhernandez
	 * */
	public void sendEmailInscriptionTeacher() {
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

}
