public class Card {
  private final String suit;
  private final int value;

  public Card(String suit, int value) {
    this.suit = suit;
    this.value = value;
  }

  public int getValue() {
    return value;
  }

  public String toString() {
    return value + " of " + suit;
  }
}
