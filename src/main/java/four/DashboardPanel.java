package four;

import javax.swing.*;
import java.awt.*;

public class DashboardPanel extends JPanel {
	
	public DashboardPanel(DashboardNanny dashboardNanny) {
		setLayout(new BorderLayout());
		add(new CardsPanel(), BorderLayout.CENTER);
		add(new SouthPanel(), BorderLayout.SOUTH);
		add(new WestPanel(), BorderLayout.EAST);
	}
	
}