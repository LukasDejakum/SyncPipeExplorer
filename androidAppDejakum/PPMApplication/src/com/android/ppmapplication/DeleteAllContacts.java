package com.android.ppmapplication;

import android.app.Service;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.IBinder;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Toast;

public class DeleteAllContacts extends Service{
	private static final String TAG = "ReadContactsXML";

	public IBinder onBind(Intent intent) {
		return null;
	}
	
	@Override
	public void onCreate() {
		Toast.makeText(this, "My Service DeleteAllContacts Created ", Toast.LENGTH_LONG).show();
		Log.d(TAG, "onCreate DeleteAllContacts");
		
		this.initialize();
	}

	@Override
	public void onDestroy() {
		//Toast.makeText(this, "My Service Stopped Contacts", Toast.LENGTH_SHORT).show();
		Log.d(TAG, "onDestroy DeleteAllContacts");
		
	}
	
	@Override
	public void onStart(Intent intent, int startid) {
		//Toast.makeText(this, "My Service Started DeleteAllContacts", Toast.LENGTH_SHORT).show();
		Log.d(TAG, "onStart DeleteAllContacts");
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
            	deleteAllContact();
        		stopSelf();
        	} 
        });
        th.start();
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


}

