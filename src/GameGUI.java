import javax.swing.*;
import java.awt.*;

public class GameGUI extends JFrame {
  private final BlackjackGame game;
  private final AIAdvisor aiAdvisor;
  private final JLabel playerHandLabel;
  private final JLabel dealerCardLabel;
  private final JLabel feedbackLabel;
  private final int[] initialState = new int[3]; // [playerTotal, dealerCard, usableAce]
  private JButton hitButton;
  private JButton standButton;

  public GameGUI() {
    game = new BlackjackGame();
    aiAdvisor = new AIAdvisor("q_table.json");

    setTitle("Blackjack Q-Learning Trainer");
    setSize(500, 300);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setLayout(new GridLayout(6, 1));

    // Labels
    playerHandLabel = new JLabel("Your Hand: " + game.getPlayerHandString());
    dealerCardLabel = new JLabel("Dealer Shows: " + game.getDealerVisibleCardString());
    feedbackLabel = new JLabel("Choose your move:");

    // Buttons
    hitButton = new JButton("Hit");
    standButton = new JButton("Stand");

    hitButton.addActionListener(e -> makeMove("Hit"));
    standButton.addActionListener(e -> makeMove("Stand"));

    add(playerHandLabel);
    add(dealerCardLabel);
    add(feedbackLabel);
    add(hitButton);
    add(standButton);

    // Next Round button
    JButton nextButton = new JButton("Next Round");
    nextButton.addActionListener(e -> {
      dispose(); // close current window
      new GameGUI().setVisible(true); // open a new one
    });
    add(nextButton);

    // ✅ Save the initial state AFTER components render
    SwingUtilities.invokeLater(() -> {
      int[] state = game.getPlayerState();
      initialState[0] = state[0];
      initialState[1] = state[1];
      initialState[2] = state[2];
      System.out.println("📊 Initial state: " + state[0] + ", " + state[1] + ", " + state[2]);
    });
  }

  private void makeMove(String userAction) {
    System.out.println("🧪 Checking state: " + initialState[0] + ", " + initialState[1] + ", " + initialState[2]);
    String aiSuggestion = aiAdvisor.getOptimalAction(
        initialState[0], initialState[1], initialState[2]
    );

    if (aiSuggestion.equals("Unknown")) {
      feedbackLabel.setText("❓ No Q-table entry for this state.");
    } else {
      String result = userAction.equalsIgnoreCase(aiSuggestion)
          ? "✅ Correct! Optimal is: " + aiSuggestion
          : "❌ Wrong! Optimal is: " + aiSuggestion;
      feedbackLabel.setText(result);
    }

    hitButton.setEnabled(false);
    standButton.setEnabled(false);
  }

  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
      GameGUI gui = new GameGUI();
      gui.setVisible(true);
    });
  }
}
