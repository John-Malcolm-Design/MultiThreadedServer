package client;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

import user_mgmt.RegularUser;
import user_mgmt.User;

/*
 * Name: ClientRunner.java
 * Author: John Malcolm Anderson
 * Description: This class contains the main method for the client
 * This method handles retrieving the username and password from the user.
 * Connecting to the server, spawning off a thread to retrieve messages from the sever
 * and initializing the user object for the session.
 */
public class ClientRunner {
	
	public static void main(String[] args) {
		
		// Keyboard object
		Scanner console = new Scanner(System.in);
		
		// Connects to server
		Connector server = new Connector();
		server.connect();		
		
		// Retrieve username and password
		System.out.println("Please enter your username: ");
		String usernameInput = console.nextLine();
		
		System.out.println("Please enter your password: ");
		String passwordInput = console.nextLine();
		
		// Call client side login function for user input
		// Authentication is called from the server side and will send a message 
		// if logged in was succesfull/failed.
		User john = new RegularUser(usernameInput, passwordInput, 1);
		john.login(server.getOut(), server.getIn());
		
		// Starts thread listening for messages
		server.start();
	}

}
