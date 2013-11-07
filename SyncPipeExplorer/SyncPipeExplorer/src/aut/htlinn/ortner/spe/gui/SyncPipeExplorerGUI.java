package aut.htlinn.ortner.spe.gui;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Label;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import aut.htlinn.ortner.spe.PortListener;

public class SyncPipeExplorerGUI {

	private JFrame frame;
	private JTree fileTree = new JTree();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					doPortListening();
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
	 */
	public SyncPipeExplorerGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 650, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel infoPanel = new JPanel();
		infoPanel.setPreferredSize(new Dimension(50,frame.getHeight()));
		frame.getContentPane().add(infoPanel, BorderLayout.EAST);
		
		JSplitPane splitPane = new JSplitPane();
		frame.getContentPane().add(splitPane, BorderLayout.CENTER);
		
		JPanel treePanel = new JPanel();
		treePanel.setPreferredSize(new Dimension(300,frame.getHeight()));
//		splitPane.setLeftComponent(treePanel);
		
		buildTree(fileTree);
//		treePanel.add(new JScrollPane(fileTree));
		splitPane.setLeftComponent(new JScrollPane(fileTree));
		splitPane.setDividerLocation(splitPane.getSize().width
                - 400);
		
		JPanel filePanel = new JPanel();
		splitPane.setRightComponent(filePanel);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JButton btnInfo = new JButton("Info");
		menuBar.add(btnInfo);
		
		JLabel label;
		ArrayList<String> imgs = searchForImages(FileSystems.getDefault().getPath("M:/Bilder/Stubai_kicker"));
		for(String img:imgs){
			label = new JLabel(img, new ImageIcon(Toolkit.getDefaultToolkit().getImage(new File(img)
			.getAbsolutePath()).getScaledInstance(80, 80, Image.SCALE_FAST)), JLabel.CENTER);
			label.setPreferredSize(new Dimension(100,100));
			filePanel.add(label);
		}
	}
	private void buildTree(JTree tree){
		DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("PC");
		File[] roots = File.listRoots();
		DefaultMutableTreeNode[] rootNodes = new DefaultMutableTreeNode[roots.length];
		
		for(int i=0; i<roots.length; i++){
			rootNode.add(new DefaultMutableTreeNode(roots[i].toString()));
		}
		tree.setModel(new DefaultTreeModel(rootNode));				
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

}
