package com.zyos.core.mail.io.mn.aws;

import com.zyos.core.common.controller.ErrorNotificacion;


public class Test {

	public Test() {
		try {
			new SMTPEmail().sendProcessEmail(null, "prueba", "test test test",
					"ogarzon@zyos.co");
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new Test();
	}

}
