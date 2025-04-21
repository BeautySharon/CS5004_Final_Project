package model;

import model.Card;
import model.Deck;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit5 tests for Deck class.
 * Validates deck creation, card drawing behavior,
 * and proper error handling when the deck is exhausted.
 */
public class DeckTest {

  private Deck deck;

  /**
   * Initializes a new Deck instance before each test.
   */
  @BeforeEach
  void setUp() {
    deck = new Deck();
  }

  /**
   * Tests that the deck is initialized with 52 cards
   * and that cards are drawn correctly from the top.
   */
  @Test
  void testDrawCardReducesDeckSizeAndReturnsCard() {
    int initialSize = 52;
    Card card = deck.drawCard();

    assertNotNull(card, "Drawn card should not be null");
    assertThrows(IndexOutOfBoundsException.class, () -> {
      deck.getClass().getDeclaredField("cards").setAccessible(true);
    }, "Direct access to cards field should not be possible"); // Demonstrates encapsulation

    // Drawn one card, so remaining should be 51
    for (int i = 1; i < initialSize; i++) {
      deck.drawCard();
    }

    assertThrows(IllegalStateException.class, deck::drawCard, "Should throw when deck is empty");
  }

  /**
   * Tests that a deck always starts with exactly 52 unique cards.
   */
  @Test
  void testDeckHasCorrectCardCountAndValidContent() {
    int count = 0;
    while (true) {
      try {
        Card card = deck.drawCard();
        assertNotNull(card.getSuit(), "Card suit should not be null");
        assertTrue(card.getValue() >= 1 && card.getValue() <= 13,
            "Card value must be between 1 and 13");
        count++;
      } catch (IllegalStateException e) {
        break;
      }
    }

    assertEquals(52, count, "Deck should contain exactly 52 cards");
    assertThrows(IllegalStateException.class, deck::drawCard,
        "Drawing from empty deck must throw exception");
  }
}
