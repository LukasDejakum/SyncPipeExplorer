package aut.htlinn.ortner.spe;

import java.io.IOException;
import java.util.ArrayList;

public class PortListener implements Runnable{

	@Override
	public void run() {
		ArrayList<String> commands = new ArrayList<String>();
		
		try {
			// wait for device
			commands.add("M:/Dropbox/Diplomarbeit_Dejakum_Ortner/Code/Code_Desktop/ADB/adb.exe");
			commands.add("wait-for-devices");
			ProcessBuilder pb = new ProcessBuilder (commands);
			Process p;
			System.out.println("Connecting...");
			p = pb.start();
			p.waitFor();
			System.out.println("Device connected...");
			commands.clear();
			p.destroy();
			Thread xmlListener = new Thread(new XMLFileListener());
			xmlListener.start();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
