package com.android.ppmapplication;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.regex.Pattern;

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

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.StatFs;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.widget.Toast;

public class CreatePropertiesXML extends Service{
	
	private static final String TAG = "CreatePropertiesXML";
	private Handler handler = new Handler();
	
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	public void onCreate() {
		Toast.makeText(this, "My Service Created Properties", Toast.LENGTH_LONG).show();
		Log.d(TAG, "onCreate");
		
		this.initialize();
	}
	
	public void onDestroy() {
		//Toast.makeText(this, "My Service Stopped Properties", Toast.LENGTH_SHORT).show();
		Log.d(TAG, "onDestroy");
		
	}

	public void onStart(Intent intent, int startid) {
		//Toast.makeText(this, "My Service Started Properties", Toast.LENGTH_SHORT).show();
		Log.d(TAG, "onStart");
	}
	
	 public void initialize(){
	        Thread th = new Thread(new Runnable() {

	            public void run() {
	            	

	            	String propertiesFileNameString = "properties.xml";
	            	DecimalFormat f = new DecimalFormat("0.00");
	        		
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
	        				final Document document = documentBuilder.newDocument();
	        				
	        	            final Element rootElement = document.createElement("properties");
	        	            document.appendChild(rootElement);
	        	            
	        	            Element valueElement; 
	        	            
	        	            
	        	            
	        	            
	        	            //ANDROID Basic INFO
	        	            
	        	            Element androidVersionElement = document.createElement("androidinfo");
	        	            rootElement.appendChild(androidVersionElement);
	        	           
	        	            //ANDROID Company Name
	        	            
	        	            String manufacturerString = Build.MANUFACTURER;
	        	            valueElement = document.createElement("androiddevicename");
    	    	            androidVersionElement.appendChild(valueElement);
    	    			    valueElement.appendChild(document.createTextNode(manufacturerString));
	        	            
	        	            //ANDROID Device Name

	        	            String deviceModelString = android.os.Build.MODEL;
	        	            valueElement = document.createElement("androiddevicemodel");
    	    	            androidVersionElement.appendChild(valueElement);
    	    			    valueElement.appendChild(document.createTextNode(deviceModelString));
    	    			    
    	    			    
	        	            //ANDROID Version
	        	            
	        	            Integer androidVersion = Build.VERSION.SDK_INT;
	        	            
	        	            switch(androidVersion){
	        	            	case 14:
	        	            		valueElement = document.createElement("androidversion");
	        	    	            androidVersionElement.appendChild(valueElement);
	        	    			    valueElement.appendChild(document.createTextNode("4.0 Ice Cream Sandwich"));
	        	            		break;
	        	            	case 15:
	        	            		valueElement = document.createElement("androidversion");
	        	    	            androidVersionElement.appendChild(valueElement);
	        	    			    valueElement.appendChild(document.createTextNode("4.03 Ice Cream Sandwich MR 1"));
	        	            		break;
	        	            	case 16:
	        	            		valueElement = document.createElement("androidversion");
	        	    	            androidVersionElement.appendChild(valueElement);
	        	    			    valueElement.appendChild(document.createTextNode("4.1 Jeally Bean"));
	        	            		break;
	        	            	case 17:
	        	            		valueElement = document.createElement("androidversion");
	        	    	            androidVersionElement.appendChild(valueElement);
	        	    			    valueElement.appendChild(document.createTextNode("4.2 Jeally Bean MR 1"));
	        	            		break;
	        	            	case 18:
	        	            		valueElement = document.createElement("androidversion");
	        	    	            androidVersionElement.appendChild(valueElement);
	        	    			    valueElement.appendChild(document.createTextNode("4.3 Jeally Bean MR 2"));
	        	            		break;
	        	            	case 19:
	        	            		valueElement = document.createElement("androidversion");
	        	    	            androidVersionElement.appendChild(valueElement);
	        	    			    valueElement.appendChild(document.createTextNode("4.4 Kitkat"));
	        	            		break;
	        	            	default:
	        	            		valueElement = document.createElement("androidversion");
	        	    	            androidVersionElement.appendChild(valueElement);
	        	    			    valueElement.appendChild(document.createTextNode("not supported"));
	        	            		break;
	        	            
	        	            }
	        	            

	        	            
	        	            //INTERNET SIGNAL
	        	            
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
	        	    		    valueElement.appendChild(document.createTextNode("no"));

	        	    	    }
	        	    	    
	        	    	    else{
	        	    	    	 int netType = Info.getType();
	        	    	         int netSubtype = Info.getSubtype();
	        	    	         
	        	    	        if (netType == ConnectivityManager.TYPE_WIFI) {
	        	    	        		        	    	           
	        	    	            valueElement = document.createElement("wificonnection");
	        	    	            internetSignalElement.appendChild(valueElement);
	        	    			    valueElement.appendChild(document.createTextNode("yes"));
	        	    			    
	        	    			    valueElement = document.createElement("mobileconnection");
	        	    			    mobileSignalElement.appendChild(valueElement);
	        	    			    valueElement.appendChild(document.createTextNode("no wifi"));

	        	    	            
	        	    	            WifiManager wifiManager = (WifiManager) getApplication().getSystemService(Context.WIFI_SERVICE);
	        	    	            List<ScanResult> scanResult = wifiManager.getScanResults();
	        	    	            for (int i = 0; i < scanResult.size(); i++) {
	        	    	            	
	        	    	            	Integer wifiStrengthInteger = scanResult.get(i).level;
	        	    	            	
	        	    	            	valueElement = document.createElement("wifistrength");
		        	    	            internetSignalElement.appendChild(valueElement);
		        	    			    valueElement.appendChild(document.createTextNode(wifiStrengthInteger.toString()));
	        	    	            
	        	    	            }
	        	    	        } 
	        	    	        else if (netType == ConnectivityManager.TYPE_MOBILE) {
	        	    	            
	        	    	            valueElement = document.createElement("wificonnection");
	        	    	            internetSignalElement.appendChild(valueElement);
	        	    			    valueElement.appendChild(document.createTextNode("no"));
	        	    			    
	        	    			    valueElement = document.createElement("mobileconnection");
	        	    			    mobileSignalElement.appendChild(valueElement);
	        	    			    valueElement.appendChild(document.createTextNode("GPRS/3G"));

	        	    			    
	        	    	            // Need to get differentiate between 3G/GPRS
	        	    	            
	        	    	            switch (netSubtype) {
	        	    	            case TelephonyManager.NETWORK_TYPE_1xRTT:
	        	    	                
	        	    	                valueElement = document.createElement("mobilesubtype");
	        	    				    mobileSignalElement.appendChild(valueElement);
	        	    				    valueElement.appendChild(document.createTextNode("1xRTT (50 - 100 Kbps)"));
	        	    	                
	        	    	            case TelephonyManager.NETWORK_TYPE_CDMA:
	        	    	            	
	        	    	                valueElement = document.createElement("mobilesubtype");
	        	    				    mobileSignalElement.appendChild(valueElement);
	        	    				    valueElement.appendChild(document.createTextNode("CDMA (3G) (2000 Kbps)"));
	        	    				    
	        	    	            case TelephonyManager.NETWORK_TYPE_EDGE:

	        	    	                valueElement = document.createElement("mobilesubtype");
	        	    				    mobileSignalElement.appendChild(valueElement);
	        	    				    valueElement.appendChild(document.createTextNode("EDGE  (100 - 120 Kbps)"));
	        	    				    
	        	    	            case TelephonyManager.NETWORK_TYPE_EVDO_0:
	        	    	            	
	        	    	                valueElement = document.createElement("mobilesubtype");
	        	    				    mobileSignalElement.appendChild(valueElement);
	        	    				    valueElement.appendChild(document.createTextNode("EVDO_0"));
	        	    				    
	        	    	            case TelephonyManager.NETWORK_TYPE_EVDO_A:
	        	    	            	
	        	    	                valueElement = document.createElement("mobilesubtype");
	        	    				    mobileSignalElement.appendChild(valueElement);
	        	    				    valueElement.appendChild(document.createTextNode("EVDO_A"));
	        	    				    
	        	    	            case TelephonyManager.NETWORK_TYPE_GPRS:
	        	    	            	
	        	    	                valueElement = document.createElement("mobilesubtype");
	        	    				    mobileSignalElement.appendChild(valueElement);
	        	    				    valueElement.appendChild(document.createTextNode("GPRS (2.5G) (40 - 50 Kbps)"));
	        	    				    
	        	    	            case TelephonyManager.NETWORK_TYPE_HSDPA:
	        	    	            	
	        	    	                valueElement = document.createElement("mobilesubtype");
	        	    				    mobileSignalElement.appendChild(valueElement);
	        	    				    valueElement.appendChild(document.createTextNode("HSDPA (4G) (2000 - 14000 Kbps)"));
	        	    				    
	        	    	            case TelephonyManager.NETWORK_TYPE_HSPA:
	        	    	            	
	        	    	                valueElement = document.createElement("mobilesubtype");
	        	    				    mobileSignalElement.appendChild(valueElement);
	        	    				    valueElement.appendChild(document.createTextNode("HSPA (4G) (700 - 1700 Kbps)"));
	        	    				    
	        	    	            case TelephonyManager.NETWORK_TYPE_HSUPA:
	        	    	            	
	        	    	                valueElement = document.createElement("mobilesubtype");
	        	    				    mobileSignalElement.appendChild(valueElement);
	        	    				    valueElement.appendChild(document.createTextNode("HSUPA (3G) (1000 - 23000 Kbps)"));
	        	    				    
	        	    	            case TelephonyManager.NETWORK_TYPE_UMTS:
	        	    	            	
	        	    	                valueElement = document.createElement("mobilesubtype");
	        	    				    mobileSignalElement.appendChild(valueElement);
	        	    				    valueElement.appendChild(document.createTextNode("UMTS (3G) (400 - 7000 Kbps)"));
	        	    				    
	        	    	                // NOT AVAILABLE YET IN API LEVEL 7
	        	    				    
	        	    				    
	        	    	            case TelephonyManager.NETWORK_TYPE_EHRPD:
	        	    	            	
	        	    	                valueElement = document.createElement("mobilesubtype");
	        	    				    mobileSignalElement.appendChild(valueElement);
	        	    				    valueElement.appendChild(document.createTextNode("EHRPD"));
	        	    				    
	        	    	            case TelephonyManager.NETWORK_TYPE_EVDO_B:
	        	    	            	
	        	    	                valueElement = document.createElement("mobilesubtype");
	        	    				    mobileSignalElement.appendChild(valueElement);
	        	    				    valueElement.appendChild(document.createTextNode("EVDO_B"));
	        	    				    
	        	    	            case TelephonyManager.NETWORK_TYPE_HSPAP:
	        	    	            	
	        	    	                valueElement = document.createElement("mobilesubtype");
	        	    				    mobileSignalElement.appendChild(valueElement);
	        	    				    valueElement.appendChild(document.createTextNode("HSPA+ (4G) (10000 - 20000 Kbps)"));
	        	    				    
	        	    	            case TelephonyManager.NETWORK_TYPE_IDEN:
	        	    	            	
	        	    	                valueElement = document.createElement("mobilesubtype");
	        	    				    mobileSignalElement.appendChild(valueElement);
	        	    				    valueElement.appendChild(document.createTextNode("IDEN"));
	        	    				    
	        	    	            case TelephonyManager.NETWORK_TYPE_LTE:
	        	    	                
	        	    	                valueElement = document.createElement("mobilesubtype");
	        	    				    mobileSignalElement.appendChild(valueElement);
	        	    				    valueElement.appendChild(document.createTextNode("LTE (4G) (10000+ Kbps)"));
	        	    	                
	        	    	            }
	        	    	        } else {
	        	    	            valueElement = document.createElement("mobilesubtype");
        	    				    mobileSignalElement.appendChild(valueElement);
        	    				    valueElement.appendChild(document.createTextNode("ERROR"));
	        	    	        }
	        	    	    }
	        	            
	        	    	    
	        	    	 //NUR JeallyBean
	        	    	    
	        	    	 /*
	        	    	  if(androidVersion >= 16){
	        	    		  //this code will be executed on devices running on DONUT (NOT ICS) or later
	        	    	  
		        	    	   stringToWrite=stringToWrite+"Signal Strength \n";
		        	    	   
		        	    	   valueElement = document.createElement("mobiesignal");
		        			   mobileSignalElement.appendChild(valueElement);
		        	    	   valueElement.appendChild(document.createTextNode("LTE (4G) (10000+ Kbps)"));
		        	    	   
		        	    	   
		        	    	    TelephonyManager telephonyManager = (TelephonyManager)this.getSystemService(Context.TELEPHONY_SERVICE);
		        	    	 // for example value of first element
		        	    	 CellInfoGsm cellinfogsm = (CellInfoGsm)telephonyManager.getAllCellInfo().get(0);
		        	    	 CellSignalStrengthGsm cellSignalStrengthGsm = cellinfogsm.getCellSignalStrength();
		        	    	 cellSignalStrengthGsm.getDbm();	
		        			
		        	    	    
		        	    	 
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
	        	    	    
	        	    	    /*
	        	    	    
	        	    	    
	        	    	    
	        	    	    //NUR Galaxy S4
	        	    	    
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
    	    			    
    	    			    valueElement = document.createElement("freesdstorage");
    	    	            sdCardStorageElement.appendChild(valueElement);
    	    			    valueElement.appendChild(document.createTextNode(freeSDSpaceInMB.toString()));
    	    			    
    	    			    valueElement = document.createElement("usedsdstorage");
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
	        	    	    
	        	    	    
	        	    	    //BATTERY VALUES
	        	    	    
	        	    	    Element batteryElement = document.createElement("battery");
	        	            rootElement.appendChild(batteryElement);
	        	    	    
	        	    	    IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
	        	    	    Intent batteryStatus = registerReceiver(null, ifilter);
	        	    	    
	        	    	    Integer level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
	        	    	    Double temp = (double) (batteryStatus.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, -1)) / 10;
	        			    Double voltage = (double) batteryStatus.getIntExtra(BatteryManager.EXTRA_VOLTAGE, -1)/1000;
	        			    Integer status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
	        			    String  technology= batteryStatus.getExtras().getString(BatteryManager.EXTRA_TECHNOLOGY);
	        	    	    
	        	    	    valueElement = document.createElement("batteryvalue");
    	    	            batteryElement.appendChild(valueElement);
    	    			    valueElement.appendChild(document.createTextNode(level.toString()));

    	    			    valueElement = document.createElement("batterytemperature");
    	    	            batteryElement.appendChild(valueElement);
    	    			    valueElement.appendChild(document.createTextNode(f.format(temp)+" ¡C"));

    	    			    valueElement = document.createElement("batteryvoltage");
    	    	            batteryElement.appendChild(valueElement);
    	    			    valueElement.appendChild(document.createTextNode(voltage.toString() + " V"));
    	    			    
    	    			    boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
    	    	                     status == BatteryManager.BATTERY_STATUS_FULL;
    	    			    
    	    			    String statusTextString;
    	    			    
    	    			    if(isCharging){
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
    	    			    
    	    			    
    	    			    
    	    			    
    	    			  //CPU
    	    			    
    	    			    Element cpuElement = document.createElement("cpu");
	        	            rootElement.appendChild(cpuElement);
	        	            
	        	            valueElement = document.createElement("cpumaxfrequency");
    	    	            cpuElement.appendChild(valueElement);
    	    			    valueElement.appendChild(document.createTextNode(getMaxCPUFreqMHz().toString()+" Mhz"));
    	    			    
    	    			    valueElement = document.createElement("cpucorenumber");
    	    	            cpuElement.appendChild(valueElement);
    	    			    valueElement.appendChild(document.createTextNode(getNumCores().toString()));
    	    			    
    	    			    
    	    			    //DISPLAY
	        	    	    
    	    			    WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
    	    			    Display display = wm.getDefaultDisplay();
    	    			    Point size = new Point();
	        	    	    display.getSize(size);
	        	    	    Integer width = size.x;
	        	    	    Integer heigth = size.y;
	        	    	    
	        	    	    
	        	    	    DisplayMetrics dm = new DisplayMetrics();
	        	    	    wm.getDefaultDisplay().getMetrics(dm);
	        	    	    Double x = Math.pow(dm.widthPixels/dm.xdpi,2);
	        	    	    Double y = Math.pow(dm.heightPixels/dm.ydpi,2);
	        	    	    Double screenInches = Math.sqrt(x+y);
	        	    	    
	        	    	    Element displayElement = document.createElement("display");
	        	            rootElement.appendChild(displayElement);
	        	            
	        	            valueElement = document.createElement("displaywidth");
    	    	            displayElement.appendChild(valueElement);
    	    			    valueElement.appendChild(document.createTextNode(f.format(x)+" cm"));
    	    			    
    	    			    valueElement = document.createElement("displayheigth");
    	    	            displayElement.appendChild(valueElement);
    	    			    valueElement.appendChild(document.createTextNode(f.format(y)+" cm"));
    	    			    
    	    			    valueElement = document.createElement("displaysize");
    	    	            displayElement.appendChild(valueElement);
    	    			    valueElement.appendChild(document.createTextNode(f.format(screenInches)+" ''"));
    	    			    
    	    			    valueElement = document.createElement("displayresolution");
    	    	            displayElement.appendChild(valueElement);
    	    			    valueElement.appendChild(document.createTextNode(width.toString()+" x "+heigth.toString()));
    	    			    
    	    			    
    	    			    //SENSORS
    	    			    
    	    			    final SensorManager mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
    	    			    List<Sensor> sensorList = mSensorManager.getSensorList(Sensor.TYPE_ALL);
    	    			    
    	    			    Element sensorElement = document.createElement("sensorlist");
	        	            rootElement.appendChild(sensorElement);
	        	            
	        	            Integer sensorAmount = sensorList.size();

	        	            valueElement = document.createElement("sensoramount");
    	    	            sensorElement.appendChild(valueElement);
    	    			    valueElement.appendChild(document.createTextNode(sensorAmount.toString()));
	        	            
    	    			    for(int i=0;i<sensorAmount;i++) {
    	    			    	
    	    			    	valueElement = document.createElement("sensor");
        	    	            sensorElement.appendChild(valueElement);
        	    			    valueElement.appendChild(document.createTextNode(sensorList.get(i).getName()));
    	    			    	
    	    			    }
    	    			    
    	    			    
    	    			    
    	    			    
    	    			    
    	    			    
    	    			    
    	    			    
    	    			    
    	    			    //PROVIDER Types
    	    			    
    		        		final LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
    	    			    
    	    			    Element providerElement = document.createElement("provider");
	        	            rootElement.appendChild(providerElement);
    	    			    
	        	            List<String> providerNamesList = locationManager.getAllProviders();
	        	            
	        	            for(String providerType : providerNamesList){
	        	            
	    	    			valueElement = document.createElement("providerTypes");
    	    	            providerElement.appendChild(valueElement);
    	    			    valueElement.appendChild(document.createTextNode(providerType));
    	    			    
	        	            }
    	    			    
	        	            
	        	            
	        	            
	        	            
	        	            
	        	            
    	    			    
    	    			    //LATITUDE AND LOGITUDE
	        	            
	        	            /*
							class MyLocationListener implements LocationListener
	        	            {

	        	              @Override
	        	              public void onLocationChanged(Location loc)
	        	              {

	        	                Double latitude = loc.getLatitude();
	        	                Double longitude = loc.getLongitude();

	        	                String Text = "My current location is: " +
	        	                "Latitud = " + loc.getLatitude() +
	        	                "Longitud = " + loc.getLongitude();

	        	                Toast.makeText( getApplicationContext(), Text, Toast.LENGTH_SHORT).show();
	        	                
	        	                
	        	                Element locationElement = document.createElement("location");
	    	        	            rootElement.appendChild(locationElement);
	    	        	            
	    	        	            Element valueElement;
	        	    			    
	    	        	            valueElement = document.createElement("latitude");
	        	    	            locationElement.appendChild(valueElement);
	        	    			    valueElement.appendChild(document.createTextNode(latitude.toString()));
	        	    			    
	        	    			    valueElement = document.createElement("longitude");
	        	    	            locationElement.appendChild(valueElement);
	        	    			    valueElement.appendChild(document.createTextNode(longitude.toString()));
	        	                
	        	                
	        	              }

	        	              @Override
	        	              public void onProviderDisabled(String provider)
	        	              {
	        	                Toast.makeText( getApplicationContext(), "Gps Disabled", Toast.LENGTH_SHORT ).show();
	        	              }

	        	              @Override
	        	              public void onProviderEnabled(String provider)
	        	              {
	        	                Toast.makeText( getApplicationContext(), "Gps Enabled", Toast.LENGTH_SHORT).show();
	        	              }

	        	              @Override
	        	              public void onStatusChanged(String provider, int status, Bundle extras)
	        	              {

	        	              }
	        	            }
	        	            
	        	            
	        	            
	        	            
	        	            
	        	            
	        	            final LocationListener locationListener = new MyLocationListener();

	        	                public void onLocationChanged(Location location) {
	        	                    updateWithNewLocation(location);
	        	                }

	        	                public void onProviderDisabled(String provider) {
	        	                    updateWithNewLocation(null);
	        	                }

	        	                public void onProviderEnabled(String provider) {}

	        	                public void onStatusChanged(String provider,int status,Bundle extras){}
	        	            };
	        	            
*/
	        	                       
	        	            
	        	            
	        	            
	        	            handler.post(new Runnable() { // This thread runs in the UI
	                            @Override
	                            public void run() {
	                                
	                            	
	                            	 if( locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
	      	        	        	   
	      	        	        	   
//	      		        	            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,0, locationListener);
	      		        	            Location loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
	      		        	            
	      		        	          Double latitude = loc.getLatitude();
	  	        	                Double longitude = loc.getLongitude();

	  	        	                String Text = "My current location is: " +
	  	        	                "Latitud = " + loc.getLatitude() +
	  	        	                "Longitud = " + loc.getLongitude();

	  	        	                Toast.makeText( getApplicationContext(), Text, Toast.LENGTH_SHORT).show();
	  	        	                
	  	        	                
	  	        	                Element locationElement = document.createElement("location");
	  	    	        	            rootElement.appendChild(locationElement);
	  	    	        	            
	  	    	        	            Element valueElement;
	  	        	    			    
	  	    	        	            valueElement = document.createElement("latitude");
	  	        	    	            locationElement.appendChild(valueElement);
	  	        	    			    valueElement.appendChild(document.createTextNode(latitude.toString()));
	  	        	    			    
	  	        	    			    valueElement = document.createElement("longitude");
	  	        	    	            locationElement.appendChild(valueElement);
	  	        	    			    valueElement.appendChild(document.createTextNode(longitude.toString()));
	      		        	            
	                                 }
		                            	
		                            	
		                            	
		                            	
	                            }
	                        });
	      		        	           /* Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
	      		        	            
	      	    	    				
	      	    	    		        if (location != null) {
	      	    	    		        	
	 	      	        	        	   System.out.println("test");

	      	    	    		            Double latitude = location.getLatitude();
	      	    	    		            Double longitude = location.getLongitude();
	      	    	    		            
	      	    	    		            Element locationElement = document.createElement("location");
	      	    	        	            rootElement.appendChild(locationElement);
	      	    	        	            
	      	    	        	            Element valueElement;
	      	        	    			    
	      	    	        	            valueElement = document.createElement("latitude");
	      	        	    	            locationElement.appendChild(valueElement);
	      	        	    			    valueElement.appendChild(document.createTextNode(latitude.toString()));
	      	        	    			    
	      	        	    			    valueElement = document.createElement("longitude");
	      	        	    	            locationElement.appendChild(valueElement);
	      	        	    			    valueElement.appendChild(document.createTextNode(longitude.toString()));
	      	    	    		        }
	      	        	           }
	                            	
	                            	
	                            	
	                            	
	                            }
	                        });
	        	            
	        	          
    	    			 
    	    			   LocationListener locationListener = new LocationListener() {
    	    				   
    	    				   Double longitudeValue =0.0;
    	    				   Double latitudeValue=0.0;
    	    				   String longitudeValueString="";
    	    				   String latitudeValueString="";
								
								@Override
								public void onStatusChanged(String provider, int status, Bundle extras) {
									// TODO Auto-generated method stub
									
								}
								
								@Override
								public void onProviderEnabled(String provider) {
									// TODO Auto-generated method stub
									
								}
								
								@Override
								public void onProviderDisabled(String provider) {
									// TODO Auto-generated method stub
									
								}
								
								@Override
								public void onLocationChanged(Location location) {
									// TODO Auto-generated method stub
									longitudeValue = location.getLongitude();
									latitudeValue = location.getLatitude();
									longitudeValueString =longitudeValue.toString();
									latitudeValueString = latitudeValueString.toString();
									
								}
								
								public String getLatitudeString(){
									return this.latitudeValueString;
								}
								
							};
							
							Element locationElement = document.createElement("location");
	        	            rootElement.appendChild(locationElement);
	        	            
    	    			    
    	    			    valueElement = document.createElement("latitude");
    	    	            locationElement.appendChild(valueElement);
    	    			    valueElement.appendChild(document.createTextNode(String.valueOf(gpsTracker.latitude)));
    	    			    
    	    			    
	        	            valueElement = document.createElement("latitude");
    	    	            locationElement.appendChild(valueElement);
    	    			    valueElement.appendChild(document.createTextNode(latitude.toString);
	        	            
    	    			    valueElement = document.createElement("longitude");
    	    	            locationElement.appendChild(valueElement);
    	    			    valueElement.appendChild(document.createTextNode(String.valueOf(gpsTracker.longitude)));
    	    			    */
    	    			    
//    	    			    System.out.println(gpsTracker.getLocality(myContext));  
	        	            
    	    				
    	    				
    	    			    
    	    			   
    	    			        	    			    
    	    			    //APPS
    	    			    
    	    			  final PackageManager pm = getPackageManager();
    	    			  //get a list of installed apps.
    	    			  List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);
    	    			  
    	    			  Element appListElement = document.createElement("applist");
	        	          rootElement.appendChild(appListElement);
	        	          
	        	          Integer appAmount = packages.size();
	        	          
	        	          valueElement = document.createElement("appamount");
	        	          appListElement.appendChild(valueElement);
	        	          valueElement.appendChild(document.createTextNode(appAmount.toString()));
    	    			  
    	    			  for (ApplicationInfo packageInfo : packages) {
    	    			      //String[] packagePart = packageInfo.packageName.split("\\.");
	    			    	  //String appName = packagePart[packagePart.length-1];
    	    				  
//    	    				  System.out.println(packageInfo.packageName);
	    			    	  
	    			    	  valueElement = document.createElement("appname");
		        	          appListElement.appendChild(valueElement);
		        	          valueElement.appendChild(document.createTextNode(packageInfo.packageName));
    	    			  }
    	    			      
    	    			  
    	    			  //LIGHT SENSOR
    	    			  
    	    			  /*
    	    			  handler.post(new Runnable() { // This thread runs in the UI
	                            @Override
	                            public void run() {
	                                
	                            	  mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

	            	    			  mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT), mSensorManager.SENSOR_DELAY_GAME);

	                            	
	            	    			  Float currentLux = event.values[0];
			  	    			         
										Element lightElement = document.createElement("lightsensor");
					        	         rootElement.appendChild(lightElement);
					        	          
					        	          
					        	          Element valueElement = document.createElement("lightvalue");
					        	          lightElement.appendChild(valueElement);
					        	          valueElement.appendChild(document.createTextNode(currentLux.toString()));	
		                            	
		                            	
	                            }
	                        });
    	    			  */
    	    			
    	    			  
    	    			final SensorEventListener LightSensorEventListener = new SensorEventListener(){
    	    			  
							@Override
							public void onAccuracyChanged(Sensor sensor,
									int accuracy) {
								// TODO Auto-generated method stub
								
							}
	
							@Override
							public void onSensorChanged(SensorEvent event) {
									// TODO Auto-generated method stub
									if( event.sensor.getType() == Sensor.TYPE_LIGHT){
										
										Float currentLux = event.values[0];
			  	    			         
										Element lightElement = document.createElement("lightsensor");
					        	         rootElement.appendChild(lightElement);
					        	          
					        	          
					        	          Element valueElement = document.createElement("lightvalue");
					        	          lightElement.appendChild(valueElement);
					        	          valueElement.appendChild(document.createTextNode(currentLux.toString()));
								}
							}	
	    	    		 };
    	    		
	    	    		 
	    	    		 
	    	    		 
	    	    		 Sensor currentSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT );
	    	    		 if(currentSensor != null){
//	    	    		 ÊÊÊmSensorManager.registerListener(currentSensor, SensorManager.SENSOR_DELAY_FASTEST);
	    	    		 }
    	    			  
    	    			  
    	    			  
    	    			  
    	    			//NUR mit Temperature Sensor
    	    		      
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
	        	    	    
	        	    	    
	        	    	    try {
	        	                
	        	    			TransformerFactory factory = TransformerFactory.newInstance();
	        	    			Transformer transformer = factory.newTransformer();
	        	    			Properties outFormat = new Properties();
	        	    	        outFormat.setProperty(OutputKeys.INDENT, "yes");
	        	    	        outFormat.setProperty(OutputKeys.METHOD, "xml");
	        	    	        outFormat.setProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
	        	    	        outFormat.setProperty(OutputKeys.VERSION, "1.0");
	        	    	        outFormat.setProperty(OutputKeys.ENCODING, "UTF-8");
	        	    	        //outFormat.setProperty(OutputKeys.DOCTYPE_SYSTEM, "properties.dtd");
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
	 private Integer getMaxCPUFreqMHz() {

		    int maxFreq = -1;
		    try {

		        @SuppressWarnings("resource")
				RandomAccessFile reader = new RandomAccessFile( "/sys/devices/system/cpu/cpu0/cpufreq/stats/time_in_state", "r" );

		        boolean done = false;
		        while ( ! done ) {
		            String line = reader.readLine();
		            if ( null == line ) {
		                done = true;
		                break;
		            }
		            String[] splits = line.split( "\\s+" );
		            assert ( splits.length == 2 );
		            int timeInState = Integer.parseInt( splits[1] );
		            if ( timeInState > 0 ) {
		                int freq = Integer.parseInt( splits[0] ) / 1000;
		                if ( freq > maxFreq ) {
		                    maxFreq = freq;
		                }
		            }
		        }

		    } catch ( IOException ex ) {
		        ex.printStackTrace();
		    }

		    return maxFreq;
		}
	 
	 private Integer getNumCores() {
		    //Private Class to display only CPU devices in the directory listing
		    class CpuFilter implements FileFilter {
		        @Override
		        public boolean accept(File pathname) {
		            //Check if filename is "cpu", followed by a single digit number
		            if(Pattern.matches("cpu[0-9]+", pathname.getName())) {
		                return true;
		            }
		            return false;
		        }      
		    }

		    try {
		        //Get directory containing CPU info
		        File dir = new File("/sys/devices/system/cpu/");
		        //Filter to only list the devices we care about
		        File[] files = dir.listFiles(new CpuFilter());
		        //Return the number of cores (virtual CPU devices)
		        return files.length;
		    } catch(Exception e) {
		        //Default to return 1 core
		        return 1;
		    }
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


