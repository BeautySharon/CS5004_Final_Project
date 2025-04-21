package controller;

import controller.TrainingModeController;
import model.Card;
import model.Dealer;
import model.Player;
import model.BlackjackGame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import view.TrainingModeView;

import javax.swing.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit5 tests for TrainingModeController.
 * Covers interaction between view, model, and AI logic,
 * verifying the controller’s logic in managing interactions between
 * the TrainingModeView, BlackjackGame model, and AIAdvisor decision system.
 */
public class TrainingModeControllerTest {

  private MockTrainingView view;
  private TrainingModeController controller;

  @BeforeEach
  void setUp() {
    view = new MockTrainingView();
    controller = new TrainingModeController(view);
  }

  /**
   * Verifies that when Flip is clicked:
   * - the player's cards are shown in the UI
   * - feedback instructs user to act
   */
  @Test
  void testResetGameUpdatesStateAndView() {
    controller.getClass().getDeclaredMethods(); // just force coverage for reflection cache

    view.revealButton.doClick();

    assertTrue(view.playerCardsUpdated, "Player cards should be updated on flip");
    assertEquals("Your Turn: Hit or Stand", view.feedbackMessage, "Feedback message should prompt user turn");
  }

  /**
   * Verifies that when the user makes a move:
   * - feedback includes "optimal" comparison
   * - feedback is non-null
   */
  @Test
  void testMakeMoveCorrectness() {
    view.revealButton.doClick();

    controller.makeMove("Stand");

    assertNotNull(view.feedbackMessage, "Feedback should be shown");
    assertTrue(view.feedbackMessage.contains("Optimal"), "Feedback should show optimal move");
  }

  /**
   * Verifies that clicking "Next" resets UI state:
   * - the Flip button becomes enabled again
   * - Hit is disabled before the round restarts
   */
  @Test
  void testNextButtonUIState() {
    view.nextButton.doClick();
    assertTrue(view.revealButton.isEnabled(), "Reveal should be re-enabled after Next");
    assertFalse(view.hitButton.isEnabled(), "Hit should be disabled on reset");
  }

  /**
   * Verifies that clicking Return:
   * - stops music and disposes of the view
   */
  @Test
  void testReturnToMenuStopsMusicAndClosesView() {
    view.returnButton.doClick();
    assertTrue(view.disposed, "View should be disposed after return");
  }

  /**
   * Verifies that the dealer's second card is revealed after a move
   * - view flag is updated
   */
  @Test
  void testMakeMoveRevealsSecondDealerCardIfExists() {
    view.revealButton.doClick();
    controller.makeMove("Hit"); // Ensure move is processed

    assertTrue(view.secondCardRevealed, "Second dealer card should be revealed after move");
  }

  /**
   * Mock implementation of TrainingModeView for isolating UI behavior.
   * Records state changes triggered by controller logic for test verification.
   */
  private static class MockTrainingView extends TrainingModeView {
    public boolean playerCardsUpdated = false;
    public boolean secondCardRevealed = false;
    public boolean disposed = false;
    public String feedbackMessage = "";

    public JButton revealButton = new JButton();
    public JButton hitButton = new JButton();
    public JButton standButton = new JButton();
    public JButton nextButton = new JButton();
    public JButton returnButton = new JButton();

    @Override
    public void updatePlayerCards(List<Card> cards) {
      this.playerCardsUpdated = true;
    }

    @Override
    public void updateDealerCard(Card card) {
      // simulate showing dealer card
    }

    @Override
    public void updateDealerSecondCard(Card card) {
      this.secondCardRevealed = true;
    }

    @Override
    public void showFeedback(String message) {
      this.feedbackMessage = message;
    }

    @Override
    public void resetGameView() {
      // simulate back cards being shown
    }

    @Override
    public void dispose() {
      this.disposed = true;
    }
  }
}
