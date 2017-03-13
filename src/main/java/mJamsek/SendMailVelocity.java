package mJamsek;

import java.io.StringWriter;
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

	public static StringWriter getVelocityEngine(String templateName, VelocityContext velocityContext){
		VelocityEngine velocityEngine = new VelocityEngine();
        velocityEngine.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        velocityEngine.setProperty(Velocity.RUNTIME_LOG_LOGSYSTEM_CLASS, NullLogChute.class.getName());
        velocityEngine.setProperty("classpath.resource.loader.class",
                "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        StringWriter stringWriter = null;
        velocityEngine.init();
        Template template = velocityEngine.getTemplate(templateName);

        stringWriter = new StringWriter();
        template.merge(velocityContext, stringWriter);

        return stringWriter;
	}
	
	
	
	public static void sendMail(String userMailId, String url){
		final String username = "playernobody@gmail.com";
		final String password = "jljyjoaipsjojvbi";
		
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		
		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});
		
		try{
			VelocityContext velContext = new VelocityContext();
			velContext.put("URL", url);
			StringWriter sw = getVelocityEngine("templates/email-template.vtl", velContext);
			Message message = new MimeMessage(session);
            message.setContent(sw.toString() + "", "text/html");
            message.setFrom(new InternetAddress("me@gmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(userMailId));
            message.setSubject("Subject Header");
            Transport.send(message);
			
		}catch (MessagingException e) {
            throw new RuntimeException(e);
        }
	}
	
}


