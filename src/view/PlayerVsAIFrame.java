package view;
import javax.swing.*;
import java.awt.*;

/**
 * The PlayerVsAIFrame class represents the main game window (View) for the Player vs AI mode
 * in the Blackjack game. It displays the AI and player avatars, their cards, the game status label,
 * and the control buttons at the bottom.
 */
public class PlayerVsAIFrame extends JFrame {

  /** Displays dynamic game messages (e.g., instructions, win/loss) */
  public JLabel statusLabel;

  /** Panel containing player's visible cards */
  public JPanel playerPanel;

  /** Panel containing AI (dealer)'s visible cards */
  public JPanel aiPanel;

  /** Button to flip the initial cards */
  public JButton revealButton;

  /** Button to hit and draw a card */
  public JButton hitButton;

  /** Button to stand and end player's turn */
  public JButton standButton;

  /** Button to start a new round */
  public JButton nextRoundButton;

  /** Button to return to the main menu */
  public JButton returnToMenuButton;

  /**
   * Constructs the Player vs AI window, initializes the UI layout and all components.
   * Sets background color, status label, card areas, avatars, and game control buttons.
   */
  public PlayerVsAIFrame() {
    setTitle("ACE (AI Card Expert) - Versus Mode");
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setResizable(false);
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    setSize((int) (screenSize.width * 0.9), (int) (screenSize.height * 0.9));
    setLocationRelativeTo(null);

    // ====== Main layout background ======
    JPanel content = new JPanel(new BorderLayout());
    content.setBackground(new Color(39, 119, 20)); // green table background
    setContentPane(content);

    // ====== Status label ======
    statusLabel = new JLabel("♠ Welcome to ACE - Dare to beat the AI dealer? ♠", SwingConstants.CENTER);
    statusLabel.setFont(new Font("SansSerif", Font.BOLD, 28));
    statusLabel.setForeground(Color.WHITE);
    statusLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0)); // top/bottom spacing
    add(statusLabel, BorderLayout.NORTH);

    // ====== Dealer section ======
    JLabel dealerAvatar = new JLabel(new ImageIcon(
        new ImageIcon("assets/dealer_avatar.png")
            .getImage().getScaledInstance(123, 160, Image.SCALE_SMOOTH)
    ));
    dealerAvatar.setAlignmentX(Component.CENTER_ALIGNMENT);

    aiPanel = GamePanelBuilder.createHiddenCardPanel();

    JPanel dealerSection = new JPanel();
    dealerSection.setLayout(new BoxLayout(dealerSection, BoxLayout.Y_AXIS));
    dealerSection.setOpaque(false);
    dealerSection.add(dealerAvatar);
    dealerSection.add(Box.createVerticalStrut(0));
    dealerSection.add(aiPanel);

    // ====== Player section ======
    JLabel playerAvatar = new JLabel(new ImageIcon(
        new ImageIcon("assets/player_avatar.png")
            .getImage().getScaledInstance(113, 142, Image.SCALE_SMOOTH)
    ));
    playerAvatar.setAlignmentX(Component.CENTER_ALIGNMENT);

    playerPanel = GamePanelBuilder.createHiddenCardPanel();

    JPanel playerSection = new JPanel();
    playerSection.setLayout(new BoxLayout(playerSection, BoxLayout.Y_AXIS));
    playerSection.setOpaque(false);
    playerSection.add(playerAvatar);
    playerSection.add(Box.createVerticalStrut(0));
    playerSection.add(playerPanel);

    // ====== Combined card area ======
    JPanel cardPanel = new JPanel();
    cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
    cardPanel.setOpaque(false);
    cardPanel.add(Box.createVerticalStrut(40));  // space from top
    cardPanel.add(dealerSection);
    cardPanel.add(Box.createVerticalStrut(10));  // space between dealer & player
    cardPanel.add(playerSection);
    add(cardPanel, BorderLayout.CENTER);

    // ====== Control button panel ======
    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
    buttonPanel.setOpaque(false);

    revealButton = createStyledButton("Flip");
    hitButton = createStyledButton("Hit");
    standButton = createStyledButton("Stand");
    nextRoundButton = createStyledButton("Next Round");
    returnToMenuButton = createStyledButton("Return to Menu");

    buttonPanel.add(revealButton);
    buttonPanel.add(hitButton);
    buttonPanel.add(standButton);
    buttonPanel.add(nextRoundButton);
    buttonPanel.add(returnToMenuButton);

    add(buttonPanel, BorderLayout.SOUTH);
  }

  /**
   * Creates a consistently styled button with white background and fixed size.
   *
   * @param text Text label to show on the button
   * @return A styled {@code JButton}
   */
  private JButton createStyledButton(String text) {
    JButton btn = new JButton(text);
    btn.setPreferredSize(new Dimension(130, 40));
    btn.setFocusPainted(false);
    btn.setBackground(Color.WHITE);
    btn.setForeground(Color.DARK_GRAY);
    btn.setFont(new Font("SansSerif", Font.PLAIN, 14));
    return btn;
  }
}
