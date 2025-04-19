package view;
import controller.PlayerVsAIController;
import controller.TrainingModeController;
import javax.swing.*;
import java.awt.*;

/**
 * MainMenu is the entry point of the ACE (AI Card Expert) Blackjack application.
 * It displays the main screen where users can choose between:
 * - Training Mode: play Blackjack with AI feedback based on a Q-table
 * - Versus Mode: play Blackjack against an AI opponent
 *
 * This class sets up a visually styled menu using Java Swing and handles mode transitions.
 */
public class MainMenu extends JFrame {

  /**
   * Constructs the main menu window and initializes UI components.
   * Sets layout, title, buttons, and their corresponding event handlers.
   */
  public MainMenu() {
    setTitle("ACE - Main Menu");
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setSize(600, 400);
    setLocationRelativeTo(null);
    setResizable(false);

    // Panel layout and styling
    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    panel.setBackground(new Color(34, 139, 34));  // Dark green like poker table

    // Game title label
    JLabel title = new JLabel("🂡 ACE (AI Card Expert)", SwingConstants.CENTER);
    title.setFont(new Font("SansSerif", Font.BOLD, 32));
    title.setForeground(Color.WHITE);
    title.setAlignmentX(Component.CENTER_ALIGNMENT);

    // Buttons for selecting modes
    JButton trainingButton = new JButton("Black Jack Training Mode");
    JButton versusButton = new JButton("Black Jack Versus Mode");

    trainingButton.setMaximumSize(new Dimension(200, 50));
    versusButton.setMaximumSize(new Dimension(200, 50));
    trainingButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    versusButton.setAlignmentX(Component.CENTER_ALIGNMENT);

    // Event handler: start Training Mode (MVC version)
    trainingButton.addActionListener(e -> {
      dispose();
      TrainingModeView view = new TrainingModeView();
      new TrainingModeController(view);
      view.setVisible(true);
    });

    // Event handler: start Versus Mode (MVC version)
    versusButton.addActionListener(e -> {
      dispose();
      PlayerVsAIFrame frame = new PlayerVsAIFrame();
      new PlayerVsAIController(frame);
      frame.setVisible(true);
    });

    // Layout components vertically with spacing
    panel.add(Box.createVerticalStrut(60));
    panel.add(title);
    panel.add(Box.createVerticalStrut(50));
    panel.add(trainingButton);
    panel.add(Box.createVerticalStrut(20));
    panel.add(versusButton);

    add(panel);
  }

  /**
   * Launches the MainMenu GUI on the Event Dispatch Thread.
   *
   * @param args command-line arguments (unused)
   */
  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> new MainMenu().setVisible(true));
  }
}
