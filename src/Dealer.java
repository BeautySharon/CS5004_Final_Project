public class Dealer extends Player {
  private boolean revealSecondCard = false;

  public void revealCard() {
    this.revealSecondCard = true;
  }

  public int getVisibleCardValue() {
    return hand.get(0).getGameValue(); // ✅ 关键修改点
  }
}
