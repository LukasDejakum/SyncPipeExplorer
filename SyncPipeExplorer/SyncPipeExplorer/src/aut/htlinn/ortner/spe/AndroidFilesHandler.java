package aut.htlinn.ortner.spe;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class AndroidFilesHandler {

	public void getAndroidFiles(){
		ArrayList<String> commands = new ArrayList<String>();
		ProcessBuilder pb;
		Process p;
		ArrayList<String> dirs = new ArrayList<String>();
		try {

			System.out.println("Requesting Files...");
			pb = new ProcessBuilder("cmd.exe");
			pb.redirectErrorStream();
			//			pb.redirectOutput(new File("files/output.txt"));
			p = pb.start();
			BufferedWriter p_stdin = new BufferedWriter(new OutputStreamWriter(p.getOutputStream()));
			Scanner s = new Scanner(p.getInputStream());

			BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line;

			p_stdin.write("cd M:/Dropbox/Diplomarbeit_Dejakum_Ortner/Code/Code_Desktop/ADB/adb.exe\n");
			p_stdin.write("adb shell\n");
			p_stdin.write("ls -d */ > /sdcard/temp.txt\n");
			p_stdin.write("exit\n");
			p_stdin.flush();
			Thread.sleep(1000);
//			p_stdin.write("M:/Dropbox/Diplomarbeit_Dejakum_Ortner/Code/Code_Desktop/ADB/adb.exe\n");
			p_stdin.write("adb pull /sdcard/temp.txt M:/ \n");
			p_stdin.flush();

			while ((line = br.readLine()).length()<100000) {
				System.out.println(line);
			}

			System.out.println("after while");
			p_stdin.close();
			s.close();
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		System.out.println("Fertig");
	}
}

// if (out == null) write("cd..")
//else readFiles();
