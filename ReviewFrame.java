import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class ReviewFrame extends JFrame {

    public ReviewFrame(int orderId) {
        setTitle("Give Review");
        setSize(300, 250);
        setLayout(new FlowLayout());

        JLabel label = new JLabel("Rate your order (1-5):");
        JTextField ratingField = new JTextField(5);
        JTextArea commentArea = new JTextArea(5, 20);

        JButton submitBtn = new JButton("Submit Review");

        add(label);
        add(ratingField);
        add(new JLabel("Comment:"));
        add(commentArea);
        add(submitBtn);

        submitBtn.addActionListener(e -> {
            try {
                String text = ratingField.getText();

                int rating = Integer.parseInt(text);

                if (rating < 1 || rating > 5) {
                    JOptionPane.showMessageDialog(null, "Rating must be between 1 to 5");
                    return;
                }

                String comment = commentArea.getText();

                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(
                        "INSERT INTO reviews(username, order_id, rating, comment) VALUES (?,?,?,?)"
                );

                ps.setString(1, LoginFrame.currentUser);
                ps.setInt(2, orderId);
                ps.setInt(3, rating);
                ps.setString(4, comment);

                ps.executeUpdate();

                JOptionPane.showMessageDialog(null, "Review Submitted!");
                dispose();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Enter valid number (1-5)");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        setVisible(true);
    }
}