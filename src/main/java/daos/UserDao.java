package daos;

import exceptions.InvalidPasswordException;
import exceptions.NotFoundException;
import exceptions.UnauthorizedException;
import models.User;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public interface UserDao {
	HashMap <Integer, String> getUserNames () throws SQLException;
	
	User getUser (int id) throws SQLException, NotFoundException;
	
	User getUser (String username) throws SQLException, UnauthorizedException, InvalidPasswordException, NotFoundException;
}
