package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

import entities.User;
import frames.MainFrame;

public class Client {
	final String HOST = "127.0.0.1";
	final int PORT = 1234;
	ObjectOutputStream out = null;
	ObjectInputStream in = null;
	Socket server = null;
	MainFrame mainFrame = null;
	
	
	public Client(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
		start();
	}
	
	/**
	 * create connection to server on port 1234
	 * initialize object streams
	 */
	private void start() {
		try{
			server = new Socket(HOST, PORT);
			out = new ObjectOutputStream(server.getOutputStream());
			in = new ObjectInputStream(server.getInputStream());
		}catch(IOException e) {
			System.out.println("Error occured when connecting to server: "+ e.getMessage() );
			System.exit(1);
		}
	}
	
	
	public void sendCommand(String action) {
		try {
			out.writeObject(action);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sendUserCredentials(String id, String password) {
		try {
			out.writeObject(id);
			out.flush();
			out.writeObject(password);
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Object authenticationResponse() {
		Object result = null;
		try {
			result = (Object) in.readObject();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
	

}
