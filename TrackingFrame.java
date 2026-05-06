import javax.swing.*;
import java.awt.*;

public class TrackingFrame extends JFrame {

    JLabel status;

    public TrackingFrame() {

        setTitle("Order Tracking");
        setSize(300,200);
        setLayout(new BorderLayout());

        status = new JLabel("Preparing your order 🍳", JLabel.CENTER);
        status.setFont(new Font("Arial", Font.BOLD, 14));

        add(status);

        setLocationRelativeTo(null);
        setVisible(true);

        // 🔥 Auto status change
        new Thread(() -> {
            try {
                Thread.sleep(2000);
                status.setText("Out for Delivery 🚚");

                Thread.sleep(2000);
                status.setText("Delivered ✅");

            } catch (Exception e) {}
        }).start();
    }
}