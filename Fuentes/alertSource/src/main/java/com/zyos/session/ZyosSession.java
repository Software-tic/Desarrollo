package com.zyos.session;

import java.util.ArrayList;
import java.util.List;

import com.zyos.core.common.controller.ErrorNotificacion;
import com.zyos.session.common.User;

/**
 * Zyos Session Class
 * 
 * @author Oscar Garzon
 * 
 */
public class ZyosSession {

	/**
	 * list that contains all user that are login on the application
	 */
	private static ArrayList<User> listUserZyosSession = new ArrayList<User>();

	/**
	 * Default constructor
	 */
	private ZyosSession() {
	}

	/**
	 * Method synchronized that get the user of the general list as the id
	 * 
	 * @param idUser
	 * @return
	 */
	public static synchronized List<User> getLoginUser(String idUser) {
		try {
			List<User> login = new ArrayList<User>();
			for (User user : listUserZyosSession) {
				if (user.getId().toString().equals(idUser)) {
					// already exist
					login.add(user);
				}
			}
			if (!login.isEmpty()) {
				return login;
			}
			return null;
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, "getLoginUser");
			return null;
		}
	}

	/**
	 * Method synchronized that add the new user logins on the application
	 * 
	 * @param user
	 */
	public static synchronized void addLoginUser(User user) {
		try {
			if (user != null)
				listUserZyosSession.add(user);
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, "addLoginUser");
		}
	}

	/**
	 * Method synchronized that delete the user that do it logout on the
	 * application
	 * 
	 * @param u
	 */
	public static synchronized void delLoginSessionId(String idSession,
			String idUser) {
		try {
			int element = -1;
			if (idSession != null || idUser != null) {
				for (int i = 0; i < listUserZyosSession.size(); i++) {
					User user = listUserZyosSession.get(i);
					if (user.getId().toString().equals(idUser)
							|| user.getIdSession().equals(idSession)) {
						// already exist
						element = i;
						break;
					}
				}
				if (element > -1)
					listUserZyosSession.remove(element);
			}
		} catch (Exception e) {
			System.out
					.println("ERROR: Zyos Session - java.util.ConcurrentModificationException ");
		}
	}

	/**
	 * Method synchronized that get the user about the string id session
	 * 
	 * @param idSession
	 * @return
	 */
	public static synchronized User getUserBySession(String idSession) {
		try {
			for (User user : listUserZyosSession) {
				if (user.getIdSession().equals(idSession)) {
					return user;
				}
			}
			return null;
		} catch (Exception e) {
			System.out
					.println("ERROR: Zyos Session - java exception impossible get user");
			return null;
		}
	}
}