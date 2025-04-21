package controller;

import model.*;
import java.util.List;
import view.MainMenu;
import view.TrainingModeView;

/**
 * Controller class for the Training Mode of the Blackjack game.
 * Connects the TrainingModeView (GUI) with the game logic in the model.
 * Handles user interactions, updates the view based on game state,
 * and compares player actions with AIAdvisor recommendations.
 */
public class TrainingModeController {

  /** Model instance representing the core Blackjack game logic */
  private final BlackjackGame game;

  /** AI decision maker, loaded from Q-table */
  private final AIAdvisor aiAdvisor;

  /** View instance for the training mode */
  private final TrainingModeView view;

  private final int[] initialState = new int[3]; // Player total, dealer upcard, usable ace
  /** Audio player for background music */
  private final AudioPlayer bgmPlayer = new AudioPlayer();

  /**
   * Constructs the controller with a reference to the view.
   * Initializes the game and AI advisor, sets up button listeners.
   * Does not start the first round immediately to preserve welcome message.
   *
   * @param view the TrainingModeView instance to be controlled
   */
  public TrainingModeController(TrainingModeView view) {
    this.view = view;
    this.game = new BlackjackGame();
    this.aiAdvisor = new AIAdvisor("q_table.json");
    bgmPlayer.playLoop("assets/bg_music.wav");
    initListeners();
  }

  /**
   * Sets up action listeners for all the buttons in the view.
   * Handles Flip, Hit, Stand, Next Round, and Return to Menu actions.
   */
  private void initListeners() {
    view.revealButton.addActionListener(e -> {
      resetGame();  // Start a new round here (first time or next round)
      //view.showFeedback("New Round Started! Click Flip!");

      view.updatePlayerCards(game.getPlayer().getHand());
      view.updateDealerCard(game.getDealer().getHand().get(0));
      view.showFeedback("Your Turn: Hit or Stand");
      view.revealButton.setEnabled(false);
      view.hitButton.setEnabled(true);
      view.standButton.setEnabled(true);
    });

    view.hitButton.addActionListener(e -> makeMove("Hit"));
    view.standButton.addActionListener(e -> makeMove("Stand"));

    view.nextButton.addActionListener(e -> {
      view.showFeedback("New Round Started! Click Flip!");
      view.revealButton.setEnabled(true);
      view.hitButton.setEnabled(false);
      view.standButton.setEnabled(false);
    });

    view.returnButton.addActionListener(e -> {
      bgmPlayer.stop();
      view.dispose();
      new MainMenu().setVisible(true);
    });
  }

  /**
   * Resets the game to a new round:
   * - Resets the model
   * - Updates the card panels with back cards
   * - Updates the internal state for AI comparison
   */
  private void resetGame() {
    game.reset();
    view.resetGameView();  // shows back-side cards
    System.arraycopy(game.getPlayerState(), 0, initialState, 0, 3);
  }

  /**
   * Makes a move ("Hit" or "Stand") and compares the user's action
   * against the AI advisor's optimal decision.
   * Displays visual feedback accordingly and reveals dealer's second card.
   *
   * @param action the player's chosen action ("Hit" or "Stand")
   */
  void makeMove(String action) {
    String ai = aiAdvisor.getOptimalAction(initialState[0], initialState[1], initialState[2]);

    if (ai.equals("Unknown")) {
      view.showFeedback("No Q-table entry for this state.");
    } else if (ai.equalsIgnoreCase(action)) {
      view.showFeedback("Correct! Optimal is: " + ai);
    } else {
      view.showFeedback("Wrong! Optimal is: " + ai);
    }

    view.hitButton.setEnabled(false);
    view.standButton.setEnabled(false);

    List<Card> dealerCards = game.getDealer().getHand();
    if (dealerCards.size() > 1)
      view.updateDealerSecondCard(dealerCards.get(1));
  }
}
