package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerRunner {
	public static void main(String[] args) throws IOException {
		ServerSocket mainSocket = new ServerSocket(2004,10);
	    int id = 0;
	    while (true) {
	      Socket clientSocket = mainSocket.accept();
	      ClientServiceThread cliThread = new ClientServiceThread(clientSocket, id++);
	      cliThread.start();
	    }
	}
}
