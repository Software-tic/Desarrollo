package com.zyos.core.password.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.zyos.core.common.controller.ErrorNotificacion;
import com.zyos.core.common.controller.ZyosBackingBean;
import com.zyos.core.lo.user.controller.UserController;
import com.zyos.core.lo.user.model.ZyosUser;

@ManagedBean
@ViewScoped
public class ExternalUserBean extends ZyosBackingBean {
	private boolean initialized = false;
	private UserController controller = new UserController();
	private ZyosUser data;

	public ExternalUserBean() throws Exception {
		try {
			if (isValidateLogin()) {
				if (!initialized) {
					System.out.println("INFO: ZM_EU load personal data");
					initialized = true;
					// TODO
					data = controller.loadDataUser(getZyosUserSession());
				}
			}
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}
	}

	public void editUser() {
		try {
			if (controller.editUserData(data, getUserSession()
					.getDocumentNumber(), getUserSession().getDocumentNumber())) {
				addInfo( "Edición exitosa",
						"Los datos fueros actualizados exitosamente");
			} else {
				addError(
						"Fallo de edición",
						"Se presento un problema registrado los datos, intente de nuevo o contacte al administrador");
			}
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}
	}

	public ZyosUser getData() {
		return data;
	}

	public void setData(ZyosUser data) {
		this.data = data;
	}

}
