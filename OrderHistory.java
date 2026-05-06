import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;

public class OrderHistory extends JFrame {

    public OrderHistory() {
        setTitle("Order History");
        setSize(500, 300);
        setLayout(new BorderLayout());

        String[] columns = {"ID", "User", "Items", "Total", "Time", "Status"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);
        table.getTableHeader().setReorderingAllowed(false);

        try {
            Connection con = DBConnection.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(
                    "SELECT * FROM orders WHERE username = '" + LoginFrame.currentUser + "'"
            );
            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("items"),
                        rs.getInt("total"),
                        rs.getString("order_time"),
                        rs.getString("status")
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        add(new JScrollPane(table), BorderLayout.CENTER);
        JButton deliverBtn = new JButton("Mark as Delivered");
        add(deliverBtn, BorderLayout.SOUTH);
        deliverBtn.addActionListener(e -> {
            int row = table.getSelectedRow();

            if (row != -1) {
                if (model.getValueAt(row, 5).equals("Delivered")) {
                    JOptionPane.showMessageDialog(null, "Already Delivered!");
                    return;
                }
                int id = (int) model.getValueAt(row, 0);

                try {
                    Connection con = DBConnection.getConnection();
                    PreparedStatement ps = con.prepareStatement(
                            "UPDATE orders SET status='Delivered' WHERE id=?"
                    );
                    ps.setInt(1, id);
                    ps.executeUpdate();

                    model.setValueAt("Delivered", row, 5);
                    new ReviewFrame(id);

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Select a row first!");
            }
        });
        setVisible(true);
    }
}