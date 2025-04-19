package view;
import javax.swing.*;
import java.awt.*;
import java.util.List;
import model.Card;

/**
 * TrainingModeView is the GUI component for the training mode of the ACE Blackjack game.
 * It displays the dealer and player hands, status messages, and action buttons.
 * This class handles the visual layout and provides public methods for the controller
 * to update the interface based on game state.
 */
public class TrainingModeView extends JFrame {

  /** Buttons for user actions in training mode */
  public JButton hitButton, standButton, revealButton, nextButton, returnButton;

  /** Label to display AI advisor feedback or game instructions */
  public JLabel statusLabel;

  /** Panels that visually display the dealer and player cards */
  public JPanel dealerCardPanel, playerCardPanel;

  /** Container panel for the status label */
  private final JPanel statusPanel;

  /**
   * Constructs the main window for training mode.
   * Sets up the status area, dealer and player sections, and control buttons.
   */
  public TrainingModeView() {
    setTitle("ACE (AI Card Expert) - Training Mode");
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setResizable(false);
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    setSize((int) (screenSize.width * 0.9), (int) (screenSize.height * 0.9));
    setLocationRelativeTo(null);

    // Main layout container with green background
    JPanel content = new JPanel(new BorderLayout());
    content.setBackground(new Color(39, 119, 20));
    setContentPane(content);

    // ====== Status Label at the Top ======
    statusPanel = new JPanel(new BorderLayout());
    statusPanel.setBackground(new Color(39, 119, 20));
    statusLabel = new JLabel("♠ Welcome to ACE – Practice makes perfect in Training Mode! Click Flip! ♠", SwingConstants.CENTER);
    statusLabel.setFont(new Font("SansSerif", Font.BOLD, 26));
    statusLabel.setForeground(Color.WHITE);
    statusLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 30, 0));
    statusPanel.add(statusLabel, BorderLayout.CENTER);
    add(statusPanel, BorderLayout.NORTH);

    // ====== Dealer and Player Card Area ======
    JPanel gameArea = new JPanel();
    gameArea.setLayout(new BoxLayout(gameArea, BoxLayout.Y_AXIS));
    gameArea.setOpaque(false);
    gameArea.add(Box.createVerticalStrut(10));

    dealerCardPanel = createHiddenCardPanel(2);
    playerCardPanel = createHiddenCardPanel(2);
    gameArea.add(wrapDealerSection(dealerCardPanel));
    gameArea.add(Box.createVerticalStrut(10));
    gameArea.add(wrapPlayerSection(playerCardPanel));

    add(gameArea, BorderLayout.CENTER);

    // ====== Control Buttons at the Bottom ======
    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
    buttonPanel.setOpaque(false);

    hitButton = createStyledButton("Hit");
    standButton = createStyledButton("Stand");
    revealButton = createStyledButton("Flip");
    nextButton = createStyledButton("Next Round");
    returnButton = createStyledButton("Return to Menu");

    buttonPanel.add(revealButton);
    buttonPanel.add(hitButton);
    buttonPanel.add(standButton);
    buttonPanel.add(nextButton);
    buttonPanel.add(returnButton);

    add(buttonPanel, BorderLayout.SOUTH);

    // Disable hit/stand until cards are flipped
    hitButton.setEnabled(false);
    standButton.setEnabled(false);
  }

  /**
   * Creates a styled JButton for the bottom panel.
   *
   * @param text Button label
   * @return Styled JButton
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

  /**
   * Displays a game message (like correct/incorrect moves or round info)
   * in the status label.
   *
   * @param msg the message to display
   */
  public void showFeedback(String msg) {
    statusLabel.setText(msg);
    statusLabel.setForeground(Color.WHITE);
    statusPanel.revalidate();
    statusPanel.repaint();
  }

  /**
   * Updates the player's hand panel to display a list of card images.
   *
   * @param cards List of player's cards
   */
  public void updatePlayerCards(List<Card> cards) {
    playerCardPanel.removeAll();
    for (Card c : cards)
      playerCardPanel.add(makeCardLabel(c.getImagePath()));
    playerCardPanel.revalidate();
    playerCardPanel.repaint();
  }

  /**
   * Updates the dealer panel to show one visible card and one face-down.
   *
   * @param visibleCard The dealer's first (visible) card
   */
  public void updateDealerCard(Card visibleCard) {
    dealerCardPanel.removeAll();
    dealerCardPanel.add(makeCardLabel(visibleCard.getImagePath()));
    dealerCardPanel.add(makeCardLabel("assets/cards/back1.png"));
    dealerCardPanel.revalidate();
    dealerCardPanel.repaint();
  }

  /**
   * Reveals the dealer's second card after a flip.
   *
   * @param secondCard The second card to display
   */
  public void updateDealerSecondCard(Card secondCard) {
    if (dealerCardPanel.getComponentCount() > 1) {
      JLabel secondLabel = (JLabel) dealerCardPanel.getComponent(1);
      secondLabel.setIcon(new ImageIcon(new ImageIcon(secondCard.getImagePath())
          .getImage().getScaledInstance(111, 162, Image.SCALE_SMOOTH)));
    }
  }

  /**
   * Resets both player and dealer panels with two face-down cards.
   * Also resets the feedback message.
   */
  public void resetGameView() {
    resetHiddenCards(dealerCardPanel);
    resetHiddenCards(playerCardPanel);
  }

  /**
   * Replaces the specified panel with two hidden cards.
   *
   * @param targetPanel panel to reset
   */
  private void resetHiddenCards(JPanel targetPanel) {
    targetPanel.removeAll();
    for (int i = 0; i < 2; i++)
      targetPanel.add(makeCardLabel("assets/cards/back1.png"));
    targetPanel.revalidate();
    targetPanel.repaint();
  }

  /**
   * Creates a panel filled with a given number of hidden (backside) cards.
   *
   * @param count Number of cards to show
   * @return JPanel containing the hidden cards
   */
  private JPanel createHiddenCardPanel(int count) {
    JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
    panel.setOpaque(false);
    for (int i = 0; i < count; i++)
      panel.add(makeCardLabel("assets/cards/back1.png"));
    return panel;
  }

  /**
   * Creates a JLabel displaying a scaled card image with white border.
   *
   * @param imagePath Path to the image
   * @return JLabel showing the card
   */
  private JLabel makeCardLabel(String imagePath) {
    Image img = new ImageIcon(imagePath).getImage().getScaledInstance(111, 162, Image.SCALE_SMOOTH);
    JLabel label = new JLabel(new ImageIcon(img));
    label.setPreferredSize(new Dimension(111, 162));
    label.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
    label.setOpaque(true);
    label.setBackground(Color.WHITE);
    return label;
  }

  /**
   * Wraps the dealer avatar and its associated card panel into one vertical block.
   *
   * @param cardPanel The dealer's card panel
   * @return Panel containing dealer avatar and cards
   */
  private JPanel wrapDealerSection(JPanel cardPanel) {
    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    panel.setOpaque(false);

    Image dealerImage = new ImageIcon("assets/dealer_avatar.png")
        .getImage().getScaledInstance(123, 160, Image.SCALE_SMOOTH);
    JLabel dealerAvatar = new JLabel(new ImageIcon(dealerImage));
    dealerAvatar.setAlignmentX(Component.CENTER_ALIGNMENT);

    panel.add(dealerAvatar);
    panel.add(Box.createVerticalStrut(0));
    panel.add(cardPanel);
    return panel;
  }

  /**
   * Wraps the player avatar and its associated card panel into one vertical block.
   *
   * @param cardPanel The player's card panel
   * @return Panel containing player avatar and cards
   */
  private JPanel wrapPlayerSection(JPanel cardPanel) {
    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    panel.setOpaque(false);

    Image playerImage = new ImageIcon("assets/player_avatar.png")
        .getImage().getScaledInstance(113, 142, Image.SCALE_SMOOTH);
    JLabel playerAvatar = new JLabel(new ImageIcon(playerImage));
    playerAvatar.setAlignmentX(Component.CENTER_ALIGNMENT);

    panel.add(playerAvatar);
    panel.add(Box.createVerticalStrut(0));
    panel.add(cardPanel);
    return panel;
  }
}
