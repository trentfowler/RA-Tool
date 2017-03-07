import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.Timer;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.text.SimpleDateFormat;

public class Call extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	static JTextField called_jtf;
	static JTextArea steps_jta;
	static String timestamp;
	static JCheckBox called_jcb;
	static JButton called_jb;
	static JPanel called_pane;
	static JPanel called_jb_jp;
	static JPanel called_subpane;
	static JPanel steps_jp;
	static JPanel steps_taken_panel;
	static JToggleButton clipboard_jtb;
	static JToggleButton view_edit_jtb;
	
	public Call() {
		/* call section */
		called_pane = new JPanel();
		called_pane.setBackground(RA.bleach(Color.BLUE, (float)0.85));
		called_pane.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(Color.BLACK),
				BorderFactory.createEmptyBorder(5,5,5,5)
		));
		called_pane.setLayout(new BorderLayout());
		JLabel called_jl = new JLabel("Called customer at: ");
		Font jlabel_font = new JLabel("").getFont();
		called_jl.setFont(jlabel_font.deriveFont(Font.BOLD));
		called_jtf = new JTextField();
		called_jtf.setEditable(true);
		called_jtf.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(Color.BLACK),
				BorderFactory.createEmptyBorder(5,5,5,5)
		));
		called_jcb = new JCheckBox();
		called_jcb.setBackground(RA.bleach(Color.BLUE, (float)0.85));
		called_jcb.setSelected(true);
		called_jcb.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent arg0) {
				if (called_jcb.isSelected()) {
					called_pane.setBackground(RA.bleach(Color.BLUE, (float)0.85));
					called_jcb.setBackground(RA.bleach(Color.BLUE, (float)0.85));
					called_jb_jp.setBackground(RA.bleach(Color.BLUE, (float)0.85));
					called_subpane.setBackground(RA.bleach(Color.BLUE, (float)0.85));
					called_jtf.setEditable(true);
					called_jb.setEnabled(true);
				} else {
					called_pane.setBackground(RA.bleach(Color.YELLOW, (float)0.85));
					called_jcb.setBackground(RA.bleach(Color.YELLOW, (float)0.85));
					called_subpane.setBackground(RA.bleach(Color.YELLOW, (float)0.85));
					called_jb_jp.setBackground(RA.bleach(Color.YELLOW, (float)0.85));
					called_jtf.setEditable(false);
					called_jb.setEnabled(false);
				}
		} });
		called_jb_jp = new JPanel();
		called_jb_jp.setBackground(RA.bleach(Color.BLUE, (float)0.85));
		called_jb_jp.setLayout(new BorderLayout());
		called_jb_jp.setBorder(BorderFactory.createEmptyBorder(0,2,0,0));
		called_jb = new JButton("Add Timestamp");
		called_jb.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent arg0) {
				Date now = new Date();
				SimpleDateFormat sdf = 
						new SimpleDateFormat("E yyyy.MM.dd 'at' hh:mm a zzz");
				timestamp = sdf.format(now);
				called_jb.setText("Refresh");
				called_jb.setToolTipText(timestamp);
		} });
		called_jb.setEnabled(true);
		called_jb_jp.add(called_jb, BorderLayout.CENTER);
		called_subpane = new JPanel();
		called_subpane.setBackground(RA.bleach(Color.BLUE, (float)0.85));
		called_subpane.setLayout(new BorderLayout());
		called_subpane.add(called_jl, BorderLayout.WEST);
		called_subpane.add(called_jtf, BorderLayout.CENTER);
		called_subpane.add(called_jb_jp, BorderLayout.EAST);
		called_pane.add(called_subpane, BorderLayout.CENTER);
		called_pane.add(called_jcb, BorderLayout.EAST);
		/* steps section */
		steps_jta = new JTextArea(15, 15);
		steps_jta.setText("• ");
		steps_jta.setLineWrap(true);
		steps_jta.setWrapStyleWord(true);
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
		JLabel steps_jl = new JLabel("Call Log:");
		steps_jl.setFont(jlabel_font.deriveFont(Font.BOLD));
		steps_jp.add(steps_jl, BorderLayout.WEST);
		steps_taken_panel = new CustomPanel();
		steps_taken_panel.setBackground(RA.bleach(Color.BLUE, (float)0.85));
		steps_taken_panel.setLayout(new BorderLayout());
		steps_taken_panel.add(steps_jp, BorderLayout.NORTH);
		steps_taken_panel.add(steps_jsp, BorderLayout.CENTER);
		
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
		topPanel.add(called_pane);
		/* generate panel */
		JPanel generatePanel = new JPanel();
		JButton generate_jb = new JButton("Generate");
		generate_jb.setFont(generate_jb.getFont().deriveFont(Font.BOLD));
		generate_jb.setFocusPainted(false);
		generate_jb.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) {
				if (clipboard_jtb.isSelected()) {
					/* copy to clipboard */
					StringSelection selection = new StringSelection(
							generate());
					Clipboard clipboard = Toolkit.getDefaultToolkit()
							.getSystemClipboard();
					clipboard.setContents(selection, null);
					/* notify on copy to clipboard */
					final JOptionPane optionPane = new JOptionPane(
							"Copied to Clipboard.", 
							JOptionPane.INFORMATION_MESSAGE, 
							JOptionPane.DEFAULT_OPTION, 
							null, 
							new Object[]{}, 
							null);
					final JDialog dialog = new JDialog();
					dialog.setTitle("Call");
					dialog.setAlwaysOnTop(true);
					dialog.setModal(true);
					dialog.setContentPane(optionPane);
					dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
					dialog.pack();
					dialog.setLocationRelativeTo(RA.frame);
					Timer timer = new Timer(700, new AbstractAction() {
						private static final long serialVersionUID = 1L;
						@Override public void actionPerformed(ActionEvent ae) {
					        dialog.dispose();
					    }
					});
					timer.setRepeats(false);
					timer.start();
					dialog.setVisible(true);
				} else {
					JTextArea jta = new JTextArea(35, 35);
					jta.setText(generate());
					jta.setLineWrap(true);
					jta.setWrapStyleWord(true);
					RA.initializeSpellcheck(jta);
					JOptionPane.showMessageDialog(RA.frame, new BubbleScrollPane(jta));
				}
			}
		});
		generatePanel.setLayout(new BorderLayout());
		generatePanel.add(generate_jb, BorderLayout.CENTER);
		/* toggle panel */
		clipboard_jtb = new JToggleButton("Copy to Clipboard");
		view_edit_jtb = new JToggleButton("View & Edit");
		clipboard_jtb.setFocusPainted(false);
		clipboard_jtb.setSelected(false);
		view_edit_jtb.setFocusPainted(false);
		view_edit_jtb.setSelected(true);
		clipboard_jtb.addItemListener(new ItemListener() {
			@Override public void itemStateChanged(ItemEvent ie) {
				if (ie.getStateChange() == ItemEvent.SELECTED) {
					view_edit_jtb.setSelected(false);
				} else if (ie.getStateChange() == ItemEvent.DESELECTED) {
					view_edit_jtb.setSelected(true);
				}
		} });
		view_edit_jtb.addItemListener(new ItemListener() {
			@Override public void itemStateChanged(ItemEvent ie) {
				if (ie.getStateChange() == ItemEvent.SELECTED) {
					clipboard_jtb.setSelected(false);
				} else if (ie.getStateChange() == ItemEvent.DESELECTED) {
					clipboard_jtb.setSelected(true);				}
		} });
		JPanel togglePanel = new JPanel();
		togglePanel.setLayout(new BorderLayout());
		togglePanel.add(clipboard_jtb, BorderLayout.NORTH);
		togglePanel.add(view_edit_jtb, BorderLayout.CENTER);
		/* bottom panel */
		JPanel bottomPanel = new JPanel();
		bottomPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		bottomPanel.setLayout(new BorderLayout());
		JButton arrow_jb = new JButton("\u25C4");
		arrow_jb.setFont(arrow_jb.getFont().deriveFont(Font.BOLD));
		arrow_jb.setFocusPainted(false);
		arrow_jb.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent arg0) {
				StringBuilder sb = new StringBuilder(generate());
				if (!steps_jta.getText().equalsIgnoreCase("• ")
					&& !steps_jta.getText().isEmpty())
					RA.steps_jta.setText(
							RA.steps_jta.getText().trim() 
							+ "\n-----------------------------\n" 
							+ sb.toString().trim().replaceAll("\n\t", "\n"));
				String tmp = called_jtf.getText();
				Call.reset();
				if ((!RA.called_jtf.getText().isEmpty()) 
						&& (RA.called_jtf.getText() != null))
						Call.called_jtf.setText(RA.called_jtf.getText());
				if (!tmp.isEmpty())
					Call.called_jtf.setText(tmp);
			}
		});
		bottomPanel.add(arrow_jb, BorderLayout.WEST);
		bottomPanel.add(generatePanel, BorderLayout.CENTER);
		bottomPanel.add(togglePanel, BorderLayout.EAST);
		this.setLayout(new BorderLayout());
		this.add(topPanel, BorderLayout.NORTH);
		this.add(steps_taken_panel, BorderLayout.CENTER);
		this.add(bottomPanel, BorderLayout.SOUTH);
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
	}
	
	public void setPhoneNumber(String number) {
		Call.called_jtf.setText(number);
	}
	
	public String generate() {
		StringBuilder sb = new StringBuilder();
		if (called_jcb.isSelected()) {
			if (!called_jtf.getText().isEmpty()) {
				sb.append("Called customer at: " + called_jtf.getText());
			}
			if (!called_jtf.getText().isEmpty() && timestamp != null) {
				sb.append(" " + timestamp + "\n");
			}
			if (!called_jtf.getText().isEmpty() && timestamp == null) {
				sb.append("\n");
			}
			if (called_jtf.getText().isEmpty() && timestamp != null) {
				sb.append(timestamp + "\n");
			}
		}
		sb.append("Call Log: \n\t");
		sb.append(steps_jta.getText().trim().replaceAll("[\\r\\n]+", "\n\t"));
		return sb.toString();
	}
	
	public static void updateView() {
		/* called */
		if (called_jcb.isSelected()) {
			called_pane.setBackground(RA.bleach(Color.BLUE, (float)0.85));
			called_jcb.setBackground(RA.bleach(Color.BLUE, (float)0.85));
			called_jb_jp.setBackground(RA.bleach(Color.BLUE, (float)0.85));
			called_subpane.setBackground(RA.bleach(Color.BLUE, (float)0.85));
			called_jtf.setEditable(true);
			called_jb.setEnabled(true);
		} else {
			called_pane.setBackground(RA.bleach(Color.YELLOW, (float)0.85));
			called_jcb.setBackground(RA.bleach(Color.YELLOW, (float)0.85));
			called_subpane.setBackground(RA.bleach(Color.YELLOW, (float)0.85));
			called_jb_jp.setBackground(RA.bleach(Color.YELLOW, (float)0.85));
			called_jtf.setEditable(false);
			called_jb.setEnabled(false);
		}
		/* timestamp */
		if (timestamp == null || timestamp.isEmpty()) {
			called_jb.setText("Add Timestamp");
			called_jb.setToolTipText(null);
		} else {
			called_jb.setText("Refresh");
			called_jb.setToolTipText(timestamp);
		}
	}
	
	public static void reset() {
		called_pane.setBackground(RA.bleach(Color.BLUE, (float)0.85));
		called_jtf.setText("");
		called_jtf.setEditable(true);
		called_jcb.setBackground(RA.bleach(Color.BLUE, (float)0.85));
		called_jcb.setSelected(true);
		called_jb_jp.setBackground(RA.bleach(Color.BLUE, (float)0.85));
		called_jb.setText("Add Timestamp");
		called_jb.setEnabled(true);
		called_jb.setToolTipText(null);
		timestamp = "";
		called_subpane.setBackground(RA.bleach(Color.BLUE, (float)0.85));
		steps_jta.setText("• ");
	}
}
