package responsemodels;

public class LoginResponse {
	private String userRole;
	
	public LoginResponse () {}
	
	public LoginResponse (String userRole) {
		this.userRole = userRole;
	}
	
	public String getUserRole () {
		return userRole;
	}
}
