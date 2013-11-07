package aut.htlinn.ortner.spe;

import java.io.IOException;
import java.util.ArrayList;

public class XMLFileListener implements Runnable{

	@Override
	public void run() {
		ArrayList<String> commands = new ArrayList<String>();
		ProcessBuilder pb = new ProcessBuilder (commands);
		Process p;
		
		try {
			// start app
			System.out.println("Starting App");
			commands.add ("M:/Dropbox/Diplomarbeit_Dejakum_Ortner/Code/Code_Desktop/ADB/adb.exe"); // command name
			commands.add("shell");
			commands.add ("am start -a android.intent.action.START -n com.testapplication/.activity.StartActivity");
			pb = new ProcessBuilder (commands);
			p = pb.start();
			p.waitFor();
			System.out.println("App started");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
