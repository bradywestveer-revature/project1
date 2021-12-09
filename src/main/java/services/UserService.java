package services;

import daos.UserDao;
import daos.UserDaoImplementation;
import exceptions.InvalidCredentialsException;
import exceptions.UnauthorizedException;
import models.User;

import java.sql.SQLException;

public class UserService {
	private final UserDao userDao;
	
	public UserService () {
		this.userDao = new UserDaoImplementation ();
	}
	
	public UserService (UserDao userDao) {
		this.userDao = userDao;
	}
	
	public User logInUser (String username, String password) throws InvalidCredentialsException, SQLException, UnauthorizedException {
		User user = userDao.getUser (username);
		
		if (user.getPassword ().equals (password)) {
			return user;
		}
		
		else {
			throw new InvalidCredentialsException ();
		}
	}
}
