package models;

import java.util.Objects;

public class Request {
	private int id;
	private float amount;
	private String submitted;
	private String resolved;
	private String description;
	private int authorId;
	private Integer resolverId;
	private RequestStatus status;
	private RequestType type;
	
	public Request () {}
	
	public Request (int id, float amount, String submitted, String resolved, String description, int authorId, Integer resolverId, RequestStatus status, RequestType type) {
		this.id = id;
		this.amount = amount;
		this.submitted = submitted;
		this.resolved = resolved;
		this.description = description;
		this.authorId = authorId;
		this.resolverId = resolverId;
		this.status = status;
		this.type = type;
	}
	
	public int getId () {
		return id;
	}
	
	public void setId (int id) {
		this.id = id;
	}
	
	public float getAmount () {
		return amount;
	}
	
	public void setAmount (float amount) {
		this.amount = amount;
	}
	
	public String getSubmitted () {
		return submitted;
	}
	
	public void setSubmitted (String submitted) {
		this.submitted = submitted;
	}
	
	public String getResolved () {
		return resolved;
	}
	
	public void setResolved (String resolved) {
		this.resolved = resolved;
	}
	
	public String getDescription () {
		return description;
	}
	
	public void setDescription (String description) {
		this.description = description;
	}
	
	public int getAuthorId () {
		return authorId;
	}
	
	public void setAuthorId (int authorId) {
		this.authorId = authorId;
	}
	
	public Integer getResolverId () {
		return resolverId;
	}
	
	public void setResolverId (Integer resolverId) {
		this.resolverId = resolverId;
	}
	
	public RequestStatus getStatus () {
		return status;
	}
	
	public void setStatus (RequestStatus status) {
		this.status = status;
	}
	
	public RequestType getType () {
		return type;
	}
	
	public void setType (RequestType type) {
		this.type = type;
	}
	
	@Override
	public boolean equals (Object o) {
		if (this == o) return true;
		if (o == null || getClass () != o.getClass ()) return false;
		Request request = (Request) o;
		return id == request.id && Float.compare (request.amount, amount) == 0 && authorId == request.authorId && Objects.equals (resolverId, request.resolverId) && Objects.equals (submitted, request.submitted) && Objects.equals (resolved, request.resolved) && Objects.equals (description, request.description) && status == request.status && type == request.type;
	}
	
	@Override
	public int hashCode () {
		return Objects.hash (id, amount, submitted, resolved, description, authorId, resolverId, status, type);
	}
	
	@Override
	public String toString () {
		return "Request{" +
				"id=" + id +
				", amount=" + amount +
				", submitted='" + submitted + '\'' +
				", resolved='" + resolved + '\'' +
				", description='" + description + '\'' +
				", authorId=" + authorId +
				", resolverId=" + resolverId +
				", status=" + status +
				", type=" + type +
				'}';
	}
}
