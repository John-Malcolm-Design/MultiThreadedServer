package server;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import user_mgmt.RegularUser;

/*
 * Name: FileParser.java
 * Author: John Malcolm Anderson
 * Description: Handles File IO methods
 */
public class FileParser {
	public static ArrayList<RegularUser> users = new ArrayList<RegularUser>();

	// Reads CSV file with user details into an arraylist for authentication
	public static void initUsers() {
		BufferedReader fileReader = null;
		try{
			String line = "";

			//Create the file reader
			fileReader = new BufferedReader(new FileReader("C:\\Users\\johnmalcolm\\Desktop\\users.csv"));

			//Read the file line by line starting from the second line
			while ((line = fileReader.readLine()) != null) {
				String[] details = line.split(",");
				if (details.length > 0) {
					RegularUser user = new RegularUser(details[0], details[1], Integer.parseInt(details[2]));
					// System.out.println(user.toString()); // FOR DEBUG
					users.add(user);
				}
			}
		}
		catch (Exception e) {
			System.out.println("Error in CsvFileReader !!!");
			e.printStackTrace();
		} finally {
			try {
				fileReader.close();
			} catch (IOException ex) {
				System.out.println("Error while closing fileReader !!!");
				ex.printStackTrace();
			}
		}

	}
}
