package jsonmodels;

public class UserResponse {
	private String username;
	private String role;
	
	public UserResponse () {}
	
	public UserResponse (String username, String role) {
		this.username = username;
		this.role = role;
	}
	
	public String getUsername () {
		return username;
	}
	
	public String getRole () {
		return role;
	}
}
