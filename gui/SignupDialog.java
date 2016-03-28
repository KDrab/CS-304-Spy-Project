package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import database.Database;

public class SignupDialog extends JDialog {
	
	private JTextField email;
	private JTextField uname;
	private JPasswordField pass;
	private JLabel emailLb, unameLb, passLb;
	private JButton btnSignup, btnCancel;
    private boolean succeeded;
    private Database database;

	public SignupDialog(JFrame parent, Database database) {
		   super(parent, "Sign Up", true);
		   
		   this.database = database;
	       JPanel panel = new JPanel(new GridBagLayout());
	       GridBagConstraints cs = new GridBagConstraints();
	       
	        cs.fill = GridBagConstraints.HORIZONTAL;
	        
	        emailLb = new JLabel("Email: ");
	        cs.gridx = 0;
	        cs.gridy = 0;
	        cs.gridwidth = 1;
	        panel.add(emailLb, cs);
	        
	        email = new JTextField(20);
	        cs.gridx = 1;
	        cs.gridy = 0;
	        cs.gridwidth = 2;
	        panel.add(email, cs);
	        panel.setBorder(new LineBorder(Color.GRAY));
	        
	        unameLb = new JLabel("Username: ");
	        cs.gridx = 0;
	        cs.gridy = 1;
	        cs.gridwidth = 1;
	        panel.add(unameLb, cs);
	        
	        uname = new JTextField(20);
	        cs.gridx = 1;
	        cs.gridy = 1;
	        cs.gridwidth = 2;
	        panel.add(uname, cs);
	        
	        passLb = new JLabel("Password: ");
	        cs.gridx = 0;
	        cs.gridy = 2;
	        cs.gridwidth = 1;
	        panel.add(passLb, cs);
	        
	        pass = new JPasswordField(20);
	        cs.gridx = 1;
	        cs.gridy = 2;
	        cs.gridwidth = 2;
	        panel.add(pass, cs);
	        panel.setBorder(new LineBorder(Color.GRAY));
	        
	        btnSignup = new JButton("Sign Up");
	        
	        btnSignup.addActionListener(new ActionListener() {
	            
	            public void actionPerformed(ActionEvent e) {
	                if (authenticate(getEmail(), getUsername(), getPassword())) {
	                    JOptionPane.showMessageDialog(SignupDialog.this, "Welcome " + getUsername() + "!", "Login", JOptionPane.INFORMATION_MESSAGE);
	                    succeeded = true;
	                    dispose();
	                } else {
	                    JOptionPane.showMessageDialog(SignupDialog.this, "Username already exists", "Login", JOptionPane.ERROR_MESSAGE);
	                    // reset username and password
	                    uname.setText("");
	                    pass.setText("");
	                    succeeded = false;
	                    
	                }
	            }
	        });
	        
	        btnCancel = new JButton("Cancel");
	        btnCancel.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	                dispose();
	            }
	        });
	        
	        JPanel bp = new JPanel();
	        bp.add(btnSignup);
	        bp.add(btnCancel);
	        
	        getContentPane().add(panel, BorderLayout.CENTER);
	        getContentPane().add(bp, BorderLayout.PAGE_END);
	        
	        pack();
	        setResizable(false);
	        setLocationRelativeTo(parent);
	    }
	    
	    public  boolean authenticate(String email, String username, String password) {
	        // query DB for user info
	    	return this.database.authenticateSignup(email, username, password);
	    }
	    
	    public String getUsername() {
	        return uname.getText().trim();
	    }
	    
	    public String getPassword() {
	        return new String(pass.getPassword());
	    }
	    
	    public String getEmail(){
	    	return new String(email.getText().trim());
	    }
	    
	    public boolean isSucceeded() {
	        return succeeded;
	    }
		 
	}
