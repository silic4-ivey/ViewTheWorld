package frontEnd.loginSubsystem;

import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files
import java.util.HashMap;

public class VerificationServer {
	private HashMap<String,String> credentials = new HashMap<String,String>();
	
	
	public VerificationServer() {
		try {	
		      File credentialsDB = new File("credentialsDatabase.txt");
		      Scanner scan = new Scanner(credentialsDB);
		      while (scan.hasNextLine()) {
		        String data = scan.nextLine();
		        String[] creds = data.split(",");
		  
		        String user = creds[0].trim();
		        String pass = creds[1].trim();

		        credentials.put(user,pass);
		      }
		      scan.close();
		    } 
		catch (FileNotFoundException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		}
	}
	
	public Boolean verify(String user, String pass) {
		if (credentials.containsKey(user)) {
			if (pass.equals(credentials.get(user)))
				return true;
		}
		return false;
	}
}
