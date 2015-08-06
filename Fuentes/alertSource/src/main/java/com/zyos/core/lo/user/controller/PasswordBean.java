package com.zyos.core.lo.user.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;


import com.zyos.core.common.controller.ErrorNotificacion;
import com.zyos.core.common.controller.ZyosBackingBean;
import com.zyos.core.password.controller.ExternalUserController;

@ManagedBean(name = "passwordBean")
@ViewScoped
public class PasswordBean extends ZyosBackingBean {

	private boolean initialized = false;
	private boolean stop;
	private String oldPassword = "";
	private String newPassword = "";
	private String newRetryPassword = "";
	ExternalUserController controller = new ExternalUserController();

	/**
	 * Default constructor
	 * 
	 * @throws Exception
	 */
	public PasswordBean() throws Exception {
		try {
			if (isValidateLogin()) {
				if (!initialized) {
					System.out
							.println("INFO: ZM-UE - Load External User Password");
					initialized = true;
					cleanPasswords();
				}
			}
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e,
					getUserSession() == null ? "system" : getUserSession()
							.getFullName());
			redirectErrorPage();
		}
	}

	/**
	 * Method for cleaning and reseting the fields
	 */
	public void cleanPasswords() {
		this.newPassword = "";
		this.newRetryPassword = "";
		this.oldPassword = "";
	}

	/**
	 * Method for save a new password. After validate if the old password is
	 * correct
	 */
	public void saveNewPassword() {
		try {
			if (newPassword.equals(newRetryPassword)) {

				if (controller.validateOldPassword(this.oldPassword,
						this.newPassword, getUserSession().getId(),
						getUserSession().getDocumentNumber())) {
					addInfo( "Registro exitoso",
							"La contraseña fue modificada exitosamente");
					cleanPasswords();	
					getRequestContext().execute("msgPassSuccess.show()");
					//redirectToLogin();
				} else {
					addError(
							"Fallo de validación",
							"La contraseña anterior no coincide, valide e intente de nuevo");
				}
			} else {
				addError( "Fallo de validación",
						"Las contraseñas no coinciden validelas e intente de nuevo");
			}

		} catch (Exception e) {
			System.out.println("ERROR: Impossible insert the new password");
		} finally {
			controller = null;
		}
	}
	
	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getNewRetryPassword() {
		return newRetryPassword;
	}

	public void setNewRetryPassword(String newRetryPassword) {
		this.newRetryPassword = newRetryPassword;
	}

	public boolean isStop() {
		return stop;
	}

	public void setStop(boolean stop) {
		this.stop = stop;
	}

	
}
