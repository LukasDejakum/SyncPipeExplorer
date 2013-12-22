package com.android.ppmapplication.services;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import android.app.Service;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Environment;
import android.os.IBinder;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Toast;

public class CreateContactsXML extends Service{
	private static final String TAG = "CreateContactsXML";

	public IBinder onBind(Intent intent) {
		return null;
	}
	
	@Override
	public void onCreate() {
//		Toast.makeText(this, "My Service Created", Toast.LENGTH_LONG).show();
		Log.d(TAG, "onCreate Contacts");
	}

	@Override
	public void onDestroy() {
		Toast.makeText(this, "My Service Stopped Contacts", Toast.LENGTH_SHORT).show();
		Log.d(TAG, "onDestroy Contacts");
		
	}
	
	@Override
	public void onStart(Intent intent, int startid) {
		Toast.makeText(this, "My Service Started Contacts", Toast.LENGTH_SHORT).show();
		Log.d(TAG, "onStart");
		
		this.initialize();	
	}

	public void initialize(){
        Thread th = new Thread(new Runnable() {

            @Override
            public void run() {
            	String contactsFileNameString = "contact.xml";

        		
        		File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
        		File newfile = new File(dir, File.separator + contactsFileNameString);
        		if(newfile.exists()){
        			newfile.delete();
        		}
        		
        		try {
        			newfile.createNewFile();
        		} catch (IOException e) {
        			e.printStackTrace();
        		}
        		
        		try {
        			
        			
        			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        	        DocumentBuilder documentBuilder;
        			documentBuilder = documentBuilderFactory.newDocumentBuilder();
        			Document document = documentBuilder.newDocument();
        			
                  //CONTACTS
            	    
            	    Element rooElement = document.createElement("contactlist");  
            	    document.appendChild(rooElement);
            	    Element contactElement;
                    Element valueElement; 

            	    
            	    ContentResolver cr = getContentResolver();
                    Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                            null, null, null, null);
                    
                    //NAMES
                    
                    if (cur.getCount() > 0) {
                    	
                    	
            		    while (cur.moveToNext()) {
            			    String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
            			    String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            		        //String familyName = cur.getString(cur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME));
            			    String number;
            	        	
            			    contactElement= document.createElement("contact");
              	        	contactElement.setAttribute("index", id);
              	        	rooElement.appendChild(contactElement);
            			    
            			    valueElement = document.createElement("surename");
            			    contactElement.appendChild(valueElement);
            			    valueElement.appendChild(document.createTextNode(name));
            			    
            			    /*
            			    valueElement = document.createElement("familyname");
            			    contactElement.appendChild(valueElement);
            			    valueElement.appendChild(document.createTextNode(familyName));
            			    */
            			    
            		 		//NUMBERS
            		 		
            		 		if (Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
            		 				Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?", new String[]{id}, null);
            			        while (pCur.moveToNext()) {
            			        	
            			            number = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            			           
            					    valueElement = document.createElement("number");
            					    contactElement.appendChild(valueElement);
            					    valueElement.appendChild(document.createTextNode(number));

            			        } 
            			        pCur.close();
            			    }
            		 		
            		 		//E-MAIL
            		 		
            		 		Cursor emailCur = cr.query( 
            		 				ContactsContract.CommonDataKinds.Email.CONTENT_URI, 
            		 				null,
            		 				ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?", 
            		 				new String[]{id}, null); 
            		 		while (emailCur.moveToNext()) { 
            		 			    // This would allow you get several email addresses
            		 		        // if the email addresses were stored in an array
            		 			    String email = emailCur.getString(
            		 		                      emailCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
            		 			    
            		 			    valueElement = document.createElement("eMail");
            					    contactElement.appendChild(valueElement);
            					    valueElement.appendChild(document.createTextNode(email));
            		 			    
            		 		 	   /*
            		 		 	    String emailType = emailCur.getString(
            		 		                      emailCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.TYPE));
            		 		 	    
            		 		 	    valueElement = document.createElement("eMailTyp");
            					    valueElement.setAttribute("value", emailType);
            					    document.getElementsByTagName("contact").item(index).appendChild(valueElement);
            					    */
            		 		 	    
            		 		 	} 
            		 		 	emailCur.close();
            		 		 
            		 		 	
            		 		 //NOTES
            		 		 /*	
            		 		 	String noteWhere = ContactsContract.Data.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?"; 
            		 	        String[] noteWhereParams = new String[]{id, 
            		 	        		ContactsContract.CommonDataKinds.Note.CONTENT_ITEM_TYPE}; 
            		 	                       Cursor noteCur = cr.query(ContactsContract.Data.CONTENT_URI, null, noteWhere, noteWhereParams, null); 
            		 	        	if (noteCur.moveToFirst()) { 
            		 	        	    String note = noteCur.getString(noteCur.getColumnIndex(ContactsContract.CommonDataKinds.Note.NOTE));
            		 	        
            		 	        	    valueElement = document.createElement("note");
            						    valueElement.setAttribute("value", note);
            						    document.getElementsByTagName("contact").item(index).appendChild(valueElement);
            		 	        	    
            		 	        	} 
            		 	        	noteCur.close();
            		 	      */  	
            		 	     //ADDRESSE
            		 	        
            		 	        String addrWhere = ContactsContract.Data.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?"; 
            		 	       	String[] addrWhereParams = new String[]{id, 
            		 	       		ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_ITEM_TYPE}; 
            		 	       	Cursor addrCur = cr.query(ContactsContract.Data.CONTENT_URI, 
            		 	                       null, addrWhere, addrWhereParams, null); 
            		 	       	while(addrCur.moveToNext()) {
            		 	       		String poBox = addrCur.getString(
            		 	                            addrCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.POBOX));
            		 	       		
            		 	        		String street = addrCur.getString(
            		 	                            addrCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.STREET));
            		 	        		
            		 	        		valueElement = document.createElement("street");
            		 	        		contactElement.appendChild(valueElement);
            						    valueElement.appendChild(document.createTextNode(street));
            		 	        		
            		 	        		/*String city = addrCur.getString(
            		 	                            addrCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.CITY));
            		 	        		String state = addrCur.getString(
            		 	                            addrCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.REGION));
            		 	        		String postalCode = addrCur.getString(
            		 	                            addrCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.POSTCODE));
            		 	        		String country = addrCur.getString(
            		 	                            addrCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.COUNTRY));
            		 	        		String type = addrCur.getString(
            		 	                            addrCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.TYPE));*/
            			 		 	  
            		 	        	
            		 	        	} 
            		 	        	addrCur.close();
            		 	        	
            		 	      //INSTANT MESSAGE
            		 	        /*	
            		 	        	String imWhere = ContactsContract.Data.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?"; 
            		 	        	String[] imWhereParams = new String[]{id, 
            		 	        	ContactsContract.CommonDataKinds.Im.CONTENT_ITEM_TYPE}; 
            		 	        	Cursor imCur = cr.query(ContactsContract.Data.CONTENT_URI, 
            		 	                   null, imWhere, imWhereParams, null); 
            		 	        	if (imCur.moveToFirst()) { 
            		 	        		
            		 	        	    String imName = imCur.getString(imCur.getColumnIndex(ContactsContract.CommonDataKinds.Im.DATA));
            		 	        	    String imType;
            		 	        	    imType = imCur.getString(imCur.getColumnIndex(ContactsContract.CommonDataKinds.Im.TYPE));
            		 	        	    
            		 	        	    valueElement = document.createElement("imName");
            						    contactElement.appendChild(valueElement);
            						    valueElement.appendChild(document.createTextNode(imName));
            		 	        	
            		 	        	} 
            		 	        	imCur.close();
            		 	        	*/
            	        }
            		    
                    }
                      
                    try {
                        
            			TransformerFactory factory = TransformerFactory.newInstance();
            			Transformer transformer = factory.newTransformer();
            			Properties outFormat = new Properties();
            	        outFormat.setProperty(OutputKeys.INDENT, "yes");
            	        outFormat.setProperty(OutputKeys.METHOD, "xml");
            	        outFormat.setProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
            	        outFormat.setProperty(OutputKeys.VERSION, "1.0");
            	        outFormat.setProperty(OutputKeys.ENCODING, "UTF-8");
            	        outFormat.setProperty(OutputKeys.DOCTYPE_SYSTEM, "contacts.dtd");
            	        transformer.setOutputProperties(outFormat);
            	        DOMSource domSource = new DOMSource(document.getDocumentElement());
            	        OutputStream output = new ByteArrayOutputStream();
            	        StreamResult result = new StreamResult(output);
            	        try {
            				transformer.transform(domSource, result);
            			} catch (TransformerException e) {
            				e.printStackTrace();
            			}
            	        
            	        String xmlString = output.toString();
            	        
            	        try {
            				FileWriter writer = new FileWriter(newfile,true);
            				
            				writer.write(xmlString);
            				writer.flush();
            				writer.close();
            			} catch (IOException e) {
            				e.printStackTrace();
            			}
                    } catch (TransformerConfigurationException e) {
        				e.printStackTrace();
        			}
        		} catch (ParserConfigurationException e1) {
        		e1.printStackTrace();
        		}
        		stopSelf();
            }
        });
        th.start();
    }
}

