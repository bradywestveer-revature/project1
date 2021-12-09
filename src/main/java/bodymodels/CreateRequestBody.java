package bodymodels;

import models.RequestType;

public class CreateRequestBody {
	private Float amount;
	private String description;
	private RequestType type;
	
	public CreateRequestBody () {}
	
	public CreateRequestBody (Float amount, String description, RequestType type) {
		this.amount = amount;
		this.description = description;
		this.type = type;
	}
	
	public Float getAmount () {
		return amount;
	}
	
	public String getDescription () {
		return description;
	}
	
	public RequestType getType () {
		return type;
	}
}
