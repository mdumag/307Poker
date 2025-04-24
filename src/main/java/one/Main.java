package one;

import javax.swing.*;

public class Main extends JFrame {
	
	public Main() {
		LoginNanny joinRoomNanny = new LoginNanny(this);
		LoginPanel joinRoomPanel = new LoginPanel(joinRoomNanny);
		add (joinRoomPanel);
	}
	
	public static void main(String[] args) {
		Main main = new Main();
		main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		main.setSize(400, 400);
		main.setLocationRelativeTo(null);
		main.setVisible(true);
	}
	
}