import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

class LoginDialog extends JDialog  {
    
    public static boolean authenticate(String username, String password) {
        // query DB for user info
        if (username.equals("bob") && password.equals("secret")) {
            return true;
        }
        return false;
    }
    
    private JTextField uname;
    private JPasswordField pass;
    private JLabel unameLb, passLb;
    private JButton btnLogin, btnCancel;
    private boolean succeeded;
    
    public LoginDialog(Frame parent) {
        super(parent, "Login", true);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints cs = new GridBagConstraints();
        
        cs.fill = GridBagConstraints.HORIZONTAL;
        
        unameLb = new JLabel("Username: ");
        cs.gridx = 0;
        cs.gridy = 0;
        cs.gridwidth = 1;
        panel.add(unameLb, cs);
        
        uname = new JTextField(20);
        cs.gridx = 1;
        cs.gridy = 0;
        cs.gridwidth = 2;
        panel.add(uname, cs);
        
        passLb = new JLabel("Password: ");
        cs.gridx = 0;
        cs.gridy = 1;
        cs.gridwidth = 1;
        panel.add(passLb, cs);
        
        pass = new JPasswordField(20);
        cs.gridx = 1;
        cs.gridy = 1;
        cs.gridwidth = 2;
        panel.add(pass, cs);
        panel.setBorder(new LineBorder(Color.GRAY));
        
        btnLogin = new JButton("Login");
        
        btnLogin.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                if (LoginDialog.authenticate(getUsername(), getPassword())) {
                    JOptionPane.showMessageDialog(LoginDialog.this, "Welcome " + getUsername() + "!", "Login", JOptionPane.INFORMATION_MESSAGE);
                    succeeded = true;
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(LoginDialog.this, "Invalid username or password!", "Login", JOptionPane.ERROR_MESSAGE);
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
        bp.add(btnLogin);
        bp.add(btnCancel);
        
        getContentPane().add(panel, BorderLayout.CENTER);
        getContentPane().add(bp, BorderLayout.PAGE_END);
        
        pack();
        setResizable(false);
        setLocationRelativeTo(parent);
    }
    
    public String getUsername() {
        return uname.getText().trim();
    }
    
    public String getPassword() {
        return new String(pass.getPassword());
    }
    
    public boolean isSucceeded() {
        return succeeded;
    }
}