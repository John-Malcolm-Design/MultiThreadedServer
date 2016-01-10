package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import user_mgmt.RegularUser;

/*
 * Name: ServerRunner.java
 * Author: John Malcolm Anderson
 * Description: Main server side class.
 * Handles spawning thread for new requests on socket.
 */
public class ServerRunner {

	public static void main(String[] args) throws IOException {
		ServerSocket mainSocket = new ServerSocket(2004,10);
		FileParser.initUsers();

		int id = 0;
		while (true) {
			Socket clientSocket = mainSocket.accept();
			ClientServiceThread cliThread = new ClientServiceThread(clientSocket, id++);
			cliThread.start();
		}

	}
}
