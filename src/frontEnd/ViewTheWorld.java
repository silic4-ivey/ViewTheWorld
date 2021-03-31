package frontEnd;

import frontEnd.loginSubsystem.LoginSessionManager;

public class ViewTheWorld {
	public static void main(String[] args) {
		LoginSessionManager login = LoginSessionManager.getInstance();
		login.launchLogin();
	}
}
