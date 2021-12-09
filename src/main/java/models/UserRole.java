package models;

public enum UserRole {
	EMPLOYEE (1),
	MANAGER (2);
	
	private final int value;
	
	UserRole (int value) {
		this.value = value;
	}
	
	public int getValue () {
		return value;
	}
}
