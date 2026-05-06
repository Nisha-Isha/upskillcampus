import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class LoginFrame extends JFrame {
    public static String currentUser;
    JTextField userField;
    JPasswordField passField;

    public LoginFrame() {
        setTitle("Login");
        setSize(380, 260);
        setLayout(new BorderLayout());

// 🔥 Main panel (card style)
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5,1,10,10));
        panel.setBorder(BorderFactory.createEmptyBorder(20,30,20,30));
        panel.setBackground(new Color(240, 242, 245));

// 🔥 Title
        JLabel title = new JLabel("Welcome Back 👋", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        title.setForeground(new Color(44, 62, 80));

// 🔥 Fields
        userField = new JTextField();
        passField = new JPasswordField();

        userField.setPreferredSize(new Dimension(200,30));
        passField.setPreferredSize(new Dimension(200,30));

// 🔥 Buttons
        JButton loginBtn = new JButton("Login");
        JButton signupBtn = new JButton("Signup");

// 🎨 Button styling
        loginBtn.setBackground(new Color(46, 204, 113));
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setFocusPainted(false);

        signupBtn.setBackground(new Color(52, 152, 219));
        signupBtn.setForeground(Color.WHITE);
        signupBtn.setFocusPainted(false);

// 🔥 Button panel (side by side)
        JPanel btnPanel = new JPanel(new GridLayout(1,2,10,10));
        btnPanel.setBackground(new Color(240, 242, 245));
        btnPanel.add(loginBtn);
        btnPanel.add(signupBtn);

// 🔥 Add components
        panel.add(new JLabel("Username:"));
        panel.add(userField);
        panel.add(new JLabel("Password:"));
        panel.add(passField);
        panel.add(new JLabel()); // spacing
        panel.add(btnPanel);

        add(panel);

// 🔥 Actions (IMPORTANT - already discussed)
        loginBtn.addActionListener(e -> login());
        signupBtn.addActionListener(e -> signup());

// 🔥 Window settings
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    void login() {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(
                    "SELECT * FROM users WHERE username=? AND password=?"
            );

            String username = userField.getText().trim();
            String password = PasswordUtil.hashPassword(new String(passField.getPassword()));

            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                currentUser = username;
                new HomeFrame();
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Login");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    void signup() {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO users VALUES (?,?)"
            );
            ps.setString(1, userField.getText());
            ps.setString(2, PasswordUtil.hashPassword(new String(passField.getPassword())));

            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "User Registered!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}