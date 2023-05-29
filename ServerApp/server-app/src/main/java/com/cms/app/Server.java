package src.main.java.com.cms.app;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


/*
 * objective of Server
 * listen and accept incoming client connection 
 * start new thread after client connects
 */
public class Server 
{
    public static void main( String[] args )
    {
		int clientIdentity = 0;
		final int PORT = 1234;
		List<ClientRequestsHandler> clientThreads = new ArrayList<ClientRequestsHandler>();

		try(ServerSocket server = new ServerSocket(PORT);) {
			System.out.println("Server Listening...");
			
			while(true) 
			{
				Socket clientConnection = server.accept();
				ClientRequestsHandler cli = new ClientRequestsHandler(clientConnection);
				clientThreads.add(cli);
				new Thread(cli).start();
			}
			
			
		} catch (IOException e) {
			System.out.println("Error starting server: "+e.getMessage());
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
    
    
}

