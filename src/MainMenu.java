// MainMenu.java
import javax.swing.*;
import java.awt.*;

public class MainMenu extends JFrame {
  public MainMenu() {
    setTitle("ACE - Main Menu");
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setSize(600, 400);
    setLocationRelativeTo(null);
    setResizable(false);

    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    panel.setBackground(new Color(34, 139, 34));

    JLabel title = new JLabel("🂡 ACE (AI Card Expert)", SwingConstants.CENTER);
    title.setFont(new Font("SansSerif", Font.BOLD, 32));
    title.setForeground(Color.WHITE);
    title.setAlignmentX(Component.CENTER_ALIGNMENT);

    JButton trainingButton = new JButton("Black Jack Training Mode");
    JButton versusButton = new JButton("Black Jack Versus Mode");

    trainingButton.setMaximumSize(new Dimension(200, 50));
    versusButton.setMaximumSize(new Dimension(200, 50));

    trainingButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    versusButton.setAlignmentX(Component.CENTER_ALIGNMENT);

    trainingButton.addActionListener(e -> {
      dispose();
      TrainingMode training = new TrainingMode();
      training.setVisible(true);
    });

    versusButton.addActionListener(e -> {
      dispose();
      new VersusMode().setVisible(true); // 让组员完成具体实现
    });

    panel.add(Box.createVerticalStrut(60));
    panel.add(title);
    panel.add(Box.createVerticalStrut(50));
    panel.add(trainingButton);
    panel.add(Box.createVerticalStrut(20));
    panel.add(versusButton);

    add(panel);
  }

  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> new MainMenu().setVisible(true));
  }
}
