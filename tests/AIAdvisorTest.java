package model;

import model.AIAdvisor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit5 tests for AIAdvisor, verifying Q-table loading
 * and optimal action decisions based on Q-values.
 */
public class AIAdvisorTest {

  private String testQTablePath;

  /**
   * Sets up a mock Q-table in a temporary JSON file before each test.
   */
  @BeforeEach
  void setUp() throws IOException {
    // Create mock Q-table JSON content
    String json = """
        {
          "(18, 10, True)": { "hit": 0.4, "stand": 0.6 },
          "(13, 2, False)": { "hit": 0.8, "stand": 0.2 }
        }
        """;

    // Write to temporary file
    java.io.File tempFile = java.io.File.createTempFile("qtable_test", ".json");
    try (FileWriter writer = new FileWriter(tempFile)) {
      writer.write(json);
    }
    testQTablePath = tempFile.getAbsolutePath();
  }

  /**
   * Tests loading the Q-table and ensures the correct number of entries are loaded.
   */
  @Test
  void testLoadQTable() {
    AIAdvisor advisor = new AIAdvisor(testQTablePath);
    String action = advisor.getOptimalAction(18, 10, 1);
    assertNotNull(action, "Action should not be null after Q-table load");
    assertTrue(action.equals("Stand"), "Expected best action is 'Stand'");
  }

  /**
   * Tests optimal action getters for both known and unknown states.
   */
  @Test
  void testGetOptimalAction() {
    AIAdvisor advisor = new AIAdvisor(testQTablePath);

    // Known state
    String action1 = advisor.getOptimalAction(13, 2, 0);
    assertEquals("Hit", action1, "Q-value prefers hit over stand");

    // Unknown state
    String action2 = advisor.getOptimalAction(20, 5, 1);
    assertEquals("Unknown", action2, "Should return 'Unknown' for missing key");
  }
}
