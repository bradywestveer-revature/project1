package jsonmodels;

public class CredentialsBody {
	private String username;
	private String password;
	
	public CredentialsBody () {}
	
	public CredentialsBody (String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	public String getUsername () {
		return username;
	}
	
	public String getPassword () {
		return password;
	}
}
