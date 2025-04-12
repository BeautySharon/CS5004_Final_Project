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

  // 用于状态计算：将 J/Q/K 映射为 10
  public int getGameValue() {
    if (value >= 11) return 10;
    return value;
  }

  public String toString() {
    return value + " of " + suit;
  }

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

  public String getImagePath() {
    return "assets/cards/" + getImageFileName();
  }
}

