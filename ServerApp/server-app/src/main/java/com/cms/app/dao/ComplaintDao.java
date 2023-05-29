package src.main.java.com.cms.app.dao;

import java.util.List;

import src.main.java.com.cms.app.entities.Complaint;

public interface ComplaintDao {
	public List<Complaint> getAllAssignment();
	public Complaint getAssign(int id);
	public void updateAssign(Complaint assign);
	public void deleteAssign(Complaint assign);
}
