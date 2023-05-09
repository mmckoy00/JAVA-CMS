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
import java.util.Set;

import dbconnection.Db;
import entities.User;

public class Server {

	public static void main(String[] args) {
		
		//variable declaration 
		ServerSocket server = null;
		int clientCount = 0;
		final int PORT = 1234;
		 

		try {
			server = new ServerSocket(PORT);
			System.out.println("Server Listening...");
			
			while(true) {
				
				Socket client = server.accept();
				clientCount++;
				System.out.println("Client: #"+clientCount+" is connected.");
				
				ClientHandler cli = new ClientHandler(client, clientCount);
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



class ClientHandler implements Runnable{
	Socket client = null;
	ObjectOutputStream out = null;
	ObjectInputStream in = null;
	int clientCount = 0;
	java.util.Map<String, Integer> clients = new java.util.HashMap<String, Integer>();
	
	
	public ClientHandler(Socket socket, int clientCount) {
		this.client = socket;
		this.clientCount = clientCount;
	}
	
	@Override
	public void run() {
		String action = "";
		try {
			out = new ObjectOutputStream(client.getOutputStream());
			in = new ObjectInputStream(client.getInputStream());
			
			
				try {
					
					while(!action.equalsIgnoreCase("logout")) {	
						
						action = (String) in.readObject();
						
						if(action.equalsIgnoreCase("login")) {
							User user = null;
							String id = (String) in.readObject();
							String password = (String) in.readObject();
							
							user = authenticateUser(id, password);
							
							out.writeObject(user);	
						}
						
						
						
						if(action.equalsIgnoreCase("Edit Profile")) {
							
						}
						
						
						if(action.equalsIgnoreCase("Send Complaint")) {
							
						}
						
						if(action.equalsIgnoreCase("Send Msg")) {
							
						}
							
						
					}
				
				System.out.println("client: #" + clientCount + " has logged out.");	
				}catch (EOFException  e1) {
					System.out.println("Client "+clientCount+" has disconnected.");
				}
				catch (SocketException e1) {
					// TODO Auto-generated catch block
					System.out.println("Client: #"+clientCount+" has unexpectedly disconnected.");
				}
					
				
		}catch (IOException | ClassNotFoundException e ) {
			e.printStackTrace();
		}finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                    client.close();
                }
                
                Db.closeConnection();
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
		  try (java.sql.Connection con = Db.getConnection();
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
		    	clients.put(user.getUsername(), clientCount);
		        System.out.println("Client #" + clientCount + " - " + user.getUsername() + " authenticated successfully @ " + new Date(System.currentTimeMillis()));
		    } else {
		        System.out.println("Client #" + clientCount + " authentication unsuccessful @ " + new Date(System.currentTimeMillis()));
		    }
		    
		    return user;
		
	}
	
	private void sendComplaint() {
		
	}
	
		
	}
	
