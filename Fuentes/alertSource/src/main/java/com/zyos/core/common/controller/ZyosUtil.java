package com.zyos.core.common.controller;

import javax.faces.context.FacesContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

public class ZyosUtil {

	/**
	 * Method that makes a copy of one object
	 * 
	 * @param to
	 * @param from
	 */
	protected void copy(java.lang.Object to, java.lang.Object from) {
		try {
			BeanUtils.copyProperties(to, from);
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}
	}

	/**
	 * Method that locate a cookie about the name
	 * 
	 * @param cookieName
	 * @return value cookie
	 */
	public static String getCookieByName(String cookieName) {
		HttpServletRequest httpServletRequest = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();
		Cookie[] cookies = httpServletRequest.getCookies();
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				if (cookies[i].getName().equalsIgnoreCase(cookieName)) {
					return cookies[i].getValue();
				}
			}
		}
		return null;
	}

	/**
	 * Method that add a new cookie
	 * 
	 * @param name
	 * @return
	 */
	public static void addCookie(String name, String value, int expire,
			String comment, boolean secure) {
		try {
			Cookie cookie = null;
			if (cookie == null) {
				cookie = new Cookie(name, value);
				cookie.setMaxAge(expire * 60 * 60 * 24);
			}

			((HttpServletResponse) (FacesContext.getCurrentInstance()
					.getExternalContext()).getResponse()).addCookie(cookie);

		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, "system");
		}
	}
}