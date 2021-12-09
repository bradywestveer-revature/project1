package daos;

import exceptions.InvalidCredentialsException;
import exceptions.UnauthorizedException;
import models.User;

import java.sql.SQLException;

public interface UserDao {
	User getUser (String username) throws SQLException, UnauthorizedException, InvalidCredentialsException;
}
