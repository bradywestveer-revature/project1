package responsemodels;

public class ErrorResponse {
	String message;
	boolean success;
	
	public ErrorResponse () {}
	
	public ErrorResponse (String message) {
		this.message = message;
		this.success = false;
	}
	
	public ErrorResponse (String message, boolean success) {
		this.message = message;
		this.success = success;
	}
	
	public String getMessage () {
		return message;
	}
	
	public boolean isSuccess () {
		return success;
	}
}
