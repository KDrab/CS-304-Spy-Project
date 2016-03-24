package gui;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Homepage extends JFrame {
    
    public Homepage(String uname) {
        JFrame frame = new JFrame(uname + " - Dashboard");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 300);
        frame.setLayout(new FlowLayout());
        frame.setVisible(true);
        
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints cs = new GridBagConstraints();
        frame.add(panel);
        
        JButton btnLogout = new JButton("Logout");
        
        cs.gridx = 0;
        cs.gridy = 0;
        cs.gridwidth = 1;
        panel.add(btnLogout, cs);
        
        btnLogout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Splash newsplash = new Splash();
                newsplash.setVisible(true);
                frame.dispose();
            }
        });
    }
}