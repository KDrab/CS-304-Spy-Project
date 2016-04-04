package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import database.Database;

public class DeleteDialog extends JDialog {
	private Database database;
	private JLabel boxLabel;
	private JTextField playerText;
	private JButton deleteButton;

	public DeleteDialog(JFrame parent, Database database) {
		super(parent, "Delete Player", true);
		   
		   this.database = database;
	       JPanel panel = new JPanel();
	       JPanel checkBoxPanel = new JPanel();
	       
	       	boxLabel = new JLabel("Player Email");
	        panel.add(boxLabel);
	        
	        playerText = new JTextField(20);
	        panel.add(playerText);
	        panel.setBorder(new LineBorder(Color.GRAY));
	
		    panel.add(checkBoxPanel);
		    
		    deleteButton = new JButton("Delete Player");
		    
		    deleteButton.addActionListener(new ActionListener() {
		        
		        public void actionPerformed(ActionEvent e) {
		        	database.deletePlayer(getEmail());
		        }
		    });
		    
		    panel.add(deleteButton);
		
		
		    getContentPane().add(panel, BorderLayout.CENTER);
		    
		    pack();
		    setResizable(false);
		    setLocationRelativeTo(parent);
		    
	}
	
    public String getEmail() {
        return playerText.getText().trim();
    }

}
