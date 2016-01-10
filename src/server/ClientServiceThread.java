package server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
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
	private static String root = "C:\\Users\\johnmalcolm\\Desktop\\root\\";

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
				sendMessage("mkdir = make a new directory");
				sendMessage("cd = change directory");
				sendMessage("download = download file");
				sendMessage("upload = upload file into folder");
				sendMessage("help = lists commands");
				sendMessage(user.getCwd() + ">");
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

	private void upload() throws ClassNotFoundException, IOException {
		String name = (String) in.readObject();
		PrintWriter writer = new PrintWriter(root + user.getCwd() + "\\" + name, "UTF-8");
		String line = "init";
		do {
			line = (String) in.readObject();
			if (line.equals("EOF999")) {
				break;
			}else{
				writer.println(line);
			}
		} while (!line.equals("EOF999"));
		writer.close();
		sendMessage(user.getCwd() + ">");
	}

	private void download() throws ClassNotFoundException, IOException {
		String file = (String)in.readObject();
		
		File f = new File(root + user.getCwd());
		ArrayList<String> names = new ArrayList<String>(Arrays.asList(f.list()));
		boolean flag = false;
		sendMessage("bye");
		for (String name: names) {
			if (file.equals(name) ) {
				BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(root + user.getCwd() + "\\"+name)));
				String line;
				while ((line = br.readLine()) != null) {
					sendMessage(line);
					
				}
				
				sendMessage("EOF999");
				sendMessage(user.getCwd() + ">");
				flag = true;
				break;
				
			}else{
				flag = false;
			}
			
		}
		if (flag == false) {
			sendMessage("File not found - please try again");
		}
		sendMessage(user.getCwd() + ">");

	}

	private void help() {
		sendMessage("Hey " + user.getUsername() + ".");
		sendMessage("These are the available commands:");
		sendMessage("ls = list all files and folders");
		sendMessage("mkdir = make a new directory");
		sendMessage("cd = change directory");
		sendMessage("download = download file");
		sendMessage("upload = upload file into folder");
		sendMessage("help = lists commands");
		sendMessage(user.getCwd() + ">");
	}

	private void mkdir() throws ClassNotFoundException, IOException {
		String newDir = (String)in.readObject();
		File nDir = new File(root + user.getCwd() + "\\" + newDir);
		boolean successful = nDir.mkdir();
		if (successful){
			sendMessage("directory was created successfully");
			sendMessage(user.getCwd() + ">");
		}else{
			sendMessage("failed trying to create the directory");
			sendMessage(user.getCwd() + ">");
		}
	}

	private void cd() throws ClassNotFoundException, IOException {
		File f = new File("C:\\Users\\johnmalcolm\\Desktop\\root\\" + user.getCwd());
		ArrayList<String> names = new ArrayList<String>(Arrays.asList(f.list()));
		String dir = (String)in.readObject();
		if (names.contains(dir)) {
			String newCWD = user.getCwd() + "\\" + dir;
			user.setCwd(newCWD);
			sendMessage(newCWD + ">");
		}else{
			sendMessage("That directory does not exist");
		}
	}

	public void ls(){
		File f = new File(root + user.getCwd());
		ArrayList<String> names = new ArrayList<String>(Arrays.asList(f.list()));
		for (String name: names) {
			sendMessage(name);
		}
		sendMessage(user.getCwd() + ">");
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
}