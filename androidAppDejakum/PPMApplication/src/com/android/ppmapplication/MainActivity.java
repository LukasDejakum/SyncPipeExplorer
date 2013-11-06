package com.android.ppmapplication;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.Node;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.media.MediaRecorder.OutputFormat;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.R.integer;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.res.XmlResourceParser;
import android.database.Cursor;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.telephony.CellInfoGsm;
import android.telephony.CellSignalStrengthGsm;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.text.StaticLayout;
import android.util.Log;
import android.view.Menu;


public class MainActivity extends Activity {
	
	private static final String TAG = "MainActivity";
	 
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		String stringToWrite = "test\n1,2\n";  
		SensorManager mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo Info = cm.getActiveNetworkInfo();
	    

	    
	    File dir = new File("/storage/sdcard0");
		File newfile = new File(dir,File.separator + "test.xml");
		
		//STRING
		if(newfile.exists()){
			newfile.delete();
		}
		try {
			newfile.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//XML
		newfile = new File(dir,File.separator + "test2.xml");
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
			
			Element rootElement = document.createElement("contactlist");
            //rootElement.setAttribute("publisher", "Oracle Publishing");
            document.appendChild(rootElement);
            Element contactElement;
            Element valueElement;     
            
         
			
		
	 // Ausschreiben der Datei
	    /*try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.newDocument();
	
		    try {
				doc = builder.parse(new File("/storage/sdcard0/test2.xml"));
				
				
			 
		        OutputFormat format = new OutputFormat(doc); 
		        format.setEncoding(”UTF−8”);
		        format.setIndenting(true);
		        format.setIndent(2);
		        format.setLineWidth(75);
		        format.setPreserveSpace(false );
		        FileOutputStream fos = new FileOutputStream(”/storage/sdcard0/test2.xml”); 
		        XMLSerializer output = new XMLSerializer(fos,format);
		        output.serialize(doc);
		        fos.close();
		        
			} catch (SAXException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (ParserConfigurationException e1) {
			e1.printStackTrace();
		}
	    */
	    
	    
	    
	   // BEISPIEL: Erzeugen der Elemente und Erstellung des Teilbaums 
	   /* Element event = doc . createElement ( ”event ”);
	    Element place = doc . createElement ( ”place ”);
	    place . setAttribute ( ”name”, ”Arena Leipzig ”);
	    Element address = doc . createElement ( ”address ”); address.appendChild(doc.createTextNode(”Am Sportforum , 04105 Leipzig”)); place . appendChild ( address );
	    event . appendChild ( place );
	    Element date = doc . createElement ( ”date ”); date. setAttribute(”day”,”13”);
	    d a t e . s e t A t t r i b u t e ( ”month ” , ”2 ” ) ;
	    date . setAttribute ( ”year ”, ”2005 ”);
	    date. setAttribute(”hour”,”20”);
	    date . setAttribute ( ”minute ”, ”0”);
	    event . appendChild(date );*/
	    
	    
	    
	    List<Sensor> sensorList = mSensorManager.getSensorList(Sensor.TYPE_ALL);

	    Log.w(TAG, "sl size = " + sensorList.size());
	    for(int i=0;i<sensorList.size();i++) {
	        Log.w(TAG, "sn = " + sensorList.get(i).getName());
	    }
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    if (Info == null || !Info.isConnectedOrConnecting()) {
        	stringToWrite=stringToWrite+"No connection at all";
	        Log.i(TAG, "No connection");
	    }
	    else{
	    	 int netType = Info.getType();
	         int netSubtype = Info.getSubtype();

	        if (netType == ConnectivityManager.TYPE_WIFI) {
	            Log.i(TAG, "Wifi connection");
	            stringToWrite=stringToWrite+"CONNECTED VIA WIFI\n";
	            WifiManager wifiManager = (WifiManager) getApplication().getSystemService(Context.WIFI_SERVICE);
	            List<ScanResult> scanResult = wifiManager.getScanResults();
	            for (int i = 0; i < scanResult.size(); i++) {
	            	stringToWrite=stringToWrite+"scanResult"+"Speed of wifi"+scanResult.get(i).level+"\n";
	                Log.d("scanResult", "Speed of wifi"+scanResult.get(i).level);//The db level of signal 
	            }
	        } 
	        else if (netType == ConnectivityManager.TYPE_MOBILE) {
	        	stringToWrite=stringToWrite+"GPRS/3G connection avaliable\nThe phone supports following NETWORK TYPES:\n\n";
	            Log.i(TAG, "GPRS/3G connection");
	            // Need to get differentiate between 3G/GPRS
	            
	            switch (netSubtype) {
	            case TelephonyManager.NETWORK_TYPE_1xRTT:
	                stringToWrite=stringToWrite+"NETWORK TYPE 1xRTT"; // ~ 50-100 kbps
	            case TelephonyManager.NETWORK_TYPE_CDMA:
	                stringToWrite=stringToWrite+"NETWORK TYPE CDMA (3G) Speed: 2 Mbps"; // ~ 14-64 kbps
	            case TelephonyManager.NETWORK_TYPE_EDGE:

	                stringToWrite=stringToWrite+"NETWORK TYPE EDGE (2.75G) Speed: 100-120 Kbps"; // ~
	                                                                        // 50-100
	                                                                        // kbps
	            case TelephonyManager.NETWORK_TYPE_EVDO_0:
	                stringToWrite=stringToWrite+"NETWORK TYPE EVDO_0"; // ~ 400-1000 kbps
	            case TelephonyManager.NETWORK_TYPE_EVDO_A:
	                stringToWrite=stringToWrite+"NETWORK TYPE EVDO_A"; // ~ 600-1400 kbps
	            case TelephonyManager.NETWORK_TYPE_GPRS:
	                stringToWrite=stringToWrite+"NETWORK TYPE GPRS (2.5G) Speed: 40-50 Kbps"; // ~ 100
	                                                                        // kbps
	            case TelephonyManager.NETWORK_TYPE_HSDPA:
	                stringToWrite=stringToWrite+"NETWORK TYPE HSDPA (4G) Speed: 2-14 Mbps"; // ~ 2-14
	                                                                    // Mbps
	            case TelephonyManager.NETWORK_TYPE_HSPA:
	                stringToWrite=stringToWrite+"NETWORK TYPE HSPA (4G) Speed: 0.7-1.7 Mbps"; // ~
	                                                                        // 700-1700
	                                                                        // kbps
	            case TelephonyManager.NETWORK_TYPE_HSUPA:
	                stringToWrite=stringToWrite+"NETWORK TYPE HSUPA (3G) Speed: 1-23 Mbps"; // ~ 1-23
	                                                                    // Mbps
	            case TelephonyManager.NETWORK_TYPE_UMTS:
	                stringToWrite=stringToWrite+"NETWORK TYPE UMTS (3G) Speed: 0.4-7 Mbps"; // ~ 400-7000
	                // NOT AVAILABLE YET IN API LEVEL 7
	            case TelephonyManager.NETWORK_TYPE_EHRPD:
	                stringToWrite=stringToWrite+"NETWORK TYPE EHRPD"; // ~ 1-2 Mbps
	            case TelephonyManager.NETWORK_TYPE_EVDO_B:
	                stringToWrite=stringToWrite+"NETWORK_TYPE_EVDO_B"; // ~ 5 Mbps
	            case TelephonyManager.NETWORK_TYPE_HSPAP:
	                stringToWrite=stringToWrite+"NETWORK TYPE HSPA+ (4G) Speed: 10-20 Mbps"; // ~ 10-20
	                                                                    // Mbps
	            case TelephonyManager.NETWORK_TYPE_IDEN:
	                stringToWrite=stringToWrite+"NETWORK TYPE IDEN"; // ~25 kbps
	            case TelephonyManager.NETWORK_TYPE_LTE:
	                stringToWrite=stringToWrite+"NETWORK TYPE LTE (4G) Speed: 10+ Mbps"; // ~ 10+ Mbps
	            }
	        } else {
	            stringToWrite=stringToWrite+"error";
	        }
	    }
	    
	    stringToWrite=stringToWrite+"Signal Strength \n";
	    
	    
	 //nur JeallyBean
	 /*   
	    TelephonyManager telephonyManager = (TelephonyManager)this.getSystemService(Context.TELEPHONY_SERVICE);
	 // for example value of first element
	 CellInfoGsm cellinfogsm = (CellInfoGsm)telephonyManager.getAllCellInfo().get(0);
	 CellSignalStrengthGsm cellSignalStrengthGsm = cellinfogsm.getCellSignalStrength();
	 cellSignalStrengthGsm.getDbm();	
	  */
	 /*
	    int strengthAmplitude = MyPhoneStateListener.getStrength();
	    
	    //do something with it (in this case we update a text view)
	    stringToWrite=stringToWrite+"signal strength"+String.valueOf(strengthAmplitude);		
		
	    ContentResolver cr = getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);
        if (cur.getCount() > 0) {
		    while (cur.moveToNext()) {
		        String id = cur.getString(
	                        cur.getColumnIndex(ContactsContract.Contacts._ID));
			String name = cur.getString(
	                        cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
		 		if (Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
		 		    //Query phone here.  Covered next
		 	    }
	        }
        }
	   	   
	   	   
        
	     class MyPhoneStateListener extends PhoneStateListener {
	        String gsmStrength = "";

	        @Override
	        public void onSignalStrengthsChanged(SignalStrength signalStrength) {
	            super.onSignalStrengthsChanged(signalStrength);
	            gsmStrength = String.valueOf(signalStrength.getGsmSignalStrength()* 2 - 113);           
	        }

	        public String getStrength() {
	            return gsmStrength;
	        }

	        }
	    */
	    
	    
	    
	    
	    //nur Galaxy S4
	    /*Sensor TemperatureSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_TEMPERATURE);
	     if(TemperatureSensor != null){
		        Log.i(TAG, "Sensor.TYPE_TEMPERATURE Available");
	      mSensorManager.registerListener(
	        TemperatureSensorListener, 
	        TemperatureSensor, 
	        SensorManager.SENSOR_DELAY_NORMAL);
	      
	     }else{
		        Log.i(TAG, "Sensor.TYPE_TEMPERATURE NOT Available");
	     }
	     
	     Sensor AmbientTemperatureSensor 
	      = mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
	     if(AmbientTemperatureSensor != null){
		        Log.i(TAG, "Sensor.TYPE_AMBIENT_TEMPERATURE Available");
	      mSensorManager.registerListener(
	        AmbientTemperatureSensorListener, 
	        AmbientTemperatureSensor, 
	        SensorManager.SENSOR_DELAY_NORMAL);
	     }else{
		        Log.i(TAG, "Sensor.TYPE_AMBIENT_TEMPERATURE NOT Available");
	     }*/
	    
	    
	    
	    
	    //CONTACTS
	    ContentResolver cr = getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);
        
        int index=0;
        
        //NAMES
        if (cur.getCount() > 0) {
        	
        	
		    while (cur.moveToNext()) {
			    String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
			    String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
			    String number;
	        	
			    contactElement= document.createElement("contact");
  	        	contactElement.setAttribute("index", id);
  	        	rootElement.appendChild(contactElement);
			    
			    valueElement = document.createElement("surename");
			    valueElement.setAttribute("value", name);
			    document.getElementsByTagName("contact").item(index).appendChild(valueElement);
			 
			    
		        Log.i(TAG, name);
		        

		 		if (Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
		 		    //Query phone here.  Covered next
		 	    }
		 		
		 		//NUMBERS
		 		
		 		if (Integer.parseInt(cur.getString(
		                cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
		             Cursor pCur = cr.query(
				    ContactsContract.CommonDataKinds.Phone.CONTENT_URI, 
				    null, 
				    ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?", 
				    new String[]{id}, null);
			        while (pCur.moveToNext()) {
				    // Do something with phones
			        	
			            number = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
				        
			           
					    valueElement = document.createElement("number");
					    valueElement.setAttribute("value", number);
					    document.getElementsByTagName("contact").item(index).appendChild(valueElement);
			            
			            Log.i(TAG, number);


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
					    valueElement.setAttribute("value", email);
					    document.getElementsByTagName("contact").item(index).appendChild(valueElement);
		 			    
		 		 	    String emailType = emailCur.getString(
		 		                      emailCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.TYPE));
		 		 	    
		 		 	    valueElement = document.createElement("eMailTyp");
					    valueElement.setAttribute("value", emailType);
					    document.getElementsByTagName("contact").item(index).appendChild(valueElement);
		 		 	    
		 		 	    Log.i(TAG, email);
		 		 	} 
		 		 	emailCur.close();
		 		 
		 		 	
		 		 //NOTES
		 		 	
		 		 	String noteWhere = ContactsContract.Data.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?"; 
		 	        String[] noteWhereParams = new String[]{id, 
		 	        		ContactsContract.CommonDataKinds.Note.CONTENT_ITEM_TYPE}; 
		 	                       Cursor noteCur = cr.query(ContactsContract.Data.CONTENT_URI, null, noteWhere, noteWhereParams, null); 
		 	        	if (noteCur.moveToFirst()) { 
		 	        	    String note = noteCur.getString(noteCur.getColumnIndex(ContactsContract.CommonDataKinds.Note.NOTE));
		 	        
		 	        	    /*valueElement = document.createElement("note");
						    valueElement.setAttribute("value", note);
						    document.getElementsByTagName("contact").item(index).appendChild(valueElement);*/
		 	        	    
		 	        	} 
		 	        	noteCur.close();
		 	        	
		 	     //ADDRESSE
		 	        
		 	        String addrWhere = ContactsContract.Data.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?"; 
		 	       	String[] addrWhereParams = new String[]{id, 
		 	       		ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_ITEM_TYPE}; 
		 	       	Cursor addrCur = cr.query(ContactsContract.Data.CONTENT_URI, 
		 	                       null, addrWhere, addrWhereParams, null); 
		 	       	while(addrCur.moveToNext()) {
		 	       		String poBox = addrCur.getString(
		 	                            addrCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.POBOX));
		 	       		
		 	       		/*valueElement = document.createElement("poBox");
		 	       		valueElement.setAttribute("value", poBox);
		 	       		document.getElementsByTagName("contact").item(index).appendChild(valueElement);*/
		 	       		
		 	        		String street = addrCur.getString(
		 	                            addrCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.STREET));
		 	        		String city = addrCur.getString(
		 	                            addrCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.CITY));
		 	        		String state = addrCur.getString(
		 	                            addrCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.REGION));
		 	        		String postalCode = addrCur.getString(
		 	                            addrCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.POSTCODE));
		 	        		String country = addrCur.getString(
		 	                            addrCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.COUNTRY));
		 	        		String type = addrCur.getString(
		 	                            addrCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.TYPE));
			 		 	    Log.i(TAG, state);
			 		 	    
			 		 	    

		 	        	} 
		 	        	addrCur.close();
		 	      //INSTANT MESSAGE
		 	        	
		 	        	String imWhere = ContactsContract.Data.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?"; 
		 	        	String[] imWhereParams = new String[]{id, 
		 	        	ContactsContract.CommonDataKinds.Im.CONTENT_ITEM_TYPE}; 
		 	        	Cursor imCur = cr.query(ContactsContract.Data.CONTENT_URI, 
		 	                   null, imWhere, imWhereParams, null); 
		 	        	if (imCur.moveToFirst()) { 
		 	        	    String imName = imCur.getString(imCur.getColumnIndex(ContactsContract.CommonDataKinds.Im.DATA));
		 	        	    String imType;
		 	        	    imType = imCur.getString(imCur.getColumnIndex(ContactsContract.CommonDataKinds.Im.TYPE));
		 	        	    
		 	        	    /*valueElement = document.createElement("imType");
						    valueElement.setAttribute("value", imType);
						    document.getElementsByTagName("contact").item(index).appendChild(valueElement);*/
		 	        	
		 	        	} 
		 	        	imCur.close();
		 	        	index++;
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
	        
	
   
	    
	    
	     
        	File dir2 = new File("/storage/sdcard0");
			File newfile2 = new File(dir2,File.separator + "testString.xml");
			
			if(newfile2.exists()){

				newfile2.delete();
				try {
					newfile2.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			try {
				FileWriter writer = new FileWriter(newfile2,true);
				
				writer.write(stringToWrite);
				
				writer.flush();
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			 
		    	DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		    	DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
		    	try {
					document = docBuilder.parse(new File("files/test.xml"));
				} catch (SAXException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    	
		    	
		    	
		
		    	//doSomething((Node)document.getDocumentElement());
		    	
		    	
		    	
		    	
		    	
		    	
    	} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		}
        
	} catch (ParserConfigurationException e1) {
		e1.printStackTrace();
	}	
		
			
		/*	
			 try {
	              XmlResourceParser xpp = getResources().getXml();
				
	              String elemtext = null;

	              while (eventType != XmlPullParser.END_DOCUMENT) {

	                 if (eventType == XmlPullParser.START_TAG) {

	                     String elemName = xpp.getName();
	                     if (elemName.equals("catalog")) {
	                 String journalAttr = xpp.getAttributeValue(null,
	                                  "journal");
	                 String publisherAttr = xpp.getAttributeValue(null,
	                                  "publisher");
	                        journal.setText(journalAttr);
	                        publisher.setText(publisherAttr);
	                     }
	                     if (elemName.equals("article")) {
	                        iter = iter + 1;
	                     }
						
	                     if (elemName.equals("edition")) {
	                        elemtext = "edition";
	                     }
	                     if (elemName.equals("title")) {
	                        elemtext = "title";
	                     }
	                     if (elemName.equals("author")) {
	                        elemtext = "author";
	                     }
	                 }

	                 else if (eventType == XmlPullParser.TEXT) {
	                     if (iter == 1) {
	                        if (elemtext.equals("edition")) {
	                             edition1.setText(xpp.getText());
	                        } else if (elemtext.equals("title")) {
	                             title1.setText(xpp.getText());
	                        } else if (elemtext.equals("author")) {
	                             author1.setText(xpp.getText());
	                        }
	                     }

	                     else if (iter == 2) {
	                        if (elemtext.equals("edition")) {
	                             edition2.setText(xpp.getText());
	                        } else if (elemtext.equals("title")) {
	                             title2.setText(xpp.getText());
	                        } else if (elemtext.equals("author")) {
	                             author2.setText(xpp.getText());
	                        }

	                     }
	                 }
	                 eventType = xpp.next();
	              }

	         } catch (XmlPullParserException e) {
	         } catch (IOException e) {
	         }
	         */
			
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
	
	private static void doSomething(Node node) {
		// do something with the current node instead of System.out
		Log.i(TAG,((Element)node).getAttribute("catalog"));
		/*NodeList nodeList = node.getChildNodes();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node currentNode = (Node) nodeList.item(i);
			if (currentNode.getNodeType() == Node.ELEMENT_NODE) {
				//calls this method for all the children which is Element
				doSomething(currentNode);
			}
		}*/

	}
}
