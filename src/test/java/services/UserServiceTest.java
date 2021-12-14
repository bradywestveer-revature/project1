package services;

import daos.UserDao;
import daos.UserDaoImplementation;
import exceptions.InvalidPasswordException;
import exceptions.NotFoundException;
import exceptions.UnauthorizedException;
import models.User;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {
	private final UserDao userDao = Mockito.mock (UserDaoImplementation.class);
	private final UserService userService = new UserService (userDao);
	
	@Test
	void logInUser () throws SQLException, UnauthorizedException, InvalidPasswordException, NotFoundException {
		String username = "username";
		String password = "password";
		
		User expectedResult = new User ();
		
		expectedResult.setUsername (username);
		expectedResult.setPassword (password);
		
		Mockito.when (userDao.getUser (username)).thenReturn (expectedResult);
		
		assertEquals (expectedResult, userService.logInUser (username, password));
	}
	
	@Test
	void logInUserWhenInvalidPassword () throws SQLException, UnauthorizedException, InvalidPasswordException, NotFoundException {
		String username = "username";
		String password = "password";
		String invalidPassword = "passworda";
		
		User expectedResult = new User ();
		
		expectedResult.setUsername (username);
		expectedResult.setPassword (password);
		
		Mockito.when (userDao.getUser (username)).thenReturn (expectedResult);
		
		assertThrows (InvalidPasswordException.class, () -> {
			userService.logInUser (username, invalidPassword);
		});
	}
}