package src.main.java.com.cms.app.dao;

import java.util.ArrayList;
import java.util.List;

import src.main.java.com.cms.app.entities.Complaint;

public class ComplaintDaoImp implements ComplaintDao{
	
	List<Complaint>complaints;
	
	public ComplaintDaoImp(Complaint complaint) {
		complaints = new ArrayList<Complaint>();
		complaints.add(complaint);
	}
	
	@Override
	public List<Complaint> getAllAssignment() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Complaint getAssign(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateAssign(Complaint assign) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAssign(Complaint assign) {
		// TODO Auto-generated method stub
		
	}

}
