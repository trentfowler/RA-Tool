import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.*;
import javax.swing.Timer;
import javax.swing.event.*;

import com.inet.jortho.SpellChecker;
import com.inet.jortho.FileUserDictionary;

public class RA {
	
	static String agent_name;
	static Store store;
	static Call call;
	static JFrame frame;
	
	/* gui elements */
	static JTextArea customer_desc_jta;
	static JTextArea recent_history_jta; 
	static JCheckBox customer_desc_jcb;
	static JCheckBox recent_history_jcb;
	static JCheckBox agent_desc_jcb;
	static JTextArea agent_desc_jta;
	static CustomPanel agent_description_pane;
	static JPanel agent_desc_jp;
	static CustomPanel customer_description_pane;
	static CustomPanel recent_history_pane;
	static JPanel desc_jp;
	static JPanel recent_history_jp;
	static CustomPanel steps_taken_panel;
	static JPanel steps_jp;
	static String name;
	static Object generate_selection; 
	static FieldPane sr_pane;
	static FieldPane issue_pane;
	static FieldPane dc_sess_id_pane;
	static FieldPane parent_sr_pane;
	static FieldPane fusion_pane;
	static FieldPane hcrt_next_actions_pane;
	static FieldPane customer_next_actions_pane;
	static FieldPane submitter_pane;
	static FieldPane oracle_pane;
	static FieldPane finacial_impact_pane;
	static FieldPane sit_pane;
	static FieldPane raid_level_pane;
	static FieldPane peripherals_pane;
	static FieldPane bios_pane;
	static FieldPane invoice_pane;
	static FieldPane system_model_pane;
	static FieldPane clca_pane;
	static JTextArea steps_jta;
	static JCheckBox called_jcb;
	static JPanel called_pane;
	static JPanel called_subpane;
	static JTextField called_jtf;
	static JButton called_jb;
	static JPanel called_jb_jp;
	static String timestamp = "";
	static JCheckBoxMenuItem always_on_top_jcbmi;
	static JCheckBox os_checkbox;
	static JPanel os_pane;
	static JPanel os_jp;
	static JCheckBox support_checkbox;
	static JPanel support_pane;
	static JToggleButton clipboard_jtb;
	static JToggleButton view_edit_jtb;
	private static JComboBox<String> entitlements_jcb;
	private static JComboBox<String> architecture_jcb;
	private static JComboBox<String> hashtag_jcb;
	private static JComboBox<String> os_jcb;
	private static int selected_hashtag = 0;
	private static int selected_entitlement = 0;
	private static int selected_os = 0;
	private static int selected_architecture = 0;
	/*
	private static final String[] MODEL_STRING = new String[]{
			"Latitude", "Precision", "OptiPlex", "XPS"};
	private static final ArrayList<String> MODELS = 
			new ArrayList<String>(Arrays.asList(MODEL_STRING));
	*/
	private static final String[] HASHTAGS = {"#ddpe", "#ddpst", "#ddpa", 
			"#pworkspace", "#bitlocker", "#linux", "#chromeos", "#widi", 
			"#wigig", "#advvideo", "#displays", "#raid", "#imaging", "#dbrm", 
			"#malware", "#other"};
	private static final String[] ENTITLEMENTS = {"ProSupport", "ProSupport Plus", 
			"Basic"};
	private static final String[] OS = {
			"Windows 7 Professional", 
			"Windows 7 Ultimate", 
			"Windows 7 Enterprise", 
			"Windows 7 Home Basic/Premium",
			"Windows 7",
			"Windows Vista Ultimate",
			"Windows Vista Enterprise",
			"Windows Vista Home Basic/Premium",
			"Windows XP",
			"Windows 8 Pro",
			"Windows 8 Enterprise",
			"Windows 8",
			"Windows 8.1 Pro",
			"Windows 8.1 Enterprise",
			"Windows 8.1",
			"Windows 10 Home",
			"Windows 10 Pro",
			"Windows 10 Enterprise",
			"Windows 10",
			"Red Hat Enterprise Linux 7",
			"Red Hat Enterprise Linux 6",
			"Red Hat Enterprise Linux 5",
			"Red Hat Enterprise Linux 4",
			"Red Hat Enterprise Linux 3",
			"Red Hat Enterprise Linux 2.1",
			"Red Hat Enterprise Linux",
			"Ubuntu 16.04",
			"Ubuntu 15.10",
			"Ubuntu 15.04",
			"Ubuntu 14.10",
			"Ubuntu 14.04",
			"Ubuntu 13.10",
			"Ubuntu 13.04",
			"Ubuntu 12.10",
			"Ubuntu 12.04",
			"Ubuntu 11.10",
			"Ubuntu 11.04",
			"Ubuntu 10.10",
			"Ubuntu 10.04",
			"ChromeOS",
			"Other Linux",
			"Other Windows",
			"Other"
		};
	private static final String[] ARCHITECTURE = {
			"64-bit", 
			"32-bit", 
			"Unknown"
		};
	
	public RA() {
		
		/* give os look and feel */
		/*
	    try {
	        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	    } catch (ClassNotFoundException | 
	        InstantiationException | 
	        IllegalAccessException | 
	        UnsupportedLookAndFeelException e) {
	        e.printStackTrace();
	    }
	    */
		
	    /* set agent name from file */
		this.setAgentName();
		
		/* deserialize file into Store object */
		RA.store = new Store();
		File fi = new File("./store.serialized");
		if (fi.exists() && !fi.isDirectory()) {
			ObjectInputStream ois = null;
			try {
				FileInputStream fis = new FileInputStream("store.serialized");
				ois = new ObjectInputStream(fis);
				RA.store = (Store) ois.readObject();
			} catch (Exception e) { 
				e.printStackTrace(); 
			} finally {
				try { ois.close(); }
				catch (Exception ex) { ex.printStackTrace(); }
			}
		}
		
		/* menus at top */
		JMenuBar menu_bar = new JMenuBar();
		JMenu file_menu = new JMenu("File");
		JMenuItem quit_jmi = new JMenuItem("Quit");
		quit_jmi.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, 
				ActionEvent.CTRL_MASK));
		JMenuItem generate_jmi = new JMenuItem("Generate");
		generate_jmi.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G,
				ActionEvent.CTRL_MASK));
		generate_jmi.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent arg0) {
				JDialog.setDefaultLookAndFeelDecorated(true);
				Object[] values = {"View & Edit", 
						"Copy to Clipboard"};
				String initial_selection = "View & Edit";
				generate_selection = JOptionPane.showInputDialog(frame, 
						"", 
						"", JOptionPane.QUESTION_MESSAGE, null, 
						values, initial_selection);
				if (generate_selection == "Copy to Clipboard") {
					/* copy to clipboard */
					StringSelection selection = new StringSelection(
							RA.generate());
					Clipboard clipboard = Toolkit.getDefaultToolkit()
							.getSystemClipboard();
					clipboard.setContents(selection, null);
					/* notify on copy to clipboard */
					final JOptionPane optionPane = new JOptionPane("Copied to Clipboard.", 
							JOptionPane.INFORMATION_MESSAGE, 
							JOptionPane.DEFAULT_OPTION, null, new Object[]{}, null);
					final JDialog dialog = new JDialog();
					dialog.setTitle("Request Assistance");
					dialog.setAlwaysOnTop(true);
					dialog.setModal(true);
					dialog.setContentPane(optionPane);
					dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
					dialog.pack();
					dialog.setLocationRelativeTo(frame);
					Timer timer = new Timer(700, new AbstractAction() {
						private static final long serialVersionUID = 1L;
						@Override public void actionPerformed(ActionEvent ae) {
					        dialog.dispose();
					    }
					});
					timer.setRepeats(false);
					timer.start();
					dialog.setVisible(true);
				}
				if (generate_selection == "View & Edit") {
					JTextArea jta = new JTextArea(35, 35);
//					jta.addMouseListener(new ContextMenuMouseListener());
					jta.setText(RA.generate());
					jta.setLineWrap(true);
					jta.setWrapStyleWord(true);
					JOptionPane.showMessageDialog(frame, new BubbleScrollPane(jta));
				}
		}});
		quit_jmi.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) {
				RA.export();
				System.exit(0);
			}
		});
		JMenuItem reset_jmi = new JMenuItem("Start Over");
		reset_jmi.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_N, ActionEvent.CTRL_MASK));
		reset_jmi.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent arg0) {
				int selection = JOptionPane.showConfirmDialog(frame,
						"Erase and start over?",
						"Reset",
						JOptionPane.YES_NO_OPTION);
				if (selection == JOptionPane.YES_OPTION) {
					RA.export();
					RA.reset();
				}
		}});
		JMenuItem search_jmi = new JMenuItem("Search");
		search_jmi.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		search_jmi.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) {
				String value = JOptionPane.showInputDialog(frame, "Enter SR: ", "Search", 
						JOptionPane.QUESTION_MESSAGE);
				if (value!=null && !value.isEmpty()) {
					/*
					String fileName = "./export/" + value.trim() + ".txt";
					File f = new File(fileName);
					if (f.exists() && !f.isDirectory()) {
						try { java.awt.Desktop.getDesktop().edit(f); } 
						catch (IOException ex) { ex.printStackTrace(); }
					}
					*/
					for (Obj o : RA.store.get()) {
						if (o.sr.trim().equalsIgnoreCase(value.trim())) {
							RA.export();
							RA.reset();
							RA.recall(o);
							break;
						}
					}
				}
		}});
		file_menu.add(generate_jmi);
		file_menu.add(search_jmi);
		file_menu.add(reset_jmi);
		file_menu.addSeparator();
		file_menu.add(quit_jmi);
		JMenu settings_menu = new JMenu("Settings");
		JMenuItem set_name_jmi = new JMenuItem("Set Name");
		set_name_jmi.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) {
				String value = JOptionPane.showInputDialog(frame, "Name:", "", 
						JOptionPane.QUESTION_MESSAGE);
				if (value!=null && !value.isEmpty()) {
					name = value;
				}
		}});
		always_on_top_jcbmi = new JCheckBoxMenuItem("Always on top");
		always_on_top_jcbmi.setSelected(true);
		always_on_top_jcbmi.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent arg0) {
				if (always_on_top_jcbmi.isSelected()) {
					frame.setAlwaysOnTop(true);
				} else frame.setAlwaysOnTop(false);
		}});
		/*
		settings_menu.add(set_name_jmi);
		settings_menu.addSeparator();
		*/
		settings_menu.add(always_on_top_jcbmi);
		menu_bar.add(file_menu);
		menu_bar.add(settings_menu);
		
		sr_pane = new FieldPane("Service Request Number:", true);
		
		system_model_pane = new FieldPane("System Model:", true);
		
		os_pane = new JPanel();
		os_pane.setBackground(RA.bleach(Color.BLACK, (float)0.85));
		os_pane.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(Color.BLACK),
				BorderFactory.createEmptyBorder(0,5,0,5)
			));
		os_pane.setLayout(new BorderLayout());
		JLabel os_label = new JLabel("Operating System: ");
		Font jlabel_font = new JLabel("").getFont();
		os_label.setFont(jlabel_font.deriveFont(Font.BOLD));
		os_pane.add(os_label, BorderLayout.WEST);
		os_jcb = new JComboBox<String>(OS);
		os_jcb.addActionListener(new ActionListener() {
		@Override public void actionPerformed(ActionEvent e) {
			selected_os = os_jcb.getSelectedIndex();
		}});
		architecture_jcb = new JComboBox<String>(ARCHITECTURE);
		architecture_jcb.addActionListener(new ActionListener() {
		@Override public void actionPerformed(ActionEvent e) {
			selected_architecture = architecture_jcb.getSelectedIndex();
		}});
		os_jcb.setEnabled(false);
		os_jcb.setFont(jlabel_font.deriveFont(Font.BOLD));
		architecture_jcb.setEnabled(false);
		architecture_jcb.setFont(jlabel_font.deriveFont(Font.BOLD));
		os_jp = new JPanel();
		os_jp.setBackground(RA.bleach(Color.BLACK, (float)0.85));
		os_jp.setBorder(BorderFactory.createEmptyBorder(5,5,5,0));
		os_jp.setLayout(new BorderLayout());
		os_jp.add(os_jcb, BorderLayout.CENTER);
		os_jp.add(architecture_jcb, BorderLayout.EAST);
		os_pane.add(os_jp, BorderLayout.CENTER);
		os_checkbox = new JCheckBox();
		os_checkbox.setBackground(RA.bleach(Color.BLACK, (float)0.85));
		os_pane.add(os_checkbox, BorderLayout.EAST);
		os_checkbox.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent arg0) {
				if (os_checkbox.isSelected()) {
					os_jcb.setEnabled(true);
					os_jcb.setBackground(Color.WHITE);
					architecture_jcb.setEnabled(true);
					architecture_jcb.setBackground(Color.WHITE);
					os_pane.setBackground(
							RA.bleach(Color.BLUE, (float)0.85));
					os_jp.setBackground(RA.bleach(Color.BLUE, (float)0.85));
					os_checkbox.setBackground(
							RA.bleach(Color.BLUE, (float)0.85));
				}
				
				else {
					JComboBox<String> tmp_jcb = new JComboBox<String>();
					os_jcb.setEnabled(false);
					os_jcb.setBackground(tmp_jcb.getBackground());
					architecture_jcb.setEnabled(false);
					architecture_jcb.setBackground(tmp_jcb.getBackground());
					os_pane.setBackground(RA.bleach(Color.BLACK, (float)0.85));
					os_jp.setBackground(RA.bleach(Color.BLACK, (float)0.85));
					os_checkbox.setBackground(
							RA.bleach(Color.BLACK, (float)0.85));
				}
			}});
		
		issue_pane = new FieldPane("Issue:", true);
		
		agent_description_pane = new CustomPanel();
		agent_description_pane.setBackground(
				RA.bleach(Color.BLUE, (float)0.85));
		agent_desc_jp = new JPanel();
		agent_desc_jp.setBackground(RA.bleach(Color.BLUE, (float)0.85));
		agent_desc_jp.setBorder(BorderFactory.createEmptyBorder(0,0,5,0));
		agent_desc_jp.setLayout(new BorderLayout());
		JLabel agent_desc_jl = new JLabel("Agent Description:");
		agent_desc_jl.setFont(jlabel_font.deriveFont(Font.BOLD));
		agent_desc_jp.add(agent_desc_jl, BorderLayout.WEST);
		agent_desc_jta = new JTextArea(3, 10);
		agent_desc_jta.setLineWrap(true);
		agent_desc_jta.setWrapStyleWord(true);
		agent_desc_jta.setEditable(true);
		JTextField tmp_jtf = new JTextField();
		tmp_jtf.setEditable(false);
//		agent_desc_jta.addMouseListener(new ContextMenuMouseListener());
		//TODO
		agent_desc_jta.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		BubbleScrollPane agent_desc_jsp = new BubbleScrollPane(agent_desc_jta);
		agent_desc_jsp.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		agent_description_pane.setLayout(new BorderLayout());
		agent_description_pane.add(agent_desc_jp, BorderLayout.NORTH);
		agent_description_pane.add(agent_desc_jsp, BorderLayout.CENTER);
		agent_desc_jcb = new JCheckBox();
		agent_desc_jcb.setBackground(RA.bleach(Color.BLUE, (float)0.85));
		agent_desc_jcb.setSelected(true);
		agent_desc_jcb.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent arg0) {
				if (agent_desc_jcb.isSelected()) {
					agent_desc_jta.setEditable(true);
					agent_desc_jta.setBackground(Color.WHITE);
					agent_description_pane.setBackground(RA.bleach(
							Color.BLUE, (float)0.85));
					agent_desc_jp.setBackground(RA.bleach(Color.BLUE, (float)0.85));
					agent_desc_jcb.setBackground(RA.bleach(Color.BLUE, (float)0.85));

				} else {
					agent_desc_jta.setEditable(false);
					JTextField tmp_jtf = new JTextField();
					tmp_jtf.setEditable(false);
					agent_desc_jta.setBackground(tmp_jtf.getBackground());
					agent_description_pane.setBackground(RA.bleach(
							Color.BLACK, (float)0.85));
					agent_desc_jp.setBackground(RA.bleach(Color.BLACK, 
							(float)0.85));
					agent_desc_jcb.setBackground(RA.bleach(Color.BLACK, 
							(float)0.85));
				}
			}
		});
		agent_description_pane.add(agent_desc_jcb, BorderLayout.EAST);
		
		customer_description_pane = new CustomPanel();
		customer_description_pane.setBackground(
				RA.bleach(Color.BLACK, (float)0.85));
		desc_jp = new JPanel();
		desc_jp.setBackground(RA.bleach(Color.BLACK, (float)0.85));
		desc_jp.setBorder(BorderFactory.createEmptyBorder(0,0,5,0));
		desc_jp.setLayout(new BorderLayout());
		JLabel customer_desc_jl = 
				new JLabel("Customer Description:");
		customer_desc_jl.setFont(jlabel_font.deriveFont(Font.BOLD));
		desc_jp.add(customer_desc_jl, BorderLayout.WEST);
		customer_desc_jta = new JTextArea(2, 10);
		customer_desc_jta.setLineWrap(true);
		customer_desc_jta.setWrapStyleWord(true);
		customer_desc_jta.setEditable(false);
		customer_desc_jta.setBackground(tmp_jtf.getBackground());
//		customer_desc_jta.addMouseListener(new ContextMenuMouseListener());
		customer_desc_jta.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		BubbleScrollPane desc_jsp = new BubbleScrollPane(customer_desc_jta);
		desc_jsp.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		customer_desc_jcb = new JCheckBox();
		customer_desc_jcb.setBackground(
				RA.bleach(Color.BLACK, (float)0.85));
		customer_desc_jcb.setSelected(false);
		customer_desc_jcb.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent arg0) {
				if (customer_desc_jcb.isSelected()) {
					customer_desc_jta.setEditable(true);
					customer_desc_jta.setBackground(Color.WHITE);
					customer_description_pane.setBackground(
							RA.bleach(Color.BLUE, (float)0.85));
					desc_jp.setBackground(RA.bleach(Color.BLUE, (float)0.85));
					customer_desc_jcb.setBackground(
							RA.bleach(Color.BLUE, (float)0.85));
				} else {
					customer_desc_jta.setEditable(false);
					JTextField tmp_jtf = new JTextField();
					tmp_jtf.setEditable(false);
					customer_desc_jta.setBackground(tmp_jtf.getBackground());
					customer_description_pane.setBackground(
							RA.bleach(Color.BLACK, (float)0.85));
					desc_jp.setBackground(RA.bleach(Color.BLACK, (float)0.85));
					customer_desc_jcb.setBackground(
							RA.bleach(Color.BLACK, (float)0.85));
				}
			}
		});
		customer_description_pane.setLayout(new BorderLayout());
		customer_description_pane.add(desc_jp, BorderLayout.NORTH);
		customer_description_pane.add(desc_jsp, BorderLayout.CENTER);
		customer_description_pane.add(customer_desc_jcb, BorderLayout.EAST);
		
		recent_history_pane = new CustomPanel();
		recent_history_pane.setBackground(
				RA.bleach(Color.BLUE, (float)0.85));
		recent_history_jp = new JPanel();
		recent_history_jp.setBackground(RA.bleach(Color.BLUE, (float)0.85));
		recent_history_jp.setBorder(BorderFactory.createEmptyBorder(0,0,5,0));
		recent_history_jp.setLayout(new BorderLayout());
		JLabel recent_history_jl = new JLabel("Recent Service History:");
		recent_history_jl.setFont(jlabel_font.deriveFont(Font.BOLD));
		recent_history_jp.add(recent_history_jl, BorderLayout.WEST);
		recent_history_jta = new JTextArea(2, 10);
		recent_history_jta.setText("No recent service history");
		recent_history_jta.setLineWrap(true);
		recent_history_jta.setWrapStyleWord(true);
		recent_history_jta.setEditable(true);
//		recent_history_jta.addMouseListener(new ContextMenuMouseListener());
		recent_history_jta.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		BubbleScrollPane recent_history_jsp = new BubbleScrollPane(recent_history_jta);
		recent_history_jsp.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		recent_history_jcb = new JCheckBox();
		recent_history_jcb.setBackground(RA.bleach(Color.BLUE, (float)0.85));
		recent_history_jcb.setSelected(true);
		recent_history_jcb.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent arg0) {
				if (recent_history_jcb.isSelected()) {
					recent_history_jta.setEditable(true);
					recent_history_jta.setBackground(Color.WHITE);
					recent_history_pane.setBackground(RA.bleach(Color.BLUE, (float)0.85));
					recent_history_jp.setBackground(RA.bleach(Color.BLUE, (float)0.85));
					recent_history_jcb.setBackground(RA.bleach(Color.BLUE, (float)0.85));
				} else {
					recent_history_jta.setEditable(false);
					JTextField tmp_jtf = new JTextField();
					tmp_jtf.setEditable(false);
					recent_history_jta.setBackground(tmp_jtf.getBackground());
					recent_history_pane.setBackground(RA.bleach(Color.BLACK, (float)0.85));
					recent_history_jp.setBackground(RA.bleach(Color.BLACK, (float)0.85));
					recent_history_jcb.setBackground(RA.bleach(Color.BLACK, (float)0.85));
				}
			}
		});
		recent_history_pane.setLayout(new BorderLayout());
		recent_history_pane.add(recent_history_jp, BorderLayout.NORTH);
		recent_history_pane.add(recent_history_jsp, BorderLayout.CENTER);
		recent_history_pane.add(recent_history_jcb, BorderLayout.EAST);
		
		CustomPanel hashtag_panel = new CustomPanel();
		hashtag_panel.setBackground(RA.bleach(Color.BLUE, (float)0.85));
		hashtag_jcb = new JComboBox<String>(HASHTAGS);
		hashtag_jcb.setBackground(Color.WHITE);
		JLabel tmp_jl = new JLabel("");
		hashtag_jcb.setFont(tmp_jl.getFont().deriveFont(Font.BOLD));
		hashtag_jcb.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent arg0) {
				selected_hashtag = hashtag_jcb.getSelectedIndex();
				boolean flag = false;
				for (String s: HASHTAGS) {
					if (oracle_pane.get_text().equalsIgnoreCase(s)) {
						flag = true;
						break;
					}
				}
				if (oracle_pane.get_text().isEmpty()) {
					flag = true;
				}
				if (flag == true) {
					switch (selected_hashtag) {
						case 0: oracle_pane.set_text("#ddpe");
								break;
						case 1: oracle_pane.set_text("#ddpst");
								break;
						case 2: oracle_pane.set_text("#ddpa");
								break;
						case 3: oracle_pane.set_text("#pworkspace");
								break;
						case 4: oracle_pane.set_text("#bitlocker");
								break;
						case 5: oracle_pane.set_text("#linux");
								break;
						case 6: oracle_pane.set_text("#chromeos");
								break;
						case 7: oracle_pane.set_text("#widi");
								break;
						case 8: oracle_pane.set_text("#wigig");
								break;
						case 9: oracle_pane.set_text("#advvideo");
								break;
						case 10: oracle_pane.set_text("#displays");
								break;
						case 11: oracle_pane.set_text("#raid");
								break;
						case 12: oracle_pane.set_text("#imaging");
								break;
						case 13: oracle_pane.set_text("#dbrm");
								break;
						case 14: oracle_pane.set_text("#malware");
								break;
						case 15: oracle_pane.set_text("#other");
								break;
						default: break;
					}
				}
			} });
		JPanel hashtag_jcb_jp = new JPanel();
		hashtag_jcb_jp.setBackground(RA.bleach(Color.BLUE, (float)0.85));
		hashtag_jcb_jp.setLayout(new BorderLayout());
		hashtag_jcb_jp.add(hashtag_jcb, BorderLayout.CENTER);
		hashtag_panel.setLayout(new BorderLayout());
		hashtag_panel.add(hashtag_jcb_jp, BorderLayout.CENTER);
		
		called_pane = new JPanel();
		called_pane.setBackground(RA.bleach(Color.BLUE, (float)0.85));
		called_pane.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(Color.BLACK),
				BorderFactory.createEmptyBorder(5,5,5,5)
			));
		called_pane.setLayout(new BorderLayout());
		JLabel called_jl = new JLabel("Called customer at: ");
		called_jl.setFont(jlabel_font.deriveFont(Font.BOLD));
		called_jtf = new JTextField();
		called_jtf.setEditable(true);
//		called_jtf.addMouseListener(new ContextMenuMouseListener());
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
		
		dc_sess_id_pane = new FieldPane("DellConnect sessID:", false);
		
		parent_sr_pane = new FieldPane("Parent SR - Basic Linux:", false);
		
		fusion_pane = new FieldPane("Fusion Number:", false);
		
		support_pane = new JPanel();
		support_pane.setBackground(RA.bleach(Color.BLUE, (float)0.85));
		support_pane.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(Color.BLACK),
				BorderFactory.createEmptyBorder(5,5,5,5)
			));
		support_pane.setLayout(new BorderLayout());
		entitlements_jcb = new JComboBox<String>(ENTITLEMENTS);
		entitlements_jcb.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent arg0) {
				selected_entitlement = entitlements_jcb.getSelectedIndex();
			} });
		entitlements_jcb.setEnabled(true);
		entitlements_jcb.setFont(jlabel_font.deriveFont(Font.BOLD));
		entitlements_jcb.setBackground(Color.WHITE);
		support_checkbox = new JCheckBox();
		support_checkbox.setSelected(true);
		support_checkbox.setBackground(
				RA.bleach(Color.BLUE, (float)0.85));
		support_checkbox.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) {
				if (support_checkbox.isSelected()) {
					support_pane.setBackground(
							RA.bleach(Color.BLUE, (float)0.85));
					support_checkbox.setBackground(
							RA.bleach(Color.BLUE, (float)0.85));
					entitlements_jcb.setEnabled(true);
					entitlements_jcb.setBackground(Color.WHITE);
				} else {
					support_pane.setBackground(
							RA.bleach(Color.BLACK, (float)0.85));
					support_checkbox.setBackground(
							RA.bleach(Color.BLACK, (float)0.85));
					entitlements_jcb.setEnabled(false);
					JComboBox<String> tmp_jcb = new JComboBox<String>();
					entitlements_jcb.setBackground(tmp_jcb.getBackground());
				}
			}});
		JLabel support_jl = new JLabel("Support Entitlement: ");
		support_jl.setFont(jlabel_font.deriveFont(Font.BOLD));
		support_pane.add(support_jl, BorderLayout.WEST);
		support_pane.add(entitlements_jcb, BorderLayout.CENTER);
		support_pane.add(support_checkbox, BorderLayout.EAST);
		
		invoice_pane = new FieldPane("Invoice Date:", true);
		
		submitter_pane = new FieldPane("Submitter:", true);
		
		finacial_impact_pane = new FieldPane("Financial Impact:", true);
		double low = 1000.00;
		double upper = 3000.00;
		double result = Math.random() * (upper - low) + low;
		String sresult = String.format("%.2f", result);
		finacial_impact_pane.set_text("$" + sresult);
		
		clca_pane = new FieldPane("CLCA Needed/Submitted:", true);
		clca_pane.set_text("Meets requirements for HCRT");
		
		oracle_pane = new FieldPane("Oracle (Include Keywords):", true);
		oracle_pane.set_text("#ddpe");
		
		sit_pane = new FieldPane("SIT:", true);
		sit_pane.set_text("Not systemic/No relevant SIT found");
		
		bios_pane = new FieldPane("BIOS Version:", false);
		bios_pane.set_text("Latest rev from dell.com");
		
		raid_level_pane = new FieldPane("RAID Level:", true);
		raid_level_pane.set_text("No RAID");
		
		peripherals_pane = new FieldPane("Peripherals:", true);
		peripherals_pane.set_text("None");
		
		steps_jta = new JTextArea(15, 15);
		steps_jta.setText("• ");
		steps_jta.setLineWrap(true);
		steps_jta.setWrapStyleWord(true);
//		steps_jta.addMouseListener(new ContextMenuMouseListener());
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
		
		hcrt_next_actions_pane = new FieldPane("HCRT "
				+ "Next Actions:", true);
		
		customer_next_actions_pane = new FieldPane("Customer's "
				+ "Next Actions:", false);
		
		JPanel generate_pane = new JPanel();
		generate_pane.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		JButton generate_jb = new JButton("Generate");
		generate_jb.setFont(generate_jb.getFont().deriveFont(Font.BOLD));
		generate_jb.setFocusPainted(false);
		generate_pane.setLayout(new BorderLayout());
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
		JPanel toggle_panel = new JPanel();
		toggle_panel.setLayout(new BorderLayout());
		toggle_panel.add(clipboard_jtb, BorderLayout.NORTH);
		toggle_panel.add(view_edit_jtb, BorderLayout.CENTER);
		generate_pane.add(generate_jb, BorderLayout.CENTER);
		generate_pane.add(toggle_panel, BorderLayout.WEST);
		JButton start_over_jb = new JButton("Start Over");
		start_over_jb.setFont(start_over_jb.getFont().deriveFont(Font.BOLD));
		start_over_jb.setFocusPainted(false);
		generate_pane.add(start_over_jb, BorderLayout.EAST);
		start_over_jb.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent arg0) {
				int selection = JOptionPane.showConfirmDialog(frame,
						"Erase and start over?",
						"Reset",
						JOptionPane.YES_NO_OPTION);
				if (selection == JOptionPane.YES_OPTION) {
					RA.export();
					RA.reset();
				}
			} });
		generate_jb.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent arg0) {
				if (clipboard_jtb.isSelected()) {
					/* copy to clipboard */
					StringSelection selection = new StringSelection(
							RA.generate());
					Clipboard clipboard = Toolkit.getDefaultToolkit()
							.getSystemClipboard();
					clipboard.setContents(selection, null);
					/* notify on copy to clipboard */
					final JOptionPane optionPane = new JOptionPane("Copied to Clipboard.", 
							JOptionPane.INFORMATION_MESSAGE, 
							JOptionPane.DEFAULT_OPTION, null, new Object[]{}, null);
					final JDialog dialog = new JDialog();
					dialog.setTitle("Request Assistance");
					dialog.setAlwaysOnTop(true);
					dialog.setModal(true);
					dialog.setContentPane(optionPane);
					dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
					dialog.pack();
					dialog.setLocationRelativeTo(frame);
					Timer timer = new Timer(700, new AbstractAction() {
						private static final long serialVersionUID = 1L;
						@Override public void actionPerformed(ActionEvent ae) {
					        dialog.dispose();
					    }
					});
					timer.setRepeats(false);
					timer.start();
					dialog.setVisible(true);
				}
				
				else if (view_edit_jtb.isSelected()) {
					JTextArea jta = new JTextArea(35, 35);
//					jta.addMouseListener(new ContextMenuMouseListener());
					jta.setText(RA.generate());
					jta.setLineWrap(true);
					jta.setWrapStyleWord(true);
					JOptionPane.showMessageDialog(frame, 
							new BubbleScrollPane(jta));
				}
				
				else {
					StringSelection selection = new StringSelection(
							RA.generate());
					Clipboard clipboard = Toolkit.getDefaultToolkit()
							.getSystemClipboard();
					clipboard.setContents(selection, null);
					JTextArea jta = new JTextArea(35, 35);
//					jta.addMouseListener(new ContextMenuMouseListener());
					jta.setText(RA.generate());
					jta.setLineWrap(true);
					jta.setWrapStyleWord(true);
					JOptionPane.showMessageDialog(frame, 
							new BubbleScrollPane(jta));
				}
		} });
		
		JPanel top_pane = new JPanel();
		top_pane.setLayout(new BoxLayout(top_pane, BoxLayout.Y_AXIS));
		top_pane.add(called_pane);
		top_pane.add(hashtag_panel);
		top_pane.add(system_model_pane);
		top_pane.add(sr_pane);
		top_pane.add(issue_pane);
		top_pane.add(agent_description_pane);
		top_pane.add(customer_description_pane);
		top_pane.add(recent_history_pane);
		top_pane.add(dc_sess_id_pane);
		top_pane.add(parent_sr_pane);
		top_pane.add(fusion_pane);
		top_pane.add(support_pane);
		top_pane.add(invoice_pane);
		top_pane.add(submitter_pane);
		top_pane.add(finacial_impact_pane);
		top_pane.add(clca_pane);
		top_pane.add(os_pane);
		top_pane.add(oracle_pane);
		top_pane.add(sit_pane);
		top_pane.add(peripherals_pane);
		top_pane.add(raid_level_pane);
		top_pane.add(bios_pane);
		top_pane.add(steps_taken_panel);
		top_pane.add(hcrt_next_actions_pane);
		top_pane.add(customer_next_actions_pane);
		
		JPanel main_pane = new JPanel();
//		main_pane.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		main_pane.setLayout(new BorderLayout());
		main_pane.add(top_pane, BorderLayout.NORTH);
		main_pane.add(generate_pane, BorderLayout.CENTER);
		BubbleScrollPane main_jsp = new BubbleScrollPane(main_pane);
		main_jsp.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		main_jsp.getVerticalScrollBar().setUnitIncrement(16); //scroll faster
		
		JTabbedPane tab = new JTabbedPane();
		tab.setFocusable(false);
		tab.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		tab.addTab("Request Assistance", main_jsp);
		call = new Call();
		String callTitle = "Call";
		tab.addTab(callTitle, call);
		//tab.addTab("Email", new JPanel());
		tab.addTab("Notes", new Notes());
		tab.addMouseListener(new MouseAdapter() {
			@Override public void mouseClicked(MouseEvent e) {
				/* detected double click on index 0 */
				if (e.getClickCount() == 2 && tab.indexAtLocation(e.getX(), e.getY()) == 0) {
					/* copy to clipboard */
					StringSelection selection = new StringSelection(
							RA.generate());
					Clipboard clipboard = Toolkit.getDefaultToolkit()
							.getSystemClipboard();
					clipboard.setContents(selection, null);
					/* notify on copy to clipboard */
					final JOptionPane optionPane = new JOptionPane("Copied to Clipboard.", 
							JOptionPane.INFORMATION_MESSAGE, 
							JOptionPane.DEFAULT_OPTION, null, new Object[]{}, null);
					final JDialog dialog = new JDialog();
					dialog.setTitle("Request Assistance");
					dialog.setAlwaysOnTop(true);
					dialog.setModal(true);
					dialog.setContentPane(optionPane);
					dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
					dialog.pack();
					dialog.setLocationRelativeTo(frame);
					Timer timer = new Timer(700, new AbstractAction() {
						private static final long serialVersionUID = 1L;
						@Override public void actionPerformed(ActionEvent ae) {
					        dialog.dispose();
					    }
					});
					timer.setRepeats(false);
					timer.start();
					dialog.setVisible(true);
				}
				/* detected triple click on index 0 */
				if (e.getClickCount() == 3 && tab.indexAtLocation(e.getX(), e.getY()) == 0) {
					StringSelection selection = new StringSelection(
							RA.generate());
					Clipboard clipboard = Toolkit.getDefaultToolkit()
							.getSystemClipboard();
					clipboard.setContents(selection, null);
					toggle();
				}
				/* detected double click on index 1 */
				if (e.getClickCount() == 2 && tab.indexAtLocation(e.getX(), e.getY()) == 1) {
					/* copy to clipboard */
					StringSelection selection = new StringSelection(
							call.generate());
					Clipboard clipboard = Toolkit.getDefaultToolkit()
							.getSystemClipboard();
					clipboard.setContents(selection, null);
					/* notify on copy to clipboard */
					final JOptionPane optionPane = new JOptionPane("Copied to Clipboard.", 
							JOptionPane.INFORMATION_MESSAGE, 
							JOptionPane.DEFAULT_OPTION, null, new Object[]{}, null);
					final JDialog dialog = new JDialog();
					dialog.setTitle("Call");
					dialog.setAlwaysOnTop(true);
					dialog.setModal(true);
					dialog.setContentPane(optionPane);
					dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
					dialog.pack();
					dialog.setLocationRelativeTo(frame);
					Timer timer = new Timer(700, new AbstractAction() {
						private static final long serialVersionUID = 1L;
						@Override public void actionPerformed(ActionEvent ae) {
					        dialog.dispose();
					    }
					});
					timer.setRepeats(false);
					timer.start();
					dialog.setVisible(true);
				}
				/* detected double click on index 3 */
				if (e.getClickCount() == 2 && tab.indexAtLocation(e.getX(), e.getY()) == 2) {
					/* copy to clipboard */
					StringSelection selection = new StringSelection(
							Notes.notes_jta.getText());
					Clipboard clipboard = Toolkit.getDefaultToolkit()
							.getSystemClipboard();
					clipboard.setContents(selection, null);
					/* notify on copy to clipboard */
					final JOptionPane optionPane = new JOptionPane("Copied to Clipboard.", 
							JOptionPane.INFORMATION_MESSAGE, 
							JOptionPane.DEFAULT_OPTION, null, new Object[]{}, null);
					final JDialog dialog = new JDialog();
					dialog.setTitle("Notes");
					dialog.setAlwaysOnTop(true);
					dialog.setModal(true);
					dialog.setContentPane(optionPane);
					dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
					dialog.pack();
					dialog.setLocationRelativeTo(frame);
					Timer timer = new Timer(700, new AbstractAction() {
						private static final long serialVersionUID = 1L;
						@Override public void actionPerformed(ActionEvent ae) {
					        dialog.dispose();
					    }
					});
					timer.setRepeats(false);
					timer.start();
					dialog.setVisible(true);
				}
			}
		});
		tab.addChangeListener(new ChangeListener() {
			@Override public void stateChanged(ChangeEvent ce) {
				JTabbedPane sourceTabbedPane = (JTabbedPane) ce.getSource();
				int index = sourceTabbedPane.getSelectedIndex();
				if (sourceTabbedPane.getTitleAt(index).equals(callTitle)) {
					if (Call.called_jtf.getText().isEmpty()) {
						call.setPhoneNumber(called_jtf.getText());
					}
				}
			}
		});
		
		/* initialize spell check for text fields */
		this.initializeSpellcheck();
		
		JFrame.setDefaultLookAndFeelDecorated(true);
		JDialog.setDefaultLookAndFeelDecorated(true);
		frame = new JFrame();
		frame.setJMenuBar(menu_bar);
		frame.setContentPane(tab);
		frame.setSize(510, 650);
		frame.setMinimumSize(new Dimension(510, 300));
		//allow vertical resize only
		frame.addComponentListener(new ComponentAdapter() {
			@Override public void componentResized(ComponentEvent e) {
		        frame.setSize(new Dimension(510, frame.getHeight()));
		        super.componentResized(e);
		    }
		});
		frame.setLocationRelativeTo(null);
		frame.setAlwaysOnTop(true);
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		frame.setTitle("HCRT: Request Assistance");
	}
	
	public void setAgentName() {
		String name = "null";
		try {
			BufferedReader br = new BufferedReader(new FileReader("./name.txt"));
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();
			while (line != null) {
				sb.append(line);
				line = br.readLine();
			}
			name = sb.toString();
			br.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		RA.agent_name = name;
	}
	
	public void toggle() {
		frame.setVisible(!frame.isVisible());
	}
	
	/**
	 * Color tweak to tone down or wash out the color a little because the 
	 * blues/blacks are too hard and text is unreadable on that background. 
	 * 
	 * @param color		Color to wash out.
	 * @param amount	Floating point value between 0.0 and 1.0.
	 * @return			Returns the resulting color. 
	 */
	public static Color bleach(Color color, float amount) {
		int red = (int) ((color.getRed() * (1 - amount) / 255 + amount) * 255);
	    int green = (int) ((color.getGreen() * (1 - amount) / 255 + amount) * 255);
	    int blue = (int) ((color.getBlue() * (1 - amount) / 255 + amount) * 255);
	    return new Color(red, green, blue);
	}
	
	/** 
	 * Resets the "view" to defaults.
	 */
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
		agent_description_pane.setBackground(RA.bleach(Color.BLUE, (float)0.85));
		agent_desc_jp.setBackground(RA.bleach(Color.BLUE, (float)0.85));
		agent_desc_jta.setEditable(true);
		JTextField tmp_jtf = new JTextField();
		tmp_jtf.setEditable(false);
		agent_desc_jta.setBackground(Color.WHITE);
		agent_desc_jta.setText("");
		agent_desc_jcb.setBackground(RA.bleach(Color.BLUE, (float)0.85));
		agent_desc_jcb.setSelected(true);
		customer_description_pane.setBackground(RA.bleach(Color.BLACK, (float)0.85));
		desc_jp.setBackground(RA.bleach(Color.BLACK, (float)0.85));
		customer_desc_jta.setEditable(false);
		customer_desc_jta.setBackground(tmp_jtf.getBackground());
		customer_desc_jta.setText("");
		customer_desc_jcb.setBackground(RA.bleach(Color.BLACK, (float)0.85));
		customer_desc_jcb.setSelected(false);
		recent_history_pane.setBackground(RA.bleach(Color.BLUE, (float)0.85));
		recent_history_jp.setBackground(RA.bleach(Color.BLUE, (float)0.85));
		recent_history_jta.setEditable(true);
		recent_history_jta.setBackground(Color.WHITE);
		recent_history_jta.setText("No recent service history");
		recent_history_jcb.setBackground(RA.bleach(Color.BLUE, (float)0.85));
		recent_history_jcb.setSelected(true);
		sr_pane.reset();
		issue_pane.reset();
		dc_sess_id_pane.reset();
		parent_sr_pane.reset();
		fusion_pane.reset();
		hcrt_next_actions_pane.reset();
		customer_next_actions_pane.reset();
		submitter_pane.reset();
		oracle_pane.reset();
		oracle_pane.set_text("#ddpe");
		finacial_impact_pane.reset();
		double low = 1000.00;
		double upper = 3000.00;
		double result = Math.random() * (upper - low) + low;
		String sresult = String.format("%.2f", result);
		finacial_impact_pane.set_text("$" + sresult);
		raid_level_pane.reset();
		raid_level_pane.set_text("No RAID");
		peripherals_pane.reset();
		peripherals_pane.set_text("None");
		sit_pane.reset();
		sit_pane.set_text("Not systemic/No relevant SIT found");
		bios_pane.reset();
		bios_pane.set_text("Latest rev from dell.com");
		invoice_pane.reset();
		system_model_pane.reset();
		clca_pane.reset();
		clca_pane.set_text("Meets requirements for HCRT");
		steps_jta.setText("• ");
		entitlements_jcb.setSelectedItem("ProSupport");
		os_jcb.setSelectedItem("Windows 7 Professional");
		architecture_jcb.setSelectedItem("64-bit");
		hashtag_jcb.setSelectedItem("#ddpe");
		support_pane.setBackground(RA.bleach(Color.BLUE, (float)0.85));
		support_checkbox.setBackground(RA.bleach(Color.BLUE, (float)0.85));
		support_checkbox.setSelected(true);
		entitlements_jcb.setEnabled(true);
		JComboBox<String> tmp_jcb = new JComboBox<String>();
		entitlements_jcb.setBackground(Color.WHITE);
		os_jcb.setEnabled(false);
		os_jcb.setBackground(tmp_jcb.getBackground());
		architecture_jcb.setEnabled(false);
		architecture_jcb.setBackground(tmp_jcb.getBackground());
		os_pane.setBackground(RA.bleach(Color.BLACK, (float)0.85));
		os_jp.setBackground(RA.bleach(Color.BLACK, (float)0.85));
		os_checkbox.setSelected(false);
		os_checkbox.setBackground(RA.bleach(Color.BLACK, (float)0.85));
		Notes.notes_jta.setText("");
		Call.steps_jta.setText("• ");
		Call.called_jtf.setText("");
		Call.reset();
	}
	
	/**
	 * 
	 * @param o
	 */
	public static void recall(Obj o) {
		called_jcb.setSelected(o.called_checked);
		system_model_pane.setSelected(o.system_model_checked);
		sr_pane.setSelected(o.sr_checked);
		issue_pane.setSelected(o.issue_checked);
		agent_desc_jcb.setSelected(o.agent_desc_checked);
		customer_desc_jcb.setSelected(o.customer_desc_checked);
		recent_history_jcb.setSelected(o.recent_history_checked);
		dc_sess_id_pane.setSelected(o.dc_sess_checked);
		parent_sr_pane.setSelected(o.parent_sr_checked);
		fusion_pane.setSelected(o.fusion_checked); 
		support_checkbox.setSelected(o.entitlement_checked); //TODO is this correct? 
		invoice_pane.setSelected(o.invoice_checked);
		submitter_pane.setSelected(o.submitter_checked); 
		finacial_impact_pane.setSelected(o.financial_impact_checked);
		clca_pane.setSelected(o.clca_checked);
		os_checkbox.setSelected(o.os_checked);
		oracle_pane.setSelected(o.oracle_checked);
		sit_pane.setSelected(o.sit_checked);
		peripherals_pane.setSelected(o.peripherals_checked);
		raid_level_pane.setSelected(o.raid_checked);
		bios_pane.setSelected(o.bios_checked);
		hcrt_next_actions_pane.setSelected(o.hcrt_next_actions_checked); 
		customer_next_actions_pane.setSelected(o.customer_next_actions_checked);
		called_jtf.setText(o.called);
		RA.timestamp = o.timestamp;
		RA.selected_hashtag = o.selected_hashtag;
		system_model_pane.set_text(o.system_model);
		sr_pane.set_text(o.sr);
		issue_pane.set_text(o.issue);
		agent_desc_jta.setText(o.agent_desc);
		customer_desc_jta.setText(o.customer_desc);		
		recent_history_jta.setText(o.recent_history);
		dc_sess_id_pane.set_text(o.dc_sess);
		parent_sr_pane.set_text(o.parent_sr);
		fusion_pane.set_text(o.fusion);
		RA.selected_entitlement = o.selected_entitlement;
		RA.selected_architecture = o.selected_architecture;
		invoice_pane.set_text(o.invoice);
		submitter_pane.set_text(o.submitter);
		finacial_impact_pane.set_text(o.financial_impact);
		clca_pane.set_text(o.clca);
		RA.selected_os = o.selected_os;
		oracle_pane.set_text(o.oracle);
		sit_pane.set_text(o.sit);
		peripherals_pane.set_text(o.peripherals);
		raid_level_pane.set_text(o.raid);
		bios_pane.set_text(o.bios);
		steps_jta.setText(o.steps_taken);
		hcrt_next_actions_pane.set_text(o.hcrt_next_actions);
		customer_next_actions_pane.set_text(o.customer_next_actions);
		Notes.notes_jta.setText(o.notes);
		Call.steps_jta.setText(o.current_call);
		Call.called_jtf.setText(o.current_phone);
		Call.called_jcb.setSelected(o.current_call_checked);
		Call.timestamp = o.current_timestamp;
		RA.updateView();
		Call.updateView();
	}
	
	/**
	 * 
	 */
	public static void export() {
		//create dir 'export' if dne
		new File("./export").mkdirs();
		
		//determine file name
		String fileName = new SimpleDateFormat("yyyyMMddhhmm'.txt'").format(new Date());
		fileName = "./export/" + fileName;
		if (!sr_pane.get_text().trim().isEmpty()) {
			fileName = "./export/" + sr_pane.get_text().trim() + ".txt";
		}
		
		//create file if dne
		File f = new File(fileName);
		try {
			f.createNewFile(); //create file - do nothing if file exists
		} catch (IOException ex) { ex.printStackTrace(); }
		
		//write string to file
		try (PrintWriter out = new PrintWriter(fileName)) {
			String str = generate();
			for (String s : str.split("\n")) {
				out.println(s);	
			}
		} catch (FileNotFoundException e) { e.printStackTrace(); }
		
		//create obj
		Obj o = new Obj();
		o.called_checked = called_jcb.isSelected();
		o.system_model_checked = system_model_pane.isChecked();
		o.sr_checked = sr_pane.isChecked();
		o.issue_checked = issue_pane.isChecked();
		o.agent_desc_checked = agent_desc_jcb.isSelected();
		o.customer_desc_checked = customer_desc_jcb.isSelected();
		o.recent_history_checked = recent_history_jcb.isSelected();
		o.dc_sess_checked = dc_sess_id_pane.isChecked(); 
		o.parent_sr_checked = parent_sr_pane.isChecked(); 
		o.fusion_checked = fusion_pane.isChecked(); 
		o.entitlement_checked = support_checkbox.isSelected(); //TODO is this correct? 
		o.invoice_checked = invoice_pane.isChecked(); 
		o.submitter_checked = submitter_pane.isChecked(); 
		o.financial_impact_checked = finacial_impact_pane.isChecked(); 
		o.clca_checked = clca_pane.isChecked(); 
		o.os_checked = os_checkbox.isSelected(); 
		o.oracle_checked = oracle_pane.isChecked(); 
		o.sit_checked = sit_pane.isChecked(); 
		o.peripherals_checked = peripherals_pane.isChecked(); 
		o.raid_checked = raid_level_pane.isChecked(); 
		o.bios_checked = bios_pane.isChecked(); 
		o.hcrt_next_actions_checked = hcrt_next_actions_pane.isChecked(); 
		o.customer_next_actions_checked = customer_next_actions_pane.isChecked(); 
		o.called = called_jtf.getText();
		o.timestamp = RA.timestamp;
		o.selected_hashtag = RA.selected_hashtag; 
		o.system_model = system_model_pane.get_text();
		o.sr = sr_pane.get_text();
		o.issue = issue_pane.get_text();
		o.agent_desc = agent_desc_jta.getText();
		o.customer_desc = customer_desc_jta.getText();
		o.recent_history = recent_history_jta.getText();
		o.dc_sess = dc_sess_id_pane.get_text();
		o.parent_sr = parent_sr_pane.get_text();
		o.fusion = fusion_pane.get_text();
		o.selected_entitlement = RA.selected_entitlement;
		o.selected_architecture = RA.selected_architecture;
		o.invoice = invoice_pane.get_text();
		o.submitter = submitter_pane.get_text();
		o.financial_impact = finacial_impact_pane.get_text();
		o.clca = clca_pane.get_text();
		o.selected_os = RA.selected_os;
		o.oracle = oracle_pane.get_text();
		o.sit = sit_pane.get_text();
		o.peripherals = peripherals_pane.get_text();
		o.raid = raid_level_pane.get_text();
		o.bios = bios_pane.get_text();
		o.steps_taken = steps_jta.getText();
		o.hcrt_next_actions = hcrt_next_actions_pane.get_text();
		o.customer_next_actions = customer_next_actions_pane.get_text();
		o.notes = Notes.notes_jta.getText();
		o.current_call = Call.steps_jta.getText();
		o.current_phone = Call.called_jtf.getText();
		o.current_timestamp = Call.timestamp;
		o.current_call_checked = Call.called_jcb.isSelected();
		
		//add obj to store
		RA.store.add(o);
		
		//create/update serialized file
		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(new FileOutputStream("store.serialized"));
			oos.writeObject(RA.store);
		} catch (IOException e) { 
			e.printStackTrace(); 
		} finally {
			try { oos.close(); } 
			catch (Exception e) { e.printStackTrace(); }
		}
		
	}
	
	/**
	 * 
	 */
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
		if (timestamp.isEmpty()) {
			called_jb.setText("Add Timestamp");
			called_jb.setToolTipText(null);
		} else {
			called_jb.setText("Refresh");
			called_jb.setToolTipText(timestamp);
		}
		/* hashtag */
		hashtag_jcb.setSelectedIndex(selected_hashtag);
		/* agent description */
		if (agent_desc_jcb.isSelected()) {
			agent_desc_jta.setEditable(true);
			agent_desc_jta.setBackground(Color.WHITE);
			agent_description_pane.setBackground(RA.bleach(Color.BLUE, (float)0.85));
			agent_desc_jp.setBackground(RA.bleach(Color.BLUE, (float)0.85));
			agent_desc_jcb.setBackground(RA.bleach(Color.BLUE, (float)0.85));
		} else {
			agent_desc_jta.setEditable(false);
			JTextField tmp_jtf = new JTextField();
			tmp_jtf.setEditable(false);
			agent_desc_jta.setBackground(tmp_jtf.getBackground());
			agent_description_pane.setBackground(RA.bleach(Color.BLACK, (float)0.85));
			agent_desc_jp.setBackground(RA.bleach(Color.BLACK, (float)0.85));
			agent_desc_jcb.setBackground(RA.bleach(Color.BLACK, (float)0.85));
		}
		/* customer description */
		if (customer_desc_jcb.isSelected()) {
			customer_desc_jta.setEditable(true);
			customer_desc_jta.setBackground(Color.WHITE);
			customer_description_pane.setBackground(RA.bleach(Color.BLUE, (float)0.85));
			desc_jp.setBackground(RA.bleach(Color.BLUE, (float)0.85));
			customer_desc_jcb.setBackground(RA.bleach(Color.BLUE, (float)0.85));
		} else {
			customer_desc_jta.setEditable(false);
			JTextField tmp_jtf = new JTextField();
			tmp_jtf.setEditable(false);
			customer_desc_jta.setBackground(tmp_jtf.getBackground());
			customer_description_pane.setBackground(RA.bleach(Color.BLACK, (float)0.85));
			desc_jp.setBackground(RA.bleach(Color.BLACK, (float)0.85));
			customer_desc_jcb.setBackground(RA.bleach(Color.BLACK, (float)0.85));
		}
		/* recent service history */
		if (recent_history_jcb.isSelected()) {
			recent_history_jta.setEditable(true);
			recent_history_jta.setBackground(Color.WHITE);
			recent_history_pane.setBackground(RA.bleach(Color.BLUE, (float)0.85));
			recent_history_jp.setBackground(RA.bleach(Color.BLUE, (float)0.85));
			recent_history_jcb.setBackground(RA.bleach(Color.BLUE, (float)0.85));
		} else {
			recent_history_jta.setEditable(false);
			JTextField tmp_jtf = new JTextField();
			tmp_jtf.setEditable(false);
			recent_history_jta.setBackground(tmp_jtf.getBackground());
			recent_history_pane.setBackground(RA.bleach(Color.BLACK, (float)0.85));
			recent_history_jp.setBackground(RA.bleach(Color.BLACK, (float)0.85));
			recent_history_jcb.setBackground(RA.bleach(Color.BLACK, (float)0.85));
		}
		/* entitlement */
		if (support_checkbox.isSelected()) {
			support_pane.setBackground(RA.bleach(Color.BLUE, (float)0.85));
			support_checkbox.setBackground(RA.bleach(Color.BLUE, (float)0.85));
			entitlements_jcb.setEnabled(true);
			entitlements_jcb.setBackground(Color.WHITE);
		} else {
			support_pane.setBackground(RA.bleach(Color.BLACK, (float)0.85));
			support_checkbox.setBackground(RA.bleach(Color.BLACK, (float)0.85));
			entitlements_jcb.setEnabled(false);
			JComboBox<String> tmp_jcb = new JComboBox<String>();
			entitlements_jcb.setBackground(tmp_jcb.getBackground());
		}
		entitlements_jcb.setSelectedIndex(selected_entitlement);
		/* os */
		if (os_checkbox.isSelected()) {
			os_jcb.setEnabled(true);
			os_jcb.setBackground(Color.WHITE);
			architecture_jcb.setEnabled(true);
			architecture_jcb.setBackground(Color.WHITE);
			os_pane.setBackground(RA.bleach(Color.BLUE, (float)0.85));
			os_jp.setBackground(RA.bleach(Color.BLUE, (float)0.85));
			os_checkbox.setBackground(RA.bleach(Color.BLUE, (float)0.85));
		} else {
			JComboBox<String> tmp_jcb = new JComboBox<String>();
			os_jcb.setEnabled(false);
			os_jcb.setBackground(tmp_jcb.getBackground());
			architecture_jcb.setEnabled(false);
			architecture_jcb.setBackground(tmp_jcb.getBackground());
			os_pane.setBackground(RA.bleach(Color.BLACK, (float)0.85));
			os_jp.setBackground(RA.bleach(Color.BLACK, (float)0.85));
			os_checkbox.setBackground(RA.bleach(Color.BLACK, (float)0.85));
		}
		os_jcb.setSelectedIndex(selected_os);
		architecture_jcb.setSelectedIndex(selected_architecture);
	}
	
	/**
	 * 
	 * @return
	 */
	public static String generate() {
		StringBuilder sb = new StringBuilder();
		sb.append("SR# ");
		sb.append(sr_pane.get_text() + "\n\n");
		sb.append("SR Title:\n");
		sb.append("HCRT L3 Agent " + RA.agent_name + " Engaged : "
				+ system_model_pane.get_text() + " : "
				+ issue_pane.get_text() + " : "
				+ HASHTAGS[selected_hashtag] + "\n\n");
		if (customer_desc_jcb.isSelected())
		sb.append("Customer Description (Symptoms): "
				+ customer_desc_jta.getText() + "\n\n");
		if (agent_desc_jcb.isSelected())
		sb.append("Agent Description (Actual Problem): "
				+ agent_desc_jta.getText() + "\n\n");
		sb.append("** Summary **\n");
		if (called_jcb.isSelected())
		sb.append("Initial Call: " + called_jtf.getText() 
				+ " " + timestamp + "\n");
		if (dc_sess_id_pane.isChecked())
		sb.append("DellConnect/WebEx Session ID: " 
				+ dc_sess_id_pane.get_text() + "\n");
		if (parent_sr_pane.isChecked())
		sb.append("Parent SR#: " + parent_sr_pane.get_text() + "\n");
		if (fusion_pane.isChecked())
		sb.append("Fusion #: " + fusion_pane.get_text() + "\n\n");
		else 
		sb.append("\n");
		sb.append("~~Research Details~~\n");
		if (issue_pane.isChecked())
		sb.append("\t• Summary of Issue: " + issue_pane.get_text() + "\n");
		if (support_checkbox.isSelected())
		sb.append("\t• Support Entitlement: " + ENTITLEMENTS[selected_entitlement] + "\n");
		if (invoice_pane.isChecked())
		sb.append("\t• Invoice Date: " + invoice_pane.get_text() + "\n");
		if (recent_history_jcb.isSelected())
		sb.append("\t• Recent Service History (Past 6 Months): "
				+ recent_history_jta.getText() + "\n");
		if (submitter_pane.isChecked())
		sb.append("\t• Submitter: " + submitter_pane.get_text() + "\n");
		if (finacial_impact_pane.isChecked())
		sb.append("\t• Financial Impact: " + finacial_impact_pane.get_text() 
				+ "\n");
		if (oracle_pane.isChecked()) {
			sb.append("\t• Relevant Oracle KB Articles: ");
			boolean useGeneric = false;
			for (String s: HASHTAGS) {
				if (oracle_pane.get_text().equalsIgnoreCase(s)) {
					useGeneric = true;
				}
			}
			if (useGeneric == false) {
				sb.append(oracle_pane.get_text() + "\n");
			}
			else if (HASHTAGS[selected_hashtag] == "#ddpe") {
				sb.append("\n\t\t1. SLN292408 - Troubleshooting DDP | Enterprise "
						+ "Edition and Personal Edition\n"
						+ "\t\t2. SLN298374 - DDP | Personal Edition Removal "
						+ "Guide\n");
			} else if (HASHTAGS[selected_hashtag] == "#ddpst") {
				sb.append("\n\t\t1. SLN298864 - Increase/Enable Logging DDP|ST\n" 
						+ "\t\t2. SLN288377 - DDP|ST with SED does not complete "
						+ "the Encryption PBA process when configured in UEFI\n"
						+ "\t\t3. SLN297122 - How to remove DDP|ST that has been "
						+ "installed using master installer ddpsetup.exe\n");
			} else if (HASHTAGS[selected_hashtag] == "#ddpa") {
				sb.append("\n\t\t1. SLN285752 - DDP|Encryption and DDP|Access "
						+ "Should not Reside on The Same System\n");
			} else if (HASHTAGS[selected_hashtag] == "#pworkspace") {
				sb.append("\n\t\t1. SLN302687 - Uninstall Protected "
						+ "Workspace\n"
						+ "\t\t2. SLN296780 - Protected Workspace "
						+ "Configuration\n");
			} else if (HASHTAGS[selected_hashtag] == "#bitlocker") {
				sb.append("\n\t\t1. SLN300906 - Problem enabling BitLocker EXX70 "
						+ "with Windows 10\n" 
						+ "\t\t2. SLN298282 - BitLocker is prompting for a "
						+ "Recovery key\n");
			} else if (HASHTAGS[selected_hashtag] == "#linux") {
				sb.append("\n\t\t1. SLN289410 - Basic Linux Troubleshooting "
						+ "commands within Ubuntu Linux\n" 
						+ "\t\t2. SLN151664 - How to Install Ubuntu\n" 
						+ "\t\t3. HOW12112 - How to disable Secure Boot DKMS "
						+ "Signature Verification on Dell OEM Ubuntu 16.04\n" 
						+ "\t\t4. SLN303147- Limited Linux support for "
						+ "adapters/dongles/docks.\n"
						+ "\t\t5. SLN297234 - NVidia Video driver install in "
						+ "RHEL/CentOS 7.0 on the Precision Tower 5810/7810/7910\n");
			} else if (HASHTAGS[selected_hashtag] == "#chromeos") {
				sb.append("\n\t\t1. SLN293899 - Chromebook: How to recover and reset "
						+ "the Chrome OS\n");
			} else if (HASHTAGS[selected_hashtag] == "#widi") {
				sb.append("\n\t\t1. SLN302668 - Intel is no Longer Providing "
						+ "Updates to the Intel WiDi Adapter Software\n" 
						+ "\t\t2. SLN303384 - WiDi Driver Installation Order\n");
			} else if (HASHTAGS[selected_hashtag] == "#wigig") {
				sb.append("\n\t\t1. SLN151726 - General WiGig\n" 
						+ "\t\t2. SLN297279 - WLD15\n" 
						+ "\t\t3. SLN297123 - WiGig Docking on systems without "
						+ "Tri-band adapter\n");
			} else if (HASHTAGS[selected_hashtag] == "#advvideo") {
				sb.append("#advvideo\n");
			} else if (HASHTAGS[selected_hashtag] == "#displays") {
				sb.append("\n\t\t1. SLN129825 - How to Setup Multiple Monitors\n"
						+ "\t\t2. SLN151719 - Troubleshooting Multiple Monitor "
						+ "Issues\n");
			} else if (HASHTAGS[selected_hashtag] == "#raid") {
				sb.append("\n\t\t1. SLN303118 - AnswerFlow: RAID Configuration "
						+ "and Troubleshooting\n"
						+ "\t\t2. SLN301592 - Troubleshoot and Resolve RAID "
						+ "Issues on Dell Workstations\n"
						+ "\t\t3. SLN300773 - How to create a RAID with Intel, "
						+ "PERC and LSI Controllers\n");
			} else if (HASHTAGS[selected_hashtag] == "#imaging") {
				sb.append("#imaging\n");
			} else if (HASHTAGS[selected_hashtag] == "#dbrm") {
				sb.append("\n\t\t1. SLN298535 - http://kb.dell.com/infocenter/"
						+ "index?page=content&id=SLN298535&actp=SEARCH&"
						+ "viewlocale=en_US&searchid=1487174865027#D2 \n"
						+ "\t\t2. SLN155064 - Troubleshooting Dell BackUp and "
						+ "Recovery Manager (DBRM)\n");
			} else if (HASHTAGS[selected_hashtag] == "#malware") {
				sb.append("#malware\n");
			} else if (HASHTAGS[selected_hashtag] == "#other") {
				sb.append("#other\n");
			} else {
				//TODO
			}
		}
		if (sit_pane.isChecked())
		sb.append("\t• SIT: " + sit_pane.get_text() + "\n");
		/* sb.append("\t• Google (Include keywords): \n"); */ //TODO
		
		if (clca_pane.isChecked())
		sb.append("\t• CLCA Needed/Submitted?: " + clca_pane.get_text() + 
				"\n");
		
		sb.append("\n");
		sb.append("** Troubleshooting **\n");
		sb.append("~~System Information~~\n");
		if (bios_pane.isChecked())
		sb.append("\t• BIOS Version: " + bios_pane.get_text() + "\n");
		if (os_checkbox.isSelected()) {
			sb.append("\t• OS: " + OS[selected_os]);
			if (selected_architecture != 2) //exclude option
			sb.append(" " + ARCHITECTURE[selected_architecture]);
			sb.append("\n");			
		}
		if (peripherals_pane.isChecked()) 
		sb.append("\t• Peripherals: " + peripherals_pane.get_text() + "\n");
		/* sb.append("\t• DSET/Logs captured: None\n"); */ //TODO
		/* sb.append("\t• Updated Drivers: \n"); */ //TODO
		if (raid_level_pane.isChecked())
		sb.append("\t• RAID Level and Drive Configuration: " 
				+ raid_level_pane.get_text() + "\n\n");
		sb.append("~~Steps Taken~~\n");
		if (called_jcb.isSelected()) {
			sb.append("\tCalled customer at: " + called_jtf.getText() 
			+ " " + timestamp + "\n");
			sb.append("\tCall Log:\n");	
		}
		String steps = steps_jta.getText().replaceAll("[\\n\\r]", "\n\t");
		sb.append("\t" + steps + "\n\n");
		if (hcrt_next_actions_pane.isChecked() || 
				customer_next_actions_pane.isChecked())
		sb.append("** Next Steps **\n\n");
		if (hcrt_next_actions_pane.isChecked())
		sb.append("HCRT Next Actions: " + hcrt_next_actions_pane.get_text() 
				+ "\n");
		if (customer_next_actions_pane.isChecked())
		sb.append("Customer's Next Actions: " 
				+ customer_next_actions_pane.get_text() + "\n");
		return sb.toString();
	}
	
	/**
	 *
	 */
	public void initializeSpellcheck() {
		String dictionaryPath = ("./dictionary/");
		SpellChecker.setUserDictionaryProvider(new FileUserDictionary(dictionaryPath));
		SpellChecker.registerDictionaries(getClass().getResource(dictionaryPath), "en");
		SpellChecker.register(customer_desc_jta);
		SpellChecker.register(recent_history_jta);
		SpellChecker.register(called_jtf);
		SpellChecker.register(agent_desc_jta);
		SpellChecker.register(steps_jta);
		SpellChecker.enableShortKey(steps_jta, true);
		SpellChecker.register(Notes.notes_jta);
		SpellChecker.register(Call.steps_jta);
		//TODO ...
	}
	
	/**
	 * Output changelog notes if there is one on intial F10 press.
	 */
	//TODO not yet operational
	static void showChangelog() {
		for (Obj o : RA.store.get()) {
			if (o.sr.trim().equalsIgnoreCase("CHANGELOG") && !o.notes.isEmpty()) {
				JTextArea jta = new JTextArea(15, 35);
				jta.setLineWrap(true);
				jta.setWrapStyleWord(true);
				jta.setText(o.notes);
				JOptionPane.showMessageDialog(frame, new BubbleScrollPane(jta));
			}
		}
	}
}