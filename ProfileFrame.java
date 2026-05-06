import javax.swing.*;
import java.awt.*;

public class ProfileFrame extends JFrame {

    public ProfileFrame() {

        setTitle("My Profile");
        setSize(300,250);
        setLayout(new GridLayout(5,1));

        add(new JLabel("👤 User: " + LoginFrame.currentUser));
        add(new JLabel("📧 Email: demo@mail.com"));
        add(new JLabel("📞 Phone: 9999999999"));

        JButton orders = new JButton("View Orders");
        add(orders);

        orders.addActionListener(e -> new OrderHistory());

        setLocationRelativeTo(null);
        setVisible(true);
    }
}