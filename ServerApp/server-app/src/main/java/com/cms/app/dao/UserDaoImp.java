package src.main.java.com.cms.app.dao;

import java.util.ArrayList;
import java.util.List;

import src.main.java.com.cms.app.entities.User;

/***
 * 
 * @author mmckoy00
 *
 */

public class UserDaoImp implements UserDao {

	List<User> users;
	
	public UserDaoImp(User user) {
		users = new ArrayList<User>();
		users.add(user);
	}
	
	@Override
	public List<User> getAllUsers() {
		return users;
	}

	@Override
	public User getUser(int id) {
		return null;
	}

	@Override
	public void updateUser(User user) {

	
	}

	@Override
	public void deleteUser(User user) {
		
		
	}

}
