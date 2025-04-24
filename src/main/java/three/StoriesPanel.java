package three;

import javax.swing.*;
import java.awt.*;

public class StoriesPanel extends JPanel {
	
	public StoriesPanel(StoriesNanny storiesNanny) {
		setLayout(new BorderLayout());
		// Title
		JLabel titleLabel = new JLabel("Create New Story", SwingConstants.CENTER);
		titleLabel.setFont(new Font("Arial", Font.PLAIN, 20));
		add(titleLabel, BorderLayout.NORTH);
		// Story Text Area
		JTextArea storyTextArea = new JTextArea("Put your stories text here. Each line contains new story.");
		JScrollPane scrollPane = new JScrollPane(storyTextArea);
		add(scrollPane, BorderLayout.CENTER);
		// Buttons Panel
		JPanel buttonPanel = new JPanel(new GridLayout(2, 2));
		JButton saveAddNewButton = new JButton("Save & Add New");
		JButton saveCloseButton = new JButton("Save & Close");
		JButton importButton = new JButton("Import");
		JButton cancelButton = new JButton("Cancel");
		buttonPanel.add(saveAddNewButton);
		buttonPanel.add(saveCloseButton);
		buttonPanel.add(importButton);
		buttonPanel.add(cancelButton);
		add(buttonPanel, BorderLayout.SOUTH);
		// Add action listeners
		saveAddNewButton.addActionListener(e -> storiesNanny.saveAndAddNew(storyTextArea.getText()));
		saveCloseButton.addActionListener(e -> storiesNanny.saveAndClose(storyTextArea.getText()));
		importButton.addActionListener(e -> storiesNanny.importStories());
		cancelButton.addActionListener(e -> storiesNanny.cancel());
	}
	
}