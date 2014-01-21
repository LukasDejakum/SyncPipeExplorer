package com.android.ppmapplication;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import android.app.Service;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.os.IBinder;
import android.os.RemoteException;
import android.provider.Contacts;
import android.provider.Contacts.People;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.Data;
import android.util.Log;
import android.widget.Toast;

public class ReadContactsXML extends Service{
	private static final String TAG = "ReadContactsXML";

	public IBinder onBind(Intent intent) {
		return null;
	}
	
	@Override
	public void onCreate() {
		Toast.makeText(this, "My Service ReadContacts Created ", Toast.LENGTH_LONG).show();
		Log.d(TAG, "onCreate Contacts read");
		
		this.initialize();
	}

	@Override
	public void onDestroy() {
		//Toast.makeText(this, "My Service Stopped Contacts", Toast.LENGTH_SHORT).show();
		Log.d(TAG, "onDestroy Contacts read");
		
	}
	
	@Override
	public void onStart(Intent intent, int startid) {
		//Toast.makeText(this, "My Service Started Contacts read", Toast.LENGTH_SHORT).show();
		Log.d(TAG, "onStart Contacts read");
	}

	public void initialize(){
        Thread th = new Thread(new Runnable() {

            @Override
            public void run() {
            	
            	try {Thread.sleep(15000);
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
		
		//deleteAllContact();
		
		String surenameString;
		String numberString;
		String eMailString;
		
		for(int i=0; i<contacts.getLength(); i++){
			childNodes = contacts.item(i).getChildNodes();
			
			surenameString="";
			numberString="";
			eMailString="";
			
			for(int j=0; j<contacts.item(i).getChildNodes().getLength(); j++){
				if(childNodes.item(j).getNodeName().equals("surename")){
					
					surenameString=((Element) childNodes.item(j)).getTextContent();
					
				}
				else if(childNodes.item(j).getNodeName().equals("number")){
					
					numberString = ((Element) childNodes.item(j)).getTextContent();
					
				}
				else if(childNodes.item(j).getNodeName().equals("eMail")){
					
					eMailString = ((Element) childNodes.item(j)).getTextContent();
					
				}
				
			}

			System.out.println("contact:"+surenameString);
			System.out.println("number:"+numberString);
			System.out.println("email:"+eMailString);
			
		}
		//KONTAKTE ERSTELLEN
		addContact("test", "06500000001", "email@");
	}
	
	private void deleteAllContact() {

		
		ContentResolver cr = getContentResolver();
	    Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
	            null, null, null, null);
	    
	    while (cur.moveToNext()) {
	        try{
	            String lookupKey = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.LOOKUP_KEY));
	            Uri uri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_LOOKUP_URI, lookupKey);
	            cr.delete(uri, null, null);
	        }
	        catch(Exception e)
	        {
	            System.out.println(e.getStackTrace());
	        }
	    }
	    
	}
	
	
	
/*
	private void createNewContact(String nameString, String numberString){
		
		System.out.println("create contact");
		


		ArrayList<ContentProviderOperation> ops =
		      new ArrayList<ContentProviderOperation>();
		ops.add(ContentProviderOperation.newInsert(Data.CONTENT_URI)
		      .withValue(Data.RAW_CONTACT_ID, 200)
		      .withValue(Data.MIMETYPE, Phone.CONTENT_ITEM_TYPE)
		      .withValue(Phone.NUMBER, "1-800-GOOG-411")
		      .withValue(Phone.TYPE, Phone.TYPE_CUSTOM)
		      .withValue(Phone.LABEL, "free directory assistance")
		      .build()); 
		try {
			getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OperationApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		
		/*ContentValues values = new ContentValues();
        values.put(Data.RAW_CONTACT_ID, 200);
        values.put(Data.MIMETYPE, Phone.CONTENT_ITEM_TYPE);
        values.put(Phone.NUMBER, "1-800-GOOG-411");
        values.put(Phone.TYPE, Phone.TYPE_CUSTOM);
        values.put(Phone.LABEL, "Nirav");
        values.put(Phone.DISPLAY_NAME_PRIMARY, "Nirav");
        values.put(Phone.DISPLAY_NAME, "Nirav");
        values.put(Data.DISPLAY_NAME, "Nirav");


        Uri dataUri = getContentResolver().insert(android.provider.ContactsContract.Data.CONTENT_URI, values);
		
		/*
		ContentValues personValues = new ContentValues();
		personValues.put(Contacts.People.NAME, nameString);
		// STARRED 0 = Contacts, 1 = Favorites 
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
	}*/
	
	@SuppressWarnings({"deprecation" })
	private void addContact(String name, String phone, String eMail) {
		
		System.out.println("add contact");
		
        ContentValues values = new ContentValues();
        values.put(People.NUMBER, phone);
        values.put(People.TYPE, Phone.TYPE_CUSTOM);
        values.put(People.LABEL, name);
        values.put(People.NAME, name);
       
        Uri dataUri = getContentResolver().insert(People.CONTENT_URI, values);
        Uri updateUri = Uri.withAppendedPath(dataUri, People.Phones.CONTENT_DIRECTORY);
        values.clear();
        values.put(People.Phones.TYPE, People.TYPE_MOBILE);
        values.put(People.NUMBER, phone);
        getContentResolver().insert(updateUri, values);
        
        //values.put(ContactsContract.Intents.Insert.EMAIL, eMail);
        //values.put(ContactsContract.CommonDataKinds.Email.DATA, eMail);
        
        ContentValues emailValues = new ContentValues();
        Uri emailUri = Uri
        		.withAppendedPath(
        				dataUri,
        				Contacts.People.ContactMethods
                                               .CONTENT_DIRECTORY);
        emailValues.put(Contacts.ContactMethods.KIND,
        		Contacts.KIND_EMAIL);
        emailValues.put(Contacts.ContactMethods.TYPE,
        		Contacts.ContactMethods.TYPE_HOME);
        emailValues.put(Contacts.ContactMethods.DATA,
        		eMail);
        getContentResolver()
        		.insert(emailUri, emailValues);
        
      }


}

