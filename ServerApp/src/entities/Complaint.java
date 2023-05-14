package entities;

import java.sql.Date;

public class Complaint implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	private int id;
	private String studentId;
	private String complaintDetails;
	private Date complaintSentDate;
	private String status;
	
	
	public Complaint() {}
	
	public Complaint(String studentId, String details) {
		this.studentId = studentId;
		this.complaintDetails = details;
		this.complaintSentDate = new Date(System.currentTimeMillis());
		this.status = "unresolve";
	}
	public Complaint(int id, String studentId, String details, Date date, String status) {
		this.id = id;
		this.studentId = studentId;
		this.complaintDetails = details;
		this.complaintSentDate = date;
		this.status = status;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getStudentId() {
		return studentId;
	}
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	public String getComplaintDetails() {
		return complaintDetails;
	}
	public void setComplaintDetails(String complaintDetails) {
		this.complaintDetails = complaintDetails;
	}
	public Date getComplaintSentDate() {
		return complaintSentDate;
	}
	public void setComplaintSentDate(Date complaintSentDate) {
		this.complaintSentDate = complaintSentDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

}
