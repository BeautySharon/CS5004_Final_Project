package model;
/**
 * Represents a playing card with a suit and value.
 * Provides utility methods for game logic and UI image support.
 */
public class Card {

  /** The suit of the card (e.g., "Hearts", "Spades"). */
  private final String suit;

  /** The value of the card (1 for Ace, 11 for Jack, 12 for Queen, 13 for King). */
  private final int value;

  /**
   * Constructs a Card object with the given suit and value.
   *
   * @param suit  the suit of the card
   * @param value the value of the card (1-13)
   */
  public Card(String suit, int value) {
    this.suit = suit;
    this.value = value;
  }

  /**
   * Returns the raw value of the card.
   *
   * @return the card value (1-13)
   */
  public int getValue() {
    return value;
  }

  /**
   * Returns the game value used in scoring.
   * Face cards (Jack, Queen, King) are worth 10 points.
   *
   * @return the game value of the card
   */
  public int getGameValue() {
    if (value >= 11) return 10;
    return value;
  }

  /**
   * Returns a string representation of the card (e.g., "11 of Hearts").
   *
   * @return a string representing the card
   */
  @Override
  public String toString() {
    return value + " of " + suit;
  }

  /**
   * Returns the file name of the image representing the card.
   * Converts face values (1, 11-13) to "ace", "jack", "queen", or "king".
   *
   * @return the image file name of the card
   */
  public String getImageFileName() {
    String valueStr;
    switch (value) {
      case 1:  valueStr = "ace"; break;
      case 11: valueStr = "jack"; break;
      case 12: valueStr = "queen"; break;
      case 13: valueStr = "king"; break;
      default: valueStr = String.valueOf(value); break;
    }
    return valueStr + "_of_" + suit.toLowerCase() + ".png";
  }

  /**
   * Returns the full path to the image file of the card,
   * assuming it's stored under "assets/cards/".
   *
   * @return the full relative image path
   */
  public String getImagePath() {
    return "assets/cards/" + getImageFileName();
  }
}
