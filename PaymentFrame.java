import javax.swing.*;
import java.awt.*;

public class PaymentFrame extends JFrame {

    String items;
    int total;

    public PaymentFrame(String items, int total) {
        this.items = items;
        this.total = total;

        setTitle("Payment");
        setSize(400, 400);
        setLayout(new BorderLayout(10,10));

        getContentPane().setBackground(new Color(240,242,245));
        ((JPanel)getContentPane()).setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        // 🔥 Title
        JLabel title = new JLabel("💳 Select Payment Method", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        title.setForeground(new Color(44, 62, 80));

        // 📦 Order Summary
        JTextArea summary = new JTextArea();
        summary.setText("Items:\n" + items + "\n\nTotal: ₹" + total);
        summary.setEditable(false);
        summary.setBackground(Color.WHITE);
        summary.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

        // 💰 Payment Buttons
        JButton cardBtn = new JButton("💳 Pay via Card");
        JButton upiBtn = new JButton("📱 Pay via UPI");
        JButton cashBtn = new JButton("💵 Cash on Delivery");

        // 🎨 Styling
        Color primary = new Color(52,152,219);
        Color success = new Color(46,204,113);
        Color warning = new Color(241,196,15);

        cardBtn.setBackground(primary);
        cardBtn.setForeground(Color.WHITE);

        upiBtn.setBackground(success);
        upiBtn.setForeground(Color.WHITE);

        cashBtn.setBackground(warning);

        Font f = new Font("Arial", Font.BOLD, 14);
        cardBtn.setFont(f);
        upiBtn.setFont(f);
        cashBtn.setFont(f);

        // 📦 Panel for buttons
        JPanel btnPanel = new JPanel(new GridLayout(3,1,10,10));
        btnPanel.setBackground(new Color(240,242,245));
        btnPanel.add(cardBtn);
        btnPanel.add(upiBtn);
        btnPanel.add(cashBtn);

        // 🔻 Layout
        add(title, BorderLayout.NORTH);
        add(summary, BorderLayout.CENTER);
        add(btnPanel, BorderLayout.SOUTH);

        // 🔥 Actions

        cardBtn.addActionListener(e -> processPayment("Card"));
        upiBtn.addActionListener(e -> processPayment("UPI"));
        cashBtn.addActionListener(e -> processPayment("Cash on Delivery"));

        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    JDialog createLoader() {
        JDialog loader = new JDialog(this, "Processing", true);
        loader.setSize(200, 100);
        loader.setLayout(new BorderLayout());

        JLabel label = new JLabel("Processing Payment...", JLabel.CENTER);
        JProgressBar bar = new JProgressBar();
        bar.setIndeterminate(true);

        loader.add(label, BorderLayout.NORTH);
        loader.add(bar, BorderLayout.CENTER);

        loader.setLocationRelativeTo(this);
        return loader;
    }

    // 🔥 MAIN PAYMENT LOGIC
    void processPayment(String method) {

        if (method.equals("Card")) {
            showCardForm();
            return;
        }

        if (method.equals("UPI")) {
            showUPI();
            return;
        }

        completePayment(method);
    }

    void showCardForm() {

        JTextField cardNo = new JTextField();
        JTextField name = new JTextField();
        JTextField cvv = new JTextField();

        Object[] fields = {
                "Card Number:", cardNo,
                "Card Holder Name:", name,
                "CVV:", cvv
        };

        int option = JOptionPane.showConfirmDialog(this, fields, "Enter Card Details",
                JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {

            if (cardNo.getText().length() < 12 || cvv.getText().length() < 3) {
                JOptionPane.showMessageDialog(this, "Invalid Card Details ❌");
                return;
            }

            completePayment("Card");
        }
    }

    void showUPI() {

        JDialog upiDialog = new JDialog(this, "UPI Payment", true);
        upiDialog.setSize(300, 350);
        upiDialog.setLayout(new BorderLayout());

        // 👉 QR Image (add your image in /images/upi.png)
        ImageIcon icon = new ImageIcon(getClass().getResource("/images/upi.png"));
        Image img = icon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);

        JLabel qrLabel = new JLabel(new ImageIcon(img));
        qrLabel.setHorizontalAlignment(JLabel.CENTER);

        JButton paidBtn = new JButton("I have Paid");

        paidBtn.addActionListener(e -> {
            upiDialog.dispose();
            completePayment("UPI");
        });

        upiDialog.add(new JLabel("Scan QR to Pay", JLabel.CENTER), BorderLayout.NORTH);
        upiDialog.add(qrLabel, BorderLayout.CENTER);
        upiDialog.add(paidBtn, BorderLayout.SOUTH);

        upiDialog.setLocationRelativeTo(this);
        upiDialog.setVisible(true);
    }

    void completePayment(String method) {

        JDialog loader = createLoader();

        // 🔥 show loader in separate thread
        new Thread(() -> {
            try {

                // 👉 show loader (UI thread me)
                SwingUtilities.invokeLater(() -> loader.setVisible(true));

                // 👉 wait 2 sec
                Thread.sleep(2000);

                // 👉 close loader + next UI
                SwingUtilities.invokeLater(() -> {
                    loader.dispose();

                    Cart.items.clear();

                    new OrderSuccessFrame(method, items, total);

                    new TrackingFrame();

                    dispose();
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}