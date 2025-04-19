package model;
import java.util.*;

/**
 * Represents a generic player in a Blackjack game.
 * Manages the player's hand and provides methods for game-related evaluations
 * such as calculating hand value, checking for busts, and scoring.
 */
public class Player {

  /** The list of cards currently held by the player. */
  protected final List<Card> hand = new ArrayList<>();

  /**
   * Adds a card to the player's hand.
   *
   * @param c the card to be added
   */
  public void addCard(Card c) {
    hand.add(c);
  }

  /**
   * Returns the list of cards in the player's hand.
   *
   * @return the player's current hand
   */
  public List<Card> getHand() {
    return hand;
  }

  /**
   * Computes the total value of the player's hand.
   * Considers the special rule of a usable Ace (counted as 11 if it doesn't bust the hand).
   *
   * @return an array where:
   *         - index 0 is the total hand value (with usable ace as 11 if possible),
   *         - index 1 is 1 if a usable ace is used, otherwise 0
   */
  public int[] getHandValue() {
    int sum = 0;
    int aceCount = 0;
    for (Card c : hand) {
      int val = c.getGameValue();
      if (val == 1) aceCount++;
      sum += val;
    }
    boolean usableAce = aceCount > 0 && sum + 10 <= 21;
    return new int[]{usableAce ? sum + 10 : sum, usableAce ? 1 : 0};
  }

  /**
   * Determines if the player is busted (i.e., hand value exceeds 21).
   *
   * @return true if the player is busted; false otherwise
   */
  public boolean isBusted() {
    return getHandValue()[0] > 21;
  }

  /**
   * Calculates the score of the player's current hand.
   * This is the same as the hand value considering usable ace logic.
   *
   * @return the final score of the hand
   */
  public int calculateScore() {
    return getHandValue()[0];
  }
}
