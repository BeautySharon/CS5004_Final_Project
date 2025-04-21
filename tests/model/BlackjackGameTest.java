package model;

import model.BlackjackGame;
import model.Card;
import model.Dealer;
import model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit5 tests for the BlackjackGame class.
 * Validates player/dealer interaction, state transitions, and game logic.
 */
public class BlackjackGameTest {

  private BlackjackGame game;

  /**
   * Initializes a fresh game before each test.
   */
  @BeforeEach
  void setUp() {
    game = new BlackjackGame();
  }

  /**
   * Tests if getter returns a non-null object
   * and that the player initially holds 2 cards.
   */
  @Test
  void testGetPlayer() {
    Player p = game.getPlayer();
    assertNotNull(p, "Player should not be null");
    assertEquals(2, p.getHand().size(), "Player should start with 2 cards");
  }

  /**
   * Tests if delear getter returns a valid dealer
   * and that the dealer is initialized with 2 cards.
   */
  @Test
  void testGetDealer() {
    Dealer d = game.getDealer();
    assertNotNull(d, "Dealer should not be null");
    assertEquals(2, d.getHand().size(), "Dealer should start with 2 cards");
  }

  /**
   * Tests that if opponent getter returns the dealer.
   */
  @Test
  void testGetOpponent() {
    assertEquals(game.getDealer(), game.getOpponent(), "Opponent should be the dealer");
    assertEquals(2, game.getOpponent().getHand().size(), "Opponent should have 2 cards");
  }

  /**
   * Tests if action hit adds exactly one card to the player’s hand.
   */
  @Test
  void testPlayerHit() {
    int before = game.getPlayer().getHand().size();
    game.playerHit();
    int after = game.getPlayer().getHand().size();
    assertEquals(before + 1, after, "Player hand should increase by 1 after hit");
    assertTrue(after > 2, "Player should have more than 2 cards after hit");
  }

  /**
   * Tests if hand string getter returns a valid string of hand cards.
   */
  @Test
  void testGetPlayerHandString() {
    String handStr = game.getPlayerHandString();
    assertNotNull(handStr, "Player hand string should not be null");
    assertTrue(handStr.contains("of"), "Hand string should describe cards");
  }

  /**
   * Tests if visible card string returns the visible card string.
   */
  @Test
  void testGetDealerVisibleCardString() {
    String dealerCard = game.getDealerVisibleCardString();
    assertNotNull(dealerCard, "Dealer card string should not be null");
    assertTrue(dealerCard.contains("of"), "Dealer visible card string should describe a card");
  }

  /**
   * Tests if reset correctly reinitializes the game state.
   */
  @Test
  void testReset() {
    game.playerHit();
    game.reset();
    assertEquals(2, game.getPlayer().getHand().size(), "Player should have 2 cards after reset");
    assertEquals(2, game.getDealer().getHand().size(), "Dealer should have 2 cards after reset");
  }

  /**
   * Tests if state getter returns a valid 3-element state.
   */
  @Test
  void testGetPlayerState() {
    int[] state = game.getPlayerState();
    assertEquals(3, state.length, "Player state should have 3 elements");
    assertTrue(state[0] >= 2 && state[0] <= 21, "Score should be within playable range");
  }

  /**
   * Tests if opponent's action to hit adds one card to the dealer's hand.
   */
  @Test
  void testOpponentHit() {
    int before = game.getDealer().getHand().size();
    game.opponentHit();
    int after = game.getDealer().getHand().size();
    assertEquals(before + 1, after, "Dealer hand should grow by 1 after hit");
    assertTrue(after > 2, "Dealer should have more than 2 cards after hit");
  }

  /**
   * Tests if opponent state getter returns a valid 3-element state.
   */
  @Test
  void testGetOpponentState() {
    int[] state = game.getOpponentState();
    assertEquals(3, state.length, "Opponent state should have 3 elements");
    assertTrue(state[0] >= 2 && state[0] <= 21, "Opponent score should be within valid range");
  }
}
