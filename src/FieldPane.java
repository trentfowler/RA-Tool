
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class FieldPane extends CustomPanel {
	
	private static final long serialVersionUID = 1L;
		
	private JTextField jtf = new JTextField();
	private JLabel jl = new JLabel();
	private JCheckBox jcb = new JCheckBox();
	private boolean default_checked_status = true;
	
	public FieldPane(String s, boolean checked) {
		
//		jtf.addMouseListener(new ContextMenuMouseListener());
		jtf.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(Color.BLACK),
				BorderFactory.createEmptyBorder(5,5,5,5)
			));
		
		default_checked_status = checked;
		jcb.setSelected(default_checked_status);
		if (jcb.isSelected()) {
			bleach(Color.BLUE, (float)0.85);
		}
		
		else {
			jtf.setEditable(false);
			bleach(Color.BLACK, (float)0.85);
		}
		
		jcb.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) {
				if (jcb.isSelected()) {
					jtf.setEditable(true);
					bleach(Color.BLUE, (float)0.85);
				} else {
					jtf.setEditable(false);
					bleach(Color.BLACK, (float)0.85);
				}
			}
			
		});
		jl.setText(s + " ");
		Font jlabel_font = new JLabel("").getFont();
		jl.setFont(jlabel_font.deriveFont(Font.BOLD));
		
		this.setLayout(new BorderLayout());
		this.add(jl, BorderLayout.WEST);
		this.add(jtf, BorderLayout.CENTER);
		this.add(jcb, BorderLayout.EAST);
	}
	
	private void background(Color color) {
		this.setBackground(color);
		jcb.setBackground(color);
	}
	
	public void bleach(Color color, float amount) {
		int red = (int) ((color.getRed() * (1 - amount) / 255 + amount) * 255);
	    int green = (int) ((color.getGreen() * (1 - amount) / 255 + amount) * 255);
	    int blue = (int) ((color.getBlue() * (1 - amount) / 255 + amount) * 255);
	    Color c = new Color(red, green, blue);
	    background(c);
	}
	
	public String get_text() {
		return jtf.getText();
	}
	
	public void set_text(String text) {
		jtf.setText(text);
	}
	
	public void reset() {
		jtf.setText("");
		jcb.setSelected(default_checked_status);
		if (jcb.isSelected()) {
			jtf.setEditable(true);
			bleach(Color.BLUE, (float)0.85);
		} else {
			jtf.setEditable(false);
			bleach(Color.BLACK, (float)0.85);
		}
	}
	
	public boolean isChecked() {
		return jcb.isSelected();
	}
	
	public void setSelected(boolean sel) {
		jcb.setSelected(sel);
		if (jcb.isSelected()) {
			jtf.setEditable(true);
			bleach(Color.BLUE, (float)0.85);
		} else {
			jtf.setEditable(false);
			bleach(Color.BLACK, (float)0.85);
		}
	}
}
