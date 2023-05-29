package src.main.java.com.cms.app.dao;

import java.util.List;

import src.main.java.com.cms.app.entities.Assign;

public interface AssignDao {
	public List<Assign> getAllAssignment();
	public Assign getAssign(int id);
	public void updateAssign(Assign assign);
	public void deleteAssign(Assign assign);

}
