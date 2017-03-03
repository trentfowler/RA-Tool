import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class CustomPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	public CustomPanel() {
		this.setBackground(Color.WHITE);
		this.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(Color.BLACK),
				BorderFactory.createEmptyBorder(5,5,5,5)
			));
	}
}
