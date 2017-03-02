package mJamsek;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
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

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

public class SendMailWithVelocityTemplate {

	public static void poslji() {

		String to = "miha_jamsek@windowslive.com";
		String from = "me@gmail.com";
		final Properties prop = System.getProperties();

		try {
			prop.load(new FileInputStream(new File("src/main/java/mjamsek/mail-settings.properties")));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		Session session = Session.getDefaultInstance(prop,
				new Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(
						prop.getProperty("mail.user"), prop.getProperty("mail.passwd"));
					}
		});

		try{
			// Creating default MIME message object
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			message.setSubject("Test mail through simple java API");

			BodyPart body = new MimeBodyPart();

			// velocity stuff.

			// Initialize velocity
			VelocityEngine ve = new VelocityEngine();
			ve.init();

			/* next, get the Template */
			Template t = ve.getTemplate( "src/main/resources/templates/email-template.vtl" );
			/* create a context and add data */
			VelocityContext context = new VelocityContext();
			context.put("fName", "Kunal");
			context.put("lName", "kumar");
			context.put("proprietor", "Owner");
			/* now render the template into a StringWriter */
			StringWriter out = new StringWriter();
			t.merge( context, out );

			// velocity stuff ends.

			body.setContent(out.toString(), "text/html");

			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(body);

			body = new MimeBodyPart();

			String filename = "mail-attachement-template.txt";
			DataSource source = new FileDataSource(filename);
			body.setDataHandler(new DataHandler(source));
			body.setFileName("attachement");
			multipart.addBodyPart(body);
			message.setContent(multipart, "text/html");

			// Send mail
			Transport.send(message);
			System.out.println("Mail sent ");

		}catch(Exception ex){
			ex.printStackTrace();
		}

	}
}