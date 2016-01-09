package server;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

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
				sendMessage("You are now logged in. Welcome " + user.getUsername() + ".");
				sendMessage("Commands:");
				sendMessage("ls = list all files and folders");
				sendMessage("mkdir [folder name] = make a new directory");
				sendMessage("cd [folder name] = change directory");
				sendMessage("download [filename] = download file");
				sendMessage("upload [filepath + name] = upload file into folder");
				sendMessage("help = lists commands");
			}
			do{
				try
				{
					message = (String)in.readObject();
					System.out.println("client "+clientID+": "+ message);
					switch (message) {
					case "ls":
						ls();
						break;
						
					case "cd":
						cd();
						break;
						
					case "mkdir":
						mkdir();
						break;
						
					case "help":
						help();
						break;
						
					case "download":
						download();
						break;
						
					case "upload":
						upload();
						break;

					default:
						break;
					}
				}
				catch(ClassNotFoundException classnot){
					System.err.println("Data received in unknown format");
				}

			}while(!message.equals("bye"));

			System.out.println("Ending Client : ID - " + clientID + " : Address - "
					+ clientSocket.getInetAddress().getHostName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void upload() {
		// TODO Auto-generated method stub
		
	}

	private void download() {
		// TODO Auto-generated method stub
		
	}

	private void help() {
		// TODO Auto-generated method stub
		
	}

	private void mkdir() {
		// TODO Auto-generated method stub
		
	}

	private void cd() {
		// TODO Auto-generated method stub
		
	}

	// Send message function
	void sendMessage(String msg)
	{
		try{
			out.writeObject(msg);
			out.flush();
		}
		catch(IOException ioException){
			ioException.printStackTrace();
		}
	}

	public void ls(){
		System.out.println(user.getUsername());
		File f = new File("C:\\Users\\johnmalcolm\\Desktop\\root\\" + user.getUsername());
		ArrayList<String> names = new ArrayList<String>(Arrays.asList(f.list()));
		for (String name: names) {
			sendMessage(name);
		}
	}
}