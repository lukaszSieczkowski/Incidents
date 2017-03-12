package pl.incidents.utils;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import pl.incidents.model.Incident;
import pl.incidents.model.User;

public class Mail {

	public void sendMail(String userEmail, String mailContent, String subject) {

		Properties props = new Properties();

		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");

		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("incidents.rep@gmail.com", "Incidents!1");
			}
		});
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("incidents.rep@gmail.com"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(userEmail));
			message.setSubject(subject);
			message.setText(mailContent);
			Transport.send(message);

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}

	public String prepareContentNewUser(User user) {
		String mailContent = "Dear User\n\n" + "Your account in Incidents Reportin Tool was created successfuly\n\n"
				+ "Your login: " + user.getEmail() + ",\n\n" + "Your password: " + user.getPassword() + "\n\n"
				+ "When you login to your account, you can change your password\n\n" + "Kind Regards\n\n"
				+ "Incident Reporting Tool TEAM";
		return mailContent;
	}
	
	public String prepareContentNewIncidentReported(User user,Incident incident) {
		String mailContent = "Dear Administrator\n\n" 
					+ "User " + user.getName()+" "+ user.getSurname()+ " report incident Id:"+ incident.getId()+"\n\n"
					+"Incident was happend: " + incident.getIncidentDate().toString().substring(0, 10)
					+"in location : " + incident.getLocation()+".\n\n";
		return mailContent;
	}
	

	public String prepareContentNewPassword(User user) {
		String mailContent = "Dear User\n\n" + "Your password in Incidents Reportin Tool was changed successfuly\n\n"
				+ "Your login: " + user.getEmail() + ",\n\n" + "Your password: " + user.getPassword() + "\n\n"
				+ "When you login to your account, you can change your password\n\n" + "Kind Regards\n\n"
				+ "Incident Reporting Tool TEAM";
		return mailContent;
	}

	public String prepareContentUserLocked() {
		String mailContent = "Dear User\n\n" + "Your account in Incidents Reportin Tool was locked by administrator\n\n"
					+"If you want use our system in futute, please contact with administrator.\n\n"
					+ "Incident Reporting Tool TEAM";
		return mailContent;
	}
	public String prepareSubjectUserLocked() {
		String subject = "Locked account in Incident Repporting Tool";
		return subject;
	}

	public String prepareSubjectNewUser() {
		String subject = "New Account in Incident Repporting Tool";
		return subject;
	}

	public String prepareSubjectNewPassword() {
		String subject = "Password Reset in Incident Repporting Tool";
		return subject;
	}
	public String prepareSubjectNewIncident() {
		String subject = "New Incident Reported in Incident Repporting Tool";
		return subject;
	}
}
