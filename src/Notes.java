import java.awt.*;
import javax.swing.*;

public class Notes extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	static JTextArea notes_jta;
	private JPanel notes_jp;
	private JPanel notes_panel;
	
	public Notes() {
		notes_jta = new JTextArea(15, 15);
		notes_jta.setLineWrap(true);
		notes_jta.setWrapStyleWord(true);
//		notes_jta.addMouseListener(new ContextMenuMouseListener());
		notes_jta.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		BubbleScrollPane notes_jsp = new BubbleScrollPane(notes_jta);
		notes_jsp.setVerticalScrollBarPolicy(BubbleScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		notes_jsp.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		notes_jp = new JPanel();
		notes_jp.setBackground(RA.bleach(Color.BLUE, (float)0.85));
		notes_jp.setBorder(BorderFactory.createEmptyBorder(0,0,5,0));
		notes_jp.setLayout(new BorderLayout());
		JLabel notes_jl = new JLabel("Notes:");
		Font jlabel_font = new JLabel("").getFont();
		notes_jl.setFont(jlabel_font.deriveFont(Font.BOLD));
		notes_jp.add(notes_jl, BorderLayout.WEST);
		notes_panel = new CustomPanel();
		notes_panel.setBackground(RA.bleach(Color.BLUE, (float)0.85));
		notes_panel.setLayout(new BorderLayout());
		notes_panel.add(notes_jp, BorderLayout.NORTH);
		notes_panel.add(notes_jsp, BorderLayout.CENTER);
		this.setLayout(new BorderLayout());
		this.add(notes_panel, BorderLayout.CENTER);
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
	}
}