package view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit5 tests for the PlayerVsAIFrame class.
 * Validates the construction of UI components and their properties.
 */
public class PlayerVsAIFrameTest {

  private PlayerVsAIFrame frame;

  @BeforeEach
  void setUp() {
    frame = new PlayerVsAIFrame();
  }

  /**
   * Tests that the status label is initialized correctly.
   */
  @Test
  void testStatusLabel() {
    assertNotNull(frame.statusLabel, "Status label should be initialized");
    assertEquals("\u2660 Welcome to ACE - Dare to beat the AI dealer? \u2660", frame.statusLabel.getText(), "Status label should have welcome message");
  }

  /**
   * Tests the player card panel is initialized with 2 hidden cards.
   */
  @Test
  void testPlayerPanel() {
    assertNotNull(frame.playerPanel, "Player panel should be initialized");
    assertEquals(2, frame.playerPanel.getComponentCount(), "Player panel should have 2 cards");
  }

  /**
   * Tests the AI (dealer) card panel initialization.
   */
  @Test
  void testAIPanel() {
    assertNotNull(frame.aiPanel, "AI panel should be initialized");
    assertEquals(2, frame.aiPanel.getComponentCount(), "AI panel should have 2 cards");
  }

  /**
   * Tests the reveal button is styled and functional.
   */
  @Test
  void testRevealButton() {
    assertEquals("Flip", frame.revealButton.getText(), "Reveal button should say 'Flip'");
    assertEquals(new Dimension(130, 40), frame.revealButton.getPreferredSize(), "Reveal button should have correct size");
  }

  /**
   * Tests the hit button properties.
   */
  @Test
  void testHitButton() {
    assertEquals("Hit", frame.hitButton.getText(), "Hit button should say 'Hit'");
    assertNotNull(frame.hitButton.getActionListeners(), "Hit button should have listeners after binding");
  }

  /**
   * Tests the stand button properties.
   */
  @Test
  void testStandButton() {
    assertEquals("Stand", frame.standButton.getText(), "Stand button should say 'Stand'");
    assertEquals(new Dimension(130, 40), frame.standButton.getPreferredSize(), "Stand button should have correct size");
  }

  /**
   * Tests the next round button.
   */
  @Test
  void testNextRoundButton() {
    assertEquals("Next Round", frame.nextRoundButton.getText(), "Next round button should say 'Next Round'");
    assertTrue(frame.nextRoundButton instanceof JButton, "Next round button should be a JButton");
  }

  /**
   * Tests the return to menu button.
   */
  @Test
  void testReturnToMenuButton() {
    assertEquals("Return to Menu", frame.returnToMenuButton.getText(), "Return button should say 'Return to Menu'");
    assertTrue(frame.returnToMenuButton instanceof JButton, "Return to menu should be a JButton");
  }
} 
