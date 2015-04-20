package com.zyos.core.common.controller;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.zyos.core.common.util.ManageDate;
import com.zyos.session.ZyosSession;
import com.zyos.session.common.User;

/**
 * Class that handle the new sessions and destroyed sessions because it was the
 * expires
 * 
 * @author Garzón
 * 
 */
public class SessionHandler implements HttpSessionListener {

	public void sessionCreated(HttpSessionEvent sc) {
		System.out.println("INFO: " + ManageDate.getCurrentTimestamp()
				+ " Starting session with id " + sc.getSession().getId());
	}

	public void sessionDestroyed(HttpSessionEvent sd) {
		try {
			Object u = sd.getSession().getAttribute("user");
			if (u != null) {
				System.out.println("INFO: " + ManageDate.getCurrentTimestamp()
						+ " " + ((User) u).getFullName()
						+ " Deleting session with id "
						+ sd.getSession().getId());
			}
			ZyosSession.delLoginSessionId(sd.getSession().getId(), null);
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}
	}
}
