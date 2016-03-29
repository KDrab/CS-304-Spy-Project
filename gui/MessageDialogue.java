package gui;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import database.Database;

public class MessageDialogue extends JDialog {
	
	public Database database; 
	public JFrame frame;
	private JTextField receiver, message;
    private JLabel receiverLabel, messageLabel;
    private JButton sendButton, cancelButton;
    
	public GridBagConstraints cs;
	public int appWidth = 520;
	public int appHeight = 300;
	public String type;
	

	public MessageDialogue(JFrame parent, Database db, String charName, String name) {
		super(parent, "Login", true);
        
        this.database = db;
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints cs = new GridBagConstraints();
        
        cs.fill = GridBagConstraints.HORIZONTAL;
        
        receiverLabel = new JLabel("Username: ");
        cs.gridx = 0;
        cs.gridy = 0;
        cs.gridwidth = 1;
        panel.add(receiverLabel, cs);
        
        receiver = new JTextField(20);
        cs.gridx = 1;
        cs.gridy = 0;
        cs.gridwidth = 2;
        panel.add(receiver, cs);
        
        messageLabel = new JLabel("Password: ");
        cs.gridx = 0;
        cs.gridy = 1;
        cs.gridwidth = 1;
        panel.add(messageLabel, cs);
        
        message = new JTextField(40);
        cs.gridx = 1;
        cs.gridy = 1;
        cs.gridwidth = 2;
        panel.add(message, cs);
        panel.setBorder(new LineBorder(Color.GRAY));
        
        
        
        
        sendButton = new JButton("Send");
        sendButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		sendMessage();
                dispose();
        	}
        });
        
        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        
        JPanel bp = new JPanel();
        bp.add(sendButton);
        bp.add(cancelButton);
        
        frame.pack();
        frame.setVisible(true);
	}
	
	private void sendMessage(){
		// write message text and choose character to send to
		
		JTextField textField;
	    JTextArea textArea;
	    String newline = "\n";
		
		textField = new JTextField(20);
 
        textArea = new JTextArea(5, 20);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
 
        //Add Components to this panel.
        GridBagConstraints c = new GridBagConstraints();
        c.gridwidth = GridBagConstraints.REMAINDER;
 
        c.fill = GridBagConstraints.HORIZONTAL;
        add(textField, c);
 
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.0;
        c.weighty = 1.0;
        add(scrollPane, c);
		
		
	}
}
