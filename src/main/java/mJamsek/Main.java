package mJamsek;

import static spark.Spark.*;
import java.util.*;

import org.apache.velocity.VelocityContext;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import spark.*;
import spark.template.velocity.VelocityTemplateEngine;

public class Main {
	
	public static final int APP_PORT = 8080;
	public static final String DATA_DIR = "src/main/resources/public/data/";
	public static final String PUBLIC_DIR = "/public";
	
	public static void main(String[] args) {
		port(APP_PORT);
		staticFileLocation(PUBLIC_DIR);

		System.out.println("Streznik poslusa na: " + APP_PORT);
		
		get("/", (req, res) -> {
			res.type("text/html"); //tip odgovora - html
			Map<String, Object> model = new HashMap<>(); //hashmap za podatke ki se posredujejo templatu
			return new VelocityTemplateEngine().render(new ModelAndView(model, "templates/index.vtl")); //render template
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
		
		get("/projects", (req, res) -> {
			ArrayList<DB_Projekti> podatki = DB.getProjects();
			
			Map<String, Object> data = new HashMap<>();
			data.put("podatki", podatki);
			return new VelocityTemplateEngine().render(new ModelAndView(data, "templates/projects.vtl")); //render template
		});
		
		get("/projects/:id", (req, res) -> {
			String id = req.params(":id");
			DB_Projekt projekt = DB.getProjekt(id);
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("projekt", projekt);
			return new VelocityTemplateEngine().render(new ModelAndView(data, "templates/single_project.vtl")); //render template
		});
		
		get("/projects/download/:filename", (req, res) -> {
			String filename = req.params(":filename");
			
			res.header("Content-Disposition", "attachment; filename="+filename);
			res.type("application/force-download");
			
			return Utils.downloadFile(filename, res);
		});

		//lovi napake in jih posreduje v json obliki
		exception(IllegalArgumentException.class, (e, req, res) -> {
			res.status(400);
			res.body(JsonUtil.toJson(new ResponseError(e)));
		});

	}

}


