package controller;

import controller.PlayerVsAIController;
import model.BlackjackGame;
import model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import view.PlayerVsAIFrame;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link PlayerVsAIController}.
 * Tests focus on the correctness of game state transitions, UI interactions, and AI behavior.
 */
public class PlayerVsAIControllerTest {

  private PlayerVsAIFrame mockView;
  private PlayerVsAIController controller;

  /**
   * Sets up a mock view and controller before each test.
   */
  @BeforeEach
  void setUp() {
    mockView = new PlayerVsAIFrame();
    controller = new PlayerVsAIController(mockView);
  }

  /**
   * Verifies that after construction, action buttons are disabled.
   */
  @Test
  void testInitialUIState() {
    assertFalse(mockView.hitButton.isEnabled(), "Hit should be disabled initially");
    assertFalse(mockView.standButton.isEnabled(), "Stand should be disabled initially");
  }

  /**
   * Simulates flipping cards and verifies buttons and labels.
   */
  @Test
  void testRevealCards() {
    mockView.revealButton.doClick();
    assertTrue(mockView.hitButton.isEnabled(), "Hit should be enabled after reveal");
    assertEquals("Your Turn: Hit or Stand", mockView.statusLabel.getText(), "Label should prompt player's move");
  }

  /**
   * Simulates player hit action and ensures hand grows.
   */
  @Test
  void testPlayerHit() {
    mockView.revealButton.doClick();  // must reveal first
    int before = controller.getGame().getPlayer().getHand().size();
    mockView.hitButton.doClick();
    int after = controller.getGame().getPlayer().getHand().size();
    assertTrue(after > before, "Hand should grow after Hit");
    assertTrue(mockView.playerPanel.getComponentCount() >= 2, "Card UI should reflect new hand");
  }

  /**
   * Simulates stand and ensures buttons are disabled.
   */
  @Test
  void testPlayerStand() {
    mockView.revealButton.doClick();  // must reveal first
    mockView.standButton.doClick();
    assertFalse(mockView.hitButton.isEnabled(), "Hit should be disabled after stand");
    assertEquals("AI Turn...", mockView.statusLabel.getText(), "Status should update to AI turn");
  }

  /**
   * Tests that reset clears the UI and enables reveal button.
   */
  @Test
  void testResetGame() {
    mockView.revealButton.doClick();
    mockView.hitButton.doClick();
    mockView.nextRoundButton.setEnabled(true);
    mockView.nextRoundButton.doClick();
    assertEquals("New Round! Click Flip to start.", mockView.statusLabel.getText());
    assertTrue(mockView.revealButton.isEnabled(), "Reveal should be re-enabled");
  }

  /**
   * Tests return to menu interaction closes frame and plays no audio.
   */
  @Test
  void testReturnToMenu() {
    mockView.returnToMenuButton.doClick();
    assertFalse(mockView.isVisible(), "Main game frame should be disposed");
  }

}
