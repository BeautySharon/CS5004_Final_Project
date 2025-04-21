package model;

import model.Card;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit5 tests for the Card class.
 * Verifies card properties, string representations, and image file paths.
 */
public class CardTest {

  /**
   * Tests if getter returns expected value.
   */
  @Test
  void testGetValue() {
    Card card = new Card("Hearts", 7);
    assertEquals(7, card.getValue(), "Value should match constructor input");
    assertTrue(card.getValue() >= 1 && card.getValue() <= 13, "Card value should be within range");
  }

  /**
   * Tests if suit is evaluated correctly.
   */
  @Test
  void testGetSuit() {
    Card card = new Card("Spades", 10);
    assertEquals("Spades", card.getSuit(), "Suit should match constructor input");
    assertNotNull(card.getSuit(), "Suit should not be null");
  }

  /**
   * Tests if card value evaluates correctly.
   */
  @Test
  void testGetGameValue() {
    Card queen = new Card("Diamonds", 12);
    assertEquals(10, queen.getGameValue(), "Face card value should be 10");

    Card five = new Card("Clubs", 5);
    assertEquals(5, five.getGameValue(), "Non-face card should retain its value");
  }

  /**
   * Tests toString method.
   */
  @Test
  void testToString() {
    Card ace = new Card("Hearts", 1);
    String output = ace.toString();
    assertTrue(output.contains("1"), "Output should contain the card value");
    assertTrue(output.contains("Hearts"), "Output should contain the suit");
  }

  /**
   * Tests if file name is correct.
   */
  @Test
  void testGetImageFileName() {
    Card jack = new Card("Clubs", 11);
    assertEquals("jack_of_clubs.png", jack.getImageFileName(), "Should return correct file name for face card");

    Card nine = new Card("Spades", 9);
    assertEquals("9_of_spades.png", nine.getImageFileName(), "Should return correct file name for numbered card");
  }

  /**
   * Tests if image path correct.
   */
  @Test
  void testGetImagePath() {
    Card king = new Card("Diamonds", 13);
    String path = king.getImagePath();
    assertTrue(path.startsWith("assets/cards/"), "Path should begin with assets/cards/");
    assertTrue(path.endsWith("king_of_diamonds.png"), "Path should end with correct image filename");
  }
}
