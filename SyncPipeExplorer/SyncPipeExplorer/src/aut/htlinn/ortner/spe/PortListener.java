package aut.htlinn.ortner.spe;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;

import finished.DeviceManager;
import finished.SyncPipeExplorerGUI;

public class PortListener implements Runnable{

	private JPanel statusPanel;

	@Override
	public void run() {
		ArrayList<String> commands = new ArrayList<String>();
		
		try {
			commands.add("M:/Dropbox/Diplomarbeit_Dejakum_Ortner/Code/Code_Desktop/ADB/adb.exe");
			commands.add("wait-for-devices");
			ProcessBuilder pb = new ProcessBuilder (commands);
			Process p;
			SyncPipeExplorerGUI.setText("Waiting for Device...");
			p = pb.start();
			p.waitFor();
			commands.clear();
			p.destroy();
			SyncPipeExplorerGUI.setText("Device connected...");
			Thread.sleep(500);
			SyncPipeExplorerGUI.setText("Starting App...");
			DeviceManager.startApp();
			SyncPipeExplorerGUI.setText("App Started...");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
