package services;

import daos.UserDao;
import daos.UserDaoImplementation;
import exceptions.InvalidPasswordException;
import exceptions.NotFoundException;
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
	
	public User logInUser (String username, String password) throws InvalidPasswordException, SQLException, NotFoundException {
		User user = userDao.getUser (username);
		
		if (user == null || !user.getPassword ().equals (password)) {
			throw new InvalidPasswordException ();
		}
		
		return user;
	}
}
