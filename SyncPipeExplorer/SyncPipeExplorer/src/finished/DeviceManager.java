package finished;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class DeviceManager {

	//ALWAYS CALL readInADBPath() first !!!
	
	private static List<String> commands = new ArrayList<String>();
	
	private static File contactsFile = new File("M:/SyncPipe/contact.xml");
	private static File propertiesFile = new File("M:/SyncPipe/properties.xml");
	private static File filesystemFile = new File("M:/SyncPipe/filesystem.xml");
	private static File syncPipeProperties;
	private static String adbPath;
	
	public static boolean deviceConnected(){
		commands.add(adbPath);
		commands.add("devices");
		
		InputStream is;
		InputStreamReader isr;
		BufferedReader br;
		String line;

		ProcessBuilder pb = new ProcessBuilder(commands);
		Process p;
		try {
			p = pb.start();
			is = p.getInputStream();
			isr = new InputStreamReader(is);
			br = new BufferedReader(isr);
			int counter=0;
			line = br.readLine();
			while(line!=null){
				if(counter==2)return true;
				line=br.readLine();
				counter++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	public static void listen() throws IOException, InterruptedException{
		System.out.println("Connecting...");
		commands.add(adbPath);
		commands.add("wait-for-devices");
		ProcessBuilder pb = new ProcessBuilder (commands);
		Process p;
		p = pb.start();
		p.waitFor();
		commands.clear();
		p.destroy();
		System.out.println("Connected");
	}
	public static void startApp() throws IOException, InterruptedException{
		System.out.println("Starting App");
		commands.add (adbPath);
		commands.add("shell");
		commands.add ("am start -a android.intent.action.START -n com.android.ppmapplication/.MainActivity");
		ProcessBuilder pb = new ProcessBuilder (commands);
		Process p = pb.start();
		p.waitFor();
		p.destroy();
		System.out.println("App started");
	}
	public static void doFullBackup() throws IOException, InterruptedException{
		startBackup();
		commands.add("-shared");
		commands.add("-all");
//		commands.add("-shared");
//		commands.add("-all");
		commands.add("-f C:\\backup.ab");
		workOffProcess();
		System.out.println("Backup finished");
	}
	public static void doSystemAndAppsBackup() throws IOException, InterruptedException{
		startBackup();
		commands.add("–f C:\backup1.ab");
		commands.add("-all");
		workOffProcess();
		System.out.println("Backup finished");
	}
	public static void doSystemBackup() throws IOException, InterruptedException{
		startBackup();
		commands.add("–system –f C:\backup1.ab");
		workOffProcess();
		System.out.println("Backup finished");
	}
	public static void doAppsBackup() throws IOException, InterruptedException{
		startBackup();
		commands.add("–all -nosystem –f C:\backup1.ab");
		workOffProcess();
		System.out.println("Backup finished");
	}
	public static void readInADBPath(){
		try {
			System.out.println("create new");
			propertiesFile.createNewFile();
			filesystemFile.createNewFile();
			contactsFile.createNewFile();
			contactsFile.delete();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DeviceManager.adbPath=getADBPath();
	}
	public static void setSyncPipePropertiesFile(File properties){
		DeviceManager.syncPipeProperties=properties;
	}
	private static String getADBPath(){
		String line;
		String adbPath=null;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(syncPipeProperties));
			while(adbPath==null){
				line = reader.readLine();
				if(line.toLowerCase().contains("adbpath")){
					adbPath = line.split(",")[1];
				}
			}
			DeviceManager.adbPath=adbPath;
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return adbPath;
	}
	public static long getTotalInternalStorage() throws SAXException, IOException, ParserConfigurationException{
		NodeList totalInternalStorage = getDocument(propertiesFile).getElementsByTagName("totalinternalstorage");
		return Long.parseLong(totalInternalStorage.item(0).getTextContent());
	}
	public static long getFreeInternalStorage() throws SAXException, IOException, ParserConfigurationException{
		NodeList freeInternalStorage = getDocument(propertiesFile).getElementsByTagName("freeinternalstorage");
		return Long.parseLong(freeInternalStorage.item(0).getTextContent());
	}
	private static Document getDocument(File file) throws SAXException, IOException, ParserConfigurationException{
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
    	DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
		return docBuilder.parse(propertiesFile);
	}
	private static void startBackup(){
		commands.clear();
		System.out.println("Starting Backup");
		commands.add (adbPath);
		commands.add("backup");
	}
	private static void workOffProcess() throws IOException, InterruptedException{
		System.out.println(commands);
		ProcessBuilder pb = new ProcessBuilder (commands);
		Process p = pb.start();
//		commands.clear();
		p.waitFor();
		p.destroy();
	}
	public static String getAndroidVersion() throws SAXException, IOException, ParserConfigurationException{
		NodeList androidVersion = getDocument(propertiesFile).getElementsByTagName("androidversion");
		return (androidVersion.item(0).getTextContent());
	}
	public static String getTotalRAM() throws SAXException, IOException, ParserConfigurationException{
		NodeList totalRAM = getDocument(propertiesFile).getElementsByTagName("totalram");
		return (totalRAM.item(0).getTextContent());
	}
	public static String getFreeRAM() throws SAXException, IOException, ParserConfigurationException{
		NodeList freeRAM = getDocument(propertiesFile).getElementsByTagName("freeram");
		return (freeRAM.item(0).getTextContent());
	}
	public static int getBatteryPercentage() throws SAXException, IOException, ParserConfigurationException{
		NodeList batteryPercentage = getDocument(propertiesFile).getElementsByTagName("batteryvalue");
		return Integer.parseInt(batteryPercentage.item(0).getTextContent());
	}
	public static int getBatteryTemperature() throws SAXException, IOException, ParserConfigurationException{
		NodeList batteryTemperature = getDocument(propertiesFile).getElementsByTagName("batterytemperature");
		return Integer.parseInt(batteryTemperature.item(0).getTextContent());
	}
	public static String getBatteryStatus() throws SAXException, IOException, ParserConfigurationException{
		NodeList batteryStatus = getDocument(propertiesFile).getElementsByTagName("batterystatus");
		return (batteryStatus.item(0).getTextContent());
	}
	public static String getWifiStatus() throws SAXException, IOException, ParserConfigurationException{
		NodeList wifiStatus = getDocument(propertiesFile).getElementsByTagName("wificonnection");
		return (wifiStatus.item(0).getTextContent());
	}
	public static String getMobileConnection() throws SAXException, IOException, ParserConfigurationException{
		NodeList wifiStatus = getDocument(propertiesFile).getElementsByTagName("mobileconnection");
		return (wifiStatus.item(0).getTextContent());
	}
	public static File getContactsFile() throws IOException, InterruptedException{
		System.out.println("Hole kontakt file");
		commands.clear();
		long firstSize=0;
		long secondSize=0;;
		ProcessBuilder pb = new ProcessBuilder(adbPath, "pull",  "/mnt/sdcard/SyncPipe/contact.xml", "M:/SyncPipe");
		do{
			Thread.sleep(1000);
			contactsFile = new File("M:/SyncPipe/contact.xml");
			firstSize=contactsFile.length();
			Process p = pb.start();
			p.waitFor();
			System.out.println("first size:"+firstSize);
			System.out.println("second size:"+secondSize);
			Thread.sleep(100);
			secondSize=contactsFile.length();
			p.destroy();
			//if file size changed -> app is still creating the file
		}while(firstSize != secondSize || firstSize==0 );
		System.out.println("PULLED");
		return contactsFile;
	}
	public static File getPropertiesFile() throws IOException, InterruptedException{
		commands.clear();
		long firstSize=0;
		long secondSize=0;;
		ProcessBuilder pb = new ProcessBuilder(adbPath, "pull",  "/storage/sdcard0/properties.xml", "M:/SyncPipe");
		do{
			firstSize=propertiesFile.length();
			Process p = pb.start();
			p.waitFor();
			Thread.sleep(1000);
			System.out.println(secondSize);
			Thread.sleep(100);
			secondSize=propertiesFile.length();
			//if file size changed -> app is still creating the file
		}while(firstSize != secondSize);
		return propertiesFile;
	}
	public static File getFilesystemFile() throws IOException, InterruptedException{
		commands.clear();
		long firstSize=0;
		long secondSize=0;;
		ProcessBuilder pb = new ProcessBuilder(adbPath, "pull",  "/storage/sdcard0/filesystem.xml", "M:/SyncPipe");
		do{
			firstSize=filesystemFile.length();
			Process p = pb.start();
			p.waitFor();
			System.out.println(secondSize);
			Thread.sleep(100);
			secondSize=filesystemFile.length();
			//if file size changed -> app is still creating the file
		}while(firstSize != secondSize);
		return filesystemFile;
	}
}
