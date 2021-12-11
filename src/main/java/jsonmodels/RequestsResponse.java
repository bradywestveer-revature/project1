package jsonmodels;

import models.Request;

import java.util.List;

public class RequestsResponse {
	private String userRole;
	private List <RequestResponse> requests;
	
	public RequestsResponse () {}
	
	public RequestsResponse (String userRole, List <RequestResponse> requests) {
		this.userRole = userRole;
		this.requests = requests;
	}
	
	public String getUserRole () {
		return userRole;
	}
	
	public List <RequestResponse> getRequests () {
		return requests;
	}
}
