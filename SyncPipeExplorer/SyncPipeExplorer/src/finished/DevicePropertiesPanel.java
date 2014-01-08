package finished;

import java.awt.Color;
import java.awt.Dimension;
import java.io.IOException;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public class DevicePropertiesPanel extends JPanel {

	private Color lightBlue = new Color(0,210,250);
	/**
	 * Create the panel.
	 */
	public DevicePropertiesPanel() {
		try {
			
			JLabel label = new JLabel("<html><p align=center>Android Version:<br>"+DeviceManager.getAndroidVersion()+"</html>", SwingConstants.CENTER);
			label.setBorder(new LineBorder(lightBlue));
			label.setForeground(lightBlue);
//			label.setHorizontalAlignment(SwingConstants.CENTER );
			label.setPreferredSize(new Dimension(100, 70));
			this.add(label);
			
			JLabel label1 = new JLabel("<html><p align=center>Battery charge:<br>"+DeviceManager.getBatteryPercentage()+"%</html>", SwingConstants.CENTER);
			label1.setBorder(new LineBorder(lightBlue));
			label1.setForeground(lightBlue);
			label1.setHorizontalAlignment(SwingConstants.CENTER );
			label1.setPreferredSize(new Dimension(100, 70));
			this.add(label1);
			
			JLabel label2 = new JLabel("<html><p align=center>Battery charging:<br>"+DeviceManager.getBatteryStatus()+"</html>", SwingConstants.CENTER);
			label2.setBorder(new LineBorder(lightBlue));
			label2.setForeground(lightBlue);
			label2.setHorizontalAlignment(SwingConstants.CENTER );
			label2.setPreferredSize(new Dimension(100, 70));
			this.add(label2);
			
			JLabel label3 = new JLabel("<html><p align=center>Battery Tem.:<br>"+DeviceManager.getBatteryTemperature()+"</html>", SwingConstants.CENTER);
			label3.setBorder(new LineBorder(lightBlue));
			label3.setForeground(lightBlue);
			label3.setHorizontalAlignment(SwingConstants.CENTER );
			label3.setPreferredSize(new Dimension(100, 70));
			this.add(label3);
			
			JLabel label4 = new JLabel("<html><p align=center>Total RAM:<br>"+DeviceManager.getTotalRAM()+"</html>", SwingConstants.CENTER);
			label4.setBorder(new LineBorder(lightBlue));
			label4.setForeground(lightBlue);
			label4.setHorizontalAlignment(SwingConstants.CENTER );
			label4.setPreferredSize(new Dimension(100, 70));
			this.add(label4);
			
			JLabel label5 = new JLabel("<html><p align=center>Free RAM:<br>"+DeviceManager.getFreeRAM()+"</html>", SwingConstants.CENTER);
			label5.setBorder(new LineBorder(lightBlue));
			label5.setForeground(lightBlue);
			label5.setHorizontalAlignment(SwingConstants.CENTER );
			label5.setPreferredSize(new Dimension(100, 70));
			this.add(label5);
			
			JLabel label6 = new JLabel("<html><p align=center>Mobile Connection:<br>"+DeviceManager.getMobileConnection()+"</html>", SwingConstants.CENTER);
			label6.setBorder(new LineBorder(lightBlue));
			label6.setForeground(lightBlue);
			label6.setHorizontalAlignment(SwingConstants.CENTER );
			label6.setPreferredSize(new Dimension(100, 70));
			this.add(label6);
			
			JLabel label7 = new JLabel("<html><p align=center>Wifi connected:<br>"+DeviceManager.getWifiStatus()+"</html>", SwingConstants.CENTER);
			label7.setBorder(new LineBorder(lightBlue));
			label7.setForeground(lightBlue);
			label7.setHorizontalAlignment(SwingConstants.CENTER );
			label7.setPreferredSize(new Dimension(100, 70));
			this.add(label7);
			
		} catch (SAXException | IOException | ParserConfigurationException e) {
			e.printStackTrace();
		}
	}
}
