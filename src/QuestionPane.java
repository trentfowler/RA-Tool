
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class QuestionPane extends JPanel {
	
	private static final long serialVersionUID = 1L;

	static final int YES_ANSWER = 0;
	static final int NO_ANSWER = 1;
//	static final int EXCLUDE_ANSWER = 2;
	private JComboBox<String> yes_no_jcb = new JComboBox<String>(
			new String[]{"Yes", "No"});
	private int default_answer;
	private JCheckBox jcb = new JCheckBox();
	private boolean default_checked_status = true;
	
	public QuestionPane(String s, int selected_answer, boolean checked) {
		default_checked_status = checked;
		jcb.setSelected(default_checked_status);
		if (jcb.isSelected()) {
			yes_no_jcb.setBackground(Color.WHITE);
			bleach(Color.BLUE, (float)0.85);
		} else {
			yes_no_jcb.setEnabled(false);
			bleach(Color.BLACK, (float)0.85);
		}
		jcb.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) {
				if (jcb.isSelected()) {
					yes_no_jcb.setEnabled(true);
					yes_no_jcb.setBackground(Color.WHITE);
					bleach(Color.BLUE, (float)0.85);
				} else {
					yes_no_jcb.setEnabled(false);
					JComboBox<Object> tmp = new JComboBox<Object>();
					tmp.setEnabled(false);
					yes_no_jcb.setBackground(tmp.getBackground());
					bleach(Color.BLACK, (float)0.85);
				}
			}
		});
		this.default_answer = selected_answer;
		this.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(Color.BLACK),
				BorderFactory.createEmptyBorder(5,5,5,5)
		));
		
		if (selected_answer == QuestionPane.YES_ANSWER) {
			this.yes_no_jcb.setSelectedIndex(0);
		}
		
		if (selected_answer == QuestionPane.NO_ANSWER) {
			this.yes_no_jcb.setSelectedIndex(1);
		}
		
//		if (selected_answer == QuestionPane.EXCLUDE_ANSWER) {
//			this.yes_no_jcb.setSelectedIndex(2);
//		}
		
		this.setLayout(new BorderLayout());
		this.add(new JLabel(s + "  "), BorderLayout.WEST);
		this.add(yes_no_jcb, BorderLayout.CENTER);
		this.add(jcb, BorderLayout.EAST);
	}
		
	public int getAnswer() {
		int choice = this.yes_no_jcb.getSelectedIndex();
		if (choice == 0) 
			return QuestionPane.YES_ANSWER;
		return QuestionPane.NO_ANSWER;
//		if (choice == 1)
//			return QuestionPane.NO_ANSWER;
//		return QuestionPane.EXCLUDE_ANSWER;
	}
	
	public boolean isChecked() {
		return jcb.isSelected();
	}
	
	public void restoreDefault() {
		if (default_answer == QuestionPane.YES_ANSWER)
			 this.yes_no_jcb.setSelectedIndex(QuestionPane.YES_ANSWER);
		else if (default_answer == QuestionPane.NO_ANSWER)
			 this.yes_no_jcb.setSelectedIndex(QuestionPane.NO_ANSWER);
//		else if (default_answer == QuestionPane.EXCLUDE_ANSWER)
//			 this.yes_no_jcb.setSelectedIndex(QuestionPane.EXCLUDE_ANSWER);
		if (this.default_checked_status == true) {
			yes_no_jcb.setEnabled(true);
			yes_no_jcb.setBackground(Color.WHITE);
			jcb.setSelected(true);
			bleach(Color.BLUE, (float)0.85);
		} else {
			yes_no_jcb.setEnabled(false);
			JComboBox<Object> tmp = new JComboBox<Object>();
			tmp.setEnabled(false);
			yes_no_jcb.setBackground(tmp.getBackground());
			jcb.setSelected(false);
			bleach(Color.BLACK, (float)0.85);
		}
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
}
