import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Splash extends JFrame {
    
    public Splash() {
        final JFrame frame = new JFrame("Game of Politics");
        final JButton btnLogin = new JButton("Click to login");
        
        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                LoginDialog loginDlg = new LoginDialog(frame);
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