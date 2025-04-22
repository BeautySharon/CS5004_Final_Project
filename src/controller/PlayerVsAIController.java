package controller;
import java.awt.event.ActionEvent;
import javax.swing.*;
import model.AIAdvisor;
import model.AudioPlayer;
import model.BlackjackGame;
import view.GamePanelBuilder;
import view.MainMenu;
import view.PlayerVsAIFrame;

/**
 * Controller class for Player vs AI Blackjack game mode.
 * Handles user interactions, updates game state, and refreshes the UI accordingly.
 * Separates control logic from view and model layers in MVC architecture.
 */
public class PlayerVsAIController {

  /** View instance for Player vs AI mode */
  private final PlayerVsAIFrame view;

  /** Model instance representing the core Blackjack game logic */
  private final BlackjackGame game = new BlackjackGame();

  /** AI decision maker, loaded from Q-table */
  private final AIAdvisor aiAdvisor = new AIAdvisor("q_table.json");

  /** Audio player for background music */
  private final AudioPlayer bgmPlayer = new AudioPlayer();

  /**
   * Constructs a controller with reference to the game view.
   *
   * @param view the {@code PlayerVsAIFrame} instance to bind UI and actions to
   */
  public PlayerVsAIController(PlayerVsAIFrame view) {
    this.view = view;
    bindActions();
    updateInitialUI();
    bgmPlayer.playLoop("assets/bg_music.wav");
  }

  /**
   * Binds UI buttons to their corresponding controller actions.
   */
  private void bindActions() {
    view.revealButton.addActionListener(e -> revealCards());
    view.hitButton.addActionListener(e -> playerHit());
    view.standButton.addActionListener(e -> playerStand());
    view.nextRoundButton.addActionListener(e -> resetGame());
    view.returnToMenuButton.addActionListener(e -> returnToMenu());
  }

  /**
   * Disables player actions and prepares the UI for a new round.
   */
  private void updateInitialUI() {
    view.hitButton.setEnabled(false);
    view.standButton.setEnabled(false);
    view.nextRoundButton.setEnabled(false);
  }

  /**
   * Reveals the initial player and dealer cards.
   * Enables player options (Hit / Stand) after revealing.
   */
  private void revealCards() {
    view.revealButton.setEnabled(false);

    GamePanelBuilder.updatePanel(game.getPlayer().getHand(), view.playerPanel);

    view.aiPanel.removeAll();
    view.aiPanel.add(GamePanelBuilder.createCardLabel(game.getDealer().getHand().get(0).getImagePath()));
    view.aiPanel.add(GamePanelBuilder.createCardLabel("assets/cards/back1.png"));
    view.aiPanel.revalidate();
    view.aiPanel.repaint();

    int playerScore = game.getPlayer().calculateScore();

    if (playerScore == 21) {
      endRound("Blackjack! You win. 🎉");
    } else {
      view.hitButton.setEnabled(true);
      view.standButton.setEnabled(true);
      view.statusLabel.setText("Your Turn: Hit or Stand");
    }
  }


  /**
   * Performs a "Hit" action for the player, and ends the round if player busts.
   */
  private void playerHit() {
    game.playerHit();
    GamePanelBuilder.updatePanel(game.getPlayer().getHand(), view.playerPanel);
    if (game.getPlayer().isBusted()) {
      endRound("You busted! AI wins.");
    }
  }

  /**
   * Performs the "Stand" action, starts AI's turn, and invokes AI move logic.
   */
  private void playerStand() {
    disablePlayerButtons();
    view.statusLabel.setText("AI Turn...");
    revealAllAI();
    handleAIMove();
  }

  /**
   * Handles AI's decision-making using Q-table, recursively hits if needed.
   * Uses a timer to simulate AI thinking delay.
   */
  /**
   * Handles AI's decision using standard Blackjack dealer rules:
   * Dealer hits if score < 17, otherwise stands.
   */
  private void handleAIMove() {
    Timer aiTimer = new Timer(1200, null);
    aiTimer.addActionListener(new AbstractAction() {
      public void actionPerformed(ActionEvent e) {
        int dealerScore = game.getDealer().calculateScore();
        if (dealerScore < 17) {
          game.opponentHit();
          GamePanelBuilder.updatePanel(game.getDealer().getHand(), view.aiPanel);
          if (game.getDealer().isBusted()) {
            aiTimer.stop();
            endRound("AI busted! You win.");
          }
        } else {
          aiTimer.stop();
          determineWinner();
        }
      }
    });
    aiTimer.setRepeats(true);
    aiTimer.start();
  }

  /**
   * Compares scores and sets the result message accordingly.
   * Called when both sides have completed their turns.
   */
  private void determineWinner() {
    int playerScore = game.getPlayer().calculateScore();
    int aiScore = game.getDealer().calculateScore();
    String result = (playerScore > 21) ? "You busted! AI wins." :
        (aiScore > 21) ? "AI busted! You win." :
            (playerScore > aiScore) ? "You win! 🎉" :
                (aiScore > playerScore) ? "AI wins." : "It's a tie!";
    endRound(result);
  }

  /**
   * Ends the round, reveals AI hand, updates result label, and enables Next Round button.
   *
   * @param result result message to show in status label
   */
  private void endRound(String result) {
    view.statusLabel.setText(result);
    revealAllAI();
    disablePlayerButtons();
    view.nextRoundButton.setEnabled(true);
  }

  /**
   * Disables player action buttons (Hit / Stand).
   */
  private void disablePlayerButtons() {
    view.hitButton.setEnabled(false);
    view.standButton.setEnabled(false);
  }

  /**
   * Reveals all dealer cards by updating the dealer panel.
   */
  private void revealAllAI() {
    GamePanelBuilder.updatePanel(game.getDealer().getHand(), view.aiPanel);
  }

  /**
   * Resets the game state and UI for a new round.
   */
  private void resetGame() {
    game.reset();
    view.playerPanel.removeAll();
    view.aiPanel.removeAll();
    view.playerPanel.add(GamePanelBuilder.createCardLabel("assets/cards/back1.png"));
    view.playerPanel.add(GamePanelBuilder.createCardLabel("assets/cards/back1.png"));
    view.aiPanel.add(GamePanelBuilder.createCardLabel("assets/cards/back1.png"));
    view.aiPanel.add(GamePanelBuilder.createCardLabel("assets/cards/back1.png"));
    updateInitialUI();
    view.revealButton.setEnabled(true);
    view.statusLabel.setText("New Round! Click Flip to start.");
    view.revalidate();
    view.repaint();
  }

  /**
   * Closes the game window and returns to the main menu.
   */
  private void returnToMenu() {
    bgmPlayer.stop();
    view.dispose();
    new MainMenu().setVisible(true);
  }

  public BlackjackGame getGame() {
    return game;
  }
}
