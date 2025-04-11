public class BlackjackGame {
  private final Deck deck = new Deck();
  private final Player player = new Player();
  private final Dealer dealer = new Dealer();

  public BlackjackGame() {
    player.addCard(deck.drawCard());
    player.addCard(deck.drawCard());
    dealer.addCard(deck.drawCard());
    dealer.addCard(deck.drawCard());
  }

  public Player getPlayer() {
    return player;
  }

  public Dealer getDealer() {
    return dealer;
  }

  public void playerHit() {
    player.addCard(deck.drawCard());
  }

  public String getPlayerHandString() {
    return player.getHand().toString();
  }

  public String getDealerVisibleCardString() {
    return dealer.getHand().get(0).toString();
  }

  public int[] getPlayerState() {
    int[] val = player.getHandValue();
    return new int[]{val[0], dealer.getVisibleCardValue(), val[1]};
  }
}
