package com.zyos.core.common.util.resource;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.Properties;

import com.zyos.core.common.controller.ErrorNotificacion;

/**
 * Class for getting messages and systems properties
 * 
 * @author Zyos-Home
 **/
public class ManageProperties {
	private  Properties bundle;
	private  Properties properties;

	public ManageProperties() {
		getResourceBundle();
		getResourceProperties();
	}

	/**
	 * Method for getting message about the key
	 * 
	 * @param key
	 * @return String that contains the text found
	 */
	public String getMessage(String key) {
		String value = null;
		try {
			value = getResourceBundle().getProperty(key);
		} catch (MissingResourceException e) {
			System.out.println("java.util.MissingResourceException: Couldn't find value for: "
							+ key);
		}
		if (value == null) {
			value = "Could not find resource: " + key + "  ";
		}
		return value;
	}

	/**
	 * method for getting a property about the key
	 * 
	 * @param key
	 * @return
	 */
	public String getProperty(String key) {
		String value = null;
		try {
			value = getResourceProperties().getProperty(key);
		} catch (MissingResourceException e) {
			System.out
					.println("java.util.MissingResourceException: Couldn't find value for: "
							+ key);
		}
		if (value == null) {
			value = "Could not find resource: " + key + "  ";
		}
		return value;
	}

	private Properties getResourceBundle() {
		try {
			if (bundle == null) {
				System.out.println("Getting alert general messages");
				bundle = new Properties();
				bundle.load(ManageProperties.class
						.getResourceAsStream("messages.properties"));
			}
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, "properties");
		}
		return bundle;
	}

	private Properties getResourceProperties() {
		try {
			if (properties == null) {
				properties = new Properties();
				if (!System.getProperty("os.name").toLowerCase()
						.startsWith("win")) {
					System.out
							.println("Getting alert Server general properties");
					properties
							.load(ManageProperties.class
									.getResourceAsStream("propertiesServer.properties"));
				} else {
					System.out
							.println("Getting alert Locality general properties");
					properties.load(ManageProperties.class
							.getResourceAsStream("properties.properties"));
				}
			}
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, "properties");
		}
		return properties;
	}

	public  Properties getBundle() {
		return bundle;
	}

	public Properties getProperties() {
		return properties;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	public void setBundle(Properties bundle) {
		this.bundle = bundle;
	}

	/**
	 * method for getting a format message when the message has got parameters
	 * 
	 * Example.
	 * 
	 * @param pattern
	 *            - test {0} for show the way to {1} the properties format
	 *            message
	 * @param key
	 *            - "1", "uses"
	 * @return test 1 for show the way to uses the properties format message
	 */
	public  String formatMessage(String pattern, Object... key) {
		return MessageFormat.format(pattern, key);
	}
}
