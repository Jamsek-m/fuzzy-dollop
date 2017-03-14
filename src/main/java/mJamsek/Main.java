package mJamsek;

import static spark.Spark.*;
import java.util.*;

import org.apache.velocity.VelocityContext;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import spark.*;
import spark.template.velocity.VelocityTemplateEngine;

public class Main {
	public static void main(String[] args) {
		port(8080);
		staticFileLocation("/public");

		get("/", (req, res) -> {
			res.type("text/html"); //tip odgovora - html
			Map<String, Object> model = new HashMap<>(); //hashmap za podatke ki se posredujejo templatu
			return new VelocityTemplateEngine().render(new ModelAndView(model, "templates/index.vtl")); //render template
		});

		get("/data", (req, res) -> {
			res.type("application/json"); //tip odgovora - json
			User u = new User(1, "lalala", "tttt"); //kreira userja
			return JsonUtil.toJson(u); //odgovori z userjem
		});

		post("/data", (req, res) -> {
			res.type("application/json"); //response type - json

			JSONObject json = (JSONObject) new JSONParser().parse(req.body()); //sparsa request da dobi podatke

			int id = Integer.parseInt(json.get("id").toString());
			String ime = json.get("ime").toString();
			String email = json.get("email").toString();

			User u = new User(id, ime, email); //ustvari uerja s temi podatki
			return JsonUtil.toJson(u); //poslje (istega) userja nazaj
		});
		
		get("/send", (req, res) -> {
			res.type("application/json");
			
			VelocityContext podatki = new VelocityContext();
			podatki.put("URL", "http://www.google.com");
			
			Mailer mailer = new Mailer("miha_jamsek@windowslive.com", "Testno sporocilo", "templates/email-template.vtl", podatki);
			return mailer.sendMail();
		});
		
		post("/sendContact", (req, res) -> {
			res.type("application/json");
			
			JSONObject json = (JSONObject) new JSONParser().parse(req.body());
			String ime = json.get("ime").toString();
			String naslov = json.get("naslov").toString();
			String sporocilo = json.get("sporocilo").toString();
			
			VelocityContext podatki = new VelocityContext();
			podatki.put("ime", ime);
			podatki.put("naslov", naslov);
			podatki.put("sporocilo", sporocilo);
			
			Mailer mailer = new Mailer("miha_jamsek@windowslive.com", "[mJamsek Site] Novo Sporocilo", "templates/email-template.vtl", podatki);
			return mailer.sendMail();
		});

		//lovi napake in jih posreduje v json obliki
		exception(IllegalArgumentException.class, (e, req, res) -> {
			res.status(400);
			res.body(JsonUtil.toJson(new ResponseError(e)));
		});

	}

	/*public static boolean sendMail() {
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

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("from-email@gmail.com"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("miha_jamsek@windowslive.com"));
			message.setSubject("Testing Subject");
			message.setText("Dear Mail Crawler,\n\n No spam to my email, please!");
			
			Transport.send(message);

			System.out.println("Done");
			return true;

		} catch (MessagingException e) {
			return false;
		}
	}*/
}


