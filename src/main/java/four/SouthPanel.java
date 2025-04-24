package four;

import javax.swing.*;
import java.awt.*;

/**
 * Stories organized in tabs.
 * The first tab contains the active stories, and the second one contains the completed stories.
 *
 * @author javiergs
 */
public class SouthPanel extends JPanel {
	
	public SouthPanel() {
		setBackground(new Color(161, 190, 239));
		setLayout(new BorderLayout());
		JTabbedPane storyTabs = new JTabbedPane();
		
		// examples
		JTextArea activeStories = new JTextArea("As a player, I want to move Pac-man\n" +
			"As a player, I want to see an animated Pac-Man character");
		
		storyTabs.addTab("Active Stories", new JScrollPane(activeStories));
		storyTabs.addTab("Completed Stories", new JScrollPane(new JTextArea("")));
		
		add(storyTabs, BorderLayout.CENTER);
	}

}


