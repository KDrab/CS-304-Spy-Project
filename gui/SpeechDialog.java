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

public class SpeechDialog extends JDialog {
	
	public Database database; 
	public JFrame frame;
	public JPanel panel;
	public GridBagConstraints cs;
	public JTable playerList;
	public TableView playerListModel;
	public int appWidth = 520;
	public int appHeight = 480;
	public boolean isAdmin = false;
	
	public SpeechDialog(JFrame parent, Database database, int charID) {
		   super(parent, "Start a Campaign", true);
		    
	       frame = new JFrame("Start a New Marketing Campaign");
	       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	       frame.setSize(appWidth, appHeight);
	       frame.setLayout(new FlowLayout());
	       frame.setVisible(true);
	       
	       panel = new JPanel(new GridBagLayout());
	       cs = new GridBagConstraints();
	       frame.add(panel);
	        
	       this.displaySpeechList(charID, 0, 1);
	}
	
	public void displaySpeechList(int charID, int x, int y) {
		System.out.println("In displaySpeechList, pre-query");
		
    	ArrayList<String> stats = database.getCharacterStats(charID);
    	
    	int cash = Integer.parseInt(stats.get(3).toString().trim());
    	
    	// more/less expensive campaign options
    	String[] type1 = {"Type 1", "120"};
    	String[] type2 = {"Type 2", "60"};
    	String[] type3 = {"Type 3", "30"};
    	String[] type4 = {"Type 4", "10"};
    	
    	TableView speechListModel = new TableView(new String[]{"Speech", "Cost"}, 0);
    	
    	JTable speechList = new JTable(speechListModel);
    	
    	speechListModel.addRow(type1);
    	speechListModel.addRow(type2);
    	speechListModel.addRow(type3);
    	speechListModel.addRow(type4);
    	    	
    	cs.gridx = x;
    	cs.gridy = y;
    	JScrollPane pane = new JScrollPane(speechList, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
    													JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    	pane.setPreferredSize(new Dimension(appWidth/2, 84));
    	panel.add(pane, cs);
    	frame.setVisible(true);
    	
    	MouseListener tableMouseListener = new MouseAdapter() {
    		public void mouseClicked(MouseEvent e) {
    			int row = speechList.getSelectedRow();
    			int col = 1;
    			// selected = charID to assassinate
    			int selected = Integer.parseInt(speechList.getModel().getValueAt(row, col).toString().trim());
    			if (selected <= cash) {
    				displaySpeechButton(charID, row, 0, 10);
    			} else {
    				JOptionPane.showMessageDialog(SpeechDialog.this, "Not Enough Funds!", "Cancel", JOptionPane.ERROR_MESSAGE);
    			}
    			frame.repaint();
    		}
    	};
    	
    	speechList.addMouseListener(tableMouseListener);
    }
	
	public void displaySpeechButton(int charID, int cost, int x, int y){
    	JButton campaignButton = new JButton("Give Speech for " + cost);
    	campaignButton.setLocation(200,150);
    	campaignButton.addActionListener(new ActionListener(){
    	public void actionPerformed(ActionEvent e) {
    		panel.removeAll();
    		database.giveSpeech(charID, cost);
    		database.logAction(charID, 2);
    	}});
    	cs.gridx = x;
    	cs.gridy = y;
    	panel.add(campaignButton, cs); 
    	frame.setVisible(true);
    	
    }
}