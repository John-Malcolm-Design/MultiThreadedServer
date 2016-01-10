package user_mgmt;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import server.FileParser;
import server.ServerRunner;

/*
 * Name: RegularUser.java
 * Author: John Malcolm Anderson
 * Description: Implementation of User for user accounts on server.
 * Also implements serializable for writing to object stream.
 */
public class RegularUser implements User, Serializable {

	// Member Variables
	private static final long serialVersionUID = 1L;
	private String username;
	private String password;
	private String cwd;
	private int accessLevel;
	private boolean loggedIn = false;

	// Constructor
	public RegularUser(String username, String password, int accessLevel) {
		super();
		this.username = username;
		this.cwd = username;
		this.password = password;
		this.accessLevel = accessLevel;
	}

	// Getters & Setters
	@Override
	public String getUsername() {
		return this.username;
	}

	@Override
	public void setUsername(String username) {
		this.username = username;

	}

	@Override
	public int getAccessLevel() {
		return this.accessLevel;
	}

	@Override
	public void setAccessLevel(int accessLevel) {
		this.accessLevel = accessLevel;

	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public void setPassword(String password) {
		this.password = password;

	}

	@Override
	public boolean isLoggedIn() {
		return loggedIn;
	}


	@Override
	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}


	@Override
	public String getCwd() {
		return cwd;
	}


	@Override
	public void setCwd(String cwd) {
		this.cwd = cwd;
	}


	// To String ovveride
	@Override
	public String toString() {
		return "RegularUser [username=" + username + ", password=" + password + ", accessLevel=" + accessLevel + "]";
	}

	// Client side login method
	@Override
	public void login(ObjectOutputStream out, ObjectInputStream in) {
		//3: Communicating with the server
		try{
			out.writeObject(this);
			out.flush();
			// System.out.println("client: " + this.toString());
		}
		catch(IOException ioException){
			ioException.printStackTrace();
		}		
	}

	// Server side authentication method
	@Override
	public void authenticate() {
		int i = 0;
		while (i < FileParser.users.size()) {
			if (this.username.equals(FileParser.users.get(i).getUsername()) && this.password.equals(FileParser.users.get(i).getPassword())) {
				this.loggedIn = true;
				i = FileParser.users.size();
			} 
			i++;
		}
	}

}
