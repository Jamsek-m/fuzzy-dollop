package mJamsek;

import static spark.Spark.*;
import java.util.*;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import spark.*;
import spark.template.velocity.VelocityTemplateEngine;

import com.google.gson.Gson;
import javax.mail.*;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

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
			boolean ratalo = sendMail(); //poslje plain text mejl
			return JsonUtil.toJson(ratalo);
		});
		
		get("/send2", (req, res) -> {
			res.type("application/json");
			//SendMailWithVelocityTemplate.poslji(); //naj bi poslalo template-rendered html majl
			SendMailVelocity.sendMail("miha_jamsek@windowslive.com", "http://test");
			return JsonUtil.toJson(true);
		});

		//lovi napake in jih posreduje v json obliki
		exception(IllegalArgumentException.class, (e, req, res) -> {
			res.status(400);
			res.body(JsonUtil.toJson(new ResponseError(e)));
		});

	}

	public static boolean sendMail() {
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
	}
}

class ResponseError {
	private String message;

	public ResponseError(String message, String... args) {
		this.message = String.format(message, args);
	}

	public ResponseError(Exception e) {
		this.message = e.getMessage();
	}

	public String getMessage() {
		return this.message;
	}
}

class JsonUtil {
	public static String toJson(Object object) {
		return new Gson().toJson(object);
	}

	public static ResponseTransformer json() {
		return JsonUtil::toJson;
	}
}

class UserService {

	private ArrayList<User> seznam;
	private int count;

	public UserService() {
		this.seznam = new ArrayList<User>();
		this.count = 1;
	}

	public ArrayList<User> getAllUsers() {
		return this.seznam;
	}

	public User getUser(int id) {
		Iterator<User> it = this.seznam.iterator();
		while (it.hasNext()) {
			User curr = it.next();
			if (curr.getId() == id) {
				return curr;
			}
		}
		return null;
	}

	public User createUser(int id, String name, String email) {
		User u = new User(id, name, email);
		this.seznam.add(u);
		this.count++;
		return u;
	}

	public User updateUser(int id, String name, String email) {
		Iterator<User> it = this.seznam.iterator();
		while (it.hasNext()) {
			User curr = it.next();
			if (curr.getId() == id) {
				curr.setEmail(email);
				curr.setName(name);
				return curr;
			}
		}
		return null;
	}

	public int getNewId() {
		return this.count + 1;
	}

}

class User {
	private int id;
	private String name;
	private String email;

	public User(int i, String n, String e) {
		this.id = i;
		this.email = e;
		this.name = n;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}