package one;

import two.CreateRoomNanny;
import two.CreateRoomPanel;

public class LoginNanny {
	
	private Main main;
	
	public LoginNanny(Main main) {
		this.main = main;
	}
	
	public void enterRoom(String name) {
		System.out.println(name + " Entering room...");
		// maybe validate name
		Blackboard.addName(name);
		main.setTitle(name + " - Room");
		CreateRoomNanny createRoomNanny = new CreateRoomNanny(main);
		CreateRoomPanel createRoomPanel = new CreateRoomPanel(createRoomNanny);
		main.setContentPane(createRoomPanel);
		main.setSize(500, 500);
		main.revalidate();
		main.repaint();
	}
	
	public void login(String name) {
		System.out.println(name + " Logging in...");
		// maybe validate name
		Blackboard.addName(name);
		main.setTitle(name + " - Room");
		CreateRoomNanny createRoomNanny = new CreateRoomNanny(main);
		CreateRoomPanel createRoomPanel = new CreateRoomPanel(createRoomNanny);
		main.setContentPane(createRoomPanel);
		main.setSize(500, 500);
		main.revalidate();
		main.repaint();
	}
}
