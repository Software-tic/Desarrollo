package com.zyos.core.mail.io.mn.aws;

import java.text.MessageFormat;

import com.zyos.core.common.controller.ErrorNotificacion;
import com.zyos.core.common.list.BeanList;
import com.zyos.core.mail.io.mn.model.EmailTemplate;

public class MailGeneratorFunction {

	/**
	 * Method for getting the email template
	 * 
	 * @param idTypeTemplate
	 * @return
	 */
	public static EmailTemplate getEmailTemplate(Long idTypeTemplate,
			Long idEnteprise) {
		try {
			for (EmailTemplate et : BeanList.getControllerEnterpriseList(
					idEnteprise).getEmailTemplateList())
				if (et.getId().equals(idTypeTemplate))
					return et;

		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, "system");
		}
		return null;
	}
	

	/**
	 * Method for creating a generic message about the different parameters that
	 * exist inside a text
	 * 
	 * @param body
	 * @param googleAnalytics
	 * @param params
	 * @return
	 */
	public static String createGenericMessage(String body,
			String googleAnalytics, Object... params) {
		try {
			String b = body;
			b = addGoogleAnalytics(googleAnalytics, b);
			b = replaceParameterWithFunction(b, params);

			// b = replaceMessageWithParameters(b, params);
			return b;
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, "system");
			return null;
		}
	}

	/**
	 * method for searching and replacing the parameters when makes a mistake
	 * 
	 * @param b
	 * @param params
	 * @return
	 */
	public static String replaceParameterWithFunction(String b,
			Object... params) {
		int i = 0;
		for (Object object : params) {
			if (object != null && !object.toString().isEmpty())
				b = replaceMessage(b, "{" + i + "}", object.toString());
			i++;
		}
		return b;
	}

	/**
	 * Method for adding the google analytics code
	 * 
	 * @param googleAnalytics
	 * @param b
	 * @return
	 */
	public static String addGoogleAnalytics(String googleAnalytics, String b) {
		try {
			if (googleAnalytics != null && !googleAnalytics.isEmpty())
				b = replaceMessage(b, "<body>",
						getAnalyticsHTMLCode(googleAnalytics));
			return b;
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, "system");
		}
		return b;
	}

	private static String getAnalyticsHTMLCode(String analyticsCode) {
		// TODO implement
		return "";
	}

	/**
	 * Method that locate the token and replace it
	 * 
	 * @param message
	 * @param token
	 * @param value
	 * @return
	 */
	public static String replaceMessage(String message, String token,
			String value) {
		try {
			StringBuilder sb = new StringBuilder();
			int index = message.indexOf(token);
			if (index < 0) {
				return message;
			} else {
				sb.append(message.substring(0, index));
				sb.append(value);
				sb.append(message.substring(token.length() + index));
				return replaceMessage(sb.toString(), token, value);
			}
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, "system");
			return message;
		}
	}

	/**
	 * Method that replace the parameters for a message
	 * 
	 * "The disk \"{1}\" contains {0} file(s).");
	 * 
	 * @param body
	 * @param params
	 *            {0},{1}
	 * @return
	 */
	public static String replaceMessageWithParameters(String body,
			Object... params) {
		try {
			return MessageFormat.format(body, params);
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, "system");
			return replaceParameterWithFunction(body, params);
		}
	}

}
