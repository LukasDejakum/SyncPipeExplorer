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

import android.R.integer;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.os.StatFs;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

public class CreatePropertiesXML extends Service{
	
	private static final String TAG = "CreatePropertiesXML";
	
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	public void onCreate() {
		Toast.makeText(this, "My Properties XML Created", Toast.LENGTH_LONG).show();
		Log.d(TAG, "onCreate");
		

	}
	
	public void onDestroy() {
		Toast.makeText(this, "My Service Stopped Properties", Toast.LENGTH_SHORT).show();
		Log.d(TAG, "onDestroy");
		
	}

	public void onStart(Intent intent, int startid) {
		Toast.makeText(this, "My Service Started Properties", Toast.LENGTH_SHORT).show();
		Log.d(TAG, "onStart");
		this.initialize();
	}
	
	 public void initialize(){
	        Thread th = new Thread(new Runnable() {

	            public void run() {
	            	String propertiesFileNameString = "properties.xml";
	        		
	        		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	        	    NetworkInfo Info = cm.getActiveNetworkInfo();
	        		
	        		 File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/SyncPipe/");
	        		 File newfile = new File(dir, File.separator + propertiesFileNameString);
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
	        				
	        	            Element rootElement = document.createElement("properties");
	        	            document.appendChild(rootElement);
	        	            
	        	            Element valueElement; 
	        	            
	        	            
	        	            
	        	            
	        	            Element androidVersionElement = document.createElement("androidinfo");
	        	            rootElement.appendChild(androidVersionElement);
	        	            
	        	            Integer androidVersion = Build.VERSION.SDK_INT;
	        	            
	        	            switch(androidVersion){
	        	            	case 14:
	        	            		valueElement = document.createElement("androidversion");
	        	    	            androidVersionElement.appendChild(valueElement);
	        	    			    valueElement.appendChild(document.createTextNode("4.0 ICE CREAME SANDWICH"));
	        	            		break;
	        	            	case 15:
	        	            		valueElement = document.createElement("androidversion");
	        	    	            androidVersionElement.appendChild(valueElement);
	        	    			    valueElement.appendChild(document.createTextNode("4.03 ICE CREAME SANDWICH MR 1"));
	        	            		break;
	        	            	case 16:
	        	            		valueElement = document.createElement("androidversion");
	        	    	            androidVersionElement.appendChild(valueElement);
	        	    			    valueElement.appendChild(document.createTextNode("4.1 JEALLY BEAN"));
	        	            		break;
	        	            	case 17:
	        	            		valueElement = document.createElement("androidversion");
	        	    	            androidVersionElement.appendChild(valueElement);
	        	    			    valueElement.appendChild(document.createTextNode("4.2 JEALLY BEAN MR 1"));
	        	            		break;
	        	            	case 18:
	        	            		valueElement = document.createElement("androidversion");
	        	    	            androidVersionElement.appendChild(valueElement);
	        	    			    valueElement.appendChild(document.createTextNode("4.3 JEALLY BEAN MR 2"));
	        	            		break;
	        	            	case 19:
	        	            		valueElement = document.createElement("androidversion");
	        	    	            androidVersionElement.appendChild(valueElement);
	        	    			    valueElement.appendChild(document.createTextNode("4.4 KITKAT"));
	        	            		break;
	        	            	default:
	        	            		valueElement = document.createElement("androidversion");
	        	    	            androidVersionElement.appendChild(valueElement);
	        	    			    valueElement.appendChild(document.createTextNode("older or newer version"));
	        	            		break;
	        	            
	        	            }
	        	            

	        	            
	        	            
	        	            
	        	            Element internetSignalElement = document.createElement("wifisignal");
	        	            rootElement.appendChild(internetSignalElement);
	        	            
	        	            Element mobileSignalElement = document.createElement("mobilesignal");
	        	            rootElement.appendChild(mobileSignalElement);
	        	            
	        	            
	        	            if (Info == null || !Info.isConnectedOrConnecting()) {
	        	            	valueElement = document.createElement("wificonnection");
	        	            	internetSignalElement.appendChild(valueElement);
	        	    		    valueElement.appendChild(document.createTextNode("no"));

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
	        	    			    valueElement.appendChild(document.createTextNode("yes"));
	        	    			    
	        	    			    valueElement = document.createElement("mobileconnection");
	        	    			    mobileSignalElement.appendChild(valueElement);
	        	    			    valueElement.appendChild(document.createTextNode("no3G Connection because wifi is connected"));

	        	    	            
	        	    	            WifiManager wifiManager = (WifiManager) getApplication().getSystemService(Context.WIFI_SERVICE);
	        	    	            List<ScanResult> scanResult = wifiManager.getScanResults();
	        	    	            for (int i = 0; i < scanResult.size(); i++) {
	        	    	            	
	        	    	                Log.d("scanResult", "Speed of wifi"+scanResult.get(i).level);//The db level of signal 
	        	    	            
	        	    	            }
	        	    	        } 
	        	    	        else if (netType == ConnectivityManager.TYPE_MOBILE) {
//	        	    	        	stringToWrite=stringToWrite+"GPRS/3G connection avaliable\nThe phone supports following NETWORK TYPES:\n\n";
	        	    	            
	        	    	            valueElement = document.createElement("wificonnection");
	        	    	            internetSignalElement.appendChild(valueElement);
	        	    			    valueElement.appendChild(document.createTextNode("no"));
	        	    			    
	        	    			    valueElement = document.createElement("mobileconnection");
	        	    			    mobileSignalElement.appendChild(valueElement);
	        	    			    valueElement.appendChild(document.createTextNode("GPRS/3G connection"));

	        	    			    
	        	    	            // Need to get differentiate between 3G/GPRS
	        	    	            
	        	    	            switch (netSubtype) {
	        	    	            case TelephonyManager.NETWORK_TYPE_1xRTT:
//	        	    	                stringToWrite=stringToWrite+"NETWORK TYPE 1xRTT"; // ~ 50-100 kbps
	        	    	                
	        	    	                valueElement = document.createElement("mobilesubtype");
	        	    				    mobileSignalElement.appendChild(valueElement);
	        	    				    valueElement.appendChild(document.createTextNode("NETWORK TYPE 1xRTT ~ 50-100 kbps"));
	        	    	                
	        	    	            case TelephonyManager.NETWORK_TYPE_CDMA:
//	        	    	                stringToWrite=stringToWrite+"NETWORK TYPE CDMA (3G) Speed: 2 Mbps"; // ~ 14-64 kbps
	        	    	                valueElement = document.createElement("mobilesubtype");
	        	    				    mobileSignalElement.appendChild(valueElement);
	        	    				    valueElement.appendChild(document.createTextNode("NETWORK TYPE CDMA (3G) Speed: 2 Mbps"));
	        	    	            case TelephonyManager.NETWORK_TYPE_EDGE:

//	        	    	                stringToWrite=stringToWrite+"NETWORK TYPE EDGE (2.75G) Speed: 100-120 Kbps"; // ~
	        	    	                                                                       // 50-100
	        	    	                                                                        // kbps
	        	    	                valueElement = document.createElement("mobilesubtype");
	        	    				    mobileSignalElement.appendChild(valueElement);
	        	    				    valueElement.appendChild(document.createTextNode("NETWORK TYPE EDGE (2.75G) Speed: 100-120 Kbps"));
	        	    	            case TelephonyManager.NETWORK_TYPE_EVDO_0:
//	        	    	                stringToWrite=stringToWrite+"NETWORK TYPE EVDO_0"; // ~ 400-1000 kbps
	        	    	                valueElement = document.createElement("mobilesubtype");
	        	    				    mobileSignalElement.appendChild(valueElement);
	        	    				    valueElement.appendChild(document.createTextNode("NETWORK TYPE EVDO_0"));
	        	    	            case TelephonyManager.NETWORK_TYPE_EVDO_A:
//	        	    	                stringToWrite=stringToWrite+"NETWORK TYPE EVDO_A"; // ~ 600-1400 kbps
	        	    	                valueElement = document.createElement("mobilesubtype");
	        	    				    mobileSignalElement.appendChild(valueElement);
	        	    				    valueElement.appendChild(document.createTextNode("NETWORK TYPE EVDO_A"));
	        	    	            case TelephonyManager.NETWORK_TYPE_GPRS:
//	        	    	                stringToWrite=stringToWrite+"NETWORK TYPE GPRS (2.5G) Speed: 40-50 Kbps"; // ~ 100
	        	    	                                                                        // kbps
	        	    	                valueElement = document.createElement("mobilesubtype");
	        	    				    mobileSignalElement.appendChild(valueElement);
	        	    				    valueElement.appendChild(document.createTextNode("NETWORK TYPE GPRS (2.5G) Speed: 40-50 Kbps"));
	        	    	            case TelephonyManager.NETWORK_TYPE_HSDPA:
//	        	    	                stringToWrite=stringToWrite+"NETWORK TYPE HSDPA (4G) Speed: 2-14 Mbps"; // ~ 2-14
	        	    	                                                                    // Mbps
	        	    	                valueElement = document.createElement("mobilesubtype");
	        	    				    mobileSignalElement.appendChild(valueElement);
	        	    				    valueElement.appendChild(document.createTextNode("NETWORK TYPE HSDPA (4G) Speed: 2-14 Mbps"));
	        	    	            case TelephonyManager.NETWORK_TYPE_HSPA:
//	        	    	                stringToWrite=stringToWrite+"NETWORK TYPE HSPA (4G) Speed: 0.7-1.7 Mbps"; // ~
	        	    	                                                                        // 700-1700
	        	    	                                                                        // kbps
	        	    	                valueElement = document.createElement("mobilesubtype");
	        	    				    mobileSignalElement.appendChild(valueElement);
	        	    				    valueElement.appendChild(document.createTextNode("NETWORK TYPE HSPA (4G) Speed: 0.7-1.7 Mbps"));
	        	    	            case TelephonyManager.NETWORK_TYPE_HSUPA:
//	        	    	                stringToWrite=stringToWrite+"NETWORK TYPE HSUPA (3G) Speed: 1-23 Mbps"; // ~ 1-23
	        	    	                                                                    // Mbps
	        	    	                valueElement = document.createElement("mobilesubtype");
	        	    				    mobileSignalElement.appendChild(valueElement);
	        	    				    valueElement.appendChild(document.createTextNode("NETWORK TYPE HSUPA (3G) Speed: 1-23 Mbps"));
	        	    	            case TelephonyManager.NETWORK_TYPE_UMTS:
//	        	    	                stringToWrite=stringToWrite+"NETWORK TYPE UMTS (3G) Speed: 0.4-7 Mbps"; // ~ 400-7000
	        	    	                valueElement = document.createElement("mobilesubtype");
	        	    				    mobileSignalElement.appendChild(valueElement);
	        	    				    valueElement.appendChild(document.createTextNode("NETWORK TYPE UMTS (3G) Speed: 0.4-7 Mbps"));
	        	    	                // NOT AVAILABLE YET IN API LEVEL 7
	        	    	            case TelephonyManager.NETWORK_TYPE_EHRPD:
//	        	    	                stringToWrite=stringToWrite+"NETWORK TYPE EHRPD"; // ~ 1-2 Mbps
	        	    	                valueElement = document.createElement("mobilesubtype");
	        	    				    mobileSignalElement.appendChild(valueElement);
	        	    				    valueElement.appendChild(document.createTextNode("NETWORK TYPE EHRPD"));
	        	    	            case TelephonyManager.NETWORK_TYPE_EVDO_B:
//	        	    	                stringToWrite=stringToWrite+"NETWORK_TYPE_EVDO_B"; // ~ 5 Mbps
	        	    	                valueElement = document.createElement("mobilesubtype");
	        	    				    mobileSignalElement.appendChild(valueElement);
	        	    				    valueElement.appendChild(document.createTextNode("NETWORK_TYPE_EVDO_B"));
	        	    	            case TelephonyManager.NETWORK_TYPE_HSPAP:
//	        	    	                stringToWrite=stringToWrite+"NETWORK TYPE HSPA+ (4G) Speed: 10-20 Mbps"; // ~ 10-20
	        	    	                                                                    // Mbps
	        	    	                valueElement = document.createElement("mobilesubtype");
	        	    				    mobileSignalElement.appendChild(valueElement);
	        	    				    valueElement.appendChild(document.createTextNode("NETWORK TYPE HSPA+ (4G) Speed: 10-20 Mbps"));
	        	    	            case TelephonyManager.NETWORK_TYPE_IDEN:
//	        	    	                stringToWrite=stringToWrite+"NETWORK TYPE IDEN"; // ~25 kbps
	        	    	                valueElement = document.createElement("mobilesubtype");
	        	    				    mobileSignalElement.appendChild(valueElement);
	        	    				    valueElement.appendChild(document.createTextNode("NETWORK TYPE IDEN"));
	        	    	            case TelephonyManager.NETWORK_TYPE_LTE:
	        	    	                
	        	    	                valueElement = document.createElement("mobilesubtype");
	        	    				    mobileSignalElement.appendChild(valueElement);
	        	    				    valueElement.appendChild(document.createTextNode("NETWORK TYPE LTE (4G) Speed: 10+ Mbps"));
	        	    	                
	        	    	            }
	        	    	        } else {
//	        	    	            stringToWrite=stringToWrite+"ERROR";
	        	    	            valueElement = document.createElement("mobilesubtype");
        	    				    mobileSignalElement.appendChild(valueElement);
        	    				    valueElement.appendChild(document.createTextNode("ERROR"));
	        	    	        }
	        	    	    }
	        	            
	        	            
	        	            
	        	            
	        	    	    
	        	    	    
	        	    	    
	        	    	    
	        	    	 //nur JeallyBean
	        	    	    
	        	    	 /*
	        	    	  if(androidVersion >= 16){
	        	    		  //this code will be executed on devices running on DONUT (NOT ICS) or later
}
	        	    	   stringToWrite=stringToWrite+"Signal Strength \n";
	        	    	   
	        	    	   
	        	    	   
	        	    	    TelephonyManager telephonyManager = (TelephonyManager)this.getSystemService(Context.TELEPHONY_SERVICE);
	        	    	 // for example value of first element
	        	    	 CellInfoGsm cellinfogsm = (CellInfoGsm)telephonyManager.getAllCellInfo().get(0);
	        	    	 CellSignalStrengthGsm cellSignalStrengthGsm = cellinfogsm.getCellSignalStrength();
	        	    	 cellSignalStrengthGsm.getDbm();	
	        			}*/
	        	    	    
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

	        	    	    
	        	    	    
	        	    	    
	        	    	    
	        	    	    
	        	    	    
	        	    	    
	        	    	    //Internal Storage
	        	    	    
	        	    	    Element internalStorageElement = document.createElement("internalstorage");
	        	            rootElement.appendChild(internalStorageElement);
	        	    	    
	        	    	    StatFs internalStatFs = new StatFs( Environment.getRootDirectory().getPath() );
	        	    	    double internalTotal = ( internalStatFs.getBlockCount() * internalStatFs.getBlockSize() );
	        	    	    double internalFree = ( internalStatFs.getAvailableBlocks() * internalStatFs.getBlockSize() );
	        	    	       
	        	    	      
	        	    	    Double totalInternalStorage = internalTotal;
	        	    	    Double freeInternalStorage = internalFree ;
	        	    	    Double usedInternalStorage = totalInternalStorage - freeInternalStorage;
	        	    	    
	        	    	    valueElement = document.createElement("totalinternalstorage");
    	    	            internalStorageElement.appendChild(valueElement);
    	    			    valueElement.appendChild(document.createTextNode(totalInternalStorage.toString()));
    	    			    
    	    			    valueElement = document.createElement("freeinternalstorage");
    	    	            internalStorageElement.appendChild(valueElement);
    	    			    valueElement.appendChild(document.createTextNode(freeInternalStorage.toString()));
    	    			    
    	    			    valueElement = document.createElement("usedinternalstorage");
    	    	            internalStorageElement.appendChild(valueElement);
    	    			    valueElement.appendChild(document.createTextNode(usedInternalStorage.toString()));
	        	    	    
//	        	    	    System.out.println(freeInternalStorage);
//	        	    	    System.out.println(totalInternalStorage);
//	        	    	    System.out.println(usedInternalStorage);
	        	    	    
	        	    	    
	        	    	    
	        	    	    //SD CARD
	        	    	    
	        	    	    Element sdCardStorageElement = document.createElement("sdcardstorage");
	        	            rootElement.appendChild(sdCardStorageElement);
	        	    	    
	        	    	    StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getPath());
	        	    	    double sdAvailSize = (double)stat.getAvailableBlocks()* (double)stat.getBlockSize();
	        	    	    double sdTotalSize = (double)stat.getBlockCount()*(double)stat.getBlockSize();
	        	    	    
	        	    	    Double totalSDSpaceInMB= sdTotalSize;
	        	    	    Double freeSDSpaceInMB = sdAvailSize;
	        	    	    Double usedSDSpaceInMB= totalSDSpaceInMB-freeSDSpaceInMB;
	        	    	    
	        	    	    valueElement = document.createElement("totalsdstorage");
    	    	            sdCardStorageElement.appendChild(valueElement);
    	    			    valueElement.appendChild(document.createTextNode(totalSDSpaceInMB.toString()));
    	    			    
    	    			    valueElement = document.createElement("freeinternalstorage");
    	    	            sdCardStorageElement.appendChild(valueElement);
    	    			    valueElement.appendChild(document.createTextNode(freeSDSpaceInMB.toString()));
    	    			    
    	    			    valueElement = document.createElement("usedinternalstorage");
    	    	            sdCardStorageElement.appendChild(valueElement);
    	    			    valueElement.appendChild(document.createTextNode(usedSDSpaceInMB.toString()));
	        	    	    
	        	    	    //Total Storage
	        	    	    
	        	    	    Element totalStorageElement = document.createElement("totalstorage");
	        	            rootElement.appendChild(totalStorageElement);
	        	    	    
	        	    	    Double totalStorage = totalSDSpaceInMB+totalInternalStorage;
	        	    	    Double totalFreeStorage = freeInternalStorage+freeSDSpaceInMB;
	        	    	    Double totalUsedStorage = usedInternalStorage+usedSDSpaceInMB;
	        	    	    
	        	            valueElement = document.createElement("totalstorage");
    	    	            totalStorageElement.appendChild(valueElement);
    	    			    valueElement.appendChild(document.createTextNode(totalStorage.toString()));
    	    			    
    	    			    valueElement = document.createElement("totalfreestorage");
    	    	            totalStorageElement.appendChild(valueElement);
    	    			    valueElement.appendChild(document.createTextNode(totalFreeStorage.toString()));
    	    			    
    	    			    valueElement = document.createElement("totalusedstorage");
    	    	            totalStorageElement.appendChild(valueElement);
    	    			    valueElement.appendChild(document.createTextNode(totalUsedStorage.toString()));
	        	    	    
	        	    	    
	        	    	    //RAM
	        	            
	        	            Element ramElement = document.createElement("ram");
	        	            rootElement.appendChild(ramElement);
	        	    	    
	        	    	    final Runtime runtime = Runtime.getRuntime();
	        	    	    final Long usedMemInMB=(runtime.maxMemory() - runtime.freeMemory()) / 104857;
	        	    	    final Long freeMemInMB=runtime.maxMemory() / 104857;
	        	    	    final Long totalMemInMB=usedMemInMB+freeMemInMB;

	        	    	    
	        	    	    valueElement = document.createElement("totalram");
    	    	            ramElement.appendChild(valueElement);
    	    			    valueElement.appendChild(document.createTextNode(totalMemInMB.toString()+" MB"));
    	    			    
    	    			    valueElement = document.createElement("freeram");
    	    	            ramElement.appendChild(valueElement);
    	    			    valueElement.appendChild(document.createTextNode(freeMemInMB.toString()+" MB"));
	        	    	    
    	    			    valueElement = document.createElement("usedram");
    	    	            ramElement.appendChild(valueElement);
    	    			    valueElement.appendChild(document.createTextNode(usedMemInMB.toString()+" MB"));
	        	    	    
	        	    	    
	        	    	    //BATTERY VALUE
	        	    	    
	        	    	    Element batteryElement = document.createElement("battery");
	        	            rootElement.appendChild(batteryElement);
	        	    	    
	        	    	    IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
	        	    	    Intent batteryStatus = registerReceiver(null, ifilter);
	        	    	    
	        	    	    Integer level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
	        	    	    Integer temp = batteryStatus.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, -1);
	        			    Integer voltage = batteryStatus.getIntExtra(BatteryManager.EXTRA_VOLTAGE, -1);
	        			    Integer status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
	        			    String  technology= batteryStatus.getExtras().getString(BatteryManager.EXTRA_TECHNOLOGY);
	        	    	    
	        	    	    valueElement = document.createElement("batteryvalue");
    	    	            batteryElement.appendChild(valueElement);
    	    			    valueElement.appendChild(document.createTextNode(level.toString()));

    	    			    valueElement = document.createElement("batterytemperature");
    	    	            batteryElement.appendChild(valueElement);
    	    			    valueElement.appendChild(document.createTextNode(temp.toString()));

    	    			    valueElement = document.createElement("batteryvoltage");
    	    	            batteryElement.appendChild(valueElement);
    	    			    valueElement.appendChild(document.createTextNode(voltage.toString()));
    	    			    
    	    			    boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
    	    	                     status == BatteryManager.BATTERY_STATUS_FULL;
    	    			    
    	    			    String statusTextString;
    	    			    
    	    			    if(status == BatteryManager.BATTERY_STATUS_CHARGING ||
    	    	                     status == BatteryManager.BATTERY_STATUS_FULL){
    	    			    		statusTextString = "yes";
    	    			    }
    	    			    else {
	    			    		statusTextString = "no";
							}
    	    			    
    	    			    valueElement = document.createElement("batterystatus");
    	    	            batteryElement.appendChild(valueElement);
    	    			    valueElement.appendChild(document.createTextNode(statusTextString));
	        	    	    
    	    			    valueElement = document.createElement("batterytechnology");
    	    	            batteryElement.appendChild(valueElement);
    	    			    valueElement.appendChild(document.createTextNode(technology));
    	    			    
	        	    	    
	        	    	    
	        	    	    
	        	    	    
	        	    	    
	        	    	    
	        	    	    try {
	        	                
	        	    			TransformerFactory factory = TransformerFactory.newInstance();
	        	    			Transformer transformer = factory.newTransformer();
	        	    			Properties outFormat = new Properties();
	        	    	        outFormat.setProperty(OutputKeys.INDENT, "yes");
	        	    	        outFormat.setProperty(OutputKeys.METHOD, "xml");
	        	    	        outFormat.setProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
	        	    	        outFormat.setProperty(OutputKeys.VERSION, "1.0");
	        	    	        outFormat.setProperty(OutputKeys.ENCODING, "UTF-8");
	        	    	        outFormat.setProperty(OutputKeys.DOCTYPE_SYSTEM, "properties.dtd");
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

/*
	 private BroadcastReceiver mBatInfoReceiver = new BroadcastReceiver(){
		    public void onReceive(Context arg0, Intent intent) {
		        //this will give you battery current status

		    try{
		      int batteryLevel = intent.getIntExtra("level", 0);
		      int temp = intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, -1);
		      int voltage = intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE, -1);
		      int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);

		      String BStatus = "No Data";
		      if (status == BatteryManager.BATTERY_STATUS_CHARGING){BStatus = "Charging";}
		      if (status == BatteryManager.BATTERY_STATUS_DISCHARGING){BStatus = "Discharging";}
		      if (status == BatteryManager.BATTERY_STATUS_FULL){BStatus = "Full";}
		      if (status == BatteryManager.BATTERY_STATUS_NOT_CHARGING){BStatus = "Not Charging";}
		      if (status == BatteryManager.BATTERY_STATUS_UNKNOWN){BStatus = "Unknown";}


		      int BHealth = intent.getIntExtra(BatteryManager.EXTRA_HEALTH, -1);
		      String BatteryHealth = "No Data";
		      if (BHealth == BatteryManager.BATTERY_HEALTH_COLD){BatteryHealth = "Cold";}
		      if (BHealth == BatteryManager.BATTERY_HEALTH_DEAD){BatteryHealth = "Dead";}
		      if (BHealth == BatteryManager.BATTERY_HEALTH_GOOD){BatteryHealth = "Good";}
		      if (BHealth == BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE){BatteryHealth = "Over-Voltage";}
		      if (BHealth == BatteryManager.BATTERY_HEALTH_OVERHEAT){BatteryHealth = "Overheat";}
		      if (BHealth == BatteryManager.BATTERY_HEALTH_UNKNOWN){BatteryHealth = "Unknown";}
		      if (BHealth == BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE){BatteryHealth = "Unspecified Failure";}

		      //Do whatever with the data here

		    } catch (Exception e){
		        Log.v(TAG, "Battery Info Error");
		    }
		    }
		  };
		  */
}


