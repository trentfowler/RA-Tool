import java.awt.*;
import javax.swing.*;
//import javax.swing.event.DocumentEvent;
//import javax.swing.event.DocumentListener;

public class Email extends JPanel {
	
	private static final long serialVersionUID = 1L;
	static JTextArea jta = new JTextArea();
	
	public Email() {
		this.setLayout(new BorderLayout());
		this.add(jta, BorderLayout.CENTER);
		
//		JPanel email_desc_pane = new JPanel();
		
		/*
		 * 		steps_jta = new JTextArea(15, 15);
		steps_jta.setText("• ");
//		steps_jta.setFont(jlabel_font);
		steps_jta.setLineWrap(true);
		steps_jta.addMouseListener(new ContextMenuMouseListener());
		steps_jta.getDocument().addDocumentListener(new DocumentListener() {
			@Override public void changedUpdate(DocumentEvent arg0) { }
			@Override public void insertUpdate(DocumentEvent arg0) {
				if (steps_jta.getText().endsWith("\n")) {
					Runnable append_bullet = new Runnable() {
						@Override public void run() {
							steps_jta.append("• ");
						}
					};
					SwingUtilities.invokeLater(append_bullet);
				}
			}
			@Override public void removeUpdate(DocumentEvent arg0) { }
			});
		steps_jta.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		BubbleScrollPane steps_jsp = new BubbleScrollPane(steps_jta);
		steps_jsp.setVerticalScrollBarPolicy(BubbleScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		steps_jsp.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		steps_jp = new JPanel();
		steps_jp.setBackground(RA.bleach(Color.BLUE, (float)0.85));
		steps_jp.setBorder(BorderFactory.createEmptyBorder(0,0,5,0));
		steps_jp.setLayout(new BorderLayout());
		JLabel steps_jl = new JLabel("Steps Taken:");
		steps_jl.setFont(jlabel_font.deriveFont(Font.BOLD));
		steps_jp.add(steps_jl, BorderLayout.WEST);
		steps_taken_panel = new CustomPanel();
		steps_taken_panel.setBackground(RA.bleach(Color.BLUE, (float)0.85));
		steps_taken_panel.setLayout(new BorderLayout());
		steps_taken_panel.add(steps_jp, BorderLayout.NORTH);
		steps_taken_panel.add(steps_jsp, BorderLayout.CENTER);

		 */
	}
}
