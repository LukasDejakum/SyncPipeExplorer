package com.android.ppmapplication;

import java.io.File;
import java.util.List;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
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
		
		
		File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/SyncPipe/");

		
		if(dir.exists()){
			dir.delete();
		}
		
		dir.mkdir();

            
       Log.d(TAG, "onClick: starting service contacts");
       startService(new Intent(this, CreateContactsXML.class));
       
       Log.d(TAG, "onClick: starting service properties");
       startService(new Intent(this, CreatePropertiesXML.class));               
       
       Log.d(TAG, "onClick: starting service fileSystem");
       startService(new Intent(this, CreateFileSystemXML.class));

       Log.d(TAG, "onClick: starting service read");
       startService(new Intent(this, ReadContactsXML.class));
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
	
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	/*private void createSubDirs(Node node) {
		NodeList nodeList = node.getChildNodes();
		Log.i(TAG,Integer.toString(nodeList.getLength()));
		Log.i(TAG, nodeList.item(0).getNodeName());
		//Log.i(TAG, nodeList.item(index))
		for (int i = 0; i < nodeList.getLength(); i++) {
			
			
			if (nodeList.item(i).getNodeName()=="d") {
					currentTreeNode = new DefaultMutableTreeNode(((Element) nodeList.item(i)).getAttribute("name"));
					treeNode.add(currentTreeNode);
					createSubDirs(nodeList.item(i), currentTreeNode);
		    }
			else if(nodeList.item(i).getNodeName()=="f"){
				treeNode.add(new DefaultMutableTreeNode(((Element) nodeList.item(i)).getAttribute("name")));
			}
		
		}
	}*/
	
	
}

