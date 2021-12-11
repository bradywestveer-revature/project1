package controllers;

import exceptions.NotFoundException;
import jsonmodels.CredentialsBody;
import exceptions.InvalidBodyException;
import exceptions.InvalidPasswordException;
import exceptions.UnauthorizedException;
import io.javalin.http.Context;
import jsonmodels.UserResponse;
import models.User;
import jsonmodels.JsonResponse;
import services.UserService;

import java.sql.SQLException;

public class UserController {
	private static final UserService userService = new UserService ();
	
	public static void logInUser (Context context) throws InvalidBodyException, InvalidPasswordException, SQLException, UnauthorizedException, NotFoundException {
		CredentialsBody body = context.bodyAsClass (CredentialsBody.class);
		
		String username = body.getUsername ();
		String password = body.getPassword ();
		
		if (username == null || password == null) {
			throw new InvalidBodyException ();
		}
		
		User user = userService.logInUser (username, password);
		
		context.sessionAttribute ("user", user);
		
		context.json (new JsonResponse ("Logged in", true, new UserResponse (user.getUsername (), user.getRole ().name ())));
	}
	
	public static void logOutUser (Context context) throws UnauthorizedException {
		if (context.sessionAttribute ("user") == null) {
			throw new UnauthorizedException ();
		}
		
		context.sessionAttribute ("user", null);
		
		context.json (new JsonResponse ("Logged out", true));
		
		context.redirect ("/login");
	}
}
