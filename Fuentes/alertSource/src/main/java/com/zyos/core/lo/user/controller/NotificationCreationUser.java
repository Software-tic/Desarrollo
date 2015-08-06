package com.zyos.core.lo.user.controller;


public class NotificationCreationUser extends Thread {

	private String userLogin;
	private String password;
	private String userName;
	private String url;
	private String email;

	public NotificationCreationUser(String userLogin, String password,
			String userName, String email, String url) {
		this.userLogin = userLogin;
		this.password = password;
		this.userName = userName;
		this.url = url;
		this.email = email;
	}

	@Override
	public void run() {
		// ResourceBundle properties = ResourceBundle
		// .getBundle("com.zyos.pring.resources.manageCreationUser");
		//
		// String body = properties.getString("mailBody");
		// body.replaceFirst("XXXXX", userName);
		// body.replaceFirst("CCCCC", password);
		// body.replaceFirst("UUUUU", userLogin);
		// body.replaceFirst("URL", url);

		// ZyosMailNotification mailCreationUser = new ZyosMailNotification(
		// properties, body, null, email);
		// mailCreationUser.start();
	}
}
