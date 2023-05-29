package src.main.java.com.cms.app.dao;

import java.util.ArrayList;
import java.util.List;

import src.main.java.com.cms.app.entities.Assign;

public class AssignDaoImp implements AssignDao{

	List<Assign> assigns;
	
	
	public AssignDaoImp(Assign assign) {
		assigns = new ArrayList<Assign>();
		assigns.add(assign);
	}
	
	@Override
	public List<Assign> getAllAssignment() {
		return null;
	}

	@Override
	public Assign getAssign(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateAssign(Assign assign) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAssign(Assign assign) {
		// TODO Auto-generated method stub
		
	}

}
