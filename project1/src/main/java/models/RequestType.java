package models;

public enum RequestType {
	LODGING (1),
	TRAVEL (2),
	FOOD (3),
	OTHER (4);
	
	private final int value;
	
	RequestType (int value) {
		this.value = value;
	}
	
	public int getValue () {
		return value;
	}
}
