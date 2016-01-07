package user_mgmt;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class RegularUser implements User, Serializable {

	private static final long serialVersionUID = 1L;
	private String username;
	private String password;
	private int accessLevel;

	public RegularUser(String username, String password, int accessLevel) {
		super();
		this.username = username;
		this.password = password;
		this.accessLevel = accessLevel;
	}

	@Override
	public void getUsername() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setUsername() {
		// TODO Auto-generated method stub

	}

	@Override
	public void getAccessLevel() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setAccessLevel() {
		// TODO Auto-generated method stub

	}

	@Override
	public void getPassword() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setPassword() {
		// TODO Auto-generated method stub

	}

	public void login(ObjectOutputStream out, ObjectInputStream in){
		//3: Communicating with the server
		try{
			out.writeObject(this);
			out.flush();
			System.out.println("client: " + this.toString());
		}
		catch(IOException ioException){
			ioException.printStackTrace();
		}
	}

	@Override
	public String toString() {
		return "RegularUser [username=" + username + ", password=" + password + ", accessLevel=" + accessLevel + "]";
	}
	
}
