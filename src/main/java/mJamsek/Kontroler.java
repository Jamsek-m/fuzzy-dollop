package mJamsek;
import static spark.Spark.*;
import spark.*;

public class Kontroler {
	
	public UserService us = new UserService();
	
	public Kontroler(final UserService userService){
		get("/users", new Route(){
			@Override
			public Object handle(Request request, Response response){
				return us.getAllUsers();
			}
		});
	}
}
