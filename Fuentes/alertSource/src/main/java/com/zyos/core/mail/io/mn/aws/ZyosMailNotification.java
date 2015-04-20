package com.zyos.core.mail.io.mn.aws;

import java.util.ArrayList;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.zyos.core.common.controller.ErrorNotificacion;
import com.zyos.core.common.util.resource.ManageProperties;

public class ZyosMailNotification extends Thread {

	private Session session;
	private Transport transport;
	private Properties properties;
	private MimeMessage message;
	private boolean keepLogin = false;
	private String bodyMail;
	private ArrayList<MimeBodyPart> mimeBodyPart;
	private String[] to;
	private String subject;

	/**
	 * Class what manage the sending mails.
	 * 
	 * By send a mail call the sendMail method
	 * 
	 * @param propertiesURL
	 * 
	 *            The properties on the file is: 1) mail.smtp.host=(smtp origin)
	 *            mail.smtp.starttls.enable=(true/false) 2) mail.smtp.port=(smtp
	 *            port) mail.smtp.user=(mail user ...@...) 3)
	 *            mail.smtp.auth=(Need authentication true/false) 4)
	 *            protocol=(protocol that use smtp) 5) password=(password
	 *            ********) 6) subject=(subject of mail)
	 */
	public ZyosMailNotification(Properties properties, boolean keepLogin) {
		try {
			this.keepLogin = keepLogin;
			this.properties = properties;
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}
	}

	/**
	 * Method for sending asynchronized mail.
	 * 
	 * ZyosMailNotification t = new ZyosMailNotification(
	 * ResourceBundle.getBundle("com.zyos.mail.resources.manageNotification"),
	 * true, "test", null, "scarsix06d@gmail.com", "ogarzonm@webzyos.com");
	 * 
	 * after implements the constructor is necessary call the start method an
	 * ready, the asynchronized mail is send.
	 * 
	 * @param propertiesURL
	 * @param keepLogin
	 * @param bodyMail
	 * @param mimeBodyPart
	 * @param to
	 */
	public ZyosMailNotification(Properties properties, String bodyMail,
			ArrayList<MimeBodyPart> mimeBodyPart, String... to) {
		try {
			this.keepLogin = false;
			this.bodyMail = bodyMail;
			this.to = to;
			this.mimeBodyPart = mimeBodyPart;
			this.properties = properties;
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}
	}

	/**
	 * Thread method run
	 */
	public synchronized void run() {
		try {
			if (properties == null) {
				getResourceProperties();
			}
			createSessionAndTransport(properties.getProperty("protocol"));
			connectTransport(properties.getProperty("mail.smtp.user"),
					properties.getProperty("password"));
			if (!keepLogin) {
				sendMail(bodyMail, mimeBodyPart, to);
			}
			bodyMail = null;
			mimeBodyPart = null;
			to = null;
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}
	}

	/**
	 * Method that create the session y transport
	 * 
	 * @throws NoSuchProviderException
	 */
	public void createSessionAndTransport(String protocol)
			throws NoSuchProviderException {
		session = Session.getDefaultInstance(properties);
		transport = session.getTransport(protocol);
	}

	/**
	 * Method by send a mail.
	 * 
	 * @param String
	 *            bodyMail is a text of mail
	 * @param String
	 *            from is a destiny mail
	 * @param ArrayList
	 *            <MimeBodyPart> mimeBodyPart list with the mimeBodyPart objects
	 * @throws Exception
	 *             handle to exception
	 */
	public synchronized void sendMail(String bodyMail,
			ArrayList<MimeBodyPart> mimeBodyPart, String... to)
			throws Exception {
		try {
			message = new MimeMessage(session);
			for (String email : to) {
				if (email.contains(",".subSequence(0, 1))) {
					StringTokenizer token = new StringTokenizer(email, ",");
					while (token.hasMoreElements())
						addEmail(token.nextToken().trim());
				} else {
					addEmail(email.trim());
				}
			}

			message.setSubject(subject, "utf-8");

			Multipart multipart = new MimeMultipart("related");

			String file = bodyMail;

			BodyPart text = new MimeBodyPart();
			text.setContent(file, "text/html; charset=utf-8");

			multipart.addBodyPart(text);

			if (mimeBodyPart != null)
				for (MimeBodyPart imageBox : mimeBodyPart)
					multipart.addBodyPart(imageBox);

			message.setHeader("Content-Type", "text/html; charset=utf-8");
			message.setHeader("Content-Transfer-Encoding", "quoted-printable");
			message.setText(file, "utf-8");
			message.setContent(multipart);

		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		} finally {
			if (getTransport() != null && message != null) {
				transport.sendMessage(message, message.getAllRecipients());
				if (!keepLogin) {
					closeTransport();
					subject = null;
				}
				message = null;
			} else {

			}
		}
	}

	private void addEmail(String email) {
		try {
			if (message != null && email != null && !email.isEmpty()) {
				message.addRecipient(Message.RecipientType.TO,
						new InternetAddress(email));
			}
		} catch (Exception e) {
			System.out.println("FAIL ADD EMAIL " + email);
		}
	}

	/**
	 * Method to connect transport session with mail
	 * 
	 * @throws Exception
	 */
	public void connectTransport(String emailUser, String emailPassword)
			throws Exception {
		try {
			if (transport != null && !transport.isConnected())
				transport.connect(emailUser, emailPassword);
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}
	}

	public void closeTransport() throws Exception {
		if (transport != null) {
			transport.close();
		}
	}

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	public Transport getTransport() throws Exception {
		if (transport == null && properties != null) {
			connectTransport(properties.getProperty("mail.smtp.user"),
					properties.getProperty("password"));
		}
		return transport;
	}

	public void setTransport(Transport transport) {
		this.transport = transport;
	}

	public boolean isKeepLogin() {
		return keepLogin;
	}

	public void setKeepLogin(boolean keepLogin) {
		this.keepLogin = keepLogin;
	}

	private Properties getResourceProperties() {
		try {
			if (properties == null) {
				System.out.println("Getting Zyos/Alert general properties");
				properties = new Properties();
				properties.load(ManageProperties.class.getResourceAsStream("properties.properties"));
			}
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}
		return properties;
	}
}
