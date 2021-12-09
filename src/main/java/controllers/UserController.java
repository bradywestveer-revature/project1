package controllers;

import bodymodels.CredentialsBody;
import exceptions.InvalidBodyException;
import exceptions.InvalidCredentialsException;
import exceptions.UnauthorizedException;
import io.javalin.http.Context;
import models.User;
import responsemodels.ErrorResponse;
import responsemodels.LoginResponse;
import responsemodels.MessageResponse;
import services.UserService;

import java.sql.SQLException;

public class UserController {
	private static final UserService userService = new UserService ();
	
	public static void logInUser (Context context) throws InvalidBodyException, InvalidCredentialsException, SQLException, UnauthorizedException {
		CredentialsBody body = context.bodyAsClass (CredentialsBody.class);
		
		String username = body.getUsername ();
		String password = body.getPassword ();
		
		if (username == null || password == null) {
			throw new InvalidBodyException ();
		}
		
		User user = userService.logInUser (username, password);
		
		context.sessionAttribute ("user", user);
		
		context.json (new LoginResponse (user.getRole ().name ()));
	}
	
	public static void logOutUser (Context context) {
		if (context.sessionAttribute ("user") == null) {
			context.json (new ErrorResponse ("Error! Not logged in"));
			
			return;
		}
		
		context.sessionAttribute ("user", null);
		
		context.json (new MessageResponse ("Logged out"));
	}
}
