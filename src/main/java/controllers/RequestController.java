package controllers;

import jsonmodels.*;
import exceptions.InvalidBodyException;
import exceptions.NotFoundException;
import exceptions.UnauthorizedException;
import io.javalin.http.Context;
import models.Request;
import models.RequestType;
import models.User;
import models.UserRole;
import services.RequestService;

import java.sql.SQLException;
import java.util.List;

public class RequestController {
	private static final RequestService requestService = new RequestService ();
	
	public static void createRequest (Context context) throws InvalidBodyException, UnauthorizedException, SQLException {
		CreateRequestBody body = context.bodyAsClass (CreateRequestBody.class);
		
		Float amount = body.getAmount ();
		String description = body.getDescription ();
		RequestType type = body.getType ();
		
		if (amount == null || description == null || type == null) {
			throw new InvalidBodyException ();
		}
		
		User user = context.sessionAttribute ("user");
		
		//if user is not logged in
		if (user == null) {
			throw new UnauthorizedException ();
		}
		
		//if user is not an employee
		if (user.getRole () != UserRole.EMPLOYEE) {
			throw new UnauthorizedException ();
		}
		
		requestService.createRequest (amount, description, user.getId (), type);
		
		context.json (new JsonResponse ("Created request", true));
	}
	
	public static void getRequests (Context context) throws UnauthorizedException, SQLException, NotFoundException {
		User user = context.sessionAttribute ("user");
		
		//if user is not logged in
		if (user == null) {
			throw new UnauthorizedException ();
		}
		
		//get requests based on user role
		List <RequestResponse> requests = user.getRole () == UserRole.MANAGER ? requestService.getRequests () : requestService.getRequests (user.getId ());
		
		context.json (new JsonResponse ("Got " + requests.size () + " requests", true, new RequestsResponse (user.getRole ().name (), requests)));
	}
	
	public static void updateRequest (Context context) throws InvalidBodyException, UnauthorizedException, SQLException, NotFoundException {
		UpdateRequestBody body = context.bodyAsClass (UpdateRequestBody.class);
		
		Integer id = body.getId ();
		Boolean approved = body.isApproved ();
		
		if (id == null || approved == null) {
			throw new InvalidBodyException ();
		}
		
		User user = context.sessionAttribute ("user");
		
		//if user is not logged in
		if (user == null) {
			throw new UnauthorizedException ();
		}
		
		//if user is not a manager
		if (user.getRole () != UserRole.MANAGER) {
			throw new UnauthorizedException ();
		}
		
		requestService.updateRequest (id, user.getId (), approved);
		
		context.json (new JsonResponse ("Updated request", true));
	}
}
