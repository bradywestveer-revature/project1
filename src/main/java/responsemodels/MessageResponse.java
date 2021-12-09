package responsemodels;

public class MessageResponse {
	String message;
	boolean success;
	
	public MessageResponse () {}
	
	public MessageResponse (String message) {
		this.message = message;
		this.success = true;
	}
	
	public MessageResponse (String message, boolean success) {
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
