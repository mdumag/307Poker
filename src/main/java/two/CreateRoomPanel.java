package two;

import javax.swing.*;
import java.awt.*;

public class CreateRoomPanel extends JPanel {
	
	public CreateRoomPanel(CreateRoomNanny createRoomNanny) {
		setLayout(new GridLayout(4, 1));
		// Title
		JLabel title = new JLabel("Create new Room");
		title.setHorizontalAlignment(SwingConstants.CENTER);
		add(title);
		// Name Label
		JPanel box1 = new JPanel();
		box1.setLayout(new GridLayout(1, 2));
		JLabel nameLabel = new JLabel("Name:");
		JTextField nameField = new JTextField("CSC307");
		box1.add(nameLabel);
		box1.add(nameField);
		add(box1);
		// Mode Label
		JPanel box2 = new JPanel();
		box2.setLayout(new GridLayout(1, 2));
		JLabel modeLabel = new JLabel("Mode:");
		box2.add(modeLabel);
		String[] options = {"Scrum", "Fibonacci", "Sequential", "Hours", "T-shirt", "Custom deck"};
		JComboBox<String> comboBox = new JComboBox<>(options);
		box2.add(comboBox);
		add(box2);
		// Create Button
		JPanel box3 = new JPanel();
		JButton createButton = new JButton("Create");
		box3.add(createButton);
		add(box3);
		// Add action listeners
		createButton.addActionListener(
			e -> createRoomNanny.createRoom(nameField.getText(),
				(String) comboBox.getSelectedItem())
		);
	}
	
}