import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class ReviewListFrame extends JFrame {

    public ReviewListFrame() {
        setTitle("Customer Reviews");
        setSize(500, 300);
        setLayout(new BorderLayout());

        String[] columns = {"Order ID", "Rating", "Comment"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);

        try {
            Connection con = DBConnection.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM reviews");

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("order_id"),
                        rs.getInt("rating"),
                        rs.getString("comment")
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        add(new JScrollPane(table), BorderLayout.CENTER);

        setVisible(true);
    }
}