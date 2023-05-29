package src.main.java.com.cms.app.client;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

import java.util.Map;


import src.main.java.com.cms.app.entities.User;
import src.main.java.com.cms.app.views.ChatView;
import src.main.java.com.cms.app.views.LoginView;


/**
 * objective of Client
 * create socket connection with server
 * to send client request and get server response
 */
public class Client {
	final String HOST = "127.0.0.1";
	final int PORT = 1234;
	private ObjectOutputStream out = null;
	private ObjectInputStream in = null;
	private Socket server = null;
	ChatView chatView = null;
	LoginView login = null;
	static Map<String, User> list = null;
	
	public Client() {
		connectToServer();
	}
	
	
	public boolean pingServer() {
		if(server!=null) {
			return true;
		}
		return false;
	}

	
	private void connectToServer() {
		
		try{
			server = new Socket(HOST, PORT);
			out = new ObjectOutputStream(server.getOutputStream());
			in = new ObjectInputStream(server.getInputStream());
		}catch(IOException e) {
			
			System.out.println("Error occured when connecting to server: "+ e.getMessage() );
		}

	}
	
	
	public void sendCommand(String action) {
		try {
			out.writeObject(action);
		} catch (IOException e) {
			System.out.println("Error occured sending command: "+ action +" : "+ e.getMessage() );
		}
	}
	
	public void sendUserCredentials(String id, String password) {
		try {
			out.writeObject(id);
			out.flush();
			out.writeObject(password);
			out.flush();
		} catch (IOException e) {
			System.out.println("Error occured sending user credentials: "+ e.getMessage() );
		}
	}
	
	public Object authenticationResponse() {
		Object result = "none";
		try {
			result = (Object) in.readObject();
		} catch (ClassNotFoundException e) {
			System.out.println("Error object not found: "+ e.getMessage() );
		} catch (IOException e) {
			System.out.println("Error occured when parsing results:  "+ e.getMessage() );
		}
		
		return result;
	}
	

	
	public void closeConnection() {
		try {
			out.writeObject("logout");
			server.close();
			System.out.println("Connection closed");
		}catch(IOException e) {
			System.out.println("Error while closing connection: "+e.getMessage());
		}
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, User> getActiveUsers() {
		
		try {
			list = (Map<String, User>) in.readObject();
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Error occured: "+ e.getMessage() );
		}
		return list;
		
	}
	

}




