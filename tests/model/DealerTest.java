package model;

import model.Card;
import model.Dealer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit5 tests for the Dealer class.
 * Verifies behavior specific to dealer visibility and card management.
 */
public class DealerTest {

  private Dealer dealer;

  /**
   * Initializes a new Dealer instance before each test.
   */
  @BeforeEach
  void setUp() {
    dealer = new Dealer();
  }

  /**
   * Tests if revealCard()  correctly sets the internal flag.
   * Since the flag is private, we use public behavior to confirm state indirectly.
   */
  @Test
  void testRevealCard() {
    // Indirect test: we can't access the flag, but we can ensure the method runs safely
    assertDoesNotThrow(() -> dealer.revealCard(), "Calling revealCard() should not throw");

    // Confirm no exception when revealing again
    dealer.revealCard();
    assertDoesNotThrow(() -> dealer.revealCard(), "Calling revealCard() twice should be safe");
  }

  /**
   * Tests if getVisibleCardValue() returns the correct
   * game value for the first card in the dealer's hand.
   */
  @Test
  void testGetVisibleCardValue() {
    dealer.addCard(new Card("Spades", 1));   // Ace = 1
    dealer.addCard(new Card("Hearts", 10));  // Hidden

    int visible = dealer.getVisibleCardValue();
    assertEquals(1, visible, "First card is Ace, so value should be 1");

    // Add a different first card
    dealer = new Dealer();
    dealer.addCard(new Card("Diamonds", 13)); // King = 10
    dealer.addCard(new Card("Clubs", 5));

    assertEquals(10, dealer.getVisibleCardValue(), "First card is King, value should be 10");
  }
}
