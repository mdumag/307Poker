package two;

import one.Blackboard;
import one.Main;
import three.StoriesNanny;
import three.StoriesPanel;

public class CreateRoomNanny {
	
	private Main main;
	
	public CreateRoomNanny(Main main) {
		this.main = main;
	}
	
	public void createRoom(String name, String selectedItem) {
		Blackboard.addCurrentRoom(name);
		Blackboard.addCurrentMode(selectedItem);
		System.out.println(" Creating room..." + name + ", mode: " + selectedItem);
		
		main.setTitle(name + " - Stories");
		StoriesNanny storiesNanny = new StoriesNanny(main);
		StoriesPanel storiesPanel = new StoriesPanel(storiesNanny);
		main.setContentPane(storiesPanel);
		main.setSize(500, 500);
		main.revalidate();
		main.repaint();
		
	}
	
}
