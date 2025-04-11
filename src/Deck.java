import java.util.*;

public class Deck {
  private final List<Card> cards = new ArrayList<>();

  public Deck() {
    String[] suits = {"Hearts", "Diamonds", "Clubs", "Spades"};
    for (String suit : suits) {
      for (int i = 1; i <= 10; i++) {
        cards.add(new Card(suit, i));
        if (i == 10) { // Face cards
          cards.add(new Card(suit, 10));
          cards.add(new Card(suit, 10));
        }
      }
    }
    Collections.shuffle(cards);
  }

  public Card drawCard() {
    if (cards.isEmpty()) throw new IllegalStateException("Deck is empty");
    return cards.remove(0);
  }
}
