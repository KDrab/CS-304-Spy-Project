import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import database.Database;
import gui.Homepage;
import gui.LoginDialog;

public class Main {
    public static void main(String[] args) {
    	// create database
    	Database db = new Database("ora_q0b9", "a48197123");
    	
        final JFrame frame = new JFrame("Game of Politics");
        final JButton btnLogin = new JButton("Click to login");
        
        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                LoginDialog loginDlg = new LoginDialog(frame, db);
                loginDlg.setVisible(true);
                if(loginDlg.isSucceeded()){
                    // successful login, go to new window
                    btnLogin.setText("Log out");
                    Homepage homepage = new Homepage(loginDlg.getUsername());
                }
            }
        });
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 300);
        frame.setLayout(new FlowLayout());
        frame.getContentPane().add(btnLogin);
        frame.setVisible(true);
    }
}