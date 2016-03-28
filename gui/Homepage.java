package gui;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import database.Database;

public class Homepage extends JFrame {
	public Database database; 
	JFrame frame;
	JPanel panel;
	JTable playerList;
	DefaultTableModel playerListModel;

    public Homepage(String uname, Database db) {
        this.database = db;
        
        frame = new JFrame(uname + " - Homepage");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 300);
        frame.setLayout(new FlowLayout());
        frame.setVisible(true);
        
        panel = new JPanel(new GridBagLayout());
        GridBagConstraints cs = new GridBagConstraints();
        frame.add(panel);
        
        this.displayLoginButton();
        this.displaySignupButton();
    }
    
    
    public void displayLoginButton(){
    	JButton loginButton = new JButton("login");
    	loginButton.setLocation(200, 150);
    	panel.add(loginButton);
    	loginButton.addActionListener(new ActionListener()
    	{
    	public void actionPerformed(ActionEvent e) {
    		LoginDialog ld = new LoginDialog(frame, database);
    		ld.setVisible(true);
    		if(ld.isSucceeded()){
    			panel.removeAll();
    			displayLogoutButton();
    			displayPlayerList();
    			frame.repaint();
    		}
    	}});
    	frame.setVisible(true);	
    	
    }
    
    public void displaySignupButton(){
    	JButton signupButton = new JButton("Sign Up");
    	signupButton.setLocation(300, 150);
    	signupButton.addActionListener(new ActionListener(){
    	public void actionPerformed(ActionEvent e) {
    		SignupDialog sd = new SignupDialog(frame, database);
    	}});
    	panel.add(signupButton);
    	frame.setVisible(true);	 	
    }
    
    public void displayLogoutButton(){
    	JButton logoutButton = new JButton("logout");
    	logoutButton.setLocation(200,150);
    	logoutButton.addActionListener(new ActionListener(){
    	public void actionPerformed(ActionEvent e) {
    		panel.removeAll();
    		displayLoginButton();
    		displaySignupButton();
    	}});
    	panel.add(logoutButton); 
    	frame.setVisible(true);
    }
    
    
    public void displayPlayerList() {
    	ArrayList<String> players = database.getPlayerList();
    	playerList = new JTable();
    	playerListModel = new DefaultTableModel(0, 0);
    	String header[] = new String[] {"Player"};
    	playerListModel.setColumnIdentifiers(header);
    	playerList.setModel(playerListModel);
    	for (String player : players) {
    		playerListModel.addRow(new Object[] {player});
    		System.out.println(player);
    	}
    	panel.add(playerList);
    	frame.setVisible(true);
    	
    }
    
}