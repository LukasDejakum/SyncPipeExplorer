package aut.htlinn.ortner.spe.gui;

import java.io.File;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class FileTable extends JTable{

	//Pfad aus dem JTree übergeben und in XML Datei suchen, liste (String) übergeben
	public FileTable(String path){
		//symbol, name, dir or file, size, date
		
		File[] files = new File(path).listFiles();
		
		Object[][] data = new Object[files.length][4];
		String[] columnNames = {"Name", "Art", "Groesse","Datum"};
		for(int i=0; i<files.length; i++){
			
		}
		DefaultTableModel model = new DefaultTableModel(data, columnNames);
        this.setModel(model);
	}

	public void update(){
		
	}
}
