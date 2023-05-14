package server;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import dbconnection.Db;
import entities.User;

public class Server {

	
	//main 
	public static void main(String[] args) {
		 
		ServerSocket server = null;
		int clientIdentity = 0;
		final int PORT = 1234;

		try {
			server = new ServerSocket(PORT);
			System.out.println("Server Listening...");
			
			while(true) 
			{
				Socket clientConnection = server.accept();
				clientIdentity++;
				System.out.println("Client: #"+ clientIdentity+" is connected.");
				ClientRequestHandler cli = new ClientRequestHandler(clientConnection, clientIdentity);
				new Thread(cli).start();
			}
			
			
		} catch (IOException e) {
			System.out.println("Error starting server: "+e.getMessage());
		}finally {
			try {
				if(server!=null) {
					server.close();
				}
			}catch(IOException e) {
				System.out.println("Error closing server: "+e.getMessage());
			}
		}
		
		
		
	}
}


//handles user requests
class ClientRequestHandler implements Runnable{
	
	Socket clientConnection = null;
	ObjectOutputStream serverOutput = null;
	ObjectInputStream clientInput = null;
	static Db db = null;
	User user = null;
	int  clientIdentity = 0;
	static Map<Integer, String> activeUsers = new HashMap<Integer, String>();
	
	
	
	public ClientRequestHandler(Socket socket, int clientCount) {
		this.clientConnection = socket;
		clientIdentity = clientCount; 
		db = new Db();
	}
	
	@Override
	public void run() {
		String actionRequested = "";
		try {
			serverOutput = new ObjectOutputStream(clientConnection.getOutputStream());
			clientInput = new ObjectInputStream(clientConnection.getInputStream());
			
				try {
				
					
					while(!actionRequested.equalsIgnoreCase("LOGOUT")) 
					{	
						
						actionRequested = (String) clientInput.readObject();
						
						if(actionRequested.equalsIgnoreCase("LOGIN")) {

							String id = (String) clientInput.readObject();
							String password = (String) clientInput.readObject();
							
							user = authenticateUser(id, password);
							
							if(user!=null) {
								int testId= Integer.parseInt(user.getId());
								if(!activeUsers.containsKey(testId)) {
									activeUsers.put(clientIdentity, user.getUsername());
									serverOutput.writeObject(user);
								}else {
									serverOutput.writeObject("is logged in");	
								}
							}else {
								serverOutput.writeObject(user);	
							}
						}
						
						if(actionRequested.equalsIgnoreCase("Profile")) {	
						}
						
						if(actionRequested.equalsIgnoreCase("Complaint")) {	
						}
						
						if(actionRequested.equalsIgnoreCase("Send Msg")) {	
							String receiver = (String) clientInput.readObject();
							String msg = (String) clientInput.readObject();
						}
							
					}
					
				activeUsers.remove(clientIdentity);
				System.out.println("client: #" + clientIdentity + " has logged out.");
				
				}catch (EOFException  e1) {
					System.out.println("Client "+clientIdentity+" has disconnected.");
				}
				catch (SocketException e1) {
					
					removeOfflineUser(clientIdentity);
					System.out.println("Client: #"+clientIdentity+" has unexpectedly disconnected.");
					
				}
					
				
		}catch (IOException | ClassNotFoundException e ) {
			e.printStackTrace();
		}finally {
            try {
                if (serverOutput != null) {
                    serverOutput.close();
                }
                if(clientInput != null) {
                    clientInput.close();
                    clientConnection.close();
                }
                
                db.closeConnection();
            }
            catch (IOException e) {
                e.printStackTrace();
            } catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("Error occured when attempting to close DB: "+e.getMessage());
				e.printStackTrace();
			}
        }
		
		}
	
	private User authenticateUser(String id, String password) {
		User user =  null;
		try (java.sql.Connection con = db.getConnection();
			   java.sql.PreparedStatement stmt = con.prepareStatement("SELECT * FROM demo.user_tbl WHERE id = ? and password = ?"))
		  {
		      stmt.setString(1, id);
		      stmt.setString(2, password);
		      try (java.sql.ResultSet rs = stmt.executeQuery()) {
		        if (rs.next()) {
		             user = new User(id, rs.getString("username"), password, rs.getString("role"));
		          }  
		      }
		  } catch (SQLException e) {
		      System.err.println("Error authenticating user: " + e.getMessage());
		      e.printStackTrace();
		  }

		    if (user != null) {
		    	int testId = Integer.parseInt(user.getId());
		    	if(!activeUsers.containsKey(testId)) {
		    		clientIdentity = Integer.parseInt(user.getId());
		    		System.out.println("Client: #" + clientIdentity + " - " + user.getUsername() + " authenticated successfully @ " + new Date(System.currentTimeMillis()));
		    	}else {
		    		System.out.println("Client: #" + clientIdentity +" Error: is already logged in  @ " + new Date(System.currentTimeMillis()));
		    	}
		    } else {
		        System.out.println("Client: #" + clientIdentity+ " authentication unsuccessful @ " + new Date(System.currentTimeMillis()));
		    }
		    
		    return user;
		
	}
	
	private void removeOfflineUser(int clientCount) {
		if(user!=null) {
			activeUsers.remove(clientIdentity);
		}
	}
	
	private void sendComplaint() {
		
	}
	
		
	}
	
