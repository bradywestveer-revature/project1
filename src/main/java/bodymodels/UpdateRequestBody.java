package bodymodels;

import models.RequestType;

public class UpdateRequestBody {
	private Integer id;
	private Boolean approved;
	
	public UpdateRequestBody () {}
	
	public UpdateRequestBody (Integer id, Boolean approved) {
		this.id = id;
		this.approved = approved;
	}
	
	public Integer getId () {
		return id;
	}
	
	public Boolean isApproved () {
		return approved;
	}
}
