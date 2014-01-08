package aut.htlinn.ortner.spe;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class AndroidFilesHandler extends Thread{

	private static ArrayList<String> commands = new ArrayList<String>();
	private static ProcessBuilder pb;
	private static Process p;
	ArrayList<String> dirs = new ArrayList<String>();

	public static File getContactsFile(){
		commands.clear();
		File contactsFile = new File("C:/contacts.xml");
		System.out.println("getting xml file");
		long firstSize=0;
		long secondSize=0;;
		ProcessBuilder pb = new ProcessBuilder("D:/Dropbox/Diplomarbeit_Dejakum_Ortner/Code/Code_Desktop/ADB/adb.exe", "pull",  "/storage/sdcard0/contacts.xml", "C:/");
		do{
			firstSize=contactsFile.length();
			try {
				p = pb.start();
				p.waitFor();
				System.out.println(secondSize);
				Thread.sleep(100);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			secondSize=contactsFile.length();
			//if file size changed -> app is still creating the file
		}while(firstSize != secondSize);
		System.out.println("got xml file");
		return contactsFile;
	}
}
