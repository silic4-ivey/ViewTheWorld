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

public class LoginSessionManager extends JFrame implements ActionListener, KeyListener {
	private static final long serialVersionUID = 1L;

	private JTextField userTextField = new JTextField();
	private JPasswordField passwordField = new JPasswordField();

	private JButton submitButton = new JButton("Submit!");
	private JCheckBox showPassword = new JCheckBox("Show Password");

	private VerificationServer vServer = new VerificationServer();
	private static LoginSessionManager instance;

	public static LoginSessionManager getInstance() {
		if (instance == null)
			instance = new LoginSessionManager();

		return instance;
	}

	private LoginSessionManager() {
		super("Login");
		setLayout(null);
		setSize(280, 200);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension screenDim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(screenDim.width / 2 - getSize().width / 2, screenDim.height / 2 - getSize().height / 2);

		JLabel userLabel = new JLabel("Username:");
		userLabel.setBounds(10, 15, 100, 30);
		JLabel passwordLabel = new JLabel("Password:");
		passwordLabel.setBounds(10, 60, 100, 30);

		userTextField.setBounds(100, 15, 150, 30);
		passwordField.setBounds(100, 60, 150, 30);
		showPassword.setBounds(97, 90, 150, 30);
		submitButton.setBounds(80, 120, 100, 30);

		add(userLabel);
		add(passwordLabel);
		add(userTextField);
		add(passwordField);
		add(showPassword);
		add(submitButton);

		submitButton.addActionListener(this);
		showPassword.addActionListener(this);
		userTextField.addKeyListener(this);
		passwordField.addKeyListener(this);
	}

	public void launchLogin() {
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// Coding Part of submit button
		if (e.getSource() == submitButton) {
			login();
		}

		// Coding Part of showPassword JCheckBox
		if (e.getSource() == showPassword) {
			if (showPassword.isSelected()) {
				passwordField.setEchoChar((char) 0);
			} else {
				passwordField.setEchoChar('*');
			}
		}
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			login();
		}
	}

	private void login() {
		String user = userTextField.getText();
		String pass = passwordField.getText();
		if (vServer.verify(user, pass)) {
			this.dispose();
			MainUI frame = MainUI.getInstance();
//            MainUI.launchUI();
			frame.launchUI();
		} else {
			JOptionPane.showMessageDialog(this, "Invalid Username or Password");
			userTextField.setText("");
			passwordField.setText("");
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyReleased(KeyEvent e) {}
}
