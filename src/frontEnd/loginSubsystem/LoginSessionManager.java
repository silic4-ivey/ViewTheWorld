package frontEnd.loginSubsystem;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import frontEnd.MainUI;

/**
 * This class is in charge of displaying the Login Panel, collecting the User's credentials, and launching the MainUI.
 * @author Joud El-Shawa
 */
public class LoginSessionManager extends JFrame implements ActionListener, KeyListener {
	private static final long serialVersionUID = 1L;

	/**
	 * The element in the Login Panel that the User inputs their username into.
	 */
	private JTextField userTextField = new JTextField();
	
	/**
	 * The element in the Login Panel that the User inputs their password into.
	 */
	private JPasswordField passwordField = new JPasswordField();

	/**
	 * The button in the Login Panel that the User presses to initiate the verification of their credentials.
	 */
	private JButton submitButton = new JButton("Submit!");
	
	/**
	 * THe checkbox in the Login Panel that allows the User to see their password.
	 */
	private JCheckBox showPassword = new JCheckBox("Show Password");

	/**
	 * The server that will be used to verify the User's credentials.
	 */
	private VerificationServer vServer = new VerificationServer();
	
	/**
	 * Instance of the class because we are using the Singleton design pattern and only want one object.
	 */
	private static LoginSessionManager instance;

	/**
	 * Method that other classes can call to get the instance of this class. If an instance does not already
	 * exist, instantiates the instance by calling the constructor.
	 * @return the instance of the class
	 */
	public static LoginSessionManager getInstance() {
		if (instance == null)
			instance = new LoginSessionManager();

		return instance;
	}

	/**
	 * Constructor. Sets up the Login Panel and its aspects. 
	 * Adds actionListeners/keyListeners to the different elements of the panel.
	 */
	private LoginSessionManager() {
		super("Login");	// setting title
		setLayout(null);
		setSize(280, 200);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension screenDim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(screenDim.width / 2 - getSize().width / 2, screenDim.height / 2 - getSize().height / 2);

		// setting up the different aspects of the panel, including their bounds 
		JLabel userLabel = new JLabel("Username:");
		userLabel.setBounds(10, 15, 100, 30);
		JLabel passwordLabel = new JLabel("Password:");
		passwordLabel.setBounds(10, 60, 100, 30);

		userTextField.setBounds(100, 15, 150, 30);
		passwordField.setBounds(100, 60, 150, 30);
		showPassword.setBounds(97, 90, 150, 30);
		submitButton.setBounds(80, 120, 100, 30);

		// adding the different labels, fields, and buttons to the panel
		add(userLabel);
		add(passwordLabel);
		add(userTextField);
		add(passwordField);
		add(showPassword);
		add(submitButton);

		// adding action and key listeners to the different elements
		submitButton.addActionListener(this);
		showPassword.addActionListener(this);
		userTextField.addKeyListener(this);
		passwordField.addKeyListener(this);
	}

	/**
	 * Sets the Login Panel to visible to launch the login process.
	 */
	public void launchLogin() {
		setVisible(true);
	}
	
	/**
	 * Handles actions performed by the User such as pressing Submit or checking the showPassowrd box. 
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		
		// If User pressed submit button
		if (e.getSource() == submitButton) {
			login();
		}

		// If User pressed the showpassword checkbox
		if (e.getSource() == showPassword) {
			if (showPassword.isSelected()) 
				passwordField.setEchoChar((char) 0);	// displays password
			else 
				passwordField.setEchoChar('*');		// if User unselected checkbox, hide password again
		}
	}
	
	/**
	 * Handles if User presses Enter instead of pressing the Submit button.
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			login();
		}
	}

	/**
	 * Handles login process by calling the verify method of the VerificationServer.
	 * If verify method returns true, launches MainUI. Otherwise, displays error.
	 */
	private void login() {
		String user = userTextField.getText();
		String pass = passwordField.getText();
		
		// verifying credentials
		if (vServer.verify(user, pass)) {	// Proxy Design Pattern!
			this.dispose();
			MainUI frame = MainUI.getInstance();
			frame.launchUI();	// launches UI if valid credentials
		} 
		else {
			JOptionPane.showMessageDialog(this, "Invalid Username or Password");	// displays error if invalid credentials
			userTextField.setText("");	// empties fields so User can input their credentials again
			passwordField.setText("");
		}
	}

	/**
	 * Needed for implementing KeyListener. Not used.
	 */
	@Override
	public void keyTyped(KeyEvent e) {}
	
	/**
	 * Needed for implementing KeyListener. Not used.
	 */
	@Override
	public void keyReleased(KeyEvent e) {}
}
