package user_mgmt;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/*
 * Name: User.java <<Interface>>
 * Author: John Malcolm Anderson
 * Description: Specifies user types.
 * Provides generics for users of a scalable user managment system.
 */
public interface User {
	
	public String getUsername();
	
	public void setUsername(String username);
	
	public int getAccessLevel();
	
	public void setAccessLevel(int accessLevel);
	
	public String getPassword();
	
	public void setPassword(String password);
	
	public abstract void setLoggedIn(boolean loggedIn);

	public abstract boolean isLoggedIn();
		
	public void authenticate();

	public void login(ObjectOutputStream out, ObjectInputStream in);

	
}
