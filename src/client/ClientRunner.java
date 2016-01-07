package client;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import user_mgmt.RegularUser;

public class ClientRunner {
	
	public static void main(String[] args) {
		Connector client = new Connector();
		client.run();		
		RegularUser john = new RegularUser("johnmalcolm", "123Candle", 1);
		john.login(client.getOut(), client.getIn());
	}

}
