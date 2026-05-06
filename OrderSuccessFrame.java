import javax.swing.*;
import java.awt.*;

public class OrderSuccessFrame extends JFrame {

    public OrderSuccessFrame(String method, String items, int total) {

        setTitle("Order Confirmed");
        setSize(400, 300);
        setLayout(new BorderLayout(10,10));

        getContentPane().setBackground(Color.WHITE);
        ((JPanel)getContentPane()).setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        JLabel success = new JLabel("✅ Order Placed Successfully!", JLabel.CENTER);
        success.setFont(new Font("Arial", Font.BOLD, 18));
        success.setForeground(new Color(39,174,96));

        JTextArea details = new JTextArea(
                "Payment Method: " + method +
                        "\n\nItems:\n" + items +
                        "\n\nTotal: ₹" + total
        );

        details.setEditable(false);
        details.setBackground(Color.WHITE);

        JButton doneBtn = new JButton("Back to Home");

        doneBtn.setBackground(new Color(52,152,219));
        doneBtn.setForeground(Color.WHITE);

        doneBtn.addActionListener(e -> {
            new HomeFrame();
            dispose();
        });

        add(success, BorderLayout.NORTH);
        add(details, BorderLayout.CENTER);
        add(doneBtn, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
        setVisible(true);
    }
}