package services;

import at.favre.lib.crypto.bcrypt.BCrypt;
import daos.UserDao;
import daos.UserDaoImplementation;
import exceptions.InvalidPasswordException;
import exceptions.NotFoundException;
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
		
		if (!BCrypt.verifyer ().verify (password.toCharArray (), user.getPassword ()).verified) {
			throw new InvalidPasswordException ();
		}
		
		return user;
	}
}
