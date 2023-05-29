package src.main.java.com.cms.app.entities;

import java.sql.Date;

public class Assign implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private int complaintId;
	private String advisorId;
	private String complaintResponse;
	private Date responseSentDate;
	
	public Assign() {}
	public Assign(int id, int complaintId, String advisorId, String complaintResponse, Date responseSentDate) {
		this.id = id;
		this.complaintId = complaintId;
		this.advisorId = advisorId;
		this.complaintResponse = complaintResponse;
		this.responseSentDate = responseSentDate;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getComplaintId() {
		return complaintId;
	}
	public void setComplaintId(int complaintId) {
		this.complaintId = complaintId;
	}
	public String getAdvisorId() {
		return advisorId;
	}
	public void setAdvisorId(String advisorId) {
		this.advisorId = advisorId;
	}
	public String getComplaintResponse() {
		return complaintResponse;
	}
	public void setComplaintResponse(String complaintResponse) {
		this.complaintResponse = complaintResponse;
	}
	public Date getResponseSentDate() {
		return responseSentDate;
	}
	public void setResponseSentDate(Date responseSentDate) {
		this.responseSentDate = responseSentDate;
	}
	
	
}
