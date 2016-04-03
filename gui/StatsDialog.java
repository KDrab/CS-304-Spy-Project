package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import database.Database;

public class StatsDialog extends JDialog {
	
	private Database database;
	private JTextField charText;
	private JLabel charLb;
	private JButton findButton;

	public StatsDialog(JFrame parent, Database database){
		super(parent, "Player Stats", true);
		   
		   this.database = database;
	       JPanel panel = new JPanel();
	       JPanel checkBoxPanel = new JPanel();
	       
	       	charLb = new JLabel("Character Name: ");
	        panel.add(charLb);
	        
	        charText = new JTextField(20);
	        panel.add(charText);
	        panel.setBorder(new LineBorder(Color.GRAY));

	        JCheckBox idCheck = new JCheckBox("id");
	        checkBoxPanel.add(idCheck);
	        JCheckBox levelCheck = new JCheckBox("lvl");
	        checkBoxPanel.add(levelCheck);
	        JCheckBox cashCheck = new JCheckBox("cash");
	        checkBoxPanel.add(cashCheck);
	        JCheckBox teamCheck = new JCheckBox("team");
	        checkBoxPanel.add(teamCheck);

	        panel.add(checkBoxPanel);
	        
	        findButton = new JButton("Find Character");
	        
	        findButton.addActionListener(new ActionListener() {
	            
	            public void actionPerformed(ActionEvent e) {
	            	
	            	ArrayList<String> Stats = database.getStats(getName(), idCheck.isSelected(), levelCheck.isSelected(), cashCheck.isSelected(), teamCheck.isSelected());
	        		JOptionPane.showMessageDialog(parent, Stats, "Found Stats!", JOptionPane.INFORMATION_MESSAGE);
	            	
	            }
	        });
	        
	        panel.add(findButton);


	        getContentPane().add(panel, BorderLayout.CENTER);
	        
	        pack();
	        setResizable(false);
	        setLocationRelativeTo(parent);
	       
	       
	}
	
	public String getName(){
		return charText.getText().trim();
	}

}
