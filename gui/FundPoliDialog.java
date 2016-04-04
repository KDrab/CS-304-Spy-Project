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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import database.Database;

public class FundPoliDialog extends JDialog {
	
	public Database database; 
	private JLabel fromLabel, toLabel, amountLabel;
	private JTextField fromText, toText, amountText;
	private GridBagConstraints cs;
	private JButton sendButton;
	
	
	public FundPoliDialog(JFrame parent, Database database) {
		   super(parent, "Fund a Politician", true);
		   
		   this.database = database;
	       JPanel panel = new JPanel(new GridBagLayout());
	       cs = new GridBagConstraints();
	       
	       	fromLabel = new JLabel("From");
	       	cs.gridx = 0;
	       	cs.gridy = 0;
	        panel.add(fromLabel, cs);
	        
	        toLabel = new JLabel("To");
	       	cs.gridx = 0;
	       	cs.gridy = 1;
	        panel.add(toLabel, cs);
	        
	        amountLabel = new JLabel("Amount");
	       	cs.gridx = 0;
	       	cs.gridy = 2;
	        panel.add(amountLabel, cs);
	        
	        fromText = new JTextField(20);
	       	cs.gridx = 1;
	       	cs.gridy = 0;
	        panel.add(fromText, cs);
	        panel.setBorder(new LineBorder(Color.GRAY));
	        
	        toText = new JTextField(20);
	       	cs.gridx = 1;
	       	cs.gridy = 1;
	        panel.add(toText, cs);
	        panel.setBorder(new LineBorder(Color.GRAY));
	        
	        amountText = new JTextField(20);
	       	cs.gridx = 1;
	       	cs.gridy = 2;
	        panel.add(amountText, cs);
	        panel.setBorder(new LineBorder(Color.GRAY));

		    
		    sendButton = new JButton("Send Cash");
		    
		    sendButton.addActionListener(new ActionListener() {
		        
		        public void actionPerformed(ActionEvent e) {
		        	database.transferMoney(getTo(), getFrom(), getAmount());
		        }
		    });
		    
		    panel.add(sendButton);
		
		
		    getContentPane().add(panel, BorderLayout.CENTER);
		    
		    pack();
		    setResizable(false);
		    setLocationRelativeTo(parent);
	}
	
	public String getTo(){
		return toText.getText().trim();
	}
	public String getFrom(){
		return fromText.getText().trim();
	}
	public int getAmount(){
		return Integer.parseInt(amountText.getText());
	}
	
	
}