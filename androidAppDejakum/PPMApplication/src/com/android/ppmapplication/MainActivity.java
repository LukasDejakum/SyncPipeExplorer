package com.android.ppmapplication;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
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
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;


public class MainActivity extends Activity {
	
	private static final String TAG = "MainActivity";
	private int batteryLevel;
	 
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		this.registerReceiver(this.mBatInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));

		
		String contactsFileNameString = "contacts.xml";
		
		String stringToWrite = "test\n1,2\n";  
		
		SensorManager mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
		
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo Info = cm.getActiveNetworkInfo();
	    
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
			
            Element rootElement = document.createElement("doctype");
            document.appendChild(rootElement);
            
            Element internetSignalElement = document.createElement("wifisignal");
            rootElement.appendChild(internetSignalElement);
            
            Element mobileSignalElement = document.createElement("mobilesignal");
            rootElement.appendChild(mobileSignalElement);
            
            Element valueElement; 
            
            
            
       Log.d(TAG, "onClick: starting service contacts");
       startService(new Intent(this, CreateContactsXML.class));
       
       Log.d(TAG, "onClick: starting service properties");
       startService(new Intent(this, CreatePropertiesXML.class));   
            
            
            
            
            
	    List<Sensor> sensorList = mSensorManager.getSensorList(Sensor.TYPE_ALL);

	    Log.w(TAG, "sl size = " + sensorList.size());
	    for(int i=0;i<sensorList.size();i++) {
	        Log.w(TAG, "sn = " + sensorList.get(i).getName());
	    }

	    
	    
	    
	    
	    
	    
	    
	    
	    if (Info == null || !Info.isConnectedOrConnecting()) {
        	valueElement = document.createElement("wificonnection");
        	internetSignalElement.appendChild(valueElement);
		    valueElement.appendChild(document.createTextNode("noWifiConnection"));

		    valueElement = document.createElement("mobileconnection");
		    mobileSignalElement.appendChild(valueElement);
		    valueElement.appendChild(document.createTextNode("no3GConnection"));

	    }
	    
	    else{
	    	 int netType = Info.getType();
	         int netSubtype = Info.getSubtype();
	         
	        if (netType == ConnectivityManager.TYPE_WIFI) {
	        	
	            Log.i(TAG, "Wifi connection");
	           
	            valueElement = document.createElement("wificonnection");
	            internetSignalElement.appendChild(valueElement);
			    valueElement.appendChild(document.createTextNode("wifiConnection"));
			    
			    valueElement = document.createElement("mobileconnection");
			    mobileSignalElement.appendChild(valueElement);
			    valueElement.appendChild(document.createTextNode("no3GConnection because wifi is connected"));

	            
	            WifiManager wifiManager = (WifiManager) getApplication().getSystemService(Context.WIFI_SERVICE);
	            List<ScanResult> scanResult = wifiManager.getScanResults();
	            for (int i = 0; i < scanResult.size(); i++) {
	            	
	                Log.d("scanResult", "Speed of wifi"+scanResult.get(i).level);//The db level of signal 
	            
	            }
	        } 
	        else if (netType == ConnectivityManager.TYPE_MOBILE) {
	        	stringToWrite=stringToWrite+"GPRS/3G connection avaliable\nThe phone supports following NETWORK TYPES:\n\n";
	            
	            valueElement = document.createElement("wificonnection");
	            internetSignalElement.appendChild(valueElement);
			    valueElement.appendChild(document.createTextNode("noWifiConnection"));
			    
			    valueElement = document.createElement("mobileconnection");
			    mobileSignalElement.appendChild(valueElement);
			    valueElement.appendChild(document.createTextNode("GPRS/3G connection"));

			    
	            // Need to get differentiate between 3G/GPRS
	            
	            switch (netSubtype) {
	            case TelephonyManager.NETWORK_TYPE_1xRTT:
//	                stringToWrite=stringToWrite+"NETWORK TYPE 1xRTT"; // ~ 50-100 kbps
	                
	                valueElement = document.createElement("mobilesubtype");
				    mobileSignalElement.appendChild(valueElement);
				    valueElement.appendChild(document.createTextNode("NETWORK TYPE 1xRTT ~ 50-100 kbps"));
	                
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
	                
	                valueElement = document.createElement("mobilesubtype");
				    mobileSignalElement.appendChild(valueElement);
				    valueElement.appendChild(document.createTextNode("NETWORK TYPE LTE (4G) Speed: 10+ Mbps"));
	                
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
	    
	   /*
	    MemoryInfo mi = new MemoryInfo();
	    
	    ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
	    activityManager.getMemoryInfo(mi);
	    long availableInMB = mi.availMem / 104857;
	    
	    System.out.println(availableInMB);
	    */

	    
	    
	    //Internal Storage
	    
	    StatFs internalStatFs = new StatFs( Environment.getRootDirectory().getPath() );
	    double internalTotal = ( internalStatFs.getBlockCount() * internalStatFs.getBlockSize() ) / 1048576L;
	    double internalFree = ( internalStatFs.getAvailableBlocks() * internalStatFs.getBlockSize() ) /  1048576L ;
	       
	      
	    double total = internalTotal;
	    double free = internalFree ;
	    double used = total - free;
	    
	    System.out.println(free);
	    System.out.println(total);
	    System.out.println(used);
	    
	    
	    
	    //SD CARD
	    
	    StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getPath());
	    double sdAvailSize = (double)stat.getAvailableBlocks()* (double)stat.getBlockSize();
	    double sdTotalSize = (double)stat.getBlockCount()*(double)stat.getBlockSize();
	    
	    double freeSDSpaceInMB = sdAvailSize / 1048576L;
	    double totalSDSpaceInMB= sdTotalSize/1048576L;
	    double usedSDSpaceInMB= totalSDSpaceInMB-freeSDSpaceInMB;
	    
	    System.out.println(freeSDSpaceInMB);
	    System.out.println(totalSDSpaceInMB);
	    System.out.println(usedSDSpaceInMB);
	    
	    //Total Storage
	    
	    
	    
	    
	    //RAM
	    
	    final Runtime runtime = Runtime.getRuntime();
	    final long usedMemInMB=(runtime.maxMemory() - runtime.freeMemory()) / 104857;
	    final long freeMemInMB=runtime.maxMemory() / 104857;

	    System.out.println(usedMemInMB);
	    System.out.println(freeMemInMB);
	    System.out.println(freeMemInMB+usedMemInMB);
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    //CONTACTS
	    
	    Element contactListElement = document.createElement("contactlist");
	    rootElement.appendChild(contactListElement);
	    
	     Element contactElement;
	    
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
  	        	contactListElement.appendChild(contactElement);
			    
			    valueElement = document.createElement("surename");
			    //valueElement.setAttribute("value", name)
			    contactElement.appendChild(valueElement);
			    valueElement.appendChild(document.createTextNode(name));
			    //document.getElementsByTagName("contact").item(index).appendChild(valueElement);
			    
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
			 
	        
	        
	        //EINLESEN
	      
		    DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		    DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
		    
			    try {
			    	
					document = docBuilder.parse(new File(dir+File.separator+contactsFileNameString));
//					readContacts(document.getDocumentElement());
					
				} catch (SAXException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
		    	
    		} catch (TransformerConfigurationException e) {
				e.printStackTrace();
			}
        
		} catch (ParserConfigurationException e1) {
		e1.printStackTrace();
		}
		
		System.out.println(batteryLevel);
		
	}

	public void setBatteryLevel(int batteryL){
		this.batteryLevel=batteryL;
	}
	
	private BroadcastReceiver mBatInfoReceiver = new BroadcastReceiver(){
	      
	      private boolean index = true;
	      private int level=0;
	      
	      public void onReceive(Context arg0, Intent intent) {
	        
	    	if(index){
	    		level = intent.getIntExtra("level", 0);
	    		batteryLevel=level;
	    		System.err.println(level);
	    		setBatteryLevel(level);
	    		index = !index;
	    	}

	        

	       // int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
	        
	      }

	    };
	    
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
					System.out.println(((Element) childNodes.item(j)).getTextContent());
					
					String surenameString=((Element) childNodes.item(j)).getTextContent();
					
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
		personValues.put(Contacts.People.STARRED, 1);

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
}
