package com.android.ppmapplication;

import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.util.Log;
import android.view.Menu;


public class MainActivity extends Activity {
	
	private static final String TAG = "MainActivity";
	 
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		SensorManager mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);

		
		/**/
            
            
       Log.d(TAG, "onClick: starting service contacts");
       startService(new Intent(this, CreateContactsXML.class));
       
       Log.d(TAG, "onClick: starting service properties");
       startService(new Intent(this, CreatePropertiesXML.class));               
            
            
	    List<Sensor> sensorList = mSensorManager.getSensorList(Sensor.TYPE_ALL);

	    Log.w(TAG, "sl size = " + sensorList.size());
	    for(int i=0;i<sensorList.size();i++) {
	        Log.w(TAG, "sn = " + sensorList.get(i).getName());
	    }

	    
	    
	    
	    
	    
	    
  
	  
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	        //EINLESEN
	      
		    DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		    DocumentBuilder docBuilder;
			try {
				docBuilder = docBuilderFactory.newDocumentBuilder();
			
		    Document document = docBuilder.newDocument();
		    
		    File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
        	String contactsFileNameString = "contact.xml";

			    try {
			    	
					document = docBuilder.parse(new File(dir+File.separator+contactsFileNameString));
//					readContacts(document.getDocumentElement());
					
				} catch (SAXException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} catch (ParserConfigurationException e1) {
				e1.printStackTrace();
			}
		    	
	}

      
	  /*  private final SensorEventListener TemperatureSensorListener
	     = new SensorEventListener(){

	   @Override
	   public void onAccuracyChanged(Sensor sensor, int accuracy) {
	    // TODO Auto-generated method stub
	    
	   }

	   @Override
	   public void onSensorChanged(SensorEvent event) {
	    if(event.sensor.getType() == Sensor.TYPE_TEMPERATURE){
	        Log.i(TAG, "TEMPERATURE: " + event.values[0]);
	    }
	   }
	     
	    };
	    
	    private final SensorEventListener AmbientTemperatureSensorListener
	     = new SensorEventListener(){

		   @Override
		   public void onAccuracyChanged(Sensor sensor, int accuracy) {
		    // TODO Auto-generated method stub
		   
		   }
	
		   @Override
		   public void onSensorChanged(SensorEvent event) {
		    if(event.sensor.getType() == Sensor.TYPE_AMBIENT_TEMPERATURE){
		        Log.i(TAG, "AMBIENT TEMPERATURE: " + event.values[0]);
		    }
		   }
	 
	   };
		*/
	
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	private void createSubDirs(Node node) {
		NodeList nodeList = node.getChildNodes();
		Log.i(TAG,Integer.toString(nodeList.getLength()));
		Log.i(TAG, nodeList.item(0).getNodeName());
		//Log.i(TAG, nodeList.item(index))
		for (int i = 0; i < nodeList.getLength(); i++) {
			
		/*	
			if (nodeList.item(i).getNodeName()=="d") {
					currentTreeNode = new DefaultMutableTreeNode(((Element) nodeList.item(i)).getAttribute("name"));
					treeNode.add(currentTreeNode);
					createSubDirs(nodeList.item(i), currentTreeNode);
		    }
			else if(nodeList.item(i).getNodeName()=="f"){
				treeNode.add(new DefaultMutableTreeNode(((Element) nodeList.item(i)).getAttribute("name")));
			}
		*/
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
			}
			
			//KONTAKTE ERSTELLEN
	        
			//createNewContact("Test","000000");
			
//			System.out.println("\n");
		}
	}
	
	@SuppressWarnings("deprecation")
	private void createNewContact(String nameString, String numberString){
		
		ContentValues personValues = new ContentValues();
		personValues.put(Contacts.People.NAME, nameString);
		/* STARRED 0 = Contacts, 1 = Favorites */
//		personValues.put(Contacts.People.STARRED, 1);

		Uri newPersonUri = Contacts.People
		  .createPersonInMyContactsGroup(getContentResolver(), personValues);

		if (newPersonUri != null) {

			ContentValues mobileValues = new ContentValues();
			Uri mobileUri = Uri.withAppendedPath(newPersonUri,
					Contacts.People.Phones.CONTENT_DIRECTORY);
			mobileValues.put(Contacts.Phones.NUMBER,
					numberString);
			mobileValues.put(Contacts.Phones.TYPE,
					Contacts.Phones.TYPE_MOBILE);
			Uri phoneUpdate = getContentResolver()
					.insert(mobileUri, mobileValues);
			
			if (phoneUpdate == null) {
				Log.i(TAG, "error insert number");
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

