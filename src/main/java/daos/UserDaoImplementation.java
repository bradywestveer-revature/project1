package daos;

import exceptions.InvalidCredentialsException;
import exceptions.UnauthorizedException;
import models.RequestStatus;
import models.User;
import models.UserRole;
import utilities.DatabaseCredentials;

import java.sql.*;

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
	public User getUser (String username) throws SQLException, InvalidCredentialsException {
		try (Connection connection = DriverManager.getConnection (this.url, this.username, this.password)) {
			PreparedStatement statement = connection.prepareStatement ("SELECT * FROM ERS_USERS WHERE ERS_USERNAME = ?;");
			
			statement.setString (1, username);
			
			ResultSet resultSet = statement.executeQuery ();
			
			User user = null;
			
			while (resultSet.next ()) {
				user = new User (resultSet.getInt (1), resultSet.getString (2), resultSet.getString (3), resultSet.getString (4), resultSet.getString (5), resultSet.getString (6), UserRole.values () [resultSet.getInt (7) - 1]);
			}
			
			if (user == null) {
				throw new InvalidCredentialsException ();
			}
			
			return user;
		}
	}
}
