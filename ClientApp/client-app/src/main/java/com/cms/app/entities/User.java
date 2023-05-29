package src.main.java.com.cms.app.entities;

/**
 * 
 * @author mckoy00
 *
 */

public class User implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private String id;
	private String username;
	private String password;
	private String role;
	
	public User() {}
	
	public User(String id, String name, String password, String role) {
		this.id = id;
		this.username = name;
		this.password = password;
		this.role = role;
	}
	
	
	public User(String id, String name, String role) {
		this.id = id;
		this.username = name;
		this.role = role;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", role=" + role + "]";
	}
	
	
	

}
