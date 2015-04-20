package com.zyos.core.mail.io.mn.controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.zyos.core.common.controller.ErrorNotificacion;
import com.zyos.core.common.controller.ZyosBackingBean;
import com.zyos.core.password.controller.ExternalUserController;

/**
 * Class for work with the notifications
 * 
 * @author Oscar garzon
 * 
 */
@ManagedBean
@ViewScoped
public class NotificationBean extends ZyosBackingBean {
	/**
	 * Controller
	 */
	private ExternalUserController controller;

	/**
	 * temporal object for contain the selected notifications by user
	 */
	private List selectedCutOffTime;

	/**
	 * Array that contains the notifications enables by user
	 */
	private ArrayList<Long> selectedCutByUser = new ArrayList<Long>();

	/**
	 * Default constructor
	 * 
	 * @throws Exception
	 */
	public NotificationBean() throws Exception {
		try {
			controller = new ExternalUserController();
			selectedCutByUser = controller
					.loadCutOffTimeByUser(getUserSession().getId());
			selectedCutOffTime = selectedCutByUser;
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e,
					getUserSession() == null ? "system" : getUserSession()
							.getFullName());
		}
	}

	/**
	 * Method that save the changes
	 * 
	 * @return
	 */
	public void saveChangeNotification() {
		try {
			for (int i = 0; i < selectedCutOffTime.size(); i++) {
				Long item = Long.valueOf(selectedCutOffTime.get(i).toString());
				if (!selectedCutByUser.contains(item))
					controller.saveNotificationByUser(getUserSession().getId(),
							item, Long.valueOf(1), getUserSession()
									.getDocumentNumber());
				else
					selectedCutByUser.remove(item);

			}
			if (!selectedCutByUser.isEmpty())
				for (Long item : selectedCutByUser)
					controller.removeNotificationByUser(getUserSession()
							.getId(), item);
			selectedCutByUser.clear();
			for (Object item : selectedCutOffTime) {
				selectedCutByUser.add(Long.valueOf(item.toString()));
			}
			addInfo( "Notificaciones",
					"Se ha guardado las preferencias de notificación éxitosamente");
			return;
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e,
					getUserSession() == null ? "system" : getUserSession()
							.getFullName());
		}
		addInfo(
				"Notificaciones",
				"Se presento un problema guardando las preferencias de notificaciones, contacte al administrador");
	}

	public List getSelectedCutOffTime() {
		return selectedCutOffTime;
	}

	public void setSelectedCutOffTime(List selectedCutOffTime) {
		this.selectedCutOffTime = selectedCutOffTime;
	}

}
