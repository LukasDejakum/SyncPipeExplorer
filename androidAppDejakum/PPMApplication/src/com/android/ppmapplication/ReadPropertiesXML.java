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

import android.R.integer;
import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class ReadPropertiesXML extends Service{
	private static final String TAG = "ReadPropertiesXML";

	public IBinder onBind(Intent intent) {
		return null;
	}
	
	@Override
	public void onCreate() {
		Toast.makeText(this, "My Service ReadProperties Created ", Toast.LENGTH_LONG).show();
		Log.d(TAG, "onCreate Properties read properties");
		
		this.initialize();
	}

	@Override
	public void onDestroy() {
		//Toast.makeText(this, "My Service Stopped Contacts", Toast.LENGTH_SHORT).show();
		Log.d(TAG, "onDestroy Properties read properties");
		
	}
	
	@Override
	public void onStart(Intent intent, int startid) {
		//Toast.makeText(this, "My Service Started Contacts read", Toast.LENGTH_SHORT).show();
		Log.d(TAG, "onStart Properties read properteis");
	}

	public void initialize(){
        Thread th = new Thread(new Runnable() {

            @Override
            public void run() {
            	
            	try {Thread.sleep(30000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
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
    	String propertiesFileNameString = "properties.xml";

		    try {
		    	
				document = docBuilder.parse(new File(dir+File.separator+propertiesFileNameString));
				readProperties(document.getDocumentElement());
				
			} catch (SAXException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (ParserConfigurationException e1) {
			e1.printStackTrace();
		}
	}

	public void readProperties(Element doc) {
		
		
		NodeList rootNodes = doc.getElementsByTagName("properties");
		int index = 0;
		NodeList nodes = rootNodes.item(index).getChildNodes();
		NodeList childNodes;
		
		String surenameString;
		String numberString;
		String eMailString;
		
		for(int i=0; i<nodes.getLength(); i++){
			
			
			childNodes = nodes.item(i).getChildNodes();
			
//			surenameString="";
//			numberString="";
//			eMailString="";
			
			System.out.println(childNodes.item(i).getTextContent());

			
			/*boolean goON;
			
			do{
				
				
				goON=false;
//				System.out.println("test");
			
				if(nodes.item(i).getChildNodes()!=null){
					
					System.out.println(childNodes.item(i).getTextContent());
					nodes = childNodes;
					goON=true;
					
					System.out.println("test");
					
				}
			
			}while(goON);*/
			
			for(int j=0; j<nodes.item(i).getChildNodes().getLength(); j++){
				if(childNodes.item(j).getNodeName().equals("surename")){
					
//					System.out.println("Test");
					
					surenameString=((Element) childNodes.item(j)).getTextContent();
					
				}
				else if(childNodes.item(j).getNodeName().equals("number")){
					
					numberString = ((Element) childNodes.item(j)).getTextContent();
					
				}
				else if(childNodes.item(j).getNodeName().equals("eMail")){
					
					eMailString = ((Element) childNodes.item(j)).getTextContent();
					
				}
				
			}

			for(int k=0; k<index;k++){

				System.out.println("Test");
				childNodes = doc.getElementsByTagName(childNodes.item(0).getParentNode().getNodeName());
			}

			
//			System.out.println("email:"+eMailString);
			
			
			
//			addContact(surenameString, numberString, eMailString);

			
		}
		//KONTAKTE ERSTELLEN
		//addContact("test", "06500000001", "email@");
	}



}

