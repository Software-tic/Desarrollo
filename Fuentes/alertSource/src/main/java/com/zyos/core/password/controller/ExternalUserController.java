package com.zyos.core.password.controller;

import java.util.ArrayList;

import org.hibernate.Transaction;

import com.zyos.core.common.controller.ErrorNotificacion;
import com.zyos.core.common.controller.ZyosController;
import com.zyos.core.login.model.ZyosLoginDAO;
import com.zyos.core.mail.io.mn.model.CutOffTimeDAO;
import com.zyos.core.mail.io.mn.model.Notification;
import com.zyos.core.mail.io.mn.model.NotificationDAO;

public class ExternalUserController extends ZyosController {

	/**
	 * Method for validating and saving the new password
	 * 
	 * @param oldPassword
	 * @param newPassword
	 * @param idZU
	 * @param userChange
	 * @return true if the password was changed
	 */
	public boolean validateOldPassword(String oldPassword, String newPassword,
			Long idZU, String userChange) {
		ZyosLoginDAO loginDAO = new ZyosLoginDAO();
		try {
			boolean returnValue = loginDAO.validateOldPassword(oldPassword,
					newPassword, idZU, userChange);
			return returnValue;
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
			return false;
		} finally {
			loginDAO.getSession().close();
			loginDAO = null;
		}
	}

	/**
	 * Method that load all notifications by user
	 * 
	 * @param idZU
	 * @return
	 */
	public ArrayList<Long> loadCutOffTimeByUser(Long idZU) {
		CutOffTimeDAO dao = new CutOffTimeDAO();
		try {
			return dao.getListCutOffTimeByUser(idZU);
		} catch (Exception e) {
			return null;
		} finally {
			dao.getSession().close();
			dao = null;
		}
	}

	/**
	 * 
	 * @param idZU
	 * @param idCutOffTime
	 * @param idNotificationType
	 */
	public void saveNotificationByUser(Long idZU, Long idCutOffTime,
			Long idNotificationType, String user) {
		NotificationDAO dao = new NotificationDAO();
		Notification n = new Notification(idCutOffTime, idNotificationType,
				idZU, user, true);
		try {
			Transaction tx = dao.getSession().beginTransaction();
			dao.save(n);
			tx.commit();
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, "system");
		} finally {
			dao.getSession().close();
			dao = null;
			n = null;
		}
	}

	/**
	 * Method that remove the not selected notification
	 * 
	 * @param idZU
	 * @param idCutOffTime
	 * @throws Exception
	 */
	public void removeNotificationByUser(Long idZU, Long idCutOffTime) {
		NotificationDAO dao = new NotificationDAO();
		try {
			dao.removeNotificationByUser(idZU, idCutOffTime);
			Transaction tx = dao.getSession().beginTransaction();
			tx.commit();
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, "system");
		} finally {
			dao.getSession().close();
			dao = null;
		}
	}

}
