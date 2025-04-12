// GameGUI.java
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TrainingMode extends JFrame {
  private final BlackjackGame game;
  private final AIAdvisor aiAdvisor;
  private final JLabel feedbackLabel;
  private final JPanel feedbackPanel;
  private final JPanel gameAreaPanel;
  private final int[] initialState = new int[3];
  private JButton hitButton;
  private JButton standButton;
  private JButton revealButton;
  private JButton nextButton;
  private JButton returnButton;
  private JLabel secondCardLabel;
  private JPanel dealerCardPanel;
  private JPanel playerCardPanel;
  private final AudioPlayer bgmPlayer = new AudioPlayer();

  public TrainingMode() {
    game = new BlackjackGame();
    aiAdvisor = new AIAdvisor("q_table.json");
    bgmPlayer.playLoop("assets/bg_music.wav");

    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    int width = (int) (screenSize.width * 0.9);
    int height = (int) (screenSize.height * 0.9);
    setSize(width, height);
    setResizable(false);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    addWindowListener(new java.awt.event.WindowAdapter() {
      public void windowClosing(java.awt.event.WindowEvent e) {
        bgmPlayer.stop();
      }
    });
    setTitle("ACE (AI Card Expert)");

    BackgroundPanel content = new BackgroundPanel("assets/table_background.jpg");
    content.setLayout(new BorderLayout());
    setContentPane(content);

    feedbackPanel = new JPanel(new BorderLayout());
    feedbackPanel.setOpaque(true);
    feedbackPanel.setBackground(new Color(255, 255, 255, 200));
    feedbackPanel.setPreferredSize(new Dimension(800, 40));
    feedbackPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

    feedbackLabel = new JLabel("Welcome to ACE training mode!", SwingConstants.CENTER);
    feedbackLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
    feedbackLabel.setForeground(Color.BLACK);
    feedbackLabel.setPreferredSize(new Dimension(800, 60));
    feedbackPanel.add(feedbackLabel, BorderLayout.CENTER);
    content.add(feedbackPanel, BorderLayout.SOUTH);

    gameAreaPanel = new JPanel();
    gameAreaPanel.setOpaque(false);
    gameAreaPanel.setLayout(new BoxLayout(gameAreaPanel, BoxLayout.Y_AXIS));
    content.add(gameAreaPanel, BorderLayout.CENTER);

    JPanel dealerPanel = new JPanel();
    dealerPanel.setOpaque(false);
    dealerPanel.setLayout(new BoxLayout(dealerPanel, BoxLayout.Y_AXIS));
    dealerPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
    ImageIcon rawDealerIcon = new ImageIcon("assets/dealer_avatar.jpg");
    Image dealerScaled = rawDealerIcon.getImage().getScaledInstance(125, 160, Image.SCALE_SMOOTH);
    JLabel dealerIcon = new JLabel(new ImageIcon(dealerScaled));
    dealerPanel.add(Box.createVerticalStrut(10));
    dealerPanel.add(dealerIcon);
    dealerPanel.add(Box.createVerticalStrut(10));
    dealerCardPanel = createHiddenCardPanel(2);
    dealerPanel.add(dealerCardPanel);
    gameAreaPanel.add(dealerPanel);

    JPanel playerPanel = new JPanel();
    playerPanel.setOpaque(false);
    playerPanel.setLayout(new BoxLayout(playerPanel, BoxLayout.Y_AXIS));
    playerPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
    ImageIcon rawPlayerIcon = new ImageIcon("assets/player_avatar.jpg");
    Image playerScaled = rawPlayerIcon.getImage().getScaledInstance(116, 160, Image.SCALE_SMOOTH);
    JLabel playerIcon = new JLabel(new ImageIcon(playerScaled));
    playerPanel.add(Box.createVerticalStrut(10));
    playerPanel.add(playerIcon);
    playerPanel.add(Box.createVerticalStrut(10));
    playerCardPanel = createHiddenCardPanel(2);
    playerPanel.add(playerCardPanel);
    gameAreaPanel.add(playerPanel);

    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
    buttonPanel.setOpaque(false);

    revealButton = new JButton("Flip Cards");
    hitButton = new JButton("Hit");
    standButton = new JButton("Stand");
    nextButton = new JButton("Next Round");
    returnButton = new JButton("Return Menu");

    Dimension btnSize = new Dimension(120, 40);
    revealButton.setPreferredSize(btnSize);
    hitButton.setPreferredSize(btnSize);
    standButton.setPreferredSize(btnSize);
    nextButton.setPreferredSize(btnSize);
    returnButton.setPreferredSize(btnSize);

    hitButton.setEnabled(false);
    standButton.setEnabled(false);

    revealButton.addActionListener(e -> {
      revealPlayerCards(game.getPlayer().getHand());
      revealDealerCard(game.getDealer().getHand().get(0));
      revealButton.setEnabled(false);
      hitButton.setEnabled(true);
      standButton.setEnabled(true);
    });
    hitButton.addActionListener(e -> makeMove("Hit"));
    standButton.addActionListener(e -> makeMove("Stand"));
    nextButton.addActionListener(e -> resetGame());
    returnButton.addActionListener(e -> {
      bgmPlayer.stop();
      dispose();
      new MainMenu().setVisible(true);
    });

    buttonPanel.add(revealButton);
    buttonPanel.add(hitButton);
    buttonPanel.add(standButton);
    buttonPanel.add(nextButton);
    buttonPanel.add(returnButton);

    gameAreaPanel.add(buttonPanel);


    gameAreaPanel.add(buttonPanel);

    SwingUtilities.invokeLater(() -> {
      int[] state = game.getPlayerState();
      initialState[0] = state[0];
      initialState[1] = state[1];
      initialState[2] = state[2];
      revalidate();
      repaint();
    });
  }

  private void resetGame() {
    game.reset();

    hitButton.setEnabled(false);
    standButton.setEnabled(false);
    revealButton.setEnabled(true);

    resetHiddenCardsTo(dealerCardPanel, 2);
    resetHiddenCardsTo(playerCardPanel, 2);

    feedbackLabel.setText("New Round Started! Click Flip Cards!");
    feedbackLabel.setForeground(Color.BLACK);

    int[] state = game.getPlayerState();
    initialState[0] = state[0];
    initialState[1] = state[1];
    initialState[2] = state[2];
  }

  private void resetHiddenCardsTo(JPanel targetPanel, int count) {
    targetPanel.removeAll();
    for (int i = 0; i < count; i++) {
      ImageIcon backIcon = new ImageIcon("assets/cards/back1.png");
      Image scaledBack = backIcon.getImage().getScaledInstance(111, 162, Image.SCALE_SMOOTH);
      JLabel cardLabel = new JLabel(new ImageIcon(scaledBack));
      cardLabel.setPreferredSize(new Dimension(111, 162));
      cardLabel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
      cardLabel.setOpaque(true);
      cardLabel.setBackground(Color.WHITE);
      targetPanel.add(cardLabel);
    }
    targetPanel.revalidate();
    targetPanel.repaint();
  }


  private JPanel createCardPanel(List<Card> cards) {
    JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
    panel.setOpaque(false);
    for (Card c : cards) {
      String imagePath = c.getImagePath();
      ImageIcon icon = new ImageIcon(imagePath);
      Image scaled = icon.getImage().getScaledInstance(111, 162, Image.SCALE_SMOOTH);
      JLabel cardLabel = new JLabel(new ImageIcon(scaled));
      cardLabel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
      cardLabel.setOpaque(true);
      cardLabel.setBackground(Color.WHITE);
      panel.add(cardLabel);
    }
    return panel;
  }

  private JPanel createHiddenCardPanel(int count) {
    JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
    panel.setOpaque(false);
    for (int i = 0; i < count; i++) {
      ImageIcon backIcon = new ImageIcon("assets/cards/back1.png");
      Image scaledBack = backIcon.getImage().getScaledInstance(111, 162, Image.SCALE_SMOOTH);
      JLabel cardLabel = new JLabel(new ImageIcon(scaledBack));
      cardLabel.setPreferredSize(new Dimension(111, 162));
      cardLabel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
      cardLabel.setOpaque(true);
      cardLabel.setBackground(Color.WHITE);
      panel.add(cardLabel);
    }
    return panel;
  }

  private void revealPlayerCards(List<Card> cards) {
    playerCardPanel.removeAll();
    for (Card c : cards) {
      ImageIcon icon = new ImageIcon(c.getImagePath());
      Image scaled = icon.getImage().getScaledInstance(111, 162, Image.SCALE_SMOOTH);
      JLabel cardLabel = new JLabel(new ImageIcon(scaled));
      cardLabel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
      cardLabel.setOpaque(true);
      cardLabel.setBackground(Color.WHITE);
      playerCardPanel.add(cardLabel);
    }
    playerCardPanel.revalidate();
    playerCardPanel.repaint();
  }

  private void revealDealerCard(Card visibleCard) {
    dealerCardPanel.removeAll();
    ImageIcon icon = new ImageIcon(visibleCard.getImagePath());
    Image scaled = icon.getImage().getScaledInstance(111, 162, Image.SCALE_SMOOTH);
    JLabel firstCard = new JLabel(new ImageIcon(scaled));
    firstCard.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
    firstCard.setOpaque(true);
    firstCard.setBackground(Color.WHITE);
    dealerCardPanel.add(firstCard);

    ImageIcon backIcon = new ImageIcon("assets/cards/back1.png");
    Image back = backIcon.getImage().getScaledInstance(111, 162, Image.SCALE_SMOOTH);
    secondCardLabel = new JLabel(new ImageIcon(back));
    secondCardLabel.setPreferredSize(new Dimension(111, 162));
    secondCardLabel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
    secondCardLabel.setOpaque(true);
    secondCardLabel.setBackground(Color.WHITE);
    dealerCardPanel.add(secondCardLabel);

    dealerCardPanel.revalidate();
    dealerCardPanel.repaint();
  }

  private void revealDealerSecondCard(Card realCard) {
    SwingUtilities.invokeLater(() -> {
      Timer timer = new Timer(500, e -> {
        ImageIcon realIcon = new ImageIcon(realCard.getImagePath());
        Image scaled = realIcon.getImage().getScaledInstance(111, 162, Image.SCALE_SMOOTH);
        secondCardLabel.setIcon(new ImageIcon(scaled));
        secondCardLabel.setOpaque(true);
        secondCardLabel.setBackground(Color.WHITE);
        secondCardLabel.revalidate();
        secondCardLabel.repaint();
        TrainingMode.this.revalidate();
        TrainingMode.this.repaint();
      });
      timer.setRepeats(false);
      timer.start();
    });
  }

  private void makeMove(String userAction) {
    System.out.println("🧪 Checking state: " + initialState[0] + ", " + initialState[1] + ", " + initialState[2]);
    String aiSuggestion = aiAdvisor.getOptimalAction(
        initialState[0], initialState[1], initialState[2]);

    SwingUtilities.invokeLater(() -> {
      if (aiSuggestion.equals("Unknown")) {
        feedbackLabel.setText("❓ No Q-table entry for this state.");
        feedbackLabel.setForeground(Color.ORANGE);
      } else {
        if (userAction.equalsIgnoreCase(aiSuggestion)) {
          feedbackLabel.setText("✅ Correct! Optimal is: " + aiSuggestion);
          feedbackLabel.setForeground(Color.GREEN.darker());
        } else {
          feedbackLabel.setText("❌ Wrong! Optimal is: " + aiSuggestion);
          feedbackLabel.setForeground(Color.RED);
        }
      }
      feedbackPanel.revalidate();
      feedbackPanel.repaint();
    });

    hitButton.setEnabled(false);
    standButton.setEnabled(false);

    List<Card> dealerCards = game.getDealer().getHand();
    if (dealerCards.size() > 1) {
      revealDealerSecondCard(dealerCards.get(1));
    }
  }

  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
      TrainingMode gui = new TrainingMode();
      gui.setVisible(true);
    });
  }
}