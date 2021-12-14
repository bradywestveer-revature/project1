package daos;

import exceptions.NotFoundException;
import models.Request;
import models.RequestStatus;
import models.RequestType;
import utilities.DatabaseCredentials;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RequestDaoImplementation implements RequestDao {
	private final String url;
	private final String username;
	private final String password;
	
	public RequestDaoImplementation () {
		this.url = DatabaseCredentials.url;
		this.username = DatabaseCredentials.username;
		this.password = DatabaseCredentials.password;
	}
	
	public RequestDaoImplementation (String url, String username, String password) {
		this.url = url;
		this.username = username;
		this.password = password;
	}
	
	@Override
	public void createRequest (Float amount, String description, Integer authorId, RequestType type) throws SQLException {
		try (Connection connection = DriverManager.getConnection (this.url, this.username, this.password)) {
			PreparedStatement statement = connection.prepareStatement ("INSERT INTO ERS_REIMBURSEMENT (REIMB_AMOUNT, REIMB_DESCRIPTION, REIMB_AUTHOR, REIMB_TYPE_ID) VALUES (?, ?, ?, ?);");
			
			statement.setFloat (1, amount);
			statement.setString (2, description);
			statement.setInt (3, authorId);
			statement.setInt (4, type.getValue ());
			
			statement.executeUpdate ();
		}
	}
	
	@Override
	public List <Request> getRequests () throws SQLException {
		try (Connection connection = DriverManager.getConnection (this.url, this.username, this.password)) {
			PreparedStatement statement = connection.prepareStatement ("SELECT * FROM ERS_REIMBURSEMENT;");
			
			ResultSet resultSet = statement.executeQuery ();
			
			List <Request> requests = new ArrayList <> ();
			
			while (resultSet.next ()) {
				requests.add (new Request (resultSet.getInt (1), resultSet.getFloat (2), resultSet.getString (3), resultSet.getString (4), resultSet.getString (5), resultSet.getInt (7), resultSet.getInt (8), RequestStatus.values () [resultSet.getInt (9) - 1], RequestType.values () [resultSet.getInt (10) - 1]));
			}
			
			return requests;
		}
	}
	
	@Override
	public List <Request> getRequests (Integer authorId) throws SQLException {
		try (Connection connection = DriverManager.getConnection (this.url, this.username, this.password)) {
			PreparedStatement statement = connection.prepareStatement ("SELECT * FROM ERS_REIMBURSEMENT WHERE REIMB_AUTHOR = ?;");
			
			statement.setInt (1, authorId);
			
			ResultSet resultSet = statement.executeQuery ();
			
			List <Request> requests = new ArrayList <> ();
			
			while (resultSet.next ()) {
				requests.add (new Request (resultSet.getInt (1), resultSet.getFloat (2), resultSet.getString (3), resultSet.getString (4), resultSet.getString (5), resultSet.getInt (7), resultSet.getInt (8), RequestStatus.values () [resultSet.getInt (9) - 1], RequestType.values () [resultSet.getInt (10) - 1]));
			}
			
			return requests;
		}
	}
	
	@Override
	public void updateRequest (Integer id, Integer resolverId, Boolean approved) throws SQLException, NotFoundException {
		try (Connection connection = DriverManager.getConnection (this.url, this.username, this.password)) {
			PreparedStatement statement = connection.prepareStatement ("UPDATE ERS_REIMBURSEMENT SET REIMB_RESOLVED = now (), REIMB_RESOLVER = ?, REIMB_STATUS_ID = ? WHERE REIMB_ID = ?;");
			
			statement.setInt (1, resolverId);
			statement.setInt (2, (approved ? RequestStatus.APPROVED : RequestStatus.DENIED).getValue ());
			statement.setInt (3, id);
			
			if (statement.executeUpdate () == 0) {
				throw new NotFoundException ("Error! No request found with id: " + id);
			}
		}
	}
}
