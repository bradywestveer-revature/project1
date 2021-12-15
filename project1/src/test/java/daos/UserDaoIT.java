package daos;

import exceptions.NotFoundException;
import models.User;
import models.UserRole;
import org.junit.jupiter.api.*;
import utilities.DatabaseCredentials;
import utilities.H2Utilities;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserDaoIT {
	private final UserDao userDao = new UserDaoImplementation (DatabaseCredentials.H2Url, DatabaseCredentials.H2Username, DatabaseCredentials.H2Password);
	
	private final List <User> users = new ArrayList <> ();
	
	public UserDaoIT () {
		//populate users list for testing the database
		//these should be the same as the users added in H2Utilities.createUsersTable ();
		users.add (new User (1, "manager1", "$2a$12$t3usDyOnl7oQtYOABIyhc.Cz.BxVFD7gWvcIDbeFgw8jd2vYw4dDS", "Manager", "One", "managerone@gmail.com", UserRole.MANAGER));
		users.add (new User (2, "employee1", "$2a$12$qxW7yx45Zb8OMpQTCWGfmuf8VnIxtO3mRAS9ozJpyAfWDJNg0jjbS", "Employee", "One", "employeeone@gmail.com", UserRole.EMPLOYEE));
		users.add (new User (3, "employee2", "$2a$12$qxW7yx45Zb8OMpQTCWGfmuf8VnIxtO3mRAS9ozJpyAfWDJNg0jjbS", "Employee", "Two", "employeetwo@gmail.com", UserRole.EMPLOYEE));
		users.add (new User (4, "employee3", "$2a$12$qxW7yx45Zb8OMpQTCWGfmuf8VnIxtO3mRAS9ozJpyAfWDJNg0jjbS", "Employee", "Three", "employeethree@gmail.com", UserRole.EMPLOYEE));
	}
	
	@BeforeAll
	static void beforeAll () {
		H2Utilities.createUserRoleTable ();
	}
	
	@AfterAll
	static void afterAll () {
		H2Utilities.dropUserRoleTable ();
	}
	
	@BeforeEach
	void beforeEach () {
		H2Utilities.createUsersTable ();
	}
	
	@AfterEach
	void afterEach () {
		H2Utilities.dropUsersTable ();
	}
	
	@Test
	void getUserNames () throws SQLException {
		HashMap <Integer, String> userNames = new HashMap <> ();
		
		userNames.put (users.get (0).getId (), users.get (0).getFirstName () + " " + users.get (0).getLastName ());
		userNames.put (users.get (1).getId (), users.get (1).getFirstName () + " " + users.get (1).getLastName ());
		userNames.put (users.get (2).getId (), users.get (2).getFirstName () + " " + users.get (2).getLastName ());
		userNames.put (users.get (3).getId (), users.get (3).getFirstName () + " " + users.get (3).getLastName ());
		
		assertEquals (userNames, userDao.getUserNames ());
	}
	
	@Test
	void getUserWithId () throws SQLException, NotFoundException {
		assertEquals (users.get (0), userDao.getUser (users.get (0).getId ()));
	}
	
	@Test
	void getUserWithIdWhenInvalidId () {
		assertThrows (NotFoundException.class, () -> userDao.getUser (0));
	}
	
	@Test
	void getUserWithUsername () throws SQLException, NotFoundException {
		assertEquals (users.get (0), userDao.getUser (users.get (0).getUsername ()));
	}
	
	@Test
	void getUserWithUsernameWhenInvalidUsername () {
		assertThrows (NotFoundException.class, () -> userDao.getUser (null));
	}
}