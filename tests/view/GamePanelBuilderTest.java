package view;

import model.Card;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit5 tests for the GamePanelBuilder utility class.
 * This test class ensures that card labels, buttons, and panels are correctly constructed and updated.
 */
public class GamePanelBuilderTest {

  /**
   * Tests the creation of a hidden card panel.
   */
  @Test
  void testCreateHiddenCardPanel() {
    JPanel panel = GamePanelBuilder.createHiddenCardPanel();
    assertEquals(2, panel.getComponentCount(), "Panel should contain 2 hidden cards");
    assertTrue(panel.getComponent(0) instanceof JLabel, "Components should be JLabels");
  }

  /**
   * Tests button creation utility.
   */
  @Test
  void testCreateButton() {
    JButton btn = GamePanelBuilder.createButton("Hit");
    assertEquals("Hit", btn.getText(), "Button label should match");
    assertEquals(new Dimension(120, 40), btn.getPreferredSize(), "Button size should be 120x40");
  }

  /**
   * Tests creation of a JLabel representing a card.
   * Verifies it is not null and has a white background.
   */
  @Test
  void testCreateCardLabel() {
    JLabel label = GamePanelBuilder.createCardLabel("assets/cards/back1.png");
    assertNotNull(label.getIcon(), "Card label should contain an icon");
    assertEquals(Color.WHITE, label.getBackground(), "Card background should be white");
  }

  /**
   * Tests that a panel is properly updated with card labels.
   * Verifies the component count and type after update.
   */
  @Test
  void testUpdatePanel() {
    JPanel panel = new JPanel();
    List<Card> cards = List.of(new Card("Hearts", 1), new Card("Spades", 13));

    GamePanelBuilder.updatePanel(cards, panel);

    assertEquals(2, panel.getComponentCount(), "Panel should contain 2 card labels");
    assertTrue(panel.getComponent(0) instanceof JLabel, "Panel components should be JLabel instances");
  }
}
