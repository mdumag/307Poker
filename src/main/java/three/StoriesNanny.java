package three;

import four.DashboardNanny;
import four.DashboardPanel;
import one.Blackboard;
import one.Main;

/**
 * Controller responsible for managing the stories and their interactions with the user interface.
 *
 * @author javiergs
 */
public class StoriesNanny {
	
	private Main main;
	
	public StoriesNanny(Main main) {
		this.main = main;
	}
	
	public void saveAndAddNew(String text) {
		System.out.println(text);
		// add functionality to save the story and add a new one
	}
	
	public void saveAndClose(String text) {
		System.out.println(text);
		Blackboard.addStory(text);
		switchGUI();
	}
	
	public void importStories() {
		System.out.println("importing stories...");
	}
	
	public void cancel() {
		System.out.println("canceling...");
	}
	
	private void switchGUI() {
		main.setTitle("dashboard");
		DashboardNanny dashboardNanny = new DashboardNanny(main);
		DashboardPanel dashboardPanel = new DashboardPanel(dashboardNanny);
		main.setContentPane(dashboardPanel);
		main.setSize(800, 600);
		main.setLocationRelativeTo(null);
		main.revalidate();
		main.repaint();
	}

}

