package aut.htlinn.ortner.spe;

import java.awt.Dimension;

import javax.swing.Icon;
import javax.swing.JLabel;

public class DataLabel extends JLabel{

	public DataLabel(Icon icon, String text){
		this.setIcon(icon);
		this.setText(text);
		this.setPreferredSize(new Dimension(50,30));
	}
}
