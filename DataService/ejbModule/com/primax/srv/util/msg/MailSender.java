package com.primax.srv.util.msg;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import com.primax.jpa.param.CorreoEt;

public class MailSender implements MessageCenter {

	private String _mensaje, _recipiente, _asunto, _de;

	private String _clave;

	private Map<String, byte[]> archivos;

	private CorreoEt config;

	@Override
	public void setMessage(String message) {
		this._mensaje = message;
	}

	@Override
	public void setRecipient(String receiver) {
		this._recipiente = receiver;
	}

	@Override
	public void setSubject(String subject) {
		this._asunto = subject;
	}

	@Override
	public void setFrom(String from) {
		this._de = from;
	}

	@Override
	public void setAttachtment(Map<String, byte[]> filebyte) {
		this.archivos = filebyte;
	}

	@Override
	public void setConfig(CorreoEt config) {
		this.config = config;
	}

	@Override
	public boolean sendMessage() {

		try {
			Session session = obtenerSession(config);
			session.setDebug(true);
			MimeMultipart container = new MimeMultipart();
			MimeBodyPart textBodyPart = new MimeBodyPart();
			textBodyPart.setText(_mensaje, "UTF-8");
			textBodyPart.setHeader("Content-Type", "text/html");
			container.addBodyPart(textBodyPart);
			if (archivos != null && !archivos.isEmpty()) {
				for (Map.Entry<String, byte[]> file : archivos.entrySet()) {
					DataSource ds;
					try {
						ds = new ByteArrayDataSource(new ByteArrayInputStream(file.getValue()), "application/octet-stream");
						MimeBodyPart attachment = new MimeBodyPart();
						attachment.setFileName(file.getKey());
						attachment.setDataHandler(new DataHandler(ds));
						container.addBodyPart(attachment);
					} catch (IOException e) {
						System.err.print("ERROR EN ENVIO DE MAIL, Cause by :" + e.getMessage());
					}
				}
			}

			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(_de));
			message.addRecipients(RecipientType.TO, InternetAddress.parse(_recipiente));
			message.setSubject(_asunto);
			message.setContent(container);

			Transport t = session.getTransport("smtp");
			int port = 25;
			String host = config.getMailHost();
			String user = session.getProperty("mail.smtp.user");
			String password = session.getProperty("clave");
			t.connect(host, port, user, password);
			t.sendMessage(message, message.getAllRecipients());
			t.close();
			return true;
		} catch (MessagingException e) {
			System.err.print("ERROR EN ENVIO MAIL, CAUSED BY : " + e.getMessage());
			return false;
		}
	}

	// Properties props = new Properties();
	// props.put("mail.smtp.host", "10.72.1.71");
	// props.put("mail.smtp.starttls.enable", "false");
	// props.put("mail.smtp.port", "25");
	// props.put("mail.smtp.auth", "true");

	private Session obtenerSession(CorreoEt correo) {
		Properties props = new Properties();
		try {
			props.put("mail.smtp.host", correo.getMailHost());
			props.put("mail.smtp.starttls.enable", correo.getMailTls());
			props.put("mail.smtp.port", correo.getMailPort());
			props.put("mail.smtp.user", correo.getMailSmtpUser());
			props.put("mail.smtp.auth", correo.getMailAuth());
			props.put("mail.smtp.ssl.trust", correo.getMailTrustTls());
			props.put("clave", correo.getMailSmtpPwd());
			props.put("user", correo.getMailFrom());

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :M�todo obtenerSession " + " " + e.getMessage());
		}
		Session session = Session.getDefaultInstance(props);
		return session;
	}

	public static void main(String... arg) {

		MailSender send = new MailSender();
		send.setFrom("CJaraCh@atimasa.com.ec");
		send.setMessage("Test msg");
		send.setSubject("subject");
		send.setRecipient("jeffersonmaji@hotmail.com");
		send.sendMessage();
		System.out.println("Mail enviado");

	}

}
