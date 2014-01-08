package finished;

import java.io.File;
import java.io.IOException;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
 
public class DeviceFileTree extends JTree {
 
    private DefaultMutableTreeNode currentTreeNode = new DefaultMutableTreeNode();

    public DeviceFileTree() throws ParserConfigurationException, SAXException, IOException{
    	DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
    	DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
    	Document document = docBuilder.parse(new File("files/test.xml"));
    	DefaultMutableTreeNode root = new DefaultMutableTreeNode("root");
    	setModel(new DefaultTreeModel(root));
    	createSubDirs(document.getDocumentElement(),root);
    }
    public static void main(String[] args){
    	try {
			new DeviceFileTree();
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
	for (int i = 0; i < nodeList.getLength(); i++) {
		
		if (nodeList.item(i).getNodeName().equals("d")) {
				currentTreeNode = new DefaultMutableTreeNode(((Element) nodeList.item(i)).getAttribute("name"));
				treeNode.add(currentTreeNode);
				createSubDirs(nodeList.item(i), currentTreeNode);
	    }
		else if(nodeList.item(i).getNodeName().equals("f")){
			treeNode.add(new DefaultMutableTreeNode(((Element) nodeList.item(i)).getAttribute("name")));
		}
	}
}
}
