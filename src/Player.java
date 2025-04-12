import java.util.*;

public class Player {
  protected final List<Card> hand = new ArrayList<>();

  public void addCard(Card c) {
    hand.add(c);
  }

  public List<Card> getHand() {
    return hand;
  }

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
}
