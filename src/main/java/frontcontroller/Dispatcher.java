package frontcontroller;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import controllers.RequestController;
import controllers.UserController;
import exceptions.InvalidBodyException;
import exceptions.InvalidPasswordException;
import exceptions.NotFoundException;
import exceptions.UnauthorizedException;
import io.javalin.Javalin;

import org.apache.log4j.Logger;
import jsonmodels.JsonResponse;

import java.sql.SQLException;

import static io.javalin.apibuilder.ApiBuilder.*;

public class Dispatcher {
	public Dispatcher (Javalin webServer) {
		webServer.routes (() -> {
			path ("api", () -> {
				path ("sessions", () -> {
					post (UserController::logInUser);
					delete (UserController::logOutUser);
				});
				
				path ("requests", () -> {
					post (RequestController::createRequest);
					get (RequestController::getRequests);
					put (RequestController::updateRequest);
				});
			});
		});
		
		Logger logger = Logger.getLogger (Dispatcher.class);
		
		webServer.exception (Exception.class, ((exception, context) -> {
			logger.error ("Error!", exception);
			
			if (exception.getClass () == InvalidBodyException.class || exception.getClass () == JsonProcessingException.class || exception.getClass () == JsonParseException.class || exception.getClass () == MismatchedInputException.class || exception.getClass () == UnrecognizedPropertyException.class || exception.getClass () == InvalidFormatException.class) {
				context.status (400);
				
				context.json (new JsonResponse ("Error! Invalid body", false));
			}
			
			else if (exception.getClass () == InvalidPasswordException.class) {
				context.status (401);
				
				context.json (new JsonResponse ("Error! Invalid password", false));
			}
			
			else if (exception.getClass () == UnauthorizedException.class) {
				context.status (401);
				
				context.json (new JsonResponse ("Error! Unauthorized", false));
				
				context.redirect ("/login");
			}
			
			else if (exception.getClass () == SQLException.class) {
				context.status (500);
				
				context.json (new JsonResponse ("Error! SQL error", false));
			}
			
			else if (exception.getClass () == NotFoundException.class) {
				context.status (404);
				
				context.json (new JsonResponse (exception.getMessage (), false));
			}
			
			else {
				context.status (500);
				
				context.json (new JsonResponse ("Error! " + exception.getClass ().toString (), false));
			}
		}));
	}
}
