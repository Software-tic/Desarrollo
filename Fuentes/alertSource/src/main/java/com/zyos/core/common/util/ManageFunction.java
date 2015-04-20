package com.zyos.core.common.util;

import java.util.List;

public class ManageFunction {
	/**
	 * Converter to arrayList to String []
	 * 
	 * @param array
	 * @return
	 * @throws Exception 
	 */
	public static String[] getArrayEmails(List<String> array) throws Exception {
		try {
			String[] temp = new String[array.size()];
			for (int i = 0; i < array.size(); i++) {
				temp[i] = array.get(i);
			}
			return temp;
		} catch (Exception e) {
			throw e;
		}
	}
}
