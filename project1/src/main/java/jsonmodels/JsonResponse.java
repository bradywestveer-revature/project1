package jsonmodels;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class JsonResponse {
	private String message;
	private boolean success;
	private Object data;
	private String redirect;
	
	private static final Logger logger = Logger.getLogger (JsonResponse.class);
	
	public JsonResponse () {}
	
	public JsonResponse (String message, boolean success) {
		this (message, success, null, null);
	}
	
	public JsonResponse (String message, boolean success, Object data) {
		this (message, success, data, null);
	}
	
	public JsonResponse (String message, boolean success, Object data, String redirect) {
		this.message = message;
		this.success = success;
		this.data = data;
		this.redirect= redirect;
		
		try {
			logger.log (success ? Level.INFO : Level.ERROR, "Response: " + new ObjectMapper ().writeValueAsString (this));
		}
		
		catch (JsonProcessingException exception) {
			logger.error ("Error!", exception);
		}
	}
	
	public String getMessage () {
		return message;
	}
	
	public boolean isSuccess () {
		return success;
	}
	
	public String getRedirect () {
		return redirect;
	}
	
	public Object getData () {
		return data;
	}
}
