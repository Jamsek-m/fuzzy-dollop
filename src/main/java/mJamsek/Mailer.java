package mJamsek;

import org.apache.velocity.VelocityContext;
/**
 * @author miha_
 * Class ki je namenjen uporabi posiljanja majla
 * uporablja class SendMailVelocity, ki je prevec kompleksen za uporabo
 *
 */
public class Mailer {
	
	private VelocityContext podatki;
	private String templateName;
	private String prejemnik;
	private String subject;
	
	public Mailer(String receiver, String header, String template, VelocityContext data){
		this.podatki = data;
		this.prejemnik = receiver;
		this.templateName = template;
		this.subject = header;
	}
	
	public String sendMail(){
		return SendMailVelocity.sendMail(this.prejemnik, this.subject, this.templateName, this.podatki);
	}
	
	
	
	
	
}
