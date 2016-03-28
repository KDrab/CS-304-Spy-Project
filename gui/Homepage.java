package gui;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import database.Database;

public class Homepage extends JFrame {
	public Database database; 
	public JFrame frame;
	public JPanel panel;
	public GridBagConstraints cs;
	public JTable playerList;
	public DefaultTableModel playerListModel;
	public int appWidth = 640;
	public int appHeight = 480;

    public Homepage(String uname, Database db) {
        this.database = db;
        
        frame = new JFrame(uname + " - Homepage");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(appWidth, appHeight);
        frame.setLayout(new FlowLayout());
        frame.setVisible(true);
        
        panel = new JPanel(new GridBagLayout());
        cs = new GridBagConstraints();
        frame.add(panel);
        
        this.displayLoginButton();
        this.displaySignupButton();
    }
    
    
    public void displayLoginButton(){
    	JButton loginButton = new JButton("login");
    	cs.gridx = 0;
    	cs.gridy = 0;
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
    	cs.gridx = 1;
    	cs.gridy = 0;
    	panel.add(signupButton, cs);
    	signupButton.addActionListener(new ActionListener(){
    	public void actionPerformed(ActionEvent e) {
    		SignupDialog sd = new SignupDialog(frame, database);
    		sd.setVisible(true);
    	}});
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
    	cs.gridx = 0;
    	cs.gridy = 0;
    	panel.add(logoutButton, cs); 
    	frame.setVisible(true);
    }
    
    
    public void displayPlayerList() {
    	ArrayList<String> players = database.getPlayerList();
    	playerListModel = new DefaultTableModel(new String[]{"Players"}, 0);
    	playerList = new JTable(playerListModel);
    	for (String player : players) {
    		playerListModel.addRow(new Object[] {player});
    	}
    	cs.gridx = 0;
    	cs.gridy = 1;
    	JScrollPane pane = new JScrollPane(playerList, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
    													JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    	pane.setPreferredSize(new Dimension(appWidth/3, players.size() * 16));
    	panel.add(pane, cs);
    	frame.setVisible(true);
    	
    }
    
}