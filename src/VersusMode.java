// VersusMode.java
import javax.swing.*;
import java.awt.*;

public class VersusMode extends JFrame {
  public VersusMode() {
    setTitle("Versus Mode - Blackjack Duel");
    setSize(900, 700);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setResizable(false);

    JLabel placeholder = new JLabel("🧱 Versus Mode - Under Construction", SwingConstants.CENTER);
    placeholder.setFont(new Font("SansSerif", Font.BOLD, 24));
    placeholder.setForeground(Color.DARK_GRAY);

    getContentPane().setBackground(new Color(39, 119, 20));
    setLayout(new BorderLayout());
    add(placeholder, BorderLayout.CENTER);
  }
}
