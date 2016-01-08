package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import user_mgmt.User;

/*
 * Name: ClientServiceThread.java
 * Author: John Malcolm Anderson
 * Description: Spawns off thread for servicing the current session.
 * User authenticaton method invokations for each session are made here.
 * 
 */

public class ClientServiceThread extends Thread{
	
	// Member Variables
	private static Socket clientSocket;
	private static String message;
	private static int clientID = -1;
	private static boolean running = true;
	private static ObjectOutputStream out;
	private static ObjectInputStream in;
	private static User user;

	// Constructor takes socket and ID
	ClientServiceThread(Socket s, int i) {
		clientSocket = s;
		clientID = i;
	}
	
	// Run method for thread handles user authentication
	public void run() {
		try 
		{
			out = new ObjectOutputStream(clientSocket.getOutputStream());
			out.flush();
			in = new ObjectInputStream(clientSocket.getInputStream());
			System.out.println("Accepted Client : ID - " + clientID + " : Address - "
					+ clientSocket.getInetAddress().getHostName());

			// sendMessage("Connection successful");
			user = (User)in.readObject();
			user.authenticate();
			
			if (user.isLoggedIn() == false) {
				sendMessage("Incorrect username or password");
			} else {
				sendMessage("You are now logged in. Welcome " + user.getUsername());
			}
//			do{
//				try
//				{
//
//					System.out.println("client>"+clientID+"  "+ message);
//					//if (message.equals("bye"))
//					sendMessage("server got the following: "+message);
//					message = (String)in.readObject();
//				}
//				catch(ClassNotFoundException classnot){
//					System.err.println("Data received in unknown format");
//				}
//
//			}while(!message.equals("bye"));

			System.out.println("Ending Client : ID - " + clientID + " : Address - "
					+ clientSocket.getInetAddress().getHostName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// Send message function
	void sendMessage(String msg)
	{
		try{
			out.writeObject(msg);
			out.flush();
			System.out.println("client: " + msg);
		}
		catch(IOException ioException){
			ioException.printStackTrace();
		}
	}
}