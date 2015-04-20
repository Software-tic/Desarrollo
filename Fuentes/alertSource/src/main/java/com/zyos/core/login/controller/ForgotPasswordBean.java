package com.zyos.core.login.controller;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.zyos.core.common.controller.ErrorNotificacion;
import com.zyos.core.common.controller.ZyosBackingBean;
import com.zyos.core.common.controller.ZyosController;

@ManagedBean
@ViewScoped
public class ForgotPasswordBean implements Serializable {

	private String userName = "";

	private String userMail = "";

	private ZyosController controller = null;

	public static String NUMBERS = "0123456789";

	public static String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

	public static String LOWER = "abcdefghijklmnopqrstuvwxyz";

	public static String OTHERS = "��";

	/**
	 * Default constructor
	 * 
	 * @throws Exception
	 */
	public ForgotPasswordBean() throws Exception {
		try {
			controller = new ZyosController();
			clearData();
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, "system");
		}
	}

	public String validateForgotPassword() {
		try {
			if (!userName.isEmpty() && !userMail.isEmpty()) {
				if (controller.validateForgotPassword(userMail, userName, getPassword(7))) {
					ZyosBackingBean.addInfo("Restablecer contrase�a",
						"Se ha enviado un correo electr�nico con los datos necesarios para recuperar su contrase�a");
					clearData();
					return null;
				}
			}
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, "system");
		}
		ZyosBackingBean.addError("Restablecer contrase�a",
			"Los datos ingresados no coinciden, por favor asegures� de ingresar los datos correctos e intentelo de nuevo.");
		return null;
	}

	private void clearData() {
		setUserMail("");
		setUserName("");
	}

	public static String getPinNumber() {
		return getPassword(NUMBERS, 4);
	}

	public static String getPassword() {
		return getPassword(8);
	}

	public static String getPassword(int length) {
		return getPassword(NUMBERS + UPPER + LOWER, length);
	}

	public static String getPassword(String key, int length) {
		StringBuilder pswd = new StringBuilder();
		for (int i = 0; i < length; i++) {
			pswd.append(key.charAt((int) (Math.random() * key.length())));
		}
		return pswd.toString();
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserMail() {
		return userMail;
	}

	public void setUserMail(String userMail) {
		this.userMail = userMail;
	}

}
