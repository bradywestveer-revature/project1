package jsonmodels;

import models.RequestStatus;
import models.RequestType;

import java.util.Objects;

public class RequestResponse {
	private int id;
	private float amount;
	private String submitted;
	private String resolved;
	private String description;
	private String author;
	private String resolver;
	private RequestStatus status;
	private RequestType type;
	
	public RequestResponse () {}
	
	public RequestResponse (int id, float amount, String submitted, String resolved, String description, String author, String resolver, RequestStatus status, RequestType type) {
		this.id = id;
		this.amount = amount;
		this.submitted = submitted;
		this.resolved = resolved;
		this.description = description;
		this.author = author;
		this.resolver = resolver;
		this.status = status;
		this.type = type;
	}
	
	public int getId () {
		return id;
	}
	
	public float getAmount () {
		return amount;
	}
	
	public String getSubmitted () {
		return submitted;
	}
	
	public String getResolved () {
		return resolved;
	}
	
	public String getDescription () {
		return description;
	}
	
	public String getAuthor () {
		return author;
	}
	
	public String getResolver () {
		return resolver;
	}
	
	public RequestStatus getStatus () {
		return status;
	}
	
	public RequestType getType () {
		return type;
	}
	
	@Override
	public boolean equals (Object o) {
		if (this == o) return true;
		if (o == null || getClass () != o.getClass ()) return false;
		RequestResponse that = (RequestResponse) o;
		return id == that.id && Float.compare (that.amount, amount) == 0 && Objects.equals (submitted, that.submitted) && Objects.equals (resolved, that.resolved) && Objects.equals (description, that.description) && Objects.equals (author, that.author) && Objects.equals (resolver, that.resolver) && status == that.status && type == that.type;
	}
}
