package model;

import model.Card;
import model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit5 tests for Player class.
 * Verifies card management, hand value calculations,
 * bust detection, and scoring logic.
 */
public class PlayerTest {

  private Player player;

  /**
   * Sets up a fresh Player instance before each test.
   */
  @BeforeEach
  void setUp() {
    player = new Player();
  }

  /**
   * Tests that cards are correctly added to the hand.
   * Verifies hand size and content.
   */
  @Test
  void testAddCard() {
    Card card = new Card("Hearts", 10);
    player.addCard(card);
    List<Card> hand = player.getHand();
    assertEquals(1, hand.size(), "Hand should contain one card");
    assertEquals(10, hand.get(0).getValue(), "Card value should match");
  }

  /**
   * Tests that getHand returns all added cards in correct order.
   */
  @Test
  void testGetHand() {
    player.addCard(new Card("Clubs", 2));
    player.addCard(new Card("Diamonds", 5));
    List<Card> hand = player.getHand();
    assertEquals(2, hand.size(), "Hand size should be 2");
    assertEquals(2, hand.get(0).getValue(), "First card should be value 2");
  }

  /**
   * Tests hand value calculation with and without a usable ace.
   */
  @Test
  void testGetHandValue() {
    player.addCard(new Card("Spades", 1));  // Ace
    player.addCard(new Card("Hearts", 6));
    int[] value = player.getHandValue();
    assertEquals(17, value[0], "Hand value should consider usable ace as 11");
    assertEquals(1, value[1], "Usable ace should be reported as 1");

    player.addCard(new Card("Clubs", 10)); // Now ace can't be 11
    int[] value2 = player.getHandValue();
    assertEquals(17, value2[0], "Hand value should drop ace to 1 if bust otherwise");
    assertEquals(0, value2[1], "Usable ace should now be 0");
  }

  /**
   * Tests that the player is correctly marked as busted when value > 21.
   */
  @Test
  void testIsBusted() {
    player.addCard(new Card("Hearts", 10));
    player.addCard(new Card("Diamonds", 10));
    player.addCard(new Card("Clubs", 5));
    assertTrue(player.isBusted(), "Player should be busted with total > 21");

    player = new Player();
    player.addCard(new Card("Hearts", 10));
    player.addCard(new Card("Spades", 9));
    assertFalse(player.isBusted(), "Player should not be busted under 21");
  }

  /**
   * Tests calculateScore for consistency with getHandValue.
   */
  @Test
  void testCalculateScore() {
    player.addCard(new Card("Spades", 1));  // Ace
    player.addCard(new Card("Clubs", 9));
    assertEquals(20, player.calculateScore(), "Score should use usable ace as 11");

    player.addCard(new Card("Diamonds", 5));  // now ace should become 1
    assertEquals(15, player.calculateScore(), "Score should drop ace if total > 21");
  }
}
