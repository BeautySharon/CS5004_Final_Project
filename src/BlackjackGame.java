public class BlackjackGame {
  private Deck deck = new Deck();
  private Player player = new Player();
  private Dealer dealer = new Dealer();

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
  public void reset() {
    this.deck = new Deck();
    this.player = new Player();
    this.dealer = new Dealer();
    // 发新牌
    player.addCard(deck.drawCard());
    player.addCard(deck.drawCard());
    dealer.addCard(deck.drawCard());
    dealer.addCard(deck.drawCard());
  }


  public int[] getPlayerState() {
    int[] val = player.getHandValue();
    return new int[]{val[0], dealer.getVisibleCardValue(), val[1]};
  }
}
