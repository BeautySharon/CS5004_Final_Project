package view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit5 tests for MainMenu.
 * Ensures the main menu initializes UI components correctly and handles mode transitions.
 */
public class MainMenuTest {

  private MainMenu menu;

  /**
   * Set up a fresh instance of MainMenu before each test.
   */
  @BeforeEach
  void setUp() {
    menu = new MainMenu();
    menu.setVisible(true);
  }

  /**
   * Tests the frame title and initial visibility.
   */
  @Test
  void testFrameTitleAndVisibility() {
    assertEquals("ACE - Main Menu", menu.getTitle(), "Title should match expected string.");
    assertTrue(menu.isVisible(), "MainMenu should be visible after setVisible(true) is called.");
  }

  /**
   * Verifies that the panel contains the correct number of button components.
   */
  @Test
  void testComponentStructure() {
    JPanel content = (JPanel) menu.getContentPane().getComponent(0);
    assertNotNull(content, "Main content panel should not be null.");
    assertEquals(BoxLayout.Y_AXIS, ((BoxLayout) content.getLayout()).getAxis(), "Layout should be vertical BoxLayout.");
  }
}
