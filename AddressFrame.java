import javax.swing.*;
import java.awt.*;

public class AddressFrame extends JFrame {

    JTextArea addressArea;

    public AddressFrame(String items, int total) {

        setTitle("Delivery Address");
        setSize(350, 300);
        setLayout(new BorderLayout(10,10));

        addressArea = new JTextArea("Enter your address...");
        addressArea.setForeground(Color.GRAY); // placeholder color

        addressArea.addFocusListener(new java.awt.event.FocusAdapter() {

            public void focusGained(java.awt.event.FocusEvent e) {
                if (addressArea.getText().equals("Enter your address...")) {
                    addressArea.setText("");
                    addressArea.setForeground(Color.BLACK);
                }
            }

            public void focusLost(java.awt.event.FocusEvent e) {
                if (addressArea.getText().trim().isEmpty()) {
                    addressArea.setText("Enter your address...");
                    addressArea.setForeground(Color.GRAY);
                }
            }
        });
        JButton continueBtn = new JButton("Continue to Payment");

        add(new JLabel("📍 Delivery Address", JLabel.CENTER), BorderLayout.NORTH);
        add(addressArea, BorderLayout.CENTER);
        add(continueBtn, BorderLayout.SOUTH);

        continueBtn.addActionListener(e -> {
            new PaymentFrame(items, total);
            dispose();
        });

        setLocationRelativeTo(null);
        setVisible(true);
    }
}