package com.android.ppmapplication;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import android.app.Service;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.os.IBinder;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Toast;

public class ReadContactsXML extends Service{
	private static final String TAG = "ReadContactsXML";

	public IBinder onBind(Intent intent) {
		return null;
	}
	
	@Override
	public void onCreate() {
//		Toast.makeText(this, "My Service Created", Toast.LENGTH_LONG).show();
		Log.d(TAG, "onCreate Contacts read");
	}

	@Override
	public void onDestroy() {
		Toast.makeText(this, "My Service Stopped Contacts", Toast.LENGTH_SHORT).show();
		Log.d(TAG, "onDestroy Contacts read");
		
	}
	
	@Override
	public void onStart(Intent intent, int startid) {
		Toast.makeText(this, "My Service Started Contacts read", Toast.LENGTH_SHORT).show();
		Log.d(TAG, "onStart Contacts read");
		
		this.initialize();	
	}

	public void initialize(){
        Thread th = new Thread(new Runnable() {

            @Override
            public void run() {
            	
            	readXML();
        		stopSelf();
        	} 
        });
        th.start();
    }

	public void readXML(){
        //EINLESEN
	      
	    DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
	    DocumentBuilder docBuilder;
		try {
			docBuilder = docBuilderFactory.newDocumentBuilder();
		
	    Document document = docBuilder.newDocument();
	    
	    File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/SyncPipe/");
    	String contactsFileNameString = "contact.xml";

		    try {
		    	
				document = docBuilder.parse(new File(dir+File.separator+contactsFileNameString));
				readContacts(document.getDocumentElement());
				
			} catch (SAXException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (ParserConfigurationException e1) {
			e1.printStackTrace();
		}
	}

	public void readContacts(Element doc) {
		
		NodeList contacts = doc.getElementsByTagName("contact");
		NodeList childNodes;
		
		for(int i=0; i<contacts.getLength(); i++){
			childNodes = contacts.item(i).getChildNodes();
			
			System.out.println("Kontakt: ");
			 
			for(int j=0; j<contacts.item(i).getChildNodes().getLength(); j++){
				if(childNodes.item(j).getNodeName().equals("surename")){
//					System.out.println(((Element) childNodes.item(j)).getTextContent());
					
					String surenameString=((Element) childNodes.item(j)).getTextContent();
					System.out.println(surenameString);
					
					
				}
				else if(childNodes.item(j).getNodeName().equals("number")){
					System.out.println(((Element) childNodes.item(j)).getTextContent());
				}
				else if(childNodes.item(j).getNodeName().equals("eMail")){
					System.out.println(((Element) childNodes.item(j)).getTextContent());
				}
				//KONTAKTE ERSTELLEN
		        
				//createNewContact("Test","000000");
				
//				System.out.println("\n");
			}
		}
	}
	
	private void deleteAllContact() {
		
		ContentResolver cr = getContentResolver();
	    Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
	            null, null, null, null);
	    while (cur.moveToNext()) {
	        try{
	            String lookupKey = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.LOOKUP_KEY));
	            Uri uri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_LOOKUP_URI, lookupKey);
	            System.out.println("The uri is " + uri.toString());
	            cr.delete(uri, null, null);
	        }
	        catch(Exception e)
	        {
	            System.out.println(e.getStackTrace());
	        }
	    }
	}
}

