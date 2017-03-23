package mJamsek;

import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.log.NullLogChute;

public class SendMailVelocity{

	//zrendera template z podanimi vrednostmi
	public static StringWriter getVelocityEngine(String templateName, VelocityContext velocityContext){
		VelocityEngine velocityEngine = new VelocityEngine();
        velocityEngine.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        velocityEngine.setProperty(Velocity.RUNTIME_LOG_LOGSYSTEM_CLASS, NullLogChute.class.getName());
        velocityEngine.setProperty("classpath.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        velocityEngine.setProperty("input.encoding", "UTF-8");
        velocityEngine.setProperty("output.encoding", "UTF-8");
        StringWriter stringWriter = null;
        velocityEngine.init();
        Template template = velocityEngine.getTemplate(templateName, "UTF-8");

        stringWriter = new StringWriter();
        template.merge(velocityContext, stringWriter);

        return stringWriter;
	}
	
	
	//poslje mail - duuh
	public static String sendMail(String userMailId, String subject, String templateName, VelocityContext podatki){
		
		//mail settings
		final String username = "playernobody@gmail.com";
		final String password = "jljyjoaipsjojvbi";
		
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		
		//gmail seja
		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});
		
		try{
			StringWriter sw = getVelocityEngine(templateName, podatki);
			Message message = new MimeMessage(session);
            message.setContent(sw.toString() + "", "text/html; charset=UTF-8");
            message.setFrom(new InternetAddress("me@gmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(userMailId));
            message.setSubject(subject);
            Transport.send(message);
            
            //logging data
            DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss"); 
            Date date = new Date();
            
			System.out.println("E-mail sent at " + dateFormat.format(date));
			return JsonUtil.toJson(true);
		}catch (MessagingException e) {
			System.err.println(e);
			return JsonUtil.toJson(false);
            //throw new RuntimeException(e);
        }
	}
	
}


