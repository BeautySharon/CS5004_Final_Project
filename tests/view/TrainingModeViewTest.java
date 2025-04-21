package view;

import model.Card;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit5 test class for the TrainingModeView class.
 * Tests GUI updates for player and dealer panels, and feedback messages.
 */
public class TrainingModeViewTest {

  private TrainingModeView view;

  @BeforeEach
  void setup() {
    view = new TrainingModeView();
  }

  /**
   * Tests the showFeedback method.
   * Verifies that the feedback message and label color are updated.
   */
  @Test
  void testShowFeedback() {
    view.showFeedback("Great move!");
    assertEquals("Great move!", view.statusLabel.getText());
    assertEquals(Color.WHITE, view.statusLabel.getForeground());
  }

  /**
   * Tests updatePlayerCards with a sample list of cards.
   * Ensures the player panel is updated with the correct number of card components.
   */
  @Test
  void testUpdatePlayerCards() {
    Card c1 = new Card("Spades", 10);
    Card c2 = new Card("Hearts", 5);
    view.updatePlayerCards(List.of(c1, c2));

    assertEquals(2, view.playerCardPanel.getComponentCount());
    assertTrue(view.playerCardPanel.getComponent(0) instanceof JLabel);
  }

  /**
   * Tests updateDealerCard with a sample visible card.
   * Ensures one visible and one hidden card are shown in dealer panel.
   */
  @Test
  void testUpdateDealerCard() {
    Card visible = new Card("Diamonds", 7);
    view.updateDealerCard(visible);

    assertEquals(2, view.dealerCardPanel.getComponentCount());
    assertTrue(view.dealerCardPanel.getComponent(0) instanceof JLabel);
  }

  /**
   * Tests updateDealerSecondCard by injecting a second card into dealer panel.
   * Verifies that the second label's icon is updated.
   */
  @Test
  void testUpdateDealerSecondCard() {
    // Manually simulate a dealer card panel with 2 face-down cards
    view.updateDealerCard(new Card("Spades", 8));
    Card second = new Card("Clubs", 12);
    view.updateDealerSecondCard(second);

    JLabel secondLabel = (JLabel) view.dealerCardPanel.getComponent(1);
    assertNotNull(secondLabel.getIcon());
    assertTrue(secondLabel.getIcon() instanceof ImageIcon);
  }

  /**
   * Tests resetGameView resets both card panels to 2 hidden cards each.
   */
  @Test
  void testResetGameView() {
    view.resetGameView();
    assertEquals(2, view.playerCardPanel.getComponentCount());
    assertEquals(2, view.dealerCardPanel.getComponentCount());
  }
}
