import com.melloware.jintellitype.*;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import javax.swing.AbstractAction;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.apache.commons.io.FileUtils;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

/**
 * Launcher for program. 
 * 
 * @author Trent
 * @version 1.4
 */
public class Launcher {
	
	static final double VERSION = 1.4;
	RA ra = new RA();
	ClosingFrame cf = new ClosingFrame();
	
	/**
	 * 
	 */
	public Launcher() {
		/* listens for hotkeys and launches the appropriate frame */
		JIntellitype.getInstance().addHotKeyListener(new HotkeyListener() {
			@Override public void onHotKey(int identifier) {
				if (identifier == 1) { ra.toggle(); }
				else if (identifier == 2) { cf.toggle(); }
				else if (identifier == 3) {
					RA.export();
					System.exit(0);
				}
				else if (identifier == 4) { checkForUpdates(); }
		}});
		JIntellitype.getInstance().addIntellitypeListener(new IntellitypeListener() {
			@Override public void onIntellitype(int arg0) {
		}});
		JIntellitype.getInstance().registerHotKey(1, 0, 121); // F10 = TOGGLE RA
		JIntellitype.getInstance().registerHotKey(2, 0, 122); // F11 = TOGGLE CLOSURE
		JIntellitype.getInstance().registerHotKey(3, 
				JIntellitype.MOD_CONTROL, 121); // CTRL + F10 = QUIT
		JIntellitype.getInstance().registerHotKey(4, 
				JIntellitype.MOD_CONTROL, 113); // CTRL + F2 = UPDATES
	}
	
	/* check web for program updates */
	public void checkForUpdates() {
		try { new HTTPXML().start(); } 
		catch (Exception e) {
            JOptionPane.showMessageDialog(RA.frame, "Try again later.", 
            		"HCRT: RA Tool Update", JOptionPane.WARNING_MESSAGE);
			e.printStackTrace(); 
		}
	}
	
	/**
	 * main method. Instantiates 'Launcher' 
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		new Launcher();
	}
}

/**
 * Parse xml for version. Source: 
 * http://stackoverflow.com/questions/3058434/xml-parse-file-from-http
 * 
 * @author Favonius
 *
 */
class HTTPXML {
	/* start */
	void start() throws Exception {
		/* configure to use system proxy settings */
		//TODO System.setProperty("java.net.useSystemProxies", "true");
        URL url = new URL("http://trenthome.duckdns.org:8000/hcrt/tool/version.xml");
        URLConnection connection = url.openConnection();
        Document doc = parseXML(connection.getInputStream());
        NodeList node = doc.getElementsByTagName("VERSION");
        double latestVersion = Double.parseDouble(node.item(0).getTextContent());
        if (Launcher.VERSION < latestVersion) {
        	int selection = JOptionPane.showConfirmDialog(RA.frame, "Download New Version " + node.item(0).getTextContent()
        	+ "?", 
        	"HCRT: RA Tool Update", 
        	JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        	if (selection == JOptionPane.YES_OPTION) {
        		FileUtils.copyURLToFile(
        				new URL("http://trenthome.duckdns.org:8000/hcrt/tool/hcrttool.jar"),
        				new File("./hcrttool_v" + node.item(0).getTextContent() + ".jar"));
        		/* downloading... message */
				final JOptionPane optionPane = new JOptionPane("<html><i>Downloading...</i></html>", 
						JOptionPane.INFORMATION_MESSAGE, 
						JOptionPane.DEFAULT_OPTION, null, new Object[]{}, null);
				final JDialog dialog = new JDialog();
				dialog.setTitle("HCRT: RA Tool Update");
				dialog.setAlwaysOnTop(true);
				dialog.setModal(true);
				dialog.setContentPane(optionPane);
				dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
				dialog.pack();
				dialog.setLocationRelativeTo(RA.frame);
				Timer timer = new Timer(3000, new AbstractAction() {
					private static final long serialVersionUID = 1L;
					@Override public void actionPerformed(ActionEvent ae) {
				        dialog.dispose();
				        //complete... message
				        Object[] options = {"OK"};
				        int n = JOptionPane.showOptionDialog(RA.frame, 
				        		"<html><u>Success!</u><br>Quit <i>(Ctrl-Q)</i> and Launch hcrttool_v" + 
				        				node.item(0).getTextContent() + ".jar.</html>", 
				        		"trentfowler.com", 
				        		JOptionPane.PLAIN_MESSAGE, 
				        		JOptionPane.INFORMATION_MESSAGE, 
				        		null, 
				        		options, 
				        		options[0]
				        );
				        //quit
				        if (n == 1) {
							RA.export();
							System.exit(0);
				        }
				    }
				});
				timer.setRepeats(false);
				timer.start();
				dialog.setVisible(true);
        	}
        } else {
            JOptionPane.showMessageDialog(RA.frame, "No updates available.", 
            		"HCRT: RA Tool Update", JOptionPane.INFORMATION_MESSAGE);
        }
    }
	/* parse */
	private Document parseXML(InputStream stream) throws Exception {
		DocumentBuilderFactory objDocumentBuilderFactory = null;
		DocumentBuilder objDocumentBuilder = null;
		Document doc = null;
		try {
			objDocumentBuilderFactory = DocumentBuilderFactory.newInstance();
			objDocumentBuilder = objDocumentBuilderFactory.newDocumentBuilder();
			doc = objDocumentBuilder.parse(stream);
		}
        catch(Exception ex) { throw ex; }
		return doc;
    }
}
