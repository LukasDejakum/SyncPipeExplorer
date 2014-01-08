package finished;

import java.awt.BorderLayout;
import java.awt.Container;
import java.io.File;
import java.io.IOException;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ContactsTable extends JTable{

	private Container container;
	private Document document;
	private ContactChangedListener contactsListener;
	
	public ContactsTable(Container container) throws ParserConfigurationException, SAXException, IOException{
    	this.container=container;

		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
    	DocumentBuilder docBuilder;
    	
		try {
			docBuilder = docBuilderFactory.newDocumentBuilder();
//			document = docBuilder.parse(new File("M:/SyncPipe/contact.xml"));
			document = docBuilder.parse(DeviceManager.getContactsFile());
			container.add(this);
		} catch (SAXException | IOException e) {
			e.printStackTrace();
		}catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		NodeList contacts = document.getElementsByTagName("contact");
		NodeList childNodes;
		
		Object[][] data = new Object[contacts.getLength()][4];
		
		for(int i=0; i<contacts.getLength(); i++){
			childNodes = contacts.item(i).getChildNodes();
			for(int j=0; j<contacts.item(i).getChildNodes().getLength(); j++){
				if(childNodes.item(j).getNodeName().equals("surename")){
					data[i][0] = childNodes.item(j).getTextContent();
				}
				else if(childNodes.item(j).getNodeName().equals("number")){
					data[i][2] = childNodes.item(j).getTextContent();
				}
				else if(childNodes.item(j).getNodeName().equals("eMail")){
					data[i][3] = childNodes.item(j).getTextContent();
				}
			}
		}
		String[] columnNames = {"Vorname", "Nachname", "Nummer","eMail"};
		DefaultTableModel model = new DefaultTableModel(data, columnNames);
		System.out.println(container.getSize());
		this.setSize(container.getSize());
		contactsListener = new ContactChangedListener();
		model.addTableModelListener(contactsListener);
		this.setModel(model);
		container.setLayout(new java.awt.BorderLayout());
		container.add(new JScrollPane(this), BorderLayout.CENTER);
//		this.repaint();
	}
	//important for syncing
	public boolean contactsHaveChanged(){
		return contactsListener.contactsHaveChanged();
	}
}
