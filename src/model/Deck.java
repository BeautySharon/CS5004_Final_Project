package model;
import java.util.*;

/**
 * Represents a standard 52-card deck used in card games.
 * The deck contains cards from all four suits (Hearts, Diamonds, Clubs, Spades),
 * with values ranging from 1 (Ace) to 13 (King).
 * The deck is automatically shuffled upon creation.
 */
public class Deck {

  /** The list of cards currently in the deck. */
  private final List<Card> cards = new ArrayList<>();

  /**
   * Constructs a new shuffled deck containing 52 standard playing cards.
   * Each suit has 13 cards with values from 1 (Ace) to 13 (King).
   */
  public Deck() {
    String[] suits = {"Hearts", "Diamonds", "Clubs", "Spades"};

    for (String suit : suits) {
      for (int i = 1; i <= 13; i++) {
        cards.add(new Card(suit, i));
      }
    }

    // Shuffle the deck to randomize card order
    Collections.shuffle(cards);
  }

  /**
   * Draws (removes and returns) the top card from the deck.
   *
   * @return the drawn card
   * @throws IllegalStateException if the deck is empty
   */
  public Card drawCard() {
    if (cards.isEmpty()) {
      throw new IllegalStateException("Deck is empty");
    }
    return cards.remove(0);
  }
}
