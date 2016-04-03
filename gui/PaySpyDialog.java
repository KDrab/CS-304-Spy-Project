package gui;

import java.awt.BorderLayout;
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
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import database.Database;

public class PaySpyDialog extends JDialog {
	
	public Database database; 
	public JPanel panel;
	public GridBagConstraints cs;
	public JTable playerList;
	public TableView playerListModel;
	public int appWidth = 520;
	public int appHeight = 480;
	public boolean isAdmin = false;
	public JLabel amtLbl;
	public JTextField amt;
	
	public PaySpyDialog(JFrame parent, Database db, String charID) {
		   super(parent, "Hire a Spy", true);
		   charID = charID.trim();
		   database = db;
		    
		   panel = new JPanel(new GridBagLayout());
	       cs = new GridBagConstraints();
	        
	       cs.fill = GridBagConstraints.HORIZONTAL;
	       
	       amtLbl = new JLabel("Amount: ");
	       cs.gridx = 0;
	       cs.gridy = 0;
	       cs.gridwidth = 1;
	       panel.add(amtLbl, cs);
	        
	       amt = new JTextField(20);
	       cs.gridx = 1;
	       cs.gridy = 0;
	       cs.gridwidth = 2;
	       panel.add(amt, cs);
	       panel.setBorder(new LineBorder(Color.GRAY));
	       
	       JButton btnPay = new JButton("Pay");
	       
	       int cash = Integer.parseInt(database.getCharacterStats(charID).get(3).toString().trim());
	        
	       btnPay.addActionListener(new ActionListener() {
	            
	           public void actionPerformed(ActionEvent e) {
	               if (getAmt() <= cash) {
	                   JOptionPane.showMessageDialog(PaySpyDialog.this, "Payment successful!", "Done", JOptionPane.INFORMATION_MESSAGE);
	                   dispose();
	               } else {
	            	   JOptionPane.showMessageDialog(PaySpyDialog.this, "Insufficient funds!", "Cancel", JOptionPane.ERROR_MESSAGE);
	                   // reset amt field
	                   amt.setText("");	                    
	               }
	           }
	       });
	       
	       JButton btnCancel = new JButton("Cancel");
	       btnCancel.addActionListener(new ActionListener() {
	           public void actionPerformed(ActionEvent e) {
	               dispose();
	           }
	       });
	       
	       displaySpyList(charID, 0, 1);
	        
	       JPanel bp = new JPanel();
	       bp.add(btnCancel);
	        
	       getContentPane().add(panel, BorderLayout.CENTER);
	       getContentPane().add(bp, BorderLayout.PAGE_END);
	        
	       pack();
	       setResizable(false);
	       setLocationRelativeTo(parent);
	        
	       //this.displaySpyList(charID, 0, 4);
	}
	
	public void displaySpyList(String charID, int x, int y) {
		System.out.println("In displaySpyList, pre-query");
		
    	ArrayList<String> spies = database.getSpyList(); 
    	
    	TableView spyListModel = new TableView(new String[]{"ID", "Name", "Success %"}, 0);
    	
    	JTable spyList = new JTable(spyListModel);
    	
    	System.out.println("displaySpyList query done, table created");
    	
    	for (int i = 0; i < spies.size(); i = spies.size()) {
    		String[] toAdd = new String[3];
    		System.out.println("toAdd created");
    		toAdd[0] = spies.get(i);
    		toAdd[1] = spies.get(i+1);
    		toAdd[2] = spies.get(i+2);
    		spyListModel.addRow(toAdd);
    		System.out.println("row added");
    	}
    	
    	int amt = 0;
    	
    	System.out.println("out of for");
    	
    	// BROKEN HERE FOR SOME REASON????!!
    	
    	cs.gridx = x;
    	cs.gridy = y;
    	
    	System.out.println("grid coord done");
    	
    	JScrollPane pane = new JScrollPane(spyList, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
    													JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    	pane.setPreferredSize(new Dimension(appWidth/2, Math.min(20 + spies.size() * 16 / 3, 132)));
    	panel.add(pane, cs);
    	
    	MouseListener tableMouseListener = new MouseAdapter() {
    		public void mouseClicked(MouseEvent e) {
    			int row = spyList.getSelectedRow();
    			int col = 0;
    			// selected = charID to assassinate
    			String selected = spyList.getModel().getValueAt(row, col).toString().trim();
    			displayPaySpyButton(selected, charID, amt, 0, 10);
    		}
    	};
    	
    	spyList.addMouseListener(tableMouseListener);
    }
	
	public void displayPaySpyButton(String to, String from, int amt, int x, int y){
    	JButton hireButton = new JButton("Pay.");
    	hireButton.setLocation(200,150);
    	hireButton.addActionListener(new ActionListener(){
    	public void actionPerformed(ActionEvent e) {
    		panel.removeAll();
    		database.transferMoney(to, from, amt);
    		database.logAction(from, 0);
    	}});
    	cs.gridx = x;
    	cs.gridy = y;
    	panel.add(hireButton, cs); 
    	
    }
	
	public int getAmt() {
        return Integer.parseInt(amt.getText().trim());
    }
}