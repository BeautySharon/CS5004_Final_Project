package view;
import javax.swing.*;
import java.awt.*;
import java.util.List;
import model.Card;

/**
 * GamePanelBuilder is a utility class that provides static helper methods
 * for building card panels and UI elements used in the Blackjack game.
 */
public class GamePanelBuilder {

  /**
   * Creates a JPanel with two face-down card labels.
   *
   * @return A JPanel containing hidden cards.
   */
  public static JPanel createHiddenCardPanel() {
    JPanel panel = new JPanel(new FlowLayout());
    panel.setOpaque(false);
    for (int i = 0; i < 2; i++) {
      panel.add(createCardLabel("assets/cards/back1.png"));
    }
    return panel;
  }

  /**
   * Creates a styled JButton with a given label.
   *
   * @param text The text to display on the button.
   * @return A JButton with the specified label and size.
   */
  public static JButton createButton(String text) {
    JButton btn = new JButton(text);
    btn.setPreferredSize(new Dimension(120, 40));
    return btn;
  }

  /**
   * Creates a JLabel representing a card using an image at the given path.
   *
   * @param path Path to the image file.
   * @return A JLabel with scaled and bordered card image.
   */
  public static JLabel createCardLabel(String path) {
    ImageIcon icon = new ImageIcon(path);
    Image img = icon.getImage().getScaledInstance(100, 145, Image.SCALE_SMOOTH);
    JLabel label = new JLabel(new ImageIcon(img));
    label.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
    label.setOpaque(true);
    label.setBackground(Color.WHITE);
    return label;
  }

  /**
   * Updates the given panel with the specified list of cards,
   * replacing its content with visual representations of the cards.
   *
   * @param cards The list of cards to display.
   * @param panel The panel to update.
   */
  public static void updatePanel(List<Card> cards, JPanel panel) {
    panel.removeAll();
    for (Card c : cards) {
      panel.add(createCardLabel(c.getImagePath()));
    }
    panel.revalidate();
    panel.repaint();
  }
}
