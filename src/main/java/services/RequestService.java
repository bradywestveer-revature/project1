package services;

import daos.RequestDao;
import daos.RequestDaoImplementation;
import exceptions.NotFoundException;
import models.Request;
import models.RequestType;

import java.sql.SQLException;
import java.util.List;

public class RequestService {
	private final RequestDao requestDao;
	
	public RequestService () {
		this.requestDao = new RequestDaoImplementation ();
	}
	
	public RequestService (RequestDao requestDao) {
		this.requestDao = requestDao;
	}
	
	public void createRequest (Float amount, String description, Integer authorId, RequestType type) throws SQLException {
		requestDao.createRequest (amount, description, authorId, type);
	}
	
	public List <Request> getRequests () throws SQLException {
		return requestDao.getRequests ();
	}
	
	public List <Request> getRequests (Integer authorId) throws SQLException {
		return requestDao.getRequests (authorId);
	}
	
	public void updateRequest (Integer id, Integer resolverId, Boolean approved) throws SQLException, NotFoundException {
		requestDao.updateRequest (id, resolverId, approved);
	}
}
