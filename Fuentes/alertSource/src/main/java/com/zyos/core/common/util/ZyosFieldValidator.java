package com.zyos.core.common.util;

import org.apache.commons.validator.EmailValidator;
import org.apache.commons.validator.routines.CurrencyValidator;
import org.apache.commons.validator.routines.DateValidator;
import org.apache.commons.validator.routines.LongValidator;
import org.apache.commons.validator.routines.PercentValidator;

import com.zyos.core.common.api.IValidationType;
import com.zyos.core.common.controller.ErrorNotificacion;

/**
 * Class for validate fields
 * 
 * @author Zyos-Home
 * 
 */
public class ZyosFieldValidator {

	/**
	 * Method for validating a text about the selected validation type
	 * 
	 * @param value
	 * @param type
	 * @return
	 */
	public static boolean validateField(String value, IValidationType type) {
		try {
			if (type.equals(IValidationType.NUMBER)) {
				return isNumeric(value);
			} else if (type.equals(IValidationType.EMAIL)) {
				return isEmail(value);
			} else if (type.equals(IValidationType.IP)) {
				return false;
			} else if (type.equals(IValidationType.PERCENTAGE)) {
				return isPercent(value);
			} else if (type.equals(IValidationType.TEXT)) {
				return isText(value);
			} else if (type.equals(IValidationType.TIME)) {
				return isTime(value);
			} else if (type.equals(IValidationType.URL)) {
				return isURL(value);
			}
			return false;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Method that validate if a value is a numeric. The method delete
	 * characters like ", ' ."
	 * 
	 * @param value
	 *            string with the value
	 * @param user
	 *            name user request
	 * @return true if is numeric
	 */
	public static boolean isNumeric(String value) {
		try {
			if (value != null && !value.isEmpty()) {
				value = value.replace(",", "");
				value = value.replace(".", "");
				value = value.replace("'", "");
				if (!LongValidator.getInstance().isValid(value))
					return false;
				return true;
			}
			return false;
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, "validator");
			return false;
		}
	}

	/**
	 * Method that validate if a value is a currency
	 * 
	 * @param value
	 *            string with the value
	 * @param locate
	 *            as the country new Locate("es", "CO") for the Colombia
	 *            currency
	 * @param user
	 *            user that makes the request
	 * @return true if is a currency
	 */
	public static boolean isCurrency(String value, java.util.Locale locate) {
		try {
			value = value.replace(",", "");
			CurrencyValidator cv = new CurrencyValidator(true, true);
			if (cv.validate(value, locate) == null)
				return false;
			return true;
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, "validator");
			return false;
		}
	}

	/**
	 * method that validate a date in YYYY_MM_DD format
	 * 
	 * @param value
	 * @param user
	 * @return true is a valid date
	 */
	public static boolean isDate(String value, String format) {
		try {
			if (!DateValidator.getInstance().isValid(value, format))
				return false;
			return true;
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, "validator");
			return false;
		}
	}

	/**
	 * method that validate a time in HH_MM format
	 * 
	 * @param value
	 * @param user
	 * @return true if is a valid time
	 */
	public static boolean isTime(String value) {
		try {
			if (!DateValidator.getInstance().isValid(value, ManageDate.HH_MM))
				return false;
			return true;
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, "validator");
			return false;
		}
	}

	/**
	 * Method that validate is a value is a email
	 * 
	 * @param value
	 * @param user
	 * @return true if is a valid email
	 */
	public static boolean isEmail(String value) {
		try {
			if (value != null && !value.isEmpty()) {
				if (!EmailValidator.getInstance().isValid(value))
					return false;
				return true;
			}
			return false;
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, "validator");
			return false;
		}
	}

	/**
	 * method that validate is a value is a %
	 * 
	 * @param value
	 * @param user
	 * @return true if is a valid percent
	 */
	public static boolean isPercent(String value) {
		try {
			if (!PercentValidator.getInstance().isValid(value))
				return false;
			return true;
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, "validator");
			return false;
		}
	}

	/**
	 * method that validate a String and validate if isn't numeric validate max
	 * length and min length and validate if the string doesn't contain a
	 * special characters like "[^0-9! ·$%/()=?¿¡ªº@_~#]+"
	 * 
	 * @param value
	 *            value for evaluate
	 * @param minLength
	 *            Long
	 * @param maxLength
	 *            Long
	 * @param validateSpecialCharacters
	 *            boolean if validate or not the special characters
	 * @param user
	 *            user that makes the request
	 * @return true if is a valid string
	 */
	// "[^0-9! ·$%/()=?¿¡ªº@_~#]+"
	public static boolean validateString(String value, int minLength,
			int maxLength, boolean validateSpecialCharacters) {
		try {
			if (value != null && !value.isEmpty()) {
				if (isNumeric(value))
					return false;
				if (value.length() <= minLength && value.length() >= maxLength)
					return false;
				if (!isText(value))
					return false;
				return true;
			}
			return false;
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, "validator");
			return false;
		}
	}

	/**
	 * Method that validate a regular pring about the java api for regular
	 * prings
	 * 
	 * loot at http://download.oracle.com/javase/1.4.2/docs/api/java/util/regex/
	 * Pattern.html in spanish
	 * http://www.programacion.com/articulo/expresiones_regulares_en_java_127
	 * 
	 * @param value
	 * @param regularPring
	 * @param user
	 * @return true if contains validate the regular pring
	 */
	public static boolean validateRegularPring(String value, String regularPring) {
		try {
			return value.matches(regularPring);
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, "validator");
			return false;
		}
	}

	public static boolean isText(String value) {
		try {
			if (value != null) {
				for (int i = 0; i < value.length(); i++)
					if (!Character.isLetter(value.charAt(i))) {
						if (!Character.isWhitespace(value.charAt(i))) {
							return false;
						}
					}
				return true;
			}
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, "validator");
		}
		return false;
	}

	/**
	 * method for validate a URL
	 * 
	 * @param value
	 * @param user
	 * @return return true if is a valid url
	 */
	public static boolean isURL(String value) {
		try {
			if (value != null && !value.isEmpty()) {
				if (value.startsWith("www") || value.startsWith("http:"))
					return true;
			}
			return false;
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, "validator");
			return false;
		}
	}

	public static void main(String[] args) {
	}
}
