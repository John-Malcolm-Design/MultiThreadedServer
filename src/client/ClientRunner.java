package client;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.ArrayList;
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

	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {

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

		// Waits to get user choice (e.g - ls, cd, mkdir, etc...)
		String userChoice;

		// Loops until user chooses exit 
		do {

			// Get user input
			userChoice = console.nextLine();

			// Switch through user input
			switch (userChoice) {

			// List Files
			case "ls":
				server.sendMessage("ls");
				break;

				// Change Directory 
			case "cd":
				server.sendMessage("cd");
				System.out.println("Enter directory name:");
				String dir = console.nextLine();
				server.sendMessage(dir);
				break;

				// Make a new directory
			case "mkdir":
				server.sendMessage("mkdir");
				System.out.println("Enter directory name:");
				String newDir = console.nextLine();
				server.sendMessage(newDir);
				break;

				// Help
			case "help":
				server.sendMessage("help");
				break;

				// Download file
			case "download":
				server.sendMessage("download");
				System.out.println("Please enter the name of the file you would like to download");
				String file = console.nextLine();
				server.sendMessage(file);
				PrintWriter writer = new PrintWriter("downloaddedfile.txt", "UTF-8");
				String lineInput = "";
				do {
					lineInput = (String) server.getIn().readObject();
					if (lineInput.equals("EOF999")) {
						break;
					}else{
						writer.println(lineInput);
					}
				} while (!lineInput.equals("EOF999"));
				writer.close();
				server.start();
				break;

				// Upload a file
			case "upload":
				server.sendMessage("upload");
				System.out.println("Please enter full file URI");
				String fileURI = console.nextLine();
				String fileName = fileURI.substring(fileURI.lastIndexOf('\\') + 1); // FOR WINDOWS - change for mac
				server.sendMessage(fileName);
				BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileURI)));

				String line;
				while ((line = br.readLine()) != null) {
					server.sendMessage(line);

				}
				server.sendMessage("EOF999");
				break;

				// Exit server
			case "exit":
				server.sendMessage("exit");
				break;

			default:
				System.out.println("That is not a valid command - try again");
				break;
			}
		} while (userChoice != "exit");
	}

}
