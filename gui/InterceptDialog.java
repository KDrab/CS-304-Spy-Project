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

public class InterceptDialog extends JDialog {
	
	public Database database; 
	public JFrame frame;
	public JPanel panel;
	public GridBagConstraints cs;
	public JTable playerList;
	public TableView playerListModel;
	public int appWidth = 520;
	public int appHeight = 480;
	public boolean isAdmin = false;
	
	public InterceptDialog(JFrame parent, Database database, String charID) {
		   super(parent, "Spy", true);
		    
	       frame = new JFrame("Choose your Target!");
	       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	       frame.setSize(appWidth, appHeight);
	       frame.setLayout(new FlowLayout());
	       frame.setVisible(true);
	       
	       panel = new JPanel(new GridBagLayout());
	       cs = new GridBagConstraints();
	       frame.add(panel);
	       	        
	       this.displayTargetList(charID, 0, 1);
	}
	
	public void displayTargetList(String charID, int x, int y) {
		System.out.println("In displayTargetList, pre-query");
		
    	ArrayList<String> targets = database.getEnemiesList(charID);
    	
    	TableView targetsListModel = new TableView(new String[]{"ID", "Name", "Level"}, 0);
    	
    	JTable targetList = new JTable(targetsListModel);
    	
    	System.out.println("displayEnemyList query done, table created");
    	
    	for (int i = 0; i < targets.size(); i = i + 3) {
    		String[] toAdd = new String[3];
    		toAdd[0] = targets.get(i);
    		toAdd[1] = targets.get(i+1);
    		toAdd[2] = targets.get(i+2);
    		targetsListModel.addRow(toAdd);
    	}
    	
    	cs.gridx = x;
    	cs.gridy = y;
    	JScrollPane pane = new JScrollPane(targetList, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
    													JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    	pane.setPreferredSize(new Dimension(appWidth/2, Math.min(20 + targets.size() * 16 / 3, 132)));
    	panel.add(pane, cs);
    	frame.setVisible(true);
    	
    	MouseListener tableMouseListener = new MouseAdapter() {
    		public void mouseClicked(MouseEvent e) {
    			int row = targetList.getSelectedRow();
    			int col = 0;
    			// selected = charID to spy on
    			String selected = targetList.getModel().getValueAt(row, col).toString().trim();
    			displaySpyButton(selected, 0, 10, charID);
    			frame.repaint();
    		}
    	};
    	
    	targetList.addMouseListener(tableMouseListener);
    }
	
	public void displaySpyButton(String selected, int x, int y, String charID){
    	JButton spyButton = new JButton("Intercept!");
    	spyButton.setLocation(200,150);
    	spyButton.addActionListener(new ActionListener(){
    	public void actionPerformed(ActionEvent e) {
    		panel.removeAll();
    		database.getActions(selected);
    		database.logAction(charID, 1);
    	}});
    	cs.gridx = x;
    	cs.gridy = y;
    	panel.add(spyButton, cs); 
    	frame.setVisible(true);
    	
    }
}
