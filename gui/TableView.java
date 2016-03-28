package gui;

import javax.swing.table.*;

public class TableView extends DefaultTableModel {
	
	public TableView(String[] strings, int i) {
		super(strings, i);
	}

	@Override
	public boolean isCellEditable(int row,int cols) {
		return false;
	}
}
