package aut.htlinn.ortner.spe;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class ExecCommandsTest {

	public void getAndroidFiles(){
		ArrayList<String> commands = new ArrayList<String>();
		ProcessBuilder pb;
		Process p;
		ArrayList<String> dirs = new ArrayList<String>();
		try {
			// start app
			System.out.println("Requesting Files...");
			commands.add ("D:/Dropbox/Diplomarbeit_Dejakum_Ortner/Code/Code_Desktop/ADB/adb.exe"); // command name
//			commands.add("adb pull /sdcard");
//			commands.add ("am start -a android.intent.action.START -n com.testapplication/.activity.StartActivity");
			commands.add("shell");
			commands.add("ls");
			
			pb = new ProcessBuilder (commands);
			p = pb.start();
			p.waitFor();
			
			
			   InputStream is = p.getInputStream();
		       InputStreamReader isr = new InputStreamReader(is);
		       BufferedReader br = new BufferedReader(isr);
		       String line;

		       System.out.println("output: ");
				while ((line = br.readLine()) != null) {
					System.out.println(line);
				}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Fertig");
	}
}
