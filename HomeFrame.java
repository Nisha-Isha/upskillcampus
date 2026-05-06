import javax.swing.*;
import java.awt.*;
import java.util.*;

public class HomeFrame extends JFrame {

    class FoodItem {
        String name;
        int price;
        String category;

        FoodItem(String name, int price, String category) {
            this.name = name;
            this.price = price;
            this.category = category;
        }
    }

    java.util.List<FoodItem> menu = new ArrayList<>();

    public HomeFrame() {
        setTitle("Food Menu");
        setSize(600, 500);

        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(240, 242, 245));
        ((JPanel) getContentPane()).setBorder(
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        );

        // 🔍 Search
        JTextField searchField = new JTextField(15);
        JButton searchBtn = new JButton("Search");
        JButton resultBtn = new JButton();
        resultBtn.setVisible(false);

        JPanel topPanel = new JPanel(new FlowLayout());
        topPanel.setBackground(new Color(240, 242, 245));
        topPanel.add(searchField);
        topPanel.add(searchBtn);
        topPanel.add(resultBtn);

        // 🔥 MENU
        addMenuItems();

        // 🔲 Tabs
        JTabbedPane tabs = new JTabbedPane();
        tabs.add("Pizza", createCategoryPanel(menu, "Pizza"));
        tabs.add("Burger", createCategoryPanel(menu, "Burger"));
        tabs.add("Pasta", createCategoryPanel(menu, "Pasta"));
        tabs.add("Sandwich", createCategoryPanel(menu, "Sandwich"));
        tabs.add("Snacks", createCategoryPanel(menu, "Snacks"));
        tabs.add("Drinks", createCategoryPanel(menu, "Drinks"));
        tabs.add("Desserts", createCategoryPanel(menu, "Desserts"));

        // 🔥 Buttons
        JButton cartBtn = new JButton("View Cart");
        JButton historyBtn = new JButton("Order History");
        JButton logoutBtn = new JButton("Logout");
        JButton reviewBtn = new JButton("View Reviews");
        JButton adminBtn = new JButton("Admin Panel");
        JButton profileBtn = new JButton("Profile");

        // 🎨 Button styling
        Color green = new Color(46, 204, 113);
        Color blue = new Color(52, 152, 219);
        Color red = new Color(231, 76, 60);

        cartBtn.setBackground(blue);
        historyBtn.setBackground(blue);
        reviewBtn.setBackground(blue);
        adminBtn.setBackground(blue);
        profileBtn.setBackground(blue);

        logoutBtn.setBackground(red);

        cartBtn.setForeground(Color.WHITE);
        historyBtn.setForeground(Color.WHITE);
        reviewBtn.setForeground(Color.WHITE);
        adminBtn.setForeground(Color.WHITE);
        profileBtn.setForeground(Color.WHITE);
        logoutBtn.setForeground(Color.WHITE);

        JPanel actionPanel = new JPanel(new GridLayout(2, 3, 10, 10));
        actionPanel.setBackground(new Color(240,242,245));

        actionPanel.add(cartBtn);
        actionPanel.add(historyBtn);
        actionPanel.add(reviewBtn);
        actionPanel.add(adminBtn);
        actionPanel.add(profileBtn);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(new Color(240,242,245));
        bottomPanel.add(actionPanel, BorderLayout.CENTER);
        bottomPanel.add(logoutBtn, BorderLayout.SOUTH);

        // 🔥 LOGO
        ImageIcon logo;

        try {
            logo = new ImageIcon(getClass().getResource("/images/logo.png"));
        } catch (Exception e) {
            // fallback (agar resource fail ho jaye)
            logo = new ImageIcon("src/images/logo.png");
        }

        Image img = logo.getImage().getScaledInstance(120, 50, Image.SCALE_SMOOTH);
        JLabel logoLabel = new JLabel(new ImageIcon(img));
        logoLabel.setHorizontalAlignment(JLabel.CENTER);

        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.setBackground(new Color(255,255,255));

        northPanel.add(logoLabel, BorderLayout.NORTH); // ✅ yaha logo
        northPanel.add(topPanel, BorderLayout.SOUTH);  // search bar niche

        add(northPanel, BorderLayout.NORTH);

        add(northPanel, BorderLayout.NORTH);
        add(tabs, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        // 🔍 Search FIX
        searchBtn.addActionListener(e -> {
            String search = searchField.getText().trim().toLowerCase();

            if (search.isEmpty()) {
                resultBtn.setVisible(false);
                return;
            }

            boolean found = false;

            for (FoodItem item : menu) {
                if (item.name.toLowerCase().contains(search)) {
                    resultBtn.setText(item.name + " - " + item.price);
                    resultBtn.setVisible(true);
                    found = true;
                    break;
                }
            }

            if (!found) {
                resultBtn.setVisible(false);
                JOptionPane.showMessageDialog(this, "Item not found!");
            }
        });

        resultBtn.addActionListener(e -> {
            Cart.addItem(resultBtn.getText());
        });

        // 🔘 Actions
        cartBtn.addActionListener(e -> new CartFrame());
        historyBtn.addActionListener(e -> new OrderHistory());
        reviewBtn.addActionListener(e -> new ReviewListFrame());
        adminBtn.addActionListener(e -> new AdminDashboard());
        profileBtn.addActionListener(e -> new ProfileFrame());

        logoutBtn.addActionListener(e -> {
            LoginFrame.currentUser = null;
            new LoginFrame();
            dispose();
        });

        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    // 🔥 MENU DATA
    void addMenuItems() {

        menu.add(new FoodItem("Margherita Pizza", 200, "Pizza"));
        menu.add(new FoodItem("Cheese Burst Pizza", 350, "Pizza"));
        menu.add(new FoodItem("Veg Loaded Pizza", 280, "Pizza"));
        menu.add(new FoodItem("Paneer Tikka Pizza", 320, "Pizza"));


        menu.add(new FoodItem("Veg Burger", 150, "Burger"));
        menu.add(new FoodItem("Cheese Burger", 180, "Burger"));
        menu.add(new FoodItem("Paneer Burger", 200, "Burger"));
        menu.add(new FoodItem("Double Patty Burger", 250, "Burger"));

        menu.add(new FoodItem("White Sauce Pasta", 220, "Pasta"));
        menu.add(new FoodItem("Red Sauce Pasta", 200, "Pasta"));

        menu.add(new FoodItem("Grilled Sandwich", 140, "Sandwich"));
        menu.add(new FoodItem("Cheese Sandwich", 160, "Sandwich"));

        menu.add(new FoodItem("French Fries", 120, "Snacks"));
        menu.add(new FoodItem("Garlic Bread", 130, "Snacks"));

        menu.add(new FoodItem("Coke", 50, "Drinks"));
        menu.add(new FoodItem("Milkshake", 150, "Drinks"));

        menu.add(new FoodItem("Chocolate Cake", 180, "Desserts"));
        menu.add(new FoodItem("Ice Cream", 100, "Desserts"));
    }

    // 🔥 CARD UI IMPROVED
    JPanel createFoodCard(String name, int price) {

        JPanel card = new JPanel(new BorderLayout(5, 5));
        card.setBackground(Color.WHITE);

        JLabel title = new JLabel(name, JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 14));

        JLabel priceLabel = new JLabel("₹" + price, JLabel.CENTER);

        JButton addBtn = new JButton("Add to Cart");
        addBtn.setBackground(new Color(46, 204, 113));
        addBtn.setForeground(Color.WHITE);

        addBtn.addActionListener(e -> {

            String[] sizes = {"Small", "Medium", "Large"};

            String selected = (String) JOptionPane.showInputDialog(
                    this, "Select Size", name,
                    JOptionPane.PLAIN_MESSAGE, null, sizes, sizes[0]
            );

            if (selected != null) {
                int finalPrice = price;

                if (selected.equals("Medium")) finalPrice += 50;
                if (selected.equals("Large")) finalPrice += 100;

                Cart.addItem(name + " (" + selected + ") - " + finalPrice);
            }
        });

        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220,220,220)),
                BorderFactory.createEmptyBorder(10,10,10,10)
        ));

        card.add(title, BorderLayout.NORTH);
        card.add(priceLabel, BorderLayout.CENTER);
        card.add(addBtn, BorderLayout.SOUTH);

        return card;
    }

    JPanel createCategoryPanel(java.util.List<FoodItem> menu, String category) {

        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        panel.setBackground(new Color(240,242,245));

        for (FoodItem item : menu) {
            if (item.category.equals(category)) {
                panel.add(createFoodCard(item.name, item.price));
            }
        }

        return panel;
    }
}