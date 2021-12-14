package services;

import daos.RequestDao;
import daos.RequestDaoImplementation;
import daos.UserDao;
import daos.UserDaoImplementation;
import exceptions.NotFoundException;
import jsonmodels.RequestResponse;
import models.Request;
import models.RequestType;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RequestService {
	private final RequestDao requestDao;
	private final UserDao userDao;
	
	public RequestService () {
		this.requestDao = new RequestDaoImplementation ();
		this.userDao = new UserDaoImplementation ();
	}
	
	public RequestService (RequestDao requestDao, UserDao userDao) {
		this.requestDao = requestDao;
		this.userDao = userDao;
	}
	
	public void createRequest (Float amount, String description, Integer authorId, RequestType type) throws SQLException {
		requestDao.createRequest (amount, description, authorId, type);
	}
	
	public List <RequestResponse> getRequestResponses (List <Request> requests) throws SQLException {
		List <RequestResponse> requestResponses = new ArrayList <> ();
		
		HashMap <Integer, String> userNames = userDao.getUserNames ();
		
		for (Request request : requests) {
			requestResponses.add (new RequestResponse (request.getId (), request.getAmount (), request.getSubmitted (), request.getResolved (), request.getDescription (), userNames.get (request.getAuthorId ()), userNames.get (request.getResolverId ()), request.getStatus (), request.getType ()));
		}
		
		return requestResponses;
	}
	
	public List <RequestResponse> getRequests () throws SQLException, NotFoundException {
		return getRequestResponses (requestDao.getRequests ());
	}
	
	public List <RequestResponse> getRequests (Integer authorId) throws SQLException, NotFoundException {
		return getRequestResponses (requestDao.getRequests (authorId));
	}
	
	public void updateRequest (Integer id, Integer resolverId, Boolean approved) throws SQLException, NotFoundException {
		requestDao.updateRequest (id, resolverId, approved);
	}
}
