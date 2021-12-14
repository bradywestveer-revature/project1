package daos;

import exceptions.InvalidPasswordException;
import exceptions.NotFoundException;
import models.*;
import utilities.DatabaseCredentials;

import java.sql.*;
import java.util.HashMap;

public class UserDaoImplementation implements UserDao {
	private final String url;
	private final String username;
	private final String password;
	
	public UserDaoImplementation () {
		this.url = DatabaseCredentials.url;
		this.username = DatabaseCredentials.username;
		this.password = DatabaseCredentials.password;
	}
	
	public UserDaoImplementation (String url, String username, String password) {
		this.url = url;
		this.username = username;
		this.password = password;
	}
	
	@Override
	public HashMap <Integer, String> getUserNames () throws SQLException {
		try (Connection connection = DriverManager.getConnection (this.url, this.username, this.password)) {
			PreparedStatement statement = connection.prepareStatement ("SELECT ERS_USERS_ID, USER_FIRST_NAME, USER_LAST_NAME FROM ERS_USERS;");
			
			ResultSet resultSet = statement.executeQuery ();
			
			HashMap <Integer, String> userNames = new HashMap <> ();
			
			while (resultSet.next ()) {
				userNames.put (resultSet.getInt (1), resultSet.getString (2) + " " + resultSet.getString (3));
			}
			
			return userNames;
		}
	}
	
	@Override
	public User getUser (int id) throws SQLException, NotFoundException {
		try (Connection connection = DriverManager.getConnection (this.url, this.username, this.password)) {
			PreparedStatement statement = connection.prepareStatement ("SELECT * FROM ERS_USERS WHERE ERS_USERS_ID = ?;");
			
			statement.setInt (1, id);
			
			ResultSet resultSet = statement.executeQuery ();
			
			User user = null;
			
			while (resultSet.next ()) {
				user = new User (resultSet.getInt (1), resultSet.getString (2), resultSet.getString (3), resultSet.getString (4), resultSet.getString (5), resultSet.getString (6), UserRole.values () [resultSet.getInt (7) - 1]);
			}
			
			if (user == null) {
				throw new NotFoundException ("Error! User with id: " + id + " not found");
			}
			
			return user;
		}
	}
	
	@Override
	public User getUser (String username) throws SQLException, InvalidPasswordException, NotFoundException {
		try (Connection connection = DriverManager.getConnection (this.url, this.username, this.password)) {
			PreparedStatement statement = connection.prepareStatement ("SELECT * FROM ERS_USERS WHERE ERS_USERNAME = ?;");
			
			statement.setString (1, username);
			
			ResultSet resultSet = statement.executeQuery ();
			
			User user = null;
			
			while (resultSet.next ()) {
				user = new User (resultSet.getInt (1), resultSet.getString (2), resultSet.getString (3), resultSet.getString (4), resultSet.getString (5), resultSet.getString (6), UserRole.values () [resultSet.getInt (7) - 1]);
			}
			
			if (user == null) {
				throw new NotFoundException ("Error! User with username: " + username + " not found");
			}
			
			return user;
		}
	}
}
