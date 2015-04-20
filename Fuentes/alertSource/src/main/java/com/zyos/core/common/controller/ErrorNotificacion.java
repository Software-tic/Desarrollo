package com.zyos.core.common.controller;

import org.hibernate.HibernateException;

import com.zyos.core.common.list.BeanList;
import com.zyos.core.connection.HibernateSessionFactory;
import com.zyos.core.mail.io.mn.aws.SMTPEmail;

public class ErrorNotificacion {

	/**
	 * Method that send a mail notification after a exception
	 * 
	 * @param e
	 *            Exception
	 */
	public static void handleErrorMailNotification(Exception e2, Object classObject) {
		try {
			String nameUser = classObject instanceof ZyosBackingBean ? ((ZyosBackingBean)classObject).getUserSession().getDocumentNumber() 
					: (classObject instanceof String ? classObject.toString() : classObject.getClass().getName()); 
			
			System.out.println("WARN: exception for: "+ nameUser);
			e2.printStackTrace();
			if(e2 instanceof HibernateException){
				HibernateSessionFactory.getOracleSession().beginTransaction().rollback();
				HibernateSessionFactory.getOracleSession().clear();
				HibernateSessionFactory.getOracleSession().flush();
				HibernateSessionFactory.getOracleSession().cancelQuery();
				HibernateSessionFactory.getOracleSession().close();
			}

			String s = BeanList.properties.getProperty("error.mail.sendEmail");
			if (s != null && s.equals("true")) {
				SMTPEmail smtp = new SMTPEmail();
				StringBuilder m = new StringBuilder();
				for (StackTraceElement st : e2.getStackTrace()) {
					m.append(st.toString());
					m.append("</br>");
				}
				smtp.sendProcessEmail(BeanList.properties.getProperty("mail.smtp.user"),
						"Error Alert " + nameUser, m.toString(), "software.tic@ustatunja.edu.co");//"ogarzonm@zyos.co", "pfuertesc@zyos.co", "jhernandez@zyos.co" );
			}
		} catch (Exception e3) {
			e3.printStackTrace();
			System.out.println("ERROR: FAIL send error mail ");
		}
	}

	/**
	 * Method that validate if the hibernate session was closed after happened a
	 * throw (Call the function when you don't have sure that the session is
	 * closed)
	 */
	public static void validateHibernateCloseSession() {
		try {
			if (HibernateSessionFactory.getOracleSession() != null && HibernateSessionFactory.getOracleSession().isOpen()) {
				System.out.println("INFO: Hibernate session was oppened after the throw. Now is closed");
				HibernateSessionFactory.getOracleSession().close();
			}
		} catch (Exception e) {
			System.out.println("ERROR: FAIL Close hibernate session");
			ErrorNotificacion.handleErrorMailNotification(e, "validateHibernateCloseSession");
		}
	}
}
