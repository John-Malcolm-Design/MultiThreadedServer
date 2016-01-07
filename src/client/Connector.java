package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Connector {
	
	// Member Variables
	private static Socket requestSocket;
	private static ObjectOutputStream out; 
	private static ObjectInputStream in;
	private static String message="";
	private static String ipaddress;
	private static Scanner stdin;
	
	public Connector() {
		super();
	}
	
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

	// Run method for thread
	void run()
	{
		// Keyboard object initialization
		stdin = new Scanner(System.in);
		try{
			//1. Creating a socket to connect to the server
			System.out.println("Please Enter your IP Address");
			ipaddress = stdin.next();
			
			// Socket object initialization
			requestSocket = new Socket(ipaddress, 2004);
			System.out.println("Connected to "+ipaddress+" in port 2004");
			
			//2. Get Input and Output streams
			out = new ObjectOutputStream(requestSocket.getOutputStream()); // Output Stream
			
			// Writes out any data in stream "flushes"
			out.flush();
			
			in = new ObjectInputStream(requestSocket.getInputStream()); // Input Stream
			System.out.println("Hello");
		
		}
		catch(UnknownHostException unknownHost){
			System.err.println("You are trying to connect to an unknown host!");
		}
		catch(IOException ioException){
			ioException.printStackTrace();
		}
	}
}
