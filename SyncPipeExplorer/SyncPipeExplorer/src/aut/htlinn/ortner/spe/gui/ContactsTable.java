package aut.htlinn.ortner.spe.gui;

import java.awt.BorderLayout;
import java.awt.Container;
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

import finished.ContactChangedListener;
import aut.htlinn.ortner.spe.AndroidFilesHandler;

public class ContactsTable extends JTable implements Runnable{

	private Container container;
	private Document document;
	private ContactChangedListener contactsListener;
	
	public ContactsTable(Container container) throws ParserConfigurationException, SAXException, IOException{
    	this.container=container;
	}
	@Override
	public void run() {
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
    	DocumentBuilder docBuilder;
    	
		try {
			docBuilder = docBuilderFactory.newDocumentBuilder();
			document = docBuilder.parse(AndroidFilesHandler.getContactsFile());
//			readContacts(document.getDocumentElement());
			container.add(this);
		} catch (SAXException | IOException e) {
			e.printStackTrace();
		}
		catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		
		NodeList contacts = document.getElementsByTagName("contact");
		NodeList childNodes;
		
		Object[][] data = new Object[contacts.getLength()][4];
		
		for(int i=0; i<contacts.getLength(); i++){
			childNodes = contacts.item(i).getChildNodes();
			System.out.println("Kontakt: ");
			for(int j=0; j<contacts.item(i).getChildNodes().getLength(); j++){
				if(childNodes.item(j).getNodeName().equals("surename")){
//					System.out.println(((Element) childNodes.item(j)).getAttribute("value"));
					data[i][0] = ((Element) childNodes.item(j)).getAttribute("value");
				}
				else if(childNodes.item(j).getNodeName().equals("number")){
//					System.out.println(((Element) childNodes.item(j)).getAttribute("value"));
					data[i][2] = ((Element) childNodes.item(j)).getAttribute("value");
				}
				else if(childNodes.item(j).getNodeName().equals("eMail")){
//					System.out.println(((Element) childNodes.item(j)).getAttribute("value"));
					data[i][3] = ((Element) childNodes.item(j)).getAttribute("value");
				}
			}
			System.out.println("\n");
		}
		String[] columnNames = {"Vorname", "Nachname", "Nummer","eMail"};
		DefaultTableModel model = new DefaultTableModel(data, columnNames);
		this.setSize(container.getSize());
		contactsListener = new ContactChangedListener();
		model.addTableModelListener(contactsListener);
		this.setModel(model);
		container.setLayout(new java.awt.BorderLayout());
		container.add(new JScrollPane(this), BorderLayout.CENTER);
	}
	//important for syncing
	public boolean contactsHaveChanged(){
		return contactsListener.contactsHaveChanged();
	}
}
