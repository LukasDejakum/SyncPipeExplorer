package finished;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.tree.TreePath;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import aut.htlinn.ortner.spe.PortListener;

public class SyncPipeExplorerGUI {

	private JFrame frame;
	private Color lightBlue = new Color(0,210,250);
//	private static JPanel statusPanel = new JPanel(new BorderLayout());
	private static JLabel statusLabel = new JLabel();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DeviceManager.setSyncPipePropertiesFile(new File("M:/SyncPipe/SyncPipeProperties.txt"));
					DeviceManager.readInADBPath();
					doPortListening();
		            UIManager.setLookAndFeel("com.jtattoo.plaf.hifi.HiFiLookAndFeel");
					SyncPipeExplorerGUI window = new SyncPipeExplorerGUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @throws IOException 
	 * @throws SAXException 
	 * @throws ParserConfigurationException 
	 */
	public SyncPipeExplorerGUI() throws ParserConfigurationException, SAXException, IOException {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws IOException 
	 * @throws SAXException 
	 * @throws ParserConfigurationException 
	 */
	private void initialize() throws ParserConfigurationException, SAXException, IOException {
		frame = new JFrame();
		frame.setBounds(100, 100, 900, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		DevicePropertiesPanel infoPanel = new DevicePropertiesPanel();
		infoPanel.setPreferredSize(new Dimension(155,frame.getHeight()));
		JScrollPane scrollPane = new JScrollPane(infoPanel);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		frame.add(scrollPane, BorderLayout.EAST);
		
		statusLabel.setForeground(lightBlue);
		JPanel statusPanel = new JPanel(new BorderLayout());
		statusPanel.setPreferredSize(new Dimension(frame.getWidth(), 20));
		statusPanel.add(statusLabel);
		frame.add(statusPanel, BorderLayout.SOUTH);
		
//		JPanel filePanel = new JPanel();
//		filePanel.setLayout(new FlowLayout());
		
		JLabel label;
		ArrayList<String> imgs = searchForImages(FileSystems.getDefault().getPath("M:/Bilder"));
		for(String img:imgs){
			label = new JLabel(img, new ImageIcon(Toolkit.getDefaultToolkit().getImage(new File(img)
			.getAbsolutePath()).getScaledInstance(80, 80, Image.SCALE_FAST)), JLabel.CENTER);
			label.setPreferredSize(new Dimension(100,100));
//			filePanel.add(label);
		}
		//filePanel.add(new FileTable("M:/Bilder/Stubai_kicker"));

		//Thread createContacts = new Thread(new ContactsTable(filePanel));
		//createContacts.start();
		
		DeviceFileTree deviceTree = new DeviceFileTree();
		deviceTree.expandPath(new TreePath(deviceTree.getModel().getRoot()));
		
		MediaTabbedPane pane = new MediaTabbedPane(ComponentOrientation.LEFT_TO_RIGHT);
		
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, new JScrollPane(deviceTree), pane);
		splitPane.setDividerLocation(200);
		frame.add(splitPane, BorderLayout.CENTER);
//		frame.repaint();
	}

	private static void doPortListening(){
		Thread portListener = new Thread(new PortListener());
		portListener.start();	
	}
	private ArrayList<String> searchForImages(Path path){ 
//		GIF, JPEG, PNG
		ArrayList<String> imgs = new ArrayList<String>();
		if(new File(path.toString()).isDirectory()){
			try{ 
				DirectoryStream<Path> stream = Files.newDirectoryStream(path, "*.{gif,jpeg,png,jpg}");
			    for (Path file: stream) {
			    	System.out.println(file.toAbsolutePath()+file.getFileName().toString());
			        imgs.add(file.toAbsolutePath().toString());
			    }
			} catch (IOException e) {
			    // IOException can never be thrown by the iteration.
			    // In this snippet, it can only be thrown by newDirectoryStream.
			    System.err.println(e);
			}
		}
		else{
			System.out.println("Path is not a directory");
		}
		return imgs;
	}
	public static void setText(final String text){
		SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run() {
				System.out.println("update");
				statusLabel.setText(text);
			}
		});
	}
}
