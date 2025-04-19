package model;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * {@code AIAdvisor} provides advice on optimal actions (Hit or Stand)
 * based on a pre-trained Q-table loaded from a JSON file.
 *
 * <p>The Q-table maps game states to a map of action values
 * (e.g., {"Hit": 0.6, "Stand": 0.4}) and selects the action with the highest score.</p>
 */
public class AIAdvisor {

  /**
   * Q-table loaded from a JSON file.
   * Maps a game state string (e.g., "(18, 10, True)") to action-value pairs.
   */
  private final Map<String, Map<String, Double>> qTable;

  /**
   * Constructs an {@code AIAdvisor} and loads the Q-table from the given path.
   *
   * @param qTablePath Path to the Q-table JSON file
   */
  public AIAdvisor(String qTablePath) {
    this.qTable = loadQTable(qTablePath);
    System.out.println("Q-table loaded with " + qTable.size() + " entries.");
  }

  /**
   * Returns the optimal action ("Hit" or "Stand") for the given game state
   * based on Q-table scores. If the state is not found, returns "Unknown".
   *
   * @param total       The player's current hand total (e.g., 17)
   * @param dealerCard  The dealer's visible card value (e.g., 10)
   * @param usableAce   1 if player has a usable ace, 0 otherwise
   * @return "Hit", "Stand", or "Unknown" if state not found
   */
  public String getOptimalAction(int total, int dealerCard, int usableAce) {
    String key = "(" + total + ", " + dealerCard + ", " + (usableAce == 1 ? "True" : "False") + ")";
    System.out.println("🔍 Looking for key: " + key);

    Map<String, Double> actions = qTable.get(key);
    if (actions == null) {
      return "Unknown";
    }

    return actions.get("hit") > actions.get("stand") ? "Hit" : "Stand";
  }

  /**
   * Loads the Q-table from a JSON file and deserializes it into a nested map.
   *
   * @param filename Path to the Q-table JSON file
   * @return A nested map of state-action Q-values
   */
  private Map<String, Map<String, Double>> loadQTable(String filename) {
    try {
      Gson gson = new Gson();
      FileReader reader = new FileReader(filename);
      Type type = new TypeToken<Map<String, Map<String, Double>>>() {}.getType();
      return gson.fromJson(reader, type);
    } catch (Exception e) {
      e.printStackTrace();
      return Map.of();  // Return empty map as fallback
    }
  }
}
