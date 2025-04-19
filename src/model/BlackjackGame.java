package model;
/**
 * Represents a game of Blackjack between a player and a dealer.
 * Handles the game flow including dealing, hitting, and retrieving hand states.
 */
public class BlackjackGame {

  /** The deck used in the game. Automatically shuffled upon initialization or reset. */
  private Deck deck = new Deck();

  /** The player participating in the game. */
  private Player player = new Player();

  /** The dealer (AI opponent) in the game. */
  private Dealer dealer = new Dealer();

  /**
   * Constructs a new BlackjackGame instance and deals two cards to both the player and the dealer.
   */
  public BlackjackGame() {
    player.addCard(deck.drawCard());
    player.addCard(deck.drawCard());
    dealer.addCard(deck.drawCard());
    dealer.addCard(deck.drawCard());
  }

  /**
   * Returns the player object.
   *
   * @return the player
   */
  public Player getPlayer() {
    return player;
  }

  /**
   * Returns the dealer object.
   *
   * @return the dealer
   */
  public Dealer getDealer() {
    return dealer;
  }

  /**
   * Returns the opponent of the player, which is the dealer in this case.
   *
   * @return the opponent (dealer)
   */
  public Player getOpponent() {
    return dealer;
  }

  /**
   * Performs a "hit" action for the player, drawing one card from the deck.
   */
  public void playerHit() {
    player.addCard(deck.drawCard());
  }

  /**
   * Returns the player's current hand as a string.
   *
   * @return string representation of the player's hand
   */
  public String getPlayerHandString() {
    return player.getHand().toString();
  }

  /**
   * Returns the string representation of the dealer's visible (first) card.
   *
   * @return string of the dealer's first card
   */
  public String getDealerVisibleCardString() {
    return dealer.getHand().get(0).toString();
  }

  /**
   * Resets the game state by creating a new deck, player, and dealer.
   * Deals two new cards to each participant.
   */
  public void reset() {
    this.deck = new Deck();
    this.player = new Player();
    this.dealer = new Dealer();
    player.addCard(deck.drawCard());
    player.addCard(deck.drawCard());
    dealer.addCard(deck.drawCard());
    dealer.addCard(deck.drawCard());
  }

  /**
   * Returns the player's game state for decision-making or AI training.
   *
   * @return an array of [player score, dealer visible card value, player usable ace flag]
   */
  public int[] getPlayerState() {
    int[] val = player.getHandValue();
    return new int[]{val[0], dealer.getVisibleCardValue(), val[1]};
  }

  /**
   * Performs a "hit" action for the dealer, drawing one card from the deck.
   */
  public void opponentHit() {
    dealer.addCard(deck.drawCard());
  }

  /**
   * Returns the dealer's game state for AI evaluation or visualization.
   *
   * @return an array of [dealer score, dealer visible card value, dealer usable ace flag]
   */
  public int[] getOpponentState() {
    int[] val = dealer.getHandValue();
    return new int[]{val[0], dealer.getVisibleCardValue(), val[1]};
  }
}
