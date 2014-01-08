package aut.htlinn.ortner.spe;

import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
 
public class JXMLTreeTest extends JFrame {
 
    private DefaultMutableTreeNode currentTreeNode = new DefaultMutableTreeNode();
    private long longSize;
    private ArrayList<VirtualFile> virtualFiles= new ArrayList<VirtualFile>();
    private String absolutePath = "";

    public JXMLTreeTest() throws ParserConfigurationException, SAXException, IOException{
    	DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
    	DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
    	Document document = docBuilder.parse(new File("files/test.xml"));
    	DefaultMutableTreeNode root = new DefaultMutableTreeNode("root");
    	JTree tree = new JTree(new DefaultTreeModel(root));
    	createSubDirs(document.getDocumentElement(),root);
    	setVisible(true);
    	setSize(new Dimension(500, 500));
    	add(new JScrollPane(tree));
    	System.out.println(absolutePath);
    }
    public static void main(String[] args){
    	try {
			new JXMLTreeTest();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

	private void createSubDirs(Node node, DefaultMutableTreeNode treeNode) {
		NodeList nodeList = node.getChildNodes();
		for (int i=0; i<nodeList.getLength(); i++) {
			if (nodeList.item(i).getNodeName()=="d") {
					currentTreeNode = new DefaultMutableTreeNode(((Element) nodeList.item(i)).getAttribute("name"));
					treeNode.add(currentTreeNode);
					
					for(TreeNode tnode:currentTreeNode.getPath()){
						absolutePath+=tnode+"/";
					}
					//System.out.print("\n");
					virtualFiles.add(new VirtualFile(absolutePath,((Element) nodeList.item(i)).getAttribute("name"),
							Long.parseLong(((Element) nodeList.item(i)).getAttribute("size")), ((Element) nodeList.item(i)).getAttribute("date"), true));
					createSubDirs(nodeList.item(i), currentTreeNode);
					
		    }
			else if(nodeList.item(i).getNodeName()=="f"){
				currentTreeNode = new DefaultMutableTreeNode(((Element) nodeList.item(i)).getAttribute("name"));
				treeNode.add(currentTreeNode);
				for(TreeNode tnode:currentTreeNode.getPath()){
					System.out.print("/"+tnode);
				}
				System.out.print("\n");
			}
		}
	}
	private String getFileSize(Node node){
		longSize=Long.parseLong(((Element) node).getAttribute("size"));
		if(longSize<1024){
			return "("+longSize+" B)";
		}
		else if(longSize<(1024*1024)){
			return "("+longSize/1024+" KB)";
		}
		else{
			return "("+longSize/(1024*1024)+" MB)";
		}
	}
}
