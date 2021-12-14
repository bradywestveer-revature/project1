package daos;

import exceptions.NotFoundException;
import models.Request;
import models.RequestStatus;
import models.RequestType;
import org.junit.jupiter.api.*;
import org.mockito.internal.matchers.Not;
import utilities.DatabaseCredentials;
import utilities.H2Utilities;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RequestDaoIT {
	private final UserDao userDao = new UserDaoImplementation (DatabaseCredentials.H2Url, DatabaseCredentials.H2Username, DatabaseCredentials.H2Password);
	private final RequestDao requestDao = new RequestDaoImplementation (DatabaseCredentials.H2Url, DatabaseCredentials.H2Username, DatabaseCredentials.H2Password);
	
	@BeforeAll
	static void beforeAll () {
		H2Utilities.createReimbursementStatusTable ();
		H2Utilities.createReimbursementTypeTable ();
		H2Utilities.createUserRoleTable ();
	}
	
	@AfterAll
	static void afterAll () {
		H2Utilities.dropReimbursementStatusTable ();
		H2Utilities.dropReimbursementTypeTable ();
		H2Utilities.dropUserRoleTable ();
	}
	
	@BeforeEach
	void beforeEach () {
		H2Utilities.createUsersTable ();
		H2Utilities.createRequestsTable ();
	}
	
	@AfterEach
	void afterEach () {
		H2Utilities.dropRequestsTable ();
		H2Utilities.dropUsersTable ();
	}
	
	@Test
	void createRequest () throws SQLException {
		requestDao.createRequest (1.0F, "test", 2, RequestType.LODGING);
		requestDao.createRequest (1.0F, "test", 2, RequestType.LODGING);
		requestDao.createRequest (1.0F, "test", 2, RequestType.LODGING);
		
		assertEquals (3, requestDao.getRequests ().size ());
	}
	
	@Test
	void getRequests () throws SQLException, ParseException {
		List <Request> requests = new ArrayList <> ();
		
		Request request1 = new Request (1, 1.0F, null, null, "test", 2, 0, RequestStatus.PENDING, RequestType.LODGING);
		Request request2 = new Request (2, 1.0F, null, null, "test", 2, 0, RequestStatus.PENDING, RequestType.FOOD);
		Request request3 = new Request (3, 1.0F, null, null, "test", 2, 0, RequestStatus.PENDING, RequestType.TRAVEL);
		
		requests.add (request1);
		requests.add (request2);
		requests.add (request3);
		
		requestDao.createRequest (request1.getAmount (), request1.getDescription (), request1.getAuthorId (), request1.getType ());
		requestDao.createRequest (request2.getAmount (), request2.getDescription (), request2.getAuthorId (), request2.getType ());
		requestDao.createRequest (request3.getAmount (), request3.getDescription (), request3.getAuthorId (), request3.getType ());
		
		List <Request> actualResult = requestDao.getRequests ();
		
		//instead of testing if timestamps are the same, just test if they are in the right format
		String timestampRegex = "\\d+-\\d+-\\d+ \\d+:\\d+:\\d+\\.\\d+";
		
		for (Request request : actualResult) {
			assertTrue (request.getSubmitted ().matches (timestampRegex));
			
			//set submitted to null to match requests List
			request.setSubmitted (null);
		}
		
		assertEquals (actualResult, requests);
	}
	
	@Test
	void getRequestsWithId () throws SQLException {
		int authorId = 2;
		
		List <Request> requests = new ArrayList <> ();
		
		Request request1 = new Request (1, 1.0F, null, null, "test", authorId, 0, RequestStatus.PENDING, RequestType.LODGING);
		Request request2 = new Request (2, 1.0F, null, null, "test", authorId, 0, RequestStatus.PENDING, RequestType.FOOD);
		Request request3 = new Request (3, 1.0F, null, null, "test", 3, 0, RequestStatus.PENDING, RequestType.TRAVEL);
		
		requests.add (request1);
		requests.add (request2);
		
		requestDao.createRequest (request1.getAmount (), request1.getDescription (), request1.getAuthorId (), request1.getType ());
		requestDao.createRequest (request2.getAmount (), request2.getDescription (), request2.getAuthorId (), request2.getType ());
		requestDao.createRequest (request3.getAmount (), request3.getDescription (), request3.getAuthorId (), request3.getType ());
		
		List <Request> actualResult = requestDao.getRequests (authorId);
		
		//instead of testing if timestamps are the same, just test if they are in the right format
		String timestampRegex = "\\d+-\\d+-\\d+ \\d+:\\d+:\\d+\\.\\d+";
		
		for (Request request : actualResult) {
			assertTrue (request.getSubmitted ().matches (timestampRegex));
			
			//set submitted to null to match requests List
			request.setSubmitted (null);
		}
		
		assertEquals (actualResult, requests);
	}
	
	@Test
	void updateRequest () throws SQLException, NotFoundException {
		requestDao.createRequest (1.0F, "test", 2, RequestType.LODGING);
		
		requestDao.updateRequest (1, 1, true);
		
		assertEquals (RequestStatus.APPROVED, requestDao.getRequests ().get (0).getStatus ());
	}
	
	@Test
	void updateRequestWhenInvalidId () throws SQLException, NotFoundException {
		assertThrows (NotFoundException.class, () -> {
			requestDao.updateRequest (1, 1, true);
		});
	}
}