import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class AdminDashboard extends JFrame {

    public AdminDashboard() {
        setTitle("Admin Dashboard");
        setSize(400, 300);
        setLayout(new GridLayout(4, 1, 10, 10));

        JLabel totalOrders = new JLabel();
        JLabel totalRevenue = new JLabel();
        JLabel totalUsers = new JLabel();

        try {
            Connection con = DBConnection.getConnection();
            Statement st = con.createStatement();

            // Total orders
            ResultSet rs1 = st.executeQuery("SELECT COUNT(*) FROM orders");
            if (rs1.next()) {
                totalOrders.setText("Total Orders: " + rs1.getInt(1));
            }

            // Total revenue
            ResultSet rs2 = st.executeQuery("SELECT SUM(total) FROM orders");
            if (rs2.next()) {
                totalRevenue.setText("Total Revenue: ₹" + rs2.getInt(1));
            }

            // Total users
            ResultSet rs3 = st.executeQuery("SELECT COUNT(*) FROM users");
            if (rs3.next()) {
                totalUsers.setText("Total Users: " + rs3.getInt(1));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        add(totalOrders);
        add(totalRevenue);
        add(totalUsers);

        setVisible(true);
    }
}