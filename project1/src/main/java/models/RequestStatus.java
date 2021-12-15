package models;

public enum RequestStatus {
	PENDING (1),
	APPROVED (2),
	DENIED (3);
	
	private final int value;
	
	RequestStatus (int value) {
		this.value = value;
	}
	
	public int getValue () {
		return value;
	}
}
