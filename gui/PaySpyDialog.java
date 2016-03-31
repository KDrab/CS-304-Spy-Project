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

public class PaySpyDialog extends JDialog {
	
	public Database database; 
	public JFrame frame;
	public JPanel panel;
	public GridBagConstraints cs;
	public JTable playerList;
	public TableView playerListModel;
	public int appWidth = 520;
	public int appHeight = 480;
	public boolean isAdmin = false;
	
	public PaySpyDialog(JFrame parent, Database database, int charID) {
		   super(parent, "Hire a Spy", true);
		    
	       frame = new JFrame("Pay a Spy");
	       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	       frame.setSize(appWidth, appHeight);
	       frame.setLayout(new FlowLayout());
	       frame.setVisible(true);
	       
	       panel = new JPanel(new GridBagLayout());
	       cs = new GridBagConstraints();
	       frame.add(panel);
	        
	       this.displaySpyList(charID, 0, 1);
	}
	
	public void displaySpyList(int charID, int x, int y) {
		System.out.println("In displaySpyList, pre-query");
		
    	ArrayList<String> spies = database.getSpyList();
    	
    	TableView spyListModel = new TableView(new String[]{"ID", "Name", "Success %"}, 0);
    	
    	JTable spyList = new JTable(spyListModel);
    	
    	System.out.println("displaySpyList query done, table created");
    	
    	for (int i = 0; i < spies.size(); i = i + 4) {
    		String[] toAdd = new String[3];
    		toAdd[0] = spies.get(i);
    		toAdd[1] = spies.get(i+1);
    		toAdd[2] = spies.get(i+2);
    		spyListModel.addRow(toAdd);
    	}
    	
    	cs.gridx = x;
    	cs.gridy = y;
    	JScrollPane pane = new JScrollPane(spyList, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
    													JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    	pane.setPreferredSize(new Dimension(appWidth/2, Math.min(20 + spies.size() * 16 / 3, 132)));
    	panel.add(pane, cs);
    	frame.setVisible(true);
    	
    	MouseListener tableMouseListener = new MouseAdapter() {
    		public void mouseClicked(MouseEvent e) {
    			int row = spyList.getSelectedRow();
    			int col = 0;
    			// selected = charID to assassinate
    			int selected = Integer.parseInt(spyList.getModel().getValueAt(row, col).toString().trim());
    			displayPaySpyButton(selected, charID, 0, 10);
    			frame.repaint();
    		}
    	};
    	
    	spyList.addMouseListener(tableMouseListener);
    }
	
	public void displayPaySpyButton(int to, int from, int x, int y){
    	JButton hireButton = new JButton("Pay.");
    	hireButton.setLocation(200,150);
    	hireButton.addActionListener(new ActionListener(){
    	public void actionPerformed(ActionEvent e) {
    		panel.removeAll();
    		database.transferMoney(to, from);
    		database.logAction(from, 0);
    	}});
    	cs.gridx = x;
    	cs.gridy = y;
    	panel.add(hireButton, cs); 
    	frame.setVisible(true);
    	
    }
}