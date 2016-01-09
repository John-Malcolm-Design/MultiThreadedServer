package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/*
 * Name: Connector.java
 * Author: John Malcolm Anderson
 * Description: Spawns off thread for handling messages sent from server.
 * Connection method sets up initial connection to sever via sockets.
 */
public class Connector extends Thread {

	// Member Variables
	private static Socket requestSocket;
	private static ObjectOutputStream out; 
	private static ObjectInputStream in;
	private static String message="";
	private static String ipaddress;
	private static Scanner stdin;

	// Getters & Setters
	public static ObjectOutputStream getOut() {
		return out;
	}

	public static void setOut(ObjectOutputStream out) {
		Connector.out = out;
	}

	public static ObjectInputStream getIn() {
		return in;
	}

	public static void setIn(ObjectInputStream in) {
		Connector.in = in;
	}

	// Initial server connection method
	public void connect(){
		// Keyboard object initialization
		stdin = new Scanner(System.in);
		try{
			//1. Creating a socket to connect to the server
			System.out.println("Please Enter the servers IP Address");
			ipaddress = stdin.next();

			// Socket object initialization
			requestSocket = new Socket(ipaddress, 2004);
			System.out.println("Connected to "+ipaddress+" in port 2004");

			//2. Get Input and Output streams
			out = new ObjectOutputStream(requestSocket.getOutputStream()); // Output Stream

			// Writes out any data in stream "flushes"
			out.flush();

			in = new ObjectInputStream(requestSocket.getInputStream()); // Input Stream
		}
		catch(UnknownHostException unknownHost){
			System.err.println("You are trying to connect to an unknown host!");
		}
		catch(IOException ioException){
			ioException.printStackTrace();
		}
	}

	// Thread method for listening and printing messages to the screen
	public void run()
	{
		do{
			try
			{
				System.out.println((String)in.readObject());
			}
			catch(ClassNotFoundException classnot){
				System.err.println("Data received in unknown format");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}while(!message.equals("bye"));
	}

	// Send message function
	void sendMessage(String msg)
	{
		try{
			out.writeObject(msg);
			out.flush();
			// System.out.println("client: " + msg); DEBUG
		}
		catch(IOException ioException){
			ioException.printStackTrace();
		}
	}
}
