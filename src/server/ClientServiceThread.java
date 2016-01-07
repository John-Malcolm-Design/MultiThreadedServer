package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientServiceThread extends Thread{
	private static Socket clientSocket;
	private static String message;
	private static int clientID = -1;
	private static boolean running = true;
	private static ObjectOutputStream out;
	private static ObjectInputStream in;

	ClientServiceThread(Socket s, int i) {
		clientSocket = s;
		clientID = i;
	}

	void sendMessage(String msg)
	{
		try{
			out.writeObject(msg);
			out.flush();
			System.out.println("client> " + msg);
		}
		catch(IOException ioException){
			ioException.printStackTrace();
		}
	}
	public void run() {
		System.out.println("Accepted Client : ID - " + clientID + " : Address - "
				+ clientSocket.getInetAddress().getHostName());
		try 
		{
			out = new ObjectOutputStream(clientSocket.getOutputStream());
			out.flush();
			in = new ObjectInputStream(clientSocket.getInputStream());
			System.out.println("Accepted Client : ID - " + clientID + " : Address - "
					+ clientSocket.getInetAddress().getHostName());

			sendMessage("Connection successful");
			do{
				try
				{

					System.out.println("client>"+clientID+"  "+ message);
					//if (message.equals("bye"))
					sendMessage("server got the following: "+message);
					message = (String)in.readObject();
				}
				catch(ClassNotFoundException classnot){
					System.err.println("Data received in unknown format");
				}

			}while(!message.equals("bye"));

			System.out.println("Ending Client : ID - " + clientID + " : Address - "
					+ clientSocket.getInetAddress().getHostName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}