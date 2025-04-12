import java.util.*;

public class Deck {
  private final List<Card> cards = new ArrayList<>();

  public Deck() {
    String[] suits = {"Hearts", "Diamonds", "Clubs", "Spades"};

    for (String suit : suits) {
      for (int i = 1; i <= 13; i++) {
        cards.add(new Card(suit, i));
      }
    }

    Collections.shuffle(cards);
  }

  public Card drawCard() {
    if (cards.isEmpty()) throw new IllegalStateException("Deck is empty");
    return cards.remove(0);
  }
}
