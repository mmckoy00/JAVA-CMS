package src.main.java.com.cms.app.dao;

import java.util.List;

import src.main.java.com.cms.app.entities.User;

public interface UserDao {
	public List<User>getAllUsers();
	public User getUser(int id);
	public void updateUser(User user);
	public void deleteUser(User user);
}
