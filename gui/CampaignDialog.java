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
import java.lang.reflect.Array;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import database.Database;

public class CampaignDialog extends JDialog {
	
	public Database database; 
	public JFrame frame;
	public JPanel panel;
	public GridBagConstraints cs;
	public JTable playerList;
	public TableView playerListModel;
	public int appWidth = 520;
	public int appHeight = 480;
	public boolean isAdmin = false;
	
	public CampaignDialog(JFrame parent, Database database, String charID) {
		   super(parent, "Start a Campaign", true);
		    
	       frame = new JFrame("Start a New Marketing Campaign");
	       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	       frame.setSize(appWidth, appHeight);
	       frame.setLayout(new FlowLayout());
	       frame.setVisible(true);
	       
	       panel = new JPanel(new GridBagLayout());
	       cs = new GridBagConstraints();
	       frame.add(panel);
	        
	       this.displayCampaignList(charID, 0, 1);
	}
	
	public void displayCampaignList(String charID, int x, int y) {
		System.out.println("In displayCampaignList, pre-query");
		
    	ArrayList<String> stats = database.getCharacterStats(charID);
    	
    	int cash = Integer.parseInt(stats.get(3).toString().trim());
    	
    	// more/less expensive campaign options
    	String[] type1 = {"Type 1", "200"};
    	String[] type2 = {"Type 2", "100"};
    	String[] type3 = {"Type 3", "50"};
    	String[] type4 = {"Type 4", "25"};
    	
    	TableView campaignListModel = new TableView(new String[]{"Campaign", "Cost"}, 0);
    	
    	JTable campaignList = new JTable(campaignListModel);
    	
    	campaignListModel.addRow(type1);
    	campaignListModel.addRow(type2);
    	campaignListModel.addRow(type3);
    	campaignListModel.addRow(type4);
    	    	
    	cs.gridx = x;
    	cs.gridy = y;
    	JScrollPane pane = new JScrollPane(campaignList, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
    													JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    	pane.setPreferredSize(new Dimension(appWidth/2, 84));
    	panel.add(pane, cs);
    	frame.setVisible(true);
    	
    	MouseListener tableMouseListener = new MouseAdapter() {
    		public void mouseClicked(MouseEvent e) {
    			int row = campaignList.getSelectedRow();
    			// selected = charID to assassinate
    			int selectedCost = Integer.parseInt(campaignList.getModel().getValueAt(row, 1).toString().trim());
    			String selected = campaignList.getModel().getValueAt(row, 0).toString().trim();
    			if (selectedCost <= cash) {
    				displayCampaignButton(charID, selected, 0, 10);
    			} else {
    				JOptionPane.showMessageDialog(CampaignDialog.this, "Not Enough Funds!", "Cancel", JOptionPane.ERROR_MESSAGE);
    			}
    			frame.repaint();
    		}
    	};
    	
    	campaignList.addMouseListener(tableMouseListener);
    }
	
	public void displayCampaignButton(String charID, String type, int x, int y){
    	JButton campaignButton = new JButton("Pay for Type " + type);
    	campaignButton.setLocation(200,150);
    	campaignButton.addActionListener(new ActionListener(){
    	public void actionPerformed(ActionEvent e) {
    		panel.removeAll();
    		database.createCampaign(charID, type);
    		database.logAction(charID, 3);
    	}});
    	cs.gridx = x;
    	cs.gridy = y;
    	panel.add(campaignButton, cs); 
    	frame.setVisible(true);
    	
    }
}