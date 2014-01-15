package com.android.ppmapplication;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
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

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class CreateFileSystemXML extends Service{
	
	private static final String TAG = "CreateFileSystemXML";

	
	private Document document;
    private Element actualElement;
    @SuppressLint("SimpleDateFormat")
	private SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
    
    @Override
	public void onCreate() {
		Log.d(TAG, "onCreate FileSystem");
		
		this.initialize();
	}

	@Override
	public void onDestroy() {
		Toast.makeText(this, "My Service Stopped Contacts", Toast.LENGTH_SHORT).show();
		Log.d(TAG, "onDestroy FileSystem");
		
	}
	
	@Override
	public void onStart(Intent intent, int startid) {
		Toast.makeText(this, "My Service Started Contacts read", Toast.LENGTH_SHORT).show();
		Log.d(TAG, "onStart FileSystem");
		
		//this.initialize();	
	}
    
	private void showDirs(File dir, Element above){
	    	if(dir.listFiles()!=null){
		        for(File f:dir.listFiles()){
		            if(f.isDirectory()){
		            	actualElement=document.createElement("d");
		            	actualElement.setAttribute("name", f.getName());
//		            	actualElement.setAttribute("size", ""+f.length());
//		            	actualElement.setAttribute("date", sdf.format(f.lastModified()));
		            	above.appendChild(actualElement);
		                showDirs(f, actualElement);
		            }
		            else{
		            	actualElement=document.createElement("f");
		            	actualElement.setAttribute("name", f.getName());
//		            	actualElement.setAttribute("size", ""+f.length());
//		            	actualElement.setAttribute("date", sdf.format(f.lastModified()));
		            	above.appendChild(actualElement);
		            }
		        }
	    	}
	 }
	
	
	public void createFile() {
		
		String contactsFileNameString = "filesystem.xml";

		File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/SyncPipe/");
		File xmlFile = new File(dir, File.separator + contactsFileNameString);


		if(xmlFile.exists()){
 			xmlFile.delete();
 		}
 		
 		try {
 	        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
 	            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
 	            document = documentBuilder.newDocument();

 	            String[] rootDirParts = Environment.getExternalStorageDirectory().getAbsolutePath().split("/");
 	            Element rootElement = document.createElement(rootDirParts[rootDirParts.length-1]);
 	            rootElement.setAttribute("type", "d");
 	            document.appendChild(rootElement);

 	            showDirs(new File(Environment.getExternalStorageDirectory().getAbsolutePath()), rootElement);

 	            TransformerFactory factory = TransformerFactory.newInstance();
 	            Transformer transformer = factory.newTransformer();
 	            Properties outFormat = new Properties();
 	            outFormat.setProperty(OutputKeys.INDENT, "yes");
 	            outFormat.setProperty(OutputKeys.METHOD, "xml");
 	            outFormat.setProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
 	            outFormat.setProperty(OutputKeys.VERSION, "1.0");
 	            outFormat.setProperty(OutputKeys.ENCODING, "UTF-8");
 	            //outFormat.setProperty(OutputKeys.DOCTYPE_SYSTEM, "fileSystem.dtd");
 	            transformer.setOutputProperties(outFormat);
 	            DOMSource domSource = 
 	            new DOMSource(document.getDocumentElement());
 	            OutputStream output = new ByteArrayOutputStream();
 	            StreamResult result = new StreamResult(output);
 	            transformer.transform(domSource, result);
 	            String xmlString = output.toString();
 	            
 	            FileWriter writer = new FileWriter(xmlFile,true);
 				writer.write(xmlString);
 				writer.flush();
 				writer.close();
 	            
 	        } catch (ParserConfigurationException e) {
 	        } catch (TransformerConfigurationException e) {
 	        } catch (TransformerException e) {
 	        } catch (IOException e) {
 				e.printStackTrace();
 			}
	}

	public void initialize(){
        Thread th = new Thread(new Runnable() {

            @Override
            public void run() {
            	createFile();
        	} 
        });
        th.start();
    }

	public IBinder onBind(Intent intent) {
		return null;
	}
}
	

