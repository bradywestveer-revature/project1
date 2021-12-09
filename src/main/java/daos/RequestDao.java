package daos;

import exceptions.NotFoundException;
import models.Request;
import models.RequestType;

import java.sql.SQLException;
import java.util.List;

public interface RequestDao {
	void createRequest (Float amount, String description, Integer authorId, RequestType type) throws SQLException;
	
	List<Request> getRequests () throws SQLException;
	
	List<Request> getRequests (Integer authorId) throws SQLException;
	
	void updateRequest (Integer id, Integer resolverId, Boolean approved) throws SQLException, NotFoundException;
}
