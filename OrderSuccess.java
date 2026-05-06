import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class OrderSuccess {

    public OrderSuccess(String items, int total) {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO orders(items, total, username, status) VALUES (?,?,?,?)"
            );

            ps.setString(1, items);
            ps.setInt(2, total);
            ps.setString(3, LoginFrame.currentUser);
            ps.setString(4, "Pending");

            ps.executeUpdate();
            PDFGenerator.generateBill(items, total);



            Cart.items.clear();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}