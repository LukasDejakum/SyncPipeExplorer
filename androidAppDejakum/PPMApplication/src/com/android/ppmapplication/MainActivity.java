package com.android.ppmapplication;

import java.util.List;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.android.ppmapplication.services.CreateContactsXML;
import com.android.ppmapplication.services.ReadContactsXML;

import android.net.Uri;
import android.os.Bundle;
import android.provider.Contacts;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
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

            
       Log.d(TAG, "onClick: starting service contacts");
       startService(new Intent(this, CreateContactsXML.class));
       
       Log.d(TAG, "onClick: starting service properties");
       startService(new Intent(this, CreatePropertiesXML.class));               
            
       Log.d(TAG, "onClick: starting service read");
       startService(new Intent(this, ReadContactsXML.class));
       

       
       
       
       List<Sensor> sensorList = mSensorManager.getSensorList(Sensor.TYPE_ALL);

	    Log.w(TAG, "sl size = " + sensorList.size());
	    for(int i=0;i<sensorList.size();i++) {
	        Log.w(TAG, "sn = " + sensorList.get(i).getName());
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
	

}

