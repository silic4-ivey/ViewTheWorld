package frontEnd;

import frontEnd.loginSubsystem.LoginSessionManager;

/**
 * This is the main class of the system. Launches the Login Panel so User can start the login process.
 * @author Joud El-Shawa - jelshawa - 251107864
 */
public class ViewTheWorld {
	
	/**
	 * Main method. Gets the instance of the LoginSessionManager class and calls its launchLogin method.
	 * @param args usual parameter for main methods.
	 */
	public static void main(String[] args) {
		LoginSessionManager login = LoginSessionManager.getInstance();
		login.launchLogin();
	}
}
