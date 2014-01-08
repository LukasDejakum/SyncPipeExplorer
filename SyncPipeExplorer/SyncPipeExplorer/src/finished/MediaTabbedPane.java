package finished;

import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public class MediaTabbedPane extends JTabbedPane{

	// own classes for each panel must be created !!!
//	private JPanel picturePanel = new FilePanel(new File("M:/Bilder").listFiles());
	private JPanel picturePanel = new JPanel();
	private JPanel contactsPanel = new JPanel();
	private JPanel vidsPanel = new JPanel();
	private JPanel musicPanel = new JPanel();
	private JPanel appsPanel = new JPanel();
	
	public MediaTabbedPane(ComponentOrientation orientation) throws ParserConfigurationException, SAXException, IOException{
		JFileChooser chooser = new JFileChooser();
		Component c = (chooser.getComponent(0));
		c.setBackground(Color.GREEN);
		picturePanel.add(c);
		
//		new Thread() {
//			public void run() {
//				try {
//					new ContactsTable(contactsPanel);
//				} catch (ParserConfigurationException | SAXException
//						| IOException e) {
//					e.printStackTrace();
//				}
//			}
//		}.start();
		
		new ContactsTable(contactsPanel);
		this.setComponentOrientation(orientation);
		c.setPreferredSize(new Dimension(this.getWidth(),this.getHeight()));
		addTab("<html><body><table width='60'><tr><td>Kontakte</td></tr></table></body></html>", contactsPanel);
	}
}
