package model;
/**
 * Represents the dealer in a Blackjack game.
 * Inherits from the {@link Player} class and includes dealer-specific behaviors,
 * such as hiding and revealing the second card.
 */
public class Dealer extends Player {

  /** Flag indicating whether the dealer's second card is revealed. */
  private boolean revealSecondCard = false;

  /**
   * Reveals the dealer's second card.
   * Typically called after the player's turn ends.
   */
  public void revealCard() {
    this.revealSecondCard = true;
  }

  /**
   * Returns the game value of the dealer's visible card (the first card).
   * Used by the player to decide their move before the dealer reveals the full hand.
   *
   * @return the game value of the dealer's first (visible) card
   */
  public int getVisibleCardValue() {
    return hand.get(0).getGameValue();
  }
}
