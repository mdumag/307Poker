package one;

import javax.swing.*;
import java.awt.*;

public class LoginPanel extends JPanel {
	
	public LoginPanel(LoginNanny joinRoomNanny) {
		// Title
		JLabel titleLabel = new JLabel("Let's start!");
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		JLabel subtitleLabel = new JLabel("Join the room:");
		subtitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		JLabel accountLabel = new JLabel("Already have an account?");
		accountLabel.setHorizontalAlignment(SwingConstants.CENTER);
		// Text Field
		JTextField nameField = new JTextField("Enter your name");
		// Button
		JButton enterButton = new JButton("Enter");
		JButton loginButton = new JButton("Login");
		// Add components
		setLayout(new GridLayout(6, 1));
		add(titleLabel);
		add(subtitleLabel);
		add(nameField);
		add(enterButton);
		add(accountLabel);
		add(loginButton);
		// Add action listeners
		enterButton.addActionListener(e -> joinRoomNanny.enterRoom(nameField.getText()));
		loginButton.addActionListener(e -> joinRoomNanny.login(nameField.getText()));
	}
	
}