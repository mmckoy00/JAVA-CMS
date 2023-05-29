package src.main.java.com.cms.app;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import src.main.java.com.cms.app.utils.Db;
import src.main.java.com.cms.app.entities.User;

/*
 * objective of ClientRequestHandler 
 * handle client action request which includes
 * -to logout
 * -to login
 * -to view profile
 * -to log a complaint
 * -to send messages
 */

public class ClientRequestsHandler implements Runnable{
	
	private Socket clientConnection = null; 
	private ObjectOutputStream serverOutput = null; 
	private ObjectInputStream clientInput = null;
	private User user = null;
	private static int  clientIdentity = 0;
	private Db db = null;
	static Map<String, User> onlineUsers = new HashMap<String, User>();
	private Connection con = null;
	
	
	
		public ClientRequestsHandler(Socket socket) throws Exception{
			this.clientConnection = socket;
			this.serverOutput = new ObjectOutputStream(clientConnection.getOutputStream());
			this.clientInput = new ObjectInputStream(clientConnection.getInputStream());
			this.db = new Db();
			this.con = db.getConnection();
			ClientRequestsHandler.clientIdentity++; 
			
		}
		
		@Override
		public void run() {
			String actionRequested = "";
			
			try {
				
				System.out.println("Client: #"+ clientIdentity+" is connected.");
					try {
						while(!actionRequested.equalsIgnoreCase("LOGOUT")) 
						{	
							
							//USE readObject to read client string request
							actionRequested = (String) clientInput.readObject();
							
							if(actionRequested.equalsIgnoreCase("LOGIN")) {
	
								String id = (String) clientInput.readObject();
								String password = (String) clientInput.readObject();
								
								
								user = authenticateUser(id, password);
								
								if(user==null){
									serverOutput.writeObject("none");	
								}else{
									
									if(!onlineUsers.containsKey(id) ) {
										onlineUsers.put(id, user);
										serverOutput.writeObject(user);
									}else {
										serverOutput.writeObject("is logged in");	
									}
							    }
							 }
							
							
							if(actionRequested.equalsIgnoreCase("Get Users")) {
								System.out.println("Your in get users");
								serverOutput.writeObject(onlineUsers);
							}
	
		
								
						}    
						
					onlineUsers.remove(String.valueOf(clientIdentity));
					System.out.println("client: #" + clientIdentity + " has logged out.");
					
					}catch (EOFException  e1) {
						System.out.println("Client "+clientIdentity+" has disconnected.");
					}
					catch (SocketException e1) {
						
						removeOfflineUser(clientIdentity);
						System.out.println("Client: #"+clientIdentity+" has unexpectedly disconnected.");
						
					}
						
					
			}catch (IOException | ClassNotFoundException e ) {
				System.out.println("Error occur: "+e.getMessage());
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
			try (java.sql.PreparedStatement stmt = con.prepareStatement("SELECT * FROM demo.user_tbl WHERE id = ? and password = ?"))
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
	
		    if (user!= null) {
	
		    	if(!onlineUsers.containsKey(id)) {
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
			   onlineUsers.remove(String.valueOf(clientCount));
			}
		}
	
	
		
	}