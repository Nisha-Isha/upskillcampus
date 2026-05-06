import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class CartFrame extends JFrame {

    JPanel itemsPanel;
    JLabel totalLabel;

    public CartFrame() {
        setTitle("Your Cart");
        setSize(400, 450);
        setLayout(new BorderLayout(10,10));

        itemsPanel = new JPanel();
        itemsPanel.setLayout(new BoxLayout(itemsPanel, BoxLayout.Y_AXIS));

        JScrollPane scroll = new JScrollPane(itemsPanel);

        totalLabel = new JLabel("", JLabel.CENTER);

        JButton orderBtn = new JButton("Place Order");

        JPanel bottom = new JPanel(new GridLayout(2,1));
        bottom.add(totalLabel);
        bottom.add(orderBtn);

        add(scroll, BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);

        refreshCart();

        // ✅ Place Order
        orderBtn.addActionListener(e -> {
            new AddressFrame(Cart.getCartItems(), calculateTotal());
            if (Cart.items.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Cart is empty!");
                return;
            }


            new AddressFrame(Cart.getCartItems(), calculateTotal());
            dispose();
        });


        setLocationRelativeTo(null);
        setVisible(true);
    }

    // 🔄 Refresh UI
    void refreshCart() {
        itemsPanel.removeAll();

        for (Map.Entry<String, Integer> entry : Cart.items.entrySet()) {

            String item = entry.getKey();
            int qty = entry.getValue();

            JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT));

            JLabel name = new JLabel(item + "  × " + qty);

            JButton minus = new JButton("-");
            JButton plus = new JButton("+");
            JButton remove = new JButton("Remove");

            // ➖
            minus.addActionListener(e -> {
                Cart.removeOne(item);
                refreshCart();
            });

            // ➕
            plus.addActionListener(e -> {
                Cart.addItem(item);
                refreshCart();
            });

            // ❌
            remove.addActionListener(e -> {
                Cart.removeItem(item);
                refreshCart();
            });

            row.add(name);
            row.add(minus);
            row.add(plus);
            row.add(remove);

            itemsPanel.add(row);
        }

        updateTotal();

        itemsPanel.revalidate();
        itemsPanel.repaint();
    }

    // 💰 Total
    int calculateTotal() {
        int total = 0;

        for (java.util.Map.Entry<String, Integer> entry : Cart.items.entrySet()) {

            String item = entry.getKey();
            int qty = entry.getValue();

            // 🔥 price extract karo string se
            String[] parts = item.split("-");

            int price = Integer.parseInt(parts[1].trim());

            total += price * qty;
        }
        return total;
    }

    void updateTotal() {
        totalLabel.setText("Total Bill: ₹ " + calculateTotal());
    }
}