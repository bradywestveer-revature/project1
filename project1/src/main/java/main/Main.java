package main;

import at.favre.lib.crypto.bcrypt.BCrypt;
import frontcontroller.FrontController;
import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;

public class Main {
	public static void main (String[] args) {
		Javalin webServer = Javalin.create (config -> config.addStaticFiles ("/web", Location.CLASSPATH)).start (80);
		
		new FrontController (webServer);
	}
}
