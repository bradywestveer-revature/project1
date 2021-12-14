package services;

import daos.RequestDao;
import daos.RequestDaoImplementation;
import daos.UserDao;
import daos.UserDaoImplementation;
import exceptions.NotFoundException;
import jsonmodels.RequestResponse;
import models.Request;
import models.RequestStatus;
import models.RequestType;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RequestServiceTest {
	private final RequestDao requestDao = Mockito.mock (RequestDaoImplementation.class);
	private final UserDao userDao = Mockito.mock (UserDaoImplementation.class);
	private final RequestService requestService = Mockito.mock (RequestService.class, Mockito.withSettings ().useConstructor (requestDao, userDao));
	
	@Test
	void createRequest () throws SQLException {
		float amount = 1.0F;
		String description = "test";
		int authorId = 1;
		RequestType type = RequestType.LODGING;
		
		Mockito.doCallRealMethod ().when (requestService).createRequest (amount, description, authorId, type);
		
		requestService.createRequest (amount, description, authorId, type);
		
		Mockito.verify (requestDao).createRequest (amount, description, authorId, type);
	}
	
	@Test
	void getRequestResponses () throws SQLException, NotFoundException {
		Request request1 = new Request (1, 1.0F, "submitted1", "resolved1", "test", 1, 4, RequestStatus.PENDING, RequestType.LODGING);
		Request request2 = new Request (2, 1.0F, "submitted2", "resolved2", "test", 2, 5, RequestStatus.APPROVED, RequestType.FOOD);
		Request request3 = new Request (3, 1.0F, "submitted3", "resolved3", "test", 3, 6, RequestStatus.DENIED, RequestType.TRAVEL);
		
		List <Request> requests = new ArrayList <> ();
		
		requests.add (request1);
		requests.add (request2);
		requests.add (request3);
		
		HashMap <Integer, String> userNames = new HashMap <> ();
		
		userNames.put (1, "User 1");
		userNames.put (2, "User 2");
		userNames.put (3, "User 3");
		userNames.put (4, "User 4");
		userNames.put (5, "User 5");
		userNames.put (6, "User 6");
		
		List <RequestResponse> expectedResult = new ArrayList <> ();
		
		expectedResult.add (new RequestResponse (request1.getId (), request1.getAmount (), request1.getSubmitted (), request1.getResolved (), request1.getDescription (), userNames.get (1), userNames.get (4), request1.getStatus (), request1.getType ()));
		expectedResult.add (new RequestResponse (request2.getId (), request2.getAmount (), request2.getSubmitted (), request2.getResolved (), request2.getDescription (), userNames.get (2), userNames.get (5), request2.getStatus (), request2.getType ()));
		expectedResult.add (new RequestResponse (request3.getId (), request3.getAmount (), request3.getSubmitted (), request3.getResolved (), request3.getDescription (), userNames.get (3), userNames.get (6), request3.getStatus (), request3.getType ()));
		
		Mockito.when (userDao.getUserNames ()).thenReturn (userNames);
		
		Mockito.when (requestService.getRequestResponses (requests)).thenCallRealMethod ();
		
		assertEquals (expectedResult, requestService.getRequestResponses (requests));
	}
	
	@Test
	void getRequests () throws SQLException, NotFoundException {
		Request request1 = new Request (1, 1.0F, "submitted1", "resolved1", "test", 1, 4, RequestStatus.PENDING, RequestType.LODGING);
		Request request2 = new Request (2, 1.0F, "submitted2", "resolved2", "test", 2, 5, RequestStatus.APPROVED, RequestType.FOOD);
		Request request3 = new Request (3, 1.0F, "submitted3", "resolved3", "test", 3, 6, RequestStatus.DENIED, RequestType.TRAVEL);
		
		List <Request> requests = new ArrayList <> ();
		
		requests.add (request1);
		requests.add (request2);
		requests.add (request3);
		
		HashMap <Integer, String> userNames = new HashMap <> ();
		
		userNames.put (1, "User 1");
		userNames.put (2, "User 2");
		userNames.put (3, "User 3");
		userNames.put (4, "User 4");
		userNames.put (5, "User 5");
		userNames.put (6, "User 6");
		
		List <RequestResponse> requestResponses = new ArrayList <> ();
		
		requestResponses.add (new RequestResponse (request1.getId (), request1.getAmount (), request1.getSubmitted (), request1.getResolved (), request1.getDescription (), userNames.get (1), userNames.get (4), request1.getStatus (), request1.getType ()));
		requestResponses.add (new RequestResponse (request2.getId (), request2.getAmount (), request2.getSubmitted (), request2.getResolved (), request2.getDescription (), userNames.get (2), userNames.get (5), request2.getStatus (), request2.getType ()));
		requestResponses.add (new RequestResponse (request3.getId (), request3.getAmount (), request3.getSubmitted (), request3.getResolved (), request3.getDescription (), userNames.get (3), userNames.get (6), request3.getStatus (), request3.getType ()));
		
		Mockito.when (requestDao.getRequests ()).thenReturn (requests);
		
		Mockito.when (requestService.getRequestResponses (requests)).thenReturn (requestResponses);
		
		Mockito.when (requestService.getRequests ()).thenCallRealMethod ();
		
		assertEquals (requestResponses, requestService.getRequests ());
	}
	
	@Test
	void getRequestsWithId () throws SQLException, NotFoundException {
		int authorId = 1;
		
		Request request1 = new Request (1, 1.0F, "submitted1", "resolved1", "test", 1, 4, RequestStatus.PENDING, RequestType.LODGING);
		Request request2 = new Request (2, 1.0F, "submitted2", "resolved2", "test", 2, 5, RequestStatus.APPROVED, RequestType.FOOD);
		Request request3 = new Request (3, 1.0F, "submitted3", "resolved3", "test", 3, 6, RequestStatus.DENIED, RequestType.TRAVEL);
		
		List <Request> authorRequests = new ArrayList <> ();
		
		authorRequests.add (request1);
		
		HashMap <Integer, String> userNames = new HashMap <> ();
		
		userNames.put (1, "User 1");
		userNames.put (2, "User 2");
		userNames.put (3, "User 3");
		userNames.put (4, "User 4");
		userNames.put (5, "User 5");
		userNames.put (6, "User 6");
		
		List <RequestResponse> authorRequestResponses = new ArrayList <> ();
		
		RequestResponse requestResponse1 = new RequestResponse (request1.getId (), request1.getAmount (), request1.getSubmitted (), request1.getResolved (), request1.getDescription (), userNames.get (1), userNames.get (4), request1.getStatus (), request1.getType ());
		
		authorRequestResponses.add (requestResponse1);
		
		Mockito.when (requestDao.getRequests (authorId)).thenReturn (authorRequests);
		
		Mockito.when (requestService.getRequestResponses (authorRequests)).thenReturn (authorRequestResponses);
		
		Mockito.when (requestService.getRequests (authorId)).thenCallRealMethod ();
		
		assertEquals (authorRequestResponses, requestService.getRequests (authorId));
	}
	
	@Test
	void updateRequest () throws SQLException, NotFoundException {
		int id = 1;
		int resolverId = 1;
		boolean approved = true;
		
		Mockito.doCallRealMethod ().when (requestService).updateRequest (id, resolverId, approved);
		
		requestService.updateRequest (id, resolverId, approved);
		
		Mockito.verify (requestDao).updateRequest (id, resolverId, approved);
	}
}