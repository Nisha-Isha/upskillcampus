import javax.swing.*;
import java.awt.*;

public class SplashScreen extends JFrame {

    JLabel logoLabel;
    float opacity = 0.0f;
    int size = 120;

    public SplashScreen() {
        setSize(400, 350);
        setUndecorated(true);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(null);
        panel.setBackground(Color.WHITE);

        // 🔥 LOGO LOAD
        ImageIcon logo = new ImageIcon(getClass().getResource("/images/logo.png"));
        Image img = logo.getImage().getScaledInstance(size, size, Image.SCALE_SMOOTH);
        logoLabel = new JLabel(new ImageIcon(img));
        logoLabel.setOpaque(true);
        logoLabel.setBackground(Color.WHITE); // 🔥 background same as panel

        logoLabel.setBounds(140, 80, size, size);
        panel.add(logoLabel);

        // 🔥 APP NAME
        JLabel text = new JLabel("PRANISHA", JLabel.CENTER);
        text.setFont(new Font("Arial", Font.BOLD, 20));
        text.setForeground(new Color(44, 62, 80));
        int textY = (350 - size)/2 + size + 20;
        text.setBounds(100, textY, 200, 30);
        panel.add(text);

        add(panel);
        setVisible(true);

        startAnimation();
    }

    void startAnimation() {

        Timer timer = new Timer(100, null);

        timer.addActionListener(e -> {

            // 🔥 Fade in effect
            opacity += 0.05f;
            if (opacity > 1f) opacity = 1f;
            setOpacity(opacity);

            // 🔥 Scale effect (zoom in)
            size += 2;
            if (size > 160) size = 160;

            ImageIcon logo = new ImageIcon(getClass().getResource("/images/logo.png"));
            Image img = logo.getImage().getScaledInstance(size, size, Image.SCALE_SMOOTH);
            logoLabel.setIcon(new ImageIcon(img));

            logoLabel.setBounds((400 - size)/2, (350 - size)/2 - 20, size, size);

            // 🔥 END → go to login
            if (opacity >= 1f && size >= 160) {
                timer.stop();

                // ⏳ slight delay for premium feel
                Timer next = new Timer(500, ev -> {
                    new LoginFrame();
                    dispose();
                });
                next.setRepeats(false); // 🔥 IMPORTANT
                next.start();
            }

        });

        timer.start();
    }
}