package aut.htlinn.ortner.spe.gui;

import java.io.File;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class FileTable {

	private JTable table = new JTable();
	
	//Pfad aus dem JTree �bergeben und in XML Datei suchen, liste (String) �bergeben
	public FileTable(String[] files){
		//symbol, name, dir or file, size, date

		Object[][] data = new Object[files.length][4];
		String[] columnNames = {"Name", "Art", "Groesse","Datum"};
		for(int i=0; i<files.length; i++){
			
		}
		DefaultTableModel model = new DefaultTableModel(data, columnNames);
        table = new JTable(model) {

            private static final long serialVersionUID = 1L;

            /*@Override
            public Class getColumnClass(int column) {
            return getValueAt(0, column).getClass();
            }*/
            @Override
            public Class getColumnClass(int column) {
                switch (column) {
                    case 0:
                        return String.class;
                    case 1:
                        return String.class;
                    case 2:
                        return Boolean.class;
                    default:
                        return String.class;
                }
            }
//            @Override
//            public boolean isCellEditable(int rowIndex, int colIndex) {
//                return (colIndex == CHECK_COL);
//            }
        };
	}
	public void update(){
		
	}
}