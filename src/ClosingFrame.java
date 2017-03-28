
import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.event.*;
import javax.swing.*;

public class ClosingFrame {
	//TODO new tag of exchanged system
	static JFrame frame;
	static JTextArea jta = new JTextArea(10, 15);
	QuestionPane did_customer_approve_closure_pane = new QuestionPane("Has customer approved closure?", QuestionPane.YES_ANSWER, true);
	QuestionPane seven_days_pane = new QuestionPane("Was customer notified about 7 day re-engagement period?", QuestionPane.YES_ANSWER, true);
	QuestionPane sr_title_pane = new QuestionPane("Does SR title adhere to policy?", QuestionPane.YES_ANSWER, true);
	QuestionPane sr_profiled_pane = new QuestionPane("Case correctly profiled?", QuestionPane.YES_ANSWER, true);
	QuestionPane clca_pane = new QuestionPane("CLCA Submitted?", QuestionPane.NO_ANSWER, true);
	QuestionPane sit_pane = new QuestionPane("New SIT created or existing updated?", QuestionPane.NO_ANSWER, true);
	QuestionPane okb_pane = new QuestionPane("New OKB submitted?", QuestionPane.NO_ANSWER, true);
	FieldPane how_resolved_pane = new FieldPane("How was the issue resolved?", true);
	CustomPanel service_history_cp = new CustomPanel();
	JPanel service_history_jp = new JPanel();
	static JTextArea service_history_jta = new JTextArea(1,10);
	JCheckBox service_history_jcb = new JCheckBox();
	CustomPanel how_resolved_cp = new CustomPanel();
	JPanel how_resolved_jp = new JPanel();
	static JTextArea how_resolved_jta = new JTextArea(2,10);
	JCheckBox how_resolved_jcb = new JCheckBox();
	private boolean visible = false;
	
	public ClosingFrame() {
		
		/* how resolved */
		how_resolved_cp.setBackground(RA.bleach(Color.BLUE, (float)0.85));
		how_resolved_jp.setBackground(RA.bleach(Color.BLUE, (float)0.85));
		how_resolved_jp.setBorder(BorderFactory.createEmptyBorder(0,0,5,0));
		how_resolved_jp.setLayout(new BorderLayout());
		JLabel how_resolved_jl = new JLabel("How was the issue resolved?");
		Font jlabel_font = new JLabel("").getFont();
		how_resolved_jl.setFont(jlabel_font.deriveFont(Font.BOLD));
		how_resolved_jp.add(how_resolved_jl, BorderLayout.WEST);
		how_resolved_jta.setText(""); /* blank */
		how_resolved_jta.setLineWrap(true);
		how_resolved_jta.setWrapStyleWord(true);
		how_resolved_jta.setEditable(true);
		how_resolved_jta.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		BubbleScrollPane how_resolved_jsp = new BubbleScrollPane(how_resolved_jta);
		how_resolved_jsp.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		how_resolved_jcb.setBackground(RA.bleach(Color.BLUE, (float)0.85));
		how_resolved_jcb.setSelected(true);
		how_resolved_jcb.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent arg0) {
				if (how_resolved_jcb.isSelected()) {
					how_resolved_jta.setEditable(true);
					how_resolved_jta.setBackground(Color.WHITE);
					how_resolved_cp.setBackground(RA.bleach(Color.BLUE, (float)0.85));
					how_resolved_jp.setBackground(RA.bleach(Color.BLUE, (float)0.85));
					how_resolved_jcb.setBackground(RA.bleach(Color.BLUE, (float)0.85));
				} else {
					how_resolved_jta.setEditable(false);
					JTextField tmp_jtf = new JTextField();
					tmp_jtf.setEditable(false);
					how_resolved_jta.setBackground(tmp_jtf.getBackground());
					how_resolved_cp.setBackground(RA.bleach(Color.BLACK, (float)0.85));
					how_resolved_jp.setBackground(RA.bleach(Color.BLACK, (float)0.85));
					how_resolved_jcb.setBackground(RA.bleach(Color.BLACK, (float)0.85));
				}
			}
		});
		how_resolved_cp.setLayout(new BorderLayout());
		how_resolved_cp.add(how_resolved_jp, BorderLayout.NORTH);
		how_resolved_cp.add(how_resolved_jsp, BorderLayout.CENTER);
		how_resolved_cp.add(how_resolved_jcb, BorderLayout.EAST);
		/* how resolved */
		
		/* service history */
		service_history_cp.setBackground(RA.bleach(Color.BLUE, (float)0.85));
		service_history_jp.setBackground(RA.bleach(Color.BLUE, (float)0.85));
		service_history_jp.setBorder(BorderFactory.createEmptyBorder(0,0,5,0));
		service_history_jp.setLayout(new BorderLayout());
		JLabel recent_history_jl = new JLabel("New Service History:");
		recent_history_jl.setFont(jlabel_font.deriveFont(Font.BOLD));
		service_history_jp.add(recent_history_jl, BorderLayout.WEST);
		service_history_jta.setText("No service history added");
		service_history_jta.setLineWrap(true);
		service_history_jta.setWrapStyleWord(true);
		service_history_jta.setEditable(true);
		service_history_jta.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		BubbleScrollPane recent_history_jsp = new BubbleScrollPane(service_history_jta);
		recent_history_jsp.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		service_history_jcb.setBackground(RA.bleach(Color.BLUE, (float)0.85));
		service_history_jcb.setSelected(true);
		service_history_jcb.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent arg0) {
				if (service_history_jcb.isSelected()) {
					service_history_jta.setEditable(true);
					service_history_jta.setBackground(Color.WHITE);
					service_history_cp.setBackground(RA.bleach(Color.BLUE, (float)0.85));
					service_history_jp.setBackground(RA.bleach(Color.BLUE, (float)0.85));
					service_history_jcb.setBackground(RA.bleach(Color.BLUE, (float)0.85));
				} else {
					service_history_jta.setEditable(false);
					JTextField tmp_jtf = new JTextField();
					tmp_jtf.setEditable(false);
					service_history_jta.setBackground(tmp_jtf.getBackground());
					service_history_cp.setBackground(RA.bleach(Color.BLACK, (float)0.85));
					service_history_jp.setBackground(RA.bleach(Color.BLACK, (float)0.85));
					service_history_jcb.setBackground(RA.bleach(Color.BLACK, (float)0.85));
				}
			}
		});
		service_history_cp.setLayout(new BorderLayout());
		service_history_cp.add(service_history_jp, BorderLayout.NORTH);
		service_history_cp.add(recent_history_jsp, BorderLayout.CENTER);
		service_history_cp.add(service_history_jcb, BorderLayout.EAST);
		/* service history */
		
		/* top pane with fields */
		JPanel top_pane = new JPanel();
		top_pane.setLayout(new BoxLayout(top_pane, BoxLayout.Y_AXIS));
		top_pane.add(how_resolved_cp);
		top_pane.add(service_history_cp);
		top_pane.add(did_customer_approve_closure_pane);
		top_pane.add(seven_days_pane);
		top_pane.add(sr_profiled_pane);
		top_pane.add(sr_title_pane);
		top_pane.add(clca_pane);
		top_pane.add(sit_pane);
		top_pane.add(okb_pane);
		/* middle pane with text area */
		jta.setLineWrap(true);
		jta.setWrapStyleWord(true);
		//jta.addMouseListener(new ContextMenuMouseListener());
		jta.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		BubbleScrollPane jsp = new BubbleScrollPane(jta);
		jsp.setVerticalScrollBarPolicy(BubbleScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		jsp.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		JPanel textAreaPanel = new JPanel();
		textAreaPanel.setBackground(RA.bleach(Color.BLUE, (float)0.85));
		textAreaPanel.setBorder(BorderFactory.createEmptyBorder(0,0,5,0));
		textAreaPanel.setLayout(new BorderLayout());
		JLabel jl = new JLabel("Closing Email:");
		jl.setFont(jlabel_font.deriveFont(Font.BOLD));
		textAreaPanel.add(jl, BorderLayout.WEST);
		JPanel middle_pane = new CustomPanel();
		middle_pane.setBackground(RA.bleach(Color.BLUE, (float)0.85));
		middle_pane.setLayout(new BorderLayout());
		middle_pane.add(textAreaPanel, BorderLayout.NORTH);
		middle_pane.add(jsp, BorderLayout.CENTER);
		/* bottom panel with buttons */
		JPanel button_pane = new JPanel();
		JButton generate_button = new JButton("Generate formatted output");
		generate_button.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) {
				StringBuilder sb = new StringBuilder();
				sb.append("**Summary **\n"
						+ "SR Closing Checklist\n"
						+ "-----------------------------\n"
						+ "** Details **\n");	
				if (how_resolved_jcb.isSelected()) {
					sb.append("\t• How was the issue resolved? " 
							+ how_resolved_jta.getText() 
							+ "\n");
				}
				if (service_history_jcb.isSelected()) {
					sb.append("\t• New Service History: " 
							+ service_history_jta.getText() 
							+ "\n");
				}
				if (did_customer_approve_closure_pane.isChecked()) {
					sb.append("\t• Has the customer approved closure? ");
					if (did_customer_approve_closure_pane.getAnswer() == QuestionPane.YES_ANSWER)
						sb.append("Yes\n");
					else sb.append("No\n");
				}
				if (clca_pane.isChecked()) {
					sb.append("\t• CLCA needed/submitted? ");
					if (clca_pane.getAnswer() == QuestionPane.YES_ANSWER)
						sb.append("Yes\n");
					else sb.append("No\n");
				}
				if (okb_pane.isChecked()) {
					sb.append("\t• OKB Submitted? ");
					if (okb_pane.getAnswer() == QuestionPane.YES_ANSWER)
						sb.append("Yes\n");
					else sb.append("No\n");
				}
				if (sit_pane.isChecked()) {
					sb.append("\t• New SIT created? ");
					if (sit_pane.getAnswer() == QuestionPane.YES_ANSWER)
						sb.append("Yes\n");
					else sb.append("No\n");
				}
				if (sr_profiled_pane.isChecked()) {
					sb.append("\t• Case profiled correctly? ");
					if (sr_profiled_pane.getAnswer() == QuestionPane.YES_ANSWER)
						sb.append("Yes\n");
					else sb.append("No\n");
				}
				if (sr_title_pane.isChecked()) {
					sb.append("\t• Does SR title adhere to policy? ");
					if (sr_title_pane.getAnswer() == QuestionPane.YES_ANSWER)
						sb.append("Yes\n");
					else sb.append("No\n");
				}		
				sb.append("-----------------------------\n");
				sb.append("** Submitter Closing Email **\n");				
				//insert \n at next space after 80 chars
				StringBuilder email_sb = new StringBuilder(jta.getText());
				int i = 0;
				while ((i = email_sb.indexOf(" ", i + 80)) != -1) { 
					email_sb.replace(i, i + 1, "\n");
				}
				sb.append(email_sb.toString() + "\n");
				sb.append("-----------------------------\n");
				sb.append("** Next Step(s) **\n");
				sb.append("Close SR by end of day today\n");
				sb.append("***********************************************"
						+ "*************");
				
				jta.setText(sb.toString());
				jta.requestFocusInWindow();
				jta.setCaretPosition(0);
				jta.selectAll();
		}});
		JButton copy_button = new JButton("Copy to clipboard and close");
		copy_button.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) {
				StringSelection selection = new StringSelection(ClosingFrame
						.jta.getText());
				Clipboard clipboard = Toolkit.getDefaultToolkit()
						.getSystemClipboard();
				clipboard.setContents(selection, null);
				toggle();
				jta.requestFocusInWindow();
				jta.selectAll();
		}});
		JButton reset_button = new JButton("Reset");
		reset_button.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent arg0) {
				reset();
		}});
		button_pane.setLayout(new BoxLayout(button_pane, BoxLayout.X_AXIS));
		button_pane.add(generate_button);
		button_pane.add(copy_button);
		button_pane.add(reset_button);
		/* main panel */
		JPanel pane = new JPanel();
		pane.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		pane.setLayout(new BorderLayout());
		pane.add(top_pane, BorderLayout.NORTH);
		pane.add(middle_pane, BorderLayout.CENTER);
		pane.add(button_pane, BorderLayout.SOUTH);
		/* initialize frame */
		JFrame.setDefaultLookAndFeelDecorated(true);
		frame = new JFrame();
		frame.setContentPane(pane);
		frame.setSize(510, 675);
		frame.setMinimumSize(new Dimension(510, 550));
		frame.setLocation(RA.frame.getX() + RA.frame.getWidth(), RA.frame.getY());
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		frame.setTitle("HCRT: SR Closing Checklist");
		//allow vertical resize only
		/*
		frame.addComponentListener(new ComponentAdapter() {
			@Override public void componentResized(ComponentEvent e) {
				frame.setSize(new Dimension(510, frame.getHeight()));
				super.componentResized(e);
			} 
		});
		*/
		//check if minimized. if yes, reset to normal and toggle visible.
		frame.addWindowStateListener(new WindowStateListener() {
			@Override public void windowStateChanged(WindowEvent e) {
				if ((e.getNewState() & Frame.ICONIFIED) == Frame.ICONIFIED) {
					frame.setState(java.awt.Frame.NORMAL);
					toggle();
				}
			}
		});
	}
	
	void toggle() {
		frame.setVisible(!visible);
		visible = !visible;
	}
	
	void reset() {
		how_resolved_pane.set_text("");
		jta.setText("");
		did_customer_approve_closure_pane.restoreDefault();
		seven_days_pane.restoreDefault();
		sr_profiled_pane.restoreDefault();
		sr_title_pane.restoreDefault();
		clca_pane.restoreDefault();
		sit_pane.restoreDefault();
		okb_pane.restoreDefault();
		service_history_cp.setBackground(RA.bleach(Color.BLUE, (float)0.85));
		service_history_jp.setBackground(RA.bleach(Color.BLUE, (float)0.85));
		service_history_jta.setEditable(true);
		service_history_jta.setBackground(Color.WHITE);
		service_history_jta.setText("No service history added");
		service_history_jcb.setBackground(RA.bleach(Color.BLUE, (float)0.85));
		service_history_jcb.setSelected(true);
		how_resolved_cp.setBackground(RA.bleach(Color.BLUE, (float)0.85));
		how_resolved_jp.setBackground(RA.bleach(Color.BLUE, (float)0.85));
		how_resolved_jta.setEditable(true);
		how_resolved_jta.setBackground(Color.WHITE);
		how_resolved_jta.setText("");
		how_resolved_jcb.setBackground(RA.bleach(Color.BLUE, (float)0.85));
		how_resolved_jcb.setSelected(true);
	}
}
