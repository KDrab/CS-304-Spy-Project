package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import database.Database;

public class FundPoliDialog extends JDialog {
	
	public Database database; 
	public JFrame frame;
	public JPanel panel;
	public GridBagConstraints cs;
	public JTable playerList;
	public TableView playerListModel;
	public int appWidth = 520;
	public int appHeight = 480;
	public boolean isAdmin = false;
	
	public FundPoliDialog(JFrame parent, Database database, String charID) {
		   super(parent, "Fund a Politician", true);
		    
	       frame = new JFrame("Fund a Politician");
	       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	       frame.setSize(appWidth, appHeight);
	       frame.setLayout(new FlowLayout());
	       frame.setVisible(true);
	       
	       panel = new JPanel(new GridBagLayout());
	       cs = new GridBagConstraints();
	       frame.add(panel);
	        
	       this.displayPoliList(charID, 0, 1);
	}
	
	public void displayPoliList(String charID, int x, int y) {
		System.out.println("In displayPoliList, pre-query");
		
    	ArrayList<String> polis = database.getPoliList();
    	
    	TableView poliListModel = new TableView(new String[]{"ID", "Name", "Popularity"}, 0);
    	
    	JTable poliList = new JTable(poliListModel);
    	
    	System.out.println("displayPoliList query done, table created");
    	
    	for (int i = 0; i < polis.size(); i = i + 4) {
    		String[] toAdd = new String[3];
    		toAdd[0] = polis.get(i);
    		toAdd[1] = polis.get(i+1);
    		toAdd[2] = polis.get(i+2);
    		poliListModel.addRow(toAdd);
    	}
    	
    	// TODO get user input for amount to donate
    	
    	int amt = 100;
    	
    	cs.gridx = x;
    	cs.gridy = y;
    	JScrollPane pane = new JScrollPane(poliList, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
    													JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    	pane.setPreferredSize(new Dimension(appWidth/2, Math.min(20 + polis.size() * 16 / 3, 132)));
    	panel.add(pane, cs);
    	frame.setVisible(true);
    	
    	MouseListener tableMouseListener = new MouseAdapter() {
    		public void mouseClicked(MouseEvent e) {
    			int row = poliList.getSelectedRow();
    			int col = 0;
    			// selected = charID to assassinate
    			String selected  = poliList.getModel().getValueAt(row, col).toString().trim();
    			displayFundButton(selected, charID, amt, 0, 10);
    			frame.repaint();
    		}
    	};
    	
    	poliList.addMouseListener(tableMouseListener);
    }
	
	public void displayFundButton(String to, String from, int amt, int x, int y){
    	JButton fundButton = new JButton("Donate.");
    	fundButton.setLocation(200,150);
    	fundButton.addActionListener(new ActionListener(){
    	public void actionPerformed(ActionEvent e) {
    		panel.removeAll();
    		database.transferMoney(to, from, amt);
    		database.logAction(from, 4);
    	}});
    	cs.gridx = x;
    	cs.gridy = y;
    	panel.add(fundButton, cs); 
    	frame.setVisible(true);
    	
    }
}