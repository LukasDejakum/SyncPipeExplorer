package com.android.ppmapplication;

import java.io.File;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.content.Intent;
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

       //Log.d(TAG, "onClick: starting service read");
       //startService(new Intent(this, ReadContactsXML.class));
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
}

