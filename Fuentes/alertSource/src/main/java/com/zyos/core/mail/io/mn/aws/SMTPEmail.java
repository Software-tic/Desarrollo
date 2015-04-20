package com.zyos.core.mail.io.mn.aws;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.zyos.core.common.controller.ErrorNotificacion;
import com.zyos.core.common.list.BeanList;

public class SMTPEmail {

	private static final String SMTP_HOST_NAME = BeanList.properties .getProperty("mail.smtp.host");
	private static final String SMTP_AUTH_USER = BeanList.properties .getProperty("mail.smtp.user");
	private static final String SMTP_AUTH_PWD = BeanList.properties .getProperty("mail.password");

	public static void main(String[] args) throws Exception {
		new SMTPEmail().sendProcessEmail(SMTP_AUTH_USER, "prueba",
				"test test test", "og@zyos.co");
	}

	private Session mailSession;
	private List<MimeMessage> messageList;

	public SMTPEmail() {
		Properties props = new Properties();
		props.put("mail.transport.protocol", BeanList.properties.getProperty("mail.transport.protocol"));
		props.put("mail.smtp.host", SMTP_HOST_NAME);
		props.put("mail.smtp.port", BeanList.properties.getProperty("mail.smtp.port"));
		props.put("mail.smtp.auth", BeanList.properties.getProperty("mail.smtp.auth"));
		props.put("mail.smtp.starttls.enable", BeanList.properties.getProperty("mail.smtp.starttls.enable"));

		Authenticator auth = new SMTPAuthenticator();
		mailSession = Session.getDefaultInstance(props, auth);
	}

	public void sendProcessEmail(String from, String subject, String HTMLBody,
			String... to) throws Exception {
		try {
			if (from == null)
				from = BeanList.properties.getProperty("mail.smtp.user");
			if (to != null && from != null && subject != null
					&& HTMLBody != null && to.length > 0) {
				// uncomment for debugging infos to stdout
				mailSession.setDebug(true);
				Transport transport = mailSession.getTransport();

				MimeMessage message = new MimeMessage(mailSession);

				Multipart multipart = new MimeMultipart("alternative");

				BodyPart part1 = new MimeBodyPart();
				part1.setContent(HTMLBody, "text/html");

				multipart.addBodyPart(part1);

				message.setContent(multipart);
				message.setFrom(new InternetAddress(from));
				message.setSubject(subject);
				for (String e : to) {
					if (e != null && !e.isEmpty()) {
						message.addRecipient(Message.RecipientType.BCC,
								new InternetAddress(e));
					}
				}

				transport.connect();
				transport.sendMessage(message, message.getAllRecipients());
				transport.close();
			} else {
				System.out.println("INFO - email list is empty");
			}
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
			throw e;
		}
	}

	public void addMessage(String from, String subject, String body,
			String... to) {
		try {
			if (mailSession != null && to != null && to.length > 0) {
				ArrayList<String> toA = new ArrayList<String>();
				for (String email : to) {
					if (email != null && email.contains(",".subSequence(0, 1))) {
						StringTokenizer token = new StringTokenizer(email, ",");
						while (token.hasMoreElements()) {
							String e = token.nextToken().trim();
							if (e != null && !e.isEmpty())
								toA.add(e);
						}
					} else if (email != null && !email.isEmpty()) {
						toA.add(email.trim());
					}
				}

				MimeMessage message = new MimeMessage(mailSession);

				Multipart multipart = new MimeMultipart("alternative");

				BodyPart part1 = new MimeBodyPart();
				part1.setContent(body, "text/html");

				multipart.addBodyPart(part1);

				message.setContent(multipart);
				message.setFrom(new InternetAddress(from));
				message.setSubject(subject);
				for (String e : toA) {
					message.addRecipient(Message.RecipientType.BCC,
							new InternetAddress(e));
				}
				getMessageList().add(message);
			}
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}
	}

	public void sendMultipleEmail() throws Exception {
		// uncomment for debugging infos to stdout
		try {
			if (messageList != null && !messageList.isEmpty()) {
				mailSession.setDebug(true);
				Transport transport = mailSession.getTransport();
				transport.connect();
				for (Message message : messageList) {
					try {
						transport.sendMessage(message,
								message.getAllRecipients());
						Thread.sleep(300);
					} catch (Exception e) {
						Thread.sleep(10000);
						ErrorNotificacion.handleErrorMailNotification(e, this);
					}
				}
				transport.close();
			}
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
			throw e;
		}
	}

	private class SMTPAuthenticator extends javax.mail.Authenticator {
		public PasswordAuthentication getPasswordAuthentication() {
			String username = SMTP_AUTH_USER;
			String password = SMTP_AUTH_PWD;
			return new PasswordAuthentication(username, password);
		}
	}

	public List<MimeMessage> getMessageList() {
		if (messageList == null) {
			messageList = new ArrayList<MimeMessage>();
		}
		return messageList;
	}

	public void setMessageList(List<MimeMessage> messageList) {
		this.messageList = messageList;
	}

}
