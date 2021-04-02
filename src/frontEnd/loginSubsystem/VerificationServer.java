package frontEnd.loginSubsystem;

import java.io.File;  
import java.io.FileNotFoundException; 
import java.util.Scanner;
import java.util.HashMap;

/**
 * Contains all valid credentials. Used to verify User's credentials.
 * @author Joud El-Shawa
 */
public class VerificationServer {
	
	/**
	 * All valid credentials for the system. All keys are usernames and values are the corresponding passwords.
	 */
	private HashMap<String,String> credentials = new HashMap<String,String>();
	
	/**
	 * Constructor. Scans credentialsDatabase file and stores all credentials in the hashmap.
	 */
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
	
	/**
	 * Verifies that received credentials are valid for the system. Checks if username exists, and if it does,
	 * if the passwords match.
	 * @param user is the username to be found
	 * @param pass is the password to be checked
	 * @return true if valid credentials, false otherwise.
	 */
	public Boolean verify(String user, String pass) {
		if (credentials.containsKey(user)) {
			if (pass.equals(credentials.get(user)))
				return true;
		}
		return false;
	}
}
