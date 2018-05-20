package es.uji.ei1027.sape.domain;

import java.util.List;
import java.util.Properties;
/*
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
*/
public class Mail {
/*
	private Properties props;
	private Session session;
	private MimeMessage message;
	private Transport t;
	public Mail() {
		props = new Properties();
		props.setProperty("mail.smtp.host", "smtp.gmail.com");
		props.setProperty("mail.smtp.starttls.enable", "true");
		props.setProperty("mail.smtp.port", "587");
		props.setProperty("mail.smtp.user", "ei102716ggv@gmail.com");
		props.setProperty("mail.smtp.auth", "true");
		session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("ei102716ggv@gmail.com", "EI102716_ggv");
				
			}
		});
		session.setDebug(true);
	}
	
	public void sendMessage(String subject, String msg, List<String> dest) throws AddressException, MessagingException {
		message = this.createMessage(subject, msg, dest);
		this.sendMessage(message);
	}
	
	private MimeMessage createMessage(String subject, String msg, List<String> dest) throws AddressException, MessagingException {
		MimeMessage message = new MimeMessage(session);
		message.setFrom(new InternetAddress("ei102716ggv@gmail.com"));
		for(String s : dest)
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(s));
		message.setSubject(subject);
		message.setText(msg);
		return message;
	}
	
	private void sendMessage(MimeMessage message) throws MessagingException {
		t = session.getTransport("smtp");
		t.connect("ei102716ggv@gmail.com", "EI102716_ggv");
		t.sendMessage(message,  message.getAllRecipients());
		t.close();
	}
*/
}
